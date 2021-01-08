package controllers.thongke;

import controllers.khachhang.KhachHangController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import models.KhachHangModel;
import models.ThongKeKhachHangModel;
import models.XeModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.KhachHangService;
import service.NhanVienService;
import service.ThongKeService;
import service.XeService;
import views.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {

    @FXML
    private ComboBox<String> tieuChiXe;

    @FXML
    private ComboBox<String> chiTietXe;

    @FXML
    private TextField soLuongXe;

    @FXML
    private TextField soLuongKH;

    @FXML
    private TextField soLuongNV;

    @FXML
    private TableView<XeModel> tableXe;

    @FXML
    private TableColumn<XeModel, Integer> col_MaXe;

    @FXML
    private TableColumn<XeModel, String> col_LoaiXe;

    @FXML
    private TableColumn<XeModel, String> col_TenXe;

    @FXML
    private TableColumn<XeModel, Integer> col_GiaThue;

    @FXML
    private TableColumn<XeModel, String> col_MauSac;

    @FXML
    private TableColumn<XeModel, Boolean> col_TinhTrang;

    @FXML
    private TableColumn<XeModel, String> col_BienSoXe;

    @FXML
    private TextField textGiaMin;

    @FXML
    private TextField textGiaMax;

    String stieuChiXe;

    private KhachHangService khachHangService = new KhachHangService();
    private ThongKeService thongKeService = new ThongKeService();
    private XeService xeService = new XeService();

    private ObservableList<String> tieuChiOblist = FXCollections.observableArrayList("LoaiXe", "TenXe", "MauSac", "TinhTrang");
    private ObservableList<String> chiTietOblist;
    private ObservableList<ThongKeKhachHangModel> tableKHOblist;
    private ObservableList<XeModel> tableXeOblist;

    @FXML
    private TableView<ThongKeKhachHangModel> tableKH;

    @FXML
    private TableColumn<ThongKeKhachHangModel, Integer> col_MaKH;

    @FXML
    private TableColumn<ThongKeKhachHangModel, String> col_TenKH;

    @FXML
    private TableColumn<ThongKeKhachHangModel, Integer> col_SoHD;

    @FXML
    private TableColumn<ThongKeKhachHangModel, Integer> col_SoXeDaThue;

    @FXML
    private TableColumn<ThongKeKhachHangModel, Integer> col_SoXeDaTra;

    @FXML
    private TableColumn<ThongKeKhachHangModel, Integer> col_TongTienPhat;

    @FXML
    private TableColumn<ThongKeKhachHangModel, Integer> col_DaThanhToan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        soLuongXe.setEditable(false);
        tieuChiXe.setItems(tieuChiOblist);

        try {
            soLuongKH.setText(String.valueOf(thongKeService.count("MaKH", "", "KhachHang")));
            soLuongKH.setEditable(false);
            soLuongNV.setText(String.valueOf(thongKeService.count("MaNV", "","NhanVien")));
            soLuongNV.setEditable(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Xe
        col_MaXe.setCellValueFactory(new PropertyValueFactory<>("maXe"));
        col_LoaiXe.setCellValueFactory(new PropertyValueFactory<>("loaiXe"));
        col_TenXe.setCellValueFactory(new PropertyValueFactory<>("tenXe"));
        col_GiaThue.setCellValueFactory(new PropertyValueFactory<>("giaThue"));
        col_MauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        col_TinhTrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        col_BienSoXe.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));
        List<XeModel> list = xeService.getlistXe();
        tableXeOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableXeOblist);

        //thong ke khach hang
        col_MaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        col_TenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        col_SoHD.setCellValueFactory(new PropertyValueFactory<>("soHD"));
        col_SoXeDaThue.setCellValueFactory(new PropertyValueFactory<>("soXeDaThue"));
        col_SoXeDaTra.setCellValueFactory(new PropertyValueFactory<>("soXeDaTra"));
        col_TongTienPhat.setCellValueFactory(new PropertyValueFactory<>("tongTienPhat"));
        col_DaThanhToan.setCellValueFactory(new PropertyValueFactory<>("daThanhToan"));
        List<ThongKeKhachHangModel> list1 = thongKeService.getlistKH();
        tableKHOblist = FXCollections.observableArrayList(list1);
        tableKH.setItems(tableKHOblist);

    }

    @FXML
    void chiTietXe(ActionEvent event) throws SQLException {
        String chiTiet;
        if(chiTietXe.getSelectionModel().getSelectedItem() == "Tất cả"){
            chiTiet = "";
        }else {
            chiTiet = chiTietXe.getSelectionModel().getSelectedItem();
        }

        int giaMin, giaMax;
        if(textGiaMin.getText().equals("")){
            giaMin = 0;
        }else{
            giaMin = Integer.valueOf(textGiaMin.getText());
        }
        if(textGiaMax.getText().equals("")){
            giaMax = 999999999;
        }else{
            giaMax = Integer.valueOf(textGiaMax.getText());
        }
        List<XeModel> list = xeService.searchListXe(stieuChiXe, chiTiet, giaMin, giaMax);
        tableXeOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableXeOblist);

        soLuongXe.setText(String.valueOf(tableXe.getItems().size()));
    }

    @FXML
    void tieuChiXe(ActionEvent event) throws SQLException {
        stieuChiXe = tieuChiXe.getSelectionModel().getSelectedItem();
        chiTietOblist = FXCollections.observableArrayList(thongKeService.getListComboBox(stieuChiXe));
        chiTietOblist.add("Tất cả");
        chiTietXe.setItems(chiTietOblist);
    }

    @FXML
    void xuatFileKH(ActionEvent event) {
        FileChooser filechooser =new FileChooser();
        filechooser.setTitle("Output file Excel");
        //filechooser.setInitialDirectory(new File(System.getProperty("C:")));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"),new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File a= filechooser.showSaveDialog(null);
        //File temp=new File( a.getf);

        try {
            FileOutputStream fileop=new FileOutputStream(a);
            XSSFWorkbook wb=writeworktkkhachhang();
            wb.write(fileop);
            fileop.close();
            //JOptionPane.showMessageDialog(null,"Xuất báo cáo thành công");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Xuất file thành công !");
            alert.showAndWait();


        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Xuất file không thành công");
            alert.showAndWait();
        }
    }

    @FXML
    void xuatFileXe(ActionEvent event) {
        FileChooser filechooser =new FileChooser();
        filechooser.setTitle("Output file Excel");
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"),new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File a= filechooser.showSaveDialog(null);

        try {
            FileOutputStream fileop=new FileOutputStream(a);
            XSSFWorkbook wb=writeworkxe();
            wb.write(fileop);
            fileop.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Xuất file thành công !");
            alert.showAndWait();


        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Xuất file không thành công");
            alert.showAndWait();
        }
    }



    public XSSFWorkbook writeworktkkhachhang() {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("Thống kê khách hàng");
        XSSFRow row;
        int countrow=tableKH.getItems().size();


        row=sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell cell1=row.createCell(2);
        cell1.setCellValue("THỐNG KÊ KHÁCH HÀNG CỦA CỬA HÀNG - HUST");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,8));

        row=sheet.createRow(3);
        cell1=row.createCell(2);
        cell1.setCellValue("    Danh sách các khách hàng tại cửa hàng  ");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(3,3,2,8));
        int rowid=5;

        CellStyle cellStyle=createStyleForHeader(sheet);

        row=sheet.createRow(rowid);
        cell1=row.createCell(2);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Mã khách hàng ");
        cell1=row.createCell(3);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Tên khách hàng");
        cell1=row.createCell(4);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Số hợp đồng");
        cell1=row.createCell(5);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Số xe đã thuê");
        cell1=row.createCell(6);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Số xe đã trả");
        cell1=row.createCell(7);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Tổng tiền phạt");
        cell1=row.createCell(8);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Đã thanh toán");


        rowid++;



        TableView.TableViewSelectionModel<ThongKeKhachHangModel> t= tableKH.getSelectionModel();

        ObservableList<ThongKeKhachHangModel>  thongKeKhachHangModels= tableKH.getItems();

        int size=thongKeKhachHangModels.size();

        for(int i=0;i<size;i++) {
            ThongKeKhachHangModel ex=thongKeKhachHangModels.get(i);
            row=sheet.createRow(rowid);
            for(int j=2;j<=8;j++) {
                Cell cell=row.createCell(j);
//			"Mã sách","Tên sách","Tác giả","Năm xuất bản","Nhà xuất bản","Đơn giá","Tình trạng","Giới thiệu"
                switch (j) {
                    case 2: {

                        cell.setCellValue(ex.getMaKH());
                        break;
                    }
                    case 3: {

                        cell.setCellValue(ex.getTenKH());
                        break;
                    }
                    case 4: {

                        cell.setCellValue(ex.getSoHD());
                        break;
                    }
                    case 5: {

                        cell.setCellValue(ex.getSoXeDaThue());
                        break;
                    }
                    case 6: {

                        cell.setCellValue(ex.getSoXeDaTra());
                        break;
                    }
                    case 7: {

                        cell.setCellValue(ex.getTongTienPhat());
                        break;
                    }
                    case 8: {

                        cell.setCellValue(ex.getDaThanhToan());
                        break;
                    }
                }
            }
            rowid++;
        }
        rowid+=2;
        row=sheet.createRow(rowid);
        cell1=row.createCell(5);
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        cell1.setCellValue("Hà Nội : "+localTime.getHour()+" giờ "+localTime.getMinute()+"phút"+" , "+localDate.toString());
        //cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(rowid,rowid,5,9));
        rowid++;
        row=sheet.createRow(rowid);
        cell1=row.createCell(5);
        cell1.setCellValue("Người xuất báo cáo : "+new NhanVienService().getthongtinNV(Main.maNV));
        //cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(rowid,rowid,5,9));
        rowid++;
        row=sheet.createRow(rowid);
        cell1=row.createCell(6);
        cell1.setCellValue("Chữ ký nhân viên ");
        //cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(rowid,rowid,6,8));

        return workbook;
    }
    private static CellStyle createStyleForHeader(XSSFSheet sheet) {
        XSSFFont font = sheet.getWorkbook().createFont();
        ((XSSFFont) font).setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    public XSSFWorkbook writeworkxe() {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("Thống kê xe");
        XSSFRow row;
        int countrow=tableXe.getItems().size();


        row=sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell cell1=row.createCell(2);
        cell1.setCellValue("THỐNG KÊ Xe CỦA CỬA HÀNG - HUST");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,8));

        row=sheet.createRow(3);
        cell1=row.createCell(2);
        cell1.setCellValue("    Danh sách xe tại cửa hàng  ");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(3,3,2,8));

        row=sheet.createRow(5);
        cell1=row.createCell(2);
        cell1.setCellValue("Tiêu chí thống kê: "+tieuChiXe.getSelectionModel().getSelectedItem());
        row=sheet.createRow(6);
        cell1=row.createCell(2);
        cell1.setCellValue("Chi tiết thống kê: "+chiTietXe.getSelectionModel().getSelectedItem());
        row=sheet.createRow(7);
        cell1=row.createCell(2);
        cell1.setCellValue("Giá min: "+textGiaMin.getText());
        row=sheet.createRow(8);
        cell1=row.createCell(2);
        cell1.setCellValue("Giá max: "+textGiaMax.getText());
        int rowid=10;

        CellStyle cellStyle=createStyleForHeader(sheet);

        row=sheet.createRow(rowid);
        cell1=row.createCell(2);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Mã Xe ");
        cell1=row.createCell(3);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Loại xe");
        cell1=row.createCell(4);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Tên xe");
        cell1=row.createCell(5);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Giá thuê");
        cell1=row.createCell(6);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Màu sắc");
        cell1=row.createCell(7);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Tình trạng");
        cell1=row.createCell(8);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Biển số xe");


        rowid++;



        TableView.TableViewSelectionModel<XeModel> t= tableXe.getSelectionModel();

        ObservableList<XeModel>  xeModels= tableXe.getItems();

        int size=xeModels.size();

        for(int i=0;i<size;i++) {
            XeModel ex=xeModels.get(i);
            row=sheet.createRow(rowid);
            for(int j=2;j<=8;j++) {
                Cell cell=row.createCell(j);
//			"Mã sách","Tên sách","Tác giả","Năm xuất bản","Nhà xuất bản","Đơn giá","Tình trạng","Giới thiệu"
                switch (j) {
                    case 2: {

                        cell.setCellValue(ex.getMaXe());
                        break;
                    }
                    case 3: {

                        cell.setCellValue(ex.getLoaiXe());
                        break;
                    }
                    case 4: {

                        cell.setCellValue(ex.getTenXe());
                        break;
                    }
                    case 5: {

                        cell.setCellValue(ex.getGiaThue());
                        break;
                    }
                    case 6: {

                        cell.setCellValue(ex.getMauSac());
                        break;
                    }
                    case 7: {

                        cell.setCellValue(ex.getTinhTrang());
                        break;
                    }
                    case 8: {

                        cell.setCellValue(ex.getBienSoXe());
                        break;
                    }
                }
            }
            rowid++;
        }
        rowid+=2;
        row=sheet.createRow(rowid);
        cell1=row.createCell(5);
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        cell1.setCellValue("Hà Nội : "+localTime.getHour()+" giờ "+localTime.getMinute()+"phút"+" , "+localDate.toString());
        //cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(rowid,rowid,5,9));
        rowid++;
        row=sheet.createRow(rowid);
        cell1=row.createCell(5);
        cell1.setCellValue("Người xuất báo cáo : "+new NhanVienService().getthongtinNV(Main.maNV));
        //cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(rowid,rowid,5,9));
        rowid++;
        row=sheet.createRow(rowid);
        cell1=row.createCell(6);
        cell1.setCellValue("Chữ ký nhân viên ");
        //cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(rowid,rowid,6,8));

        return workbook;
    }

}

