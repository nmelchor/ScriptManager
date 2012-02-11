package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import scratchpad.PicturePanel;
import controller.LoadScript;

public class ScriptEditPanel extends JPanel
{

	private JTabbedPane parent;
	private PicturePanel floorPlan;
	private ScriptPane scriptPane;
	private JPanel cuePanel;
	private JTextPane stageDirections;
	private JPanel navigationPanel;
	
	private String textToDisplay;
	
	public ScriptEditPanel (JTabbedPane parent)
	{
		super();
		System.out.println ("Loadin ScriptEditPanel");
		this.parent = parent;
		floorPlan = new PicturePanel();
		scriptPane = new ScriptPane();
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
		cuePanel.setLayout(null);
		
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
	
	
	private class ScriptPane extends JTextPane 
	{
		private PopupMenuListener ml;
		
		public ScriptPane ()
		{
			super();
			ml = new PopupMenuListener();
			addPopupMenu();
		}
		
		private void addPopupMenu ()
		{
			JMenuItem menuItem;
			JPopupMenu popup = new JPopupMenu();
	        menuItem = new JMenuItem("Tag word as prop");
	        menuItem.addActionListener(ml);
	        menuItem.setActionCommand("tag");
	        popup.add(menuItem);
	        menuItem = new JMenuItem("Add cue here");
	        menuItem.setActionCommand("cue");
	        menuItem.addActionListener(ml);
	        popup.add(menuItem);
	        menuItem = new JMenuItem("Insert blocking at this location");
	        menuItem.setActionCommand("blocking");
	        menuItem.addActionListener(ml);
	        popup.add(menuItem);
	        
	        MouseListener popupListener = new PopupListener(popup);
	        addMouseListener(popupListener);
	        
		}
		
		private class PopupMenuListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ac = arg0.getActionCommand();
				if (ac.equals("tag"))
				{
					
				}
				else if (ac.equals("cue"))
				{
			        Rectangle caretView = null;
			        try {
						caretView = modelToView(getCaret().getDot());
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			        //Get description for cue
			        String cueDescription = JOptionPane.showInputDialog (null, "Enter cue description",
			        		JOptionPane.YES_NO_CANCEL_OPTION);
			        JLabel label = new JLabel (cueDescription); //put it into label
			        label.setBorder(BorderFactory.createLineBorder(Color.black));
			        
			        //Put the label in the correct spot
			        Insets insets = cuePanel.getInsets();
			        Dimension size = label.getPreferredSize();
			        label.setBounds(25 + insets.left, (int) caretView.getY() + insets.top, size.width, size.height);
			        
			        cuePanel.add(label);
			        cuePanel.repaint();
			        //Remove text and replace it with blue version of itself
			        String content = getText();
			        int index = content.indexOf(getSelectedText(), 0);
			        String removedText = getSelectedText();
			        try {
			        	getDocument().remove(0, 100);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						System.out.println ("Pirates");
						e.printStackTrace();
					}
			        SimpleAttributeSet sa = new SimpleAttributeSet();
			        StyleConstants.setFontFamily(sa, "Monospace"); 
			        StyleConstants.setItalic(sa, true);
			        StyleConstants.setForeground(sa, Color.blue);
			        try {
						getDocument().insertString(index, removedText, sa);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (ac.equals("blocking"))
				{
					
				}
			}
			
		}
		
	    private class PopupListener extends MouseAdapter {
	        JPopupMenu popup;

	        PopupListener(JPopupMenu popupMenu) {
	            popup = popupMenu;
	        }

	        public void mousePressed(MouseEvent e) {
	            maybeShowPopup(e);
	        }

	        public void mouseReleased(MouseEvent e) {
	            maybeShowPopup(e);
	        }

	        private void maybeShowPopup(MouseEvent e) {
	            if (e.isPopupTrigger()) {
	                popup.show(e.getComponent(),
	                           e.getX(), e.getY());
	            }
	        }
	    }

	    public void displayPage (String text)
	    {
	    	
	    }

	}

	
	
	public void loadScript (String fileName) throws FileNotFoundException
	{
		setName(fileName);
		textToDisplay = LoadScript.loadScript(fileName);
		try {
			scriptPane.getDocument().insertString(0, textToDisplay, new SimpleAttributeSet());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
	}
	
	
}
