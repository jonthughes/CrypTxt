package uw.edu.tcss450.team6.cryptxt;

import org.junit.Test;

import java.util.ArrayList;

import uw.edu.tcss450.team6.cryptxt.model.Msg;
import static org.junit.Assert.*;

/**
 * This tests the Msg class.
 *
 * @author Jonathan Hughes
 * @date 27 May 2016
 */
public class MsgTest {

    @Test
    public void testConstructor() {
        Msg msg = new Msg("2","user2","user1","LIPPSASVPH","2016-04-27 13:00:00","0","0");
        assertNotNull(msg);
    }

    @Test
    public void testParseMsgJSON() {
        String msgJSON = "[{\"mid\":\"2\",\"sender\":\"user2\",\"receiver\":\"user1\",\"msg\":\"LIPPSASVPH\",\"dtg\":\"2016-04-27 13:00:00\",\"cipher\":\"0\",\"cipherkey\":\"0\"]";
        String message =  Msg.parseMsgJSON(msgJSON
                , new ArrayList<Msg>());
        assertTrue("JSON With Valid String", message == null);
    }
}
