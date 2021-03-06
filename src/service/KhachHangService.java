package service;

import javafx.scene.control.Alert;
import models.KhachHangModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangService {

    public List<KhachHangModel> getListKhachHang() {
        List<KhachHangModel> list = new ArrayList<>();
        try {
            Connection conn = service.DBConnection.getConnection();
            ResultSet rs = service.DBConnection.getData("select * from KhachHang", conn);

            while (rs.next()) {
                KhachHangModel khachHangModel = new KhachHangModel();
                khachHangModel.setMaKH(rs.getInt("MaKH"));
                khachHangModel.setTenKH(rs.getString("TenKH"));
                khachHangModel.setSoDT(rs.getString("SoDT"));
                khachHangModel.setSoCMT(rs.getString("SoCMT"));
                khachHangModel.setDiaChi(rs.getString("DiaChi"));
                list.add(khachHangModel);
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    public List<KhachHangModel> searchListKhachHang(String colSelected, String key) {
        List<KhachHangModel> list = new ArrayList<>();
        try {

            Connection conn = service.DBConnection.getConnection();
            ResultSet rs = service.DBConnection.getData("select * from KhachHang where " + colSelected + " LIKE " + "N'%" + key + "%'", conn);

            while(rs.next()){
                KhachHangModel khachHangModel = new KhachHangModel();
                khachHangModel.setMaKH(rs.getInt("MaKH"));
                khachHangModel.setTenKH(rs.getString("TenKH"));
                khachHangModel.setSoDT(rs.getString("SoDT"));
                khachHangModel.setSoCMT(rs.getString("SoCMT"));
                khachHangModel.setDiaChi(rs.getString("DiaChi"));
                list.add(khachHangModel);
            }

            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public int addListKhachHang(KhachHangModel khachHangModel) {

        try {
            Connection  conn = DBConnection.getConnection();

            String sql = "insert into KhachHang(TenKH, SoDT, SoCMT, DiaChi) values( ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setInt(1, khachHangModel.getMaKH());
            pst.setNString(1, khachHangModel.getTenKH());
            pst.setString(2, khachHangModel.getSoDT());
            pst.setString(3, khachHangModel.getSoCMT());
            pst.setNString(4, khachHangModel.getDiaChi());

            pst.executeUpdate();

            conn.close();
            pst.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Thêm thành công");
            alert.showAndWait();
            return 0;
        }
    }

    public void deleteListKhachHang(int maKH) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Delete from KhachHang where MaKH = "+maKH;
        Statement pst = conn.createStatement();
        pst.execute(sql);

        conn.close();
        pst.close();
    }

    public int editListKhachHang(KhachHangModel khachHangModel)  {

        try {
            Connection conn = DBConnection.getConnection();

        String sql = "Update KhachHang set TenKH = ?, SoDT = ?, SoCMT = ?, DiaChi = ?\n" +
                "where MaKH = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, khachHangModel.getTenKH());
        pst.setString(2, khachHangModel.getSoDT());
        pst.setString(3, khachHangModel.getSoCMT());
        pst.setString(4, khachHangModel.getDiaChi());
        pst.setInt(5, khachHangModel.getMaKH());

        pst.executeUpdate();

        conn.close();
        pst.close();
        return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm không thành công,Nhập lại dữ liệu");
            alert.showAndWait();
            return 0;
        }
    }

}
