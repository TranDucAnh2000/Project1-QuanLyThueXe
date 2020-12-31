package controllers.dangnhap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.TaiKhoanModel;
import service.DangNhapService;

public class QuenMatKhauController {

    @FXML
    private TextField textMaNV;

    @FXML
    private TextField textTDN;

    DangNhapService dangNhapService=new DangNhapService();
    @FXML
    void datlaiMK(ActionEvent event) {
        if(!(textMaNV.getText().isEmpty() || textTDN.getText().isEmpty())){
            TaiKhoanModel taiKhoanModel=new TaiKhoanModel();
            try {
                int manv=Integer.parseInt(textMaNV.getText());
                taiKhoanModel.setMaNV(manv);
                taiKhoanModel.setTendangnhap(textTDN.getText());
                int a=dangNhapService.resetPW(taiKhoanModel);
                if(a==1){
                    // đặt lại mật khẩu thành công ,MK là 0000
                    //chuyển về màn hình đăng nhập
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Xem lại thông tin đăng nhập và mã Nhân viên !");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Xem lại thông tin đăng nhập và mã Nhân viên !");
                alert.setHeaderText(null);
                alert.showAndWait();
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Xem lại thông tin đăng nhập và mã Nhân viên !");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}
