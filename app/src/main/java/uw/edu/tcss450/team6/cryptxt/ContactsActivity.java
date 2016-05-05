package uw.edu.tcss450.team6.cryptxt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The Contacts Activity shows a list of the user's contacts or can add a new contact.
 *
 * @author David Lee
 * @date 30 April 2016
 */
public class ContactsActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }
}
