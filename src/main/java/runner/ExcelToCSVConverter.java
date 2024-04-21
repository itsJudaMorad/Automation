package runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToCSVConverter {

	public void convertExcelToCSV(String excelFilePath, String csvFilePath) {
		 File excelFileToCheck = new File(excelFilePath);
		    
		    // Check if the file exists
		    if (!excelFileToCheck.exists()) {
		        System.err.println("The specified Excel file ["+excelFilePath+"] does not exist. Please check the file path.");
		        System.err.println("The program STOP running.");
		        System.exit(1);
		    }
		
		try {
			FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
			Workbook workbook;
			if (excelFilePath.toLowerCase().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(excelFile);
			} else {
				workbook = WorkbookFactory.create(excelFile);
			}

			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvFilePath), StandardCharsets.UTF_8);
			 writer.write('\ufeff');
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
	    DataFormatter formatter = new DataFormatter();
	    // Format the cell's content and ensure it's enclosed in quotes if necessary
	    String cellValue = formatter.formatCellValue(cell);
	    // Check if the cell contains a character that might need the cell to be quoted
	    cellValue = "\"" + cellValue.replace("\"", "\"\"") + "\"";
	    return cellValue;
	}
}
