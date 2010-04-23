package binpack.comp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class DataGetter {
	public static void main (String args[]){
		try{
		Scanner scan1 = new Scanner(new File("comp.txt"));
		ArrayList<String> resultLinesSga = new ArrayList<String>();
		ArrayList<String> resultLinesGwa = new ArrayList<String>();
 		HashMap<Integer, Integer[]> hardnessSet = new HashMap<Integer, Integer[]>();
		
		while (scan1.hasNextLine()){
			String line;
			int sga = 0, gwa = 0, tie = 0, stie=0, gtie=0, ttie=0;
			if ((line = scan1.nextLine()).contains("@")){
				scan1.nextLine();
				line = scan1.nextLine().trim();
				int hardness = Integer.valueOf(line.split(" ")[1]);
				while (!(line = scan1.nextLine()).trim().startsWith("SGA:"))
					continue;
				while (!(line = scan1.nextLine()).trim().startsWith("GWA:"))
					continue;
				while (!(line = scan1.nextLine()).trim().startsWith("SGA:"))
					continue;
				double sgaval = Double.valueOf(line.split(" ")[1].split("%")[0]);
				int sgagen = Integer.valueOf(line.split("#")[1]);
				while (!(line = scan1.nextLine()).trim().startsWith("GWA:"))
					continue;
				double gwaval = Double.valueOf(line.split(" ")[1].split("%")[0]);
				int gwagen = Integer.valueOf(line.split("#")[1]);
				
				if (sgaval > gwaval)
					sga++;
				else if (sgaval < gwaval)
					gwa++;
				else{
					tie++;
					if (gwagen < sgagen)
						gtie++;
					else if (sgagen < gwagen)
						stie++;
					else
						ttie++;
				}
				
				//{sga wins, gwa wins, ties, sgatiewin, gwatiewin, realtie, numberofinstances, %oversga, %overgwa}
				int gwaover = (sgaval < gwaval)? (int)(1000*(gwaval-sgaval)) : 0, sgaover = (gwaval < sgaval)? (int)(1000*(sgaval-gwaval)) : 0;					
				
				if (!hardnessSet.containsKey(new Integer(hardness))){
					Integer[] vals = {new Integer(sga), new Integer(gwa), new Integer(tie), new Integer(stie), new Integer(gtie), new Integer(ttie), 1, sgaover, gwaover};
					hardnessSet.put(new Integer(hardness), vals);
				}else
				{
					Integer[] vals = hardnessSet.get(new Integer(hardness));
					vals[0]+=sga;
					vals[1]+=gwa;
					vals[2]+=tie;
					vals[3]+=stie;
					vals[4]+=gtie;
					vals[5]+=ttie;
					if (sgaover > 0){
						vals[7]*=vals[6];
						vals[7]+=sgaover;
						vals[7]/=vals[6]++;					
					}
					if (gwaover > 0){
						vals[8]*=vals[6];
						vals[8]+=sgaover;
						vals[8]/=vals[6]++;					
					}
				}}
		}
		for (Integer hard : hardnessSet.keySet()){
			System.out.println("Hardness :" + hard);
			Integer[] vals = hardnessSet.get(hard);
			System.out.println("SGA Wins: " + vals[0]);
			System.out.println("GWA Wins: " + vals[1]);
			System.out.println("Ties: " + vals[2]);
			System.out.println("SGA Tie Wins: " + vals[3]);
			System.out.println("GWA Tie Wins: " + vals[4]);
			System.out.println("True Ties: " + vals[5]);
			System.out.println("Trials: " + vals[6]);
			System.out.println("SGA Over %: " + (double)vals[7]/1000);
			System.out.println("GWA Over %: " + (double)vals[8]/1000);

		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
