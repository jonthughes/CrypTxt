package uw.edu.tcss450.team6.cryptxt;

import org.junit.Test;

import java.util.ArrayList;

import uw.edu.tcss450.team6.cryptxt.model.Contact;
import static org.junit.Assert.*;

/**
 * This tests the Contact class.
 *
 * @author Jonathan Hughes
 * @date 27 May 2016
 */
public class ContactTest {

    @Test
    public void testConstructor() {
        Contact contact = new Contact("2","user2","user1");
        assertNotNull(contact);
    }

    @Test
    public void testParseContactJSON() {
        String contactJSON = "[{\"cid\":\"2\",\"user\":\"user2\",\"contact\":\"user1\"]";
        String contact =  Contact.parseContactJSON(contactJSON
                , new ArrayList<Contact>());
        assertTrue("JSON With Valid String", contact == null);
    }
}
