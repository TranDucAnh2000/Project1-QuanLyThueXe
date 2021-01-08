package controllers.xe;

import controllers.nhanvien.SuaNVController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.KhachHangModel;
import models.NhanVienModel;
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
import service.NhanVienService;
import service.XeService;
import views.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class XeController implements  Initializable{

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
    private TableColumn<XeModel, Integer> col_TinhTrang;

    @FXML
    private TableColumn<XeModel, String> col_BienSoXe;

    @FXML
    private TextField textTimKiem;

    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Mã Xe", "Loại Xe", "Biển số xe",
            "Tên xe");
    private ObservableList<XeModel> tableOblist;

    @FXML
    private ComboBox<String> comboBox;
    String colSelected;
    XeService xeService=new XeService();
    private Stage themXeStage = new Stage();
    private Stage suaXeStage = new Stage();
    @FXML
    void comboBoxSelect(ActionEvent event) {
        String temp = comboBox.getSelectionModel().getSelectedItem().toString();
        switch (temp){
            case "Mã Xe": colSelected = "MaXE"; break;
            case "Tên xe": colSelected = "TenXe"; break;
            case "Loại Xe": colSelected = "LoaiXe"; break;
            case "Biển số xe": colSelected = "BienSoXe"; break;

        }
    }

    @FXML
    void sua(ActionEvent event) throws IOException {
        XeModel xeModel = tableXe.getSelectionModel().getSelectedItem();
        //canh bao
        if(xeModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 xe trước !");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("xe/SuaXe.fxml"));
            Parent parent = loader.load();

            SuaXeController suaXeController = loader.getController();
            suaXeController.initializeTextField(xeModel);

            suaXeStage.setScene(new Scene(parent));
            suaXeStage.setTitle("Sửa thông tin xe");
            suaXeStage.show();

            suaXeStage.setOnCloseRequest((e) -> {
                updateTable();
            });
        }
    }

    @FXML
    void them(ActionEvent event) throws IOException {
        themXeStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("xe/ThemXe.fxml"))));
        themXeStage.setTitle("Thêm xe mới");
        themXeStage.show();

        themXeStage.setOnCloseRequest((e)->{
            updateTable();
        });
    }

    @FXML
    void timKiem(ActionEvent event) {
        String key;
        tableXe.getItems().clear();
//        if(colSelected.equals("Mã Xe")){
//            key=String.valueOf(textTimKiem.getText());
//        }
//        else
            key = textTimKiem.getText();

        List<XeModel> list = xeService.searchListXe(colSelected, key, 0, 999999999);
        tableOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableOblist);
    }

    @FXML
    void xoa(ActionEvent event) {
        XeModel xeModel = tableXe.getSelectionModel().getSelectedItem();
        //canh bao
        if(xeModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 xe trước!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chắc muốn xóa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                int maXe = xeModel.getMaXe();
                xeService.deleteXe(maXe);
                tableXe.getItems().removeAll(tableXe.getSelectionModel().getSelectedItem());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(comboBoxOblist);
        col_MaXe.setCellValueFactory(new PropertyValueFactory<>("MaXe"));
        col_LoaiXe.setCellValueFactory(new PropertyValueFactory<>("LoaiXe"));
        col_TenXe.setCellValueFactory(new PropertyValueFactory<>("tenXe"));
        col_GiaThue.setCellValueFactory(new PropertyValueFactory<>("giaThue"));
        col_MauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        col_TinhTrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        col_BienSoXe.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));
        List<XeModel> list = xeService.getlistXe();
        tableOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableOblist);
    }

    private void updateTable(){
        col_MaXe.setCellValueFactory(new PropertyValueFactory<>("MaXe"));
        col_LoaiXe.setCellValueFactory(new PropertyValueFactory<>("LoaiXe"));
        col_TenXe.setCellValueFactory(new PropertyValueFactory<>("tenXe"));
        col_GiaThue.setCellValueFactory(new PropertyValueFactory<>("giaThue"));
        col_MauSac.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        col_TinhTrang.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
        col_BienSoXe.setCellValueFactory(new PropertyValueFactory<>("bienSoXe"));
        List<XeModel> list = xeService.getlistXe();
        tableOblist = FXCollections.observableArrayList(list);
        tableXe.setItems(tableOblist);
    }
    @FXML
    private void openfile(){
        FileChooser filechooser =new FileChooser();
        filechooser.setTitle("Open file Excel");
        //filechooser.setInitialDirectory(new File(System.getProperty("C:")));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"),new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File a= filechooser.showOpenDialog(null);
        if(a!=null){
            try {
                XSSFWorkbook workbook= openfileexcel(a.getAbsolutePath());
                List<XeModel> lsxe=getlistxefromexcel(workbook);
                for(int i=0;i<lsxe.size();i++){
                    xeService.addListXe(lsxe.get(i));
                }
                updateTable();
            } catch (IOException e) {
                e.printStackTrace();
                updateTable();
            }
        }
    }

    @FXML
    private void outputfile(){
        FileChooser filechooser =new FileChooser();
        filechooser.setTitle("Output file Excel");
        //filechooser.setInitialDirectory(new File(System.getProperty("C:")));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"),new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File a= filechooser.showSaveDialog(null);
        //File temp=new File( a.getf);

        try {
            FileOutputStream fileop=new FileOutputStream(a);
            XSSFWorkbook wb=writeworkxe();
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

    public XSSFWorkbook writeworkxe() {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("Danh sách xe");
        XSSFRow row;
        int countrow=tableXe.getItems().size();


        row=sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell cell1=row.createCell(2);
        cell1.setCellValue("Danh Sách Xe CỦA CỬA HÀNG - HUST");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,8));

        row=sheet.createRow(3);
        cell1=row.createCell(2);
        cell1.setCellValue("    Danh sách xe tại cửa hàng  ");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(3,3,2,8));

        int rowid=5;

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
    private static CellStyle createStyleForHeader(XSSFSheet sheet) {
        XSSFFont font = sheet.getWorkbook().createFont();
        ((XSSFFont) font).setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    public XSSFWorkbook openfileexcel(String excelfilepath) throws IOException {
        FileInputStream fileip=new FileInputStream(new File(excelfilepath));
        XSSFWorkbook workbook=new XSSFWorkbook(fileip);

        return workbook;
    }
    public List<XeModel> getlistxefromexcel(XSSFWorkbook wb){
        XSSFRow row;
        List<XeModel> listS=new ArrayList<>();
        XSSFSheet sheet=wb.getSheetAt(0);
        Iterator<Row> iteratorrow=sheet.iterator();
//        iteratorrow.next();
        //bor qua hang dau tien chua header cua file excel
        while(iteratorrow.hasNext()) {
            row=(XSSFRow) iteratorrow.next();
            //if(row.getCell(0).getStringCellValue()==null) break;
            if(row.getRowNum()==0) continue;
            Iterator <org.apache.poi.ss.usermodel.Cell> iteratorcell=row.iterator();
            XeModel s=new XeModel();
            while(iteratorcell.hasNext()) {
                Cell cell=iteratorcell.next();
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        s.setLoaiXe(cell.getStringCellValue());
                        System.out.println("loi khi get ten excel");
                        break;
                    case 1:
                        s.setTenXe(cell.getStringCellValue());
                        System.out.println("loi khi get sdt excel");
                        break;
                    case 2:
                        s.setGiaThue((int) cell.getNumericCellValue());
                        System.out.println("loi khi get soCMND excel");
                        break;
                    case 3:
                        s.setMauSac(cell.getStringCellValue());
                        System.out.println("loi khi get dia chi excel");
                        break;
                    case 4:
                        s.setTinhTrang(cell.getBooleanCellValue());
                        break;
                    case 5:
                        s.setBienSoXe(cell.getStringCellValue());
                        break;
                }
            }
            listS.add(s);

        }

        return listS;
    }

}
