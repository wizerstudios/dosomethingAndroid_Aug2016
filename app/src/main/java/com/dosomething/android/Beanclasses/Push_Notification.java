package com.dosomething.android.Beanclasses;

/**
 * Created by ${Madhan} on 6/30/2016.
 */
public class Push_Notification {

String conversationid,senderId,push_type;

    boolean GCM_chat;

    public Push_Notification(String conversationid, String senderId, String push_type, boolean GCM_chat) {
        this.conversationid = conversationid;
        this.senderId = senderId;
        this.push_type = push_type;
        this.GCM_chat = GCM_chat;
    }

    public String getConversationid() {
        return conversationid;
    }

    public void setConversationid(String conversationid) {
        this.conversationid = conversationid;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getPush_type() {
        return push_type;
    }

    public void setPush_type(String push_type) {
        this.push_type = push_type;
    }

    public boolean isGCM_chat() {
        return GCM_chat;
    }

    public void setGCM_chat(boolean GCM_chat) {
        this.GCM_chat = GCM_chat;
    }
}
