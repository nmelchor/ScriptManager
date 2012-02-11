package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import scratchpad.PicturePanel;

public class ScriptEditPanel extends JPanel
{

	private JTabbedPane parent;
	private PicturePanel floorPlan;
	private ScriptPane scriptPane;
	private JPanel cuePanel;
	private JTextPane stageDirections;
	private JPanel navigationPanel;
	
	public ScriptEditPanel (JTabbedPane parent)
	{
		super();
		System.out.println ("Loadin ScriptEditPanel");
		this.parent = parent;
		floorPlan = new PicturePanel();
		scriptPane = new ScriptPane (parent);
		cuePanel = new JPanel ();
		stageDirections = new JTextPane ();
		navigationPanel = new JPanel ();
		buildLayout ();
		
	}
	
	
	private void buildLayout ()
	{
		//overall layout is
		//cuepane-scriptpane-picturepanel
		//cuepane-scriptpane-stageDirections
		//navigationpanel
		
		setLayout (new BoxLayout (this, BoxLayout.X_AXIS));
		
		
		JScrollPane scroller = new JScrollPane (scriptPane);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JSplitPane split = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, cuePanel, scroller);
        split.setResizeWeight(.3);
        split.setOneTouchExpandable(true);
        
        JScrollPane scroller2 = new JScrollPane (stageDirections);
        scroller2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JSplitPane split2 = new JSplitPane (JSplitPane.VERTICAL_SPLIT, floorPlan, stageDirections);
        split2.setResizeWeight(.5);
        split2.setOneTouchExpandable(true);
        
        JSplitPane combo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split, split2);
        combo.setResizeWeight(.5);
        combo.setOneTouchExpandable(true);
        
        combo.setPreferredSize(
        		new Dimension ((int) getSize().getWidth(), (int)(getSize().getHeight()*.98)));
        navigationPanel.setPreferredSize(
        		new Dimension ((int) getSize().getWidth(), (int)(getSize().getHeight()*.01)));
        navigationPanel.setPreferredSize(
        		new Dimension ((int) getSize().getWidth(), (int)(50)));
        
        add(combo);
        add(Box.createVerticalGlue());
        add(navigationPanel);
	}
	
	public void loadScript (String fileName) throws FileNotFoundException
	{
		Scanner scanner = new Scanner (new File(fileName));
		setName(fileName);
		repaint();
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
	}
	
}
