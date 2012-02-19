package testing;

import model.Page;
import model.Script;
import model.Word;

public class HardCodedScript 
{
	
	private static final String [] words = {"apple", "ics", "teacher", "lakers", "theater", "food", "starcraft", 
			"oranges", "kobe", "discrete", "netflix", "enter", "say", "bye", "thunder", "proclaimed",
	};
	
	public static Script getScript ()
	{
		Script s1 = new Script ("Test Production");
		Page page = new Page ();
		for (int i = 1; i <= 100; i++)
		{
			page = new Page ();
			page.setPageNumber(i);
			for (int j = 0; j < 500; j++)
			{
				page.addWord(new Word(words
						[(int)((Math.random() * words.length))]));
				page.addWord (new Word(" "));
				if (j%50 == 0)
				{
					page.addWord(new Word("\n"));
				}
			}
			s1.addPage(page);
		}
		
		return s1;
	}

}
