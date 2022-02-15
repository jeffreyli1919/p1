package p1;

import java.io.File;
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
			
			
			System.out.println("hi");
			//solution for coord based
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (map1Coords[i][j].equals(".")) {
						map1Coords[i][j] = "1";
					}
				}
			}
			
			for (int i = 0; i < rows; i++) {
				System.out.println();
				for (int j = 0; j < cols; j++) {
					System.out.print(map1Coords[i][j]);
				}
			}
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
}

