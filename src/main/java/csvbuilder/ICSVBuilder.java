package csvbuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
	@SuppressWarnings("rawtypes")
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass, char delimiter) throws CSVBuilderException;
	//public List<E> getCSVFileList(Reader reader, Class csvClass, char delimiter) throws CSVBuilderException;
}
