package csvbuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import censusanalyser.CensusAnalyserException;
import censusanalyser.IndiaCensusCSV;
import censusanalyser.IndiaStateCodeCSV;
import model.CensusDAO;

public class OpenCsvBuilder<E> implements ICSVBuilder {
	
	 public static List censusList = new ArrayList<>();

	@Override
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass, char delimiter)
			throws CSVBuilderException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.withSeparator(delimiter).build();
			return csvToBean.iterator();
		} catch (IllegalStateException e) {
			throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CSVBuilderException(e.getMessage(),
					CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM);
		}
	}

	@Override
	public List<E> getCSVFileList(Reader reader, Class csvClass,char delimiter) throws CSVBuilderException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.withSeparator(',').build();
			Iterator<E> csvFileIterator = csvToBean.iterator();
			censusList.clear();
            while (csvFileIterator.hasNext())
                censusList.add(csvFileIterator.next());

			return censusList;
		} catch (IllegalStateException e) {
			throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CSVBuilderException(e.getMessage(),
					CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM);
		}
	}
	
	public static List<CensusDAO> censusDaoList = new ArrayList<>();
   

}
