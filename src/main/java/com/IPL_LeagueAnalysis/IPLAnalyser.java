package com.IPL_LeagueAnalysis;

import java.io.IOException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;
import CSVReader.CSVBuilderFactory;
import CSVReader.ICSVBuilder;

public class IPLAnalyser {
	List<MostRunsCSV> csvRunsList = null;
	List<MostWktsCSV> csvWktsList = null;

	public int loadDataOfRuns(String CSVFile) throws IOException, CSVBuilderException {
		Reader reader = Files.newBufferedReader(Paths.get(CSVFile));
		ICSVBuilder<MostRunsCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
		csvRunsList = csvBuilder.getCSVFileList(reader, MostRunsCSV.class);
		return csvRunsList.size();
	}

	public int loadDataOfWickets(String CSVFile) throws IOException, CSVBuilderException {
		Reader reader = Files.newBufferedReader(Paths.get(CSVFile));
		ICSVBuilder<MostWktsCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
		csvWktsList = csvBuilder.getCSVFileList(reader, MostWktsCSV.class);
		return csvWktsList.size();
	}
	
	/**
	 * Usecase1 : sorting data based on batting average
	 * 
	 * @return
	 */
	public String getAverageWiseSortedData() {
		Comparator<MostRunsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.avg);
		this.sort(csvRunsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvRunsList);
		return sorted;
	}

	private <E> void sort(List<E> csvList, Comparator<E> iplCSVComparator) {
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
			temp = (csvRunsList.get(i).noOfFours + csvRunsList.get(i).noOfSixes);
			tempSR = temp / csvRunsList.get(i).bF;
			if (temp > max && tempSR > maxSR) {
				max = temp;
				maxSR = tempSR;
				name = csvRunsList.get(i).playerName;
			}
		}
		System.out.println(name);
		return name;
	}
	
	/**
	 * Usecase5 : Finding Batsman with best batting Average and strike rate
	 * 
	 * @return
	 */
	public String getSortedOnAverageAndStrikeRate() {
		Comparator<MostRunsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.avg);
		this.sort(csvRunsList, iplCSVComparator.thenComparing(entry -> entry.strikeRate));
		String sorted = new Gson().toJson(csvRunsList);
		return sorted;
	}
	
	/**
	 * Usecase6 : Finding BatsMan with max runs and best average in IPL2019
	 * 
	 * @return
	 */
	public String getSortedOnMaxRunsAndStrikeRate() {
		Comparator<MostRunsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.runs);
		this.sort(csvRunsList, iplCSVComparator.thenComparing(entry -> entry.avg));
		String sorted = new Gson().toJson(csvRunsList);
		return sorted;
	}
	
	public String getSortedOnBowlingAvg() {
		Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.avg);
		this.sortForBowling(csvWktsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvWktsList);
		return sorted;
	}
	
	private <E> void sortForBowling(List<MostWktsCSV> csvList, Comparator<MostWktsCSV> iplCSVComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				MostWktsCSV player1 = csvList.get(j);
				MostWktsCSV player2 = csvList.get(j + 1);
				if (iplCSVComparator.compare(player1, player2) > 0 && (player1.wickets != 0 && player2.wickets != 0)) {
					csvList.set(j, player2);
					csvList.set(j + 1, player1);
				}
			}
		}
	}
	
	/**
	 * Usecase8 : Finding Bowler with best striking rate in IPL2019
	 * 
	 * @return
	 */
	public String getSortedOnBowlingStrikeRate() {
		Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.strikeRate);
		this.sortForBowling(csvWktsList, iplCSVComparator);
		String sorted = new Gson().toJson(csvWktsList);
		return sorted;
	}

	private <E> void sortForBowling(List<MostWktsCSV> csvList, Comparator<MostWktsCSV> iplCSVComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				MostWktsCSV player1 = csvList.get(j);
				MostWktsCSV player2 = csvList.get(j + 1);
				if (iplCSVComparator.compare(player1, player2) > 0 && (player1.wickets != 0 && player2.wickets != 0)) {
					csvList.set(j, player2);
					csvList.set(j + 1, player1);
				}
			}
		}
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

	private <E> void sortForBowling(List<MostWktsCSV> csvList, Comparator<MostWktsCSV> iplCSVComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				MostWktsCSV player1 = csvList.get(j);
				MostWktsCSV player2 = csvList.get(j + 1);
				if (iplCSVComparator.compare(player1, player2) > 0 && (player1.wickets != 0 && player2.wickets != 0)) {
					csvList.set(j, player2);
					csvList.set(j + 1, player1);
				}
			}
		}
	}
	
	/**
	 * Usecase10 : Bowler with best strike rate based on four and five wickets haul
	 * 
	 * @return
	 */
	public String getSortedOnStrikeRateAnd4wOr5w() {
		double tempSR = 0;
		TreeMap<Double, String> csvMap = new TreeMap<>();
		for(int i = 0; i < csvWktsList.size(); i++ ) {
			int temp = csvWktsList.get(i).fourWkts * 4 + csvWktsList.get(i).fiveWkts * 5;
			if(temp > 0) {
				tempSR = ((Math.round(csvWktsList.get(i).over)*6 + ((csvWktsList.get(i).over*10)%10)))/temp;
				csvMap.put(tempSR, csvWktsList.get(i).playerName);
			}
		}
		for(Map.Entry<Double, String> entry : csvMap.entrySet()) {
			System.out.println(entry.getKey()+" "+ entry.getValue());
			/**
			 * Usecase11 : Bowler with great averages and best striking rate
			 * 
			 * @return
			 */
			public String getSortedOnBowlingAvgAndStrikeRate() {
				csvWktsList.removeIf(entry -> entry.avg == 0);
				Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.avg, Comparator.reverseOrder());
				List<MostWktsCSV> tempList = this.sort(csvWktsList, iplCSVComparator).stream().limit(10).collect(Collectors.toList());
				this.sort(tempList,  Comparator.comparing(entry -> entry.strikeRate,Comparator.reverseOrder()));
				String sorted = new Gson().toJson(tempList);
				return sorted;
			}

			/**Usecase12 : Bowler with max wickets and best bowling average 
			 * @return
			 */
			public String getSortedOnMaxWicketsAndBowlingAvg() {
				Comparator<MostWktsCSV> iplCSVComparator = Comparator.comparing(entry -> entry.wickets);
				List<MostWktsCSV> tempList = this.sort(csvWktsList, iplCSVComparator).stream().limit(20).collect(Collectors.toList());
				this.sort(tempList,  Comparator.comparing(entry -> entry.avg,Comparator.reverseOrder()));
				String sorted = new Gson().toJson(tempList);
				return sorted;
				
			}private <E> void sortForBowling1(List<MostWktsCSV> csvList, Comparator<MostWktsCSV> iplCSVComparator) {
		for (int i = 0; i < csvList.size(); i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				MostWktsCSV player1 = csvList.get(j);
				MostWktsCSV player2 = csvList.get(j + 1);
				if (iplCSVComparator.compare(player1, player2) > 0 && (player1.wickets != 0 && player2.wickets != 0)) {
					csvList.set(j, player2);
					csvList.set(j + 1, player1);
				}
			}
		}
	}
			/**
			 * Usecase13 : Players with best bowling and batting average
			 * 
			 * @return
			 */
			@SuppressWarnings("unchecked")
			public List<String> getSortedOnBestBattingAndBowlingAvg() {
				List<MostRunsCSV> battingList = (ArrayList<MostRunsCSV>) new Gson().fromJson(this.getAverageWiseSortedData(),
						new TypeToken<ArrayList<MostRunsCSV>>() {
						}.getType());
				List<MostWktsCSV> bowlingList = (ArrayList<MostWktsCSV>) new Gson().fromJson(this.getSortedOnBowlingAvg(),
						new TypeToken<ArrayList<MostWktsCSV>>() {
						}.getType());
				List<String> playerList = new ArrayList<>();
				for (MostRunsCSV bat : battingList) {
					for (MostWktsCSV bowl : bowlingList) {
						if (bat.playerName.equals(bowl.playerName)) {
							playerList.add(bat.playerName);
						}
					}
				}
				return playerList;
			}

}
