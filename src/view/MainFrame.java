package view;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import model.Page;

public class MainFrame extends JFrame 
{

	public static void main (String [] args)
	{
		MainFrame f = new MainFrame();
		f.setVisible(true);
	}

	private final String name = "Script Manager";
	private final Dimension frameSize = new Dimension (800, 600);
	
	private JTabbedPane tabs = new JTabbedPane();
	private ActionListener al;
	
	public MainFrame ()
	{
		
		tabs = new JTabbedPane();
		al = new FileMenuActionListener();
		
		initFrame();
		buildFileMenuBar();
		
		getContentPane().add(tabs);
	}
	
	private void initFrame()
	{
		setSize (frameSize);
		setName (name);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);  //set it to be fullscreen on open

	}
	
	private void buildFileMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu ("File");
		JMenuItem menuItem = new JMenuItem ("New Project");
		menuItem.setActionCommand("import");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem = new JMenuItem ("Save");
		menuItem.setActionCommand("save");
		menuItem.addActionListener(al);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		menuItem = new JMenuItem ("Save As");
		menuItem.setActionCommand("saveas");
		menuItem.addActionListener(al);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		menuItem = new JMenuItem ("Load");
		menuItem.setActionCommand("load");
		menuItem.addActionListener(al);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem ("Exit");
		menuItem.setActionCommand("exit");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		JMenu system = new JMenu ("System");
		JMenuItem systemItem = new JMenuItem ("Calendar");
		systemItem.setActionCommand("calendar");
		systemItem.addActionListener(al);
		system.add(systemItem);
		systemItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		menuBar.add(menu);
		menuBar.add(system);
		setJMenuBar(menuBar);
	}
	
	private class FileMenuActionListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent e)
		{
			String ac = e.getActionCommand();
			
			if (ac.equals("import"))
			{
				
			}
			else if (ac.equals("save"))
			{
				
			}
			else if (ac.equals("saveas"))
			{
				
			}
			else if (ac.equals("load"))
			{
//				load();
			}
			else if (ac.equals("load"))
			{
				
			}
			else if(ac.equals("calendar"))
			{
				openCalendar();
			}
		}
	}
	
//	private void load ()
//	{
//		ScriptEditPanel panel = new ScriptEditPanel (tabs);
//		try
//		{
//			FileDialog fd = new FileDialog(this, "Load File");
//			fd.setVisible(true);
//			panel.loadScript(fd.getFile());
//		}
//		catch (FileNotFoundException e)
//		{
//			JOptionPane.showMessageDialog(null, "Unable to load file");	
//		}
//		tabs.add(panel);
//	}
	
	public void loadPage (Page page)
	{
		ScriptEditPanel panel = new ScriptEditPanel(tabs);
		panel.displayPage(page);
		tabs.add(panel);
	}

	public void openCalendar() {
		CalendarEditPanel panel = new CalendarEditPanel(tabs);
		tabs.add(panel);
	}	
	
}
