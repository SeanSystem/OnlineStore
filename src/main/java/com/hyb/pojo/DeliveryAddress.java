package com.hyb.pojo;

public class DeliveryAddress {
    private String id;

    private String dname;

    private String dtelphone;

    private String daddress;

    private String uid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname == null ? null : dname.trim();
    }

    public String getDtelphone() {
        return dtelphone;
    }

    public void setDtelphone(String dtelphone) {
        this.dtelphone = dtelphone == null ? null : dtelphone.trim();
    }

    public String getDaddress() {
        return daddress;
    }

    public void setDaddress(String daddress) {
        this.daddress = daddress == null ? null : daddress.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}