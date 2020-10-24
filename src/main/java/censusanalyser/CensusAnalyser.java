package censusanalyser;

import java.io.IOException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;
import model.CensusDAO;

public class CensusAnalyser {
	public List<IndiaCensusCSV> censusCsvList = null;

	public CensusAnalyser() {
		this.censusCsvList = new ArrayList<>();
	}

	public boolean correctFileName(String fileName) {
		String regex = "^([a-zA-Z0-9]+).csv$";

		if (fileName != null && fileName.length() == 0) {
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
			censusCsvList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class, ',');
			return censusCsvList.size();
		} catch (IOException e) {
			throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
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
			throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	}

	private void FileMismatch(String csvFilePath) throws CSVBuilderException {
		Path path = Paths.get(csvFilePath);
		if (!correctFileName(path.getFileName().toString())) {
			throw new CSVBuilderException("Invalid File Type", CSVBuilderException.ExceptionType.FILE_TYPE_MISMATCH);
		}
	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEnteries;
	}

	public String getStateWiseAortedCensusData() throws CSVBuilderException {
		if (censusCsvList == null || censusCsvList.size() == 0) {
			throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(censusComparator);
		String sortedStateCensus = new Gson().toJson(censusCsvList);
		return sortedStateCensus;

	}

	private void sort(Comparator<IndiaCensusCSV> censusComparator) {
		for (int i = 0; i < censusCsvList.size() - 1; i++) {
			for (int j = 0; j < censusCsvList.size() - i - 1; j++) {
				IndiaCensusCSV census1 = censusCsvList.get(j);
				IndiaCensusCSV census2 = censusCsvList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					censusCsvList.set(j, census2);
					censusCsvList.set(j + 1, census1);
				}
			}
		}
	}
}
