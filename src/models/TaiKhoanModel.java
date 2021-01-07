package models;

public class TaiKhoanModel {
    private String tendangnhap;
    private String matkhau;
    private int MaNV;

    public String getTendangnhap() {
        return tendangnhap;
    }

    public TaiKhoanModel(String tendangnhap, String matkhau) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
    }

    public TaiKhoanModel(){}
    public TaiKhoanModel(String tendangnhap, String matkhau, int maNV) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        MaNV = maNV;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }
}
