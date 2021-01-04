//package service;
//
//import models.KhachHangModel;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class XuatFileService {
//    public XSSFWorkbook openfileexcel(String excelfilepath) throws IOException {
//        FileInputStream fileip=new FileInputStream(new File(excelfilepath));
//        XSSFWorkbook workbook=new XSSFWorkbook(fileip);
//
//        return workbook;
//    }
//    public List<KhachHangModel> getlistkhachhangfromexcel(XSSFWorkbook wb){
//        XSSFRow row;
//        List<KhachHangModel> listS=new ArrayList<>();
//        XSSFSheet sheet=wb.getSheetAt(0);
//        Iterator<Row> iteratorrow=sheet.iterator();
////        iteratorrow.next();
//        //bor qua hang dau tien chua header cua file excel
//        while(iteratorrow.hasNext()) {
//            row=(XSSFRow) iteratorrow.next();
//            if(row.getRowNum()==0) continue;
//            Iterator <Cell> iteratorcell=row.iterator();
//            KhachHangModel s=new KhachHangModel();
//            while(iteratorcell.hasNext()) {
//                Cell cell=iteratorcell.next();
//
////			switch(cell.getCellType()) {
////			case String:
////			}
//                int columnIndex = cell.getColumnIndex();
//
//                switch (columnIndex) {
//                    case 0:
//                        s.setTenKH(cell.getStringCellValue());
//                        System.out.println("loi khi get ten excel");
//                        break;
//                    case 1:
//                        s.setSoDT(cell.getStringCellValue());
//                        System.out.println("loi khi get sdt excel");
//                        break;
//                    case 2:
//                        s.setSoCMT(cell.getStringCellValue());
//                        System.out.println("loi khi get soCMND excel");
//                        break;
//
////                    case 3:
////                        try {
////                            //String d=(String)getvalueincell(cell);
////                            System.out.println(getvalueincell(cell));
////                            //java.util.Date date2=new SimpleDateFormat("M/d/yyyy").parse(d);
////                            s.setNamxb(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format((java.util.Date)row.getCell(columnIndex).getDateCellValue())));
////                            System.out.println("get ngaysinh tc");
////                            System.out.println(s.getNamxb());
////                            break;
////                        } catch (Exception e) {
////                            // TODO Auto-generated catch block
////                            System.out.println("loi khi get nam xb excel");
////                            e.printStackTrace();
////                        }
//                    case 3:
//                        s.setDiaChi(cell.getStringCellValue());
//                        System.out.println("loi khi get dia chi excel");
//                        break;
////                	try {
////
////
//////                	tt.setSoCMND(new BigDecimal((Double)getvalueincell(cell)));
////                	tt.setSoCMND(new BigDecimal(row.getCell(columnIndex).getNumericCellValue()));
////
////                	System.out.println("loi khi get decimal excel");
////                	System.out.println(tt.getSoCMND());
////                	break;
////                	} catch (Exception e) {
////						// TODO: handle exception
////                		tt.setSoCMND(new BigDecimal((String)getvalueincell(cell)));
////                		break;
////					}
//                }
//            }
//            listS.add(s);
//
//        }
//
//        return listS;
//    }
//    public XSSFWorkbook writeworkbook(List<KhachHangModel>ls) {
//        XSSFWorkbook workbook=new XSSFWorkbook();
//        XSSFSheet sheet=workbook.createSheet("QuanLiSach");
//        XSSFRow row;
////        writeHeader(sheet, 0);
//
//        int size=ls.size();
//        int rowid=1;
//        for(int i=0;i<size;i++) {
//            KhachHangModel ex=ls.get(i);
//            row=sheet.createRow(rowid);
//            for(int j=0;j<5;j++) {
//                Cell cell=row.createCell(j);
////			"Mã sách","Tên sách","Tác giả","Năm xuất bản","Nhà xuất bản","Đơn giá","Tình trạng","Giới thiệu"
//                switch (j) {
//                    case 0: {
//
//                        cell.setCellValue(ex.getMaKH());
//                        break;
//                    }
//                    case 1: {
//
//                        cell.setCellValue(ex.getTenKH());
//                        break;
//                    }
//                    case 2: {
//
//                        cell.setCellValue(ex.getSoDT());
//                        break;
//                    }
//                    case 3: {
//
//                        cell.setCellValue(ex.getSoCMT());
//                        break;
//                    }
//                    case 4: {
//
//                        cell.setCellValue(ex.getDiaChi());
//                        break;
//                    }
//                }
//            }
//            rowid++;
//        }
////        int columns=sheet.getRow(0).getPhysicalNumberOfCells();
////        autosizeColumn(sheet, columns);
//        return workbook;
//    }
//}
