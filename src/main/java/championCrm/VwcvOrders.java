package championCrm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor.Order;

import runner.Run;

public class VwcvOrders extends Run {
//		@CsvBindByName(column = " טלפון 1")
	    private String name;
//		@CsvBindByName(column = " טלפון 1")
	    private String phone1;

//	    @CsvBindByName(column = " טלפון 2")
	    private String phone2;

//	    @CsvBindByName(column = " תאור קבוצת דגם")
	    private String modelGroupDescription;

//	    @CsvBindByName(column = " דגם")
	    private String model;

//	    @CsvBindByName(column = " תאור דגם")
	    private String modelDescription;

	    // Getters and setters
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getPhone1() { return phone1; }
	    public void setPhone1(String phone1) { this.phone1 = phone1; }

	    public String getPhone2() { return phone2; }
	    public void setPhone2(String phone2) { this.phone2 = phone2; }

	    public String getModelGroupDescription() { return modelGroupDescription; }
	    public void setModelGroupDescription(String modelGroupDescription) { this.modelGroupDescription = modelGroupDescription; }

	    public String getModel() { return model; }
	    public void setModel(String model) { this.model = model; }

	    public String getModelDescription() { return modelDescription; }
	    public void setModelDescription(String modelDescription) { this.modelDescription = modelDescription; }
	    
	    public static String[] headers = {
				"phone1", // טלפון 1
				"phone2", // טלפון 2
				"modelGroupDescription", // תאור קבוצת דגם
				"model", // דגם
				"modelDescription", // תאור דגם
		};
	    public static BufferedReader getReaderWithoutBOM(String filePath) throws IOException {
	        FileInputStream fis = new FileInputStream(filePath);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
	        // Skip BOM if present
	        reader.mark(1);
	        if (reader.read() != 0xFEFF) {
	            reader.reset();
	        }
	        return reader;
	    }
	    public static List<VwcvOrders> convertCSVToObject(String csvFilePath, String[] headers) throws IOException {
	    	List<VwcvOrders> orders = new ArrayList<>();

	        try (
	            BufferedReader reader = getReaderWithoutBOM(csvFilePath);
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
	        ) {
	            // Print headers to verify
	            Map<String, Integer> headerMap = csvParser.getHeaderMap();
	            for (String header : headerMap.keySet()) {
	                System.out.println("Header: '" + header + "'");
	            }

	            for (CSVRecord csvRecord : csvParser) {
	            	VwcvOrders order = new VwcvOrders();
	                order.setPhone1(csvRecord.get("טלפון 1"));
	                order.setPhone2(csvRecord.get("טלפון 2"));
	                order.setModelGroupDescription(csvRecord.get("תאור קבוצת דגם"));
	                order.setModel(csvRecord.get("דגם"));
	                order.setModelDescription(csvRecord.get("תאור דגם"));
	                orders.add(order);
	            }
	        }
	        return orders;
	    }
	    
	    public static void main(String[] args) throws IOException {
	    	String[] headers = {" טלפון 1", " טלפון 2", " תאור קבוצת דגם", " דגם", " תאור דגם"};
	        List<VwcvOrders> orders = convertCSVToObject(championCSV+"/orders.csv",headers);

	        for (VwcvOrders order : orders) {
	            System.out.println("Phone 1: " + order.getPhone1());
	            System.out.println("Phone 2: " + order.getPhone2());
	            System.out.println("Model Group Description: " + order.getModelGroupDescription());
	            System.out.println("Model: " + order.getModel());
	            System.out.println("Model Description: " + order.getModelDescription());
	            System.out.println("------");
	        }
	    }
}
