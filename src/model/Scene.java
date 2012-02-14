package model;

import java.util.ArrayList;
import java.util.List;

public class Scene 
{
	
	private List <Page> pages;
	private int sceneNumber;
	
	public Scene ()
	{
		pages = new ArrayList <Page> ();
	}
	
	public void addPage (Page page)
	{
		pages.add(page);
	}
	
	public List <Page> getPages ()
	{
		return pages;
	}
	
	public void setSceneNumber (int sceneNumber)
	{
		this.sceneNumber = sceneNumber;
	}
	
	public int getSceneNumber()
	{
		return sceneNumber;
	}
	

}
