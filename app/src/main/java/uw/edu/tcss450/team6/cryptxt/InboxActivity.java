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

import uw.edu.tcss450.team6.cryptxt.model.Msg;

/**
 * Modified code from http://androidexample.com/Create_A_Simple_Listview_-_Android_Example/
 *                    index.php?view=article_discription&aid=65&aaid=90
 */
public class InboxActivity extends AppCompatActivity implements MsgListFragment.OnListFragmentInteractionListener {

    ListView listView ;

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
//        // Get ListView object from xml
//        listView = (ListView) findViewById(R.id.inboxListView);
//
//        // Defined Array values to show in ListView
//        String[] msgs = new String[] { "Message 1",
//                "Message 2",
//                "Message 3",
//                "Message 4",
//                "Message 5",
//                "Message 6",
//                "Message 7",
//                "Message 8",
//        };
//
//        // Define a new Adapter
//        // First parameter - Context
//        // Second parameter - Layout for the row
//        // Third parameter - ID of the TextView to which the data is written
//        // Forth - the Array of data
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, msgs);
//
//
//        // Assign adapter to ListView
//        listView.setAdapter(adapter);
//
//        // ListView Item Click Listener
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                // ListView Clicked item index
//                int itemPosition     = position;
//
//                // ListView Clicked item value
//                String  itemValue    = (String) listView.getItemAtPosition(position);
//
//                Intent intent = new Intent(InboxActivity.this, ReceiveActivity.class);
//                startActivity(intent);
//            }
//
//        });
    }

    @Override
    public void onListFragmentInteraction(Msg item) { }

}