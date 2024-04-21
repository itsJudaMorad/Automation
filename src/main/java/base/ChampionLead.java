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
        // Use try-with-resources to automatically handle resource management
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName));
           
        		CSVWriter csvWriter = new CSVWriter(writer, 
                                                CSVWriter.DEFAULT_SEPARATOR, 
                                                CSVWriter.NO_QUOTE_CHARACTER, 
                                                CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                                                CSVWriter.DEFAULT_LINE_END)) {
        	 writer.write('\ufeff');
            // Writing header record
            String[] headerRecord = {"Phone Number", "Media Source", "Car Model", "Is Lead Placed Order" ,"Is Lead Set Meeting",
            		"Is Lead Opportuinty", "Process", "Campaign Name", "Ad Set Name", "Ad Name", "callboxCampaign","callboxFormName"};
            csvWriter.writeNext(headerRecord);
           

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
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }
}