package base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bmby.LeadBmby;
import csvHandler.RunAd120;

public class MediaSourceStats extends RunAd120 {
	public int totalCounts = 0;
	public int isRelevant = 0;
	public int isSchedule = 0;
	public int isDone = 0;

	// Increment functions for each counter
	public void incrementTotalCounts() { this.totalCounts++; }
	public void incrementIsRelevant() { this.isRelevant++; }
	public void incrementIsSchedule() { this.isSchedule++; }
	public void incrementIsDone() { this.isDone++; }


	public static  Map<String, MediaSourceStats>  getSummaryLeadsData(List<SummaryLead> summaryLeads) {

		try {
		csvConverter.convertExcelToCSV(ad120Folder+"/bmby_r.xlsx", ad120Folder+"/bmby_r.csv");
		csvConverter.convertExcelToCSV(ad120Folder+"/bmby_sch.xlsx", ad120Folder+"/bmby_sch.csv");
		csvConverter.convertExcelToCSV(ad120Folder+"/bmby_can.xlsx", ad120Folder+"/bmby_can.csv");
		csvConverter.convertExcelToCSV(ad120Folder+"/bmby_done.xlsx", ad120Folder+"/bmby_done.csv");
		List<LeadBmby> bmbyRLeads = csvReader.convertToObj(ad120Folder+"/bmby_r.csv");
		List<LeadBmby> bmbySCHLeads = csvReader.convertToObj(ad120Folder+"/bmby_sch.csv");
		List<LeadBmby> bmbyCANLeads = csvReader.convertToObj(ad120Folder+"/bmby_can.csv");
		List<LeadBmby> bmbyDONELeads = csvReader.convertToObj(ad120Folder+"/bmby_done.csv");
		

		for (SummaryLead summeryLead : summaryLeads) {
			String summeryLeadPhone = summeryLead.phoneNumber;

			// Check in bmbyRLeads
			boolean existsInR = bmbyRLeads.stream().anyMatch(lead -> lead.getPhoneNumber().contains(summeryLeadPhone));
			// Check in bmbySCHLeads
			boolean existsInSCH = bmbySCHLeads.stream().anyMatch(lead -> lead.getPhoneNumber().contains(summeryLeadPhone));
			// Check in bmbyCANLeads
			boolean existsInCAN = bmbyCANLeads.stream().anyMatch(lead -> lead.getPhoneNumber().contains(summeryLeadPhone));
			// Check in bmbyDONELeads
			boolean existsInDONE = bmbyDONELeads.stream().anyMatch(lead -> lead.getPhoneNumber().contains(summeryLeadPhone));

			if(existsInR) {
				summeryLead.isRellevant = true;
			} 
			if (existsInSCH) { //|| existsInCAN) {
				summeryLead.isSechedule = true;
				summeryLead.isRellevant = true;
			}
			if(existsInCAN) {
				summeryLead.isSechedule = true;
				summeryLead.isRellevant = true;
			}
			if (existsInDONE) {
				summeryLead.isDone = true;
				summeryLead.isRellevant = true;
			}
		}
		} catch (Exception e) {
			System.err.println("Something Worng with the files [relevant / scheduled / cancel / done] \n "
					+ "1. Check that the files are exist \n2. Check the name of the files. it should match to README file" );
		}
		Map<String, MediaSourceStats> mediaSourceCounts = new HashMap<>();

		for (SummaryLead summeryLead : summaryLeads) {
			String mediaSource = summeryLead.sourceAndPlatform.getMediaSource();
			MediaSourceStats stats = mediaSourceCounts.computeIfAbsent(mediaSource, k -> new MediaSourceStats());

			stats.incrementTotalCounts();

			if (summeryLead.isRellevant) {
				stats.incrementIsRelevant();
			}
			if (summeryLead.isSechedule) {
				stats.incrementIsSchedule();
			}
			if (summeryLead.isDone) {
				stats.incrementIsDone();
			}
		}
		return mediaSourceCounts;
	}

	public static Map<String, MediaSourceAndPlatform> getMediaSourcePriorityMap(List<EntryPhoneAndMedia> allLeadNumbers) {
		// get unique Phone number 
		Map<String, List<MediaSourceAndPlatform>> phoneNumberToMediaSourcesMap = allLeadNumbers.stream()
				.collect(Collectors.groupingBy(
						EntryPhoneAndMedia::getPhoneNumber,
						Collectors.mapping(entry -> new MediaSourceAndPlatform(entry.getMediaSource(), entry.getMediaPlatform()),
								Collectors.toList())
						));
		phoneNumberToMediaSourcesMap.forEach((phone, mediaSourcesAndPlatforms) -> {
		});

		// for each phone number get the priority source media
		Map<String, MediaSourceAndPlatform> phoneNumberToPriorityMediaSourceMap = phoneNumberToMediaSourcesMap.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey, // Phone number as the key
						entry -> MediaSourcePriority.getPriorityMediaSource(entry.getValue()) // Get the priority MediaSourceAndPlatform
						));
		phoneNumberToPriorityMediaSourceMap.forEach((phone, mediaSourceAndPlatform) -> {
		});

		return phoneNumberToPriorityMediaSourceMap;
	}

	public static Map<String, Map<String, Long>> getStatsOfMediaSourceAndPlatform(Map<String, MediaSourceAndPlatform>phoneNumberToPriorityMediaSourceMap) {
		// count the appearance of each media source with 
		Map<String, Map<String, Long>> mediaSourcePlatformCounts = phoneNumberToPriorityMediaSourceMap.values().stream()
				.collect(Collectors.groupingBy(
						mediaSourceAndPlatform -> mediaSourceAndPlatform.getMediaSource(),
						Collectors.groupingBy(
								mediaSourceAndPlatform -> mediaSourceAndPlatform.getPlatform(),
								Collectors.counting()
								)
						));
		return mediaSourcePlatformCounts;
	}
}
