package models;

public class KhachHangModel {

    private String maKH;
    private String tenKH;
    private int soDT;
    private int soCMT;
    private String diaChi;

    public KhachHangModel(){ }

    public KhachHangModel(String maKH, String tenKH, int soDT, int soCMT, String diaChi){
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDT = soDT;
        this.soCMT = soCMT;
        this.diaChi = diaChi;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getSoDT() {
        return soDT;
    }

    public void setSoDT(int soDT) {
        this.soDT = soDT;
    }

    public int getSoCMT() {
        return soCMT;
    }

    public void setSoCMT(int soCMT) {
        this.soCMT = soCMT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
