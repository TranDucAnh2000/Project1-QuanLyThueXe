package controllers.nhanvien;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.KhachHangModel;
import models.NhanVienModel;
import service.NhanVienService;

import java.sql.Date;

public class SuaNVController {
    @FXML
    private TextField textDiaChi;

    @FXML
    private TextField textMaNV;

    @FXML
    private TextField textSoDT;

    @FXML
    private TextField textNgaysinh;

    @FXML
    private TextField textSoCMT;

    @FXML
    private TextField textTenNV;
    private NhanVienService nhanVienService=new NhanVienService();
    @FXML
    void sua(ActionEvent event) {
        NhanVienModel nhanVienModel = new NhanVienModel();
        if (!(textTenNV.getText().isEmpty() || textSoCMT.getText().isEmpty() || textSoDT.getText().isEmpty() || textNgaysinh.getText().isEmpty())) {
            nhanVienModel.setMaNV(Integer.valueOf(textMaNV.getText()));
            nhanVienModel.setTenNV(textTenNV.getText());
            nhanVienModel.setSoDT(textSoDT.getText().trim());
            nhanVienModel.setSoCMT(textSoCMT.getText().trim());
            nhanVienModel.setDiachi(textDiaChi.getText().trim());
            nhanVienModel.setNgaysinh(Date.valueOf(textNgaysinh.getText()));
            int a = nhanVienService.editListNV(nhanVienModel);
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
        textDiaChi.clear();
        textTenNV.clear();
        textSoCMT.clear();
        textSoDT.clear();
        textNgaysinh.clear();
    }
    public void initializeTextField(NhanVienModel nhanVienModel){
        textMaNV.setText(String.valueOf(nhanVienModel.getMaNV()));
        textMaNV.setEditable(false);
        textDiaChi.setText(nhanVienModel.getDiachi());
        textTenNV.setText(nhanVienModel.getTenNV());
        textSoDT.setText(nhanVienModel.getSoDT());
        textSoCMT.setText(nhanVienModel.getSoCMT());
        textNgaysinh.setText(String.valueOf(nhanVienModel.getNgaysinh()));
    }
}
