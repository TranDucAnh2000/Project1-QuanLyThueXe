package service;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import models.CTHopDongModel;
import models.HopDongModel;
import models.NhanVienModel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import views.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CTHopDongService {

    public static int maHD;

    public List<CTHopDongModel> getListCTHD() {
        List<CTHopDongModel> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = DBConnection.getData("select * from ChitietHopDong where MaHD = "+maHD, conn);

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

    public int addListCTHD(CTHopDongModel ctHopDongModel)  {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
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
        return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm không thành công,Nhập lại dữ liệu");
            alert.showAndWait();
            return 0;
        }
    }

    public void deleteListCTHD(CTHopDongModel ctHopDongModel) throws SQLException {
        Connection conn = service.DBConnection.getConnection();
        String sql = "Delete from ChitietHopDong where MaHD = "+ctHopDongModel.getMaHD()+" and MaXe = "+ctHopDongModel.getMaXe();
        Statement pst = conn.createStatement();
        pst.execute(sql);

        conn.close();
        pst.close();
    }

    public void exportExcelHopDong(int maHD) throws SQLException, IOException {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Connection conn = DBConnection.getConnection();
        String sql1 = "select * from HopDong where MaHD = "+maHD;
        PreparedStatement pst1 = conn.prepareStatement(sql1);
        ResultSet rs1 = pst1.executeQuery();

        String sql2 = "select * from ChitietHopDong where MaHD = "+maHD;
        PreparedStatement pst2 = conn.prepareStatement(sql2);
        ResultSet rs2 = pst2.executeQuery();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("HopDong details");
        XSSFRow tenBang = sheet.createRow(0);
        tenBang.createCell(0).setCellValue("Hợp đồng");
        XSSFRow taiKhoanThem = sheet.createRow(1);
        taiKhoanThem.createCell(0).setCellValue("Người dùng");
        taiKhoanThem.createCell(1).setCellValue(Main.TDN);
        XSSFRow maNV = sheet.createRow(2);
        maNV.createCell(0).setCellValue("Mã nhân viên");
        maNV.createCell(1).setCellValue(Main.maNV);
        XSSFRow thoiGian = sheet.createRow(3);
        thoiGian.createCell(0).setCellValue("Thời gian xuất");
        thoiGian.createCell(1).setCellValue(time.format(now));

        //Hop dong
        XSSFRow header = sheet.createRow(5);
        header.createCell(0).setCellValue("MaKH");
        header.createCell(1).setCellValue("MaHD");
        header.createCell(2).setCellValue("MaNV");
        header.createCell(3).setCellValue("NgayThue");
        header.createCell(4).setCellValue("NgayHenTra");
        header.createCell(5).setCellValue("TienCoc");
        header.createCell(6).setCellValue("TienThanhToan");

        XSSFRow hopDong = sheet.createRow(6);
        while(rs1.next()) {
            hopDong.createCell(0).setCellValue(rs1.getInt("MaKH"));
            hopDong.createCell(1).setCellValue(rs1.getInt("MaHD"));
            hopDong.createCell(2).setCellValue(rs1.getInt("MaNV"));
            hopDong.createCell(3).setCellValue(rs1.getDate("NgayThue"));
            hopDong.createCell(4).setCellValue(rs1.getDate("NgayHenTra"));
            hopDong.createCell(5).setCellValue(rs1.getInt("TienCoc"));
            hopDong.createCell(6).setCellValue(rs1.getInt("TienThanhToan"));
        }

        //Chi tiet hop dong
        XSSFRow header2 = sheet.createRow(8);
        header2.createCell(0).setCellValue("MaHD");
        header2.createCell(1).setCellValue("MaXe");
        header2.createCell(2).setCellValue("TienPhat");
        header2.createCell(3).setCellValue("NgayTra");
        header2.createCell(4).setCellValue("SuCo");

        int index = 9;
        while(rs2.next()){
            XSSFRow row = sheet.createRow(index);
            row.createCell(0).setCellValue(rs2.getInt("MaHD"));
            row.createCell(1).setCellValue(rs2.getInt("MaXe"));
            row.createCell(2).setCellValue(rs2.getInt("TienPhat"));
            row.createCell(3).setCellValue(rs2.getDate("NgayTra"));
            row.createCell(4).setCellValue(rs2.getString("SuCo"));
            index++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        try {
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Output file Excel");
            filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX", "*.xlsx"), new FileChooser.ExtensionFilter("All", "*.*"));
            File fileURL = filechooser.showSaveDialog(null);

            FileOutputStream fileOut = new FileOutputStream(fileURL);
            wb.write(fileOut);
            fileOut.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Ða~ xuâ´t file Excel tha`nh công");
            alert.setContentText(null);
            alert.showAndWait();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Xuâ´t file thâ´t ba?i!");
            alert.setContentText(null);
            alert.showAndWait();
        }

        pst1.close();
        pst2.close();
        rs1.close();
        rs2.close();

    }

}
