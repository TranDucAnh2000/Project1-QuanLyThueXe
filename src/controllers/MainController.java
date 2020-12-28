package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import views.Main;

import java.net.URL;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

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

}
