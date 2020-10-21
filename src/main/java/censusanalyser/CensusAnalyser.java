package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CensusAnalyser {
	public boolean correctFileName(String fileName) {
		String regex = "^([a-zA-Z0-9]+).csv$";
		if (fileName.length() == 0) {
			return false;
		} else {
			return Pattern.matches(regex, fileName);
		}
	}

	public int loadIndiaCensusData(String csvFilePath, char delimiter) throws CensusAnalyserException {
		try {
			Path path = Paths.get(csvFilePath);
			Reader reader = Files.newBufferedReader(path);
			if (!correctFileName(path.getFileName().toString())) {
				throw new CensusAnalyserException("Invalid File Type",
						CensusAnalyserException.ExceptionType.FILE_TYPE_MISMATCH);
			}
			Iterator<IndiaCensusCSV> censusCSVIterator = this.getCSVFileIterator(reader, IndiaCensusCSV.class, ',');
			Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
			int namOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
			return namOfEnteries;
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.OTHER_RUNTIME_PROBLEM);
		}

	}

	public int loadIndiaStateCode(String csvFilePath, char delimiter) throws CensusAnalyserException {
		try {
			Path path = Paths.get(csvFilePath);
			Reader reader = Files.newBufferedReader(path);
			if (!correctFileName(path.getFileName().toString())) {
				throw new CensusAnalyserException("Invalid File Type",
						CensusAnalyserException.ExceptionType.FILE_TYPE_MISMATCH);
			}
			Iterator<IndiaStateCodeCSV> stateCSVIterator = this.getCSVFileIterator(reader, IndiaStateCodeCSV.class,
					',');
			Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
			int namOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
			return namOfEnteries;
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	}

	private <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass, char delimiter)
			throws CensusAnalyserException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.withSeparator(delimiter).build();
			return csvToBean.iterator();
		}

		catch (IllegalStateException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.OTHER_RUNTIME_PROBLEM);
		}
	}
}
