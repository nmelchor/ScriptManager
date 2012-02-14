package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import model.Page;
import model.Prop;
import model.Word;
import scratchpad.PicturePanel;

public class ScriptEditPanel extends JPanel
{
	
	private boolean testing = true;

	private TopPanelListener topPanelListener;
	
	private JTabbedPane parent;
	private PicturePanel floorPlan;
	private ScriptPane scriptPane;
	private JPanel cuePanel;
	private JTextPane stageDirections;
	private JPanel navigationPanel;
	private final JTextField searchBox;
	
	private final String nextPageString = "Next Page";
	private final String prevPageString = "Previous Page";
	private final String searchBoxString = "Search";
	private final String addNoteString = "Add Note";
	private final String addCueString = "Add Cue";
	private final String addBlockingString = "Add Blocking";
	private final String addTagString = "Add Tag";
	
	
	private String textToDisplay;
	private Script script;
	
	public ScriptEditPanel (JTabbedPane parent)
	{
		super();
		this.parent = parent;
		topPanelListener = new TopPanelListener();
		floorPlan = new PicturePanel();
		scriptPane = new ScriptPane();
		cuePanel = new JPanel ();
		stageDirections = new JTextPane ();
		navigationPanel = new JPanel ();
		searchBox = new JTextField();
		buildLayout ();
		
	}
	
	private void buildLayout ()
	{
		//overall layout is
		//cuepane-scriptpane-picturepanel
		//cuepane-scriptpane-stageDirections
		//navigationpanel
		
		setLayout (new BorderLayout());
		
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
        
//        combo.setPreferredSize(
//        		new Dimension ((int) getSize().getWidth(), (int)(getSize().getHeight()*.5)));
//        navigationPanel.setPreferredSize(
//        		new Dimension ((int) getSize().getWidth(), (int)(getSize().getHeight()*.5)));
        

        buildTopPanel();
        
        add(navigationPanel, BorderLayout.PAGE_START);
        add(combo, BorderLayout.CENTER);
        
	}
	
	private void buildTopPanel ()
	{
		JButton button = new JButton();
		button = new JButton (addTagString);
		button.setActionCommand("tag");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(button);
		button = new JButton (addBlockingString);
		button.setActionCommand("blocking");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(button);
		button = new JButton (addCueString);
		button.setActionCommand("cue");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(button);
		button = new JButton (addNoteString);
		button.setActionCommand("note");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(button);
		searchBox.setText(searchBoxString);
		searchBox.setActionCommand("search");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(searchBox);
		searchBox.setAlignmentX(1);
		button = new JButton (nextPageString);
		button.setActionCommand("next");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(button);
		button = new JButton (prevPageString);
		button.setActionCommand("prev");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(button);
	
		if (testing)
		{
			navigationPanel.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked (MouseEvent e)
				{
					System.out.println (navigationPanel.getSize());
				}
			});
		}
		else
		{
			throw new NullPointerException ();
		}
		
	}
	
	private class ScriptPane extends JTextPane 
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1043205916846444609L;
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
					PropEditPanel pe = new PropEditPanel (new Prop(new Word("test")));
					parent.add(pe);
					parent.setSelectedComponent(pe);
				}
				else if (ac.equals("cue"))
				{
			        Rectangle caretView = null;
			        try {
						caretView = modelToView(getCaret().getDot());
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			        
			        int start = getSelectionStart();
			        int end = getSelectionEnd();
			        String text = "";
			        
					try {
						text = getText(start, end);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
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
//			        String content = getText();
//			        try {
//			        	getDocument().remove(start, end);
//					} catch (BadLocationException e) {
//						// TODO Auto-generated catch block
//						System.out.println ("Pirates");
//						e.printStackTrace();
//					}
//			        SimpleAttributeSet sa = new SimpleAttributeSet();
//			        StyleConstants.setFontFamily(sa, "Monospace"); 
//			        StyleConstants.setItalic(sa, true);
//			        StyleConstants.setForeground(sa, Color.blue);
//			        try {
//						getDocument().insertString(start, text, sa);
//					} catch (BadLocationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
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


	}

	private class TopPanelListener implements ActionListener, KeyListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
//	public void loadScript (String fileName) throws FileNotFoundException
//	{
//		setName(fileName);
//		textToDisplay = LoadScript.loadScript(fileName);
//		try {
//			scriptPane.getDocument().insertString(0, textToDisplay, new SimpleAttributeSet());
//		} catch (BadLocationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void displayPage (Page page)
	{
		StringBuffer pageContents = new StringBuffer();
		for (Word word: page.getWords())
		{
			pageContents.append(word.toString());
		}
		try{
			scriptPane.getDocument().insertString(0, pageContents.toString(), new SimpleAttributeSet());
		}
		catch (BadLocationException e){
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
	}
	
	
}
