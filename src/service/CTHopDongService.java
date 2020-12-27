package service;

import models.CTHopDongModel;
import models.HopDongModel;
import models.NhanVienModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CTHopDongService {

    public static int maHD;

    public List<CTHopDongModel> getListCTHD() {
        List<CTHopDongModel> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = DBConnection.getData("select * from ChitietHopDong\n"+
                    "where MaHD = "+maHD, conn);

            while (rs.next()) {
                CTHopDongModel ctHopDongModel = new CTHopDongModel();
                ctHopDongModel.setMaHD(rs.getInt("MaHD"));
                ctHopDongModel.setMaXe(rs.getInt("MaXe"));
                ctHopDongModel.setTienPhat(rs.getInt("TienPhat"));
                ctHopDongModel.setNgayTra(rs.getDate("NgayTra"));
                ctHopDongModel.setSuCo(rs.getString("SuCo"));
                list.add(ctHopDongModel);
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    public void addListCTHD(CTHopDongModel ctHopDongModel) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "Insert into ChitietHopDong(MaHD, MaXe, TienPhat, NgayTra, SuCo) Values(?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, ctHopDongModel.getMaHD());
        pst.setInt(2, ctHopDongModel.getMaXe());
        pst.setInt(3, ctHopDongModel.getTienPhat());
        pst.setDate(4, ctHopDongModel.getNgayTra());
        pst.setString(5, ctHopDongModel.getSuCo());

        pst.executeUpdate();

        conn.close();
        pst.close();
    }

    public void deleteListCTHD(int maXe) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Delete from ChitietHopDong where MaHD = "+maHD+" and MaXe = "+maXe;
        Statement pst = conn.createStatement();
        pst.execute(sql);

        conn.close();
        pst.close();
    }

}
