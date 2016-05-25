package uw.edu.tcss450.team6.cryptxt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by David on 5/19/16.
 */
public class Contact implements Serializable {

    public static final String CID = "cid", USER = "User", CONTACT = "Contact";

    private String cid;
    private String user;
    private String contact;

    public Contact(String cid, String user, String contact) {
        this.cid = cid;
        this.user = user;
        this.contact = contact;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param contactJSON
     * @return reason or null if successful.
     */
    public static String parseContactJSON(String contactJSON, List<Contact> contactList) {
        String reason = null;
        if (contactJSON != null) {
            try {
                JSONArray arr = new JSONArray(contactJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Contact contact = new Contact(obj.getString(Contact.CID), obj.getString(Contact.USER)
                            , obj.getString(Contact.CONTACT));
                    contactList.add(contact);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }



    public String getCid() { return cid; }

    public String getUser() { return user; }

    public String getContact() { return contact; }

    public void setCid(String cid) { this.cid = cid; }

    public void setUser(String user) { this.user = user; }

    public void setContact(String contact) { this.contact = contact; }

}
