package base;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MediaSourcePriority {
	public static void main(String[] args) {
		Map<String, List<String>> phoneNumberToMediaSourcesMap = Map.of(
				"123", List.of("אורגני", "flow", "רדיו", "pmax", "מספר טלפון", "mankalim"),
				"456", List.of("אורגני", "google", "pmax", "רדיו", "facebook", "mankalim"),
				"789", List.of("pmax", "אורגני", "mankalim")
				);

		Map<String, String> phoneNumberToPriorityMediaSourceMap = phoneNumberToMediaSourcesMap.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> getPriorityMediaSource(entry.getValue())
						));

		phoneNumberToPriorityMediaSourceMap.forEach((phone, mediaSource) -> {
			System.out.println("Phone Number: " + phone + " -> Priority Media Source: " + mediaSource);
		});
	}

	public static String getPriorityMediaSource(List<String> mediaSources) {
        if (mediaSources.stream().anyMatch(source -> source.equalsIgnoreCase("flow"))) {
            return "flow";
        }
        
        // Prioritize "mankalim" over others
        String mankalimSource = mediaSources.stream()
                .filter(source -> source.toLowerCase().contains("mankalim"))
                .findFirst().orElse(null);
        if (mankalimSource != null) {
            return mankalimSource;
        }

        // Check for "google" or "facebook" regardless of additional string content
        String googleOrFacebookSource = mediaSources.stream()
                .filter(source -> source.toLowerCase().contains("google") || source.toLowerCase().contains("facebook"))
                .findFirst().orElse(null);
        if (googleOrFacebookSource != null) {
            return googleOrFacebookSource;
        }

        boolean containsPmax = mediaSources.stream().anyMatch(source -> source.equalsIgnoreCase("pmax"));
        if (containsPmax) {
            return "pmax";
        }

        String radioOrOrganic = mediaSources.stream()
                .filter(source -> source.equalsIgnoreCase("רדיו") || source.equalsIgnoreCase("אורגני"))
                .findFirst().orElse(null);
        if (radioOrOrganic != null) {
            return radioOrOrganic;
        }

        // If none of the specific conditions are met, return the first media source or null if the list is empty
        return mediaSources.stream().findFirst().orElse(null);
    }

}
