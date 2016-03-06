package org.dy.springmvc.util.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

public class ExcelUtilTest {
	static String file = "temp/excel.xls";

	@Test
	public void createExcelTest() {
		try {
			Map<String, List<List<Object>>> data = new HashMap<String, List<List<Object>>>();
			data.put("sheet 1", getSheetData());
			data.put("sheet 2", getSheetData());

			FileOutputStream fos = new FileOutputStream(file);
			ExcelUtil.createExcel(file, data);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void readExcelTest() {
		try {
			Map<String, List<List<String>>> data = ExcelUtil.readExcel(file);
			if (data != null && !data.isEmpty()) {
				Set<Entry<String, List<List<String>>>> set = data.entrySet();
				for (Entry<String, List<List<String>>> entry : set) {
					System.out.println("sheet : " + entry.getKey());
					List<List<String>> sheet = entry.getValue();
					if (CollectionUtils.isNotEmpty(sheet)) {
						for (List<String> row : sheet) {
							if (CollectionUtils.isNotEmpty(row)) {
								for (String cell : row) {
									System.out.print("  " + cell);
								}
								System.out.println();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void udpateCellTest() {
		try {
			String sheetName = "sheet 2";
			int rowNum = 2;
			int cellNum = 3;
			String value = "test";
			ExcelUtil.udpateCell(file, sheetName, rowNum, cellNum, (Object) value);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	private List<List<Object>> getSheetData() {
		List<List<Object>> data = new ArrayList<List<Object>>();
		List<Object> rowData = null;
		for (int i = 0; i < 5; i++) {
			rowData = new ArrayList<Object>();
			for (int j = 0; j < 10; j++) {
				String string = String.valueOf(j);
				rowData.add(string);
			}
			data.add(rowData);
		}
		return data;
	}
}
