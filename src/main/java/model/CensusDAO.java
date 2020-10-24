package model;

import censusanalyser.IndiaCensusCSV;
import censusanalyser.IndiaStateCodeCSV;

public class CensusDAO {

	public String state;
	public int areaInSqKm;
	public int densityPerSqKm;
	public int population;
	public String srNo;
	public String stateName;
	public String tin;
	public String StateCode;

	public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
		state = indiaCensusCSV.state;
		areaInSqKm = indiaCensusCSV.areaInSqKm;
		densityPerSqKm = indiaCensusCSV.densityPerSqKm;
		population = indiaCensusCSV.population;
	}

	public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
		srNo = indiaStateCodeCSV.srNo;
		stateName = indiaStateCodeCSV.stateName;
		tin = indiaStateCodeCSV.tin;
		StateCode = indiaStateCodeCSV.StateCode;
	}
}
