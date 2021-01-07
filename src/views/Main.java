package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage = new Stage();
    public static String TDN;
    public static int maNV;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("dangnhap/DangNhap.fxml"));
        this.primaryStage.setTitle("Quản lý thuê xe");
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
