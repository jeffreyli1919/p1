package p1;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class p1 {
	
	private static int rows;
	private static int cols;
	private static int rooms;
	static long start = System.currentTimeMillis();
	static String[][] map1Coords;
	
	public static void main(String[] args) {
		Scanner scanner = null;
		File f = new File("JeffMap1Coord.txt");
		
		try {
			//code that might throw a special error
			scanner = new Scanner(f);
			rows = scanner.nextInt();
			cols = scanner.nextInt();
			rooms = scanner.nextInt();
			System.out.println(rows + " " + cols + " " + rooms);
			map1Coords = new String[rows][cols];
		
			//use next methods to grab
			
			int currentRow = 0;
			char[][] map1 = new char[rows][cols];
			char[][] map2 = new char[rows][cols];
			
			scanner.nextLine();
			
			
			//take in the cols and # of rooms
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (currentRow < rows) {
					for (int i = 0; i < line.length(); i++) {
						System.out.print(line.charAt(i));
						map1Coords[currentRow][i] = line.substring(0, 1);
						map1[currentRow][i] = line.charAt(i);
					}
				} else if (currentRow >= rows) {
					for (int i = 0; i < line.length(); i++) {
						System.out.print(line.charAt(i));
						map2[currentRow-rows][i] = line.charAt(i);
					}
				}
				System.out.println();
				currentRow++;
				//System.out.println("currentRow: " + currentRow);
				//use charAt to grab the elements of the map for a given row
			}
			System.out.println("runtime: " + (System.currentTimeMillis()-start));
			
			//printing out map1
			for (int i = 0; i < rows; i++) {
				System.out.println();
				for (int j = 0; j < cols; j++) {
					System.out.print(map1[i][j] + "");
				}
			}
			
			
			
			
		} catch(Exception e) {
			//System.out.println(e);
			System.out.println();
			coordinateBased(scanner);
			queueSolution(scanner);
			
			
			
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
			for (int i = 0; i < rows; i++) {
				System.out.println();
				for (int j = 0; j < cols; j++) {
					System.out.print(map1Coords[i][j]);
				}
			}
			
			System.out.println();
			
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("exception catch fail");
		}
		
	}
	
	public static void queueSolution(Scanner scan) {
		try {
			//solution for coord based
			//finding kirby and possible spaces for kirby
			
			
			long start = System.currentTimeMillis();
			
			Queue<Integer> queue = new LinkedList<>();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (map1Coords[i][j].equals(".")) {
						map1Coords[i][j] = "1";
					} else if (map1Coords[i][j].equals("K")) {
						queue.add(i);
						queue.add(j);
					} 
				}
			}
			
			for (int i = 0; i < rows; i++) {
				System.out.println();
				for (int j = 0; j < cols; j++) {
					System.out.print(map1Coords[i][j]);
				}
			}
			
			while (queue.size() > 0) {
				int curY = queue.remove(); //3
				int curX = queue.remove(); //0
				System.out.println(curX + " " + curY);
				//System.out.println();
				//System.out.println(curX);
				//System.out.println(curY);
				//System.out.println(rows);
				//System.out.println(cols);
				
			
				
				if (map1Coords[curX][curY].equals("C")) {
					System.out.print("found");
				}
				
				map1Coords[curX][curY] = "-1";
				
				if (curY > 0) {
					if (map1Coords[curY-1][curX].equals("1")) {
						queue.add(curY-1);
						queue.add(curX);
					}
				}
				if (curY < cols-1) {
					if (map1Coords[curY+1][curX].equals("1")) {
						queue.add(curY+1);
						queue.add(curX);
					}
				}
				
				if (curX < rows-1) {
					//rows are y cols are x
					if (map1Coords[curY][curX+1].equals("1")) {
						queue.add(curY);
						queue.add(curX+1);
						//System.out.println("added");
					}
				}
				
				if (curX > 0) {
					if (map1Coords[curY][curX-1].equals("1")) {
						queue.add(curY);
						queue.add(curX-1);
					}
				}
				
				//System.out.println("end");
				System.out.println(queue.size());
			}
			
			System.out.println();
			System.out.println("runtime: " + (System.currentTimeMillis()-start));
			
			
		} catch(Exception e) {
			System.out.println();
			System.out.println(e);
		}
	}
}

