package controllers.xe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import models.KhachHangModel;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class XeController implements  Initializable{

    @FXML
    private TableView<?> tableXe;

    @FXML
    private TableColumn<?, ?> col_MaXe;

    @FXML
    private TableColumn<?, ?> col_Model;

    @FXML
    private TableColumn<?, ?> col_TenXe;

    @FXML
    private TableColumn<?, ?> col_GiaThue;

    @FXML
    private TableColumn<?, ?> col_MauSac;

    @FXML
    private TableColumn<?, ?> col_SoLuong;

    @FXML
    private TableColumn<?, ?> col_BienSoXe;

    @FXML
    private TextField textTimKiem;

    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Mã Xe", "Loại Xe", "Biển số xe",
            "Tên xe");
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    void comboBoxSelect(ActionEvent event) {

    }

    @FXML
    void sua(ActionEvent event) {

    }

    @FXML
    void them(ActionEvent event) {

    }

    @FXML
    void timKiem(ActionEvent event) {

    }

    @FXML
    void xoa(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(comboBoxOblist);
    }

}
