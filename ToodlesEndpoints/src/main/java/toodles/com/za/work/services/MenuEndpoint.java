package toodles.com.za.work.services;

import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import toodles.com.za.work.dao.ToodlesDBReader;
import toodles.com.za.work.model.Bunny;
import toodles.com.za.work.model.BunnyIngredients;
import toodles.com.za.work.model.Constants;
import toodles.com.za.work.model.OrderDescr;
import toodles.com.za.work.model.OrderID;
import toodles.com.za.work.model.OrderWrapper;
import toodles.com.za.work.model.PlacedOrders;
import toodles.com.za.work.model.ToodlesMessage;
import toodles.com.za.work.model.User;

/**
 * Created by smoit on 2016/12/27.
 */

@Api(
        name = "menuApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "services.work.za.com.toodles",
                ownerName = "services.work.za.com.toodles",
                packagePath = ""
        )
)
public class MenuEndpoint {

        private ToodlesDBReader toodlesDBReader;

        public MenuEndpoint()
        {
            try {
                this.toodlesDBReader = new ToodlesDBReader();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }

        @ApiMethod(name = "testing", httpMethod = ApiMethod.HttpMethod.POST,
                    scopes = {Constants.EMAIL_SCOPE},
                    clientIds = {Constants.WEB_CLIENT_ID,
                                 Constants.ANDROID_CLIENT_ID,
                                 Constant.API_EXPLORER_CLIENT_ID},
                audiences = {Constants.ANDROID_AUDIENCE})
        public ToodlesMessage testing(User user)
        {
            ToodlesMessage message = new ToodlesMessage("Message has been recieved");
            message.setStatus(ToodlesMessage.STATUS_OK);

            return message;


        }

        @ApiMethod(name = "registerNewCustomer",
                   httpMethod = ApiMethod.HttpMethod.POST)
        public ToodlesMessage registerNewCustomer(User user)
        {
            ToodlesMessage tm = new ToodlesMessage();

            try {
                toodlesDBReader = new ToodlesDBReader();
                int result = toodlesDBReader.addNewUser(user);

                if(result < 1)
                {
                    tm.setOrderID(0);
                    tm.setMessage("User not added.  Error occured, please contact support or try again");
                    tm.setStatus(ToodlesMessage.STATUS_NOT_OK);
                }
                else
                {
                    tm.setOrderID(0);
                    tm.setMessage("User added");
                    tm.setStatus(ToodlesMessage.STATUS_OK);

                }


            } catch (ClassNotFoundException | SQLException | IOException | ParseException e) {

                e.printStackTrace();
                tm.setOrderID(0);
                tm.setStatus(ToodlesMessage.STATUS_NOT_OK);
                tm.setMessage(e.getMessage());
            }

            return tm;

        }

        @ApiMethod(name = "signIn",
                   httpMethod = ApiMethod.HttpMethod.POST,
                   path = "signIn",
                   scopes = {Constants.EMAIL_SCOPE},
                   clientIds = {Constants.WEB_CLIENT_ID,
                                Constants.ANDROID_CLIENT_ID,
                                Constant.API_EXPLORER_CLIENT_ID},
                    audiences = {Constants.ANDROID_AUDIENCE})
        public User signIn(User user){

            User u = null;
            try {
                toodlesDBReader = new ToodlesDBReader();
                 u = toodlesDBReader.getUser(user);
            } catch (SQLException | ParseException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

            return u;
        }

    @ApiMethod(name = "testUser",
            httpMethod = ApiMethod.HttpMethod.POST,
            scopes = {Constants.EMAIL_SCOPE},
            clientIds = {Constants.WEB_CLIENT_ID,
                    Constants.ANDROID_CLIENT_ID,
                    Constant.API_EXPLORER_CLIENT_ID},
            audiences = {Constants.ANDROID_AUDIENCE} )
    public ToodlesMessage testUser(OrderWrapper ow)
    {
        ToodlesMessage tm = new ToodlesMessage();
        tm.setOrderID(1);
        tm.setMessage("Order Recieved");
        tm.setStatus(ToodlesMessage.STATUS_OK);

        return tm;

    }

    @ApiMethod(name = "updateShopFCMToken",
                path = "updateShopFCMToken")
    public ToodlesMessage updateShopFCMToken(@Named("refreshedToken") String refreshedToken)
    {
        ToodlesMessage message = new ToodlesMessage("");
        try {
            toodlesDBReader = new ToodlesDBReader();
            int result = toodlesDBReader.updateShopToken(refreshedToken);
            message.setMessage("Token successfully update");
            message.setStatus(ToodlesMessage.STATUS_OK);
            message.setOrderID(0);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            message.setMessage(e.getMessage());
            message.setStatus(ToodlesMessage.STATUS_NOT_OK);
            message.setOrderID(0);
            e.printStackTrace();

        }

        return message;
    }


    @ApiMethod(name = "insertNewOrder",
                   httpMethod = ApiMethod.HttpMethod.POST,
                   path = "NewOrder",
                scopes = {Constants.EMAIL_SCOPE},
                clientIds = {Constants.WEB_CLIENT_ID,
                        Constants.ANDROID_CLIENT_ID,
                        Constant.API_EXPLORER_CLIENT_ID},
                audiences = {Constants.ANDROID_AUDIENCE})
        public ToodlesMessage insertNewOrder(OrderWrapper orders){
            Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
            ArrayList<OrderDescr> ordersArray = new ArrayList<OrderDescr>();

            int created= 0;
            ToodlesMessage message = new ToodlesMessage();
            OrderDescr order = null;

            try {

               /*  for (int x = 0; x<= orders.size()-1;x++) {


                    order = gson.fromJson(orders.get(x), OrderDescr.class);
                    ordersArray.add(order);

                }*/
                toodlesDBReader = new ToodlesDBReader();
                int orderid = toodlesDBReader.getOrderID();
//                order.setOrderid(orderid);
                ArrayList<OrderDescr> ods = (ArrayList<OrderDescr>) orders.getOrdersList();

                created = toodlesDBReader.placeOrder(orders,orderid);

              //  created = toodlesDBReader.CreateNewOrder(ordersArray);
                if(created >0)
                {
                    message.setMessage("Order Successfully created");
                    message.setStatus(ToodlesMessage.STATUS_OK);
                    message.setOrderID(created);
                    ToodlesMessenger tm = new ToodlesMessenger();
                    String token = toodlesDBReader.getShopToken(1);
                    ArrayList<PlacedOrders> placedOrders = (ArrayList<PlacedOrders>) toodlesDBReader.getAllPlacedOrders("N",orderid);
                    tm.sendNewOrderToShop(token,orderid);
                    System.out.println("insertNewOrder(): Order inserted");

                }
                else
                {
                    message.setMessage("Order not created");
                    message.setStatus(ToodlesMessage.STATUS_NOT_OK);
                    message.setOrderID(created);
                }
            }
            catch (SQLException /*| JSONException*/ |NullPointerException |ClassNotFoundException | IOException | IllegalArgumentException |JSONException ex)
            {
                ex.printStackTrace();
                message.setMessage("Exception occured: "+ex.getMessage());
                message.setStatus(ToodlesMessage.STATUS_NOT_OK);
                message.setOrderID(created);

            }


            return message;
        }




        @ApiMethod (name = "CreateNewOrder",
                    httpMethod = ApiMethod.HttpMethod.POST,
                    scopes = {Constants.EMAIL_SCOPE},
                    clientIds = {Constants.WEB_CLIENT_ID,
                        Constants.ANDROID_CLIENT_ID,
                        Constant.API_EXPLORER_CLIENT_ID},
                audiences = {Constants.ANDROID_AUDIENCE,Constants.WEB_CLIENT_ID})
        public ToodlesMessage CreateNewOrder(){


            ToodlesMessage message = new ToodlesMessage();

            OrderDescr od = new OrderDescr();
            od.setCustormerID(1);
            od.setOrderid(3170708);
            od.setItemID(1);
            od.setExtrasID(0);
            od.setRemovedID(0);
            od.setPrice(10.0);
            od.setParentIND('Y');
            od.setParentID(0);
            od.setCartPosition(0);
            od.setAppToken("fksjflkajslfkjasljdflkajs");
            ArrayList<OrderDescr> odd = new ArrayList<>();
            odd.add(od);

            try {
                int result = new ToodlesDBReader().CreateNewOrder(odd);
                if(result == -1 )
                {
                    message.setMessage("Not successful");
                    message.setStatus(ToodlesMessage.STATUS_NOT_OK);
                    message.setOrderID(result);
                }
                else
                {

                    message.setMessage("Sucessful");
                    message.setStatus(ToodlesMessage.STATUS_OK);
                    message.setOrderID(result);

                }
            } catch (JSONException | SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }


            return  message;

        }

        @ApiMethod (name = "GetNewOrderID",
                    scopes = {Constants.EMAIL_SCOPE},
                    clientIds = {Constants.WEB_CLIENT_ID,
                        Constants.ANDROID_CLIENT_ID,
                        Constant.API_EXPLORER_CLIENT_ID},
                audiences = {Constants.ANDROID_AUDIENCE})
        public OrderID GetNewOrderID(User user){

            OrderID order_id = null;
            try {
                toodlesDBReader = new ToodlesDBReader();
                order_id = new OrderID();
                order_id.setOrder_id(this.toodlesDBReader.getOrderID());
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

            return order_id;
        }



        @ApiMethod (name = "GetMenuItems",
                    httpMethod = ApiMethod.HttpMethod.GET,
                    path = "getMenuItems",
                    scopes = {Constants.EMAIL_SCOPE},
                    clientIds = {Constants.WEB_CLIENT_ID,
                        Constants.ANDROID_CLIENT_ID,
                        Constant.API_EXPLORER_CLIENT_ID},
                audiences = {Constants.ANDROID_AUDIENCE}
        )
        public List GetMenuItems(@Named("itemType")int itemType) {
            List<Bunny> menuItems =  null;
            try {
                toodlesDBReader = new ToodlesDBReader();
                menuItems = toodlesDBReader.GetMenuItems(itemType);
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

            return  menuItems;

        }

       /* @ApiMethod(name = "closeOrder",
                   httpMethod = ApiMethod.HttpMethod.POST,
                    scopes = {Constants.EMAIL_SCOPE},
                    clientIds = {Constants.WEB_CLIENT_ID,
                        Constants.ANDROID_CLIENT_ID,
                        Constant.API_EXPLORER_CLIENT_ID},
                    audiences = {Constants.ANDROID_AUDIENCE})*/
       @ApiMethod(name = "closeOrder",
                  path = "closeOrder")
        public List<PlacedOrders> getClosedOrder(@Named("orderid") int orderID){

            ArrayList<PlacedOrders> placedOrdersList = null;
            try {
                toodlesDBReader = new ToodlesDBReader();
                placedOrdersList = toodlesDBReader.completeOrder(orderID);
                int respCode = new ToodlesMessenger().sendCompletedOrder(placedOrdersList,placedOrdersList.get(0).getAppToken());

                if(respCode == 200)
                {
                    System.out.println("Message sent out");

                }
                else
                    System.out.println("Message not sent out");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return placedOrdersList;
        }

@ApiMethod(name="getPlacedOrders",
           path="getPlaceOrders")
    public List<PlacedOrders> getPlacedOrders(@Named("status")String status){

        List<PlacedOrders> placedOrderses = null;

        try {
            toodlesDBReader = new ToodlesDBReader();
            placedOrderses = toodlesDBReader.getAllPlacedOrders(status,0);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


        return  placedOrderses;
    }

    /*
    @ApiMethod(name = "sayHi")
    public List sayHi(@Named("name") String name) {

        System.out.println("Fetching All Placed orders");
        List<PlacedOrders> placedOrderses = null;
        try {
            placedOrderses = toodlesDBReader.getAllPlacedOrders(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Orders fetched");

        return  placedOrderses;
    }*/

    /*<env-variables >
        <env-var name="GOOGLE_APPLICATION_CREDENTIALS"
            value="WEB-INF/KotaTime-c78209ce8e33.json"/>
    </env-variables> */

    //Please note that the below method testUser is to test the passing of Arraylist from client side



        @ApiMethod (name = "GetIngredients",
                httpMethod = ApiMethod.HttpMethod.GET,
                 path = "getIngredients",
                scopes = {Constants.EMAIL_SCOPE},
                clientIds = {Constants.WEB_CLIENT_ID,
                        Constants.ANDROID_CLIENT_ID,
                        Constant.API_EXPLORER_CLIENT_ID},
                audiences = {Constants.ANDROID_AUDIENCE})
        public List GetIngredients()
        {

            List<BunnyIngredients> bunnyIngredientsList = null;
            try {
                toodlesDBReader = new ToodlesDBReader();
                 bunnyIngredientsList = toodlesDBReader.GetIngredients();
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

            return  bunnyIngredientsList;
        }
}
