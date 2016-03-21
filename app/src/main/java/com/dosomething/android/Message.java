package com.dosomething.android;

/**
 * Message is a Custom Object to encapsulate message information/fields
 *
 * @author Adil Soomro
 */
public class Message {
    /**
     * The content of the message
     */
    String message,time,date;

    String user_email,name, image1,image2,image3,dosomething_sender_message,dosomething_receiver_message,type;
    int Mid,user_id;
    /**
     * boolean to determine, who is sender of this message
     */
    boolean isMine;
    /**
     * boolean to determine, whether the message is a status message or not.
     * it reflects the changes/updates about the sender is writing, have entered text etc
     */
    boolean isStatusMessage;

    /**
     * Constructor to make a Message object
     */
    public Message(String message,String date,String time, boolean isMine) {
        super();
        this.message = message;
        this.time=time;
        this.date=date;
        this.isMine = isMine;
        this.isStatusMessage = false;
    }

    /**
     * Constructor to make a status Message object
     * consider the parameters are swaped from default Message constructor,
     * not a good approach but have to go with it.
     */
    public Message(boolean status, String message) {
        super();
        this.message = message;
        this.isMine = false;
        this.isStatusMessage = status;
    }


    public Message(int Mid, int user_id, String name, String user_email, String image1, String image2, String image3, String sender_message, String receiver_message, String type) {
        this.Mid=Mid;
        this.user_id=user_id;
        this.name=name;
        this.user_email=user_email;
        this.image1=image1;
        this.image2=image2;
        this.image3=image3;
        this.dosomething_sender_message=sender_message;
        this.dosomething_receiver_message=receiver_message;
        this.type = type;
    }




    public int getMid() {
        return Mid;
    }

    public void setMid(int Mid) {
        this.Mid = Mid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int imag_Active) {
        this.user_id = user_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }


    public String getDosomething_sender_message() {
        return dosomething_sender_message;
    }

    public void setDosomething_sender_message(String message) {
        this.dosomething_sender_message = message;
    }
    public String getDosomething_receiver_message() {
        return dosomething_sender_message;
    }

    public void setDosomething_receiver_message(String message) {
        this.dosomething_sender_message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }






    public String getMessageDate() {
        return date;
    }

    public void setMessageDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setMessagetTime(String message) {
        this.time = message;
    }
    public String getMessageTime() {
        return time;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isStatusMessage() {
        return isStatusMessage;
    }

    public void setStatusMessage(boolean isStatusMessage) {
        this.isStatusMessage = isStatusMessage;
    }


}
