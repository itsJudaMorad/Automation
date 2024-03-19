package bmby;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvReaderBmby {
	public  List<LeadBmby> convertToObj() {

	   String fileName = System.getProperty("user.dir")+"/ad120Files/bmby.csv";
	   List<LeadBmby> leads = new ArrayList<>();

       try (Reader reader = new FileReader(fileName);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

           for (CSVRecord csvRecord : csvParser) {
               LeadBmby lead = new LeadBmby();
               lead.setName(csvRecord.get(0));
               lead.setProject(csvRecord.get(1));
               lead.setStatus(csvRecord.get(2));
               lead.setMedia(csvRecord.get(3));
               lead.setSalesPerson(csvRecord.get(4));
               lead.setLeadDate(String.valueOf(csvRecord.get(5)));
               lead.setTotalLeads(String.valueOf(csvRecord.get(6)));
               lead.setInterestedInProduct(csvRecord.get(7));
               lead.setCustomerNumber(String.valueOf(csvRecord.get(8)));
               lead.setPhoneNumber(csvRecord.get(9));

               leads.add(lead);
           }

           // Print out the leads
           for (LeadBmby lead : leads) {
               System.out.println(lead);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
	return leads;
   }
}
