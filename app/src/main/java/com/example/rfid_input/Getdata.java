package com.example.rfid_input;

public class Getdata {


    public Getdata() {
    }

    public Getdata(String parent, String phone, String ID, String name, String password, String pic) {
        this.parent = parent;
        this.phone = phone;
        this.ID = ID;
        Name = name;
        this.password = password;
        this.pic = pic;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    private String parent;
    private String phone;
    private String ID;
    private String Name;
    private String password;
    private String pic;
}
