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
	public List<CensusDAO> censusDaoList = null;

	public CensusAnalyser() {
		this.censusDaoList = new ArrayList<>();
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
	public <E> List loadCensusData(Class<E> classType, String csvFilePath, char delimiter)
			throws CSVBuilderException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, classType, delimiter);
			switch (classType.getSimpleName()) {
			case "IndiaCensusCSV":
				censusDaoList.clear();
				while (csvFileIterator.hasNext())
					this.censusDaoList.add(new CensusDAO((IndiaCensusCSV) csvFileIterator.next()));
				break;

			case "IndiaStateCodeCSV":
				censusDaoList.clear();
				while (csvFileIterator.hasNext())
					this.censusDaoList.add(new CensusDAO((IndiaStateCodeCSV) csvFileIterator.next()));
				break;
			}
			return censusDaoList;
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
		if (censusDaoList == null || censusDaoList.size() == 0) {
			throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(censusComparator);
		String sortedStateCensus = new Gson().toJson(censusDaoList);
		return sortedStateCensus;

	}

	public String getStateCodeWiseAortedCensusData() throws CSVBuilderException {
		if (censusDaoList == null || censusDaoList.size() == 0) {
			throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
		}

		Comparator<CensusDAO> stateCodeComparator = Comparator.comparing(stateCode -> stateCode.StateCode);
		this.sort(stateCodeComparator);
		String sortedStateCensus = new Gson().toJson(censusDaoList);
		return sortedStateCensus;

	}
	
	public String getPopulationSortedCensusData() throws CSVBuilderException {
		if (censusDaoList == null || censusDaoList.size() == 0) {
			throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
		}

		Comparator<CensusDAO> stateCodeComparator = Comparator.comparing(census -> census.population);
		this.sort(stateCodeComparator);
		String sortedStateCensus = new Gson().toJson(censusDaoList);
		return sortedStateCensus;

	}
	
	public String getPopulationDensitySortedCensusData() throws CSVBuilderException {
		if (censusDaoList == null || censusDaoList.size() == 0) {
			throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
		}

		Comparator<CensusDAO> stateCodeComparator = Comparator.comparing(census -> census.densityPerSqKm,Comparator.reverseOrder());
		this.sort(stateCodeComparator);
		String sortedStateCensus = new Gson().toJson(censusDaoList);
		return sortedStateCensus;

	}

	private void sort(Comparator<CensusDAO> censusComparator) {
		for (int i = 0; i < censusDaoList.size() - 1; i++) {
			for (int j = 0; j < censusDaoList.size() - i - 1; j++) {
				CensusDAO census1 = censusDaoList.get(j);
				CensusDAO census2 = censusDaoList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					censusDaoList.set(j, census2);
					censusDaoList.set(j + 1, census1);
				}
			}
		}
	}
}
