package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
	@SuppressWarnings("rawtypes")
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass, char delimiter) throws CensusAnalyserException;
}
