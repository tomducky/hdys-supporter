package com.carry.www.utils.ExportUtil;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.carry.www.utils.base.ServletUtils.getResponse;


/**
 * 类描述：Export导出Excel类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年08月12日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class ExcelUtil {
    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName;
    //导出表的列名 多个表头，2个
    private String[] rowName2;
    //导出表的列名 多个表头，3个
    private String[] rowName3;
    //导出表的列名 多个表头，4个
    private String[] rowName4;
    //导出表的列名 多个表头，5个
    private String[] rowName5;

    private List<Object[]> dataList = new ArrayList<Object[]>();

    HttpServletResponse response;

    //构造方法，传入要导出的数据
    public ExcelUtil(String title, String[] rowName, List<Object[]> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
    }

    //构造方法，传入要导出的数据
    public ExcelUtil(String title, String[] rowName, String[] rowName2, List<Object[]> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.rowName2 = rowName2;
        this.title = title;
    }

    //构造方法，传入要导出的数据
    public ExcelUtil(String title, String[] rowName, String[] rowName2, String[] rowName3, String[] rowName4, List<Object[]> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.rowName2 = rowName2;
        this.rowName3 = rowName3;
        this.rowName4 = rowName4;
        this.title = title;
    }

    //构造方法，传入要导出的数据
    public ExcelUtil(String title, String[] rowName, String[] rowName2, String[] rowName3, String[] rowName4, String[] rowName5, List<Object[]> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.rowName2 = rowName2;
        this.rowName3 = rowName3;
        this.rowName4 = rowName4;
        this.rowName5 = rowName5;
        this.title = title;
    }

    //构造方法，传入要导出的数据
    public ExcelUtil(String title, String[] rowName, String[] rowName2, String[] rowName3, List<Object[]> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.rowName2 = rowName2;
        this.rowName3 = rowName3;
        this.title = title;
    }


    /**
     * @方法描述: 导出数据泵
     * @return: void
     * @Author: carry
     */
    public void exportPump() throws Exception {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表

            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            //居中
            columnTopStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            columnTopStyle.setAlignment(HorizontalAlignment.CENTER);
            HSSFCellStyle style = this.getStyle(workbook);                    //单元格样式对象

            // 第一列
            int columnNum1 = rowName.length;
            HSSFRow rowRowName1 = sheet.createRow(0);

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum1; n++) {
                HSSFCell cellRowName = rowRowName1.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }

            // 定义所需列数，以第二行的列数为准
            int columnNum = rowName2.length;
            HSSFRow rowRowName = sheet.createRow(1);

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName2[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));


            sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7));

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 10, 10));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 11, 11));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 12, 12));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 13, 13));

            //将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 2);//创建所需的行数

                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if (j == 0) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());                        //设置单元格的值
                        }
                    }
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }

            if (workbook != null) {
                try {
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                    String headStr = "attachment; filename=\"" + fileName + "\"";
                    response = getResponse();
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", headStr);
                    OutputStream out = response.getOutputStream();
                    workbook.write(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @方法描述: 导出数据
     * @return: void
     * @Author: carry
     */
    public void export() throws Exception {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表

            // 产生表格标题行
//            HSSFRow rowm = sheet.createRow(0);
//            HSSFCell cellTiltle = rowm.createCell(0);

            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            //居中
            columnTopStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            columnTopStyle.setAlignment(HorizontalAlignment.CENTER);
            HSSFCellStyle style = this.getStyle(workbook);                    //文字单元格样式对象


//            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));
//            cellTiltle.setCellStyle(columnTopStyle);
//            cellTiltle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(0);                // 在索引2的位置创建行(最顶端的行开始的第二行)

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }

            //将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 1);//创建所需的行数

                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if (j == 0) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());                        //设置单元格的值
                        }
                    }
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }

            if (workbook != null) {
                try {
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                    String headStr = "attachment; filename=\"" + fileName + "\"";
                    response = getResponse();
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", headStr);
                    OutputStream out = response.getOutputStream();
                    workbook.write(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 14);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());// 设置背景色
        style.setFillPattern(style.getFillPatternEnum().SOLID_FOREGROUND); //不加此代码背景色无效
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);

        return style;

    }

    /**
     * @方法描述: 列头单元格样式
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @Author: carry
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook, short fontSize, String fontName) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) fontSize);
        //设置字体名字
        font.setFontName(fontName);
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();

        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());// 设置背景色
        style.setFillPattern(style.getFillPatternEnum().SOLID_FOREGROUND); //不加此代码背景色无效
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);

        return style;

    }

    /**
     * @方法描述: 列头单元格样式
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @Author: carry
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook, int flag) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        if (flag == 1) {
            //设置字体大小
            font.setFontHeightInPoints((short) 18);
        } else if (flag == 1) {
            font.setFontHeightInPoints((short) 10);
        } else if (flag == 2) {
            //水源字体
            font.setFontHeightInPoints((short) 8);
        } else if (flag == 5) {
            //第五行字体
            font.setFontHeightInPoints((short) 14);
        } else {
            font.setFontHeightInPoints((short) 14);
        }

        //5 宋体 其他 黑体
        if (flag == 5) {
            //设置字体名字
            font.setFontName("宋体");
        }
        {
            //设置字体名字
            font.setFontName("黑体");
        }

        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();

        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());// 设置背景色
        style.setFillPattern(style.getFillPatternEnum().SOLID_FOREGROUND); //不加此代码背景色无效
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);

        return style;

    }

    /**
     * @方法描述: 列数据信息单元格样式(靠左文字)
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @Author: carry
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();

        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);

        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);

        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);

        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        return style;

    }

    /**
     * @方法描述: 列数据信息单元格样式(靠右数字)
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @Author: carry
     */
    private HSSFCellStyle getNumberStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();

        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);

        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);

        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);

        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        style.setAlignment(HorizontalAlignment.RIGHT);// 水平方向的对齐方式
        // style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直方向的对齐方式
        return style;

    }

    /**
     * @方法描述: 列数据信息单元格样式(靠右数字红色)
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @Author: carry
     */
    private HSSFCellStyle numberStyleRed(HSSFWorkbook workbook) {
        HSSFCellStyle hssfCellStyle = this.getNumberStyle(workbook);
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体名字
        font.setFontName("Courier New");

        font.setColor(HSSFColor.RED.index);
        // 把字体 应用到当前样式
        hssfCellStyle.setFont(font);

        return hssfCellStyle;
    }

    /**
     * @方法描述: 列数据信息单元格样式(靠右数字红色)
     * @return: org.apache.poi.hssf.usermodel.HSSFCellStyle
     * @Author: carry
     */
    private HSSFCellStyle blondStyle(HSSFWorkbook workbook) {
        HSSFCellStyle hssfCellStyle = this.getStyle(workbook);
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体名字
        font.setFontName("黑体");
        font.setBold(true);
        // 把字体 应用到当前样式
        hssfCellStyle.setFont(font);

        return hssfCellStyle;
    }

}
