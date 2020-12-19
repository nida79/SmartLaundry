package com.ekr.smartlaundry.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CuciModel implements Parcelable {
    private String nama;
    private String uuid;
    private String alamat;
    private String noHp;
    private String tanggal;
    private String tipe_pesanan;
    private String tipe_pembayaran;
    private String status;
    private String total_pembayaran;
    private String resi;
    private String keyID;
    private String keterangan;
    private String foto;

    public CuciModel() {
    }

    public CuciModel(String nama, String uuid, String alamat, String noHp, String tanggal,
                     String tipe_pesanan, String tipe_pembayaran,String keterangan,
                     String status, String total_pembayaran, String resi, String keyID,String foto) {
        this.nama = nama;
        this.uuid = uuid;
        this.alamat = alamat;
        this.noHp = noHp;
        this.tanggal = tanggal;
        this.tipe_pesanan = tipe_pesanan;
        this.tipe_pembayaran = tipe_pembayaran;
        this.keterangan = keterangan;
        this.status = status;
        this.total_pembayaran = total_pembayaran;
        this.resi = resi;
        this.keyID = keyID;
        this.foto = foto;
    }

    protected CuciModel(Parcel in) {
        nama = in.readString();
        uuid = in.readString();
        alamat = in.readString();
        noHp = in.readString();
        tanggal = in.readString();
        tipe_pesanan = in.readString();
        tipe_pembayaran = in.readString();
        keterangan = in.readString();
        status = in.readString();
        total_pembayaran = in.readString();
        resi = in.readString();
        keyID = in.readString();
        foto = in.readString();
    }

    public static final Creator<CuciModel> CREATOR = new Creator<CuciModel>() {
        @Override
        public CuciModel createFromParcel(Parcel in) {
            return new CuciModel(in);
        }

        @Override
        public CuciModel[] newArray(int size) {
            return new CuciModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama);
        parcel.writeString(uuid);
        parcel.writeString(alamat);
        parcel.writeString(noHp);
        parcel.writeString(tanggal);
        parcel.writeString(tipe_pesanan);
        parcel.writeString(tipe_pembayaran);
        parcel.writeString(keterangan);
        parcel.writeString(status);
        parcel.writeString(total_pembayaran);
        parcel.writeString(resi);
        parcel.writeString(keyID);
        parcel.writeString(foto);
    }

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

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTipe_pesanan() {
        return tipe_pesanan;
    }

    public void setTipe_pesanan(String tipe_pesanan) {
        this.tipe_pesanan = tipe_pesanan;
    }

    public String getTipe_pembayaran() {
        return tipe_pembayaran;
    }

    public void setTipe_pembayaran(String tipe_pembayaran) {
        this.tipe_pembayaran = tipe_pembayaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_pembayaran() {
        return total_pembayaran;
    }

    public void setTotal_pembayaran(String total_pembayaran) {
        this.total_pembayaran = total_pembayaran;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
