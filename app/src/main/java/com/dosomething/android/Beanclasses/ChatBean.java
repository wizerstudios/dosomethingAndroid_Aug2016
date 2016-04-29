package com.dosomething.android.Beanclasses;

import android.graphics.Bitmap;

public class ChatBean
    {
    String
            user_email,
            name,
            image1,
            image2,
            image3,
            last_message,
            last_message_time,
            unreadmessage,
            oldImageUrl = "";
    int
            chat_id,
            user_id;
    Bitmap
            profileImage;

    public ChatBean(int chat_id, int user_id, String name, String user_email, String image1, String image2, String image3, String last_message, String last_message_time, String unreadmessage)
        {
        this.chat_id = chat_id;
        this.user_id = user_id;
        this.name = name;
        this.user_email = user_email;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.last_message = last_message;
        this.last_message_time = last_message_time;
        this.unreadmessage = unreadmessage;
        }

    public ChatBean(String image1)
        {
        this.image1 = image1;
        }

    public ChatBean()
        {

        }

    public int getChat_id()
        {
        return chat_id;
        }

    public void setChat_id(int chat_id)
        {
        this.chat_id = chat_id;
        }

    public String getUnreadmessage()
        {
        return unreadmessage;
        }

    public void setUnreadmessage(String unreadmessage)
        {
        this.unreadmessage = unreadmessage;
        }

    public int getUser_id()
        {
        return user_id;
        }

    public void setUser_id(int user_id)
        {
        this.user_id = user_id;
        }

    public String getName()
        {
        return name;
        }

    public void setName(String name)
        {
        this.name = name;
        }

    public String getUser_email()
        {
        return user_email;
        }

    public void setUser_email(String user_email)
        {
        this.user_email = user_email;
        }

    public String getImage1()
        {
        return image1;
        }

    public void setImage1(String image1)
        {
        this.image1 = image1;
        }

    public String getImage2()
        {
        return image2;
        }

    public void setImage2(String image2)
        {
        this.image2 = image2;
        }

    public String getImage3()
        {
        return image3;
        }

    public void setImage3(String image3)
        {
        this.image3 = image3;
        }

    public String getLast_message()
        {
        return last_message;
        }

    public void setLast_message(String last_message)
        {
        this.last_message = last_message;
        }

    public String getLast_message_time()
        {
        return last_message_time;
        }

    public void setLast_message_time(String last_message_time)
        {
        this.last_message_time = last_message_time;
        }

    public String getOldImageUrl()
        {
        return oldImageUrl;
        }

    public void setOldImageUrl(String oldImageUrl)
        {
        this.oldImageUrl = oldImageUrl;
        }

    public Bitmap getProfileImage()
        {
        return profileImage;
        }

    public void setProfileImage(Bitmap profileImage)
        {
        this.profileImage = profileImage;
        }
    }

// Adhamsheriff 2016_04_29