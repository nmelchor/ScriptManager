package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Script 
{
	
	private List <Page> pages;
	private List <Scene> scenes;
	private String scriptName;
	
	public Script (String scriptName)
	{
		this.scriptName = scriptName;
		pages = new ArrayList <Page>();
		scenes = new ArrayList <Scene> ();
	}
	
	public void addPage (Page page)
	{
		pages.add(page);
	}
	
	public List <Page> getPages ()
	{
		return pages;
	}
	
	public Iterator<Page> getPagesIterator ()
	{
		return pages.iterator();
	}
	
	public String getName ()
	{
		return scriptName;
	}

}
