package toodles.com.za.work.dao;



import org.json.JSONException;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import toodles.com.za.work.model.Bunny;
import toodles.com.za.work.model.BunnyIngredients;
import toodles.com.za.work.model.OrderDescr;
import toodles.com.za.work.model.OrderWrapper;
import toodles.com.za.work.model.PlacedOrders;
import toodles.com.za.work.model.User;


/**
 * Created by serame on 7/16/2016.
 */
public class ToodlesDBReader {

    private Connection con;
    private CallableStatement cStmt;
    private ResultSet rs;
    private String sqlStatment;
    public static final String BUNNY_ID = "bunny_id";
    public static final String INGR_ID = "ingr_id";
    public static final String DESCR = "descr";
    public static final String PRICE = "price";

    public ToodlesDBReader() throws ClassNotFoundException, SQLException, IOException {

        Class.forName("com.mysql.jdbc.Driver");
        String connection = "jdbc:mysql://localhost:3306/toodlesdb";
        this.con = DriverManager.getConnection(connection,"root","Aseblief45@");
        this.sqlStatment = "";

        /*String instanceConnectionName = "kotatime-e7946:us-central1:toodles2";
        String databaseName = "toodlesdb";

        String url = null;
        if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

            Class.forName("com.mysql.jdbc.GoogleDriver");
            url = "jdbc:google:mysql://kotatime-e7946:us-central1:toodles2/toodlesdb";
            System.out.println("Using prod environment value");
        }
        else
        {
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://35.188.9.110:3306/toodlesdb";
            System.out.println("not Using prod environment value");

        }

         con = DriverManager.getConnection(url,"root","Aseblief45@");*/

        System.out.println("Connection created");

    }

    public void getAllUser()
    {
        System.out.println("This is test method for now");
    }

    public int addNewUser(User user) throws SQLException, ParseException {

        System.out.println("Inserting new user");
        System.out.println(user.toString());
        this.sqlStatment = "insert into customers(email,password,cell,name,surname,gender,dateofbirth,login_type,signupdate,tmstamp,userid) "+
                                    "values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(this.sqlStatment);
        int result = 0;

        Date date = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyyy-MM-dd");
        java.util.Date  d = df.parse(user.getDateofbirth());
        Date dob = new Date(d.getTime());

        preparedStatement.setString(1,user.getEmail());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setString(3,user.getCell());
        preparedStatement.setString(4,user.getName());
        preparedStatement.setString(5,user.getSurname());
        preparedStatement.setString(6,user.getGender());
        preparedStatement.setDate(7,dob);
        preparedStatement.setString(8,User.LOGIN_TYPE_APP);
        preparedStatement.setDate(9,date);
        preparedStatement.setDate(10,date);
        preparedStatement.setString(11,"smoitsheki");

        result = preparedStatement.executeUpdate();

        System.out.println("Inserting new user completed");

        return result;

    }



    public ArrayList<BunnyIngredients> GetIngredients() throws SQLException {
        ArrayList<BunnyIngredients> bunniesIngredientsList = null;
        BunnyIngredients bunnyIngredients = null;
        sqlStatment = "select b.bunny_id bunny_id,\n" +
                "\t   b.ingr_id ingr_id,\n" +
                "       i.descr,\n" +
                "       i.price price\n" +
                "from bunnyingredients b\n" +
                "join ingredients i on i.ITEM_ID = b.INGR_ID;";
        Statement stm = con.createStatement();
        rs = stm.executeQuery(sqlStatment);
       // cStmt = con.prepareCall(sqlStatment);


        bunniesIngredientsList = new ArrayList<BunnyIngredients>();
        while(rs.next())
        {
            bunnyIngredients = new BunnyIngredients(rs.getInt(BUNNY_ID),
                                                      rs.getInt(INGR_ID),
                                                      rs.getString(DESCR),
                                                      rs.getDouble(PRICE));
            bunniesIngredientsList.add(bunnyIngredients);

        }

        closetAll();
        return  bunniesIngredientsList;

    }

    public String getShopToken(int shopToken) throws SQLException {

        String token = "";
        sqlStatment = "select shopfcmtoken from shop where shopno = 1";

        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(sqlStatment);
        while(rs.next())
        {
            token = rs.getString("shopfcmtoken");
        }

        return token;



    }

    public int updateShopToken(String token) throws SQLException {

        int result = 0;
        sqlStatment = "update shop " +
                " set shopfcmtoken = '"+token+"'" +
                " where shopno = 1";
        Statement stm = con.createStatement();

        result = stm.executeUpdate(sqlStatment);
        stm.close();
        System.out.println("Token has been updated");

        return result;


    }

    public int placeOrder(OrderWrapper orderWrapper,int oi) throws SQLException {

        sqlStatment = "{CALL SP_ADDNEWORDER(?,?,?,?,?,?,?,?,?,?,?)}";
        OrderDescr orderDescr = null;
        ArrayList<OrderDescr> orders = (ArrayList<OrderDescr>) orderWrapper.getOrdersList();
        int orderID = oi;

        try {

            cStmt = con.prepareCall(sqlStatment);
            for (OrderDescr ods : orders) {

                int x = 1;
               /* `SP_ADDNEWORDER`(IN XORDERID INT(10),IN XCUSTID INT(10),IN XITEMID INT(10),
                            IN XEXTRASID INT(10),IN XREMOVEDID INT(10), IN XPRICE DOUBLE, IN XPARENTIND VARCHAR(10),

                           IN XCARTID INT(10),IN PARENTID INT(10))*/

                orderDescr = ods;
                if(orderDescr != null) {

                    System.out.println(orderDescr.toString());

                    cStmt.setInt(OrderDescr.ORDER_ID, orderID);
                    cStmt.setLong(OrderDescr.CUST_ID, ods.getCustormerID());
                    cStmt.setInt(OrderDescr.ITEM_ID, ods.getItemID());
                    cStmt.setInt(OrderDescr.EXTRAS_ID, ods.getExtrasID());
                    cStmt.setInt(OrderDescr.REMOVED_ID, ods.getRemovedID());
                    cStmt.setDouble(OrderDescr.PRICE, ods.getPrice());
                    cStmt.setDouble(OrderDescr.ADDED_ITEMS_TOTAL, ods.getAddedItemsTotal());
                    cStmt.setString(OrderDescr.PARENT_IND, ods.getParentIND() + "");
                    cStmt.setInt(OrderDescr.CART_POSITION, ods.getCartPosition());
                    cStmt.setInt(OrderDescr.PARENT_ID, ods.getParentID());
                    cStmt.setString(OrderDescr.APP_TOKEN, ods.getAppToken());
                    rs = cStmt.executeQuery();

                    x++;
                }


            }
            closetAll();
        }catch(SQLException | NullPointerException ex)
        {
            ex.printStackTrace();
            Logger l = Logger.getLogger(ToodlesDBReader.class.getName());
            l.info("There's an error somewhere.......");
            System.out.println("There's an error somewhere......");
            l.info(ex.getMessage());
            orderID = 0;


        }

        return  orderID;

    }


    public int CreateNewOrder(ArrayList<OrderDescr> orders) throws JSONException, SQLException {

        boolean orderCreated = false;
        int orderID = getOrderID();

        sqlStatment = "{CALL SP_ADDNEWORDER(?,?,?,?,?,?,?,?,?,?)}";

        OrderDescr orderDescr = null;

        try {
            cStmt = con.prepareCall(sqlStatment);

            for (OrderDescr ods : orders) {
                System.out.println("Looping...");
                int x = 1;
               /* `SP_ADDNEWORDER`(IN XORDERID INT(10),IN XCUSTID INT(10),IN XITEMID INT(10),
                            IN XEXTRASID INT(10),IN XREMOVEDID INT(10), IN XPRICE DOUBLE, IN XPARENTIND VARCHAR(10),

                           IN XCARTID INT(10),IN PARENTID INT(10))*/

                orderDescr = ods;
                if(orderDescr != null) {
                    System.out.println(orderDescr.toString());
                    cStmt.setInt(OrderDescr.ORDER_ID, orderID);
                    cStmt.setLong(OrderDescr.CUST_ID, orderDescr.getCustormerID());
                    cStmt.setInt(OrderDescr.ITEM_ID, orderDescr.getItemID());
                    cStmt.setInt(OrderDescr.EXTRAS_ID, orderDescr.getExtrasID());
                    cStmt.setInt(OrderDescr.REMOVED_ID, orderDescr.getRemovedID());
                    cStmt.setDouble(OrderDescr.PRICE, orderDescr.getPrice());
                    cStmt.setString(OrderDescr.PARENT_IND, orderDescr.getParentIND() + "");
                    cStmt.setInt(OrderDescr.CART_POSITION, orderDescr.getCartPosition());
                    cStmt.setInt(OrderDescr.PARENT_ID, orderDescr.getParentID());
                    cStmt.setString(OrderDescr.APP_TOKEN, orderDescr.getAppToken());
                    rs = cStmt.executeQuery();
                    System.out.println(x + "Order has been created");
                    x++;
                }


            }

            closetAll();
        }catch(SQLException | NullPointerException ex)
        {
            ex.printStackTrace();
            Logger l = Logger.getLogger(ToodlesDBReader.class.getName());
            l.info("There's an error somewhere F***");
            l.info(ex.getMessage());
            orderID = 0;

        }

        return orderID;



    }



    public User createNewUser(User user) throws SQLException, ParseException {
        User createdUser = null;
        /*(IN XEMAIL VARCHAR(30), IN XPASSOWRD VARCHAR(30),IN XCELL VARCHAR(10),
            IN XNAME VARCHAR(30),IN XSURNAME VARCHAR(30),IN XGENDER VARCHAR(6),
            IN XDATEOFBIRTH DATE)*/
        sqlStatment = "{CALL SP_ADDNEWCUSTOMER(?,?,?,?,?,?,?,?)}";
        if(user.getDateofbirth().equals("No Data"))
            user.setDateofbirth("01/01/1900");

        SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");
        java.util.Date d = sdf.parse(user.getDateofbirth());
        Date dob = new Date(d.getTime());
        cStmt = con.prepareCall(sqlStatment);
        cStmt.setString(User.EMAIL_COL,user.getEmail());
        cStmt.setString(User.PASSWORD_COL,user.getPassword());
        cStmt.setString(User.CELL_COL,user.getCell());
        cStmt.setString(User.NAME_COL,user.getName());
        cStmt.setString(User.SURNAME_COL,user.getSurname());
        cStmt.setString(User.GENDER_COL,user.getGender());
        cStmt.setDate(User.DATEOFBIRTH_COL,dob);
        cStmt.setString(User.LOGIN_TYPE_COL,user.getLoginType());
        rs = cStmt.executeQuery();
        if(rs.next())
        {
            //User(String name, String email, String cell, String password, String surname, String gender, Date dateofbirth)
            createdUser = new User(rs.getLong(User.CUST_ID_COL),
                                   rs.getString(User.NAME_COL),
                                   rs.getString(User.EMAIL_COL),
                                   rs.getString(User.CELL_COL),
                                   rs.getString(User.PASSWORD_COL),
                                   rs.getString(User.SURNAME_COL),
                                   rs.getString(User.GENDER_COL),
                                   rs.getDate(User.DATEOFBIRTH_COL).toString(),
                                   rs.getString(User.LOGIN_TYPE_COL));
        }

        return createdUser;

    }



    public ArrayList<Bunny> GetMenuItems(int itemType) throws SQLException {
        ArrayList<Bunny> BunniesList =  null;
        sqlStatment = "{CALL GetAllMenuItems(?)}";
        cStmt = con.prepareCall(sqlStatment);
        cStmt.setInt("xitemtype",itemType);
        rs = cStmt.executeQuery();

            Bunny bunny;
            BunniesList = new ArrayList<Bunny>();
            while(rs.next())
            {
                bunny = new Bunny(rs.getInt(Bunny.ITEM_ID),rs.getString(Bunny.DESCR),rs.getDouble(Bunny.PRICE));
                bunny.setName(rs.getString(Bunny.NAME));
                BunniesList.add(bunny);

            }

        closetAll();
        return BunniesList;
    }

    public int getOrderID() throws SQLException {

        int newOrderID = 0;

        sqlStatment = "{CALL SP_GENORDERID()}";
        cStmt = con.prepareCall(sqlStatment);
        rs = cStmt.executeQuery();

        if(rs.next())
        {
            newOrderID = rs.getInt("order_id");
        }
        rs.close();
        cStmt.close();

        return newOrderID;
    }

    public List<PlacedOrders> getAllPlacedOrders(String status,int orderID) throws SQLException {

        sqlStatment = "{CALL SP_GETNEW_ORDERS(?,?)}";
        cStmt = con.prepareCall(sqlStatment);
        cStmt.setString(PlacedOrders.STATUS_COLUMN_NAME,status);
        cStmt.setInt(OrderDescr.ORDER_ID,orderID); //We are passing zero which the SP will understand as pulling all incomplete orders
        rs = cStmt.executeQuery();
        List<PlacedOrders> placedOrders = new ArrayList<PlacedOrders>();
        PlacedOrders placedOrder = null;
        ArrayList<PlacedOrders> addedItems = new ArrayList<>();

        while(rs.next())
        {

            placedOrder = new PlacedOrders(rs.getInt(PlacedOrders.ORDER_ID),
                                           rs.getInt(PlacedOrders.CUST_ID),
                                           rs.getString(PlacedOrders.CUST_NAME),
                                           rs.getString(PlacedOrders.ITEM_NAME),
                                           rs.getDouble(PlacedOrders.PRICE),
                                           rs.getInt(PlacedOrders.PARENT_CHILD_LINK),
                                           rs.getInt(PlacedOrders.PARENT_ID),
                                           rs.getString(PlacedOrders.PARENT_IND),
                                           rs.getString(PlacedOrders.DESCR),
                                           rs.getDouble(PlacedOrders.TOT_AMOUNT));
            placedOrder.setAddedItemsTotal(rs.getDouble(PlacedOrders.ADDED_ITEMS_TOTAL));
            if(placedOrder.getParentIND() == "Y")
            {
                System.out.println(placedOrder.toString());
            }
            if(orderID != 0)
            {
                placedOrder.setAppToken(rs.getString(PlacedOrders.APP_TOKEN));
                placedOrder.setCompletionDate(DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT).format(System.currentTimeMillis()));

            }

            if(placedOrder.getParentIND().equals("N"))
            {
                addedItems.add(placedOrder);

            }


            placedOrders.add(placedOrder);
        }
        String addedItemsStr = "";
        for(int x = 0; x < placedOrders.size(); x++)
        {
            for(PlacedOrders pl:addedItems)
            {
                if(pl.getOrderID() == placedOrders.get(x).getOrderID() && pl.getParentID() == placedOrders.get(x).getParentChildLink())
                {
                    addedItemsStr += pl.getItemDescr()+",";

                }
            }

            placedOrders.get(x).setAddedItems(addedItemsStr);
            addedItemsStr = "";
        }





        closetAll();
        return placedOrders;

    }

    public User getUser(User usr) throws SQLException, ParseException {

        User user = null;

        sqlStatment = "{CALL SP_GETCUSTOMER(?,?)}";

        cStmt  = con.prepareCall(sqlStatment);
        cStmt.setString(User.EMAIL_COL,usr.getEmail());
        cStmt.setString(User.PASSWORD_COL,usr.getPassword());

        rs = cStmt.executeQuery();
        if(rs.next())
        {


            user = new User(rs.getInt(User.CUST_ID_COL),
                            rs.getString(User.NAME_COL),
                            rs.getString(User.EMAIL_COL),
                            rs.getString(User.CELL_COL),
                            rs.getString(User.PASSWORD_COL),
                            rs.getString(User.SURNAME_COL),
                            rs.getString(User.GENDER_COL),
                            rs.getDate(User.DATEOFBIRTH_COL).toString(),
                            rs.getString(User.LOGIN_TYPE_COL));

        }
        else
        {
            //IF the re requested user does not exist we return a user with customerID -1 and null values for all fields
            user = new User(-1,"null","null","null","null","null","null",null,"null");

        }

        if(user.getCustID() == -1 && usr.getLoginType().equals(User.LOGIN_TYPE_FACEBOOK))
        {

            user = createNewUser(usr);

        }

        closetAll();
        return  user;


    }


    public ArrayList<PlacedOrders> completeOrder(int orderID) throws SQLException {
        ArrayList<PlacedOrders> placedOrderse = null;
        ArrayList<PlacedOrders> bunnies = null;
        ArrayList<PlacedOrders> addedItems = null;
        sqlStatment = "update orders " +
                " set completed = 'Y', completionDate =  sysdate() " +
                " where orderid =  "+orderID+";";

        Statement stm = con.createStatement();

        int result = stm.executeUpdate(sqlStatment);

        if(result == 1)
        {
            placedOrderse = (ArrayList<PlacedOrders>) getAllPlacedOrders("Y",orderID);
            bunnies = new ArrayList<>();
            addedItems = new ArrayList<>();
            StringBuilder extra = new StringBuilder();
            double totAmount = 0;

            for(PlacedOrders pl:placedOrderse)
            {
                if(pl.getParentIND().equals("Y"))
                {
                    bunnies.add(pl);
                }
                else
                {
                    addedItems.add(pl);

                }
            }

            for(int x = 0; x < bunnies.size(); x++)
            {
                for(PlacedOrders a:addedItems)
                {
                    if(a.getParentID() == bunnies.get(x).getParentChildLink())
                    {
                        extra.append(","+a.getItemName());
                        totAmount += a.getPrice();

                    }
                }

                bunnies.get(x).setAddedItems(extra.toString());
                bunnies.get(x).setRemovedItems("-");
                bunnies.get(x).setPrice(bunnies.get(x).getPrice()+totAmount);
                totAmount = 0;
                extra = new StringBuilder();

                System.out.println(bunnies.get(x).toString());
            }





        }
        else
        {
            //Do something
            System.out.println("Ooopss someting went wrong");
        }
        return  bunnies;


    }













    public static void main(String[] args )
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = sdf.parse("1991-06-15");
            Date dob = new Date(date.getTime());
            System.out.println(dob.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void closetAll() throws SQLException {
        /*rs.close();
        cStmt.close();*/

    }


}