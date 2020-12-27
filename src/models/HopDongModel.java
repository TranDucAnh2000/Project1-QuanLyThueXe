package models;

import java.sql.Date;

public class HopDongModel {
    private int maKH;
    private int maHD;
    private int maNV;
    private int tienCoc;
    private int tienThanhToan;
    private Date ngayThue;
    private Date ngayHenTra;

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(int tiencoc) {
        this.tienCoc = tiencoc;
    }

    public int getTienThanhToan() {
        return tienThanhToan;
    }

    public void setTienThanhToan(int tienThanhToan) {
        this.tienThanhToan = tienThanhToan;
    }

    public Date getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(Date ngayThue) {
        this.ngayThue = ngayThue;
    }

    public Date getNgayHenTra() {
        return ngayHenTra;
    }

    public void setNgayHenTra(Date ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }
}
