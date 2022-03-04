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
	static String[][] map1Coords;
	private static boolean coordBased;
	
	public static void main(String[] args) {
		Scanner scanner = null;
		File f = new File("JeffMap3Coord.txt");
		coordBased = false;
		
		try {
			//code that might throw a special error
			scanner = new Scanner(f);
			rows = scanner.nextInt();
			cols = scanner.nextInt();
			rooms = scanner.nextInt();
			System.out.println(rows + " " + cols + " " + rooms);
			map1Coords = new String[rows][cols];		
			int currentRow = 0;
			String[][] map1 = new String[rows][cols];
			String[][] map2 = new String[rows][cols];
			scanner.nextLine();
			
			
			start = System.currentTimeMillis();
			
			
			//determine if input map is coordinate or regular
			String first = scanner.nextLine();
			if (first.substring(2, 3).equals("0")) {
				coordBased = true;
				map1Coords[0][0] = first.substring(0, 1);
			} else {
				for (int i = 0; i < first.length(); i++) {
					map1[currentRow][i] = first.substring(i, i+1);
					System.out.print(first.charAt(i));
				}
				System.out.println();
				currentRow++;
			}
			
			//if regular input map fill in map1
			while (scanner.hasNextLine() && coordBased == false) {
				String line = scanner.nextLine();
				if (currentRow < rows) {
					for (int i = 0; i < line.length(); i++) {
						System.out.print(line.charAt(i));
						map1Coords[currentRow][i] = line.substring(0, 1);
						map1[currentRow][i] = line.substring(i, i+1);
					}
				} else if (currentRow >= rows) {
					for (int i = 0; i < line.length(); i++) {
						System.out.print(line.charAt(i));
						map2[currentRow][i] = line.substring(i, i+1);
					}
				}
				System.out.println();
				currentRow++;
				//use charAt to grab the elements of the map for a given row
			}
			
			
			System.out.println("runtime: " + (System.currentTimeMillis()-start));
			
		
			//solution if input is coordinate based
			if (coordBased) {
				coordinateBased(scanner);
				queueCakeLocation(scanner, map1Coords);
			}
			
			
			//solution if input is regular
			if (!coordBased) {
				queueCakeLocation(scanner, map1);
			}
			
			
			
			
			
			
			
			
		} catch(Exception e) {
			System.out.println(e);
			
			
			
		}
		
	}
	
	public static void coordinateBased(Scanner scan) {
		
		try {
			
			
			
			//Integer.valueOf(null) to convert from strings to ints
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String symbol = line.substring(0, 1);
				Integer curRow = Integer.valueOf(line.substring(2, 3));
				Integer curCol = Integer.valueOf(line.substring(4, 5));
				map1Coords[curRow][curCol] = symbol;
				System.out.println(symbol + " " + curRow + " " + curCol);
			
				
				
			}
			System.out.println("runtime: " + (System.currentTimeMillis()-start));
			
			//printing out 2d array map1coords
			System.out.println();
			System.out.println("map1Coords: ");
			for (int i = 0; i < rows; i++) {
				System.out.println();
				for (int j = 0; j < cols; j++) {
					System.out.print(map1Coords[i][j]);
				}
			}
			
			System.out.println();
			
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("exception catch fail: coordbased");
		}
		
	}
	
	public static void queueCakeLocation(Scanner scan, String[][] map) {
		try {
			//solution for coord based
			//finding kirby and possible spaces for kirby
			
			
			long locationStart = System.currentTimeMillis();
			Queue<Integer> queue = new LinkedList<>();
			ArrayList<Integer> dequeue = new ArrayList<>();
			int cakeRow = 0;
			int cakeCol = 0;
			
			
			//turning available paths . into 1 and finding initial location
			for (int i = 0; i < rows; i++) {
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
				//System.out.println(curRow + " " + curCol);
				if (map[curRow][curCol].equals("1")) {
					map[curRow][curCol] = "+";
				}
			
				
				if (curCol > 0) {
					if (map[curRow][curCol-1].equals("C")) {
						cakeRow = curRow;
						cakeCol = curCol - 1;
						System.out.print("found");
						break;
					} else if (map[curRow][curCol-1].equals("1")) {
						queue.add(curRow);
						queue.add(curCol-1);
					}
				}
				if (curCol < cols-1) {
					if (map[curRow][curCol+1].equals("C")) {
						cakeRow = curRow;
						cakeCol = curCol+1;
						System.out.print("found");
						break;
					} else if (map[curRow][curCol + 1].equals("1")) {
						queue.add(curRow);
						queue.add(curCol+1);
					}
				}
				
				if (curRow < rows-1) {
					if (map[curRow+1][curCol].equals("C")) {
						cakeRow = curRow+1;
						cakeCol = curCol;
						System.out.print("found");
						break;
					}else if (map[curRow+1][curCol].equals("1")) {
						queue.add(curRow+1);
						queue.add(curCol);
					}
				}
				
				if (curRow > 0) {
					if (map[curRow-1][curCol].equals("C")) {
						cakeRow = curRow-1;
						cakeCol = curCol;
						System.out.print("found");
						break;
					} else if (map[curRow-1][curCol].equals("1")) {
						queue.add(curRow-1);
						queue.add(curCol);
					}
				}
				
				//System.out.println("end");
				//System.out.println("size: " + queue.size());
			}
			
			System.out.println();
			System.out.println("Cake Row: "  + cakeRow + " Cake Col: " + cakeCol);
			System.out.println("location runtime: " + (System.currentTimeMillis()-locationStart));
			
			//optimal path
			for (int i = 0; i < dequeue.size(); i+=2) {
				int temp = dequeue.get(i);
				int temp2 = dequeue.get(i+1);
				System.out.println("row: " + temp + "col: " + temp2);
			}
			
			//removing non optimal moves from dequeue
			int x = 1;
			while (x < dequeue.size()) {
				
			}
			
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			
		} catch(Exception e) {
			System.out.println();
			System.out.println(e);
		}
	}
	
	public static void stackSolution(Scanner scan, String[][] map) {
		try {
			long locationStart = System.currentTimeMillis();
			Stack<Integer> stack = new Stack<>();
			int cakeRow = 0;
			int cakeCol = 0;
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (map[i][j].equals(".")) {
						map[i][j] = "1";
					} else if (map[i][j].equals("K")) {
						stack.push(i);
						stack.push(j);
					} 
				}
			}

			
			while (stack.size() > 0) {
				int curRow = stack.pop(); //x
				int curCol = stack.pop(); //y
				//System.out.println(curRow + " " + curCol);
			
				map[curRow][curCol] = "+";
				
				if (curCol > 0) {
					if (map[curRow][curCol-1].equals("C")) {
						cakeRow = curRow;
						cakeCol = curCol - 1;
						System.out.print("found");
						break;
					} else if (map[curRow][curCol-1].equals("1")) {
						stack.push(curRow);
						stack.push(curCol-1);
						
					}
				}
				if (curCol < cols-1) {
					if (map[curRow][curCol+1].equals("C")) {
						cakeRow = curRow;
						cakeCol = curCol+1;
						System.out.print("found");
						break;
					} else if (map[curRow][curCol + 1].equals("1")) {
						stack.push(curRow);
						stack.push(curCol+1);
					}
				}
				
				if (curRow < rows-1) {
					if (map[curRow+1][curCol].equals("C")) {
						cakeRow = curRow+1;
						cakeCol = curCol;
						System.out.print("found");
						break;
					}else if (map[curRow+1][curCol].equals("1")) {
						stack.push(curRow+1);
						stack.push(curCol);
					}
				}
				
				if (curRow > 0) {
					if (map[curRow-1][curCol].equals("C")) {
						cakeRow = curRow-1;
						cakeCol = curCol;
						System.out.print("found");
						break;
					} else if (map[curRow-1][curCol].equals("1")) {
						stack.push(curRow-1);
						stack.push(curCol);
					}
				}
				
				//System.out.println("size: " + stack.size());
			}
			
			System.out.println();
			System.out.println("Cake Row: "  + cakeRow + " Cake Col: " + cakeCol);
			System.out.println("location runtime: " + (System.currentTimeMillis()-locationStart));
			
			
			
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			
			
			
		} catch(Exception e) {
			System.out.println();
			System.out.println(e);
		}
	}
}

