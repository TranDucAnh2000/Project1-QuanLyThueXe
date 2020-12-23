package service;

import models.KhachHangModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                khachHangModel.setMaKH(rs.getString("MaKH"));
                khachHangModel.setTenKH(rs.getString("TenKH"));
                khachHangModel.setSoDT(rs.getInt("SoDT"));
                khachHangModel.setSoCMT(rs.getInt("SoCMT"));
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
                khachHangModel.setMaKH(rs.getString("MaKH"));
                khachHangModel.setTenKH(rs.getString("TenKH"));
                khachHangModel.setSoDT(rs.getInt("SoDT"));
                khachHangModel.setSoCMT(rs.getInt("SoCMT"));
                khachHangModel.setDiaChi(rs.getString("DiaChi"));
                list.add(khachHangModel);
            }

            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void addListKhachHang(KhachHangModel khachHangModel) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Insert into KhachHang(MaKH, TenKH, SoDT, SoCMT, DiaChi) Values(?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, khachHangModel.getMaKH());
        pst.setString(2, khachHangModel.getTenKH());
        pst.setInt(3, khachHangModel.getSoDT());
        pst.setInt(4, khachHangModel.getSoCMT());
        pst.setString(5, khachHangModel.getDiaChi());

        pst.executeUpdate();

        conn.close();
        pst.close();
    }

    public void deleteListKhachHang(String maKH) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Delete from KhachHang where MaKH = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, String.valueOf(maKH));

        pst.execute();

        conn.close();
        pst.close();
    }

    public void editListKhachHang(KhachHangModel khachHangModel) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Update KhachHang set TenKH = ?, SoDT = ?, SoCMT = ?, DiaChi = ?\n" +
                "where MaKH = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, khachHangModel.getTenKH());
        pst.setInt(2, khachHangModel.getSoDT());
        pst.setInt(3, khachHangModel.getSoCMT());
        pst.setString(4, khachHangModel.getDiaChi());
        pst.setString(5, khachHangModel.getMaKH());

        pst.executeUpdate();

        conn.close();
        pst.close();
    }

}
