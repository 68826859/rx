package com.rx.common.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.SheetUtil;
import org.springframework.util.CollectionUtils;

import com.rx.base.utils.DateUtil;
import com.rx.base.utils.ReflectUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.struts2.ServletActionContext;
//import com.gdca.framework.common.CommonStatic;
//import com.gdca.framework.common.exception.ReflectException;
//import com.gdca.framework.common.util.DateUtils;
//import com.gdca.framework.common.util.ReflectUtil;

public class ExcelExport {
    private static Log log = LogFactory.getLog(ExcelExport.class);
    public static final int SHEET_ROW_MAX_SIZE = 65000;
    
    
    public static final String ENCODING = "UTF-8";
    /**
     * EXCEL列高度的单位是磅,Apache POI的行高度单位是缇(twip)
     * <p>
     * 1英寸=72磅=25.4毫米=1440缇
     * <p>
     * 1磅=0.353毫米=20缇
     * <p>
     * POI中的行高＝Excel的行高度*20
     * <p>
     * Excel的行高度=POI中的行高/20
     * <p>
     * **********************************************************
     * <p>
     * DPI = 1英寸内可显示的像素点个数。通常电脑屏幕是96DPI, IPhone4s的屏幕是326DPI, 普通激光黑白打印机是400DPI
     * <p>
     * 要计算POI行高或者Excel的行高，就先把它行转换到英寸，再乘小DPI就可以得到像素
     * <p>
     * 像素 ＝ (磅/72)*DPI
     * <p>
     * 像素= (Excel的行高度/72)*DPI
     * <p>
     * 像素= (POI中的行高/20/72)*DPI
     * <p>
     * Excel的行高度=像素/DPI*72
     * <p>
     * POI中的行高=像素/DPI*72*20
     * <p>
     * 例如在电脑屏幕上， Excel默认行高度为13.5， (13.5/72)*96=18像素，而Excel中也确实显示的就是18像素。
     */
    public static final int SHEET_ROW_DEFAULT_HEIGHT = 24;

    /**
     * 直接下载导出的数据记录
     *
     * @param list           实体集合
     * @param headers        按顺序传入标题
     * @param content        按顺序传入填充的实体属性
     * @param sheetName      工作表名称
     * @param fileNameSuffix excel文件名后缀。前缀是日期：yyyyMMddHHmmss
     */
    public static void downloadFile(HttpServletResponse response,List list, String headers[], String content[], String sheetName,
                                    String fileNameSuffix) {
        downloadFile(response,list, headers, content, sheetName, fileNameSuffix, null);
    }

    public static void downloadFile(HttpServletResponse response,List list, String headers[], String content[], String sheetName, String fileNameSuffix, String[] sumCols) {
        downloadFile(response,list, headers, content, sheetName, fileNameSuffix, sumCols, true);
    }

    @SuppressWarnings("rawtypes")
    public static void downloadFile(HttpServletResponse response,List list, String headers[], String content[], String sheetName,
                                    String fileNameSuffix, String[] sumCols, boolean formatString) {
        response.setCharacterEncoding(ENCODING);
        response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/vnd.ms-excel; charset=" + ENCODING);

        String fileName = DateUtil.textForNow("yyyyMMddHHmmss") + fileNameSuffix;
        try {
            fileName = java.net.URLEncoder.encode(fileName, ENCODING);
        } catch (UnsupportedEncodingException encodeExp) {
            encodeExp.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        OutputStream fileOut = null;
        try {
            fileOut = response.getOutputStream();
            bulidExportStream(fileOut, list, headers, content, sheetName, sumCols, formatString);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public static byte[] createExcelFile(List list, String headers[], String content[], String sheetName, String fileNameSuffix) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bulidExportStream(bout, list, headers, content, sheetName);

        return bout.toByteArray();
    }

    /**
     * 将数据记录转换为POI Excel对象，并输出到指定流里
     *
     * @param fileOut
     * @param list
     * @param headers
     * @param content
     * @param sheetName
     * @throws IOException
     */
    public static void bulidExportStream(OutputStream fileOut, List list, String[] headers, String[] content,
                                         String sheetName) throws Exception {
        bulidExportStream(fileOut, list, headers, content, sheetName, null, true);
    }

    @SuppressWarnings("rawtypes")
    public static void bulidExportStream(OutputStream fileOut, List list, String[] headers, String[] content,
                                         String sheetName, String[] sumCols, boolean formatString) throws Exception {
        int index = 0;// 定义行标志
        int sheetNameIndex = 1;

        // 建立Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = null;

        HSSFCellStyle headerStyle = getHeaderStyle(workbook);
        HSSFCellStyle cellStyle = getCellStyle(workbook);

        if (CollectionUtils.isEmpty(list)) {
            // 如果行记录数大于设定值，则新建工作表
            if (index % ExcelExport.SHEET_ROW_MAX_SIZE == 0) {
                sheet = workbook.createSheet(sheetName + (sheetNameIndex++));
                index = 0;
            }
            // 在工作表中创建标题行
            HSSFRow row = sheet.createRow(index++);
            row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

            // 建立单元格,头信息
            for (int j = 0; j < headers.length; j++) {
                HSSFCell headCell = row.createCell(j);
                // 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
                // headCell.setEncoding(HSSFCell.ENCODING_UTF_16);
                headCell.setCellValue(headers[j]);
                headCell.setCellStyle(headerStyle);
            }
        } else {
            Map<String, Object> sumMap = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                // 如果行记录数大于设定值，则新建工作表
                if (index % ExcelExport.SHEET_ROW_MAX_SIZE == 0) {
                    sheet = workbook.createSheet(sheetName + (sheetNameIndex++));
                    index = 0;
                }

                if (index == 0) {
                    // 在工作表中创建标题行
                    HSSFRow row = sheet.createRow(index++);
                    row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);
                    // 建立单元格,头信息
                    for (int j = 0; j < headers.length; j++) {
                        HSSFCell headCell = row.createCell(j);
                        // 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
                        // headCell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        headCell.setCellValue(headers[j]);
                        headCell.setCellStyle(headerStyle);
                    }
                }

                Object object = list.get(i);
                HSSFRow row = sheet.createRow(index++);
                row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

                if (i == 0 && null != sumCols && sumCols.length > 0) {
                    for (String sumCol : sumCols) {
                        //try {
                            Object invoke = ReflectUtil.getPropertyValue(sumCol, object);
                            if (invoke instanceof BigDecimal) {
                                sumMap.put(sumCol, new BigDecimal(0));
                            } else if (invoke instanceof Float) {
                                sumMap.put(sumCol, 0f);
                            } else if (invoke instanceof Float) {
                                sumMap.put(sumCol, 0f);
                            } else if (invoke instanceof Integer) {
                                sumMap.put(sumCol, 0);
                            }
                        //} catch (ReflectException e) {
                        //   e.printStackTrace();
                        //}
                    }
                }

                for (int j = 0; j < content.length; j++) {
                    String varValue = null;
                    // 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
                    HSSFCell cell = row.createCell(j);
                    //try {
                        Object invoke = ReflectUtil.getPropertyValue(content[j], object);
                        if (invoke instanceof String) {
                            varValue = (String) invoke;
                            if (!formatString) {
                                cell.setCellValue(varValue);
                            }
                        } else if (invoke instanceof Date) {
                            if (formatString) {
                                varValue = DateUtil.textForNow(DateUtil.DEFAULT_DATE_TIME_FORMART, (Date) invoke);
                            } else {
                                cell.setCellValue((Date) invoke);
                            }
                        } else if (invoke instanceof BigDecimal) {
                            if (formatString) {
                                varValue = invoke.toString();
                            } else {
                                cell.setCellValue(Double.valueOf(invoke.toString()));
                            }
                            if (sumMap.containsKey(content[j])) {
                                sumMap.put(content[j], ((BigDecimal) sumMap.get(content[j])).add((BigDecimal) invoke));
                            }
                        } else if (invoke instanceof Double) {
                            if (formatString) {
                                varValue = invoke.toString();
                            } else {
                                cell.setCellValue((Double) invoke);
                            }
                            if (sumMap.containsKey(content[j])) {
                                sumMap.put(content[j], (Double) sumMap.get(content[j]) + (Double) invoke);
                            }
                        } else if (invoke instanceof Float) {
                            if (formatString) {
                                varValue = invoke.toString();
                            } else {
                                cell.setCellValue((Float) invoke);
                            }
                            if (sumMap.containsKey(content[j])) {
                                sumMap.put(content[j], (Float) sumMap.get(content[j]) + (Float) invoke);
                            }
                        } else if (invoke instanceof Integer) {
                            if (formatString) {
                                varValue = invoke.toString();
                            } else {
                                cell.setCellValue((Integer) invoke);
                            }
                            if (sumMap.containsKey(content[j])) {
                                sumMap.put(content[j], (Integer) sumMap.get(content[j]) + (Integer) invoke);
                            }
                        } else if (invoke instanceof List) {
                            varValue = invoke.toString();
                            if (!formatString) {
                                cell.setCellValue(varValue);
                            }

                        } else if (invoke instanceof Map) {
                            varValue = invoke.toString();
                            if (!formatString) {
                                cell.setCellValue(varValue);
                            }

                        }
                    //} catch (ReflectException e) {
                    //    e.printStackTrace();
                    //}
                    // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    if (formatString) {
                        cell.setCellValue(varValue == null ? "" : varValue);
                    }
                    cell.setCellStyle(cellStyle);
                }

                if (i == list.size() - 1 && null != sumCols && sumCols.length > 0) {
                    sheet.addMergedRegion(new CellRangeAddress(index, index, 0, content.length - 1));
                    HSSFRow rowStatsHead = sheet.createRow(index);
                    row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

                    HSSFCell cellStatsHead = rowStatsHead.createCell(0);
                    cellStatsHead.setCellValue("合计");
                    cellStatsHead.setCellStyle(headerStyle);

                    index++;
                    HSSFRow rowStats = sheet.createRow(index);
                    row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

                    for (int j = 0; j < content.length; j++) {
                        if (sumMap.containsKey(content[j])) {
                            HSSFCell cellStats = rowStats.createCell(j);
                            cellStats.setCellValue(sumMap.get(content[j]).toString());
                            cellStats.setCellStyle(cellStyle);
                        }
                    }
                }
            }
        }

        for (int column = 0; column < headers.length; column++) {
            // sheet.autoSizeColumn(column);

            double width = SheetUtil.getColumnWidth(sheet, column, false) + 2;

            if (width != -1) {
                width *= 256;
                int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255 characters
                if (width > maxColumnWidth) {
                    width = maxColumnWidth;
                }
                sheet.setColumnWidth(column, (int) (width));
            }
        }

        workbook.write(fileOut);
    }

    private static void createTitleCell(HSSFWorkbook workbook, HSSFSheet sheet, String title, String[] headers) {

        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(25.0F);
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment((short) 2);
        titleStyle.setVerticalAlignment((short) 1);
        HSSFFont titleFont = workbook.createFont();

        titleFont.setColor((short) 10);
        titleFont.setBoldweight((short) 700);
        titleFont.setFontHeightInPoints((short) 16);

        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, headers.length - 1));
        HSSFCell titleCell = row0.createCell(0);
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(title);

    }

    private static void createSecondTitleCell(HSSFWorkbook workbook, HSSFSheet sheet, String secondTitle, String[] headers) {

        HSSFRow row1 = sheet.createRow(1);
        row1.setHeightInPoints(19.0F);
        HSSFCellStyle operatorStyle = workbook.createCellStyle();
        operatorStyle.setVerticalAlignment((short) 1);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, headers.length - 1));
        HSSFCell cell1 = row1.createCell(0);
        HSSFFont ff = workbook.createFont();
        ff.setFontName("黑体");
        ff.setBoldweight((short) 700);
        operatorStyle.setFont(ff);
        cell1.setCellStyle(operatorStyle);
        cell1.setCellValue("操作人" + "【" + secondTitle + "】");

    }


    private static void createHeaderTitleCell(HSSFWorkbook workbook, HSSFSheet sheet, String[] headers) {

        // 在工作表中创建标题行
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

        HSSFCellStyle headerStyle = getHeaderStyle(workbook);

        // 建立单元格,头信息
        for (int j = 0; j < headers.length; j++) {
            HSSFCell headCell = row2.createCell(j);
            // 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
            // headCell.setEncoding(HSSFCell.ENCODING_UTF_16);
            headCell.setCellValue(headers[j]);
            headCell.setCellStyle(headerStyle);
        }

    }

    /**
     * 将数据记录转换为POI Excel对象，并输出到指定流里
     *
     * @param fileOut
     * @param list
     * @param headers
     * @param content
     * @param sheetName
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void bulidExportStreamForSF(OutputStream fileOut, List list, String title, String operator, String[] headers, String[] content,
                                              String sheetName, int ignoreRows) throws Exception {
        int index = 0;// 定义行标志
        int sheetNameIndex = 1;

        // 建立Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = null;

        HSSFCellStyle headerStyle = getHeaderStyle(workbook);
        HSSFCellStyle cellStyle = getCellStyle(workbook);

        // 如果行记录数大于设定值，则新建工作表
        if (index % ExcelExport.SHEET_ROW_MAX_SIZE == 0) {
            sheet = workbook.createSheet(sheetName + (sheetNameIndex++));
            index = 0;
        }

        createTitleCell(workbook, sheet, title, headers);
        index++;


        createSecondTitleCell(workbook, sheet, operator, headers);
        index++;

        createHeaderTitleCell(workbook, sheet, headers);
        index++;

        if (CollectionUtils.isEmpty(list)) {
			
			/*// 在工作表中创建标题行
			HSSFRow row2 = sheet.createRow(index++);
			row2.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

			// 建立单元格,头信息
			for (int j = 0; j < headers.length; j++) {
				HSSFCell headCell = row2.createCell(j);
				// 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
				// headCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				headCell.setCellValue(headers[j]);
				headCell.setCellStyle(headerStyle);
			}*/
        } else {
            for (int i = 0; i < list.size(); i++) {
                // 如果行记录数大于设定值，则新建工作表
                if (index % ExcelExport.SHEET_ROW_MAX_SIZE == 0) {
                    sheet = workbook.createSheet(sheetName + (sheetNameIndex++));
                    index = 0;
                }

                if (index == 0) {

                    createTitleCell(workbook, sheet, title, headers);
                    index++;


                    createSecondTitleCell(workbook, sheet, operator, headers);
                    index++;

                    createHeaderTitleCell(workbook, sheet, headers);
                    index++;
                }

                Object object = list.get(i);
                HSSFRow row = sheet.createRow(index++);
                row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

                for (int j = 0; j < content.length; j++) {
                    String varValue = null;
                        Object invoke = ReflectUtil.getPropertyValue(content[j], object);
                        if (invoke instanceof String) {
                            varValue = (String) invoke;
                        } else if (invoke instanceof Date) {
                            varValue = DateUtil.textForNow(DateUtil.DEFAULT_DATE_TIME_FORMART, (Date) invoke);
                        } else if (invoke instanceof Double) {
                            varValue = invoke.toString();
                        } else if (invoke instanceof Integer) {
                            varValue = invoke.toString();
                        } else if (invoke instanceof List) {
                            varValue = invoke.toString();
                        } else if (invoke instanceof Map) {
                            varValue = invoke.toString();
                        }

                    // 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
                    HSSFCell cell = row.createCell(j);
                    // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue(varValue == null ? "" : varValue);
                    cell.setCellStyle(cellStyle);
                }

            }
        }

        for (int column = 0; column < headers.length; column++) {
            // sheet.autoSizeColumn(column);

            double width = SheetUtil.getColumnWidth(sheet, column, false) + 2;

            if (width != -1) {
                width *= 256;
                int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255 characters
                if (width > maxColumnWidth) {
                    width = maxColumnWidth;
                }
                sheet.setColumnWidth(column, (int) (width));
            }
        }

        workbook.write(fileOut);
    }


    /**
     * 将数据记录转换为POI Excel对象，并输出到指定流里
     *
     * @param fileOut
     * @param list
     * @param headers
     * @param content
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void bulidExportStreamBySFTemplate(OutputStream fileOut, List list, String[] headers, String[] content,
                                                     InputStream in, int ignoreRows) throws Exception {
        String sheetName = "";
        int index = ignoreRows;// 定义行标志
        //int index = 0;// 定义行标志
        int sheetNameIndex = 1;

        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        // 建立Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook(fs);
        HSSFSheet sheet = null;

        //TODO 读取模板默认读取第一个sheet
        int sheetIndex = 0;
        sheet = workbook.getSheetAt(sheetIndex);
        sheetName = sheet.getSheetName();


        //HSSFCellStyle headerStyle = getHeaderStyle(workbook);
        //HSSFCellStyle cellStyle = getCellStyle(workbook);

        if (CollectionUtils.isEmpty(list)) {
            //TODO 数据为空不做处理
			/*
			// 如果行记录数大于设定值，则新建工作表
			if (index % ExcelExport.SHEET_ROW_MAX_SIZE == 0) {
				sheet = workbook.createSheet(sheetName + (sheetNameIndex++));
				index = 0;
			}
			// 在工作表中创建标题行
			HSSFRow row = sheet.createRow(index++);
			row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

			// 建立单元格,头信息
			for (int j = 0; j < headers.length; j++) {
				HSSFCell headCell = row.createCell(j);
				// 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
				// headCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				headCell.setCellValue(headers[j]);
				headCell.setCellStyle(headerStyle);
			}
	*/
        } else {
            for (int i = 0; i < list.size(); i++) {
                // 如果行记录数大于设定值，则新建工作表
                if (index % ExcelExport.SHEET_ROW_MAX_SIZE == 0) {
                    sheet = workbook.createSheet(sheetName + (sheetNameIndex++));
                    index = 0;
                }

				/*if (index == 0) {
					// 在工作表中创建标题行
					HSSFRow row = sheet.createRow(index++);
					row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);
					// 建立单元格,头信息
					for (int j = 0; j < headers.length; j++) {
						HSSFCell headCell = row.createCell(j);
						// 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
						// headCell.setEncoding(HSSFCell.ENCODING_UTF_16);
						headCell.setCellValue(headers[j]);
						headCell.setCellStyle(headerStyle);
					}
				}*/

                Object object = list.get(i);
                HSSFRow row = sheet.createRow(index++);
                //row.setHeightInPoints(SHEET_ROW_DEFAULT_HEIGHT);

                for (int j = 0; j < content.length; j++) {
                    String varValue = null;
                    Object invoke = ReflectUtil.getPropertyValue(content[j], object);
                    if (invoke instanceof String) {
                        varValue = (String) invoke;
                    } else if (invoke instanceof Date) {
                        varValue = DateUtil.textForNow(DateUtil.DEFAULT_DATE_TIME_FORMART, (Date) invoke);
                    } else if (invoke instanceof Double) {
                        varValue = invoke.toString();
                    } else if (invoke instanceof Integer) {
                        varValue = invoke.toString();
                    } else if (invoke instanceof List) {
                        varValue = invoke.toString();
                    } else if (invoke instanceof Map) {
                        varValue = invoke.toString();
                    }

                    // 将单元格定义成UTF_16编码，这样才能使输出数据不会乱码
                    HSSFCell cell = row.createCell(j);
                    // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellValue(varValue == null ? "" : varValue);
                    //cell.setCellStyle(cellStyle);
                }

            }
        }

	/*for (int column = 0; column < headers.length; column++) {
			// sheet.autoSizeColumn(column);
			
	        double width = SheetUtil.getColumnWidth(sheet, column, false) + 2;

	        if (width != -1) {
	            width *= 256;
	            int maxColumnWidth = 255 * 256; // The maximum column width for an individual cell is 255 characters
	            if (width > maxColumnWidth) {
	                width = maxColumnWidth;
	            }
	            sheet.setColumnWidth(column, (int) (width));
	        }
	}*/

        workbook.write(fileOut);
    }

    private static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle headerStyle = (HSSFCellStyle) workbook.createCellStyle();// 创建标题样式
        headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 设置垂直居中
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置水平居中
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

        HSSFFont headerFont = (HSSFFont) workbook.createFont(); // 创建字体样式
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
        headerFont.setFontName("宋体"); // 设置字体类型
        headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
        headerStyle.setFont(headerFont); // 为标题样式设置字体样式

        return headerStyle;
    }

    private static HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = (HSSFCellStyle) workbook.createCellStyle();// 设置字体样式
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle.setWrapText(true); // 设置为自动换行

        HSSFFont cellFont = (HSSFFont) workbook.createFont();
        cellFont.setFontName("宋体");
        cellFont.setFontHeightInPoints((short) 10);
        cellStyle.setFont(cellFont);

        return cellStyle;
    }
}
