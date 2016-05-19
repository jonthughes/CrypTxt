package uw.edu.tcss450.team6.cryptxt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * The Send Activity creates a message to send to a chosen user.  It enables them to
 * encrypted it with a chosen cipher and key before sending.
 *
 * @author Jonathan Hughes
 * @date 28 April 2016
 */
public class SendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String cipherkey = "0";
    private static final String sender = "user1";
    private static final String MSG_ADD_URL = "https://staff.washington.edu/jth7985/CrypTxt/addMsg.php";

    private Cipher cipher;
    private int cipherNum;
    private View sendButton;

    EditText input_EditText;
    EditText key_EditText;
    EditText recipient;
    TextView msg;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cipher = new Cipher();
        cipherNum = 0;
        super.onCreate(savedInstanceState);

        setTitle(R.string.Send_Title);
        setContentView(R.layout.activity_send);
        Spinner spinner = (Spinner) findViewById(R.id.sendSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cipher_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setEnabled(false);
        input_EditText = (EditText) findViewById(R.id.plaintext);
        key_EditText = (EditText) findViewById(R.id.sendKey);
        recipient = (EditText) findViewById(R.id.sendRecipient);
        msg = (TextView) findViewById(R.id.sendMessage);
    }

    /**
     * Changes the chosen cipher, selected by the user with the spinner.
     */
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        cipherNum = pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    /**
     * Encrypts the message with the cipher and key chosen by the user.
     */
    public void encrypt(View view) {
        String plaintext = input_EditText.getText().toString();
        String key = key_EditText.getText().toString();
        String ciphertext = "";
        switch (cipherNum) {
            case 0: //shift
                ciphertext = cipher.caesarShift(plaintext, key);
                break;
            case 1: //vigenere
                ciphertext = cipher.vigenere(plaintext, key);
                break;
            case 2: //substitution
                ciphertext = cipher.substitution(plaintext, key);
                break;
            case 3: //permutation
                ciphertext = cipher.permutation(plaintext, key);
                break;
            case 4: //advanced
                ciphertext = cipher.advanced(plaintext, key);
                break;
            default:
                ciphertext = "Invalid cipher";
                break;
        }
        msg.setText(ciphertext);
        sendButton.setEnabled(true);
    }

    /**
     * Executes the send message task upon button press.
     */
    public void send(View view) {
        String url = buildMsgURL(view);
        AddMsgTask task = new AddMsgTask();
        task.execute(new String[]{url.toString()});
    }

    /**
     * Builds a URL string to interact with the PHP code associated with sending a message.
     * @return The built URL
     */
    private String buildMsgURL(View v) {
        StringBuilder sb = new StringBuilder(MSG_ADD_URL);
        try {
            sb.append("?sender=");
            sb.append(URLEncoder.encode(sender, "UTF-8"));

            String receiver = recipient.getText().toString();
            sb.append("&receiver=");
            sb.append(URLEncoder.encode(receiver, "UTF-8"));

            String message = msg.getText().toString();
            sb.append("&msg=");
            sb.append(URLEncoder.encode(message, "UTF-8"));

            sb.append("&cipher=");
            sb.append(URLEncoder.encode("" + cipherNum, "UTF-8"));

            sb.append("&cipherkey=");
            sb.append(URLEncoder.encode(cipherkey, "UTF-8"));

            Log.i("MsgSend", sb.toString());

        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * Executes the PHP code URL associated with sending a message.
     */
    private class AddMsgTask extends AsyncTask<String, Void, String> {
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
                    response = "Unable to add msg, Reason: "
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
                    Toast.makeText(getApplicationContext(), "Msg sent!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to send: "
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
