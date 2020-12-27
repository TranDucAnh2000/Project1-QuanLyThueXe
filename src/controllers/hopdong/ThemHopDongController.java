package controllers.hopdong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.HopDongModel;
import service.HopDongService;

import java.sql.Date;
import java.sql.SQLException;


public class ThemHopDongController {

    @FXML
    private TextField textMaKH;

    @FXML
    private TextField textMaNV;

    @FXML
    private TextField textNgayThue;

    @FXML
    private TextField textNgayHenTra;

    @FXML
    private TextField textTienCoc;

    @FXML
    private TextField textTienThanhToan;

    @FXML
    void them(ActionEvent event) throws SQLException {
        HopDongModel hopDongModel = new HopDongModel();
        hopDongModel.setMaNV(Integer.valueOf(textMaNV.getText()));
        hopDongModel.setMaKH(Integer.valueOf(textMaKH.getText()));
        hopDongModel.setNgayThue(Date.valueOf(textNgayThue.getText()));
        hopDongModel.setNgayHenTra(Date.valueOf(textNgayHenTra.getText()));
        hopDongModel.setTienCoc(Integer.valueOf(textTienCoc.getText()));
        hopDongModel.setTienThanhToan(Integer.valueOf(textMaKH.getText()));

        HopDongService hopDongService = new HopDongService();
        hopDongService.addListHopDong(hopDongModel);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Thêm thành công!");
        alert.showAndWait();
    }

    public void initializeText(HopDongModel hopDongModel){
        textMaKH.setText(String.valueOf(hopDongModel.getMaKH()));
        textMaNV.setText(String.valueOf(hopDongModel.getMaNV()));
        textNgayThue.setText(String.valueOf(hopDongModel.getNgayThue()));
        textNgayHenTra.setText(String.valueOf(hopDongModel.getNgayHenTra()));
        textTienCoc.setText(String.valueOf(hopDongModel.getTienCoc()));
        textTienThanhToan.setText(String.valueOf(hopDongModel.getTienThanhToan()));

        textMaNV.setEditable(false);
        textMaKH.setEditable(false);
    }

}
