package csvHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import base.MediaSourceStats;

public class WriteMediaSourceAndPlatforms {
	  
    public static void writeCSV_MediaSourceAndPlatformStats(Map<String, Map<String, Long>> mediaSourcePlatformCounts, String filePath) {
        // CSV header
        String header = "media source,bmby,callbox,total";

        try (	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            // Write the Byte Order Mark (BOM) for UTF-8 at the beginning of the file
            writer.write('\ufeff');

            // Write the header
            writer.append(header).append("\n");

            // Iterate through the mediaSourcePlatformCounts map to write each row
            for (Map.Entry<String, Map<String, Long>> entry : mediaSourcePlatformCounts.entrySet()) {
                String mediaSource = entry.getKey();
                long bmbyCount = entry.getValue().getOrDefault("BMBY", 0L);
                long callboxCount = entry.getValue().getOrDefault("CALLBOX", 0L);
                long total = bmbyCount + callboxCount;

                // Construct the CSV row
                String row = mediaSource + "," + bmbyCount + "," + callboxCount + "," + total + "\n";

                // Write the row to the CSV
                writer.append(row);
            }

            System.out.println("CSV file was successfully written to: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing the CSV file: " + e.getMessage());
        }
    }
    public static void writeCSV_SummaryLeadStats(Map<String, MediaSourceStats> mediaSourceCounts, String filePath) {
        // CSV header
        String header = "Media Source,Is Relevant,Is Schedule,Is Done,Total";

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            // Write the Byte Order Mark (BOM) for UTF-8 at the beginning of the file
            writer.write('\ufeff');

            // Write the header
            writer.append(header).append("\n");

            // Iterate through mediaSourceCounts to write each row
            for (Map.Entry<String, MediaSourceStats> entry : mediaSourceCounts.entrySet()) {
                String mediaSource = entry.getKey();
                MediaSourceStats stats = entry.getValue();
                long total = stats.totalCounts;

                // Construct the CSV row
                String row = String.join(",",
                        mediaSource,
                        String.valueOf(stats.isRelevant),
                        String.valueOf(stats.isSchedule),
                        String.valueOf(stats.isDone),
                        String.valueOf(total)
                ) + "\n";

                // Write the row to the CSV
                writer.append(row);
            }

            System.out.println("CSV file was successfully written to: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing the CSV file: " + e.getMessage());
        }
    }

}
