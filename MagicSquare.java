import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Andre Murphy
 * @class CS221-002 MThomas
 * @project Project01 Magic Square
 */
public class MagicSquare {
	private static int dimension;
	private static int[][] size;
	private static String fileName2 = "";
	private static File file;
	private static int row;
	private static int col;
	private static int oldrow;
	private static int oldcol;
	private static ArrayList<Integer> duplicate;

	/**
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 * Goes through rows, columns and diagonals to check if the sum of each integer has the same sum
	 */
	public static boolean checkMatrix(String filename) throws FileNotFoundException {

		readMatrix(filename); //runs read matrix to parse file
		int num = dimension * ((dimension * dimension) + 1) / 2; //magic #

		int rowSum = 0;
		int colSum = 0;
		int downDiag = 0;
		int upDiag = 0;
		boolean matrixRow = true;
		boolean matrixCol = true;
		boolean upMatrix = true;
		boolean downMatrix = true;
		boolean trueMatrix = true;
		boolean truth = false;

		for (int a = 0; a < dimension && trueMatrix; a++) { //iterates through rows
			rowSum = 0; //resets counter
			colSum = 0; //resets counter
			for (int b = 0; b < dimension && trueMatrix; b++) { //iterates through columns

				rowSum += size[a][b]; //adds up sum of rows
				colSum += size[b][a]; //adds up sum of cols


			}
			matrixRow = (rowSum == num); //sets boolean equal to T or F if row sum = magicnum
			matrixCol = (colSum == num); //sets boolean equal to T or F if col sum = magicnum

			downDiag += size[a][a]; //checks down diagonal
			upDiag += size[dimension - a - 1][a]; //checks up diagonal


			trueMatrix = (matrixRow && matrixCol); //checks to see if row and cols are equal

			if (trueMatrix) { //checks to see if rows and cols are true

				truth = true; //sets return value
			} else {
				truth = false; //sets return value
			}
		}
		upMatrix = (num == upDiag); //checks diagonals outside of loops
		downMatrix = (num == downDiag); //checks diagonals outside of loops
		trueMatrix = (matrixRow && matrixCol && upMatrix && downMatrix); //checks to see if all rows, cols and diags are equal
		if (trueMatrix) { //if all true, set return value

			truth = true;
		} else {
			truth = false; //if any false, set return value
		}
		if (truth == true) { //print square if true
			System.out.println(toString(size));
			System.out.println("is a magic square.");
		}
		if (truth == false) { //print square if false
			System.out.println(toString(size));
			System.out.println("is NOT a magic square.");
		}
		return truth; //returns value
	}

	/**
	 * @param name //passed in from driver
	 * @param x //passed in from driver
	 * @throws IOException
	 * Creates a magic square with passed in name and size
	 */
	public static void createMagicSquare(String name, int x) throws IOException {

		dimension = x;
		size = new int[dimension][dimension];
		row = x - 1;
		col = x / 2;
		oldrow = 0;
		oldcol = 0;
		fileName2 = name;

		for (int i = 1; i < ((dimension * dimension)+1); i++) {
			size[row][col] = i;
			oldrow = row;
			oldcol = col;
			row++;
			col++;

			if (row == dimension) {
				row = 0;
			}
			if (col == dimension) {
				col = 0;
			}
			if (size[row][col] > 0) {
				row = oldrow;
				col = oldcol;
				row--;
			}
		}

		writeMatrix(fileName2); //writes created matrix to passed in filename

	}

	/**
	 * @param filename
	 * reads through a file and parses it so its able to print in checkMatrix();
	 */
	public static void readMatrix(String filename) {

		try {
			Scanner scanMatrix = new Scanner(new File(filename)); //creates a file scanner
			int rowcount = 0; //creates row counter
			int colcount = 0; //creats col counter
			duplicate = new ArrayList<Integer>(); //creates array to check for duplicate nums
			String matrixSize = scanMatrix.nextLine(); //sets string to grab matrix size
			dimension = Integer.parseInt(matrixSize); //sets dimension
			size = new int[dimension][dimension]; //creates matrix with grabbed size

			while (scanMatrix.hasNextLine()) { // iterates through .txt file

				if (rowcount > dimension) { //if row count is too large, not square
					System.out.println("Row count not right");
				}
				String curLine = scanMatrix.nextLine(); //scans next row
				//				lineScan = scanMatrix.useDelimiter("\\s");
				Scanner tokenScan = new Scanner(curLine); //scanner for cols
				while (tokenScan.hasNext()) {// if column, read
					String curToken = tokenScan.next().trim(); //gets rid of whitespace
					if (colcount > dimension) { //if col count is too large, not square
						System.out.println("Column count not right");
					}

					int content = Integer.parseInt(curToken); //parse to integer
					size[rowcount][colcount] = content; //set static variable
					duplicate.add(content); //adds content to array
					colcount++; //counts through cols
				}
				duplicate.sort(null);
				colcount = 0; //resets col count
				if (!curLine.isEmpty()) { //if no line in col, go up row
					rowcount++;
				}
				tokenScan.close(); 
			}
			scanMatrix.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param arra
	 * @throws IOException
	 * write passed in matrix to a file
	 */
	public static void writeMatrix(String arra) throws IOException {

		file = new File(arra); //sets static variable filename
		PrintWriter outFile = new PrintWriter(new FileWriter(file)); //makes print writer for file
		outFile.println(dimension); //prints dimension at top of file, above matrix
		for (int i = 0; i < size.length; i++) { //iterate through row
			for (int j = 0; j < size[i].length; j++) { //iterate through col
				outFile.print(size[i][j] + " "); //add each variable with a space
			}
			outFile.println("	" + "\n"); //prints in new line
		}
		outFile.close();
	}

	/**
	 * @param printArray
	 * @return
	 * a toString to use in checkMatrix when printing the matrix to the console
	 */
	public static String toString(int[][] printArray) { 
		String retString = ""; //create empty string
		retString += "The Matrix:" + "\n\n"; //first line statement
		for (int i = 0; i < printArray.length; i++) { //iterate through row
			for (int j = 0; j < printArray[i].length; j++) { //iterate through col
				retString += printArray[i][j] + " "; //concatenates matrix to first line statement, adds to string
			}
			retString += "\n"; //tabs for each row
		}
		return retString; //returns created string
	}
}