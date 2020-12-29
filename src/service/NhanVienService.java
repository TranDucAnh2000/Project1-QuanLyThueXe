package service;

import javafx.scene.control.Alert;
import models.NhanVienModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienService {
    public List<NhanVienModel> getlistNV()  {
        List<NhanVienModel> lsNV=new ArrayList<>();
        String sql="select * from NhanVien";

        try {
            Connection connection = DBConnection.getConnection();
            ResultSet res=DBConnection.getData(sql,connection);
            while (res.next()){
                NhanVienModel nv=new NhanVienModel();
                nv.setMaNV(res.getInt("MaNV"));
                nv.setTenNV(res.getNString("TenNV"));
                nv.setSoDT(res.getString("SoDT"));
                nv.setNgaysinh(res.getDate("NgaySinh"));
                nv.setDiachi(res.getNString("DiaChi"));
                nv.setSoCMT(res.getString("SoCMT"));
                lsNV.add(nv);
            }
            connection.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lsNV;

    }
    public List<NhanVienModel> searchListNV(String colSelected, String key) {
        List<NhanVienModel> lsNV = new ArrayList<>();
        try {

            Connection conn = service.DBConnection.getConnection();
            ResultSet res = service.DBConnection.getData("select * from NhanVien where " + colSelected + " LIKE " + "N'%" + key + "%'", conn);

            while(res.next()){
                NhanVienModel nv=new NhanVienModel();
                nv.setMaNV(res.getInt("MaNV"));
                nv.setTenNV(res.getNString("TenNV"));
                nv.setSoDT(res.getString("SoDT"));
                nv.setNgaysinh(res.getDate("NgaySinh"));
                nv.setDiachi(res.getNString("DiaChi"));
                nv.setSoCMT(res.getString("SoCMT"));
                lsNV.add(nv);
            }
            conn.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("loi khi get list tim kiem nv");
        }
        return lsNV;
    }

    public int addListNV(NhanVienModel nv)  {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "Insert into NhanVien Values( ?, ?, ?, ?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setInt(1, khachHangModel.getMaKH());
            pst.setNString(1, nv.getTenNV());
            pst.setString(2, nv.getSoDT());
            pst.setDate(3,nv.getNgaysinh());
            pst.setNString(4, nv.getDiachi());
            pst.setString(5,nv.getSoCMT());
            pst.executeUpdate();
            conn.close();
            pst.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm không thành công,Nhập lại dữ liệu");
            alert.showAndWait();
            return  0;
        }

    }

    public void deleteNV(int maNV)  {
        try {
            Connection  conn = DBConnection.getConnection();
            String sql = "Delete from NhanVien where MaNV = "+maNV;
            Statement pst = conn.createStatement();
            pst.execute(sql);
            conn.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int editListNV(NhanVienModel nv)  {
        String sql = "Update NhanVien set TenNV = ?, SoDT = ?, NgaySinh = ?,DiaChi = ?,SoCMT=? where MaNV = ?";
        try {
            Connection  conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setNString(1, nv.getTenNV());
            pst.setString(2, nv.getSoDT());
            pst.setDate(3,nv.getNgaysinh());
            pst.setNString(4,nv.getDiachi());
            pst.setString(5,nv.getSoCMT());
            pst.setInt(6,nv.getMaNV());
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
}
