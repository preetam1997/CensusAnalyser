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
			this.FileMismatch(csvFilePath);
			Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
			Iterator<IndiaStateCodeCSV> censusCSVIterator = new OpenCsvBuilder().getCSVFileIterator(reader, IndiaStateCodeCSV.class,
					',');

			int namOfEnteries = this.getCount(censusCSVIterator);
			return namOfEnteries;
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	}

	public int loadIndiaStateCode(String csvFilePath, char delimiter) throws CensusAnalyserException {
		try {
			this.FileMismatch(csvFilePath);
			Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
			
			Iterator<IndiaStateCodeCSV> stateCSVIterator = new OpenCsvBuilder().getCSVFileIterator(reader, IndiaStateCodeCSV.class,
					',');

			int namOfEnteries = this.getCount(stateCSVIterator);
			return namOfEnteries;
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	}

	private void FileMismatch(String csvFilePath) throws CensusAnalyserException {
		Path path = Paths.get(csvFilePath);
		if (!correctFileName(path.getFileName().toString())) {
			throw new CensusAnalyserException("Invalid File Type",
					CensusAnalyserException.ExceptionType.FILE_TYPE_MISMATCH);
		}
	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEnteries;
	}

}
