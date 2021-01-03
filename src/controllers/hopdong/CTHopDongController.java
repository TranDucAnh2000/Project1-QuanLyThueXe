package controllers.hopdong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.CTHopDongModel;
import models.HopDongModel;
import service.CTHopDongService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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
        CTHopDongModel ctHopDongModel = new CTHopDongModel();
        ctHopDongModel.setMaHD(ctHopDongService.maHD);
        if(!(textMaXe.getText().isEmpty()))
        ctHopDongModel.setMaXe(Integer.valueOf(textMaXe.getText()));
        else ctHopDongModel.setMaXe(Integer.parseInt(null));
        if(!textTienPhat.getText().trim().isEmpty())
        ctHopDongModel.setTienPhat(Integer.parseInt(textTienPhat.getText()));
//        else ctHopDongModel.setTienPhat(Integer.parseInt(null));
        ctHopDongModel.setSuCo(textSuCo.getText());
        if(!textNgayTra.getText().trim().isEmpty())
        ctHopDongModel.setNgayTra(Date.valueOf(textNgayTra.getText()));
        int a= ctHopDongService.addListCTHD(ctHopDongModel);
        if(a==1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Thêm thành công!");
            alert.showAndWait();
        }
        updateTable();
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
    void xuatFile(ActionEvent event) throws IOException, SQLException {
        ctHopDongService.exportExcelHopDong(ctHopDongService.maHD);
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

}
