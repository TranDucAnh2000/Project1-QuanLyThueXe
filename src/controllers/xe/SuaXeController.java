package controllers.xe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.XeModel;
import service.XeService;

public class SuaXeController {
    @FXML
    private TextField textLoaiXe;

    @FXML
    private TextField textMaXe;

    @FXML
    private TextField textBienSoXe;

    @FXML
    private TextField textMauSac;

    @FXML
    private TextField textGiaThue;

    @FXML
    private TextField textTenXe;

    @FXML
    private ComboBox<String> combotinhtrang;

    XeService xeService=new XeService();
    private ObservableList<String> comboBoxOblist = FXCollections.observableArrayList("Chưa cho thuê", "Đã cho thuê");
    @FXML
    void sua(ActionEvent event) {
        XeModel xeModel = new XeModel();
        if (!(textLoaiXe.getText().isEmpty() || textTenXe.getText().isEmpty() || textBienSoXe.getText().isEmpty() || combotinhtrang.getValue().isEmpty())) {
            xeModel.setMaXe(Integer.valueOf(textMaXe.getText()));
            xeModel.setTenXe(textTenXe.getText());
            xeModel.setLoaiXe(textLoaiXe.getText().trim());
            xeModel.setMauSac(textMauSac.getText().trim());
            xeModel.setBienSoXe(textBienSoXe.getText().trim());
            xeModel.setGiaThue(Integer.parseInt(textGiaThue.getText().trim()));
            if(combotinhtrang.getValue().equals("Chưa cho thuê"))
                xeModel.setTinhTrang(true);
            else xeModel.setTinhTrang(false);

            int a = xeService.editListXe(xeModel);
            if (a == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Sửa thành công!");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Thêm không thành công,Nhập lại dữ liệu");
            alert.showAndWait();
        }
    }

    @FXML
    void xoaText(ActionEvent event) {
        textMaXe.clear();
        textMaXe.setEditable(false);
        textBienSoXe.clear();
        textGiaThue.clear();
        textLoaiXe.clear();
        textMauSac.clear();
        textTenXe.clear();
    }
    public void initializeTextField(XeModel xeModel){
        combotinhtrang.setItems(comboBoxOblist);
        textMaXe.setText(String.valueOf(xeModel.getMaXe()));
        textMaXe.setEditable(false);
        textTenXe.setText(xeModel.getTenXe());
        textMauSac.setText(xeModel.getMauSac());
        textLoaiXe.setText(xeModel.getLoaiXe());
        textGiaThue.setText(String.valueOf(xeModel.getGiaThue()));
        if(xeModel.getTinhTrang()) combotinhtrang.setValue("Chưa cho thuê");
        else combotinhtrang.setValue("Đã cho thuê");
        textBienSoXe.setText(xeModel.getBienSoXe());
    }

}
