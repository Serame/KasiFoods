package dev.com.jtd.toodles.view;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import app.AppController;
import dev.com.jtd.toodles.R;

/**
 * Created by smoit on 2017/04/17.
 */

public class ConfirmationFragment extends DialogFragment {

    private Button btnOk;
    private TextView txtOrderNumber;
    private  AppController appController;
    private int orderNumber = 0;
    public static final String CLASS_NAME = "ConfirmationFragment";

    public ConfirmationFragment() {

        Log.w("ConfirmFragment","Launched Confirmation Fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appController = AppController.getInstance();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        orderNumber = args.getInt("orderNumber");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.confirmation_fragment,null,false);
        btnOk = (Button) view.findViewById(R.id.btnOrderOk);
        txtOrderNumber = (TextView) view.findViewById(R.id.txtOrderNumber);
        txtOrderNumber.setText(txtOrderNumber.getText()+""+orderNumber);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appController.getBunniesCart().clearCart();
                Intent intent = new Intent(getActivity(),MainMenuActivity.class);
                getActivity().startActivity(intent);
                dismiss();
                getActivity().finish();
                Log.w("ConfirmFrag","OrdersActivity destroyed");

            }
        });
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appController.getBunniesCart().clearCart();
        Intent intent = new Intent(getActivity(),MainMenuActivity.class);
        getActivity().startActivity(intent);
        dismiss();
        getActivity().finish();
        Log.w("ConfirmFrag","OrdersActivity destroyed");

    }
}
