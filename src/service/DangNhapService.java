package service;

import models.TaiKhoanModel;

import java.sql.*;

public class DangNhapService {
    public String getpassword(TaiKhoanModel taiKhoanModel){
        String matkhau=null;
        String sql="select MatKhau from TaiKhoan  where TenDangNhap =  '"+taiKhoanModel.getTendangnhap()+"'";
        try {
            Connection connection=DBConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet res=statement.executeQuery(sql);
            if(res.next()){
                matkhau=res.getString(1);
                return matkhau;
            }
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public int getMaNV(TaiKhoanModel taiKhoanModel){
        int maNV = 0;
        String sql="select MaNV from TaiKhoan  where TenDangNhap =  '"+taiKhoanModel.getTendangnhap()+"'";
        try {
            Connection connection=DBConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet res=statement.executeQuery(sql);
            while(res.next()){
                maNV = res.getInt("MaNV");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maNV;
    }
    public int checkacc(TaiKhoanModel taiKhoanModel){
        String matkhau=null;
        String sql="select * from NhanVien where MaNV='"+taiKhoanModel.getMaNV()+"'";
        String sql2="select * from TaiKhoan where TenDangNhap='"+taiKhoanModel.getTendangnhap()+"'";
        String sql3="select * from TaiKhoan where MaNV="+taiKhoanModel.getMaNV()+"'";
        try {
            Connection connection=DBConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet res=statement.executeQuery(sql);
            if(res.next()){
                ResultSet res3=statement.executeQuery(sql3);
                if(!res3.next()){
                    ResultSet res2=statement.executeQuery(sql2);
                    if(!res2.next()){
                        createacc(taiKhoanModel);
                        return 3;//thoa man tao tai khoan moi
                    }
                    else return 2;
                    //trung ten dang nhap
                }
                else return 1;
                //nhan vien da co tai khoan, khong cho tao moi
            }
            else
                return 0;
            //sai ma nhan vien
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int resetPW(TaiKhoanModel taiKhoanModel){
        String sql="select MaNV from TaiKhoan where TenDangNhap="+taiKhoanModel.getTendangnhap()+"'";
        try {
            Connection connection=DBConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet res=statement.executeQuery(sql);
            if(res.next()){
                if(taiKhoanModel.getMaNV()==res.getInt(1))
                {
                    resetpassword(taiKhoanModel);
                    return 1;
                }

                else return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void resetpassword(TaiKhoanModel taiKhoanModel){
        String sql="UPDATE TaiKhoan set MatKhau ='0000' where TenDangNhap="+taiKhoanModel.getTendangnhap()+"'";
        try {
            Connection connection=DBConnection.getConnection();
            Statement statement=connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createacc(TaiKhoanModel taiKhoanModel){
        String sql="insert into TaiKhoan values(?,?,?)";
        try {
            Connection connection=DBConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,taiKhoanModel.getTendangnhap());
            statement.setString(2,taiKhoanModel.getMatkhau());
            statement.setInt(3,taiKhoanModel.getMaNV());
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
