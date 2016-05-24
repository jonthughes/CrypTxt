package uw.edu.tcss450.team6.cryptxt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import uw.edu.tcss450.team6.cryptxt.model.Contact;

/**
 * The Contacts Activity shows a list of the user's contacts or can add a new contact.
 *
 * @author David Lee
 * @date 30 April 2016
 */
public class ContactsActivity extends AppCompatActivity implements ContactListFragment.OnListFragmentInteractionListener {
    private static final String CONTACT_ADD_URL = "https://staff.washington.edu/jth7985/CrypTxt/addContact.php";
    public static final String CONTACT_ITEM_SELECTED = "cis";
    private static final String userTempValue = "user1";
    ListView listView;
    private View addContactButton;
    private EditText newContact;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.Contacts_Title);
        setContentView(R.layout.activity_contacts);
        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            ContactListFragment contactListFragment = new ContactListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contactListFragment)
                    .commit();
        }
        addContactButton = findViewById(R.id.addcontactbutton);
        addContactButton.setEnabled(true);
        newContact = (EditText) findViewById(R.id.newContact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onListFragmentInteraction(Contact item) {
        Intent intent = new Intent(ContactsActivity.this, SendActivity.class);
        intent.putExtra(ContactsActivity.CONTACT_ITEM_SELECTED, item);
        startActivity(intent);
    }

    /**
     * Executes the add contact task upon button press.
     */
    public void addContact(View view) {
        String url = buildContactURL(view);
        AddContactTask task = new AddContactTask();
        task.execute(new String[]{url.toString()});
    }

    /**
     * Builds a URL string to interact with the PHP code associated with adding a contact.
     * @return The built URL
     */
    private String buildContactURL(View v) {
        StringBuilder sb = new StringBuilder(CONTACT_ADD_URL);
        try {
            sb.append("?user=");
            sb.append(URLEncoder.encode(userTempValue, "UTF-8"));

            sb.append("&contact=");
            sb.append(URLEncoder.encode(newContact.getText().toString(), "UTF-8"));

            Log.i("MsgSend", sb.toString());

        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * Executes the PHP code URL associated with adding a contact.
     */
    private class AddContactTask extends AsyncTask<String, Void, String> {
        /**
         * {@inheritDoc}
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * {@inheritDoc}
         */
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
                    response = "Unable to add contact, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "New contact added!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add contact: "
                            + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
