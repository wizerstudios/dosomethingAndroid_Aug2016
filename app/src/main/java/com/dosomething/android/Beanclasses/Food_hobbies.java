package com.dosomething.android.Beanclasses;

public class Food_hobbies
    {
    String
            imag_inActive_food,
            imag_Active_food,
            image_name_food;
    int
            image_id_food,
            category_id_food;
    boolean
            state;


    public Food_hobbies(int image_id,int category_id,String image_name,String imag_inActive,String imag_Active,boolean state)
        {
        this.image_id_food=image_id;
        this.category_id_food=category_id;
        this.image_name_food=image_name;
        this.imag_Active_food=imag_Active;
        this.imag_inActive_food = imag_inActive;
        this.state=state;
        }

    public String getImag_inActive_food()
        {
        return imag_inActive_food;
        }

    public String getImag_Active_food()
        {
        return imag_Active_food;
        }

    public void setImag_Active_food(String imag_Active)
        {
        this.imag_Active_food = imag_Active;
        }

    public void setImag_inActive_food(String imag_inActive)
        {
        this.imag_inActive_food = imag_inActive;
        }

    public int getImage_id_food()
        {
        return image_id_food;
        }

    public void setImage_id_food(int image_id)
        {
        this.image_id_food = image_id;
        }

    public int getcategory_id_food()
        {
        return category_id_food;
        }

    public void setcategory_id_food(int category_id)
        {
        this.category_id_food = category_id;
        }

    public String getImage_name_food()
        {
        return image_name_food;
        }

    public void setImage_name_food(String image_name)
        {
        this.image_name_food = image_name;
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
