package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvHandler {
    public static <T> List<T> convertCSVToObject(String csvFilePath, String[] headers, Class<T> type) throws IOException {
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(type);
        strategy.setColumnMapping(headers);

        // Try reading with UTF-8
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(csvFilePath)), Charset.forName("UTF-8")))) {
            // Skip BOM if present
            reader.mark(1);
            if (reader.read() != '\uFEFF') {
                reader.reset();
            }

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (MalformedInputException e) {
            System.err.println("MalformedInputException with UTF-8: " + e.getMessage());
            System.err.println("Trying with ISO-8859-1");

            // Try reading with ISO-8859-1 if UTF-8 fails
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(csvFilePath)), Charset.forName("ISO-8859-1")))) {
                CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                        .withMappingStrategy(strategy)
                        .withSkipLines(1)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                return csvToBean.parse();
            } catch (MalformedInputException e2) {
                throw new IOException("The file has an invalid encoding or contains characters that cannot be processed with the specified charsets.", e2);
            }
        }
    }
}
