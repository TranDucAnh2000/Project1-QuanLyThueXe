package controllers.dangnhap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.TaiKhoanModel;
import service.DangNhapService;
import views.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DangNhapController implements Initializable {
    @FXML
    private PasswordField textMK;

    @FXML
    private TextField textTDN;

    DangNhapService dangNhapService = new DangNhapService();

    @FXML
    void dangnhap(ActionEvent event) throws IOException {
        if(!(textTDN.getText().isEmpty() || textMK.getText().isEmpty())){
            String pwtemp = textMK.getText();
            TaiKhoanModel t = new TaiKhoanModel(textTDN.getText(), pwtemp);
            String password = dangNhapService.getpassword(t);
            try {
                if (password.equals(pwtemp)) {
                    Main.TDN = t.getTendangnhap();
                    Main.maNV = dangNhapService.getMaNV(t);
                    Main.primaryStage.close();
                    Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
                    Main.primaryStage.setScene(new Scene(root));
                    Main.primaryStage.setTitle("Quản lý thuê xe - Nhóm 1 - Trần Quốc Chung - Trần Đức Anh - Đỗ Hữu Bằng");
                    Main.primaryStage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Sai mật khẩu !");
                    alert.showAndWait();
                }
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Tài khoản không tồn tại !");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Nhập tài khoản và mật khẩu !");
            alert.showAndWait();
        }
    }

    @FXML
    void taoTK(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Main.class.getResource("dangnhap/TaoTaiKhoan.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Đăng ký tài khoản mới");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textMK.clear();
        textTDN.clear();

    }
}