package censusanalyser;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;

import csvbuilder.CSVBuilderException;

public class CensusAnalyserTest {

	private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
	private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
	private static final String WRONG_FILE_TYPE_FILE_PATH = "./src/test/resources/IndianStateCensus.txt";
	private static final String INDIA_CENSUS_CSV1_FILE_PATH = "./src/test/resources/IndiaStateCensusData1.csv";

	private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
	private static final String INDIA_STATE_CODE_CSV_WRONG_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
	private static final String INDIA_STATE_CODE_CSV1_FILE_PATH = "./src/test/resources/IndiaStateCode1.csv";

	@Test
	public void givenIndianCensusCSVFileReturnsCorrectRecords() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			List numOfRecords = censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV1_FILE_PATH, ',');
			Assert.assertEquals(29, numOfRecords.size());
		} catch (CSVBuilderException e) {
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV1_FILE_PATH, ',');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV1_FILE_PATH, ',');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV1_FILE_PATH, '|');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndiaCensusData_WithIncorrectHeader_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV1_FILE_PATH, ',');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM, e.type);
		}
	}

	@Test
	public void givenStateCodeCSVFileReturnsCorrectRecords() {
		try {

			CensusAnalyser censusAnalyser = new CensusAnalyser();
			int numOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH, ',');
			Assert.assertEquals(37, numOfRecords);
		} catch (CSVBuilderException e) {
		}
	}

	@Test
	public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_WRONG_FILE_PATH, ',');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndiaStateCode_WithWrongFileType_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadIndiaStateCode(WRONG_FILE_TYPE_FILE_PATH, ',');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.FILE_TYPE_MISMATCH, e.type);
		}
	}

	@Test
	public void givenIndiaStateCode_WithWrongDelimiter_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH, '|');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndiaStateCode_WithIncorrectHeader_ShouldThrowException() {
		try {
			CensusAnalyser censusAnalyser = new CensusAnalyser();
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(CSVBuilderException.class);
			censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV1_FILE_PATH, ',');
		} catch (CSVBuilderException e) {
			Assert.assertEquals(CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() throws CSVBuilderException {
		CensusAnalyser censusAnalyser = new CensusAnalyser();
		censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV_FILE_PATH, ',');
		String sortedCensusData = censusAnalyser.getStateWiseAortedCensusData();
		IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
		Assert.assertEquals("Andhra Pradesh", censusCsv[0].state);
	}

	@Test
	public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CSVBuilderException {
		CensusAnalyser censusAnalyser = new CensusAnalyser();
		censusAnalyser.loadCensusData(IndiaStateCodeCSV.class, INDIA_STATE_CODE_CSV_FILE_PATH, ',');
		String sortedCensusData = censusAnalyser.getStateCodeWiseAortedCensusData();
		IndiaStateCodeCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
		Assert.assertEquals("AD", censusCsv[0].StateCode);
	}
	
	@Test
	public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() throws CSVBuilderException {
		CensusAnalyser censusAnalyser = new CensusAnalyser();
		censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV_FILE_PATH, ',');
		String sortedCensusData = censusAnalyser.getPopulationSortedCensusData();
		IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
		Assert.assertEquals("Uttar Pradesh", censusCsv[28].state);
	}
	
	@Test
	public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() throws CSVBuilderException {
		CensusAnalyser censusAnalyser = new CensusAnalyser();
		censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV_FILE_PATH, ',');
		String sortedCensusData = censusAnalyser.getPopulationDensitySortedCensusData();
		IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
		Assert.assertEquals("Bihar", censusCsv[0].state);
	}
	
	@Test
	public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnSortedResult() throws CSVBuilderException {
		CensusAnalyser censusAnalyser = new CensusAnalyser();
		censusAnalyser.loadCensusData(IndiaCensusCSV.class, INDIA_CENSUS_CSV_FILE_PATH, ',');
		String sortedCensusData = censusAnalyser.getAreaWiseSortedCensusData();
		IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
		Assert.assertEquals("Rajasthan", censusCsv[0].state);
	}
}
