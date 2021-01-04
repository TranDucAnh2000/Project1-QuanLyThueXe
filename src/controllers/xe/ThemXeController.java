package controllers.xe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.NhanVienModel;
import models.XeModel;
import service.XeService;

import java.sql.Date;

public class ThemXeController {
    @FXML
    private TextField textLoaiXe;

    @FXML
    private TextField textBienSoXe;

    @FXML
    private TextField textMauSac;

    @FXML
    private TextField textGiaThue;

    @FXML
    private TextField textTenXe;

    XeService xeService=new XeService();
    @FXML
    void them(ActionEvent event) {
        XeModel xeModel = new XeModel();
        if(!textTenXe.getText().isEmpty())
            xeModel.setTenXe(textTenXe.getText().trim());
        else xeModel.setTenXe(null);
        if(!textLoaiXe.getText().isEmpty())
            xeModel.setLoaiXe(textLoaiXe.getText().trim());
        else xeModel.setLoaiXe(null);
        if(!textGiaThue.getText().isEmpty())
            xeModel.setGiaThue(Integer.parseInt(textGiaThue.getText().trim()));
        else xeModel.setGiaThue(Integer.parseInt(null));
        xeModel.setMauSac(textMauSac.getText().trim());
        if(!textBienSoXe.getText().isEmpty())
            xeModel.setBienSoXe(textBienSoXe.getText().trim());
        else xeModel.setBienSoXe(null);
        xeModel.setTinhTrang(true);
        //System.out.println(nha.getTenKH()+"\t"+khachHangModel.getSoDT());
        int t= xeService.addListXe(xeModel);

        if (t==1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Thêm thành công!");
            alert.showAndWait();
        }
    }

    @FXML
    void xoaText(ActionEvent event) {
        textBienSoXe.clear();
        textGiaThue.clear();
        textLoaiXe.clear();
        textMauSac.clear();
        textTenXe.clear();
    }

}
