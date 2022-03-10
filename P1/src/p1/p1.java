package p1;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class p1 {
	
	private static int rows;
	private static int cols;
	private static int rooms;
	static long start;
	static String[][] mapCoords;
	private static boolean coordBased;
	private static ArrayList<Integer> dequeue = new ArrayList<>();
	
	public static void main(String[] args) {
		Scanner scanner = null;
		File f = new File("JeffMap2Coord.txt");
		coordBased = false;
		
		try {
			//code that might throw a special error
			scanner = new Scanner(f);
			rows = scanner.nextInt();
			cols = scanner.nextInt();
			rooms = scanner.nextInt();
			System.out.println(rows + " " + cols + " " + rooms);
			mapCoords = new String[rows*rooms][cols];		
			int currentRow = 0;
			String[][] map1 = new String[rows*rooms][cols];
			scanner.nextLine();
			
			
			start = System.currentTimeMillis();
			
		
			//determine if input map is coordinate or regular
			String first = scanner.nextLine();
			if (first.substring(2, 3).equals("0")) {
				coordBased = true;
				mapCoords[0][0] = first.substring(0, 1);
			} else {
				for (int i = 0; i < first.length(); i++) {
					map1[currentRow][i] = first.substring(i, i+1);
					//System.out.print(first.charAt(i));
				}
				currentRow++;
			}
			
			if (coordBased == false) {
				//if regular input map fill in map1
				while (scanner.hasNextLine() && coordBased == false) {
					String line = scanner.nextLine();
					if (currentRow < rows*rooms) {
						for (int i = 0; i < cols; i++) {
							map1[currentRow][i] = line.substring(i, i+1);
						}
					}
					currentRow++;
					//use charAt to grab the elements of the map for a given row
				}
				
				
				System.out.println("runtime: " + (System.currentTimeMillis()-start));
				
				
				/*
				//printing coord based map with regular map input
				for (int i = 0; i < rooms; i++) {
					for (int j = i*rows; j < rows*(i+1); j++) {
						for (int k = 0; k < map1[0].length; k++) {
							System.out.println(map1[j][k] + " " + (j - (rows*i)) + " " + k);
						}
					}
				}
				*/
				for (int i = 0 ; i < map1.length; i++) {
					for (int j = 0 ; j < map1[0].length; j++) {
						System.out.print(map1[i][j]);
					}
					System.out.println();
				}
			}
			
			
			//solution if input is coordinate based
			if (coordBased) {
				coordinateBased(scanner);
				for (int i = 1; i <= rooms; i++) {
					queueCakeLocation(scanner, mapCoords, i);
				}
				
			}
			
			
			//solution if input is regular
			if (!coordBased) {
				for (int i = 1; i <= rooms; i++) {
					queueCakeLocation(scanner, map1, i);
				}
			}	
			
		} catch(Exception e) {
			System.out.println(e);	
		}
		
	}
	
	public static void coordinateBased(Scanner scan) {
		
		try {
			
			
			
			//Integer.valueOf(null) to convert from strings to ints
			int currentRoom = 0;
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String symbol = line.substring(0, 1);
				Integer curRow = Integer.valueOf(line.substring(2, 3));
				Integer curCol = Integer.valueOf(line.substring(4, 5));
				if (curRow == 0 && curCol == 0) {
					currentRoom++;
				}
				mapCoords[curRow+(currentRoom*10)][curCol] = symbol;
				//System.out.println(symbol + " " + curRow + " " + curCol);
			
				
				
			}
			System.out.println("runtime: " + (System.currentTimeMillis()-start));
			
			//printing out 2d array map1coords
			System.out.println();
			System.out.println("map1Coords: ");
			for (int i = 0; i < rows*rooms; i++) {
				System.out.println();
				for (int j = 0; j < cols; j++) {
					System.out.print(mapCoords[i][j]);
				}
			}
			
			System.out.println();
			
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("exception catch fail: coordbased");
		}
		
	}
	
	public static void queueCakeLocation(Scanner scan, String[][] map, int currentRoom) {
		try {
			//solution for coord based
			//finding kirby and possible spaces for kirby
			
			
			long locationStart = System.currentTimeMillis();
			Queue<Integer> queue = new LinkedList<>();
			int cakeRow = -1;
			int cakeCol = -1;
			
			
			//turning available paths . into 1 and finding initial location
			for (int i = rows*(currentRoom-1); i < rows*currentRoom; i++) {
				for (int j = 0; j < cols; j++) {
					if (map[i][j].equals(".")) {
						map[i][j] = "1";
					} else if (map[i][j].equals("K")) {
						queue.add(i);
						queue.add(j);
					} 
				}
			}
			
			while (queue.size() > 0) {
				int curRow = queue.remove(); //x
				int curCol = queue.remove(); //y
				dequeue.add(curRow);
				dequeue.add(curCol);
			
				if (curRow > (currentRoom-1)*rows) {
					if (map[curRow-1][curCol].equals("C") || map[curRow-1][curCol].equals("|")) {
						cakeRow = curRow-1;
						cakeCol = curCol;
						System.out.print("found");
						break;
					} else if (map[curRow-1][curCol].equals("1")) {
						queue.add(curRow-1);
						queue.add(curCol);
						map[curRow-1][curCol] = "2";
					}
				}
				if (curRow-((currentRoom-1)*rows) < rows-1) {
					if (map[curRow+1][curCol].equals("C") || map[curRow+1][curCol].equals("|")) {
						cakeRow = curRow+1;
						cakeCol = curCol;
						System.out.print("found");
						break;
					}else if (map[curRow+1][curCol].equals("1")) {
						queue.add(curRow+1);
						queue.add(curCol);
						map[curRow+1][curCol] = "2";
					}
				}
				if (curCol < cols-1) {
					if (map[curRow][curCol+1].equals("C") || map[curRow][curCol + 1].equals("|")) {
						cakeRow = curRow;
						cakeCol = curCol+1;
						System.out.print("found");
						break;
					} else if (map[curRow][curCol + 1].equals("1")) {
						queue.add(curRow);
						queue.add(curCol+1);
						map[curRow][curCol+1] = "2";
					}
				}
				if (curCol > 0) {
					if (map[curRow][curCol-1].equals("C") || map[curRow][curCol-1].equals("|")) {
						cakeRow = curRow;
						cakeCol = curCol - 1;
						System.out.print("found");
						break;
					} else if (map[curRow][curCol-1].equals("1")) {
						queue.add(curRow);
						queue.add(curCol-1);
						map[curRow][curCol-1] = "2";
					}
				}
			}
			
			System.out.println();
			System.out.println("Cake Row: "  + cakeRow + " Cake Col: " + cakeCol);
			
			//optimal path from cake
			
			//printing all moves
			for (int i = 0; i < dequeue.size(); i+=2) {
				int temp = dequeue.get(i);
				int temp2 = dequeue.get(i+1);
				//System.out.println("row: " + temp + "col: " + temp2);
			}
			
			//finding and printing solution
			int index = dequeue.size();
			int curRow = cakeRow;
			int curCol = cakeCol;
			while (index >= 2) {
				int tempRow1 = dequeue.get(index-2);
				int tempCol1 = dequeue.get(index-1);
				//System.out.println(curRow + " " + curCol + " " + tempRow1 + " " + tempCol1);
				if (curRow == tempRow1 && curCol == tempCol1-1 || curRow == tempRow1 && curCol == tempCol1+1 ||
						curRow == tempRow1+1 && curCol == tempCol1 || curRow == tempRow1-1 && curCol == tempCol1) {
					curRow = tempRow1;
					curCol = tempCol1;
				} else {
					dequeue.remove(index-1);
					dequeue.remove(index-2);
				}
				index -= 2;
			}
			
			System.out.println();
			
			//printing optimal coords
			for (int i = 2; i < dequeue.size(); i+=2) {
				int temp = dequeue.get(i);
				int temp2 = dequeue.get(i+1);
				//System.out.println("row: " + temp + "col: " + temp2);
				map[temp][temp2] = "+";
				
			}
			
			//printing solution map
			if (currentRoom == rooms) {
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[0].length; j++) {
						if (map[i][j] == "2") {
							map[i][j] = ".";
						} else if (map[i][j] == "1") {
							map[i][j] = ".";
						}
						System.out.print(map[i][j]);
					}
					System.out.println();
				}
				System.out.println("solution runtime: " + (System.currentTimeMillis()-locationStart));
			}
			
			
		} catch(Exception e) {
			System.out.println();
			System.out.println(e);
		}
	}
	
	
	//true if 1-3 is shorter, false if 2-3 is shorter
	static int temp = dequeue.size()-1;
	public static boolean shorterDistance(int x1, int y1, int x2, int y2, int x3, int y3, int iter) {
		int distance1 = (int) Math.pow((Math.pow(x1-x3, 2) + Math.pow(y1-y3, 2)), 0.5);
		int distance2 = (int) Math.pow((Math.pow(x2-x3, 2) + Math.pow(y2-y3, 2)), 0.5);
		
		if (distance1 == distance2) {
			iter++;
			x3 = dequeue.get(dequeue.size()-2*iter);
			y3 = dequeue.get(dequeue.size()-1*iter);
			return shorterDistance(x1, y1, x2, y2, x3, y3, iter);
		}
		return distance1 < distance2;
	}
	

	
	
	//optimal path using distance and arraylist from kirby
	
	/*
	//removing non optimal moves from dequeue
	int x = 0;
	while (x < dequeue.size() - 5) {
		int curRow = dequeue.get(x);
		int curCol = dequeue.get(x+1);
		int tempRow1 = dequeue.get(x+2);
		int tempCol1 = dequeue.get(x+3);
		int tempRow2 = dequeue.get(x+4);
		int tempCol2 = dequeue.get(x+5);
		
		//determine if possible move from curRow and curCol to next step
		if (!(curRow == tempRow1 && curCol == tempCol1-1 || curRow == tempRow1 && curCol == tempCol1+1 ||
				curRow == tempRow1+1 && curCol == tempCol1 || curRow == tempRow1-1 && curCol == tempCol1)) {
			dequeue.remove(x+2);
			dequeue.remove(x+2);
		} else if (!(curRow == tempRow2 && curCol == tempCol2-1 || curRow == tempRow2 && curCol == tempCol2+1 ||
				curRow == tempRow2+1 && curCol == tempCol2 || curRow == tempRow2-1 && curCol == tempCol2)) {
			x+=2;
		} else {
			if (shorterDistance(tempRow1, tempCol1, tempRow2, tempCol2, cakeRow, cakeCol, 0)) {
				dequeue.remove(x+4);
				dequeue.remove(x+4);
				x+=2;
			} else {
				dequeue.remove(x+2);
				dequeue.remove(x+2);
				x+=2;
			}
		}
		
	}
	*/
	
}

