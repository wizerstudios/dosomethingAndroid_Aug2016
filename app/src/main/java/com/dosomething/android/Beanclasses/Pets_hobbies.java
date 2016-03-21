package com.dosomething.android.Beanclasses;

/**
 * Created by hi on 26-Oct-15.
 */
public class Pets_hobbies {
    String imag_inActive_pets, imag_Active_pets,image_name_pets;
    int image_id_pets;
    int category_id_pets;
    boolean state;


    public Pets_hobbies(int image_id,int category_id,String image_name,String imag_inActive,String imag_Active,boolean state) {
        this.image_id_pets=image_id;
        this.category_id_pets=category_id;
        this.image_name_pets=image_name;
        this.imag_Active_pets=imag_Active;
        this.imag_inActive_pets = imag_inActive;
        this.state=state;
    }


    public String getImag_inActive_pets() {
        return imag_inActive_pets;
    }
    public String getImag_Active_pets() {
        return imag_Active_pets;
    }

    public void setImag_Active_pets(String imag_Active) {
        this.imag_Active_pets = imag_Active;
    }

    public void setImag_inActive_pets(String imag_inActive) {
        this.imag_inActive_pets = imag_inActive;
    }
    public int getImage_id_pets() {
        return image_id_pets;
    }
    public void setImage_id_pets(int image_id) {
        this.image_id_pets = image_id;
    }
    public int getcategory_id_pets() {
        return category_id_pets;
    }
    public void setcategory_id_pets(int category_id) {
        this.category_id_pets = category_id;
    }

    public String getImage_name_pets() {
        return image_name_pets;
    }

    public void setImage_name_pets(String image_name) {
        this.image_name_pets = image_name;
    }
    public boolean getstate() {
        return state;
    }
    public void setstate(boolean state) {
        this.state = state;
    }
}
