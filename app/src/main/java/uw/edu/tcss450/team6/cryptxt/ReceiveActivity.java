package uw.edu.tcss450.team6.cryptxt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ReceiveActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Cipher cipher;
    private int cipherNum;
    private View replyButton;

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
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        cipherNum = pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void decrypt(View view) {
        EditText input_EditText = (EditText) findViewById(R.id.receiveMessage);
        String ciphertext = input_EditText.getText().toString();
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

    public void reply(View view) {
        //@TODO go to new message with To: filled out, use same key
    }
}
