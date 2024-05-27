package runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.javafaker.Faker;

import base.ChampionLead;
import base.JsonUtil;
import base.PhoneNumberHandler;
import callbox.FilterCallboxLeads;
import callbox.LeadCallbox;
import callbox.SourceRulesCallbox;
import championCrm.CarBrand;
import championCrm.CarBrand.Brand;
import championCrm.Leads;
import championCrm.Orders;
import championCrm.Process;
import championCrm.VwcvOrders;
import championCrm.ZapierLeads;

public class Run2 {

	public static ExcelToCSVConverter csvConverter = new ExcelToCSVConverter();
	public static final String championFiles = System.getProperty("user.dir")+"/ChampionFiles";
	public static final String championCSV = System.getProperty("user.dir")+"/ChampionCsvFiles";
	public static Brand carBrand = null;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<ChampionLead> championLeads = new ArrayList<ChampionLead>();
		List<ChampionLead> filteredChampionLeads = new ArrayList<ChampionLead>();

		csvConverter.convertExcelToCSV(championFiles+"/callbox.xls", championCSV+"/callbox.csv");
		List<LeadCallbox> callboxLeads = CsvHandler.convertCSVToObject(championCSV+"/callbox.csv", LeadCallbox.headers , LeadCallbox.class);
		carBrand = CarBrand.findCarBrand(callboxLeads);

//		csvConverter.convertExcelToCSV(championFiles+"/orders.xlsx", championCSV+"/orders.csv");
//		List<VwcvOrders> orders = CsvHandler.convertCSVToObject(championCSV+"/orders.csv", VwcvOrders.headers , VwcvOrders.class);
//		System.out.println(orders.get(0).getName());

//		List<VwcvOrders> orders2 = CsvHandler.convertCSVToObject(championCSV+"/orders.csv", VwcvOrders.headers , VwcvOrders.class);
//		List<championCrm.Process> process = CsvHandler.convertCSVToObject(championCSV+"/process.csv", championCrm.Process.headers, championCrm.Process.class);
//		System.out.println(process.size());
//		List<Leads> leads = CsvHandler.convertCSVToObject(championCSV+"/leads.csv", Leads.headers, Leads.class);
//		System.out.println(leads.size());
//		csvConverter.convertExcelToCSV(championFiles+"/zapierLeads.xlsx", championCSV+"/zapierLeads.csv");
//		List<ZapierLeads> zapierLeads = CsvHandler.convertCSVToObject(championCSV+"/zapierLeads.csv", ZapierLeads.headers, ZapierLeads.class);
//
//		for (LeadCallbox lead: callboxLeads) {
//			// filter all test leads
//			if(FilterCallboxLeads.validateLeadIsNotTest(lead)) { // && FilterCallboxLeads.validateLeadDurationIsAbove30Sec(lead)) {
//				lead.setCustomerNumber(PhoneNumberHandler.extractDigits(lead.getCustomerNumber()));
//				lead.setMediaSource(SourceRulesCallbox.setMediaSourceByRules(lead));
//				ChampionLead championLead =new ChampionLead(lead.getCustomerNumber(), lead.getMediaSource());
//				championLeads.add(championLead);
//			}
//		}
//		
//		// get lead that not exist on callbox
//		List<ChampionLead> notExistOnCallbox = new ArrayList<ChampionLead>();
//		for(Leads lead : leads) {
//			String source = Leads.setMediaSourceByRules(lead);
//			List<String> leadPhonenNumbers = PhoneNumberHandler.getMultiplePhoneNumberAsArray(lead.phone);
//			boolean leadExist = false;
//			for(ChampionLead championLead : championLeads) {
//				if(leadPhonenNumbers.contains(championLead.phoneNumber)) {
//					leadExist = true;
//					break;
//				}
//			}
//			if(!leadExist && CarBrand.containsAnySubBrand(lead.modelGroup, carBrand) && lead.processNumber.length() > 6) {
//				ChampionLead newChampionLead = new ChampionLead(leadPhonenNumbers.get(0), source);
//				newChampionLead.CarModel = lead.modelGroup;
//				notExistOnCallbox.add(newChampionLead);
//				
//			}
//		}
//		List<ChampionLead> combinedChampion = new ArrayList<ChampionLead>(championLeads);
//		combinedChampion.addAll(notExistOnCallbox);
//		combinedChampion = Orders.getLeadOrder(orders, combinedChampion);
//		
//		
//		// Set Lead Process
//		Map<String, List<String>> as =Process.getTheDuplicatesLeadInProcessList(process);
//		combinedChampion = Process.getLeadProcess(process,combinedChampion);
//		
//		// get the campaign details
//		for(ChampionLead championLead : combinedChampion) {
//			Process.countTheNumberOfProcess(championLead);
//			for (ZapierLeads zap : zapierLeads) {
//				if (championLead.phoneNumber.contains(zap.phone)) {
//					championLead.campaignName = zap.campaignName;
//					championLead.adSetName 	   = zap.adSetName;
//					championLead.adName 	   = zap.adName;
//					break;
//				}
//			}
//		}
//				
//		// update Media campaign names
//		for(ChampionLead championLead : combinedChampion) {
//			for(LeadCallbox callboxLead : callboxLeads) {
//				if(callboxLead.getCustomerNumber().contains(championLead.phoneNumber)) {
//					championLead.callboxFormName= callboxLead.getFormName();
//					if (callboxLead.getCampaign() != null) {
//						championLead.callboxCampaign = callboxLead.getCampaign();
//						break;
//					}
//				}
//			}
//		}
////		String jsonOutput= JsonUtil.toJson(combinedChampion);
////		Map<String, List<ChampionLead>> asss = LeadFilter.groupLeadsByPhoneNumber(combinedChampion);
////		 jsonOutput = JsonUtil.toJson(asss);
////        filteredChampionLeads = LeadFilter.filterChampionLeads(asss);
//		
//		Map<String, Integer> preferences = LeadFilter.getUserPreferencesWithPanel();
//		filteredChampionLeads = LeadFilter.filterLeadsByPreferencess(combinedChampion, preferences);
//		Faker faker= new Faker();
//		ChampionLead.writeToCSV(filteredChampionLeads, "results/allLeadData_"+carBrand.name()+"_"+faker.harryPotter().character() +".csv");
	}	
	
}