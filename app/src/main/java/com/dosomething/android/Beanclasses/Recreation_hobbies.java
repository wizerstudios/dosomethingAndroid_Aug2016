package com.dosomething.android.Beanclasses;


public class Recreation_hobbies
    {
    String
            imag_inActive_recreation,
            imag_Active_recreation,
            image_name_recreation;
    int
            image_id_recreation,
            category_id_recreation;
    boolean
            state;


    public Recreation_hobbies(int image_id,int category_id,String image_name,String imag_inActive,String imag_Active,boolean state)
        {
        this.image_id_recreation=image_id;
        this.category_id_recreation=category_id;
        this.image_name_recreation=image_name;
        this.imag_Active_recreation=imag_Active;
        this.imag_inActive_recreation = imag_inActive;
        this.state=state;
        }

    public String getImag_inActive_recreation()
        {
        return imag_inActive_recreation;
        }

    public String getImag_Active_recreation()
        {
        return imag_Active_recreation;
        }

    public void setImag_Active_recreation(String imag_Active)
        {
        this.imag_Active_recreation = imag_Active;
        }

    public void setImag_inActive_recreation(String imag_inActive)
        {
        this.imag_inActive_recreation = imag_inActive;
        }

    public int getImage_id_recreation()
        {
        return image_id_recreation;
        }

    public void setImage_id_recreation(int image_id)
        {
        this.image_id_recreation = image_id;
        }

    public int getcategory_id_recreation()
        {
        return category_id_recreation;
        }

    public void setcategory_id_recreation(int category_id)
        {
        this.category_id_recreation = category_id;
        }

    public String getImage_name_recreation()
        {
        return image_name_recreation;
        }

    public void setImage_name_recreation(String image_name)
        {
        this.image_name_recreation = image_name;
        }

    public boolean getstate()
        {
        return state;
        }

    public void setstate(boolean state)
        {
        this.state = state;
        }
    }

// Adhamsheriff 2016_04_29
