package service;

import models.ThongKeKhachHangModel;
import models.XeModel;

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

    public List<ThongKeKhachHangModel> getlistKH()  {
        List<ThongKeKhachHangModel> lsTKKH = new ArrayList<>();
        String sql="select KhachHang.MaKH, TenKH, SoHopDong.SoHopDong, COUNT(MaXe) as SoXeDaThue, SoXeDaTra.SoXeDaTra,\n" +
                "\t\tSUM(TienPhat) as TongTienPhat, DaThanhToan.DaThanhToan\n" +
                "from KhachHang, HopDong, ChitietHopDong, (select KhachHang.MaKH, COUNT(MaHD) as SoHopDong\n" +
                "\t\t\t\t\t\t\t\t\t\t\tfrom HopDong, KhachHang\n" +
                "\t\t\t\t\t\t\t\t\t\t\twhere HopDong.MaKH = KhachHang.MaKH\n" +
                "\t\t\t\t\t\t\t\t\t\t\tgroup by KhachHang.MaKH) SoHopDong\n" +
                "\t, (select KhachHang.MaKH, SUM(TienThanhToan) as DaThanhToan\n" +
                "\t\tfrom HopDong, KhachHang\n" +
                "\t\twhere HopDong.MaKH = KhachHang.MaKH\n" +
                "\t\tgroup by KhachHang.MaKH) DaThanhToan\n" +
                "\t, (select KhachHang.MaKH, count(MaXe) as SoXeDaTra\n" +
                "\t\tfrom HopDong, KhachHang, ChitietHopDong\n" +
                "\t\twhere HopDong.MaKH = KhachHang.MaKH and HopDong.MaHD = ChitietHopDong.MaHD\n" +
                "\t\t\tand NgayTra is not NULL\n" +
                "\t\tgroup by KhachHang.MaKH) SoXeDaTra \n" +
                "where KhachHang.MaKH = HopDong.MaKH and HopDong.MaHD = ChitietHopDong.MaHD and KhachHang.MaKH = SoHopDong.MaKH and KhachHang.MaKH = DaThanhToan.MaKH\n" +
                "\tand KhachHang.MaKH = SoXeDaTra.MaKH\n" +
                "group by KhachHang.MaKH, TenKH, SoHopDong.SoHopDong, DaThanhToan.DaThanhToan, SoXeDaTra.SoXeDaTra;";

        try {
            Connection connection = DBConnection.getConnection();

            ResultSet res=DBConnection.getData(sql,connection);
            while (res.next()){
                ThongKeKhachHangModel thongKeKhachHangModel = new ThongKeKhachHangModel();
                thongKeKhachHangModel.setMaKH(res.getInt("MaKH"));
                thongKeKhachHangModel.setTenKH(res.getString("TenKH"));
                thongKeKhachHangModel.setSoHD(res.getInt("SoHopDong"));
                thongKeKhachHangModel.setSoXeDaThue(res.getInt("SoXeDaThue"));
                thongKeKhachHangModel.setSoXeDaTra(res.getInt("SoXeDaTra"));
                thongKeKhachHangModel.setTongTienPhat(res.getInt("TongTienPhat"));
                thongKeKhachHangModel.setDaThanhToan(res.getInt("DaThanhToan"));
                lsTKKH.add(thongKeKhachHangModel);
            }
            connection.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lsTKKH;

    }


}