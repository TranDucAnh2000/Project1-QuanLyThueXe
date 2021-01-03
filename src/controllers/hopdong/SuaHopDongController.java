package controllers.hopdong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.HopDongModel;
import models.XeModel;
import service.HopDongService;

import java.sql.Date;

public class SuaHopDongController {

    @FXML
    private TextField textMaKH;

    @FXML
    private TextField textMaHD;

    @FXML
    private TextField textNV;

    @FXML
    private TextField textNgayThue;

    @FXML
    private TextField textNgayHenTra;

    @FXML
    private TextField textTienCoc;

    @FXML
    private TextField textTienThanhToan;

    private HopDongService hopDongService = new HopDongService();

    @FXML
    void sua(ActionEvent event) {
        HopDongModel hopDongModel = new HopDongModel();
        if (!(textMaHD.getText().isEmpty() || textMaKH.getText().isEmpty() || textNgayHenTra.getText().isEmpty() || textNgayThue.getText().isEmpty()
            ||(textNV.getText().isEmpty())||(textTienCoc.getText().isEmpty())||(textTienThanhToan.getText().isEmpty()))) {
            hopDongModel.setMaKH(Integer.valueOf(textMaKH.getText()));
            hopDongModel.setMaHD(Integer.valueOf(textMaHD.getText()));
            hopDongModel.setMaNV(Integer.valueOf(textNV.getText().trim()));
            hopDongModel.setNgayThue(Date.valueOf(textNgayThue.getText().trim()));
            hopDongModel.setNgayHenTra(Date.valueOf(textNgayHenTra.getText().trim()));
            hopDongModel.setTienCoc(Integer.valueOf(textTienCoc.getText().trim()));
            hopDongModel.setTienThanhToan(Integer.valueOf(textTienThanhToan.getText().trim()));
            int a = hopDongService.editListHopDong(hopDongModel);
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

    public void initializeTextField(HopDongModel hopDongModel){
        textMaKH.setText(String.valueOf(hopDongModel.getMaKH()));
        textMaHD.setText(String.valueOf(hopDongModel.getMaHD()));
        textNgayThue.setText(String.valueOf(hopDongModel.getNgayThue()));
        textNV.setText(String.valueOf(hopDongModel.getMaNV()));
        textNgayHenTra.setText(String.valueOf(hopDongModel.getNgayHenTra()));
        textTienCoc.setText(String.valueOf(hopDongModel.getTienCoc()));
        textTienThanhToan.setText(String.valueOf(hopDongModel.getTienThanhToan()));

        textMaHD.setEditable(false);
    }

}
