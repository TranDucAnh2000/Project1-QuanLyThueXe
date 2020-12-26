package service;

import models.KhachHangModel;
import models.XeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XeService {
    public List<XeModel> getlistXe()  {
        List<XeModel> lsXe=new ArrayList<>();
        String sql="select * from Xe";

        try {
            Connection connection = DBConnection.getConnection();

        ResultSet res=DBConnection.getData(sql,connection);
        while (res.next()){
            XeModel xe=new XeModel();
            xe.setMaXe(res.getInt("MaXE"));
            xe.setBienSoXe(res.getString("BienSoXe"));
            xe.setLoaiXe(res.getNString("LoaiXe"));
            xe.setGiaThue(res.getInt("GiaThue"));
            xe.setTinhTrang(res.getBoolean("TinhTrang"));
            xe.setMauSac(res.getNString("MauSac"));
            xe.setTenXe(res.getNString("TenXe"));
            lsXe.add(xe);
        }
        connection.close();
        res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lsXe;

    }
    public List<XeModel> searchListXe(String colSelected, String key) {
        List<XeModel> list = new ArrayList<>();
        try {

            Connection conn = service.DBConnection.getConnection();
            ResultSet res = service.DBConnection.getData("select * from Xe where " + colSelected + " LIKE " + "N'%" + key + "%'", conn);

            while(res.next()){
                XeModel xe=new XeModel();
                xe.setMaXe(res.getInt("MaXE"));
                xe.setBienSoXe(res.getString("BienSoXe"));
                xe.setLoaiXe(res.getNString("LoaiXe"));
                xe.setGiaThue(res.getInt("GiaThue"));
                xe.setTinhTrang(res.getBoolean("TinhTrang"));
                xe.setMauSac(res.getNString("MauSac"));
                xe.setTenXe(res.getNString("TenXe"));
                list.add(xe);
            }

            conn.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("loi khi get list tim kiem xe");
        }
        return list;
    }

    public void addListXe(XeModel xe)  {
        try {
            Connection conn = DBConnection.getConnection();
        String sql = "Insert into Xe Values( ?, ?, ?, ?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        //pst.setInt(1, khachHangModel.getMaKH());
        pst.setNString(1, xe.getLoaiXe());
        pst.setNString(2, xe.getTenXe());
        pst.setInt(3,xe.getGiaThue());
        pst.setString(4, xe.getMauSac());
        pst.setBoolean(5,xe.getTinhTrang());
        pst.setNString(6, xe.getBienSoXe());
        pst.executeUpdate();
        conn.close();
        pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteXe(int maxe)  {
        try {
            Connection  conn = DBConnection.getConnection();
        String sql = "Delete from Xe where MaXE = "+maxe;
        Statement pst = conn.createStatement();
        pst.execute(sql);

        conn.close();
        pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editListXe(XeModel xe)  {
        String sql = "Update KhachHang set LoaiXe = ?, TenXe = ?, GiaThue = ?,MauSac = ?,TinhTrang=?,BienSoXe=? where MaKH = ?";
        try {
            Connection  conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setNString(1, xe.getLoaiXe());
        pst.setString(2, xe.getTenXe());
        pst.setInt(3, xe.getGiaThue());
        pst.setNString(4, xe.getMauSac());
        pst.setBoolean(5, xe.getTinhTrang());
        pst.setString(6,xe.getBienSoXe());
        pst.executeUpdate();
        conn.close();
        pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
