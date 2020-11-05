package com.ust.shbay.common.utils;

import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtil {

    @Data
    public class SheetData {

        //工作表名称
        public String sheetName;

        //表头
        public List<String> columnList;

        //表数据
        public List<String[]> dataList;
    }

    /**
     * 下载Excel
     * @param sheetDataList 工作表
     * @param fileName 文件名
     */
    public void write(List<SheetData> sheetDataList,
                      String fileName,
                      HttpServletResponse response){
        try {
            //excel的工作簿
            HSSFWorkbook wb = new HSSFWorkbook();

            //基本数据工作表创建
            for(SheetData sheetData : sheetDataList){
                HSSFSheet sheet = wb.createSheet(sheetData.getSheetName());

                // 设置表头字体样式
                HSSFFont columnHeadFont = wb.createFont();
                columnHeadFont.setFontName("宋体");
                columnHeadFont.setFontHeightInPoints((short) 10);
                //columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                columnHeadFont.setBold(true);

                // 列头的样式
                HSSFCellStyle columnHeadStyle = wb.createCellStyle();
                columnHeadStyle.setFont(columnHeadFont);
                // 左右居中
                //columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                columnHeadStyle.setAlignment(HorizontalAlignment.CENTER);
                // 上下居中
                //columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                columnHeadStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                columnHeadStyle.setLocked(true);
                columnHeadStyle.setWrapText(true);
                // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
                //columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);
                columnHeadStyle.setFillForegroundColor(IndexedColors.WHITE.index);

                // 设置普通单元格字体样式
                HSSFFont font = wb.createFont();
                font.setFontName("宋体");
                font.setFontHeightInPoints((short) 10);

                //列宽
                for (int i=0;i<sheetData.columnList.size()+1;i++){
                    //默认列宽
                    Integer cellWidth=3500;
                    sheet.setColumnWidth(i,cellWidth);
                }

                //创建Excel工作表第一行
                HSSFRow row0 = sheet.createRow(0);
                // 设置行高
                row0.setHeight((short) 750);
                HSSFCell nCell=null;
                for (int i=0;i<sheetData.columnList.size();i++){
                    nCell = row0.createCell(i);
                    //设置单元格内容
                    nCell.setCellValue(sheetData.columnList.get(i));
                    //设置单元格字体样式
                    nCell.setCellStyle(columnHeadStyle);
                }

                //循环写入数据
                for (int j=0;j<sheetData.getDataList().size();j++){
                    String[] arr = sheetData.getDataList().get(j);
                    //创建行
                    HSSFRow nRow = sheet.createRow(j + 1);
                    //内容
                    for (int i=0;i<sheetData.columnList.size();i++){
                        nCell = nRow.createCell(i);
                        nCell.setCellValue(arr[i]);
                        nCell.setCellStyle(columnHeadStyle);
                    }

                }
            }

            // 获取输出流
            OutputStream output=response.getOutputStream();
            // 重置输出流
            response.reset();
            // 设定输出文件头,并处理中文乱码
            response.setHeader( "Content-Disposition", "attachment;filename="
                    +fileName);
            // 定义输出类型
            response.setContentType("application/vnd.ms-excel");
            wb.write(output);
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
