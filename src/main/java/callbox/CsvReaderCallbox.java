package callbox;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvReaderCallbox {
	public static  List<LeadCallbox> convertToObj() throws IOException {
		  String csvFile =  System.getProperty("user.dir")+"/ad120Files/callbox.csv";

	        ColumnPositionMappingStrategy<LeadCallbox> strategy = new ColumnPositionMappingStrategy<>();
	        strategy.setType(LeadCallbox.class);
	        String[] memberFieldsToBindTo = {
	            "name", "customerNumber", "trackingSource", "page", "duration", 
	            "talkTime", "hourOfDay", "date", "time", "tags", "campaign", 
	            "source", "medium", "keyword", "adgroupId", "campaignId", "email", 
	            "callPath", "device", "sourceTag", "formName", "keywordSpotting"
	        };
	        strategy.setColumnMapping(memberFieldsToBindTo);

	        Reader reader = Files.newBufferedReader(Paths.get(csvFile));

	        CsvToBean<LeadCallbox> csvToBean = new CsvToBeanBuilder<LeadCallbox>(reader)
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();

	        List<LeadCallbox> callRecords = csvToBean.parse();
		return callRecords;
	   }
}


