package csvbuilder;

import java.io.Reader;
import java.util.Iterator;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCsvBuilder<E> implements ICSVBuilder {

	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass, char delimiter)
			throws CSVBuilderException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.withSeparator(delimiter).build();
			return csvToBean.iterator();
		} catch (IllegalStateException e) {
			throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CSVBuilderException(e.getMessage(),
					CSVBuilderException.ExceptionType.OTHER_RUNTIME_PROBLEM);
		}
	}
}
