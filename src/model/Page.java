package model;

import java.util.List;
import java.util.ArrayList;

public class Page
{
	
	private List <Word> words;
	private Floorplan floorplan;
	private int pageNumber;
	
	public Page (Floorplan floorplan, int pageNumber)
	{
		words = new ArrayList <Word>();
		this.floorplan = floorplan;
		this.pageNumber = pageNumber;
	}
	
	public Page ()
	{
		words = new ArrayList <Word> ();
	}
	
	public void addWord (Word word)
	{
		words.add(word);
	}
	
	public void setFloorplan (Floorplan floorplan)
	{
		this.floorplan = floorplan;
	}
	
	public void setPageNumber (int pageNumber)
	{
		this.pageNumber = pageNumber;
	}
	
	public List <Word> getWords ()
	{
		return words;
	}

}
