package csvHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class main {

	 public static void main(String[] args) {
	        // Hypothetical list of entries (could be objects, tuples, etc.), assuming duplicates are possible here
	        List<Entry> entries = Arrays.asList(
	                new Entry("123", "SourceA"),
	                new Entry("123", "SourceB"),
	                new Entry("456", "SourceC")
	        );

	        Map<String, List<String>> phoneNumberToMediaSourcesMap = entries.stream()
	                .collect(Collectors.groupingBy(
	                        Entry::getPhoneNumber, // Assuming Entry class has a getPhoneNumber method
	                        Collectors.mapping(Entry::getMediaSource, Collectors.toList())
	                ));

	        // phoneNumberToMediaSourcesMap now has phone numbers as keys and lists of media sources as values
	        phoneNumberToMediaSourcesMap.forEach((phone, mediaSources) -> {
	            System.out.println("Phone Number: " + phone + " -> Media Sources: " + mediaSources);
	        });
	    }

	    // Placeholder for your actual object class
	    static class Entry {
	        private String phoneNumber;
	        private String mediaSource;

	        public Entry(String phoneNumber, String mediaSource) {
	            this.phoneNumber = phoneNumber;
	            this.mediaSource = mediaSource;
	        }

	        public String getPhoneNumber() {
	            return phoneNumber;
	        }

	        public String getMediaSource() {
	            return mediaSource;
	        }
	    }
}

