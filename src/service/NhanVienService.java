package service;

import models.NhanVienModel;
import models.XeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienService {
    public List<NhanVienModel> getlistXe()  {
        List<NhanVienModel> lsNV=new ArrayList<>();
        String sql="select * from NhanVien";

        try {
            Connection connection = DBConnection.getConnection();
            ResultSet res=DBConnection.getData(sql,connection);
            while (res.next()){
                NhanVienModel nv=new NhanVienModel();
                nv.setMaNV(res.getInt("MaXE"));
                nv.setTenNV(res.getNString("BienSoXe"));
                nv.setSoDT(res.getString("SoDT"));
                nv.setNgaysinh(res.getDate("NgaySinh"));
                nv.setDiachi(res.getNString("DiaChi"));
                nv.setSoCMND(res.getString("SoCMT"));
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
                nv.setMaNV(res.getInt("MaXE"));
                nv.setTenNV(res.getNString("BienSoXe"));
                nv.setSoDT(res.getString("SoDT"));
                nv.setNgaysinh(res.getDate("NgaySinh"));
                nv.setDiachi(res.getNString("DiaChi"));
                nv.setSoCMND(res.getString("SoCMT"));
                lsNV.add(nv);
            }
            conn.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("loi khi get list tim kiem xe");
        }
        return lsNV;
    }

    public void addListXe(NhanVienModel nv)  {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "Insert into NhanVien Values( ?, ?, ?, ?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setInt(1, khachHangModel.getMaKH());
            pst.setNString(1, nv.getTenNV());
            pst.setString(2, nv.getSoDT());
            pst.setDate(3,nv.getNgaysinh());
            pst.setNString(4, nv.getDiachi());
            pst.setString(5,nv.getSoCMND());
            pst.executeUpdate();
            conn.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteXe(int maNV)  {
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

    public void editListXe(NhanVienModel nv)  {
        String sql = "Update NhanVien set TenNV = ?, SoDT = ?, NgaySinh = ?,DiaChi = ?,SoCMT=? where MaNV = ?";
        try {
            Connection  conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setNString(1, nv.getTenNV());
            pst.setString(2, nv.getSoDT());
            pst.setDate(3,nv.getNgaysinh());
            pst.setNString(4,nv.getDiachi());
            pst.setString(5,nv.getSoCMND());
            pst.executeUpdate();
            conn.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
