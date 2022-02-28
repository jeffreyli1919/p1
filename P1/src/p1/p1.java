package p1;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class p1 {
	
	private static int rows;
	private static int cols;
	private static int rooms;
	static long start;
	static String[][] map1Coords;
	
	public static void main(String[] args) {
		Scanner scanner = null;
		File f = new File("JeffMap3Coord.txt");
		
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
			String[][] map1 = new String[rows][cols];
			String[][] map2 = new String[rows][cols];
			
			scanner.nextLine();
			
			//take in the cols and # of rooms
			
			
			start = System.currentTimeMillis();
			while (scanner.hasNextLine()) {
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
			
			//printing out map1
//			for (int i = 0; i < rows; i++) {
//				System.out.println();
//				for (int j = 0; j < cols; j++) {
//					System.out.print(map1[i][j] + "");
//				}
//			}
			
			queueCakeLocation(scanner, map1);
			
			
			
			
			
			
			
		} catch(Exception e) {
			//System.out.println(e);
			System.out.println();
			coordinateBased(scanner);
			queueCakeLocation(scanner, map1Coords);
			
			
			
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
			System.out.println("exception catch fail");
		}
		
	}
	
	public static void queueCakeLocation(Scanner scan, String[][] map) {
		try {
			//solution for coord based
			//finding kirby and possible spaces for kirby
			
			
			long locationStart = System.currentTimeMillis();
			Queue<Integer> queue = new LinkedList<>();
			int cakeRow = 0;
			int cakeCol = 0;
			
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
				System.out.println(curRow + " " + curCol);
			
				map[curRow][curCol] = "-1";
				
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
				System.out.println("size: " + queue.size());
			}
			
			System.out.println();
			System.out.println("Cake Row: "  + cakeRow + " Cake Col: " + cakeCol);
			System.out.println("location runtime: " + (System.currentTimeMillis()-locationStart));
			
		} catch(Exception e) {
			System.out.println();
			System.out.println(e);
		}
	}
}

