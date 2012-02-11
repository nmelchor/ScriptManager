import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;



public class FrameStuff extends JFrame implements ActionListener,  CaretListener, DocumentListener{

	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 600;
	private final String FRAME_NAME = "Practice";
	private final boolean CENTER_FRAME = true;
	private final int MAX_CHARACTERS = 3000;
	
	private JTextPane scriptPane;
	private JTextPane cuePane;
	
	private JScrollPane scriptScroller;
	private JScrollPane cueScroller;
	
	private JPanel cuePanel;
	private JPanel lineNumberPanel;
	private PicturePanel picturePanel;
	private JTextPane pictureDescriptions;
	
	private AbstractDocument scriptDoc;
	private AbstractDocument cueDoc;
	
	private MyMouseListener m1;
	
	private Highlighter hilit;
	private Highlighter.HighlightPainter painter;
	
	public FrameStuff ()
	{
		super();
		initFrame();
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout (contentPane, BoxLayout.PAGE_AXIS));
		
		//Init the menu bars
        JMenuBar menuBar = new JMenuBar ();
        menuBar.add(createFileMenu());
        menuBar.add(createStyleMenu());
        setJMenuBar (menuBar);
        
        //Build TextPanes
        scriptPane = new JTextPane();
        scriptPane.setCaretPosition(0);
        scriptPane.getDocument().addDocumentListener(this);
        scriptPane.setMargin (new Insets (5,5,5,5));
        scriptPane.setBorder(BorderFactory.createTitledBorder("Script"));
        if (scriptPane.getStyledDocument() instanceof AbstractDocument)
        {
        	scriptDoc = (AbstractDocument) scriptPane.getStyledDocument();
        	scriptDoc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
        }
        else
        {
        	System.err.println ("ScriptPane did not have an abstractDoc");
        }  
        //
       
        cuePane = new JTextPane();
        cuePane.setCaretPosition(0);
        cuePane.setMargin (new Insets (5,5,5,5));
        cuePane.setBorder(BorderFactory.createTitledBorder("Cues"));
        if (cuePane.getStyledDocument() instanceof AbstractDocument)
        {
        	cueDoc = (AbstractDocument) cuePane.getStyledDocument();
        	cueDoc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
        }
        else
        {
        	System.err.println ("CuePane did not have an abstractDoc");
        }
        
        //Right justify the text of cue scroller
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_RIGHT);  
        cuePane.setParagraphAttributes(attribs,true);
        //Init the scroll panes
        scriptScroller = new JScrollPane (scriptPane);
        scriptScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        cueScroller = new JScrollPane (cuePane);
        cueScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        addPopupMenu();
        
        cuePanel = new JPanel();
        cuePanel.setLayout(null);
        
        picturePanel = new PicturePanel ();
        m1 = new MyMouseListener (picturePanel);
        picturePanel.addMouseListener(m1);
        picturePanel.addMouseMotionListener(m1);

        pictureDescriptions = new JTextPane ();
        JSplitPane splitPane2 = new JSplitPane (JSplitPane.VERTICAL_SPLIT, picturePanel, pictureDescriptions);
        splitPane2.setResizeWeight(.5);
        splitPane2.setOneTouchExpandable(true);
        
        JSplitPane splitPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, cuePanel, scriptScroller);
        splitPane.setResizeWeight(0.3);
        
        JSplitPane pan2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, splitPane2);
        pan2.setResizeWeight(.5);
        pan2.setOneTouchExpandable(true);
        
        contentPane.add(pan2);
        
        
        scriptPane.setText(readTextFromFile());
        
        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        scriptPane.setHighlighter(hilit);
        cuePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        scriptPane.setBorder(BorderFactory.createLineBorder(Color.black));
        picturePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        pictureDescriptions.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	private void initFrame ()
	{
		setSize (new Dimension (FRAME_WIDTH, FRAME_HEIGHT));
		if (CENTER_FRAME)
			setLocationRelativeTo(null);
		setTitle(FRAME_NAME);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
	}

	private JMenu createFileMenu ()
	{
		JMenu fileMenu = new JMenu ("File");
		JMenuItem newFile = new JMenuItem ("New Script");
		JMenuItem saveFile = new JMenuItem ("Save Script");
		JMenuItem loadFile = new JMenuItem ("Load Script");
		JSeparator seperator = new JSeparator ();
		JMenuItem exit = new JMenuItem ("Exit");
		
		fileMenu.add(newFile);
		fileMenu.add(saveFile);
		fileMenu.add(loadFile);
		fileMenu.add(seperator);
		fileMenu.add(exit);
		
		return fileMenu;
		
	}
	
	private JMenu createStyleMenu() {
        JMenu menu = new JMenu("Style");

        Action action = new StyledEditorKit.BoldAction();
        action.putValue(Action.NAME, "Bold");
        menu.add(action);

        action = new StyledEditorKit.ItalicAction();
        action.putValue(Action.NAME, "Italic");
        menu.add(action);

        action = new StyledEditorKit.UnderlineAction();
        action.putValue(Action.NAME, "Underline");
        menu.add(action);

        menu.addSeparator();

        menu.add(new StyledEditorKit.FontSizeAction("12", 12));
        menu.add(new StyledEditorKit.FontSizeAction("14", 14));
        menu.add(new StyledEditorKit.FontSizeAction("18", 18));

        menu.addSeparator();

        menu.add(new StyledEditorKit.FontFamilyAction("Serif",
                                                      "Serif"));
        menu.add(new StyledEditorKit.FontFamilyAction("SansSerif",
                                                      "SansSerif"));

        menu.addSeparator();

        menu.add(new StyledEditorKit.ForegroundAction("Red",
                                                      Color.red));
        menu.add(new StyledEditorKit.ForegroundAction("Green",
                                                      Color.green));
        menu.add(new StyledEditorKit.ForegroundAction("Blue",
                                                      Color.blue));
        menu.add(new StyledEditorKit.ForegroundAction("Black",
                                                      Color.black));

        return menu;
    }

	
	private void addPopupMenu ()
	{
		JMenuItem menuItem;
		
		JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem("Tag item");
        menuItem.addActionListener(this);
        popup.add(menuItem);
        menuItem = new JMenuItem("Add Cue Here");
        menuItem.setActionCommand("tag");
        menuItem.addActionListener(this);
        popup.add(menuItem);

        //Add listener to the text area so the popup menu can come up.
        MouseListener popupListener = new PopupListener(popup);
        scriptPane.addMouseListener(popupListener);
	}

	private String readTextFromFile ()
	{
		StringBuffer fileText = new StringBuffer ();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("/Users/ashwinamit/Dropbox/CS Projects/TextEditor/ceaser"));
		} catch (FileNotFoundException e) {
			System.out.println ("error");
			e.printStackTrace();
		}
		int i = 0;
		while (i++ < 25)
		{
			fileText.append(scanner.nextLine());
			fileText.append(System.getProperty("line.separator"));
		}
		return fileText.toString();
//		return "apple";
	}
	
	@Override
	public void paint(Graphics g)	
	{
		super.paint(g);

		// We need to properly convert the points to match the viewport
		// Read docs for viewport
		
	}
	
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("tag"))
    	{
    		System.out.println ("found");
    		JOptionPane.showInputDialog (null, "Enter info");
    	}
    	else
    	{
        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println (source.getText() + " at " + scriptPane.getSelectionStart());
//        scriptPane.getSelectedText().
        Rectangle caretView = null;
        try {
			caretView = scriptPane.modelToView(scriptPane.getCaret().getDot());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String tagName = JOptionPane.showInputDialog(null, "Enter string", JOptionPane.YES_NO_CANCEL_OPTION);
        JLabel label = new JLabel (tagName);
        cuePanel.add(label);
        Insets insets = cuePanel.getInsets();
        Dimension size = label.getPreferredSize();
        label.setBounds(25 + insets.left, (int) caretView.getY() + insets.top, size.width, size.height);
        System.out.println (label.getPreferredSize());
        System.out.println (caretView.getY());
        String content = scriptPane.getText();
        int index = content.indexOf(scriptPane.getSelectedText(), 0);
        try {
			hilit.addHighlight(index, index + scriptPane.getSelectedText().length(), painter);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	}
    }
	
	public static void main (String [] args)
	{
		FrameStuff fs = new FrameStuff ();
		fs.setExtendedState(JFrame.MAXIMIZED_BOTH);  
		fs.setVisible(true);
	}
	
    class PopupListener extends MouseAdapter {
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

	@Override
	public void caretUpdate(CaretEvent arg0) {
				
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class MyMouseListener implements MouseInputListener{

		private PicturePanel myPanel;
		private Point startClick;
		
		
		public MyMouseListener (PicturePanel panel)
		{
			myPanel = panel;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			startClick = arg0.getPoint();
			myPanel.addX(arg0.getPoint());
			myPanel.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			myPanel.repaint();
			myPanel.addX(arg0.getPoint());
//			myPanel.addText(JOptionPane.showMessageDialog(null, "Enter tag"));
			try {
				pictureDescriptions.getDocument().insertString(0, "\n" + JOptionPane.showInputDialog(null, 
						"Enter Description"), null);
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startClick = null;
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			if (startClick != null)
			{
				myPanel.addPoints(startClick,  arg0.getPoint());
				myPanel.repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			if (startClick != null)
			{
				myPanel.addPoints(startClick,  arg0.getPoint());
				myPanel.repaint();
			}
		}

	}


}
