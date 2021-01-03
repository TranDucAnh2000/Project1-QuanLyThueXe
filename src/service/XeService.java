package service;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import models.KhachHangModel;
import models.XeModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import views.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<XeModel> searchListXe(String colSelected, String key) {
        List<XeModel> list = new ArrayList<>();
        try {

            Connection conn = service.DBConnection.getConnection();
            ResultSet res = service.DBConnection.getData("select * from Xe where " + colSelected + " LIKE N'%" + key + "%'", conn);

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
        pst.setString(1, xe.getLoaiXe());
        pst.setString(2, xe.getTenXe());
        pst.setInt(3,xe.getGiaThue());
        pst.setString(4, xe.getMauSac());
        pst.setBoolean(5,xe.getTinhTrang());
        pst.setString(6, xe.getBienSoXe());
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
        pst.setString(1, xe.getLoaiXe());
        pst.setString(2, xe.getTenXe());
        pst.setInt(3, xe.getGiaThue());
        pst.setString(4, xe.getMauSac());
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

    public void exportExcelXe() throws SQLException, IOException {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Connection conn = DBConnection.getConnection();
        String sql = "select * from Xe";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Xe details");
        XSSFRow tenBang = sheet.createRow(0);
        tenBang.createCell(0).setCellValue("Tên bảng");
        tenBang.createCell(1).setCellValue("Xe");
        XSSFRow taiKhoanThem = sheet.createRow(1);
        taiKhoanThem.createCell(0).setCellValue("Người dùng");
        taiKhoanThem.createCell(1).setCellValue(Main.TDN);
        XSSFRow maNV = sheet.createRow(2);
        maNV.createCell(0).setCellValue("Mã nhân viên");
        maNV.createCell(1).setCellValue(Main.maNV);
        XSSFRow thoiGian = sheet.createRow(3);
        thoiGian.createCell(0).setCellValue("Thời gian xuất");
        thoiGian.createCell(1).setCellValue(time.format(now));

        XSSFRow header = sheet.createRow(5);
        header.createCell(0).setCellValue("MaXE");
        header.createCell(1).setCellValue("LoaiXe");
        header.createCell(2).setCellValue("TenXe");
        header.createCell(3).setCellValue("GiaThue");
        header.createCell(4).setCellValue("MauSac");
        header.createCell(5).setCellValue("TinhTrang");
        header.createCell(6).setCellValue("BienSoXe");

        int index = 6;
        while(rs.next()){
            XSSFRow row = sheet.createRow(index);
            row.createCell(0).setCellValue(rs.getInt("MaXE"));
            row.createCell(1).setCellValue(rs.getString("LoaiXe"));
            row.createCell(2).setCellValue(rs.getString("TenXe"));
            row.createCell(3).setCellValue(rs.getInt("GiaThue"));
            row.createCell(4).setCellValue(rs.getString("MauSac"));
            row.createCell(5).setCellValue(rs.getBoolean("TinhTrang"));
            row.createCell(6).setCellValue(rs.getString("BienSoXe"));
            index++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
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

        pst.close();
        rs.close();

    }


    public void importExcelXe() throws SQLException, IOException {
        String sql = "Insert into Xe(MaXE, LoaiXe, TenXe, GiaThue, MauSac, TinhTrang, BienSoXe) values(?, ?, ?, ?, ?, ?, ?);";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        try {
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Open file Excel");
            filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX", "*.xlsx"), new FileChooser.ExtensionFilter("All", "*.*"));
            File fileURL = filechooser.showOpenDialog(null);

            FileInputStream fileIn = new FileInputStream(new File(fileURL.getAbsolutePath()));

            XSSFWorkbook wb = new XSSFWorkbook(fileIn);
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                pst.setInt(1, (int) row.getCell(0).getNumericCellValue());
                pst.setString(2, row.getCell(1).getStringCellValue());
                pst.setString(3, row.getCell(2).getStringCellValue());
                pst.setInt(4, (int) row.getCell(3).getNumericCellValue());
                pst.setString(5, row.getCell(4).getStringCellValue());
                pst.setBoolean(6, (boolean) row.getCell(5).getBooleanCellValue());
                pst.setString(7, row.getCell(6).getStringCellValue());
                pst.execute();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Nhâ?p du~ liê?u tu` file Excel tha`nh công");
            alert.showAndWait();

            wb.close();
            pst.close();
            fileIn.close();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Nhâ?p file thâ´t ba?i!");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

}
