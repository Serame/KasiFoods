package dev.com.jtd.toodles.view;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import app.AppController;
import dev.com.jtd.toodles.R;
import dev.com.jtd.toodles.background.serviceworkers.MenuClientServerComm;
import dev.com.jtd.toodles.background.ToodlesDBAO;
import dev.com.jtd.toodles.model.Shop;
import dev.com.jtd.toodles.model.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnSignIn;
    private ProgressDialog pd;
    private TextView txtWarning;
    private TextView txtSignUpHere;
    public static final String LOGIN_SHARED_PREFERENCES = "LOGIN_SHARED_PREFERENCES";
    public static final String SP_SETTINGS = "SP_SETTINGS";
    public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final int ACCOUNT_PICKER_REQ_ID = 2;
    public static final String SIGNED_IN = "SIGNED_IN"; //This will be the name of the boolean value which represents the sign in state of the app
    private User user;
    private SharedPreferences logingSession;
    private LoginButton facebookLogginButton;
    private ProfileTracker profileTracker;
    private MenuClientServerComm csm;
    private static LoginActivity thisInstance;
    private AccessToken accessToken;
    private String propicUrl = "";
    private SharedPreferences spSettings;
    private String accountName;
    private GoogleAccountCredential credential;
    private Shop selectedShop;
    private AppController appController;


    private CallbackManager mCallBackManager;
    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Log.w("AccessToken",accessToken.getToken());

            Profile profile = Profile.getCurrentProfile();
            if(profile != null) {
                Uri uri = profile.getProfilePictureUri(10,10);
                Log.w("Profile Picture",uri.toString());
            }
            else {
                Log.w("Profile","Profile is null");
            }

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

            error.printStackTrace();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initGoogleAccount();
        init();



    }

    private void initGoogleAccount()
    {

        spSettings = getSharedPreferences(SP_SETTINGS,Context.MODE_PRIVATE);
        credential = GoogleAccountCredential.usingAudience(this,"server:client_id:756161739003-9e1gefoeo2f9tsnrbjchtsn2shfq7q8k.apps.googleusercontent.com");
        setAccountName(spSettings.getString(ACCOUNT_NAME,null));

        if(credential.getSelectedAccountName() == null)
        {
            Log.w("iniGoogleAccount","ChoosingAccount");
            chooseAccount();
            AppController.getInstance().setGoogleAccountCredential(credential);

        }
        else
            Log.w("initGoogleAccount",credential.getSelectedAccountName());
            AppController.getInstance().setGoogleAccountCredential(credential);

    }
    private void chooseAccount()
    {
        startActivityForResult(credential.newChooseAccountIntent(),ACCOUNT_PICKER_REQ_ID);
    }

    private void setAccountName(String accountName)
    {
        SharedPreferences.Editor editor = spSettings.edit();
        editor.putString(ACCOUNT_NAME,accountName);
        editor.apply();
        credential.setSelectedAccountName(accountName);
        this.accountName = accountName;
    }

    private void init()
    {
        Log.w("SignIn","onCreate()");

        appController = AppController.getInstance();
        selectedShop = appController.getSelectedShop();

        txtSignUpHere = findViewById(R.id.txtSignUp);
        txtSignUpHere.setMovementMethod(LinkMovementMethod.getInstance());



        thisInstance = this;
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        csm = new MenuClientServerComm();
        csm.setContext(this);
        csm.setProgressDialog(pd);

        logingSession = getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        long custID = logingSession.getLong(User.CUST_ID_COL,-1);

        ToodlesDBAO toodlesDBAO = new ToodlesDBAO(this);
        toodlesDBAO.test();

        if(custID == -1)
        {

            initializeFacebookLogin();
            editEmail = (EditText) findViewById(R.id.editEmail);
            editPassword = (EditText) findViewById(R.id.editPassword);
            btnSignIn = (Button) findViewById(R.id.btnSignIn);
            txtWarning = (TextView)findViewById(R.id.txtWarnign);
            btnSignIn.setOnClickListener(this);

            Log.w("CustID","User is not signed in "+custID);
        }
        else
        {
            Intent intent = new Intent(this,MainMenuActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void initializeFacebookLogin()
    {
        mCallBackManager = CallbackManager.Factory.create();
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

               if(currentProfile != null)
               {
                   Log.w("ProfileTracker","Profile is not null");
                   String lastName = currentProfile.getLastName() != null ? currentProfile.getLastName() : "Last name is empty";
                   String fristName = currentProfile.getFirstName() != null ? currentProfile.getFirstName() : "first name is empty";
                   String name = currentProfile.getName() != null ? currentProfile.getName() : "name is empty";
                   String id = currentProfile.getId();

                   Log.w("Profile",lastName);
                   Log.w("Profile",fristName);
                   Log.w("Profile",name);
                   Log.w("ProfielID",id);

               }
               else
               {
                   Log.w("ProfileTracker","Profile is null");
               }



            }
        };
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                try {
                    Log.w("AccessToken", currentAccessToken.getToken());
                    accessToken = currentAccessToken;
                    fetchUserFacebookData(currentAccessToken);
                }
                catch (NullPointerException ex)
                {
                    Log.w("AccessToken Null","Access Token is null");
                }

            }
        };
        profileTracker.startTracking();
        //(int cust_id, String name, String email, String cell, String password, String surname, String gender, String dateofbirth,String loginType)
        facebookLogginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookLogginButton.setReadPermissions(Arrays.asList( new String[]{"email","public_profile","user_birthday"}));
        facebookLogginButton.registerCallback(mCallBackManager,facebookCallback);

    }

    private void fetchUserFacebookData(AccessToken token)
    {
        Log.w("fetchUserFBData","Fetching User Facebook Data");
        GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback = new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                if(object != null)
                {

                  //  String lastName = currentProfile.getLastName() != null ? currentProfile.getLastName() : "Last name is empty";
                    //{"id":"1528247663860526","gender":"male","email":"drjack.moitsheki@gmail.com"}
                    try {
                        Log.w("Object",object.toString());
                        Log.w("InstanceOF",object.get("picture").getClass().getName());
                        propicUrl = object.has("picture") ? object.getJSONObject("picture").getJSONObject("data").getString("url"): null;
                        Log.w("PropicUrl",propicUrl);
                        User fbUser = new User();
                        fbUser.setCustID(object.getLong("id"));
                        fbUser.setName(object.getString("name"));
                        fbUser.setEmail(object.has("email")? object.getString("email") : "No Data");
                        fbUser.setGender(object.has("gender")? object.getString("gender") : "Data");
                        fbUser.setDateofbirth(object.has("birthday")? object.getString("birthday") : "No Data");
                        fbUser.setRole("No Data");
                        fbUser.setPassword("No Data");
                        fbUser.setSurname("No Data");
                        fbUser.setCell("0000000000");
                        fbUser.setLoginType(User.LOGIN_TYPE_FACEBOOK);
                        Log.w("User",fbUser.toString());
                        MenuClientServerComm c = new MenuClientServerComm();
                        c.setContext(thisInstance);
                        pd = new ProgressDialog(thisInstance);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        c.getSignIn(fbUser,selectedShop.getShopID());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.w("Object","Ojbect is null");

                }

            }
        };

        GraphRequest request = GraphRequest.newMeRequest(token,graphJSONObjectCallback);
        Bundle params = new Bundle();
        params.putString("fields","email,gender,name,birthday,picture.width(100)");
        request.setParameters(params);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACCOUNT_PICKER_REQ_ID)
        {
            if(data != null && data.getExtras() != null)
            {
                String accName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                if(accName != null)
                {
                    SharedPreferences.Editor editor = spSettings.edit();
                    setAccountName(accName);
                    editor.putString(ACCOUNT_NAME,accName);
                    editor.apply();
                    Log.w("onActivityResults","User is authorised");
                }
            }
        }
        mCallBackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void getUser(User user)
    {
        if(user.getCustID() != -1) {
            SharedPreferences.Editor editor = logingSession.edit();
            editor.putLong(User.CUST_ID_COL, user.getCustID());
            editor.putString(User.EMAIL_COL, user.getEmail());
            editor.putString(User.NAME_COL, user.getName());
            editor.putString(User.SURNAME_COL, user.getSurname());
            editor.putString(User.CELL_COL, user.getCell());
            editor.putString(User.DATEOFBIRTH_COL, user.getDateofbirth().toString());
            editor.putString(User.PASSWORD_COL, user.getPassword());
            editor.putString(User.GENDER_COL, user.getGender());
            editor.putString(User.LOGIN_TYPE_COL,user.getLoginType());
            editor.commit();
            Intent intent = new Intent(this,MainMenuActivity.class);
            if(user.getLoginType().equals(User.LOGIN_TYPE_FACEBOOK)) {
                intent.putExtra("AccessToken", accessToken);
                intent.putExtra("ProfilePicture",propicUrl);
                Log.w("myFacebookSign","propic and access token set");
            }


            startActivity(intent);
            finish();
        }
        else
        {
            editPassword.setText("");
            editEmail.setText("");
            editEmail.setFocusable(true);
            txtWarning.setText(getResources().getString(R.string.login_error_message));


        }

    }

    public static boolean isValidEmail(CharSequence email)
    {
        boolean isValid = false;
        if(email == null)
        {
            isValid = false;
        }
        else
        {
            isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return isValid;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            profileTracker.stopTracking();
        }
        catch(NullPointerException ex)
        {
            Log.w("ProfileTracker","Profile Tracker is empty");
        }
    }

    @Override
    public void onClick(View view) {

        if(view == findViewById(R.id.btnSignIn)) {
            boolean isValid = isValidEmail(editEmail.getText());
            if (isValid) {

            }

            User user = new User();
            user.setEmail(editEmail.getText().toString());
            user.setPassword(editPassword.getText().toString());
            user.setLoginType(User.LOGIN_TYPE_APP);
            try {
                csm.getSignIn(user,selectedShop.getShopID());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            {
                txtWarning.setText(getResources().getString(R.string.invalid_email));
            }


        }
    }
}
