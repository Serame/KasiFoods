package toodles.com.za.work.services;

import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import toodles.com.za.work.dao.KasiFoodsDBReader;
import toodles.com.za.work.model.Constants;
import toodles.com.za.work.model.Kasi;

@Api(
        name = "kasiFoodsApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "services.work.za.com.toodles",
                ownerName = "services.work.za.com.toodles",
                packagePath = ""
        )
)

public class KasiFoodsEndpoint {

    @ApiMethod(name = "getKasiWithShops",
            httpMethod = ApiMethod.HttpMethod.GET,
            path = "getKasiWithShops",
            scopes = {Constants.EMAIL_SCOPE},
            clientIds = {Constants.WEB_CLIENT_ID,
                    Constants.ANDROID_CLIENT_ID,
                    Constant.API_EXPLORER_CLIENT_ID},
            audiences = {Constants.ANDROID_AUDIENCE})
    public HashMap<String,List> getKasiWithShops() throws SQLException, ClassNotFoundException {
            HashMap<String,List> listKasiShops = null;

        KasiFoodsDBReader kasiFoodsDBReader = new KasiFoodsDBReader();

        listKasiShops = kasiFoodsDBReader.getShopsListWithKasi();

        return listKasiShops;

    }

    @ApiMethod(name = "getKasis",
            httpMethod = ApiMethod.HttpMethod.GET,
            path = "kasis",
            scopes = {Constants.EMAIL_SCOPE},
            clientIds = {Constants.WEB_CLIENT_ID,
                    Constants.ANDROID_CLIENT_ID,
                    Constant.API_EXPLORER_CLIENT_ID},
            audiences = {Constants.ANDROID_AUDIENCE})
    public List<Kasi> getKasis() throws SQLException, ClassNotFoundException {

        System.out.println("getKasis()");
        KasiFoodsDBReader kasiFoodsDBReader = new KasiFoodsDBReader();

        ArrayList<Kasi> kasiList = (ArrayList<Kasi>) kasiFoodsDBReader.getKasiList();

        System.out.println(String.valueOf(kasiList.size()));
        return kasiList;
    }

}
