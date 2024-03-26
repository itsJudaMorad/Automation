package csvHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.EntryPhoneAndMedia;
import base.EntryPhoneAndMedia.Platform;
import base.MediaSourceAndPlatform;
import base.MediaSourceStats;
import base.PhoneNumberHandler;
import base.SummaryLead;
import bmby.CsvReaderBmby;
import bmby.LeadBmby;
import bmby.SourceRulesBmby;
import callbox.CsvReaderCallbox;
import callbox.LeadCallbox;
import callbox.SourceRulesCallbox;

public class test {

	public static ExcelToCSVConverter csvConverter = new ExcelToCSVConverter();
	public static CsvReaderBmby csvReader = new CsvReaderBmby();
	public static final String ad120Folder = System.getProperty("user.dir")+"/ad120Files";
	
	public static void main(String[] args) throws IOException {

		List<EntryPhoneAndMedia> allLeadNumbers = new ArrayList<EntryPhoneAndMedia>();

		// bmby job
		csvConverter.convertExcelToCSV(ad120Folder+"/bmby.xlsx", ad120Folder+"/bmby.csv");
		CsvReaderBmby csvReader = new CsvReaderBmby();
		List<LeadBmby> bmbyLeads = csvReader.convertToObj(System.getProperty("user.dir")+"/ad120Files/bmby.csv");
		System.out.println(bmbyLeads.size());

		for (LeadBmby lead : bmbyLeads) {
			if(PhoneNumberHandler.containsPhoneNumber(lead.getPhoneNumber())) {
				lead.setPhoneNumber(PhoneNumberHandler.extractDigits(lead.getPhoneNumber()));
				lead.setMedia(SourceRulesBmby.setSourceMediaByRules(lead));
				allLeadNumbers.add(new EntryPhoneAndMedia(lead.getPhoneNumber(), lead.getMedia(), Platform.BMBY));
			}
		}	

		// callbox job
		csvConverter.convertExcelToCSV(ad120Folder+"/callbox.xls", ad120Folder+"/callbox.csv");
		List<LeadCallbox> callboxLeads = CsvReaderCallbox.convertToObj();
		for (LeadCallbox lead: callboxLeads) {
			if(PhoneNumberHandler.containsPhoneNumber(lead.getCustomerNumber())) {
				lead.setCustomerNumber(PhoneNumberHandler.extractDigits(lead.getCustomerNumber()));
				lead.setMediaSource(SourceRulesCallbox.setMediaSourceByRules(lead));
				allLeadNumbers.add(new EntryPhoneAndMedia(lead.getCustomerNumber(), lead.getMediaSource(), Platform.CALLBOX));
			}
		}
		
		Map<String, MediaSourceAndPlatform> phoneNumberToPriorityMediaSourceMap = MediaSourceStats.getMediaSourcePriorityMap(allLeadNumbers);
		// count the appearance of each media source with 
		Map<String, Map<String, Long>> mediaSourcePlatformCounts = MediaSourceStats.getStatsOfMediaSourceAndPlatform(phoneNumberToPriorityMediaSourceMap);
		WriteMediaSourceAndPlatforms.writeCSV_MediaSourceAndPlatformStats(mediaSourcePlatformCounts, System.getProperty("user.dir")+"/media_source_platform_counts.csv");

		List<SummaryLead> summeryLeads = new ArrayList<SummaryLead>();
		phoneNumberToPriorityMediaSourceMap.forEach((phone, mediaSourceAndPlatform) -> {
			summeryLeads.add(new SummaryLead(phone, mediaSourceAndPlatform));
		});
		
		Map<String, MediaSourceStats> mediaSourceCounts = MediaSourceStats.getSummaryLeadsData(summeryLeads);
		WriteMediaSourceAndPlatforms.writeCSV_SummaryLeadStats(mediaSourceCounts,System.getProperty("user.dir")+"/mediaSource_leadsSummary.csv");
		
		System.out.println("\n\n\n #### Ad 120 reports are successfuly created! ####\n"
				+"Paths to the CSV Reports: \n"
				+ "Media Source and Platform stats: " +System.getProperty("user.dir")+"/media_source_platform_counts.csv\n"
				+ "Media Source and leads summary: "+System.getProperty("user.dir")+"/mediaSource_leadsSummary.csv"); 
		
	}
}


