package dev.com.jtd.toodles.background;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import app.AppController;

/**
 * Created by smoit on 2017/05/13.
 */

public class KotaOrderInstanceID extends FirebaseInstanceIdService {

    public static final String TAG = "Registration";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        AppController.getInstance().updateFirebaseToken(FirebaseInstanceId.getInstance().getToken());
        Log.w("AppToken",FirebaseInstanceId.getInstance().getToken());


    }
}

