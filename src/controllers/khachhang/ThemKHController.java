package controllers.khachhang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.KhachHangModel;
import service.KhachHangService;

import java.sql.SQLException;

public class ThemKHController {

    @FXML
    private TextField textTenKH;

    @FXML
    private TextField textSoDT;

    @FXML
    private TextField textSoCMT;

    @FXML
    private TextField textDiaChi;

    private KhachHangService khachHangService = new KhachHangService();

    @FXML
    void them(ActionEvent event) throws SQLException {

        KhachHangModel khachHangModel = new KhachHangModel();
        if(!textTenKH.getText().isEmpty())
            khachHangModel.setTenKH(textTenKH.getText());
        else khachHangModel.setTenKH(null);
        if(!textSoDT.getText().isEmpty())
            khachHangModel.setSoDT(textSoDT.getText().trim());
        else khachHangModel.setSoDT(null);
        if(!textSoCMT.getText().isEmpty())
            khachHangModel.setSoCMT(textSoCMT.getText().trim());
        else khachHangModel.setSoCMT(null);
        khachHangModel.setDiaChi(textDiaChi.getText().trim());
        System.out.println(khachHangModel.getTenKH()+"\t"+khachHangModel.getSoDT());
        int t= khachHangService.addListKhachHang(khachHangModel);

        if (t==1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Thêm thành công!");
            alert.showAndWait();
        }


    }

    @FXML
    void xoaText(ActionEvent event) {
        textDiaChi.clear();
        textTenKH.clear();
        textSoCMT.clear();
        textSoDT.clear();
    }

}
