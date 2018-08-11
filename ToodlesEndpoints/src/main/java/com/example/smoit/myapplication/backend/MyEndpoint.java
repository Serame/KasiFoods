/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.smoit.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Named;

import toodles.com.za.work.model.PlacedOrders;
import toodles.com.za.work.dao.ToodlesDBReader;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.smoit.example.com",
                ownerName = "backend.myapplication.smoit.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */

 @ApiMethod(name = "sayHi")
    public List<PlacedOrders> sayHi(@Named("name") String name) {

     List<PlacedOrders> placedOrderses = null;
     try {
        ToodlesDBReader  toodlesDBReader = new ToodlesDBReader();
         placedOrderses = toodlesDBReader.getAllPlacedOrders(name,0);
     } catch (SQLException e) {
         e.printStackTrace();
     } catch (ClassNotFoundException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }

        return placedOrderses;
    }
}
