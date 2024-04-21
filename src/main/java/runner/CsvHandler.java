package runner;

import java.io.IOException;
import java.io.Reader;
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

	        Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));

	        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
	                .withMappingStrategy(strategy)
	                .withSkipLines(1)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();

	        return csvToBean.parse();
	    }
}
