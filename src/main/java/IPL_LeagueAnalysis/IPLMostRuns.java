package IPL_LeagueAnalysis;

import com.opencsv.bean.CsvBindByName;

public class IPLMostRuns {
	@CsvBindByName(column = "POS", required = true)
	public int position;

	@CsvBindByName(column = "PLAYER", required = true)
	public String player;

	@CsvBindByName(column = "Mat", required = true)
	public int matches;

	@CsvBindByName(column = "Inns", required = true)
	public int innings;

	@CsvBindByName(column = "NO", required = true)
	public int notOut;

	@CsvBindByName(column = "Runs", required = true)
	public int runs;

	@CsvBindByName(column = "HS", required = true)
	public String highScore;

	@CsvBindByName(column = "Avg", required = true)
	public double average;

	@CsvBindByName(column = "BF", required = true)
	public int bf;

	@CsvBindByName(column = "SR", required = true)
	public double strikeRate;

	@CsvBindByName(column = "100", required = true)
	public int hundreds;

	@CsvBindByName(column = "50", required = true)
	public int fiftys;

	@CsvBindByName(column = "4s", required = true)
	public int fours;

	@CsvBindByName(column = "6s", required = true)
	public int sixes;
	
	@Override
	public String toString() {
		return "IPLRunsCSV{" + "Position='" + position + '\'' + ", Player='" + player + '\'' + ", Matches='" + matches
				+ '\'' + ", Innings='" + innings + '\'' + ", NO='" + notOut + '\'' + ", Runs='" + runs + '\''
				+ ", High Score='" + highScore + '\'' + ", Average='" + average + ", BF='" + bf + '\''
				+ ", Strike Rate='" + strikeRate + '\'' + ", 100='" + hundreds + '\'' + ", 50='" + fiftys + ", 4s='"
				+ fours + '\'' + ", 6s='" + sixes + '}';
	}

}
