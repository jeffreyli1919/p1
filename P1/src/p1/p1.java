package p1;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class p1 {
	
	private static int rows, cols, rooms;
	static String[][] mapCoords;
	private static boolean coordBasedInput, coordBasedOutput, legalInput, isSolution, queueBased, showRunTime;
	private static Queue<Integer> queue = new LinkedList<>();
	private static Stack<Integer> stack = new Stack<>();
	private static ArrayList<Integer> dequeue = new ArrayList<>();
	public static void main(String[] args) {
		
				
		//can't change these
		coordBasedOutput = false;
		coordBasedInput = false;
		legalInput = true;
		isSolution = false;
		showRunTime = false; 
				
		
		//*************Command Line Arguments
		int search = 0;
		for (int i = 0; i < args.length; i++) {
			//System.out.println("Argument " + i  + ": "  + args[i]);
			if (args[i].equals("--Stack")) {
				queueBased = false;
				search++;
			} else if (args[i].equals("--Queue")) {
				queueBased = true;
				search++;
			} else if (args[i].equals("--Opt")) {
				queueBased = true;
				search++;
			} else if (args[i].equals("--Help")) {
				System.out.println("Enter commands to help Kirby solve the maze.");
				System.out.println("Enter '--Stack' to use stack based approach.");
				System.out.println("Enter '--Queue' to use queue based approach.");
				System.out.println("Enter '--Opt' to use optimal approach.");
				System.out.println("Enter '--Time' to see runtime.");
				System.out.println("Enter '--Incoordinate' if the input file is coord Based.");
				System.out.println("Enter '--Outcoordinate' to see output file in coord Based system.");
				System.out.println("Important: Enter the name of the file containing your map as the last argument");
				System.out.println("Enter '--Help' for help.");
				System.exit(-1);
			} 
		}
		
		if (search != 1) {
			System.out.println("Error: Please enter ONE of stack, queue, or opt for an approach. Enter '--Help' for help");
			System.exit(-1);
		}
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--Time")) {
				showRunTime = true;
			} else if (args[i].equals("--Incoordinate")) {
				coordBasedInput = true;
			} else if (args[i].equals("--Outcoordinate")) {
				coordBasedOutput = true;
			} 
		}
		//***************
		
		Scanner scanner = null;
		File f = new File(args[args.length-1]);
		
		try {
			//code that might throw a special error
			//initializing stuff
			//map1 is used if regular input
			//mapCoords is used if coord input
			scanner = new Scanner(f);
			rows = scanner.nextInt();
			cols = scanner.nextInt();
			rooms = scanner.nextInt();
			//System.out.println(rows + " " + cols + " " + rooms);
			mapCoords = new String[rows*rooms][cols];		
			int currentRow = 0;
			String[][] map1 = new String[rows*rooms][cols];
			scanner.nextLine();
			
			
			
		
			//determine if input map is coordinate or regular by simply looking at line 1
			String first = scanner.nextLine();
			if (first.substring(2, 3).equals("0")) {
				coordBasedInput = true;
				mapCoords[0][0] = first.substring(0, 1);
			} else {
				for (int i = 0; i < first.length(); i++) {
					map1[currentRow][i] = first.substring(i, i+1);
				}
				currentRow++;
			}
			
			if (coordBasedInput == false) {
				//if regular input map fill in map1
				while (scanner.hasNextLine() && legalInput) {
					String line = scanner.nextLine();
					if (currentRow < rows*rooms) {
						for (int i = 0; i < cols; i++) {
							if (line.substring(i, i+1).equals(".") || line.substring(i, i+1).equals("C") || line.substring(i, i+1).equals("@") || line.substring(i, i+1).equals("K")
									|| line.substring(i, i+1).equals("|")) {
								map1[currentRow][i] = line.substring(i, i+1);
							} else { //checking for illegal characters
								legalInput = false;
								System.out.println("Error: illegal character");
								break;
							}
						}
					}
					currentRow++;
					//use charAt to grab the elements of the map for a given row
				}
				
				//printing original regular input map
				if (legalInput) {
					for (int i = 0 ; i < map1.length; i++) {
						for (int j = 0 ; j < map1[0].length; j++) {
							//System.out.print(map1[i][j]);
						}
						//System.out.println();
					}
				}
			}
			
			/* this prints out the coords of a regular based map to make a coord-based input map
			 * of the regular map input
			for (int i = 0; i < rooms; i++) {
				for (int j = i*rows; j < rows*(i+1); j++) {
					for (int k = 0; k < map1[0].length; k++) {
						System.out.println(map1[j][k] + " " + (j - (rows*i)) + " " + k);
					}
				}
			}
			*/
			
			//solution if input is coordinate based
			if (coordBasedInput) {
				coordinateBased(scanner);
				if (legalInput) {
					
					double time = System.currentTimeMillis();
					for (int i = 1; i <= rooms; i++) {
						 if (queueBased)  {queueApproach(scanner, mapCoords, i); }
						 else { stackApproach(scanner, mapCoords, i); }
					}
					if (showRunTime) System.out.println("Solution runtime: " + (((double) System.currentTimeMillis()) - time));
					
				}
			}
			
			
			//solution if input is regular
			if (!coordBasedInput) {
				if (legalInput) {
					double time = System.currentTimeMillis();
					for (int i = 1; i <= rooms; i++) {
						if (queueBased) queueApproach(scanner, map1, i);
						 else { stackApproach(scanner, map1, i); }
					}
					if (showRunTime) System.out.println("Solution runtime: " + (((double) System.currentTimeMillis()) - time));
				}
			}	
			
			//runtime
		} catch(Exception e) {
			System.out.println(e);	
		}
		
	}
	
	public static void coordinateBased(Scanner scan) {
		
		try {
			//insantiate stuff
			int currentRoom = 0;
			int col = 1;
			int row = 0;
			Integer curRow = 0;
			Integer curCol = 0;
			
			//storing all values in coord based map into 2d array
			while (scan.hasNextLine()) {
				
				//when column reaches the last col move to next row and reset col
				if (col >= cols) {
					col = 0;
					row++;
				} 
				
				//when row reaches last row move to next room and reset row and col
				if (row >= rows) {
					row = 0;
					col = 0;
					currentRoom++;
				}
				
				String line = scan.nextLine();
				String symbol = line.substring(0, 1);
				
				//determing what to scan based off of whether row and col are single digit or double digit
				if (row >= 10 && col < 10) {
					curRow = Integer.valueOf(line.substring(2, 4));
					curCol = Integer.valueOf(line.substring(5, 6));
				} else if (row < 10 && col >= 10){
					curRow = Integer.valueOf(line.substring(2, 3));
					curCol = Integer.valueOf(line.substring(4, 6));
				} else if (row >= 10 && col >= 10) {
					curRow = Integer.valueOf(line.substring(2, 4));
					curCol = Integer.valueOf(line.substring(5, 7));
				} else {
					curRow = Integer.valueOf(line.substring(2, 3));
					curCol = Integer.valueOf(line.substring(4, 5));
				}
				//increasing column for the next coordinate
				col++;
				
				//checking for invalid symbols
				if (symbol.equals(".") || symbol.equals("K")|| symbol.equals("C") || symbol.equals("@") || symbol.equals("|")) {
					mapCoords[curRow+(currentRoom*rows)][curCol] = symbol;
				} else {
					legalInput = false;
					System.out.println("Error: illegal character");
				}
				
			}
			//print map for coord based input
			//System.out.println();
			//System.out.println("map1Coords: ");
			boolean complete = true;
			for (int i = 0; i < rows*rooms; i++) {
				for (int j = 0; j < cols; j++) {
					//checking for missing coordinates
					if (mapCoords[i][j] == null) {
						System.out.println();
						legalInput = false;
						complete = false;
						System.out.println("Error: incomplete map");
						break;
					}
					if (complete) {
						//System.out.print(mapCoords[i][j]);
					}
				}
				//System.out.println();
			}
			
			//System.out.println();
			
		} catch(Exception e) {
			legalInput = false;
			System.out.println("Error: Coordinate does not fit within maze");
		}
		
	}
	//this is the approach to finding the cake using a queue, called once for each room
	public static void queueApproach(Scanner scan, String[][] map, int currentRoom) {
		try {
			int cakeRow = -1;
			int cakeCol = -1;
			//turning available paths . into 1 and finding initial location
			//checking for a door or cake to determine solution is potentially possible
			for (int i = rows*(currentRoom-1); i < rows*currentRoom; i++) {
				for (int j = 0; j < cols; j++) {
					if (map[i][j].equals(".")) {
						map[i][j] = "1";
					} else if (map[i][j].equals("K")) {
						queue.add(i);
						queue.add(j);
					} else if (map[i][j].equals("|") || map[i][j].equals("C")) {
						isSolution = true;
						cakeRow = i;
						cakeCol = j;
					}
				}
			}
			//if no cake or door is present
			if (isSolution == false) {
				System.out.println("The Cake is a Lie.");
			}
			
			//adding and removing moves from queue in order of North South East West
			//if the cake if found the loop stops
			//moves are stored in dequeue
			while (queue.size() >= 2) {
				int curRow = queue.remove(); //x
				int curCol = queue.remove(); //y
				dequeue.add(curRow);
				dequeue.add(curCol);
				if (curRow > (currentRoom-1)*rows) {
					if (map[curRow-1][curCol].equals("C") || map[curRow-1][curCol].equals("|")) {
						cakeRow = curRow-1;
						cakeCol = curCol;
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
						break;
					} else if (map[curRow][curCol-1].equals("1")) {
						queue.add(curRow);
						queue.add(curCol-1);
						map[curRow][curCol-1] = "2";
					}
				}
			}
			
			//finding optimal paths if cake or door exists within the room
			if (isSolution) {
				findOptimalPath(map, cakeRow, cakeCol);
			}
			//printing out the solution map if there is an optimal path within every room
			if (isSolution) {
				if (currentRoom == rooms) {
					if (!coordBasedOutput) {
						printSolution(map);
					}
					
				}
			}
			
		} catch(Exception e) {
			System.out.println();
			System.out.println(e);
		}
	}
	
	//this is the approach to finding the cake using a stack, called once for each room
	public static void stackApproach(Scanner scan, String[][] map, int currentRoom) {
		try {
			int cakeRow = -1;
			int cakeCol = -1;
			//turning available paths . into 1 and finding initial location of Kirby
			//checking for a door or cake to determine solution is potentially possible
			for (int i = rows*(currentRoom-1); i < rows*currentRoom; i++) {
				for (int j = 0; j < cols; j++) {
					if (map[i][j].equals(".")) {
						map[i][j] = "1";
					} else if (map[i][j].equals("K")) {
						stack.push(i);
						stack.push(j);
					} else if (map[i][j].equals("|") || map[i][j].equals("C")) {
						isSolution = true;
						cakeRow = i;
						cakeCol = j;
					}
				}
			}
			//if no cake or door present in the room
			if (isSolution == false) {
				System.out.println("The Cake is a Lie.");
			}
			//adding possible moves in north, south, east, west locations of the current spot of Kirby
			//Kirby's move are kept track in dequeue
			//loop stops when cake is found
			while (stack.size() >= 2) {
				int curCol = stack.pop(); //y
				int curRow = stack.pop(); //x
				//System.out.println(curRow + " " + curCol);
				dequeue.add(curRow);
				dequeue.add(curCol);
				if (curRow > (currentRoom-1)*rows) {
					if (map[curRow-1][curCol].equals("C") || map[curRow-1][curCol].equals("|")) {
						cakeRow = curRow-1;
						cakeCol = curCol;
						break;
					} else if (map[curRow-1][curCol].equals("1")) {
						stack.push(curRow-1);
						stack.push(curCol);
						map[curRow-1][curCol] = "2";
					}
				}
				if (curRow-((currentRoom-1)*rows) < rows-1) {
					if (map[curRow+1][curCol].equals("C") || map[curRow+1][curCol].equals("|")) {
						cakeRow = curRow+1;
						cakeCol = curCol;
						break;
					}else if (map[curRow+1][curCol].equals("1")) {
						stack.push(curRow+1);
						stack.push(curCol);
						map[curRow+1][curCol] = "2";
					}
				}
				if (curCol < cols-1) {
					if (map[curRow][curCol+1].equals("C") || map[curRow][curCol + 1].equals("|")) {
						cakeRow = curRow;
						cakeCol = curCol+1;
						break;
					} else if (map[curRow][curCol + 1].equals("1")) {
						stack.push(curRow);
						stack.push(curCol+1);
						map[curRow][curCol+1] = "2";
					}
				}
				if (curCol > 0) {
					if (map[curRow][curCol-1].equals("C") || map[curRow][curCol-1].equals("|")) {
						cakeRow = curRow;
						cakeCol = curCol - 1;
						break;
					} else if (map[curRow][curCol-1].equals("1")) {
						stack.push(curRow);
						stack.push(curCol-1);
						map[curRow][curCol-1] = "2";
					}
				}
			}
			
			//calls optimal path if cake and door exists
			if (isSolution) {
				findOptimalPath(map, cakeRow, cakeCol);
			}
			//calls to print solution if every room has an optimal path
			if (isSolution) {
				if (currentRoom == rooms) {
					if (!coordBasedOutput) {
						printSolution(map);
					}
					
				}
			}
			
		} catch(Exception e) {
			System.out.println();
			System.out.println(e);
		}
	}
	
	//Takes in all the locations visited by Kirby and removes all moves that aren't optimal
	public static void findOptimalPath(String map[][], int cakeRow, int cakeCol) {
		
		//finding optimal path by removing non optimal moves
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
		
		//checking if solution exists to see whether to continue to printing the solution
		if (dequeue.size() < 2) {
			isSolution = false;
		}

		//printing the coordinates of the optimal path
		if (isSolution) {
			for (int i = 2; i < dequeue.size(); i+=2) {
				int temp = dequeue.get(i);
				int temp2 = dequeue.get(i+1);
				map[temp][temp2] = "+";
				if (coordBasedOutput) {
					int roomAdjust = temp / rows;
					System.out.println("+" + " " + (temp-(roomAdjust*rows)) + " " + temp2);
				}
				
				
			}
		} else { //printing that there was no optimal path to the cake
			System.out.println("The Cake is a Lie, cake exists but no solution");
		}
		
	}
	
	//prints the solution map with the optimal path and whether stack/queue approach was used
	public static void printSolution(String map[][]) {
		//printing solution map
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
		
		/*
		if (queueBased) {
			 System.out.println("Solution with Queue");
		} else {
			 System.out.println("Solution with Stack");
		}
		*/
		
	}
}

