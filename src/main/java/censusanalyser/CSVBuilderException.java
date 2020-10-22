package censusanalyser;


public class CSVBuilderException extends Exception {
	 enum ExceptionType {
	        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,FILE_TYPE_MISMATCH,OTHER_RUNTIME_PROBLEM
	    }

	    ExceptionType type;

	    public CSVBuilderException(String message, ExceptionType type) {
	        super(message);
	        this.type = type;
	    }

	    public CSVBuilderException(String message, ExceptionType type, Throwable cause) {
	        super(message, cause);
	        this.type = type;
	    }
}
