package csvHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import base.EntryPhoneAndMedia;
import base.MediaSourcePriority;
import base.PhoneNumberHandler;
import bmby.CsvReaderBmby;
import bmby.LeadBmby;
import bmby.SourceRulesBmby;
import callbox.CsvReaderCallbox;
import callbox.LeadCallbox;
import callbox.SourceRulesCallbox;

public class test {
	
	public static void main(String[] args) throws IOException {
		ExcelToCSVConverter csvConverter = new ExcelToCSVConverter();
		final String ad120Folder = System.getProperty("user.dir")+"/ad120Files";

		List<EntryPhoneAndMedia> allLeadNumbers = new ArrayList<EntryPhoneAndMedia>();

		// bmby job
		csvConverter.convertExcelToCSV(ad120Folder+"/bmby.xlsx", ad120Folder+"/bmby.csv");
		CsvReaderBmby csvReader = new CsvReaderBmby();
		List<LeadBmby> bmbyLeads = csvReader.convertToObj();
		System.out.println(bmbyLeads.size());

		for (LeadBmby lead : bmbyLeads) {
			String phoneNumber = lead.getPhoneNumber();
			lead.setPhoneNumber(PhoneNumberHandler.extractDigits(phoneNumber));
			lead.setMedia(SourceRulesBmby.setSourceMediaByRules(lead));
			allLeadNumbers.add(new EntryPhoneAndMedia(lead.getPhoneNumber(), lead.getMedia()));
		}	

		// callbox job
		csvConverter.convertExcelToCSV(ad120Folder+"/callbox.xls", ad120Folder+"/callbox.csv");
		List<LeadCallbox> callboxLeads = CsvReaderCallbox.convertToObj();
		for (LeadCallbox lead: callboxLeads) {
			lead.setCustomerNumber(PhoneNumberHandler.extractDigits(lead.getCustomerNumber()));
			lead.setMediaSource(SourceRulesCallbox.setMediaSourceByRules(lead));
			allLeadNumbers.add(new EntryPhoneAndMedia(lead.getCustomerNumber(), lead.getMediaSource()));
		}

		Map<String, List<String>> phoneNumberToMediaSourcesMap = allLeadNumbers.stream()
				.collect(Collectors.groupingBy(
						EntryPhoneAndMedia::getPhoneNumber, // Assuming Entry class has a getPhoneNumber method
						Collectors.mapping(EntryPhoneAndMedia::getMediaSource, Collectors.toList())
						));
		phoneNumberToMediaSourcesMap.forEach((phone, mediaSources) -> {
			System.out.println("Phone Number: " + phone + " -> Media Sources: " + mediaSources);
		});
		
        Map<String, String> phoneNumberToPriorityMediaSourceMap = phoneNumberToMediaSourcesMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> MediaSourcePriority.getPriorityMediaSource(entry.getValue())
                ));
        phoneNumberToPriorityMediaSourceMap.forEach((phone, mediaSource) -> {
            System.out.println("Phone Number: " + phone + " -> Priority Media Source: " + mediaSource);
        });
	}
}


