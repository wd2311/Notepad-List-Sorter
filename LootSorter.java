package lootSorter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LootSorter {
	
	int[] amtOfItem = new int[46];
	String[] lines;
	String[] names = new String[46];
	String[] times;
	int kills = 0;
	int amtOfLines = 0;
	String totalTime;
	String averageTime;
	
	public LootSorter(){
		names[0] = "Zulrah Scales";
		names[1] = "Tanzanite Fang";
		names[2] = "Magic Fang";
		names[3] = "Serpentine Visage";
		names[4] = "Uncut Onyx";
		names[5] = "Dragon Med Helm";
		names[6] = "Dragon Halberd";
		names[7] = "Law Rune";
		names[8] = "Death Rune";
		names[9] = "Chaos Rune";
		names[10]= "Pure Essence";
		names[11]= "Toadflax Seed";
		names[12]= "Snapdragon Seed";
		names[13]= "Dwarf Weed Seed";
		names[14]= "Torstol Seed";
		names[15]= "Toadflax";
		names[16]= "Snapdragon";
		names[17]= "Dwarf Weed";
		names[18]= "Torstol";
		names[19]= "Spirit Seed";
		names[20]= "Crystal Seed";
		names[21]= "Palm Tree Seed";
		names[22]= "Papaya Tree Seed";
		names[23]= "Calquat Tree Seed";
		names[24]= "Magic Seed";
		names[25]= "Flax";
		names[26]= "Snakeskin";
		names[27]= "Dragon Bolt Tips";
		names[28]= "Magic Logs";
		names[29]= "Coal";
		names[30]= "Runite Ore";
		names[31]= "Zul-Andra Teleport";
		names[32]= "Dragon Bones";
		names[33]= "Coconut";
		names[34]= "Grapes";
		names[35]= "Battlestaff";
		names[36]= "Antidote++";
		names[37]= "Raw Shark";
		names[38]= "Mahogany Plank";
		names[39]= "Swamp Tar";
		names[40]= "Saradomin Brew";
		names[41]= "Adamantite Bar";
		names[42]= "Crystal Key";
		names[43]= "Elite Clue Scroll";
		names[44]= "Jar of Swamp";
		names[45]= "Pet Snakeling";
	}//Constorter
	
	public static void main(String[]args) throws IOException{
		LootSorter GO = new LootSorter();
		GO.run();
	}//main
	
	private void run(){
		try {
			saveOntoArrays();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getTimes();
		getItemAmounts();
	    printAll();
	}//run
	
	private void saveOntoArrays() throws IOException{
		String filename = "C:/Users/wd2311/Desktop/EclipseWorkspace/ZulrahLootSorter/src/lootSorter/ZulrahLootNotepad.txt";
		
	    FileReader in = new FileReader(filename);
	    BufferedReader br = new BufferedReader(in);
	    
	    br.mark(10000000);
	    String line;
	    while ((line = br.readLine()) != null) {
	    	line = line.toLowerCase();
	    	amtOfLines = amtOfLines + 1;
	    	if(line.startsWith("kill")){
	    		kills = kills + 1;
	    	}
	    }//figure out amount of lines, and amount of kills
	    br.reset();
	    
	    lines = new String[amtOfLines];
	    for(int i = 0; i < amtOfLines; i ++){
	    	lines[i] = br.readLine().toLowerCase();
	    }//save all of the lines that are read on to the "lines" array so i can play with them
	    in.close();
	}//saveOntoArrays
	
	private void getTimes(){
		times = new String[kills];
	    int timesRegistered = 0; 
	    for(int i = 0; i < amtOfLines; i ++){
	    	if(lines[i].startsWith("kill")){
	    		for(int j = 0; j < lines[i].length(); j ++){
	    			if(lines[i].charAt(j) == ':'){
	    				times[timesRegistered] = lines[i].substring(j + 2, j + 6);
	    				timesRegistered = timesRegistered + 1;
	    				break;
	    			}
	    		}
	    	}
	    }//this block records the times and puts them on the "times" array    
	    
	    int total = 0;
	    for(int i = 0; i < kills; i ++){
	    	total = total + ((60*Integer.parseInt(times[i].substring(0, 1))) + Integer.parseInt(times[i].substring(2, 4)));//total in seconds
	    }
	    double average = (double) total / (double) kills;//average in seconds
	    
	    //Below puts together averageTime (minutes:seconds)
	    int secondsColumn = (int) ((average) - 60 * Math.floor(average/60));//seconds column for average
	    if(secondsColumn < 10){
	    	averageTime = ((int) Math.floor(average/60) + ":0" + secondsColumn);//if the seconds column is one digit, add a 0 before it
	    }else{
	    	averageTime = ((int) Math.floor(average/60) + ":" + secondsColumn);
	    }
	    
	    //Below puts together totalTime (hours:minutes:seconds)
	    int totalHoursColumn = (int) Math.floor(total/3600);
	    int totalMinutesColumn = (int) Math.floor(total - totalHoursColumn*3600)/60;
	    int totalSecondsColumn = (int) Math.floor((total - totalHoursColumn*3600) - (totalMinutesColumn*60));
	    totalTime = "" + totalHoursColumn;
	    if(totalMinutesColumn < 10){
	    	totalTime = (totalTime + ":0" + totalMinutesColumn);
	    }else{
	    	totalTime = (totalTime + ":" + totalMinutesColumn);
	    }
	    if(totalSecondsColumn < 10){
	    	totalTime = (totalTime + ":0" + totalSecondsColumn);
	    }else{
	    	totalTime = (totalTime + ":" + totalSecondsColumn);
	    }
	}//getTimes
	
	private void getItemAmounts(){
		  for(int i = 0; i < amtOfLines; i ++){
		    	if(!lines[i].contains("kill ")){//if it's not a kill line
		    		for(int j = 0; j < lines[i].length(); j ++){
			    		if(lines[i].charAt(j) == ' '){
			    			int quantity = Integer.parseInt(lines[i].substring(0, j));//how many do we have?
			    			for(int k = 0; k < 45; k ++){
			    				if(lines[i].contains(names[k].toLowerCase())){//which item is it?
			    					amtOfItem[k] = amtOfItem[k] + quantity;
			    					break;
			    				}
			    			}
			    			break;
			    		}
		    		}
		    	}
		    }
	}//getItemAmounts
	
	private void printAll(){
		System.out.println("Amount of Kills: " + kills);
		System.out.println("Total Time: " + totalTime);
		System.out.println("Average Time: " + averageTime + "\n");
		
		for(int i = 0; i < 45; i ++){
	    	System.out.println(names[i] + " : " + amtOfItem[i]);
	    }
		System.out.println("\nTimes:");
		for(int i = 0; i < times.length; i ++){
			System.out.println("Kill " + (i+1) + ": " + times[i]);
		}
	}//printTotalLoot
}//LootSorter