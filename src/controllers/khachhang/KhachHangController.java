package controllers.khachhang;

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
import views.Main;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class KhachHangController implements Initializable {

    @FXML
    private TableView<KhachHangModel> tableKhachHang;

    @FXML
    private TableColumn<KhachHangModel, String> col_MaKH;

    @FXML
    private TableColumn<KhachHangModel, String> col_TenKH;

    @FXML
    private TableColumn<KhachHangModel, Integer> col_SDT;

    @FXML
    private TableColumn<KhachHangModel, Integer> col_SoCMT;

    @FXML
    private TableColumn<KhachHangModel, Integer> col_DiaChi;

    @FXML
    private TextField textTimKiem;

    @FXML
    private ComboBox<String> comboBox;

    String colSelected;

    private Stage themKHStage = new Stage();
    private Stage suaKHStage = new Stage();

    private KhachHangService khachHangService = new KhachHangService();

    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Mã khách hàng", "Tên khách hàng", "Số điện thoại",
                                                                                      "Số CMT", "Địa chỉ");
    private ObservableList<KhachHangModel> tableOblist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(comboBoxOblist);

        col_MaKH.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        col_TenKH.setCellValueFactory(new PropertyValueFactory<>("TenKH"));
        col_SDT.setCellValueFactory(new PropertyValueFactory<>("SoDT"));
        col_SoCMT.setCellValueFactory(new PropertyValueFactory<>("SoCMT"));
        col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));

        List<KhachHangModel> list = khachHangService.getListKhachHang();
        tableOblist = FXCollections.observableArrayList(list);
        tableKhachHang.setItems(tableOblist);
    }

    @FXML
    void comboBoxSelect(ActionEvent event) {
        String temp = comboBox.getSelectionModel().getSelectedItem().toString();
        switch (temp){
            case "Mã khách hàng": colSelected = "MaKH"; break;
            case "Tên khách hàng": colSelected = "TenKH"; break;
            case "Số điện thoại": colSelected = "SoDT"; break;
            case "Số CMT": colSelected = "SoCMT"; break;
            case "Địa chỉ": colSelected = "DiaChi"; break;
        }
    }

    @FXML
    void sua(ActionEvent event) throws IOException {
        KhachHangModel khachHangModel = tableKhachHang.getSelectionModel().getSelectedItem();
        //canh bao
        if(khachHangModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 khách hàng trước !");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("khachhang/SuaKH.fxml"));
            Parent parent = loader.load();

            SuaKHController suaKHController = loader.getController();
            suaKHController.initializeTextField(khachHangModel);

            suaKHStage.setScene(new Scene(parent));
            suaKHStage.setTitle("Sửa thông tin khách hàng");
            suaKHStage.show();

            suaKHStage.setOnCloseRequest((e) -> {
                updateTable();
            });
        }
    }

    @FXML
    void them(ActionEvent event) throws IOException {
        themKHStage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("khachhang/themKH.fxml"))));
        themKHStage.setTitle("Thêm khách hàng");
        themKHStage.show();

        themKHStage.setOnCloseRequest((e)->{
            updateTable();
        });
    }

    @FXML
    void timKiem(ActionEvent event) {
        tableKhachHang.getItems().clear();
        String key = textTimKiem.getText();

        List<KhachHangModel> list = khachHangService.searchListKhachHang(colSelected, key);
        tableOblist = FXCollections.observableArrayList(list);
        tableKhachHang.setItems(tableOblist);
    }

    @FXML
    void xoa(ActionEvent event) throws SQLException {
        KhachHangModel khachHangModel = tableKhachHang.getSelectionModel().getSelectedItem();
        //canh bao
        if(khachHangModel == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Chọn 1 khách hàng đi bạn!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chắc muốn xóa?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                int maKH = khachHangModel.getMaKH();
                khachHangService.deleteListKhachHang(maKH);
                tableKhachHang.getItems().removeAll(tableKhachHang.getSelectionModel().getSelectedItem());
            }
        }
    }

    private void updateTable(){
        col_MaKH.setCellValueFactory(new PropertyValueFactory<>("MaKH"));
        col_TenKH.setCellValueFactory(new PropertyValueFactory<>("TenKH"));
        col_SDT.setCellValueFactory(new PropertyValueFactory<>("SoDT"));
        col_SoCMT.setCellValueFactory(new PropertyValueFactory<>("SoCMT"));
        col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));

        List<KhachHangModel> list = khachHangService.getListKhachHang();
        tableOblist = FXCollections.observableArrayList(list);
        tableKhachHang.setItems(tableOblist);
    }

    @FXML
    void openfile(){
        FileChooser filechooser =new FileChooser();
        filechooser.setTitle("Open file Excel");
        //filechooser.setInitialDirectory(new File(System.getProperty("C:")));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"),new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File a= filechooser.showOpenDialog(null);
        if(a!=null){
            try {
                XSSFWorkbook workbook= openfileexcel(a.getAbsolutePath());
                List<KhachHangModel> lskhachhang=getlistkhachhangfromexcel(workbook);
                for(int i=0;i<lskhachhang.size();i++){
                    khachHangService.addListKhachHang(lskhachhang.get(i));
                }
                updateTable();
            } catch (IOException e) {
                e.printStackTrace();
                updateTable();
            }
        }


    }
    @FXML
    void outputfile(){
        FileChooser filechooser =new FileChooser();
        filechooser.setTitle("Output file Excel");
        //filechooser.setInitialDirectory(new File(System.getProperty("C:")));
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.*"),new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File a= filechooser.showSaveDialog(null);
        //File temp=new File( a.getf);

        try {
            FileOutputStream fileop=new FileOutputStream(a);
            XSSFWorkbook wb=writeworkkhachhang();
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
    public List<KhachHangModel> getlistkhachhangfromexcel(XSSFWorkbook wb){
        XSSFRow row;
        List<KhachHangModel> listS=new ArrayList<>();
        XSSFSheet sheet=wb.getSheetAt(0);
        Iterator<Row> iteratorrow=sheet.iterator();
//        iteratorrow.next();
        //bor qua hang dau tien chua header cua file excel
        while(iteratorrow.hasNext()) {
            row=(XSSFRow) iteratorrow.next();
            //if(row.getCell(0).getStringCellValue()==null) break;
            if(row.getRowNum()==0) continue;
            Iterator <org.apache.poi.ss.usermodel.Cell> iteratorcell=row.iterator();
            KhachHangModel s=new KhachHangModel();
            while(iteratorcell.hasNext()) {
                Cell cell=iteratorcell.next();

//			switch(cell.getCellType()) {
//			case String:
//			}
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        s.setTenKH(cell.getStringCellValue());
                        System.out.println("loi khi get ten excel");
                        break;
                    case 1:
                        s.setSoDT(cell.getStringCellValue());
                        System.out.println("loi khi get sdt excel");
                        break;
                    case 2:
                        s.setSoCMT(cell.getStringCellValue());
                        System.out.println("loi khi get soCMND excel");
                        break;

//                    case 3:
//                        try {
//                            //String d=(String)getvalueincell(cell);
//                            System.out.println(getvalueincell(cell));
//                            //java.util.Date date2=new SimpleDateFormat("M/d/yyyy").parse(d);
//                            s.setNamxb(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format((java.util.Date)row.getCell(columnIndex).getDateCellValue())));
//                            System.out.println("get ngaysinh tc");
//                            System.out.println(s.getNamxb());
//                            break;
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            System.out.println("loi khi get nam xb excel");
//                            e.printStackTrace();
//                        }
                    case 3:
                        s.setDiaChi(cell.getStringCellValue());
                        System.out.println("loi khi get dia chi excel");
                        break;
//                	try {
//
//
////                	tt.setSoCMND(new BigDecimal((Double)getvalueincell(cell)));
//                	tt.setSoCMND(new BigDecimal(row.getCell(columnIndex).getNumericCellValue()));
//
//                	System.out.println("loi khi get decimal excel");
//                	System.out.println(tt.getSoCMND());
//                	break;
//                	} catch (Exception e) {
//
//					}
                }
            }
            System.out.println(s.getTenKH()+"\t"+s.getSoDT()+"\t"+s.getSoCMT()+"\t"+s.getDiaChi());
            listS.add(s);

        }

        return listS;
    }
    public XSSFWorkbook writeworkkhachhang() {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("Quản Lí Khách Hàng");
        XSSFRow row;
        int countrow=tableKhachHang.getItems().size();


        row=sheet.createRow(0);
        Cell cell1=row.createCell(2);
        cell1.setCellValue("DANH SÁCH KHÁCH HÀNG CỦA CỬA HÀNG - HUST");
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
        cell1.setCellValue("Họ và tên");
        cell1=row.createCell(4);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Số điện thoại");
        cell1=row.createCell(5);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Số CMT");
        cell1=row.createCell(6);
        cell1.setCellStyle(cellStyle);
        cell1.setCellValue("Địa chỉ");


        rowid++;



        TableView.TableViewSelectionModel<KhachHangModel> t= tableKhachHang.getSelectionModel();
//        for(int i=0;i<countrow;i++){
//            ObservableList<KhachHangModel> k= tableKhachHang.getItems();
//        }
        ObservableList<KhachHangModel>  khachHangModels= tableKhachHang.getItems();
//        writeHeader(sheet, 0);

        int size=khachHangModels.size();

        for(int i=0;i<size;i++) {
            KhachHangModel ex=khachHangModels.get(i);
            row=sheet.createRow(rowid);
            for(int j=2;j<7;j++) {
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

                        cell.setCellValue(ex.getSoDT());
                        break;
                    }
                    case 5: {

                        cell.setCellValue(ex.getSoCMT());
                        break;
                    }
                    case 6: {

                        cell.setCellValue(ex.getDiaChi());
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

        for(int i=0; i<=10; i++){
            sheet.autoSizeColumn(i);
        }

//        int columns=sheet.getRow(0).getPhysicalNumberOfCells();
//        autosizeColumn(sheet, columns);
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
