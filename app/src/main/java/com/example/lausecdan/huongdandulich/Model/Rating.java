package com.example.lausecdan.huongdandulich.Model;

public class Rating {
    private String userPhone;
    private String diadiemId;
    private String rateValue;
    private String comment;

    public Rating() {
    }

    public Rating(String userPhone, String diadiemId, String rateValue, String comment) {
        this.userPhone = userPhone;
        this.diadiemId = diadiemId;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDiadiemId() {
        return diadiemId;
    }

    public void setDiadiemId(String diadiemId) {
        this.diadiemId = diadiemId;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
