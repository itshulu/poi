package sl.newpoi.util;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sl.newpoi.entity.Person;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  舒露
 */

public class ExcelUtil {

    private static final String I_XLS_$ = "^.+\\.(?i)(xls)$";
    private static final int INT = 2;

    /**
     * 导出用户的所有列表到excel
     *
     * @param personList  用户列表
     * @param outputStream 输出流
     */
    public static void exportUserExcel(List<Person> personList, ServletOutputStream outputStream) {
        try {
            //1、创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //1.1、创建合并单元格对象
            // 起始行号，结束行号，起始列号，结束列号
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
            //1.2、头标题样式
            HSSFCellStyle style1 = createCellStyle(workbook, (short) 16);
            //1.3、列标题样式
            HSSFCellStyle style2 = createCellStyle(workbook, (short) 13);
            //2、创建工作表
            HSSFSheet sheet = workbook.createSheet("用户列表");
            //2.1、加载合并单元格对象
            sheet.addMergedRegion(cellRangeAddress);
            //设置默认列宽
            sheet.setDefaultColumnWidth(25);
            //3、创建行
            //3.1、创建头标题行；并且设置头标题
            HSSFRow row1 = sheet.createRow(0);
            HSSFCell cell1 = row1.createCell(0);
            //加载单元格样式
            cell1.setCellStyle(style1);
            cell1.setCellValue("用户列表");
            //3.2、创建列标题行；并且设置列标题
            HSSFRow row2 = sheet.createRow(1);
            String[] titles = {"id", "姓名", "密码"};
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell2 = row2.createCell(i);
                //加载单元格样式
                cell2.setCellStyle(style2);
                cell2.setCellValue(titles[i]);
            }

            //4、操作单元格；将用户列表写入excel
            if (personList != null) {
                for (int j = 0; j < personList.size(); j++) {
                    HSSFRow row = sheet.createRow(j + 2);
                    HSSFCell cell11 = row.createCell(0);
                    cell11.setCellValue(personList.get(j).getId());
                    HSSFCell cell12 = row.createCell(1);
                    cell12.setCellValue(personList.get(j).getName());
                    HSSFCell cell13 = row.createCell(2);
                    cell13.setCellValue(personList.get(j).getPwd());
                }
            }
            //5、输出
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<Person> importExcel(File personExcel, String userExcelFileName) {
        List<Person> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(personExcel);
            boolean is03Excel = userExcelFileName.matches(I_XLS_$);
            //1、读取工作簿
            Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream) : new XSSFWorkbook(fileInputStream);
            //2、读取工作表
            Sheet sheet = workbook.getSheetAt(0);
            //3、读取行
            if (sheet.getPhysicalNumberOfRows() > INT) {
                Person person;
                for (int k = INT; k < sheet.getPhysicalNumberOfRows(); k++) {
                    Row row = sheet.getRow(k);
                    person = new Person();
                    person.setId(getCellValue(row, 0));
                    person.setName(getCellValue(row, 1));
                    person.setPwd(getCellValue(row, 2));
                    list.add(person);
                }
            }
            workbook.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 创建单元格样式
     *
     * @param workbook 工作簿
     * @param fontSize 字体大小
     * @return 单元格样式
     */
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
        HSSFCellStyle style = workbook.createCellStyle();
        //水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //创建字体
        HSSFFont font = workbook.createFont();
        //加粗字体
        font.setBold(true);
        font.setColor(Font.COLOR_NORMAL);
        font.setFontHeightInPoints(fontSize);
        //加载字体
        style.setFont(font);
        return style;
    }

    /**
     * 获取单元格中的数据
     *
     * @param row     行
     * @param cellNum 单元格号
     * @return 该单元格的数据
     */
    private static String getCellValue(Row row, int cellNum) {
        String stringCellValue = null;
        Cell cell;
        if ((cell = row.getCell(cellNum)) != null) {
            try {
                stringCellValue = cell.getStringCellValue();
            } catch (IllegalStateException e) {
                try {
                    stringCellValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e1) {
                    try {
                        stringCellValue = String.valueOf(cell.getBooleanCellValue());
                    } catch (IllegalStateException e2) {
                        try {
                            stringCellValue = String.valueOf(cell.getDateCellValue());
                        } catch (IllegalStateException ignored) {
                        }
                    }
                }
            }
        }
        return stringCellValue;
    }
}
