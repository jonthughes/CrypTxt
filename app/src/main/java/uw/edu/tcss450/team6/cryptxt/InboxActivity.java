package uw.edu.tcss450.team6.cryptxt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import uw.edu.tcss450.team6.cryptxt.model.Msg;

/**
 * The Inbox Activity displays messages for the user in a list.
 *
 * @author Jonathan Hughes
 * @date 28 April 2016
 *
 * Modified code from http://androidexample.com/Create_A_Simple_Listview_-_Android_Example/
 *                    index.php?view=article_discription&aid=65&aaid=90
 */
public class InboxActivity extends AppCompatActivity implements MsgListFragment.OnListFragmentInteractionListener {

    public static final String MSG_ITEM_SELECTED = "mis";
    ListView listView ;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            MsgListFragment msgListFragment = new MsgListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, msgListFragment)
                    .commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onListFragmentInteraction(Msg item) {
        Intent intent = new Intent(InboxActivity.this, ReceiveActivity.class);
        intent.putExtra(InboxActivity.MSG_ITEM_SELECTED, item);
        startActivity(intent);
    }

}