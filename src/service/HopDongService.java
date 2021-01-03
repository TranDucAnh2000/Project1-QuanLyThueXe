package service;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import models.CTHopDongModel;
import models.HopDongModel;
import models.XeModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import views.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HopDongService {

    public List<HopDongModel> getListHopDong(){
        List<HopDongModel> lshopdong=new ArrayList<>();
        //Connection connection= null;
        try {
            Connection connection = DBConnection.getConnection();

        String sql="select * from HopDong order by MaHD asc";
        ResultSet res=service.DBConnection.getData(sql,connection);
        while (res.next()){
            HopDongModel ex=new HopDongModel();
            ex.setMaHD(res.getInt("MaHD"));
            ex.setMaKH(res.getInt("MaKH"));
            ex.setMaNV(res.getInt("MaNV"));
            ex.setTienCoc(res.getInt("TienCoc"));
            ex.setTienThanhToan(res.getInt("TienThanhToan"));
            ex.setNgayThue(res.getDate("NgayThue"));
            ex.setNgayHenTra(res.getDate("NgayHenTra"));
            lshopdong.add(ex);
        }
        connection.close();
        res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("loi khi get danh sach hop dong ");
        }

        return lshopdong;
    }

    public int addListHopDong(HopDongModel hopDongModel) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();

        String sql = "Insert into HopDong(MaKH, MaNV, NgayThue, NgayHenTra, TienCoc, TienThanhToan) Values(?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, hopDongModel.getMaKH());
        pst.setInt(2, hopDongModel.getMaNV());
        pst.setDate(3, hopDongModel.getNgayThue());
        pst.setDate(4, hopDongModel.getNgayHenTra());
        pst.setInt(5, hopDongModel.getTienCoc());
        pst.setInt(6, hopDongModel.getTienThanhToan());
        pst.executeUpdate();
        conn.close();
        pst.close();
        return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm không thành công,Nhập lại dữ liệu");
            alert.showAndWait();
        }
        return 0;
    }

    public void deleteListHopDong(HopDongModel ctHopDongModel) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Delete from ChitietHopDong where MaHD = "+ctHopDongModel.getMaHD() ;
        String sql2="Delete from HopDong where MaHD = "+ctHopDongModel.getMaHD();
        Statement pst = conn.createStatement();
        pst.execute(sql);
        pst.execute(sql2);
        conn.close();
        pst.close();
    }

    public int editListHopDong(HopDongModel hopDongModel)  {
        String sql = "Update HopDong set MaKH = ?, MaNV = ?, NgayThue = ?, NgayHenTra = ?, TienCoc=?,TienThanhToan=? where MaHD = ?";
        try {
            Connection  conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, hopDongModel.getMaKH());
            pst.setInt(2, hopDongModel.getMaNV());
            pst.setDate(3, hopDongModel.getNgayThue());
            pst.setDate(4, hopDongModel.getNgayHenTra());
            pst.setInt(5, hopDongModel.getTienCoc());
            pst.setInt(6,hopDongModel.getTienThanhToan());
            pst.setInt(7, hopDongModel.getMaHD());
            pst.executeUpdate();
            conn.close();
            pst.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
