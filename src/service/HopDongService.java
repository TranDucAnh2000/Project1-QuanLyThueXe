package service;

import models.HopDongModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HopDongService {
    public static List<HopDongModel> getListHopDong(){
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
            ex.setTiencoc(res.getInt("TienCoc"));
            ex.setTienThanhToan(res.getInt("TienThanhToan"));
            ex.setNgaythue(res.getDate("NgayThue"));
            ex.setNgaytra(res.getDate("NgayTra"));
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
}
