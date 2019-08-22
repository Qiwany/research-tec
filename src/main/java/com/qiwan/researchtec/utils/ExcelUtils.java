package com.qiwan.researchtec.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <br>类 名: ExcelUtils 
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com 
 * <br>创 建: 2019年5月21日 上午11:39:15 
 * <br>版 本: v1.0.0
 */
public class ExcelUtils {
	
	/**
	 * @Description:根据excel文件路径解析excel所有sheet的方法（起始行、列都为0）
	 * @param filePath 待解析文件路径
	 * @return
	 */
	public static List<List<List<String>>> doParse(String filePath){
		return doParse(filePath, -1, 0, 0);
	}
	
	/**
	 * @Description:根据excel文件流和文件后缀解析excel
	 * @param is excel文件流
	 * @param suffix excel文件后缀
	 * @return
	 */
	public static List<List<List<String>>> doParse(InputStream is, String suffix){
		return doParse(is, suffix, -1, 0, 0);
	}
	
	/**
	 * @Description:根据excel文件路径解析excel（指定sheet、起始行、起始列）
	 * @param filePath 待解析文件路径
	 * @param sheetIndex 指定解析的sheet（第一个为0，所有-1）
	 * @param startRow 指定解析时开始行（默认0）
	 * @param startColumn 指定解析时开始列（默认0）
	 */
	public static List<List<List<String>>> doParse(String filePath, int sheetIndex, int startRow, int startColumn) {
		Workbook wb = readExcel(filePath);
		return doParse(wb, sheetIndex, startRow, startColumn);
	}
	
	/**
	 * @Description:根据excel文件流解析excel（指定sheet、起始行、起始列）
	 * @param is excel文件流
	 * @param suffix excel文件后缀（xls或xlsx）
	 * @param sheetIndex 指定解析的sheet（第一个为0，所有-1）
	 * @param startRow 指定解析时开始行（默认0）
	 * @param startColumn 指定解析时开始列（默认0）
	 * @return
	 */
	public static List<List<List<String>>> doParse(InputStream is, String suffix, int sheetIndex, int startRow, int startColumn) {
		Workbook wb = readExcel(is, suffix);
		return doParse(wb, sheetIndex, startRow, startColumn);
	}
	
	/**
	 * @Description:根据 Workbook 解析excel（指定sheet、起始行、起始列）
	 * @param wb Workbook
	 * @param sheetIndex 指定解析的sheet（第一个为0，所有-1）
	 * @param startRow 指定解析时开始行（默认0）
	 * @param startColumn 指定解析时开始列（默认0）
	 * @return
	 */
	public static List<List<List<String>>> doParse(Workbook wb, int sheetIndex, int startRow, int startColumn) {
		if(wb == null) return null;
		Sheet sheet = null;
		Row row = null;
		String cellData = null;
		
		List<List<List<String>>> sheetsList = new ArrayList<>();//存放所有sheet的数据
		List<List<String>> rowList = null;
		List<String> cellList = null;
		
		int startSheet = 0;
		int numberOfSheets = 0;
		if(sheetIndex == -1){//解析所有sheet
			numberOfSheets = wb.getNumberOfSheets();//获取sheet数
		} else {
			startSheet = sheetIndex;
			numberOfSheets = sheetIndex + 1;
		}
		
		for (int i = startSheet; i < numberOfSheets; i++) {//遍历所有sheet
			
			rowList = new ArrayList<>();//存放当前sheet所有行数据
			sheet = wb.getSheetAt(i);
			if(sheet == null) continue;
			int numberOfRows = sheet.getPhysicalNumberOfRows();// 获取当前sheet最大行数
			
			for (int j = startRow; j < numberOfRows; j++) {//遍历当前sheet所有行
				
				cellList = new ArrayList<>();//存放每行单元格数据
				row = sheet.getRow(j);
				if(row == null) continue;
				int numberOfColnums = row.getPhysicalNumberOfCells();// 获取最大列数
				
				for (int k = startColumn; k < numberOfColnums; k++) {//遍历当前行所有列
					cellData = (String) getCellFormatValue(row.getCell(k));
					cellList.add(cellData);
				}
				rowList.add(cellList);
			}
			sheetsList.add(rowList);
		}
		return sheetsList;
	}
	
	/**
	 * @Description:根据excel文件路径获取Workbook
	 * @param filePath excel文件所在路径
	 * @return
	 */
	private static Workbook readExcel(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			if (".xls".equals(extString)) {
				return new HSSFWorkbook(is);
			}
			if (".xlsx".equals(extString)) {
				return new XSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description:根据excel文件流获取Workbook
	 * @param is excel文件流
	 * @param suffix excel文件后缀（xls或xlsx）
	 * @return
	 */
	private static Workbook readExcel(InputStream is, String suffix) {
		if (is == null) return null;
		try {
			if (".xls".equals(suffix)) {
				return new HSSFWorkbook(is);
			}
			if (".xlsx".equals(suffix)) {
				return new XSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description:由单元格获取单元格内容
	 * @param cell 单元格
	 * @return
	 */
	private static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			// 判断cell类型
			switch (cell.getCellType()) {
			case NUMERIC: {
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			}
			case FORMULA: {
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue();// 转换为日期格式YYYY-mm-dd
				} else {
					cellValue = String.valueOf(cell.getNumericCellValue());// 数字
				}
				break;
			}
			case STRING: {
				cellValue = cell.getRichStringCellValue().getString();
				break;
			}
			default:
				cellValue = null;
			}
		}
		return cellValue;
	}
}
