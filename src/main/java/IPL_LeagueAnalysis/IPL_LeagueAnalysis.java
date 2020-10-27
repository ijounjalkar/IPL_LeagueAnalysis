package IPL_LeagueAnalysis;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import CSVReader.CSVBuilderFactory;
import CSVReader.ICSVBuilder;

public class IPL_LeagueAnalysis {
	
	List<IPLMostRuns> csvRuns = null;
	List<IPLMostWickets> csvWickets = null;
	
	public static void main() {
		System.out.println("Welcome to IPL League Analyser");

	}
	/**
	 * Load CSVFiles
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws CSVBuilderExecption
	 */
	public int loadMostRunsCSVFile(String fileName) throws IOException, CSVBuilderExecption{
		Reader read = Files.newBufferedReader(Paths.get(fileName));
		ICSVBuilder csv = CSVBuilderFactory.createCSVBuilder();
		Iterator<IPLMostRuns> mostRunsIterator = csv.getCSVFileIterator(read, IPLMostRuns.class);
		int numOfRecord = this.getNumOfPlayers(mostRunsIterator);
		return numOfRecord;
	}
	public int loadMostWicketsCSVFile(String fileName) throws IOException, CSVBuilderExecption{
		Reader read = Files.newBufferedReader(Paths.get(fileName));
		ICSVBuilder csv = CSVBuilderFactory.createCSVBuilder();
		Iterator<IPLMostWickets> mostRunsIterator = csv.getCSVFileIterator(read, IPLMostWickets.class);
		int numOfRecord = this.getNumOfPlayers(mostRunsIterator);
		return numOfRecord;
	}
	private<E> int getNumOfPlayers(Iterator<E> iterator) {
		int numOfRecord = 0;
		while (iterator.hasNext()) {
			numOfRecord++;
			E censusData = iterator.next();
		}
		return numOfRecord;
	}
	/**
	 * UC1_Return player with highest Batting avg.
	 * @return
	 */
	public double getTopBattingAvg() {
		double max = 0;
		int count = 0;
		for(int i =0; i < csvRuns.size(); i++) {
			if (csvRuns.get(i).average > max) {
				max = csvRuns.get(i).average;
				count = i;
			}
		}
		System.out.println("Player with highest Avg is: "+csvRuns.get(count).player);
		return max;
	}

}
