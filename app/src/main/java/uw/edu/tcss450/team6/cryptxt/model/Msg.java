package uw.edu.tcss450.team6.cryptxt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * The Msg class holds data for a single message, so that it can be displayed in the Inbox and
 * additional information is shown on the ReceiveActivity when a msg is clicked on in the Inbox.
 *
 * @author Jonathan Hughes
 * @date 28 April 2016
 */
public class Msg implements Serializable {

    public static final String MID = "mid", SENDER = "sender"
            , RECEIVER = "receiver", MSG = "msg", DTG = "dtg"
            , CIPHER = "cipher", CIPHERKEY = "cipherkey";

    private String mid;
    private String sender;
    private String receiver;
    private String msg;
    private String dtg;
    private String cipher;
    private String cipherkey;

    public Msg(String mid, String sender, String receiver, String msg, String dtg, String cipher,
               String cipherkey) {
        this.mid = mid;
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.dtg = dtg;
        this.cipher = cipher;
        this.cipherkey = cipherkey;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns msg list if success.
     * @param msgJSON
     * @return reason or null if successful.
     */
    public static String parseMsgJSON(String msgJSON, List<Msg> msgList) {
        String reason = null;
        if (msgJSON != null) {
            try {
                JSONArray arr = new JSONArray(msgJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Msg msg = new Msg(obj.getString(Msg.MID), obj.getString(Msg.SENDER)
                            , obj.getString(Msg.RECEIVER), obj.getString(Msg.MSG)
                            , obj.getString(Msg.DTG), obj.getString(Msg.CIPHER), obj.getString(Msg.CIPHERKEY));
                    msgList.add(msg);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }


    public String getCipher() {
        return cipher;
    }

    public String getMid() {
        return mid;
    }

    public String getCipherkey() {
        return cipherkey;
    }

    public String getDtg() {
        return dtg;
    }

    public String getMsg() {
        return msg;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public void setCipherkey(String cipherkey) {
        this.cipherkey = cipherkey;
    }

    public void setDtg(String dtg) {
        this.dtg = dtg;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
