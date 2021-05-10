package bai.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Map;

public class WriteExcel {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    /**
     * 向Excel文件中写入数据
     * @param dataList 数据
     * @param columnCount 单元格数
     * @param filePath 文件路径
     */
    public void writeExcel(List<Map> dataList, int columnCount, String filePath){
        OutputStream os = null;
        try{
            int columnNumCount = columnCount;
            File excelFile = new File(filePath);
            Workbook workbook = getWorkBook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            int rowNumber = sheet.getLastRowNum();
            System.out.println("Total Line " + rowNumber);


            os = new FileOutputStream(filePath);

            Row firstRow = sheet.createRow(0);
            firstRow.createCell(0).setCellValue("name");
            firstRow.createCell(1).setCellValue("address");
            firstRow.createCell(2).setCellValue("phone");

            int lastRowNum = sheet.getLastRowNum();

            System.out.println(lastRowNum);

            for(int j = 0; j < dataList.size() ; j++){
                Row row = sheet.createRow(lastRowNum + j + 1);
                Map dataMap = dataList.get(j);
                String address = dataMap.get("address").toString();
                String name = dataMap.get("name").toString();
                String phone = dataMap.get("phone").toString();


                for(int k = 0; k < columnNumCount; k ++){
                    Cell first = row.createCell(0);
                    first.setCellValue(name);

                    Cell second = row.createCell(1);
                    second.setCellValue(address);

                    Cell third = row.createCell(2);
                    third.setCellValue(phone);
                }
                os = new FileOutputStream(filePath);
                workbook.write(os);

            }
            System.out.println("Working Done!!!!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(os != null){
                    os.flush();
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取Excel文件的Workbook对象
     * @param excelFile Excel文件
     * @return Workbook对象
     * @throws IOException
     */
    private Workbook getWorkBook(File excelFile) throws IOException{
        Workbook wb = null;
        FileInputStream fis = new FileInputStream(excelFile);
        if(excelFile.getName().endsWith(EXCEL_XLS)){
            wb = new HSSFWorkbook(fis);
        }else if(excelFile.getName().endsWith(EXCEL_XLSX)){
            wb = new XSSFWorkbook(fis);
        }
        return wb;
    }
    /**
     * 读取Excel文件内容
     * @param filePath
     */
    public void readExcel(String filePath){
        File file = new File(filePath);
        if(!file.exists())
            return;

        Workbook workbook = null;
        try {
            workbook = getWorkBook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        for(int i = 0; i < rowCount; i ++){
            Row row = sheet.getRow(i);
            for(int j = 0; j < row.getLastCellNum(); j ++){
                System.out.print(row.getCell(j).getStringCellValue() + "  \t");
            }
            System.out.println();
        }
    }
}
