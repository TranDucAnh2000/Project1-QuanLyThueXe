package controllers.nhanvien;

import controllers.khachhang.SuaKHController;
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
import views.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class NhanVienController implements Initializable {

    @FXML
    private TableView<NhanVienModel> tableNhanVien;

    @FXML
    private TableColumn<NhanVienModel, Integer> col_MaNV;

    @FXML
    private TableColumn<NhanVienModel, String> col_TenNV;

    @FXML
    private TableColumn<NhanVienModel, String> col_SDT;

    @FXML
    private TableColumn<NhanVienModel, Date> col_NgaySinh;

    @FXML
    private TableColumn<NhanVienModel, Integer> col_DiaChi;

    @FXML
    private TableColumn<NhanVienModel,String> col_SoCMT;

    @FXML
    private TextField textTimKiem;

    @FXML
    private ComboBox<String> comboBox;

    private Stage themNVStage = new Stage();
    private Stage suaNVStage = new Stage();
    String colSelected;
    //"Mã nhân viên", "Tên nhân viên", "Số điện thoại","Ngày sinh","Địa chỉ","Số CMT"
    @FXML
    void comboBoxSelect(ActionEvent event) {
        String temp = comboBox.getSelectionModel().getSelectedItem().toString();
        switch (temp){
            case "Mã nhân viên": colSelected = "MaNV"; break;
            case "Tên nhân viên": colSelected = "TenNV"; break;
            case "Số điện thoại": colSelected = "SoDT"; break;
            case "Số CMT": colSelected = "SoCMT"; break;
            case "Ngày sinh": colSelected = "NgaySinh"; break;
            case "Địa chỉ": colSelected = "DiaChi"; break;

        }
    }

    @FXML
    void sua(ActionEvent event) throws IOException {
        NhanVienModel nhanVienModel = tableNhanVien.getSelectionModel().getSelectedItem();
        //canh bao
        if(nhanVienModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 nhân viên trước !");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("nhanvien/SuaNV.fxml"));
            Parent parent = loader.load();

            SuaNVController suaNVController = loader.getController();
            suaNVController.initializeTextField(nhanVienModel);

            suaNVStage.setScene(new Scene(parent));
            suaNVStage.setTitle("Sửa thông tin khách hàng");
            suaNVStage.show();

            suaNVStage.setOnCloseRequest((e) -> {
                updateTable();
            });
        }
    }

    @FXML
    void them(ActionEvent event) throws IOException {
        themNVStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("nhanvien/ThemNV.fxml"))));
        themNVStage.setTitle("Thêm khách hàng");
        themNVStage.show();

        themNVStage.setOnCloseRequest((e)->{
            updateTable();
        });
    }

    @FXML
    void timKiem(ActionEvent event) {
        tableNhanVien.getItems().clear();
        String key = textTimKiem.getText();

        List<NhanVienModel> list = nhanVienService.searchListNV(colSelected, key);
        tableOblist = FXCollections.observableArrayList(list);
        tableNhanVien.setItems(tableOblist);
    }

    @FXML
    void xoa(ActionEvent event) {
        NhanVienModel nhanVienModel = tableNhanVien.getSelectionModel().getSelectedItem();
        //canh bao
        if(nhanVienModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 nhân viên trước ! ");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chắc muốn xóa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                int maNV = nhanVienModel.getMaNV();
                nhanVienService.deleteNV(maNV);
                tableNhanVien.getItems().removeAll(tableNhanVien.getSelectionModel().getSelectedItem());
            }
        }
    }
    private NhanVienService nhanVienService=new NhanVienService();
    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Mã nhân viên", "Tên nhân viên", "Số điện thoại","Ngày sinh","Địa chỉ","Số CMT");
    private ObservableList<NhanVienModel> tableOblist;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(comboBoxOblist);

        col_MaNV.setCellValueFactory(new PropertyValueFactory<>("MaNV"));
        col_TenNV.setCellValueFactory(new PropertyValueFactory<>("TenNV"));
        col_SDT.setCellValueFactory(new PropertyValueFactory<>("SoDT"));
        col_NgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaysinh"));
        col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        col_SoCMT.setCellValueFactory(new PropertyValueFactory<>("SoCMT"));
        List<NhanVienModel> list = nhanVienService.getlistNV();
        tableOblist = FXCollections.observableArrayList(list);
        tableNhanVien.setItems(tableOblist);
    }
    private void updateTable(){
        col_MaNV.setCellValueFactory(new PropertyValueFactory<>("MaNV"));
        col_TenNV.setCellValueFactory(new PropertyValueFactory<>("TenNV"));
        col_SDT.setCellValueFactory(new PropertyValueFactory<>("SoDT"));
        col_NgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaysinh"));
        col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        col_SoCMT.setCellValueFactory(new PropertyValueFactory<>("SoCMT"));
        List<NhanVienModel> list = nhanVienService.getlistNV();
        tableOblist = FXCollections.observableArrayList(list);
        tableNhanVien.setItems(tableOblist);
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
                List<NhanVienModel> lsnv=getlistnvfromexcel(workbook);
                for(int i=0;i<lsnv.size();i++){
                    nhanVienService.addListNV(lsnv.get(i));
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
            XSSFWorkbook wb=writeworknv();
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

    public XSSFWorkbook openfileexcel(String excelfilepath) throws IOException {
        FileInputStream fileip=new FileInputStream(new File(excelfilepath));
        XSSFWorkbook workbook=new XSSFWorkbook(fileip);

        return workbook;
    }
    public List<NhanVienModel> getlistnvfromexcel(XSSFWorkbook wb){
        XSSFRow row;
        List<NhanVienModel> listS=new ArrayList<>();
        XSSFSheet sheet=wb.getSheetAt(0);
        Iterator<Row> iteratorrow=sheet.iterator();
//        iteratorrow.next();
        //bor qua hang dau tien chua header cua file excel
        while(iteratorrow.hasNext()) {
            row=(XSSFRow) iteratorrow.next();
            //if(row.getCell(0).getStringCellValue()==null) break;
            if(row.getRowNum()==0) continue;
            Iterator <org.apache.poi.ss.usermodel.Cell> iteratorcell=row.iterator();
            NhanVienModel s=new NhanVienModel();
            while(iteratorcell.hasNext()) {
                Cell cell=iteratorcell.next();
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        s.setTenNV(cell.getStringCellValue());
                        System.out.println("loi khi get ten excel");
                        break;
                    case 1:
                        s.setSoDT(cell.getStringCellValue());
                        System.out.println("loi khi get sdt excel");
                        break;
                    case 2:
                        s.setNgaysinh((Date)cell.getDateCellValue());
                        System.out.println("loi khi get soCMND excel");
                        break;
                    case 3:
                        s.setDiachi(cell.getStringCellValue());
                        System.out.println("loi khi get dia chi excel");
                        break;
                    case 4:
                        s.setSoCMT(cell.getStringCellValue());
                        break;
                }
            }
            listS.add(s);

        }

        return listS;
    }

    public XSSFWorkbook writeworknv() {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("Danh sách nhân viên");
        XSSFRow row;
        int countrow=tableNhanVien.getItems().size();


        row=sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell cell1=row.createCell(2);
        cell1.setCellValue("DANH SÁCH NHÂN VIÊN CỦA CỬA HÀNG - HUST");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,8));

        row=sheet.createRow(3);
        cell1=row.createCell(2);
        cell1.setCellValue("    Danh sách nhân viên tại cửa hàng  ");
        cell1.setCellStyle(createStyleForHeader(sheet));
        sheet.addMergedRegion(new CellRangeAddress(3,3,2,8));

        int rowid=5;

        CellStyle cellStyle=createStyleForHeader(sheet);

        row=sheet.createRow(rowid);
        cell1=row.createCell(2);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Mã nhân viên ");
        cell1=row.createCell(3);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Tên nhân viên");
        cell1=row.createCell(4);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Số điện thoại");
        cell1=row.createCell(5);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Ngày sinh");
        cell1=row.createCell(6);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Địa chỉ");
        cell1=row.createCell(7);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Số CMT");


        rowid++;



        TableView.TableViewSelectionModel<NhanVienModel> t= tableNhanVien.getSelectionModel();

        ObservableList<NhanVienModel>  nhanVienModels= tableNhanVien.getItems();

        int size=nhanVienModels.size();

        for(int i=0;i<size;i++) {
            NhanVienModel ex=nhanVienModels.get(i);
            row=sheet.createRow(rowid);
            for(int j=2;j<=8;j++) {
                Cell cell=row.createCell(j);
//			"Mã sách","Tên sách","Tác giả","Năm xuất bản","Nhà xuất bản","Đơn giá","Tình trạng","Giới thiệu"
                switch (j) {
                    case 2: {

                        cell.setCellValue(ex.getMaNV());
                        break;
                    }
                    case 3: {

                        cell.setCellValue(ex.getTenNV());
                        break;
                    }
                    case 4: {

                        cell.setCellValue(ex.getSoDT());
                        break;
                    }
                    case 5: {

                        cell.setCellValue(ex.getNgaysinh());
                        break;
                    }
                    case 6: {

                        cell.setCellValue(ex.getDiachi());
                        break;
                    }
                    case 7: {

                        cell.setCellValue(ex.getSoCMT());
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
}
