package toodles.com.za.work.services;

import com.google.api.control.ServiceManagementConfigFilter;
import com.google.api.control.extensions.appengine.GoogleAppEngineControlFilter;
import com.google.api.server.spi.EndpointsServlet;
import com.google.api.server.spi.guice.EndpointsModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.servlet.GuiceFilter;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

import toodles.com.za.work.services.KasiFoodsEndpoint;
import toodles.com.za.work.services.MenuEndpoint;


public class KasiFoodsEndpointModule extends EndpointsModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();

        bind(ServiceManagementConfigFilter.class).in(Singleton.class);
        filter("/_ah/api/*").through(ServiceManagementConfigFilter.class);

        Map<String, String> apiController = new HashMap<String, String>();
        apiController.put("endpoints.projectId", "xenon-lantern-213109");
        apiController.put("endpoints.serviceName", "xenon-lantern-213109.appspot.com");

        bind(GoogleAppEngineControlFilter.class).in(Singleton.class);
        filter("/_ah/api/*").through(GoogleAppEngineControlFilter.class, apiController);

       /* bind(MenuEndpoint.class).toInstance(new MenuEndpoint());
        configureEndpoints("/_ah/api/*", ImmutableList.of(MenuEndpoint.class));*/

        bind(KasiFoodsEndpoint.class).toInstance(new KasiFoodsEndpoint());
        configureEndpoints("/_ah/api/*", ImmutableList.of(KasiFoodsEndpoint.class));




    }
}
