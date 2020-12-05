package com.ekr.smartlaundry.data;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    private String nama;
    private String uuid;
    private String alamat;
    private String email;
    private String nohp;
    private String rule;

    public UserModel(){}



    protected UserModel(Parcel in) {
        nama = in.readString();
        uuid = in.readString();
        alamat = in.readString();
        email = in.readString();
        nohp = in.readString();
        rule = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(uuid);
        dest.writeString(alamat);
        dest.writeString(email);
        dest.writeString(nohp);
        dest.writeString(rule);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
