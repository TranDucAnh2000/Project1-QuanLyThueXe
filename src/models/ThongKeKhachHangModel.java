package models;

public class ThongKeKhachHangModel {
    private int maKH;
    private String tenKH;
    private int soHD;
    private int soXeDaThue;
    private int soXeDaTra;
    private int tongTienPhat;
    private int daThanhToan;

    public ThongKeKhachHangModel(){ }

    public int getSoXeDaThue() {
        return soXeDaThue;
    }

    public void setSoXeDaThue(int soXeDaThue) {
        this.soXeDaThue = soXeDaThue;
    }

    public int getSoXeDaTra() {
        return soXeDaTra;
    }

    public void setSoXeDaTra(int soXeDaTra) {
        this.soXeDaTra = soXeDaTra;
    }

    public int getTongTienPhat() {
        return tongTienPhat;
    }

    public void setTongTienPhat(int tongTienPhat) {
        this.tongTienPhat = tongTienPhat;
    }

    public int getDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(int daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getSoHD() {
        return soHD;
    }

    public void setSoHD(int soHD) {
        this.soHD = soHD;
    }
}
