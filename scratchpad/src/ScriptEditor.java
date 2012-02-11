import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;


public class ScriptEditor extends JDesktopPane
{
	
	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 600;
	private final String FRAME_NAME = "Stage Manager";
	private final String MENU_1_NAME = "File";
	private final String MENU_2_NAME = "Data Manager";
	private final String MENU_3_NAME = "Scheduler";
	
	//Components for script management view
	private JFrame frame;
	private JTextPane scriptPane;
	private JPanel cuePanel;
	
	//Components for calender side
	private JTextPane calenderViewPane;
	
	public ScriptEditor ()
	{
		super();
		frame = new JFrame();
		frame.setVisible(true);
		frame.setContentPane(this);
		initFrame();
		buildMenuBar ();
	}

	private void initFrame ()
	{
		setSize (new Dimension (FRAME_WIDTH, FRAME_HEIGHT));
		setName (FRAME_NAME);
	}
	
	private void initCuePanel ()
	{
	}
		
	
	private void initScriptTextArea()
	{
		
	}
	
	private void buildMenuBar ()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu (MENU_1_NAME);
		JMenuItem item = new JMenuItem ("Import Script (Ctrl+i)");
		menu1.add(item);
		menuBar.add(menu1);
		frame.add (menuBar);
	}
	
	public static void main (String [] args)
	{
		System.out.println ("Run");
		ScriptEditor se = new ScriptEditor ();
		se.setVisible(true);
		System.out.println ("Should be visible");
	}

	
	
	

}
