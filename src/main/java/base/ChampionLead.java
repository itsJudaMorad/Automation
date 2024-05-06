package base;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVWriter;

import championCrm.Orders;

public class ChampionLead {

	public ChampionLead(String phoneNumber, String mediaSource) {
		this.phoneNumber = phoneNumber;
		this.mediaSource = mediaSource;
	}
	public enum LeadProcess{
		OPPORTUNITY, LEAD, ORDER, FIRST_MEETING, RELEASE, DELIVERY 
	}
	public String phoneNumber;
	public String mediaSource;
	public String CarModel;
	public String leadStatus;
	public String process;
	public boolean isLeadPlacedOrder;
	public String campaignName; // campaign name
	public String adSetName; // ad set name
	public String adName; // ad name
	public String callboxCampaign;
	public String callboxFormName;
	public int isLeadSetMeeting;
	public int isLeadOpportunity;
	public int isLeadOrdered;
	
	  public static void writeToCSV(List<ChampionLead> leads, String fileName) {
	        String[] headerRecord = {"Phone Number", "Media Source", "Car Model", "Is Lead Placed Order" ,"Is Lead Set Meeting",
	            "Is Lead Opportuinty", "Process", "Campaign Name", "Ad Set Name", "Ad Name", "callboxCampaign","callboxFormName"};

	        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName));
	             CSVWriter csvWriter = new CSVWriter(writer, 
	                                                CSVWriter.DEFAULT_SEPARATOR, 
	                                                CSVWriter.NO_QUOTE_CHARACTER, 
	                                                CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
	                                                CSVWriter.DEFAULT_LINE_END)) {
	            
	            // Write UTF-8 BOM for Excel compatibility
	            writer.write('\ufeff');

	            // Writing header record
	            csvWriter.writeNext(headerRecord);

	            int rowCount = 0;
	            // Writing data records
	            for (ChampionLead lead : leads) {
	                csvWriter.writeNext(new String[] {
	                    lead.phoneNumber,
	                    lead.mediaSource,
	                    lead.CarModel,
	                    String.valueOf(lead.isLeadOrdered),
	                    String.valueOf(lead.isLeadSetMeeting),
	                    String.valueOf(lead.isLeadOpportunity),
	                    lead.process,
	                    lead.campaignName,
	                    lead.adSetName,
	                    lead.adName,
	                    lead.callboxCampaign,
	                    lead.callboxFormName
	                });
	                rowCount++;
	            }

	            // Explicitly flushing and closing CSVWriter
	            csvWriter.flush();
	            System.out.println("Number of rows written: " + rowCount);
	            System.out.println("The CSV was written successfuly! \n +"
	            		+ "File path: " + System.getProperty("user.dir") + "/results/"+fileName);

	        } catch (IOException e) {
	            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
	        }
    }
}