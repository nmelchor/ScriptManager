package model;

public class Prop 
{

	private Word word;
	
	public Prop (Word word)
	{
		this.word = word;
	}
	
	public Prop ()
	{
		
	}
	
	public void setWord (Word word)
	{
		this.word = word;
	}
	
	public Word getWord ()
	{
		return word;
	}
	
}
