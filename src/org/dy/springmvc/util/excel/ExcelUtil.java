package org.dy.springmvc.util.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelUtil {
	/**
	 * 
	 * create Excel with only one sheet
	 * 
	 * @param out
	 *            the outputStream of excel file
	 * @param data
	 *            excel data
	 * @return void
	 * @exception/throws IOException
	 * @since createExcel
	 */
	public static void createExcel(String file,
			Map<String, List<List<Object>>> data) throws IOException {
		// create workbook
		FileOutputStream fos = new FileOutputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFFont contentFont = contentFont(workbook);
		HSSFFont headFont = headFont(workbook);

		HSSFCellStyle cellStyleHead = headerSetting(workbook, headFont);
		HSSFCellStyle cellStyleContent = contentSetting(workbook, contentFont);

		Set<Entry<String, List<List<Object>>>> set = data.entrySet();
		if (CollectionUtils.isNotEmpty(set)) {
			for (Entry<String, List<List<Object>>> entry : set) {
				String sheetName = entry.getKey();
				List<List<Object>> sheetData = entry.getValue();
				generateSheet(workbook, sheetName, sheetData, cellStyleHead,
						cellStyleContent);
			}
		}

		workbook.write(fos);
		workbook.close();
		fos.close();
	}

	private static void generateSheet(HSSFWorkbook workbook, String sheetName,
			List<List<Object>> data, HSSFCellStyle cellStyleHead,
			HSSFCellStyle cellStyleContent) {
		HSSFSheet sheet = workbook.createSheet(sheetName);
		int rowIndex = 0;
		HSSFRow row = null;
		int size = 0;
		if (CollectionUtils.isNotEmpty(data)) {
			for (List<Object> rowData : data) {
				row = sheet.createRow(rowIndex);
				size = rowData.size();
				if (0 == rowIndex) {
					for (int i = 0; i < size; i++) {
						Object cellData = rowData.get(i);
						setCell(row, i, cellData, cellStyleHead);
					}
				}
				for (int i = 0; i < size; i++) {
					Object cellData = rowData.get(i);
					setCell(row, i, cellData, cellStyleContent);
				}
				rowIndex++;
			}
		}
	}

	private static HSSFCellStyle contentSetting(HSSFWorkbook workbook,
			HSSFFont contentFont) {
		HSSFCellStyle cellStyleContent = workbook.createCellStyle();
		cellStyleContent.setFont(contentFont);
		// border
		cellStyleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleContent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleContent.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleContent.setBorderRight(HSSFCellStyle.BORDER_THIN);
		return cellStyleContent;
	}

	private static HSSFFont headFont(HSSFWorkbook workbook) {
		HSSFFont headFont = workbook.createFont();
		headFont.setFontHeightInPoints((short) 12);
		headFont.setFontName("宋体");
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		return headFont;
	}

	private static HSSFFont contentFont(HSSFWorkbook workbook) {
		HSSFFont contentFont = workbook.createFont();
		contentFont.setFontHeightInPoints((short) 12);
		contentFont.setFontName("宋体");
		return contentFont;
	}

	private static HSSFCellStyle headerSetting(HSSFWorkbook workbook,
			HSSFFont headFont) {
		HSSFCellStyle cellStyleHead = workbook.createCellStyle();

		cellStyleHead.setFont(headFont);

		// color
		cellStyleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHead.setFillForegroundColor(new HSSFColor.PALE_BLUE()
				.getIndex());
		// border
		cellStyleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);
		return cellStyleHead;
	}

	private static void setCell(HSSFRow row, int index, Object value,
			HSSFCellStyle cellStyle) {
		HSSFCell cell = row.createCell(index, HSSFCell.CELL_TYPE_STRING);
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		String temp = "";
		if (value != null) {
			if (value instanceof String) {
				temp = (String) value;
			} else if (value instanceof Integer) {
				temp = String.valueOf(value);
			} else if (value instanceof Date) {
				temp = value.toString();
			}
		}

		cell.setCellValue(new HSSFRichTextString(temp));
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 
	 * read Excel
	 * 
	 * @param is
	 *            the inputStream of excel file
	 * @return List<Map<String, String>> data
	 * @exception/throws IOException
	 * @since createExcel
	 */
	public static Map<String, List<List<String>>> readExcel(String file)
			throws IOException {
		FileInputStream fis = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fis);
		Map<String, List<List<String>>> data = new HashMap<String, List<List<String>>>();
		List<List<String>> sheetData = null;
		List<String> rowData = null;

		HSSFSheet hssfSheet = null;
		HSSFRow hssfRow = null;
		HSSFCell hssfCell = null;
		int sheetCount = hssfWorkbook.getNumberOfSheets();
		int rowCount = 0, cellCount = 0;

		for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {
			hssfSheet = hssfWorkbook.getSheetAt(sheetNum);
			if (hssfSheet == null) {
				continue;
			}
			rowCount = hssfSheet.getLastRowNum();
			sheetData = new ArrayList<List<String>>(rowCount);
			data.put(hssfSheet.getSheetName(), sheetData);
			for (int rowNum = 0; rowNum <= rowCount; rowNum++) {
				hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				cellCount = hssfRow.getLastCellNum();
				rowData = new ArrayList<String>(cellCount);
				sheetData.add(rowData);
				for (int cellNum = 0; cellNum <= cellCount; cellNum++) {
					hssfCell = hssfRow.getCell(cellNum);
					if (hssfCell == null) {
						continue;
					}
					rowData.add(getValue(hssfCell));
				}
			}
		}
		hssfWorkbook.close();
		fis.close();
		return data;

	}

	private static String getValue(HSSFCell hssfCell) {
		String value = null;
		int type = hssfCell.getCellType();
		switch (type) {
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(hssfCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = String.valueOf(hssfCell.getBooleanCellValue());
			break;
		default:
			value = String.valueOf(hssfCell.getStringCellValue());
			break;
		}
		return value;
	}

	public static void udpateCell(String file, String sheetName,
			int rowNum, int cellNum, Object value) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fis);
		HSSFSheet hssfSheet = hssfWorkbook.getSheet(sheetName);
		if(hssfSheet == null){
			hssfSheet = hssfWorkbook.createSheet(sheetName);
		}
		
		HSSFRow hssfRow = hssfSheet.getRow(rowNum-1);
		if(hssfRow == null){
			hssfSheet.createRow(rowNum);
		}
		
		HSSFCell hssfCell = hssfRow.getCell(cellNum-1);
		if(hssfCell == null){
			hssfRow.createCell(rowNum);
		}
		String temp = "";
		if (value != null) {
			if (value instanceof String) {
				temp = (String) value;
			} else if (value instanceof Integer) {
				temp = String.valueOf(value);
			} else if (value instanceof Date) {
				temp = value.toString();
			}
		}

		hssfCell.setCellValue(new HSSFRichTextString(temp));
		FileOutputStream fos = new FileOutputStream(file);
		hssfWorkbook.write(fos);
		hssfWorkbook.close();
		fis.close();
		fos.close();
	}

}
