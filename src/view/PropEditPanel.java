package view;

import javax.swing.JPanel;

import model.Prop;

public class PropEditPanel extends JPanel 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4054986034496013358L;

	public PropEditPanel ()
	{
		super();
	}
	
	public PropEditPanel (Prop prop)
	{
		super();
		setName(prop.getWord().toString());
	}
	
	private void loadPropInfo()
	{
		//some code to load the info stored in the prop;
	}
	
	

}
