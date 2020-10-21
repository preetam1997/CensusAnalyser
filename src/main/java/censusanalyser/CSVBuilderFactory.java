package censusanalyser;

public class CSVBuilderFactory {

	public static ICSVBuilder createCSVBuilder() {
		// TODO Auto-generated method stub
		return new OpenCsvBuilder();
	}

}
