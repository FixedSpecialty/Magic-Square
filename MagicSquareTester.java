
import java.io.IOException;
import java.util.Scanner;

public class MagicSquareTester {
	private static String fileType;
	private static int size;

	/**
	 * @param args
	 * @throws Exception
	 * runs with command line input and uses Magic Square methods
	 */
	public static void main(String[] args) throws Exception {


		if(args[0].equals("-check")) { //if first args check, run
			fileType = args[1]; //sets String to input
			MagicSquare.checkMatrix(fileType); //pass in String
		}

		else if(args[0].equals("-create")) { //if first args create, run
			if(args[2] != null)	{
				size = Integer.parseInt(args[2]);//set size given 3rd args
			}
			if(size%2 != 0) { //if odd,...
				MagicSquare.createMagicSquare(args[1],size); //pass in things to createSquare
				MagicSquare.writeMatrix(args[1]); //pass in things to write file
			}
			else {
				System.out.println("That is an invalid size. Enter an odd number."); //throw error
			}

		}
		else System.out.println("Invalid Input!"); //throw error
	}

}
