package com.integration.core.util;


import com.integration.core.anno.ExcelExport;
import com.integration.core.util.destruct.ExcelClassField;

import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * @author cyh
 * 导出工具类，后续补充
 * 包含解密方法
 */
public class ExcelUtils {
    private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);
    private static final String XLSX = ".xlsx";
    public static final String ROW_MERGE = "row_merge";
    public static final String COLUMN_MERGE = "column_merge";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final int CELL_OTHER = 0;
    private static final int CELL_ROW_MERGE = 1;
    private static final int CELL_COLUMN_MERGE = 2;


    public static <T> void exportTemplate(HttpServletResponse response, String fileName, String sheetName,
                                          Class<T> clazz, boolean isContainExample, Map<String, String> mapCode) {
        // 获取表头字段
        List<ExcelClassField> headFieldList = getExcelClassFieldList(clazz, mapCode);
        // 获取表头数据和示例数据
        List<List<Object>> sheetDataList = new ArrayList<>();
        List<Object> headList = new ArrayList<>();
        List<Object> exampleList = new ArrayList<>();
        Map<Integer, List<String>> selectMap = new LinkedHashMap<>();
        for (int i = 0; i < headFieldList.size(); i++) {
            ExcelClassField each = headFieldList.get(i);
            headList.add(each.getName());
            exampleList.add(each.getExample());
            LinkedHashMap<String, String> kvMap = each.getKvMap();
            if (kvMap != null && kvMap.size() > 0) {
                selectMap.put(i, new ArrayList<>(kvMap.values()));
            }
        }
        sheetDataList.add(headList);
        if (isContainExample) {
            sheetDataList.add(exampleList);
        }
        // 导出数据
        export(response, fileName, sheetName, sheetDataList, selectMap);
    }

    private static <T> List<ExcelClassField> getExcelClassFieldList(Class<T> clazz, Map<String, String> mapCode) {
        // 解析所有字段
        Field[] fields = clazz.getDeclaredFields();
        boolean hasExportAnnotation = false;
        Map<Integer, List<ExcelClassField>> map = new LinkedHashMap<>();
        List<Integer> sortList = new ArrayList<>();
        for (Field field : fields) {
            ExcelClassField cf = getExcelClassField(field, mapCode);
            if (cf.getHasAnnotation() == 1) {
                hasExportAnnotation = true;
            }
            int sort = cf.getSort();
            if (map.containsKey(sort)) {
                map.get(sort).add(cf);
            } else {
                List<ExcelClassField> list = new ArrayList<>();
                list.add(cf);
                sortList.add(sort);
                map.put(sort, list);
            }
        }
        Collections.sort(sortList);
        // 获取表头
        List<ExcelClassField> headFieldList = new ArrayList<>();
        if (hasExportAnnotation) {
            for (Integer sort : sortList) {
                for (ExcelClassField cf : map.get(sort)) {
                    if (cf.getHasAnnotation() == 1) {
                        headFieldList.add(cf);
                    }
                }
            }
        } else {
            headFieldList.addAll(map.get(0));
        }
        return headFieldList;
    }

    private static ExcelClassField getExcelClassField(Field field, Map<String, String> mapCode) {
        ExcelClassField cf = new ExcelClassField();
        String fieldName = field.getName();
        cf.setFieldName(fieldName);
        ExcelExport annotation = field.getAnnotation(ExcelExport.class);
        // 无 ExcelExport 注解情况
        if (annotation == null) {
            cf.setHasAnnotation(0);
            cf.setName(fieldName);
            cf.setSort(0);
            return cf;
        }
        // 有 ExcelExport 注解情况
        cf.setHasAnnotation(1);
        cf.setName(annotation.value());
        String example = getString(annotation.example());
        if (!example.isEmpty()) {
            if (isNumeric(example)) {
                cf.setExample(Double.valueOf(example));
            } else {
                cf.setExample(example);
            }
        } else {
            cf.setExample("");
        }
        cf.setSort(annotation.sort());
        // 解析映射
        String kv = getString(annotation.kv());
        cf.setKvMap(getKvMap(kv, mapCode));
        return cf;
    }

    private static LinkedHashMap<String, String> getKvMap(String kv, Map<String, String> mapCode) {
        LinkedHashMap<String, String> kvMap = new LinkedHashMap<>();
        if (kv.isEmpty()) {
            return kvMap;
        }
        //数据字典查询出来的映射关系
        if (!CollectionUtils.isEmpty(mapCode) && !mapCode.isEmpty() && null != mapCode && mapCode.containsKey(kv)) {
            kv = mapCode.get(kv);
        }
        String[] kvs = kv.split(";");
        if (kvs.length == 0) {
            return kvMap;
        }
        for (String each : kvs) {
            String[] eachKv = getString(each).split("-");
            if (eachKv.length != 2) {
                continue;
            }
            String k = eachKv[0];
            String v = eachKv[1];
            if (k.isEmpty() || v.isEmpty()) {
                continue;
            }
            kvMap.put(k, v);
        }
        return kvMap;
    }

    /**
     * 导出表格到本地
     *
     * @param file      本地文件对象
     * @param sheetData 导出数据
     */
    public static void exportFile(File file, List<List<Object>> sheetData) {
        if (file == null) {
            log.info("文件创建失败");
            return;
        }
        if (sheetData == null) {
            sheetData = new ArrayList<>();
        }
        export(null, file, file.getName(), file.getName(), sheetData, null);
    }


    private static <T> List<List<Object>> getSheetData(List<T> list, boolean isNumber) {
        // 获取表头字段
        List<ExcelClassField> excelClassFieldList = getExcelClassFieldList(list.get(0).getClass(), new HashMap<>());
        List<String> headFieldList = new ArrayList<>();
        List<Object> headList = new ArrayList<>();
        Map<String, ExcelClassField> headFieldMap = new HashMap<>();
        for (ExcelClassField each : excelClassFieldList) {
            String fieldName = each.getFieldName();
            headFieldList.add(fieldName);
            headFieldMap.put(fieldName, each);
            headList.add(each.getName());
        }
        // 添加表头名称
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(headList);
        // 获取表数据
        for (T t : list) {
            Map<String, Object> fieldDataMap = getFieldDataMap(t);
            Set<String> fieldDataKeys = fieldDataMap.keySet();
            List<Object> rowList = new ArrayList<>();
            for (String headField : headFieldList) {
                if (!fieldDataKeys.contains(headField)) {
                    continue;
                }
                Object data = fieldDataMap.get(headField);
                if (data == null) {
                    rowList.add("");
                    continue;
                }
                ExcelClassField cf = headFieldMap.get(headField);
                // 判断是否有映射关系
                LinkedHashMap<String, String> kvMap = cf.getKvMap();
                if (kvMap == null || kvMap.isEmpty()) {
                    rowList.add(data);
                    continue;
                }
                String val = kvMap.get(data.toString());
                if (isNumeric(val) && isNumber) {
                    rowList.add(Double.valueOf(val));
                } else {
                    rowList.add(val);
                }
            }
            sheetDataList.add(rowList);
        }
        return sheetDataList;
    }

    private static <T> Map<String, Object> getFieldDataMap(T t) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = t.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                Object object = field.get(t);
                map.put(fieldName, object);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("属性异常," + e.getMessage(), e);
        }
        return map;
    }


    public static void exportEmpty(HttpServletResponse response, String fileName) {
        List<List<Object>> sheetDataList = new ArrayList<>();
        List<Object> headList = new ArrayList<>();
        headList.add("导出无数据");
        sheetDataList.add(headList);
        export(response, fileName, sheetDataList);
    }


    public static void export(HttpServletResponse response, String fileName, List<List<Object>> sheetDataList) {
        export(response, fileName, fileName, sheetDataList, null);
    }


    public static void export(HttpServletResponse response, String fileName, String sheetName,
                              List<List<Object>> sheetDataList, Map<Integer, List<String>> selectMap) {
        export(response, null, fileName, sheetName, sheetDataList, selectMap);
    }


    public static <T, K> void export(HttpServletResponse response, String fileName, List<T> list, Class<K> template) {
        // list 是否为空
        boolean lisIsEmpty = list == null || list.isEmpty();
        // 如果模板数据为空，且导入的数据为空，则导出空文件
        if (template == null && lisIsEmpty) {
            exportEmpty(response, fileName);
            return;
        }
        // 如果 list 无数据，则导出模板数据
        if (lisIsEmpty) {
            exportTemplate(response, fileName, fileName,
                    template, false, null);
            //exportTemplate(response, fileName, template);
            return;
        }
        // 导出数据
        List<List<Object>> sheetDataList = getSheetData(list, true);
        export(response, fileName, sheetDataList);

    }


    private static void export(HttpServletResponse response, File file, String fileName, String sheetName,
                               List<List<Object>> sheetDataList, Map<Integer, List<String>> selectMap) {
        // 整个 Excel 表格 book 对象
        SXSSFWorkbook book = new SXSSFWorkbook();
        book.createName();
        // 每个 Sheet 页
        Sheet sheet = book.createSheet(sheetName);
        Drawing<?> patriarch = sheet.createDrawingPatriarch();
        // 设置表头背景色（默认色）
        CellStyle headStyle = book.createCellStyle();
        headStyle.setWrapText(true);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        // 设置表身背景色（默认色）
        CellStyle rowStyle = book.createCellStyle();
        rowStyle.setAlignment(HorizontalAlignment.LEFT);
        rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置表格列宽度（默认为16个字节）
        //sheet.setDefaultColumnWidth(16);
        // 创建合并算法数组
        int rowLength = sheetDataList.size();
        int columnLength = sheetDataList.get(0).size();
        int[][] mergeArray = new int[rowLength][columnLength];
        for (int i = 0; i < sheetDataList.size(); i++) {
            // 每个 Sheet 页中的行数据
            Row row = sheet.createRow(i);
            List<Object> rowList = sheetDataList.get(i);
            for (int j = 0; j < rowList.size(); j++) {
                // 每个行数据中的单元格数据
                Object o = rowList.get(j);
                int v = 0;
                Cell cell = row.createCell(j);
                int columnValueLength = o.toString().trim().getBytes().length;
                if (columnValueLength * 256 > sheet.getColumnWidth(j)) {
                    if (columnValueLength > 255) {
                        sheet.setColumnWidth(j, 65280);
                    } else {
                        sheet.setColumnWidth(j, columnValueLength * 256);
                    }
                }
                if (i == 0) {
                    // 第一行为表头行
                    v = setCellValue(cell, o, headStyle);
                    //设置表头只适应宽度，全是文本情况下
                } else {
                    // 其他行为数据行，默认白底色
                    v = setCellValue(cell, o, rowStyle);
                }
                //}
                mergeArray[i][j] = v;
            }
        }
        // 合并单元格
        mergeCells(sheet, mergeArray);
        // 设置下拉列表
        setSelect(sheet, selectMap);
        // 写数据
        // 本地导出
        //FileOutputStream fos = null;
        try (FileOutputStream fos = response != null ? null : new FileOutputStream(file)) {
            if (response != null) {
                // 前端导出
                write(response, book, fileName);
            } else {
                //fos = new FileOutputStream(file);
                ByteArrayOutputStream ops = new ByteArrayOutputStream();
                book.write(ops);
                fos.write(ops.toByteArray());
            }
        } catch (Exception e) {
            log.error("文件导出异常," + e.getMessage(), e);
        }
    }

    /**
     * 合并当前Sheet页的单元格
     *
     * @param sheet      当前 sheet 页
     * @param mergeArray 合并单元格算法
     */
    private static void mergeCells(Sheet sheet, int[][] mergeArray) {
        // 横向合并
        for (int x = 0; x < mergeArray.length; x++) {
            int[] arr = mergeArray[x];
            boolean merge = false;
            int y1 = 0;
            int y2 = 0;
            for (int y = 0; y < arr.length; y++) {
                int value = arr[y];
                if (value == CELL_COLUMN_MERGE) {
                    if (!merge) {
                        y1 = y;
                    }
                    y2 = y;
                    merge = true;
                } else {
                    merge = false;
                    if (y1 > 0) {
                        sheet.addMergedRegion(new CellRangeAddress(x, x, (y1 - 1), y2));
                    }
                    y1 = 0;
                    y2 = 0;
                }
            }
            if (y1 > 0) {
                sheet.addMergedRegion(new CellRangeAddress(x, x, (y1 - 1), y2));
            }
        }
        // 纵向合并
        int xLen = mergeArray.length;
        int yLen = mergeArray[0].length;
        for (int y = 0; y < yLen; y++) {
            boolean merge = false;
            int x1 = 0;
            int x2 = 0;
            for (int x = 0; x < xLen; x++) {
                int value = mergeArray[x][y];
                if (value == CELL_ROW_MERGE) {
                    if (!merge) {
                        x1 = x;
                    }
                    x2 = x;
                    merge = true;
                } else {
                    merge = false;
                    if (x1 > 0) {
                        sheet.addMergedRegion(new CellRangeAddress((x1 - 1), x2, y, y));
                    }
                    x1 = 0;
                    x2 = 0;
                }
            }
            if (x1 > 0) {
                sheet.addMergedRegion(new CellRangeAddress((x1 - 1), x2, y, y));
            }
        }
    }


    private static void write(HttpServletResponse response, SXSSFWorkbook book, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String name = fileName + XLSX;
        //解决文件名中文乱码或者不显示
        String fileNameURL = URLEncoder.encode(name, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
        ServletOutputStream out = response.getOutputStream();
        book.write(out);
        out.flush();
        out.close();
    }


    /**
     * 文件名称解密方法 todo
     *
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String name = "程亚辉配置文件" + XLSX;
        String fileNameURL = URLEncoder.encode(name, "UTF-8");
        System.out.println(fileNameURL);
        String decode = URLDecoder.decode(fileNameURL);
        System.out.println(decode);
    }


    private static int setCellValue(Cell cell, Object o, CellStyle style) {
        // 设置样式
        cell.setCellStyle(style);
        // 数据为空时
        if (o == null) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue("");
            return CELL_OTHER;
        }
        // 是否为字符串
        if (o instanceof String) {
            String s = o.toString();
            //修復bug,0开头险种代码导出缺少0,类型不对
            /*if (isNumeric(s)) {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(Double.parseDouble(s));
                return CELL_OTHER;
            } else {*/
            cell.setCellType(CellType.STRING);
            cell.setCellValue(StringUtils.isBlank(s) ? "" : s);
            //}
            if (s.equals(ROW_MERGE)) {
                return CELL_ROW_MERGE;
            } else if (s.equals(COLUMN_MERGE)) {
                return CELL_COLUMN_MERGE;
            } else {
                return CELL_OTHER;
            }
        }
        // 是否为字符串
//        if (o instanceof Integer || o instanceof Long || o instanceof Double || o instanceof Float) {
        if (o instanceof Integer || o instanceof Long) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(Double.parseDouble(o.toString()));
            return CELL_OTHER;
        }
        // 是否为Boolean
        if (o instanceof Boolean) {
            cell.setCellType(CellType.BOOLEAN);
            cell.setCellValue((Boolean) o);
            return CELL_OTHER;
        }
        // 如果是BigDecimal，则默认3位小数
        if (o instanceof BigDecimal) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((BigDecimal) o).setScale(3, RoundingMode.HALF_UP).doubleValue());
            return CELL_OTHER;
        }
        // 如果是Date数据，则显示格式化数据
        if (o instanceof Date) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(formatDate((Date) o));
            return CELL_OTHER;
        }
        // 如果是其他，则默认字符串类型
        cell.setCellType(CellType.STRING);
        cell.setCellValue(o.toString());
        return CELL_OTHER;
    }

    private static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        //SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        //return format.format(date);
        return TimeUtils.format(date, DATE_FORMAT); //白猫扫描modify 20220608
    }


    private static void setSelect(Sheet sheet, Map<Integer, List<String>> selectMap) {
        if (selectMap == null || selectMap.isEmpty()) {
            return;
        }
        Set<Entry<Integer, List<String>>> entrySet = selectMap.entrySet();
        for (Entry<Integer, List<String>> entry : entrySet) {
            int y = entry.getKey();
            List<String> list = entry.getValue();
            if (list == null || list.isEmpty()) {
                continue;
            }
            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = list.get(i);
            }
            DataValidationHelper helper = sheet.getDataValidationHelper();
            CellRangeAddressList addressList = new CellRangeAddressList(1, 65000, y, y);
            DataValidationConstraint dvc = helper.createExplicitListConstraint(arr);
            DataValidation dv = helper.createValidation(dvc, addressList);
            if (dv instanceof HSSFDataValidation) {

                dv.setSuppressDropDownArrow(false);
            } else {
                dv.setSuppressDropDownArrow(true);
                dv.setShowErrorBox(true);
            }
            sheet.addValidationData(dv);
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        if ("0.0".equals(str)) {
            return true;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String getString(String s) {
        if (s == null) {
            return "";
        }
        if (s.isEmpty()) {
            return s;
        }
        return s.trim();
    }
}
