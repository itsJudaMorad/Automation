package runner;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import base.ChampionLead;


	public class LeadFilter {
		
		
		 public static List<ChampionLead> filterChampionLeads(Map<String, List<ChampionLead>> allLeads) {
	        List<ChampionLead> filteredLeads = new ArrayList<>();
	        Map<Integer, Integer> mediaSourcePriority = Map.of(12, 1, 60, 2, 54, 3);

	        
	        for (Map.Entry<String, List<ChampionLead>> entry : allLeads.entrySet()) {
	            List<ChampionLead> leads = entry.getValue();
	            List<ChampionLead> halfFilteredLeads = new ArrayList<ChampionLead>();
	            ChampionLead selectedLead = null;
	            for (ChampionLead lead : leads) {
	            	if(lead.isLeadOrdered == 1 || lead.isLeadSetMeeting == 1 || lead.isLeadOpportunity == 1) {
	            		halfFilteredLeads.add(lead);
	            	}else {
	            		halfFilteredLeads.add(lead);
	            	}
	            	for (ChampionLead l : halfFilteredLeads) {
	            		if(l.mediaSource.equals("60")) {
	            			selectedLead = l;
	            			break;
	            		} else if(l.mediaSource.equals("12")) {
	            			selectedLead = l;
	            			break;
	            		}else if (l.mediaSource.equals("54")) {
	            			selectedLead = l;
	            			break;
	            		} else {
	            			selectedLead = l;
	            		}
	            	}
	            }
	           filteredLeads.add(selectedLead); 
	        }
	        return filteredLeads;
	    }
		 
		 
		 public static Map<String, List<ChampionLead>> groupLeadsByPhoneNumber(List<ChampionLead> leads) {
		        Map<String, List<ChampionLead>> leadsByPhoneNumber = new HashMap<>();

		        for (ChampionLead lead : leads) {
		                leadsByPhoneNumber.computeIfAbsent(lead.phoneNumber, k -> new ArrayList<>()).add(lead);
		        }
		        return leadsByPhoneNumber;
		    }
	    public static List<ChampionLead> filterLeadsByPreferencess(List<ChampionLead> leads, Map<String, Integer> preferences) {
	        // Define platforms with a default priority of 4
	        
	    	  // Filter based on preferences and default priorities
	        List<ChampionLead> filteredLeads = new ArrayList<>();
	    	Set<String> defaultPriorityPlatforms = new HashSet<>(Arrays.asList(
	            "5", "62", "119", "81", "118", "61", "128", "129", "220", "85", "84"
	        ));

	        // Group leads by phoneNumber
	        Map<String, List<ChampionLead>> groupedLeads = new HashMap<>();
	        for (ChampionLead lead : leads) {
	        	
	        	groupedLeads.computeIfAbsent(lead.phoneNumber, k -> new ArrayList<>()).add(lead);
	        }

	      
	        for (List<ChampionLead> groupedLead : groupedLeads.values()) {
	        	 List<ChampionLead> leadsWithMultiOrders = groupedLead.stream().filter(lead -> lead.isLeadOrdered == 1 ).toList();
	        	if(leadsWithMultiOrders.size() > 1) {
	        		filteredLeads.addAll(leadsWithMultiOrders);
	        	}else {
	        		ChampionLead preferredLead = getPreferredLead(groupedLead, preferences, defaultPriorityPlatforms);
	        		if (preferredLead != null) {
	        			filteredLeads.add(preferredLead);
	        		}
	        	}
	        }
	        return filteredLeads;
	    }
	    public static ChampionLead getPreferredLead(List<ChampionLead> leads, Map<String, Integer> preferences, Set<String> defaultPriorityPlatforms) {
	        // Sort leads within the same group based on user and default priorities
	        leads.sort((lead1, lead2) -> {
	            Integer priority1 = preferences.getOrDefault(lead1.mediaSource, defaultPriorityPlatforms.contains(lead1.mediaSource) ? 4 : Integer.MAX_VALUE);
	            Integer priority2 = preferences.getOrDefault(lead2.mediaSource, defaultPriorityPlatforms.contains(lead2.mediaSource) ? 4 : Integer.MAX_VALUE);
	            return Integer.compare(priority1, priority2);
	        });

	        // Assuming the first lead in the sorted list has the highest priority
	        return leads.isEmpty() ? null : leads.get(0);
	    }

	    public static Map<String, Integer> getUserPreferencesWithPanel() {
	        // Initialize JComboBoxes with priorities
	        JComboBox<String> facebookPriority = new JComboBox<>(new String[]{"", "1", "2", "3"});
	        JComboBox<String> googlePriority = new JComboBox<>(new String[]{"", "1", "2", "3"});
	        JComboBox<String> tabulaPriority = new JComboBox<>(new String[]{"", "1", "2", "3"});

	        // Reset button
	        JButton resetButton = new JButton("Reset");

	        // Panel setup
	        JPanel myPanel = new JPanel();
	        myPanel.add(new JLabel("Facebook (54) Priority:"));
	        myPanel.add(facebookPriority);
	        myPanel.add(new JLabel("Google (12) Priority:"));
	        myPanel.add(googlePriority);
	        myPanel.add(new JLabel("Tabula (60) Priority:"));
	        myPanel.add(tabulaPriority);
	        myPanel.add(resetButton);

	        // Action listeners for JComboBoxes
	        ActionListener priorityActionListener = e -> {
	            JComboBox source = (JComboBox) e.getSource();
	            String selected = (String) source.getSelectedItem();
	            // Disable selected item in other JComboBoxes
	            if (!selected.isEmpty()) {
	                for (JComboBox box : new JComboBox[]{facebookPriority, googlePriority, tabulaPriority}) {
	                    if (box != source) {
	                        box.removeItem(selected);
	                    }
	                }
	            }
	        };

	        facebookPriority.addActionListener(priorityActionListener);
	        googlePriority.addActionListener(priorityActionListener);
	        tabulaPriority.addActionListener(priorityActionListener);

	        // Reset button action listener
	        resetButton.addActionListener(e -> {
	            // Reset JComboBoxes
	            facebookPriority.setModel(new DefaultComboBoxModel<>(new String[]{"", "1", "2", "3"}));
	            googlePriority.setModel(new DefaultComboBoxModel<>(new String[]{"", "1", "2", "3"}));
	            tabulaPriority.setModel(new DefaultComboBoxModel<>(new String[]{"", "1", "2", "3"}));

	            // Reattach action listeners to reset JComboBoxes
	            facebookPriority.addActionListener(priorityActionListener);
	            googlePriority.addActionListener(priorityActionListener);
	            tabulaPriority.addActionListener(priorityActionListener);
	        });

	        int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter Your Preferences. Select the priority for each platform. Each priority can be selected only once.", 
	               JOptionPane.OK_CANCEL_OPTION);

	        Map<String, Integer> preferences = new HashMap<>();
	        
	        if (result == JOptionPane.OK_OPTION) {
	            try {
	                if (facebookPriority.getSelectedItem() != null && googlePriority.getSelectedItem() != null && tabulaPriority.getSelectedItem() != null) {
	                    preferences.put("54", Integer.parseInt((String) facebookPriority.getSelectedItem()));
	                    preferences.put("12", Integer.parseInt((String) googlePriority.getSelectedItem()));
	                    preferences.put("60", Integer.parseInt((String) tabulaPriority.getSelectedItem()));
	                }
	                else {
	                    JOptionPane.showMessageDialog(null, "Please select a priority for each platform.", "Input Error", JOptionPane.ERROR_MESSAGE);
	                    System.exit(0);
	                    return new HashMap<>(); // Handle incomplete input
	                } 
	                	
	            } catch (NumberFormatException e) {
	                JOptionPane.showMessageDialog(null, "An error occurred. Please try again.", "Input Error", JOptionPane.ERROR_MESSAGE);
	                System.err.println("Input Error Program Stop Running  >>  You need to choose the your prefernce by the rules");
	                System.exit(0);
	                
	                return new HashMap<>(); // Handle error
	            }
	          
	        }else if (result == JOptionPane.CANCEL_OPTION) {
	        	System.out.println("Job Cancelled >> program Stop runnig");
                System.exit(0);
            }
	        return preferences; // Return the collected preferences
	    }

}
