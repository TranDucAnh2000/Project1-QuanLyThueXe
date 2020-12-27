package service;

import models.HopDongModel;

import java.sql.*;
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

    public void addListHopDong(HopDongModel hopDongModel) throws SQLException {
        Connection conn = DBConnection.getConnection();
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
    }

    public void deleteListHopDong(int maHD) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Delete from HopDong where MaHD = "+maHD+
                        "\nDelete from ChitietHopDong where MaHD = "+maHD;
        Statement pst = conn.createStatement();
        pst.execute(sql);

        conn.close();
        pst.close();
    }
}
