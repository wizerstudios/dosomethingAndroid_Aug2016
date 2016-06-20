package com.dosomething.android.Beanclasses;


import java.util.ArrayList;

public class Filterbean
    {
    String
            first_name;
        String last_name;
        String about;
        String gender;
        String age;
        String date_of_birth;
        String image2;
        String image1;
        String image3;
        String online_status;
        String Avilable_now;
        String DoSomething;
        String distance;
        String user_id;
        String imag_inActive;
        String imag_Active;
        String image_name;
        String imag_inActive1;
        String imag_Active1;
        String image_name1;
        String imag_inActive2;
        String imag_Active2;
        String image_name2;

        public String getMatched() {
            return matched;
        }

        public void setMatched(String matched) {
            this.matched = matched;
        }

        String matched;
    ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();
    int
            image_id,
            image_id1,
            image_id2;

    public Filterbean()
        {
        }


    public Filterbean(String user_id, String first_name, String last_name, String about, String gender, String age, String date_of_birth, String online_status,String image2, String image1, String image3, String Available_now, String DoSomething, String distance, int image_id, String image_name, String imag_Active, String imag_inActive, int image_id1, String image_name1, String imag_Active1, String imag_inActive1, int image_id2, String image_name2, String imag_Active2, String imag_inActive2, ArrayList<HobbiesBean> hobbiesBeans,String matched)
        {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.about = about;
        this.age = age;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.online_status = online_status;
        this.image2 = image2;
        this.image1 = image1;
        this.image3 = image3;
        this.Avilable_now = Available_now;
        this.DoSomething = DoSomething;
        this.distance = distance;
        this.image_id = image_id;
        this.image_name = image_name;
        this.imag_Active = imag_Active;
        this.imag_inActive = imag_inActive;
        this.image_id1 = image_id1;
        this.image_name1 = image_name1;
        this.imag_Active1 = imag_Active1;
        this.imag_inActive1 = imag_inActive1;
        this.image_id2 = image_id2;
        this.image_name2 = image_name2;
        this.imag_Active2 = imag_Active2;
        this.imag_inActive2 = imag_inActive2;
        this.hobbiesBeans = hobbiesBeans;
            this.matched=matched;
        }

    public ArrayList<HobbiesBean> getListHobbies()
        {
        return hobbiesBeans;
        }

    public void setListHobbies(ArrayList<HobbiesBean> hobbiesBeans)
        {
        this.hobbiesBeans = hobbiesBeans;
        }

    public String getAge()
        {
        return age;
        }

    public void setAge(String age)
        {
        this.age = age;
        }

    public String getAvilable_now()
        {
        return Avilable_now;
        }

    public String getfirst_name()
        {
        return first_name;
        }

    public void setAvilable_now(String avilableNow)
        {
        this.Avilable_now = avilableNow;
        }

    public void setfirst_name(String first_name)
        {
        this.first_name = first_name;
        }

    public String getlast_name()
        {
        return last_name;
        }

    public void setlast_name(String last_name)
        {
        this.last_name = last_name;
        }

    public String getuser_id()
        {
        return user_id;
        }

    public void setuser_id(String user_id)
        {
        this.user_id = user_id;
        }

    public String getonline_status()
        {
        return online_status;
        }

    public void setonline_status(String online_status)
        {
        this.online_status = online_status;
        }

    public String getgender()
        {
        return gender;
        }

    public void setgender(String gender)
        {
        this.gender = gender;
        }

    public String getabout()
        {
        return about;
        }

    public void setabout(String about)
        {
        this.about = about;
        }

    public String getdate_of_birth()
        {
        return date_of_birth;
        }

    public void setdate_of_birth(String date_of_birth)
        {
        this.date_of_birth = date_of_birth;
        }

    public String getimage2()
        {
        return image2;
        }

    public void setimage2(String image2)
        {
        this.image2 = image2;
        }

    public String getimage1()
        {
        return image1;
        }

    public void setimage1(String image1)
        {
        this.image1 = image1;
        }

    public String getimage3()
        {
        return image3;
        }

    public void setimage3(String image3)
        {
        this.image3 = image3;
        }

    public String getDoSomething()
        {
        return DoSomething;
        }

    public void setDoSomething(String DoSomething)
        {
        this.DoSomething = DoSomething;
        }

    public String getdistance()
        {
        return distance;
        }

    public void setdistance(String distance)
        {
        this.distance = distance;
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

    public String getImag_inActive1()
        {
        return imag_inActive1;
        }

    public String getImag_Active1()
        {
        return imag_Active1;
        }

    public void setImag_Active1(String imag_Active1)
        {
        this.imag_Active1 = imag_Active1;
        }

    public void setImag_inActive1(String imag_inActive1)
        {
        this.imag_inActive1 = imag_inActive1;
        }

    public int getImage_id1()
        {
        return image_id1;
        }

    public void setImage_id1(int image_id1)
        {
        this.image_id1 = image_id1;
        }

    public String getImage_name1()
        {
        return image_name1;
        }

    public void setImage_name1(String image_name1)
        {
        this.image_name1 = image_name1;
        }

    public String getImag_inActive2()
        {
        return imag_inActive2;
        }

    public String getImag_Active2()
        {
        return imag_Active2;
        }

    public void setImag_Active2(String imag_Active2)
        {
        this.imag_Active2 = imag_Active2;
        }

    public void setImag_inActive2(String imag_inActive2)
        {
        this.imag_inActive2 = imag_inActive2;
        }

    public int getImage_id2()
        {
        return image_id2;
        }

    public void setImage_id2(int image_id2)
        {
        this.image_id2 = image_id2;
        }

    public String getImage_name2()
        {
        return image_name2;
        }

    public void setImage_name2(String image_name2)
        {
        this.image_name2 = image_name2;
        }
    }

// Adhamsheriff 2016_04_29


