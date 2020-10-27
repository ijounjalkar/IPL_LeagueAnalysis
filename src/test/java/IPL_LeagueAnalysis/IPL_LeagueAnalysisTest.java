package IPL_LeagueAnalysis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IPL_LeagueAnalysisTest {
	String FileName_MostRuns = "C:\\Users\\ADMIN\\eclipse-workspace\\IPL_LeagueAnalysis\\WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	String FileName_MostWickets = "C:\\Users\\ADMIN\\eclipse-workspace\\IPL_LeagueAnalysis\\WP DP Data_02 IPL2019FactsheetMostWkts.csv";

	@Test
	public void givenCSVFileOfRuns_ReturnsNumOfRecord() {
		IPL_LeagueAnalysis test = new IPL_LeagueAnalysis();
		try {
			assertEquals(101, test.loadMostRunsCSVFile(FileName_MostRuns));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenCSVFileOfWickets_ReturnsNumOfRecord() {
		IPL_LeagueAnalysis test = new IPL_LeagueAnalysis();
		try {
			assertEquals(99, test.loadMostWicketsCSVFile(FileName_MostWickets));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
