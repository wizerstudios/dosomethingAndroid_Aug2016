package com.dosomething.android.Beanclasses;

/**
 * Created by hi on 26-Oct-15.
 */
public class Arts_hobbies {
    String imag_inActive_arts, imag_Active_arts,image_name_arts;
    int image_id_arts;
    int category_id_arts;
    boolean state;


    public Arts_hobbies(int image_id,int category_id,String image_name,String imag_inActive,String imag_Active,boolean state) {
        this.image_id_arts=image_id;
        this.category_id_arts=category_id;
        this.image_name_arts=image_name;
        this.imag_Active_arts=imag_Active;
        this.imag_inActive_arts = imag_inActive;
        this.state=state;
    }


    public String getImag_inActive_arts() {
        return imag_inActive_arts;
    }
    public String getImag_Active_arts() {
        return imag_Active_arts;
    }

    public void setImag_Active_arts(String imag_Active) {
        this.imag_Active_arts = imag_Active;
    }

    public void setImag_inActive_arts(String imag_inActive) {
        this.imag_inActive_arts = imag_inActive;
    }
    public int getcategory_id_arts() {
        return category_id_arts;
    }
    public void setcategory_id_arts(int category_id) {
        this.category_id_arts = category_id;
    }
    public int getImage_id_arts() {
        return image_id_arts;
    }
    public void setImage_id_arts(int image_id) {
        this.image_id_arts = image_id;
    }

    public String getImage_name_arts() {
        return image_name_arts;
    }

    public void setImage_name_arts(String image_name) {
        this.image_name_arts = image_name;
    }

    public boolean getstate() {
        return state;
    }
    public void setstate(boolean state) {
        this.state = state;
    }

}


