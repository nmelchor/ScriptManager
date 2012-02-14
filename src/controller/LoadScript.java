package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadScript 
{
	
	public static String loadScript (String fileName) throws FileNotFoundException
	{
		Scanner scanner = new Scanner (new File (fileName));
		StringBuffer output = new StringBuffer();
		while (scanner.hasNextLine())
		{
			output.append(scanner.nextLine() + "\n");
		}
		System.out.println("Hey Ashwin");
		return output.toString();
	}

}
