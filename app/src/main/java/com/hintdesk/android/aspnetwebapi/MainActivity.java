package com.hintdesk.android.aspnetwebapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    SampleApi api = SampleApiFactory.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @ViewById
    EditText editTextEmail;

    @ViewById
    EditText editTextPassword;

    @AfterViews
    protected void afterViews() {
        Long currentTimeStampe = (System.currentTimeMillis()/1000);
        String email = currentTimeStampe.toString()+ "@mail.com";
        editTextEmail.setText(email);
        editTextPassword.setText("1@aA1@aA1@aA");
    }

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
            TokenModel tokenModel = null;
            try {
                tokenModel = api.Login("password", models[0].getEmail(),models[0].getPassword()).execute().body();
                if (tokenModel != null && tokenModel.AccessToken != null) {
                    return new AuthenticationResult(true, tokenModel.AccessToken, null);
                } else
                    return new AuthenticationResult(false, null, "Login failed");
            } catch (IOException e) {
                e.printStackTrace();
                return new AuthenticationResult(false, null, "Login failed\r\n" + e.getMessage());
            }
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
            RegisterBindingModel model = models[0];
            String result = null;
            try {
                Response<String> response = api.Register(model).execute();
                result = response.body();
                if (StringUtil.isNullOrWhitespace(result)) {
                    TokenModel tokenModel = api.Login("password", models[0].getEmail(),models[0].getPassword()).execute().body();
                    if (tokenModel != null && tokenModel.AccessToken != null) {
                        return new AuthenticationResult(true, tokenModel.AccessToken, null);
                    } else
                        return new AuthenticationResult(false, null, "Get Token failed");

                } else
                    return new AuthenticationResult(false, null, result);
            } catch (Exception e) {
                StringWriter exception = new StringWriter();
                e.printStackTrace(new PrintWriter(exception));
                return new AuthenticationResult(false, null, "Get Token failed\r\n"+exception);
            }



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
