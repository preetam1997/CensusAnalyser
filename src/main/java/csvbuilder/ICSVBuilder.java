package csvbuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import censusanalyser.IndiaCensusCSV;
@SuppressWarnings("rawtypes")
public interface ICSVBuilder<E> {

	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass, char delimiter) throws CSVBuilderException;
	public List<IndiaCensusCSV> getCSVFileList(Reader reader, Class<IndiaCensusCSV> csvClass,char delimiter) throws CSVBuilderException;
}
