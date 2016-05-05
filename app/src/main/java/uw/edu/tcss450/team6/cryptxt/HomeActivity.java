package uw.edu.tcss450.team6.cryptxt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * The Home Activity is the first page after login.  It has buttons to start the inbox, contacts
 * page, start new message, share screen, help menu, and log out.
 *
 * @author Jonathan Hughes
 * @date 28 April 2016
 */
public class HomeActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /**
     * Starts the Inbox Activity.
     */
    public void startInbox(View view) {
        Intent intent = new Intent(HomeActivity.this, InboxActivity.class);
        startActivity(intent);
    }

    /**
     * Starts the Contacts Activity.
     */
    public void startContacts(View view) {
        Intent intent = new Intent(HomeActivity.this, ContactsActivity.class);
        startActivity(intent);
    }

    /**
     * Starts the Send Activity.
     */
    public void startSend(View view) {
        Intent intent = new Intent(HomeActivity.this, SendActivity.class);
        startActivity(intent);
    }

    /**
     * Starts the Share Activity.
     */
    public void startShare(View view) {
        Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
        startActivity(intent);
    }

    /**
     * Starts the Help Activity.
     */
    public void startHelp(View view) {
        Intent intent = new Intent(HomeActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    /**
     * Logs out the current user.
     */
    public void logout(View view) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                .commit();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
