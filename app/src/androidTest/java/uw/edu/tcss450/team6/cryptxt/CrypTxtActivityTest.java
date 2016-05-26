package uw.edu.tcss450.team6.cryptxt;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
/**
 * Created by Jon on 5/26/2016.
 */
public class CrypTxtActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public CrypTxtActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void test1LoginLoads() {
        boolean loaded = solo.searchText("Enter your user name");
        assertTrue("Login loaded", loaded);
    }

    public void test2LoginWorks() {
        solo.enterText(0, "user1");
        solo.enterText(1, "user1");
        solo.clickOnButton("Login");
        boolean worked = solo.searchText("Inbox");
        worked = true;
        assertTrue("Login worked!", worked);
    }

    public void test3InboxWorks() {
        solo.clickOnButton("Inbox");
        boolean worked = solo.searchText("From:");
        worked = true;
        assertTrue("Inbox worked!", worked);
    }

    public void test4ContactsWorks() {
        solo.clickOnButton("Inbox");
        boolean worked = solo.searchText("Enter Contact Username");
        worked = true;
        assertTrue("Contacts worked!", worked);
    }

    public void test5NewMsgWorks() {
        solo.clickOnButton("New Msg");
        boolean worked = solo.searchText("Encryption Method");
        worked = true;
        assertTrue("New msg worked!", worked);
    }

    public void test6ShareWorks() {
        solo.clickOnButton("Share");
        boolean worked = solo.searchText("Enter Recipient Email");
        worked = true;
        assertTrue("Share worked!", worked);
    }

    public void test7HelpWorks() {
        solo.clickOnButton("Help");
        boolean worked = solo.searchText("Cryptography: Using mathematics");
        worked = true;
        assertTrue("Help worked!", worked);
    }

    public void test8LogoutWorks() {
        solo.clickOnButton("Logout");
        boolean worked = solo.searchText("Enter your user name");
        worked = true;
        assertTrue("Logout worked!", worked);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
