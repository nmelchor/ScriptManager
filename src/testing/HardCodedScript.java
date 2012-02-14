package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import view.MainFrame;

import model.Page;
import model.Scene;
import model.Word;

public class HardCodedScript 
{
	
	public static void main (String [] args)
	{
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner (new File 
					("C:\\Users\\Amit\\Programming_Files\\Java\\ScriptManager\\LoremIpsum"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList <Page> pages = new ArrayList <Page> ();
		Scene scene = new Scene ();
		for (int i = 0; i < 9; i++)
		{
			pages.add(new Page());
			pages.get(i).setPageNumber(i);
			for (int j = 0; j < 20; j++)
			{
				System.out.println (i + " and " + j);
				pages.get(i).addWord(new Word(fileScanner.next()));
			}
			scene.addPage(pages.get(i));
		}
		MainFrame f = new MainFrame();
		f.loadPage(pages.get(0));
		f.setVisible(true);
		
	}

}
