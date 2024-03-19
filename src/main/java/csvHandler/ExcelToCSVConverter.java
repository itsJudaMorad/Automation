package csvHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToCSVConverter {

	public void convertExcelToCSV(String excelFilePath, String csvFilePath) {
		try {
			FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
			Workbook workbook;
			if (excelFilePath.toLowerCase().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(excelFile);
			} else {
				workbook = WorkbookFactory.create(excelFile);
			}

			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvFilePath), StandardCharsets.UTF_8);
			PrintWriter csvWriter = new PrintWriter(writer);

			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				for (Cell cell : row) {
					csvWriter.print(cellToString(cell));
					csvWriter.print(",");
				}
				csvWriter.println();
			}

			workbook.close();
			csvWriter.close();
			excelFile.close();

			System.out.println("Conversion completed successfully!");
		} catch (IOException | EncryptedDocumentException e) {
			e.printStackTrace();
		}
	}
	private static String cellToString(Cell cell) {
	    String cellValue = "";
	    switch (cell.getCellType()) {
	        case STRING:
	            cellValue = cell.getStringCellValue();
	            break;
	        case NUMERIC:
	            cellValue = String.valueOf(cell.getNumericCellValue());
	            break;
	        case BOOLEAN:
	            cellValue = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case FORMULA:
	            cellValue = cell.getCellFormula();
	            break;
	    }
	    // Remove commas from cell value
	    return cellValue.replaceAll(",", "");
	}
}
