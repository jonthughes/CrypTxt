package uw.edu.tcss450.team6.cryptxt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The Share Activity shows a menu to share info about CrypTxt via email or text.
 *
 * @author David Lee
 * @date 30 April 2016
 */
public class ShareActivity extends AppCompatActivity {

    public static final String DOWNLOAD_LINK = "https://drive.google.com/file/d/0B3sIjP86ASGgN3dDXy15UnppaEU/view?usp=sharing";
    Button emailSend;
    EditText emailTo;
    EditText emailSubject;
    EditText emailMessage;

    Button smsSend;
    EditText smsTo;
    EditText smsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        emailSend = (Button) findViewById(R.id.email_button);
        emailTo = (EditText) findViewById(R.id.email_recipient);
        emailSubject = (EditText) findViewById(R.id.recipient_subject);
        emailMessage = (EditText) findViewById(R.id.body);

        smsSend = (Button) findViewById(R.id.sms_button);
        smsTo = (EditText) findViewById(R.id.sms_recipient);
        smsMessage = (EditText) findViewById(R.id.sms_body);

        emailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String to = emailTo.getText().toString();
                String subject = emailSubject.getText().toString();
                String message = emailMessage.getText().toString();
                message += DOWNLOAD_LINK;

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });

        smsSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNo = smsTo.getText().toString();
                String sms = smsMessage.getText().toString();
                sms += DOWNLOAD_LINK;

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
