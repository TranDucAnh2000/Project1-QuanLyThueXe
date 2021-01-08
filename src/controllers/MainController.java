package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import views.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label TDN;

    @FXML
    private Label maNV;

    @FXML
    void handleHopDong(ActionEvent event) {
        Pane view = getPage("hopdong/HopDong.fxml");
        mainBorderPane.setCenter(view);
    }

    @FXML
    void handleKhach(ActionEvent event) {
        Pane view = getPage("khachhang/KhachHang.fxml");
        mainBorderPane.setCenter(view);
    }

    @FXML
    void handleNhanVien(ActionEvent event) {
        Pane view = getPage("nhanvien/NhanVien.fxml");
        mainBorderPane.setCenter(view);
    }

    @FXML
    void handleThongKe(ActionEvent event) {
        Pane view = getPage("thongke/ThongKe.fxml");
        mainBorderPane.setCenter(view);
    }

    @FXML
    void handleXe(ActionEvent event) {
        Pane view = getPage("xe/Xe.fxml");
        mainBorderPane.setCenter(view);
    }

    @FXML
    void handleDangXuat(ActionEvent event) throws IOException {
        Main.primaryStage.close();
        Parent root = FXMLLoader.load(Main.class.getResource("dangnhap/DangNhap.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.setTitle("Đăng nhập");
        Main.primaryStage.show();
    }

    public Pane getPage(String fileName){
        Pane view = new Pane();
        try {
            URL fileUrl = Main.class.getResource("/views/"+ fileName);
            if(fileName == null){
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("No page " +fileName+ " please check controllers files");
        }
        return view;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TDN.setText("Tài khoản: "+Main.TDN);
        maNV.setText("Mã NV: "+Main.maNV);
    }
}