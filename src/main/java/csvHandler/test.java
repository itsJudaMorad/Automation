package csvHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.rowset.RowSetMetaDataImpl;

import base.EntryPhoneAndMedia;
import base.EntryPhoneAndMedia.Platform;
import base.MediaSourceAndPlatform;
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
		
		// get unique Phone number 
		Map<String, List<MediaSourceAndPlatform>> phoneNumberToMediaSourcesMap = allLeadNumbers.stream()
				.collect(Collectors.groupingBy(
						EntryPhoneAndMedia::getPhoneNumber,
						Collectors.mapping(entry -> new MediaSourceAndPlatform(entry.getMediaSource(), entry.getMediaPlatform()),
								Collectors.toList())
						));
		phoneNumberToMediaSourcesMap.forEach((phone, mediaSourcesAndPlatforms) -> {
            System.out.println("Phone Number: " + phone + " -> Media Sources and Platforms: " + mediaSourcesAndPlatforms);
        });

		// for each phone number get the priority source media
		Map<String, MediaSourceAndPlatform> phoneNumberToPriorityMediaSourceMap = phoneNumberToMediaSourcesMap.entrySet().stream()
		        .collect(Collectors.toMap(
		                Map.Entry::getKey, // Phone number as the key
		                entry -> MediaSourcePriority.getPriorityMediaSource(entry.getValue()) // Get the priority MediaSourceAndPlatform
		        ));
		phoneNumberToPriorityMediaSourceMap.forEach((phone, mediaSourceAndPlatform) -> {
		    System.out.println("Phone Number: " + phone + " -> Priority Media Source and Platform: " + mediaSourceAndPlatform.getMediaSource() +" | "+ mediaSourceAndPlatform.getPlatform());
		});
		
		// count the appearance of each media source with 
		Map<String, Map<String, Long>> mediaSourcePlatformCounts = phoneNumberToPriorityMediaSourceMap.values().stream()
		        .collect(Collectors.groupingBy(
		                mediaSourceAndPlatform -> mediaSourceAndPlatform.getMediaSource(),
		                Collectors.groupingBy(
		                        mediaSourceAndPlatform -> mediaSourceAndPlatform.getPlatform(),
		                        Collectors.counting()
		                )
		        ));

		// Step 2: Display the results
		mediaSourcePlatformCounts.forEach((mediaSource, platformCounts) -> {
		    long total = platformCounts.values().stream().mapToLong(count -> count).sum();
		    System.out.println("Priority Media Source: " + mediaSource + ", Total: " + total);
		    platformCounts.forEach((platform, count) -> {
		        System.out.println("\t" + platform + ": " + count);
		    });
		});
		
		WriteMediaSourceAndPlatforms.writeCSV(mediaSourcePlatformCounts, System.getProperty("user.dir")+"/media_source_platform_counts.csv");

//			Map<String, List<String>> phoneNumberToMediaSourcesMap = allLeadNumbers.stream()
//					.collect(Collectors.groupingBy(
//							EntryPhoneAndMedia::getPhoneNumber, // Assuming Entry class has a getPhoneNumber method
//							Collectors.mapping(EntryPhoneAndMedia::getMediaSource, Collectors.toList())
//							));
//			phoneNumberToMediaSourcesMap.forEach((phone, mediaSources) -> {
//				System.out.println("Phone Number: " + phone + " -> Media Sources: " + mediaSources);
//			});
	
//			Map<String, String> phoneNumberToPriorityMediaSourceMap = phoneNumberToMediaSourcesMap.entrySet().stream()
//					.collect(Collectors.toMap(
//							Map.Entry::getKey,
//							entry -> MediaSourcePriority.getPriorityMediaSource(entry.getValue())
//							));
//			phoneNumberToPriorityMediaSourceMap.forEach((phone, mediaSource) -> {
//				System.out.println("Phone Number: " + phone + " -> Priority Media Source: " + mediaSource);
//	
//			});

		//		// Count the media source
		//		Map<String, Long> mediaSourceCounts = phoneNumberToPriorityMediaSourceMap.values().stream()
		//				.collect(Collectors.groupingBy(mediaSource -> mediaSource, Collectors.counting()));
		//
		//		mediaSourceCounts.forEach((mediaSource, count) -> {
		//			System.out.println("Media Source: " + mediaSource + " -> Count: " + count);
		//		});
		//		
		//		
		// count the media source and platform segmentation
		//		 Map<String, Map<String, Long>> mediaSourcePlatformCounts = allLeadNumbers.stream()
		//		            .collect(Collectors.groupingBy(
		//		                EntryPhoneAndMedia::getMediaSource,
		//		                Collectors.groupingBy(
		//		                    EntryPhoneAndMedia::getMediaPlatform,
		//		                    Collectors.counting()
		//		                )
		//		            ));

		//		        mediaSourcePlatformCounts.forEach((mediaSource, platformCounts) -> {
		//		            System.out.println("Media Source: " + mediaSource);
		//		            platformCounts.forEach((platform, count) -> {
		//		                System.out.println("\tPlatform: " + platform + ", Count: " + count);
		//		            });
		//		        });
	}
}


