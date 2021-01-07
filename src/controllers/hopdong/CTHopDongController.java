package controllers.hopdong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import models.CTHopDongModel;
import models.HopDongModel;
import models.KhachHangModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.CTHopDongService;
import service.NhanVienService;
import views.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class CTHopDongController implements Initializable {

    @FXML
    private TableView<CTHopDongModel> tableCTHopDong;

    @FXML
    private TableColumn<CTHopDongModel, Integer> col_MaHD;

    @FXML
    private TableColumn<CTHopDongModel, Integer> col_MaXe;

    @FXML
    private TableColumn<CTHopDongModel, Integer> col_TienPhat;

    @FXML
    private TableColumn<CTHopDongModel, Date> col_NgayTra;

    @FXML
    private TableColumn<CTHopDongModel, String> col_SuCo;

    @FXML
    private TextField textMaXe;

    @FXML
    private TextField textTienPhat;

    @FXML
    private TextField textNgayTra;

    @FXML
    private TextField textSuCo;

    @FXML
    private TextField textMaHD;

    private CTHopDongService ctHopDongService = new CTHopDongService();

    private ObservableList<CTHopDongModel> tableOblist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_MaHD.setCellValueFactory(new PropertyValueFactory<>("MaHD"));
        col_MaXe.setCellValueFactory(new PropertyValueFactory<>("MaXe"));
        col_TienPhat.setCellValueFactory(new PropertyValueFactory<>("TienPhat"));
        col_NgayTra.setCellValueFactory(new PropertyValueFactory<>("NgayTra"));
        col_SuCo.setCellValueFactory(new PropertyValueFactory<>("SuCo"));
        List<CTHopDongModel> list = ctHopDongService.getListCTHD();
        tableOblist = FXCollections.observableArrayList(list);
        tableCTHopDong.setItems(tableOblist);
        //MaHD.setEditable(false);
        textMaHD.setEditable(false);
        textMaHD.setText(String.valueOf(ctHopDongService.maHD));

    }

    @FXML
    void them(ActionEvent event) throws SQLException {
        try {
            CTHopDongModel ctHopDongModel = new CTHopDongModel();
            ctHopDongModel.setMaHD(ctHopDongService.maHD);
            if (!(textMaXe.getText().isEmpty()))
                ctHopDongModel.setMaXe(Integer.valueOf(textMaXe.getText()));
            else ctHopDongModel.setMaXe(Integer.parseInt(null));
            if (!textTienPhat.getText().trim().isEmpty())
                ctHopDongModel.setTienPhat(Integer.parseInt(textTienPhat.getText()));
//        else ctHopDongModel.setTienPhat(Integer.parseInt(null));
            ctHopDongModel.setSuCo(textSuCo.getText());
            if (!textNgayTra.getText().trim().isEmpty())
                ctHopDongModel.setNgayTra(Date.valueOf(textNgayTra.getText()));
            int a = ctHopDongService.addListCTHD(ctHopDongModel);
            if (a == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Thêm thành công!");
                alert.showAndWait();
            }
            updateTable();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm thất bại!");
            alert.showAndWait();
        }
    }

    @FXML
    void xoa(ActionEvent event) throws SQLException {
        CTHopDongModel ctHopDongModel = tableCTHopDong.getSelectionModel().getSelectedItem();
        if(ctHopDongModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 hàng cần xóa trước!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chắc muốn xóa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                ctHopDongService.deleteListCTHD(ctHopDongModel);
                tableCTHopDong.getItems().removeAll(tableCTHopDong.getSelectionModel().getSelectedItem());
            }
            updateTable();
        }
    }

    @FXML
    void traxe(ActionEvent event) {
        CTHopDongModel ctHopDongModel = tableCTHopDong.getSelectionModel().getSelectedItem();
        if(ctHopDongModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn xe cần trả trước!");
            alert.showAndWait();
        }
        else {
            LocalDate l=LocalDate.now();
            try {
                java.util.Date ngaytra=new SimpleDateFormat("yyyy-MM-dd").parse(l.toString());
                java.util.Date datehentra=new SimpleDateFormat("yyyy-MM-dd").parse(ctHopDongService.getngayhentra(ctHopDongModel.getMaHD()).toString());
                long timeinmiliseconds=ngaytra.getTime()-datehentra.getTime();
                long day=timeinmiliseconds/(1000*60*60*24);
                if(day<=0) day=0;
                int tienphat=(int)day*100000;
                ctHopDongModel.setTienPhat(tienphat);
                String nt=new SimpleDateFormat("yyyy-MM-dd").format(ngaytra);
                ctHopDongModel.setNgayTra(java.sql.Date.valueOf(nt));
                int a= ctHopDongService.traxe(ctHopDongModel);
                if(a==1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Trả xe thành công!");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Trả xe thất bại !");
                    alert.showAndWait();
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Trả xe thất bại !");
                alert.showAndWait();
            }

            updateTable();
        }
    }

    @FXML
    void xuatFile(ActionEvent event) {
        FileChooser filechooser =new FileChooser();
        filechooser.setTitle("Output file Excel");
        //filechooser.setInitialDirectory(new File(System.getProperty("C:")));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"),new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File a= filechooser.showSaveDialog(null);
        //File temp=new File( a.getf);

        try {
            FileOutputStream fileop=new FileOutputStream(a);
            XSSFWorkbook wb=outputhopdong();
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
    public XSSFWorkbook outputhopdong() throws SQLException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Hợp Đồng Thuê Xe");
        XSSFRow row;
//        writeHeader(sheet, 0);
        row=sheet.createRow(0);
        Cell cell1=row.createCell(2);
        cell1.setCellValue("HỢP ĐỒNG THUÊ XE");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,6));
        ResultSet resultSet=ctHopDongService.getthongtinhopdong(HopDongController.hopDongModel1);
        resultSet.next();
        try {
            row=sheet.createRow(2);
            cell1=row.createCell(2);
            cell1.setCellValue("Mã Hợp Đồng Thuê Xe : " +resultSet.getInt(1));
            sheet.addMergedRegion(new CellRangeAddress(2,2,2,6));
            row=sheet.createRow(3);
            cell1=row.createCell(2);
            cell1.setCellValue("Tên Khách Hàng Thuê Xe : " +resultSet.getNString(2));
            sheet.addMergedRegion(new CellRangeAddress(3,3,2,6));
            row=sheet.createRow(4);
            cell1=row.createCell(2);
            cell1.setCellValue("Tên Nhân Viên Tạo Hợp Đồng : " +resultSet.getNString(3));
            sheet.addMergedRegion(new CellRangeAddress(4,4,2,6));
            row=sheet.createRow(5);
            cell1=row.createCell(2);
            cell1.setCellValue("Ngày bắt đầu thuê  : " +resultSet.getDate(4));
            sheet.addMergedRegion(new CellRangeAddress(5,5,2,6));
            row=sheet.createRow(6);
            cell1=row.createCell(2);
            cell1.setCellValue("Ngày hẹn trả xe  : " +resultSet.getDate(5));
            sheet.addMergedRegion(new CellRangeAddress(6,6,2,6));
            row=sheet.createRow(7);
            cell1=row.createCell(2);
            cell1.setCellValue("Tiền Đặt Cọc : " +resultSet.getInt(6)+" VND");
            sheet.addMergedRegion(new CellRangeAddress(7,7,2,6));
            row=sheet.createRow(9);
            cell1=row.createCell(2);
            cell1.setCellValue("    Danh sách các xe được thuê  ");
            sheet.addMergedRegion(new CellRangeAddress(8,8,2,6));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //int size = khachHangModels.size();
        int rowid = 10;
//        for (int i = 0; i < size; i++) {
//            KhachHangModel ex = khachHangModels.get(i);
//            row = sheet.createRow(rowid);
//            for (int j = 0; j < 5; j++) {
//                Cell cell = row.createCell(j);
////			"Mã sách","Tên sách","Tác giả","Năm xuất bản","Nhà xuất bản","Đơn giá","Tình trạng","Giới thiệu"
//                switch (j) {
//                    case 0: {
//
//                        cell.setCellValue(ex.getMaKH());
//                        break;
//                    }
//                    case 1: {
//
//                        cell.setCellValue(ex.getTenKH());
//                        break;
//                    }
//                    case 2: {
//
//                        cell.setCellValue(ex.getSoDT());
//                        break;
//                    }
//                    case 3: {
//
//                        cell.setCellValue(ex.getSoCMT());
//                        break;
//                    }
//                    case 4: {
//
//                        cell.setCellValue(ex.getDiaChi());
//                        break;
//                    }
//                }
//            }
//            rowid++;
//        }
        ResultSet res=ctHopDongService.getthongtinchitiethopdong(HopDongController.hopDongModel1);
        CellStyle cellStyle=createStyleForHeader(sheet);
        row=sheet.createRow(rowid);
        cell1=row.createCell(2);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Mã xe");
        cell1=row.createCell(3);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Tên xe");
        cell1=row.createCell(4);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Màu sắc xe");
        cell1=row.createCell(5);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Biển số xe");
        rowid++;
        while(res.next()){
            row=sheet.createRow(rowid);
            for(int i=2;i<6;i++){
                Cell cell=row.createCell(i);
                switch (i){
                    case 2:
                        cell.setCellValue(res.getInt(1));
                        break;
                    case 3:
                        cell.setCellValue(res.getNString(2));
                        break;
                    case 4:
                        cell.setCellValue(res.getNString(3));
                        break;
                    case 5:
                        cell.setCellValue(res.getString(4));
                        break;
                }
            }
            rowid++;
        }
        rowid++;
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        row=sheet.createRow(rowid);
        cell1=row.createCell(4);
        cell1.setCellValue("Thời gian tạo hợp đồng : "+localTime.getHour()+":"+localTime.getMinute()+" ngày "+localDate.toString());
        rowid++;
        row=sheet.createRow(rowid);
        cell1=row.createCell(5);
        cell1.setCellValue("Chữ ký nhân viên ");
        return workbook;
    }
        public void updateTable(){
            col_MaHD.setCellValueFactory(new PropertyValueFactory<>("MaHD"));
            col_MaXe.setCellValueFactory(new PropertyValueFactory<>("MaXe"));
            col_TienPhat.setCellValueFactory(new PropertyValueFactory<>("TienPhat"));
            col_NgayTra.setCellValueFactory(new PropertyValueFactory<>("NgayTra"));
            col_SuCo.setCellValueFactory(new PropertyValueFactory<>("SuCo"));
            List<CTHopDongModel> list = ctHopDongService.getListCTHD();
            tableOblist = FXCollections.observableArrayList(list);
            tableCTHopDong.setItems(tableOblist);
        }
    private static void autosizeColumn(XSSFSheet sheet, int lastColumn) {
        for (int columnIndex = 2; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
    private static CellStyle createStyleForHeader(XSSFSheet sheet) {
        // Create font
        XSSFFont font = sheet.getWorkbook().createFont();
        ((XSSFFont) font).setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12); // font size
        //font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        //cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        //cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

}
