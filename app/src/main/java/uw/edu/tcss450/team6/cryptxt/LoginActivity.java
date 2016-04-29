package uw.edu.tcss450.team6.cryptxt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    private static final String REGISTER_URL =
            "https://staff.washington.edu/jth7985/CrypTxt/addUser.php";
    private static final String VERIFY_USER_URL =
            "https://staff.washington.edu/jth7985/CrypTxt/verifyUser.php";
    private EditText userIdText;
    private EditText pwdText;
    private SharedPreferences mSharedPreferences;
    private boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loggedIn = false;
        Intent intent = getIntent();
        userIdText = (EditText) findViewById(R.id.user_id);
        pwdText = (EditText) findViewById(R.id.pwd);
        LinearLayout layout = (LinearLayout) findViewById(R.id.loginView);

        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }


    }

    public void attemptRegister(View v) {
        String userId = userIdText.getText().toString();
        String pwd = pwdText.getText().toString();
        if (TextUtils.isEmpty(userId))  {
            Toast.makeText(v.getContext(), "Enter user name"
                    , Toast.LENGTH_SHORT)
                    .show();
            userIdText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pwd))  {
            Toast.makeText(v.getContext(), "Enter password"
                    , Toast.LENGTH_SHORT)
                    .show();
            pwdText.requestFocus();
            return;
        }
        register(userId, pwd, v);
    }

    public void attemptLogin(View v) {
        String userId = userIdText.getText().toString();
        String pwd = pwdText.getText().toString();
        if (TextUtils.isEmpty(userId))  {
            Toast.makeText(v.getContext(), "Enter user name"
                    , Toast.LENGTH_SHORT)
                    .show();
            userIdText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pwd))  {
            Toast.makeText(v.getContext(), "Enter password"
                    , Toast.LENGTH_SHORT)
                    .show();
            pwdText.requestFocus();
            return;
        }
        login(userId, pwd, v);
    }

    public void login(String userId, String pwd, View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Check if the login and password are valid
            //new LoginTask().execute(url);
            try {
                String url = buildVerifyUserURL(view);
                VerifyUserTask task = new VerifyUserTask();
                task.execute(new String[]{url.toString()});
                if(loggedIn == true) {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                            openFileOutput(getString(R.string.LOGIN_FILE)
                                    , Context.MODE_PRIVATE));
                    outputStreamWriter.write(userId);
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "No network connection available. Cannot authenticate user",
                    Toast.LENGTH_SHORT) .show();
            return;
        }

    }

    private String buildVerifyUserURL(View v) {

        StringBuilder sb = new StringBuilder(VERIFY_USER_URL);

        try {
            sb.append("?user=");
            sb.append(URLEncoder.encode(userIdText.getText().toString(), "UTF-8"));

            sb.append("&pwd=");
            sb.append(URLEncoder.encode(pwdText.getText().toString(), "UTF-8"));

            Log.i("LoginActivity-Verify", sb.toString());

        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something's wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    private class VerifyUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to login, Reason: "
                            + e.getMessage();
                    loggedIn = false;
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Logged In!"
                            , Toast.LENGTH_LONG)
                            .show();
                    loggedIn = true;
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .commit();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Failed to login: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                    loggedIn = false;
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something's wrong with the data " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
                loggedIn = false;
            }
        }
    }

    public void register(String userId, String pwd, View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Check if the login and password are valid
            //new LoginTask().execute(url);
            try {
                String url = buildRegisterURL(view);
                AddUserTask task = new AddUserTask();
                task.execute(new String[]{url.toString()});
                if(loggedIn == true) {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                            openFileOutput(getString(R.string.LOGIN_FILE)
                                    , Context.MODE_PRIVATE));
                    outputStreamWriter.write(userId);
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "No network connection available. Cannot authenticate user",
                    Toast.LENGTH_SHORT) .show();
            return;
        }
        if (loggedIn == true) {
            mSharedPreferences
                    .edit()
                    .putBoolean(getString(R.string.LOGGEDIN), true)
                    .commit();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }



    private String buildRegisterURL(View v) {

        StringBuilder sb = new StringBuilder(REGISTER_URL);

        try {
            sb.append("?user=");
            sb.append(URLEncoder.encode(userIdText.getText().toString(), "UTF-8"));

            sb.append("&pwd=");
            sb.append(URLEncoder.encode(pwdText.getText().toString(), "UTF-8"));

            Log.i("LoginActivity-AddUser", sb.toString());

        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something's wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    private class AddUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add user, Reason: "
                            + e.getMessage();
                    loggedIn = false;
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Registered!"
                            , Toast.LENGTH_LONG)
                            .show();
                    loggedIn = true;
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .commit();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to register: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                    loggedIn = false;
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something's wrong with the data " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
                loggedIn = false;
            }
        }
    }
}
