package com.lenovo.onlinelaptoprentalapp;

public class UploadModel {
    private String company;
    private String pro_level;
    private String memory;
    private String ram;
    private String os;
    private String pro_speed;
    private String features;
    private String phone;
    private String email_id;
    private String url;
    private String buyer;
    private String buyer_no;
    private String status;
    private String availability;
    private String address;
    private String center;
    private String hours;
    private String gmail;

    public UploadModel() {}

    public UploadModel(String company, String pro_level, String memory, String ram, String os, String pro_speed, String features,
                       String phone, String email_id, String url, String buyer, String status, String availability,String address,
                       String buyer_no,String center,String hours,String gmail)
    {
        this.company = company;
        this.pro_level = pro_level;
        this.memory = memory;
        this.ram = ram;
        this.os = os;
        this.pro_speed = pro_speed;
        this.features = features;
        this.phone = phone;
        this.email_id = email_id;
        this.url = url;
        this.buyer = buyer;
        this.buyer_no=buyer_no;
        this.status = status;
        this.availability = availability;
        this.address=address;
        this.center=center;
        this.hours=hours;
        this.gmail=gmail;
    }

    public String getGmail() {
        return gmail;
    }

    public String getHours() {
        return hours;
    }

    public String getCenter() {
        return center;
    }

    public String getBuyer_no() {
        return buyer_no;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany() {
        return company;
    }

    public String getPro_level() {
        return pro_level;
    }

    public String getMemory() {
        return memory;
    }

    public String getRam() {
        return ram;
    }

    public String getOs() {
        return os;
    }

    public String getPro_speed() {
        return pro_speed;
    }

    public String getFeatures() {
        return features;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getUrl() {
        return url;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getStatus() {
        return status;
    }

    public String getAvailability() {
        return availability;
    }
}