package service;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import models.NhanVienModel;
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

public class NhanVienService {
    public List<NhanVienModel> getlistNV()  {
        List<NhanVienModel> lsNV=new ArrayList<>();
        String sql="select * from NhanVien";

        try {
            Connection connection = DBConnection.getConnection();
            ResultSet res=DBConnection.getData(sql,connection);
            while (res.next()){
                NhanVienModel nv=new NhanVienModel();
                nv.setMaNV(res.getInt("MaNV"));
                nv.setTenNV(res.getNString("TenNV"));
                nv.setSoDT(res.getString("SoDT"));
                nv.setNgaysinh(res.getDate("NgaySinh"));
                nv.setDiachi(res.getNString("DiaChi"));
                nv.setSoCMT(res.getString("SoCMT"));
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
                nv.setMaNV(res.getInt("MaNV"));
                nv.setTenNV(res.getNString("TenNV"));
                nv.setSoDT(res.getString("SoDT"));
                nv.setNgaysinh(res.getDate("NgaySinh"));
                nv.setDiachi(res.getNString("DiaChi"));
                nv.setSoCMT(res.getString("SoCMT"));
                lsNV.add(nv);
            }
            conn.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("loi khi get list tim kiem nv");
        }
        return lsNV;
    }

    public int addListNV(NhanVienModel nv)  {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "Insert into NhanVien Values( ?, ?, ?, ?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setInt(1, khachHangModel.getMaKH());
            pst.setNString(1, nv.getTenNV());
            pst.setString(2, nv.getSoDT());
            pst.setDate(3,nv.getNgaysinh());
            pst.setNString(4, nv.getDiachi());
            pst.setString(5,nv.getSoCMT());
            pst.executeUpdate();
            conn.close();
            pst.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm không thành công,Nhập lại dữ liệu");
            alert.showAndWait();
            return  0;
        }

    }

    public void deleteNV(int maNV)  {
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

    public int editListNV(NhanVienModel nv)  {
        String sql = "Update NhanVien set TenNV = ?, SoDT = ?, NgaySinh = ?,DiaChi = ?,SoCMT=? where MaNV = ?";
        try {
            Connection  conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setNString(1, nv.getTenNV());
            pst.setString(2, nv.getSoDT());
            pst.setDate(3,nv.getNgaysinh());
            pst.setNString(4,nv.getDiachi());
            pst.setString(5,nv.getSoCMT());
            pst.setInt(6,nv.getMaNV());
            pst.executeUpdate();
            conn.close();
            pst.close();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm không thành công,Nhập lại dữ liệu");
            alert.showAndWait();

        }
        return 0;
    }

    public void exportExcelNV() throws SQLException, IOException {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Connection conn = DBConnection.getConnection();
        String sql = "select * from NhanVien";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("NhanVien details");
        XSSFRow tenBang = sheet.createRow(0);
        tenBang.createCell(0).setCellValue("Tên bảng");
        tenBang.createCell(1).setCellValue("Nhân viên");
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
        header.createCell(0).setCellValue("MaNV");
        header.createCell(1).setCellValue("TenNV");
        header.createCell(2).setCellValue("SoDT");
        header.createCell(3).setCellValue("NgaySinh");
        header.createCell(4).setCellValue("DiaChi");
        header.createCell(5).setCellValue("SoCMT");

        int index = 6;
        while(rs.next()){
            XSSFRow row = sheet.createRow(index);
            row.createCell(0).setCellValue(rs.getInt("MaNV"));
            row.createCell(1).setCellValue(rs.getString("TenNV"));
            row.createCell(2).setCellValue(rs.getString("SoDT"));
            row.createCell(3).setCellValue(rs.getDate("NgaySinh"));
            row.createCell(4).setCellValue(rs.getString("DiaChi"));
            row.createCell(5).setCellValue(rs.getString("SoCMT"));
            index++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
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


    public void importExcelNV() throws SQLException, IOException {
        String sql = "Insert into NhanVien(MaNV, TenNV, SoDT, NgaySinh, DiaChi, SoCMT) values(?, ?, ?, ?, ?, ?);";
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
                pst.setDate(4, (Date) row.getCell(3).getDateCellValue());
                pst.setString(5, row.getCell(4).getStringCellValue());
                pst.setString(6, row.getCell(5).getStringCellValue());
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
