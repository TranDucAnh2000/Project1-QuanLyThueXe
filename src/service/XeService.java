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
            xe.setLoaiXe(res.getString("LoaiXe"));
            xe.setGiaThue(res.getInt("GiaThue"));
            xe.setTinhTrang(res.getBoolean("TinhTrang"));
            xe.setMauSac(res.getString("MauSac"));
            xe.setTenXe(res.getString("TenXe"));
            lsXe.add(xe);
        }
        connection.close();
        res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lsXe;

    }
    public List<XeModel> searchListXe(String colSelected, String key, int giaMin, int giaMax) {
        List<XeModel> list = new ArrayList<>();
        try {

            Connection conn = service.DBConnection.getConnection();
            ResultSet res = service.DBConnection.getData("select * from Xe where " + colSelected + " LIKE N'%" + key + "%'\n" +
                    "and GiaThue >= "+giaMin+" and GiaThue <= "+giaMax, conn);

            while(res.next()){
                XeModel xe=new XeModel();
                xe.setMaXe(res.getInt("MaXE"));
                xe.setBienSoXe(res.getString("BienSoXe"));
                xe.setLoaiXe(res.getString("LoaiXe"));
                xe.setGiaThue(res.getInt("GiaThue"));
                xe.setTinhTrang(res.getBoolean("TinhTrang"));
                xe.setMauSac(res.getString("MauSac"));
                xe.setTenXe(res.getString("TenXe"));
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

    public int addListXe(XeModel xe)  {
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
        return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public int editListXe(XeModel xe)  {
        String sql = "Update Xe set LoaiXe = ?, TenXe = ?, GiaThue = ?,MauSac = ?,TinhTrang=?,BienSoXe=? where MaXE = ?";
        try {
            Connection  conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setNString(1, xe.getLoaiXe());
        pst.setNString(2, xe.getTenXe());
        pst.setInt(3, xe.getGiaThue());
        pst.setNString(4, xe.getMauSac());
        pst.setBoolean(5, xe.getTinhTrang());
        pst.setString(6,xe.getBienSoXe());
        pst.setInt(7,xe.getMaXe());
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
