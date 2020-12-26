package models;
import java.sql.Date;
public class CTHopDongModel {
    private  int maHD;
    private int maXe;
    private int tienphat;
    private Date ngaytra;
    private  String suco;

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

    public int getTienphat() {
        return tienphat;
    }

    public void setTienphat(int tienphat) {
        this.tienphat = tienphat;
    }

    public Date getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(Date ngaytra) {
        this.ngaytra = ngaytra;
    }

    public String getSuco() {
        return suco;
    }

    public void setSuco(String suco) {
        this.suco = suco;
    }
}
