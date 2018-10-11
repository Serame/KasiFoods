package dev.com.jtd.toodles.background.serviceworkers;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.gson.Gson;

import app.AppController;

public class UserClientServerComm extends CommService {

    private AppController appController;
    private Context context;
    private ProgressDialog progressDialog;
    private String urlJSON = "https://kasifoods-218712.appspot.com/_ah/api/menuApi/v1/";
    private Gson gson;
    private GoogleAccountCredential credential;


    @Override
    public void showDailog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void setProgressDialog(ProgressDialog progressDialog) {

    }

    @Override
    public void setContext(Context context) {

    }
}
