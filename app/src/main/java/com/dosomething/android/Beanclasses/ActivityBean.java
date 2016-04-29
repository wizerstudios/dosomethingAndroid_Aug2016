package com.dosomething.android.Beanclasses;


public class ActivityBean
    {
    String
            imag_inActive,
            imag_Active,
            image_name,
            LastActivity;
    int
            image_id;

    public ActivityBean(int image_id, String image_name, String imag_Active, String imag_inActive,String lastActivity)
        {
        this.image_id=image_id;
        this.image_name=image_name;
        this.imag_Active=imag_Active;
        this.imag_inActive = imag_inActive;
        this.LastActivity=lastActivity;
        }

    public ActivityBean()
        {

        }

    public String getImag_inActive()
        {
        return imag_inActive;
        }

    public String getImag_Active()
        {
        return imag_Active;
        }

    public void setImag_Active(String imag_Active)
        {
        this.imag_Active = imag_Active;
        }

    public void setImag_inActive(String imag_inActive)
        {
        this.imag_inActive = imag_inActive;
        }

    public int getImage_id()
        {
        return image_id;
        }

    public void setImage_id(int image_id)
        {
        this.image_id = image_id;
        }

    public String getImage_name()
        {
        return image_name;
        }

    public void setImage_name(String image_name)
        {
        this.image_name = image_name;
        }

    public String getLastActivity()
        {
        return LastActivity;
        }

    public void setLastActivity(String lastActivity)
        {
        this.LastActivity = lastActivity;
        }
    }

// Adhamsheriff 2016_04_29