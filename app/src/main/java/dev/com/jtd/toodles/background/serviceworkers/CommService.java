package dev.com.jtd.toodles.background.serviceworkers;

import android.app.ProgressDialog;
import android.content.Context;

public abstract class CommService {

    public CommService()
    {

    }

    public  abstract void showDailog();
    public abstract  void dismissDialog();
    public abstract  void setProgressDialog(ProgressDialog progressDialog);
    public  abstract void setContext(Context context);
}
