/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.serame.myapplication.backend.controller;

import com.example.serame.myapplication.backend.model.Bunny;
import com.example.serame.myapplication.backend.model.MenuTBLReader;
import com.mysql.jdbc.StringUtils;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        try {
            ArrayList<Bunny> bunniesList = new MenuTBLReader().GetMenuItems();
            JSONObject menuJSONObject = new JSONObject();
            JSONObject bunnyJSON;
            JSONArray items;
            for(int x= 0; x<= bunniesList.size()-1; x++)
            {
                bunnyJSON = new JSONObject();
                bunnyJSON.put(Bunny.PRICE,bunniesList.get(x).getPrice());
                bunnyJSON.put(Bunny.ITEM_ID,bunniesList.get(x).getID());
                List l = com.mysql.jdbc.StringUtils.split(bunniesList.get(x).getDescr(),",",true);
                items = new JSONArray();
                for(int t = 0; t<= l.size()-1; t++)
                {
                    items.add(l.get(t).toString());
                }
                bunnyJSON.put(Bunny.DESCR,items);
                menuJSONObject.put(Bunny.class.getSimpleName()+bunniesList.get(x).getID(),bunnyJSON);
            }

            System.out.println(menuJSONObject.toString());
            resp.setContentType("application/json");

            OutputStreamWriter os = new OutputStreamWriter(resp.getOutputStream());
            os.write(menuJSONObject.toJSONString());
            os.flush();
            os.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
