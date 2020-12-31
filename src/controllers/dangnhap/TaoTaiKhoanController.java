package controllers.dangnhap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.TaiKhoanModel;
import service.DangNhapService;

public class TaoTaiKhoanController {
    @FXML
    private PasswordField textMK;

    @FXML
    private TextField textMaNV;

    @FXML
    private PasswordField textMKrep;

    @FXML
    private TextField textTDN;

    DangNhapService dangNhapService=new DangNhapService();

    @FXML
    void dangki(ActionEvent event) {
        if(!(textTDN.getText().isEmpty() || textMK.getText().isEmpty() || textMKrep.getText().isEmpty() || textMaNV.getText().isEmpty())){
            String pwtemp=textMK.getText();
            String pwrep=textMKrep.getText();
            try {
                int manv=Integer.parseInt(textMaNV.getText());
                TaiKhoanModel t=new TaiKhoanModel(textTDN.getText(),pwtemp,manv);
                int a=dangNhapService.checkacc(t);
                if(a==3){
                    //tao tai khoan thanh cong
                    //chuyen ra man hinh dang nhap
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Tạo tài khoản thành công!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                else if(a==2){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Tên đăng nhập đã tồn tại !");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                else if(a==1){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Nhân viên này đã có tài khoản !");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Sai thông tin nhân viên");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Xem lại thông tin đăng kí!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}
