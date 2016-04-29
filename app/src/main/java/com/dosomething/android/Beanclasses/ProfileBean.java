package com.dosomething.android.Beanclasses;


public class ProfileBean
    {
    String
            first_name,
            last_name,
            about,
            gender,
            email,
            status,
            device,
            date_of_birth,
            image2,
            image1,
            image3,
            device_token;
    int
            user_id,
            profile_id,
            online_status,
            age;

    public ProfileBean()
        {

        }

    public ProfileBean(int user_id,int profile_id, String first_name, String last_name, String gender, String about, String email,String status ,String device,int age,String image2, String image1, String image3,String device_token,String date_of_birth)
        {
        this.user_id=user_id;
        this.profile_id=profile_id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.gender=gender;
        this.about=about;
        this.email=email;
        this.status=status;
        this.device=device;
        this.age=age;
        this.image2=image2;
        this.image1=image1;
        this.image3=image3;
        this.device_token=device_token;
        this.date_of_birth=date_of_birth;
        }

    public int getprofile_id()
        {
        return profile_id;
        }

    public void setprofile_id(int profile_id)
        {
        this.profile_id = profile_id;
        }

    public String getfirst_name()
        {
        return first_name;
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

    public int getuser_id()
        {
        return user_id;
        }

    public void setuser_id(int user_id)
        {
        this.user_id = user_id;
        }

    public int getage()
        {
        return age;
        }

    public void setage(int age)
        {
        this.age = age;
        }

    public int getonline_status()
        {
        return online_status;
        }

    public void setonline_status(int online_status)
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

    public String getstatus()
        {
        return status;
        }

    public void setstatus(String status)
        {
        this.status = status;
        }

    public String getabout()
        {
        return about;
        }

    public void setabout(String about)
        {
        this.about = about;
        }

    public String getemail()
        {
        return email;
        }

    public void setemail(String email)
        {
        this.email = email;
        }

    public String getdevice()
        {
        return device;
        }

    public void setdevice(String device)
        {
        this.device = device;
        }

    public String getdevice_token()
        {
        return device_token;
        }

    public void setdevice_token(String device_token)
        {
        this.device_token = device_token;
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
    }

// Adhamsheriff 2016_04_29
