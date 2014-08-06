package com.example.Introduction_to_ASP_NET_Identity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import com.hintdesk.core.activities.AlertMessageBox;
import com.hintdesk.core.activities.AlertMessageBoxOkClickCallback;
import com.hintdesk.core.utils.JSONHttpClient;
import com.hintdesk.core.utils.StringUtil;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.main)
public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    @ViewById
    EditText editTextEmail;

    @ViewById
    EditText editTextPassword;

    @Click(R.id.buttonRegister)
    void Register() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (!StringUtil.isNullOrWhitespace(email) && !StringUtil.isNullOrWhitespace(email)) {
            if (StringUtil.isEmailAddress(email)) {
                if (StringUtil.isMatch("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*(_|[^\\w])).{6,}$", password)) {
                    RegisterBindingModel model = new RegisterBindingModel(email, password, password);
                    new RegisterAsyncTask().execute(model);
                } else
                    AlertMessageBox.Show(MainActivity.this, "Error", "Passwords must have at least one non letter and digit character. Passwords must have at least one lowercase ('a'-'z'). Passwords must have at least one uppercase ('A'-'Z').", AlertMessageBox.AlertMessageBoxIcon.Error);

            } else
                AlertMessageBox.Show(MainActivity.this, "Error", "A valid email address is required", AlertMessageBox.AlertMessageBoxIcon.Error);
        } else
            AlertMessageBox.Show(MainActivity.this, "Error", "Email and password are required", AlertMessageBox.AlertMessageBoxIcon.Error);
    }

    @Click(R.id.buttonLogIn)
    void LogIn() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (!StringUtil.isNullOrWhitespace(email) && !StringUtil.isNullOrWhitespace(email)) {
            RegisterBindingModel model = new RegisterBindingModel(email, password, password);
            new LoginAsyncTask().execute(model);
        } else
            AlertMessageBox.Show(MainActivity.this, "Error", "Email and password are required", AlertMessageBox.AlertMessageBoxIcon.Error);
    }

    class LoginAsyncTask extends AsyncTask<RegisterBindingModel, String, AuthenticationResult> {
        protected AuthenticationResult doInBackground(RegisterBindingModel... models) {
            JSONHttpClient httpClient = new JSONHttpClient();
            String url = ServiceUrl.TOKEN;
            String object = String.format("grant_type=password&username=%s&password=%s", models[0].getEmail(), models[0].getPassword());
            TokenModel tokenModel = httpClient.PostStringAsFormUrlEncodedContent(ServiceUrl.TOKEN, object, TokenModel.class);
            if (tokenModel != null && tokenModel.AccessToken != null) {
                return new AuthenticationResult(true, tokenModel.AccessToken, null);
            } else
                return new AuthenticationResult(false, null, "Login failed");
        }

        @Override
        protected void onPostExecute(final AuthenticationResult result) {

            if (!result.isSuccessful())
                AlertMessageBox.Show(MainActivity.this, "Error", result.getError(), AlertMessageBox.AlertMessageBoxIcon.Error);
            else {
                AlertMessageBox.Show(MainActivity.this, "Successful", "Login successfully. You're going to be forwarded to protected resource. ", AlertMessageBox.AlertMessageBoxIcon.Info, new AlertMessageBoxOkClickCallback() {
                    @Override
                    public void run() {
                        ValueActivity_.intent(MainActivity.this).accessToken(result.getAccessToken()).start();
                    }
                });

            }


            super.onPostExecute(result);
        }
    }

    class RegisterAsyncTask extends AsyncTask<RegisterBindingModel, String, AuthenticationResult> {
        @Override
        protected AuthenticationResult doInBackground(RegisterBindingModel... models) {
            String url = ServiceUrl.REGISTER;
            RegisterBindingModel model = models[0];
            JSONHttpClient httpClient = new JSONHttpClient();
            String result = httpClient.PostObject(ServiceUrl.REGISTER, model);
            if (StringUtil.isNullOrWhitespace(result)) {

                url = ServiceUrl.TOKEN;
                String object = String.format("grant_type=password&username=%s&password=%s", model.getEmail(), model.getPassword());
                TokenModel tokenModel = httpClient.PostStringAsFormUrlEncodedContent(ServiceUrl.TOKEN, object, TokenModel.class);
                if (tokenModel != null && tokenModel.AccessToken != null) {
                    return new AuthenticationResult(true, tokenModel.AccessToken, null);
                } else
                    return new AuthenticationResult(false, null, "Get Token failed");

            } else
                return new AuthenticationResult(false, null, result);


        }

        @Override
        protected void onPostExecute(final AuthenticationResult result) {


            if (!result.isSuccessful())
                AlertMessageBox.Show(MainActivity.this, "Error", result.getError(), AlertMessageBox.AlertMessageBoxIcon.Error);
            else {
                AlertMessageBox.Show(MainActivity.this, "Successful", "Register successfully. You're going to be forwarded to protected resource. ", AlertMessageBox.AlertMessageBoxIcon.Info, new AlertMessageBoxOkClickCallback() {
                    @Override
                    public void run() {
                        ValueActivity_.intent(MainActivity.this).accessToken(result.getAccessToken()).start();
                    }
                });


                super.onPostExecute(result);
            }
        }
    }


}
