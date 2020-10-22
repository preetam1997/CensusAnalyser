package censusanalyser;

import csvbuilder.CSVBuilderException;

@SuppressWarnings("serial")
public class CensusAnalyserException extends CSVBuilderException {

	ExceptionType type;


    public CensusAnalyserException(String message, ExceptionType type) {
		super(message, type);
		this.type = type;
	}
    
    public CensusAnalyserException(String message, ExceptionType type, Throwable throwable) {
		super(message, type);
		this.type = type;
	}
	
    
}
