package com.ekr.smartlaundry.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";

    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_UID ="spUid";
    public static final String SP_RULE ="spRule";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_NOHP = "spNohp";


    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    public Session(Context context){
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void logout(Context context){
        sp =context.getSharedPreferences(SP_MAHASISWA_APP,Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPUid(){
        return sp.getString(SP_UID, "");
    }
    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }
    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }
    public String getSpRule(){
        return sp.getString(SP_RULE, "");
    }
    public String getSpAlamat(){
        return sp.getString(SP_ALAMAT, "");
    }
    public String getSpNohp(){
        return sp.getString(SP_NOHP, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
