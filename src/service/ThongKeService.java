package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThongKeService {

    public List<String> getListComboBox(String tieuChi) throws SQLException {
        List<String> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String sql = "Select distinct " + tieuChi + " from Xe";
        ResultSet rs = DBConnection.getData(sql, conn);
        while (rs.next()) {
            String temp = rs.getString(tieuChi);
            list.add(temp);
        }
        conn.close();
        return list;
    }

    public int count(String tieuChi, String chiTiet, String bang) throws SQLException {
        int result = 0;
        Connection conn = DBConnection.getConnection();
        String sql = "Select count("+tieuChi+") as SoLuong\n"+
                "from "+bang+"\n"+
                "where "+tieuChi+" like N'%"+chiTiet+"%'";
        ResultSet rs = DBConnection.getData(sql, conn);
        while(rs.next()){
            result = rs.getInt("SoLuong");
        }

        conn.close();
        return  result;
    }

    public int countSoXeDaThue(int maKH) throws SQLException {
        int result = 0;
        Connection conn = DBConnection.getConnection();
        String sql = "select count(Xe.MaXE) as SoLuong\n" +
                "from KhachHang, HopDong, ChitietHopDong, Xe\n" +
                "where KhachHang.MaKH = HopDong.MaKH and HopDong.MaHD = ChitietHopDong.MaHD and  ChitietHopDong.MaXe = Xe.MaXE and KhachHang.MaKH = "+maKH+"\n" +
                "group by KhachHang.MaKH";
        ResultSet rs = DBConnection.getData(sql, conn);
        while(rs.next()){
            result = rs.getInt("SoLuong");
        }

        conn.close();
        return  result;
    }

    public int countTongTienThanhToan(int maKH) throws SQLException {
        int result = 0;
        Connection conn = DBConnection.getConnection();
        String sql = "select sum(TienThanhToan) as SoLuong\n" +
                "from KhachHang, HopDong\n" +
                "where KhachHang.MaKH = HopDong.MaKH and KhachHang.MaKH = "+maKH;
        ResultSet rs = DBConnection.getData(sql, conn);
        while(rs.next()){
            result = rs.getInt("SoLuong");
        }

        conn.close();
        return  result;
    }
}