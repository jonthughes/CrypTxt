package uw.edu.tcss450.team6.cryptxt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startInbox(View view) {
        Intent intent = new Intent(HomeActivity.this, InboxActivity.class);
        startActivity(intent);
    }

    public void startContacts(View view) {
        Intent intent = new Intent(HomeActivity.this, ContactsActivity.class);
        startActivity(intent);
    }

    public void startSend(View view) {
        Intent intent = new Intent(HomeActivity.this, SendActivity.class);
        startActivity(intent);
    }

    public void startShare(View view) {
        Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
        startActivity(intent);
    }

    public void startHelp(View view) {
        Intent intent = new Intent(HomeActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                .commit();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
