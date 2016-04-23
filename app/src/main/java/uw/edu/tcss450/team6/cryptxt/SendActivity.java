package uw.edu.tcss450.team6.cryptxt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SendActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Cipher cipher;
    private int cipherNum;
    private View sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cipher = new Cipher();
        cipherNum = 0;
        super.onCreate(savedInstanceState);
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
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        cipherNum = pos;

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void encrypt(View view) {
        EditText input_EditText = (EditText) findViewById(R.id.plaintext);
        String plaintext = input_EditText.getText().toString();
        EditText key_EditText = (EditText) findViewById(R.id.sendKey);
        String key = key_EditText.getText().toString();
        String ciphertext = "";
        switch(cipherNum) {
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
        TextView t = (TextView)findViewById(R.id.sendMessage);
        t.setText(ciphertext);
        sendButton.setEnabled(true);
    }

    public void send(View view) {
        //@TODO send message to server
    }
}
