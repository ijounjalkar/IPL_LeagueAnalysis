package IPL_LeagueAnalysis;

import static org.junit.Assert.assertEquals;


import java.io.IOException;
import CSVReader.CSVBuilderException;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLAnalysisTest {
	IPLAnalyser iPLAnalyser;
	public static final String MOST_RUNS = "C:\\Users\\ADMIN\\eclipse-workspace\\IPL_LeagueAnalysis\\WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	public static final String MOST_WKTS = "C:\\Users\\ADMIN\\eclipse-workspace\\IPL_LeagueAnalysis\\WP DP Data_02 IPL2019FactsheetMostWkts.csv";

	@Before
	public void setUp() {
		iPLAnalyser = new IPLAnalyser();
	}

	/**
	 * TestCase for checking the file is properly loaded or not
	 */
	@Test
	public void givenFileData_IfMatchNumberOfRecords_ShouldReturnTrue() {
		try {
			int count = iPLAnalyser.loadDataOfRuns(MOST_RUNS);
			assertEquals(101, count);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CSVBuilderException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TestCase for checking the file is properly loaded or not
	 */
	@Test
	public void givenWKTSFileData_IfMatchNumberOfRecords_ShouldReturnTrue() {
		try {
			int count = iPLAnalyser.loadDataOfWickets(MOST_WKTS);
			assertEquals(99, count);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CSVBuilderException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sorting the data based on best batting average
	 * 
	 * @throws IOException
	 * @throws CSVBuilderException
	 */
	@Test
	public void givenRunsData_WhenSortedOnAverage_ShouldReturnTrue() throws IOException, CSVBuilderException {
		iPLAnalyser.loadDataOfRuns(MOST_RUNS);
		String sortedCSVData = iPLAnalyser.getAverageWiseSortedData();
		MostRunsCSV[] iplCSV = new Gson().fromJson(sortedCSVData, MostRunsCSV[].class);
		assertEquals(83.2, iplCSV[0].avg, 0.0);
	}

	/**
	 * Sorting the data based on top Striking rate
	 * 
	 * @throws IOException
	 * @throws CSVBuilderException
	 */
	@Test
	public void givenRunsData_WhenSortedOnSR_ShouldReturnTrue() throws IOException, CSVBuilderException {
		iPLAnalyser.loadDataOfRuns(MOST_RUNS);
		String sortedCSVData = iPLAnalyser.getSRWiseSortedData();
		MostRunsCSV[] iplCSV = new Gson().fromJson(sortedCSVData, MostRunsCSV[].class);
		assertEquals(333.33f, iplCSV[0].strikeRate, 0.0);
	}

	/**
	 * TestCase for player with maximum no of fours and sixes
	 * 
	 * @throws IOException
	 * @throws CSVBuilderException
	 */
	@Test
	public void givenRunsData_WhenSortedOn4sand6s_ShouldReturnTrue() throws IOException, CSVBuilderException {
		iPLAnalyser.loadDataOfRuns(MOST_RUNS);
		String sortedCSVData = iPLAnalyser.getSortedDataOnNoOfFoursAndSixes();
		MostRunsCSV[] iplCSV = new Gson().fromJson(sortedCSVData, MostRunsCSV[].class);
		assertEquals("Andre Russell", iplCSV[0].playerName);
	}
}
