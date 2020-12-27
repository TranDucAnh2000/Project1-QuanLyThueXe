package models;
import java.sql.Date;
public class CTHopDongModel {
    private  int maHD;
    private int maXe;
    private int tienPhat;
    private Date ngayTra;
    private  String suCo;

    public CTHopDongModel() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaXe() {
        return maXe;
    }

    public void setMaXe(int maXe) {
        this.maXe = maXe;
    }

    public int getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(int tienPhat) {
        this.tienPhat = tienPhat;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getSuCo() {
        return suCo;
    }

    public void setSuCo(String suCo) {
        this.suCo = suCo;
    }
}
