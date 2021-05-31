# Project1-QuanLyThueXe.
Phần mềm quản lý thuê xe.

## Cài đặt
1. Cài đặt JDK làm môi trường chạy java.
2. Cài IntelliJ IDEA.
3. VM options: 
  ```bash
  --module-path "path to javafx lib folder" --add-modules javafx.controls,javafx.fxml
  ```
  ví dụ: 
 ```bash
  --module-path "C:\Program Files\Java\openjfx-15.0.1_windows-x64_bin-sdk\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml
 ```
 
4. Chạy file quanlythuvienDB.sql trong MS SQL Server.
5. Cài jdbc sql server driver để kết nối sql server với java: https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15
