package censusanalyser;

import java.io.IOException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

public class CensusAnalyser {
	public boolean correctFileName(String fileName) {
		String regex = "^([a-zA-Z0-9]+).csv$";
		
		if (fileName!= null &&fileName.length() == 0) {
			return false;
		} else {
			return Pattern.matches(regex, fileName);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int loadIndiaCensusData(String csvFilePath, char delimiter) throws CSVBuilderException {
		try {
			this.FileMismatch(csvFilePath);
			Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class,
					',');

			return this.getCount(censusCSVIterator);
		} catch (IOException e) {
			throw new CSVBuilderException(e.getMessage(),
					CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	} 

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int loadIndiaStateCode(String csvFilePath, char delimiter) throws CSVBuilderException {
		try {
			this.FileMismatch(csvFilePath);
			Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader,
					IndiaStateCodeCSV.class, ',');

			return this.getCount(stateCSVIterator);
		} catch (IOException e) {
			throw new CSVBuilderException(e.getMessage(),
					CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	}

	private void FileMismatch(String csvFilePath) throws CSVBuilderException {
		Path path = Paths.get(csvFilePath);
		if (!correctFileName(path.getFileName().toString())) {
			throw new CSVBuilderException("Invalid File Type",
					CSVBuilderException.ExceptionType.FILE_TYPE_MISMATCH);
		}
	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEnteries;
	}

}
