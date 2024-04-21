package base;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MediaSourcePriority {
//	public static void main(String[] args) {
//		Map<String, List<String>> phoneNumberToMediaSourcesMap = Map.of(
//				"123", List.of("אורגני", "flow", "רדיו", "pmax", "מספר טלפון", "mankalim"),
//				"456", List.of("אורגני", "google", "pmax", "רדיו", "facebook", "mankalim"),
//				"789", List.of("pmax", "אורגני", "mankalim")
//				);
//
//		Map<String, String> phoneNumberToPriorityMediaSourceMap = phoneNumberToMediaSourcesMap.entrySet().stream()
//				.collect(Collectors.toMap(
//						Map.Entry::getKey,
//						entry -> getPriorityMediaSource(entry.getValue())
//						));
//
//		phoneNumberToPriorityMediaSourceMap.forEach((phone, mediaSource) -> {
//			System.out.println("Phone Number: " + phone + " -> Priority Media Source: " + mediaSource);
//		});
//	}   

	public static MediaSourceAndPlatform getPriorityMediaSource(List<MediaSourceAndPlatform> mediaSourcesAndPlatforms) {
	    // Prioritize "flow"
	    MediaSourceAndPlatform flowSource = mediaSourcesAndPlatforms.stream()
	            .filter(source -> source.getMediaSource().equalsIgnoreCase("flow"))
	            .findFirst().orElse(null);
	    if (flowSource != null) {
	        return flowSource;
	    }

	    // Prioritize "mankalim" over others
	    MediaSourceAndPlatform mankalimSource = mediaSourcesAndPlatforms.stream()
	            .filter(source -> source.getMediaSource().toLowerCase().contains("mankalim"))
	            .findFirst().orElse(null);
	    if (mankalimSource != null) {
	        return mankalimSource;
	    }

	    // New prioritization logic for "facebook" over "google", and within "google", prioritize those with "category"
	    Optional<MediaSourceAndPlatform> facebookSource = mediaSourcesAndPlatforms.stream()
	            .filter(source -> source.getMediaSource().toLowerCase().contains("facebook"))
	            .findFirst();

	    if (facebookSource.isPresent()) {
	        return facebookSource.get();
	    } else {
	        List<MediaSourceAndPlatform> googleSources = mediaSourcesAndPlatforms.stream()
	                .filter(source -> source.getMediaSource().toLowerCase().contains("google"))
	                .collect(Collectors.toList());

	        if (!googleSources.isEmpty()) {
	            // Prioritize google sources with 'category' in them
	            Optional<MediaSourceAndPlatform> googleSourceWithCategory = googleSources.stream()
	                    .filter(source -> source.getMediaSource().toLowerCase().contains("category"))
	                    .findFirst();

	            if (googleSourceWithCategory.isPresent()) {
	                return googleSourceWithCategory.get();
	            } else {
	                return googleSources.get(0); // Return the first google source if none contain 'category'
	            }
	        }
	    }

	    // Check for "pmax"
	    MediaSourceAndPlatform pmaxSource = mediaSourcesAndPlatforms.stream()
	            .filter(source -> source.getMediaSource().equalsIgnoreCase("pmax"))
	            .findFirst().orElse(null);
	    if (pmaxSource != null) {
	        return pmaxSource;
	    }

	    // Check for "רדיו" or "אורגני"
	    MediaSourceAndPlatform radioOrOrganicSource = mediaSourcesAndPlatforms.stream()
	            .filter(source -> source.getMediaSource().equalsIgnoreCase("רדיו") || 
	                              source.getMediaSource().equalsIgnoreCase("אורגני"))
	            .findFirst().orElse(null);
	    if (radioOrOrganicSource != null) {
	        return radioOrOrganicSource;
	    }

	    // If none of the specific conditions are met, return the first media source or null if the list is empty
	    return mediaSourcesAndPlatforms.stream().findFirst().orElse(null);
	}

}
