package controllers.dangnhap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.TaiKhoanModel;
import service.DangNhapService;

import java.net.URL;
import java.util.ResourceBundle;

public class DangNhapController implements Initializable {
    @FXML
    private PasswordField textMK;

    @FXML
    private TextField textTDN;

    DangNhapService dangNhapService=new DangNhapService();
    @FXML
    void dangnhap(ActionEvent event) {
        if(!(textTDN.getText().isEmpty() || textMK.getText().isEmpty())){
            String pwtemp=textMK.getText();
            TaiKhoanModel t=new TaiKhoanModel(textTDN.getText(),pwtemp);
            String password=dangNhapService.getpassword(t);
            if(password.equals(pwtemp)) {
                //chuyen vao ung dung
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sai mật khẩu hoặc tên đăng nhập !");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @FXML
    void quenMK(ActionEvent event) {

    }

    @FXML
    void taoTK(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textMK.clear();
        textTDN.clear();

    }
}
