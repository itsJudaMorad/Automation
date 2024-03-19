package csvHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import base.PhoneNumberHandler;
import bmby.CsvReaderBmby;
import bmby.LeadBmby;
import callbox.CsvReaderCallbox;
import callbox.LeadCallbox;

public class test {

	public static void main(String[] args) throws IOException {
		ExcelToCSVConverter csvConverter = new ExcelToCSVConverter();
		final String ad120Folder = System.getProperty("user.dir")+"/ad120Files";

		
//		// bmby job
//		csvConverter.convertExcelToCSV(ad120Folder+"/bmby.xlsx", ad120Folder+"/bmby.csv");
//		CsvReaderBmby csvReader = new CsvReaderBmby();
//		List<LeadBmby> bmbyLeads = csvReader.convertToObj();
//		System.out.println(bmbyLeads.size());
//
//		for (LeadBmby lead : bmbyLeads) {
//			String phoneNumber = lead.getPhoneNumber();
//			lead.setPhoneNumber(PhoneNumberHandler.extractDigits(phoneNumber));
//		}
		
		// callbox job
		csvConverter.convertExcelToCSV(ad120Folder+"/callbox.xls", ad120Folder+"/callbox.csv");
		List<LeadCallbox> callboxLeads = CsvReaderCallbox.convertToObj();
		for (LeadCallbox lead: callboxLeads) {
			String phoneNumber = lead.getCustomerNumber();

		}
	}
}


