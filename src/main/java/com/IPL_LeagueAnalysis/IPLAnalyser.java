package com.IPL_LeagueAnalysis;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.CSVReader.CSVBuilderException;
import com.CSVReader.CSVBuilderFactory;
import com.CSVReader.ICSVBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IPLAnalyser {
	List<MostRunsCSV> csvRunsList = null;
	List<MostWktsCSV> csvWktsList = null;

	public int loadDataOfRuns(String CSVFile) throws IOException, CSVBuilderException, IPLAnalyserException {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(CSVFile));
			ICSVBuilder<MostRunsCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvRunsList = csvBuilder.getCSVFileList(reader, MostRunsCSV.class);
			return csvRunsList.size();
		} catch (CSVBuilderException exception) {
			throw new IPLAnalyserException(exception.getMessage(), IPLAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (IOException exception) {
			throw new IPLAnalyserException(exception.getMessage(), IPLAnalyserException.ExceptionType.INCORRECT_FILE);
		}

	}

	public int loadDataOfWickets(String CSVFile) throws IOException, CSVBuilderException, IPLAnalyserException {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(CSVFile));
			ICSVBuilder<MostWktsCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvWktsList = csvBuilder.getCSVFileList(reader, MostWktsCSV.class);
			return csvWktsList.size();
		} catch (CSVBuilderException exception) {
			throw new IPLAnalyserException(exception.getMessage(), IPLAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (IOException exception) {
			throw new IPLAnalyserException(exception.getMessage(), IPLAnalyserException.ExceptionType.INCORRECT_FILE);
		}
	}

	/**
	 * Usecase1 : sorting data based on batting average
	 * 
	 * @return
	 */
	public String getAverageWiseSortedData() {
		Comparator<MostRunsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.avg);
		return new Gson().toJson(this.sort(csvRunsList, iplCSVComparator));
	}

	private <E> List<E> sort(List<E> csvList, Comparator<E> iplCSVComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				E player1 = csvList.get(j);
				E player2 = csvList.get(j + 1);
				if (iplCSVComparator.compare(player1, player2) < 0) {
					csvList.set(j, player2);
					csvList.set(j + 1, player1);
				}
			}
		}
		return csvList;
	}

	/**
	 * Usecase2 : finding top striking rate in the IPL 2019
	 * 
	 * @return
	 */
	public String getSRWiseSortedData() {
		Comparator<MostRunsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.strikeRate);
		this.sort(csvRunsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvRunsList);
		return sorted;
	}

	/**
	 * Usecase3 : Player with maximum fours and sixes
	 * 
	 * @return
	 */
	public String getSortedDataOnNoOfFoursAndSixes() {
		Comparator<MostRunsCSV> iplCSVComparator = Comparator
				.comparing(entry -> (entry.noOfFours * 4) + (entry.noOfSixes * 6));
		this.sort(csvRunsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvRunsList);
		return sorted;
	}

	/**
	 * Usecase4 : Finding strike rate based on Sixes and fours
	 * 
	 * @return
	 */
	public String getSortedDataOnStrikeRateOnSixAndFour() {
		double max = 0;
		double temp = 0;
		String name = "";
		double maxSR = 0;
		double tempSR = 0;
		for (int i = 0; i < csvRunsList.size(); i++) {
			temp = (csvRunsList.get(i).noOfFours*4 + csvRunsList.get(i).noOfSixes*6);
			tempSR = temp / csvRunsList.get(i).bF;
			if (temp > max && tempSR > maxSR) {
				max = temp;
				maxSR = tempSR;
				name = csvRunsList.get(i).playerName;

			}
		}
		return name;
	}

	/**
	 * Usecase5 : Finding Batsman with best batting Average and strike rate
	 * 
	 * @return
	 */
	public String getSortedOnAverageAndStrikeRate() {
		this.sort(csvRunsList, Comparator.comparing(entry -> entry.avg));
		List<MostRunsCSV> tempList = this.sort(csvRunsList.stream().limit(10).collect(Collectors.toList()),
				Comparator.comparing(entry -> entry.strikeRate));
		String sorted = new Gson().toJson(tempList);
		return sorted;
	}

	/**
	 * Usecase6 : Finding BatsMan with max runs and best average in IPL2019
	 * 
	 * @return
	 */
	public String getSortedOnMaxRunsAndStrikeRate() {
		this.sort(csvRunsList, Comparator.comparing(entry -> entry.runs));
		List<MostRunsCSV> tempList = this.sort(csvRunsList.stream().limit(10).collect(Collectors.toList()),
				Comparator.comparing(entry -> entry.strikeRate));
		String sorted = new Gson().toJson(tempList);
		return sorted;
	}

	/**
	 * Usecase7 : Finding Bowler with best bowling average in IPL2019
	 * 
	 * @return
	 */
	public String getSortedOnBowlingAvg() {
		csvWktsList.removeIf(entry -> entry.avg == 0);
		Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.avg, Comparator.reverseOrder());
		this.sort(csvWktsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvWktsList);
		return sorted;
	}

	/**
	 * Usecase8 : Finding Bowler with best striking rate in IPL2019
	 * 
	 * @return
	 */
	public String getSortedOnBowlingStrikeRate() {
		csvWktsList.removeIf(entry -> entry.avg == 0);
		Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.strikeRate,
				Comparator.reverseOrder());
		this.sort(csvWktsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvWktsList);
		return sorted;
	}

	/**
	 * Usecase9 : Finding Bowler with good economy
	 * 
	 * @return
	 */
	public String getSortedOnBowlingEconomy() {
		Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.economy,
				Comparator.reverseOrder());
		this.sort(csvWktsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvWktsList);
		return sorted;
	}

	/**
	 * Usecase10 : Bowler with best strike rate based on four and five wickets haul
	 * 
	 * @return
	 */
	public String getSortedOnStrikeRateAnd4wOr5w() {
		csvWktsList.removeIf(entry -> entry.strikeRate == 0 || entry.fourWkts == 0 && entry.fiveWkts == 0);
		this.sort(csvWktsList, Comparator.comparing((entry -> entry.strikeRate), Comparator.reverseOrder()));
		String sorted = new Gson().toJson(csvWktsList);
		return sorted;
	}

	/**
	 * Usecase11 : Bowler with great averages and best striking rate
	 * 
	 * @return
	 */
	public String getSortedOnBowlingAvgAndStrikeRate() {
		csvWktsList.removeIf(entry -> entry.avg == 0);
		Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.avg, Comparator.reverseOrder());
		List<MostWktsCSV> tempList = this.sort(csvWktsList, iplCSVComparator).stream().limit(10)
				.collect(Collectors.toList());
		this.sort(tempList, Comparator.comparing(entry -> entry.strikeRate, Comparator.reverseOrder()));
		String sorted = new Gson().toJson(tempList);
		return sorted;
	}

	/**
	 * Usecase12 : Bowler with max wickets and best bowling average
	 * 
	 * @return
	 */
	public String getSortedOnMaxWicketsAndBowlingAvg() {
		Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.wickets);
		List<MostWktsCSV> tempList = this.sort(csvWktsList, iplCSVComparator).stream().limit(20)
				.collect(Collectors.toList());
		this.sort(tempList, Comparator.comparing(entry -> entry.avg, Comparator.reverseOrder()));
		String sorted = new Gson().toJson(tempList);
		return sorted;

	}

	/**
	 * Usecase13 : Players with best bowling and batting average
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSortedOnBestBattingAndBowlingAvg() {
		List<MostRunsCSV> battingList = (ArrayList<MostRunsCSV>) new Gson().fromJson(this.getAverageWiseSortedData(),new TypeToken<ArrayList<MostRunsCSV>>() {}.getType());
		List<MostWktsCSV> bowlingList = (ArrayList<MostWktsCSV>) new Gson().fromJson(this.getSortedOnBowlingAvg(),new TypeToken<ArrayList<MostWktsCSV>>() {}.getType());
		return this.forAllRounder(battingList,bowlingList);	
	}
	
	/**
	 * Usecase14: Finding best all rounder
	 * 
	 * @return
	 */
	public List<String> getSortedOnMaxRunsAndWkts() {
		List<MostRunsCSV> runsList = this.sort(csvRunsList, Comparator.comparing(entry->entry.runs)).stream().limit(50).collect(Collectors.toList());
		List<MostWktsCSV> wicketsList = this.sort(csvWktsList, Comparator.comparing(entry->entry.wickets));
		return this.forAllRounder(runsList,wicketsList);	
	}

	private List<String> forAllRounder(List<MostRunsCSV> runsList, List<MostWktsCSV> wicketsList) {
		List<String> playerList = new ArrayList<>();
		for (MostRunsCSV bat : runsList) {
			for (MostWktsCSV bowl : wicketsList) {
				if (bat.playerName.equals(bowl.playerName)) {
					playerList.add(bat.playerName);
				}
			}
		}
		return playerList;
	}
	/**
	 * Usecase15: Player with max hundreds and best averages
	 * 
	 * @return
	 */
	public List<MostRunsCSV> getSortedOnMaxHundredsAndBattingAverage() {
		csvRunsList.removeIf(entry -> entry.noOfHundreds == 0 );
		Comparator<MostRunsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.noOfHundreds);
		List<MostRunsCSV> tempList = this.sort(csvRunsList, iplCSVComparator);
		this.sort(tempList, Comparator.comparing(entry -> entry.avg));
		return tempList;
	}
}
