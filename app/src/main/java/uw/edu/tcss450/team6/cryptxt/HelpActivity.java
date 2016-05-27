package uw.edu.tcss450.team6.cryptxt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The Help Activity displays descriptions of how each encryption works and other related terms that
 * a user may be interested in.
 * @author Jonathan Hughes
 * @date 28 April 2016
 */
public class HelpActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.Help_Title);
        setContentView(R.layout.activity_help);
    }
}
