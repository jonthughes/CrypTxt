package uw.edu.tcss450.team6.cryptxt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import uw.edu.tcss450.team6.cryptxt.model.Msg;

/**
 * The Receive Activity loads a message that was sent to the current user.  It enables them to
 * decrypted it and reply if necessary.
 *
 * @author Jonathan Hughes
 * @date 28 April 2016
 */
public class ReceiveActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String MSG_ITEM_SELECTED = "mis";

    private Cipher cipher;
    private int cipherNum;
    private View replyButton;

    private TextView receiveFrom;
    private TextView receiveDate;
    private TextView receiveMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cipher = new Cipher();
        cipherNum = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        Spinner spinner = (Spinner) findViewById(R.id.receiveSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cipher_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        replyButton = findViewById(R.id.replyButton);
        replyButton.setEnabled(false);

        receiveFrom = (TextView) findViewById(R.id.receiveFrom);
        receiveDate = (TextView) findViewById(R.id.receiveDate);
        receiveMessage = (TextView) findViewById(R.id.receiveMessage);
    }

    /**
     * @param msg The message to load on the screen.
     */
    public void updateView(Msg msg) {
        if (msg != null) {
            receiveFrom.setText(msg.getSender());
            receiveDate.setText(msg.getDtg());
            receiveMessage.setText(msg.getMsg());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getIntent().getExtras();
        if (args != null) {
            // Set article based on argument passed in
            updateView((Msg) args.getSerializable(MSG_ITEM_SELECTED));
        }
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
     * Decrypts the message with the cipher and key chosen by the user.
     */
    public void decrypt(View view) {
        TextView input_text = (TextView) findViewById(R.id.receiveMessage);
        String ciphertext = input_text.getText().toString();
        EditText key_EditText = (EditText) findViewById(R.id.receiveKey);
        String key = key_EditText.getText().toString();
        String plaintext = "";
        switch(cipherNum) {
            case 0: //shift
                plaintext = cipher.caesarShiftUndo(ciphertext, key);
                break;
            case 1: //vigenere
                plaintext = cipher.vigenereUndo(ciphertext, key);
                break;
            case 2: //substitution
                plaintext = cipher.substitutionUndo(ciphertext, key);
                break;
            case 3: //permutation
                plaintext = cipher.permutationUndo(ciphertext, key);
                break;
            case 4: //advanced
                plaintext = cipher.advancedUndo(ciphertext, key);
                break;
            default:
                plaintext = "Invalid cipher";
                break;
        }
        TextView t = (TextView)findViewById(R.id.receiveDecryptedMessage);
        t.setText(plaintext);
        replyButton.setEnabled(true);
    }

    /**
     * Start a Send Activity with the previous sender as the receiver.
     */
    public void reply(View view) {
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);
        //@TODO go to new message with To: filled out, use same key
    }
}
