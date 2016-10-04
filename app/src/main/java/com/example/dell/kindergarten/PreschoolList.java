package com.example.dell.kindergarten;

/**
 * Created by Dell on 4/6/2016.
 */
public class PreschoolList {
    private int SchoolId;
    private String SchoolName;
    private String SchoolAddress;
    private String SchoolEmail;
    private String SchoolWebsite;
    private String SchoolPhone;
    private double latitude;
    private double longitude;
    public int getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(int schoolId) {
        SchoolId = schoolId;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getSchoolAddress() {
        return SchoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        SchoolAddress = schoolAddress;
    }

    public String getSchoolEmail() {
        return SchoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        SchoolEmail = schoolEmail;
    }

    public String getSchoolWebsite() {
        return SchoolWebsite;
    }

    public void setSchoolWebsite(String schoolWebsite) {
        SchoolWebsite = schoolWebsite;
    }

    public String getSchoolPhone() {
        return SchoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        SchoolPhone = schoolPhone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
