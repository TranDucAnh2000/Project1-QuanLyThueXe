package controllers.nhanvien;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.KhachHangModel;
import models.NhanVienModel;
import service.NhanVienService;

import java.sql.Date;
import java.sql.SQLException;

public class ThemNVController {

    @FXML
    private TextField textDiaChi;

    @FXML
    private TextField textSoDT;

    @FXML
    private TextField textNgaysinh;

    @FXML
    private TextField textTenNV;

    @FXML
    private TextField textSoCMT;

    private NhanVienService nhanVienService=new NhanVienService();
    @FXML
    void them(ActionEvent event) throws SQLException {
        NhanVienModel nhanVienModel = new NhanVienModel();
        if(!textTenNV.getText().isEmpty())
            nhanVienModel.setTenNV(textTenNV.getText());
        else nhanVienModel.setTenNV(null);
        if(!textSoDT.getText().isEmpty())
            nhanVienModel.setSoDT(textSoDT.getText().trim());
        else nhanVienModel.setSoDT(null);
        if(!textSoCMT.getText().isEmpty())
            nhanVienModel.setSoCMT(textSoCMT.getText().trim());
        else nhanVienModel.setSoCMT(null);
        nhanVienModel.setDiachi(textDiaChi.getText().trim());
        if(!textNgaysinh.getText().isEmpty())
        nhanVienModel.setNgaysinh(Date.valueOf(textNgaysinh.getText().trim()));
        else nhanVienModel.setNgaysinh(null);
        //System.out.println(nha.getTenKH()+"\t"+khachHangModel.getSoDT());
        int t= nhanVienService.addListNV(nhanVienModel);

        if (t==1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Thêm thành công!");
            alert.showAndWait();
        }
    }

    @FXML
    void xoaText(ActionEvent event) {
        textDiaChi.clear();
        textTenNV.clear();
        textSoCMT.clear();
        textSoDT.clear();
        textNgaysinh.clear();
    }

}
