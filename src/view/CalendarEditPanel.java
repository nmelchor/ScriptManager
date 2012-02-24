package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import scratchpad.PicturePanel;
import scratchpad.PicturePanel;



public class CalendarEditPanel extends JPanel{
	
	private boolean testing = true;
	
	private JTabbedPane parent;
	private TopPanelListener topPanelListener;
	
	int prevSelection = 0;
	
	private JPanel calendar;
	private JPanel calendarChange;
	private JPanel daysEvents;
	private JPanel createEvent;
	private JPanel navigationPanel;
	private final JTextField searchBox;
	
	int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog d;
    JButton[] button = new JButton[49];

	public CalendarEditPanel (JTabbedPane parent)
	{
		super();
		this.parent = parent;
		topPanelListener = new TopPanelListener();
		calendar = new JPanel(new GridLayout(7, 7));
		calendarChange = new JPanel();
		daysEvents = new JPanel();
		createEvent = new JPanel();
		navigationPanel = new JPanel();
		searchBox = new JTextField();
		buildLayout ();
		
	}
	
	private void buildLayout() {
		
		setLayout (new BorderLayout());
		
		insertCalendar(calendar,calendarChange);
		
		JScrollPane scroller = new JScrollPane (daysEvents);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JSplitPane split = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, calendar, scroller);
        split.setResizeWeight(.3);
        
        createEvent(createEvent);
        
        JScrollPane scroller2 = new JScrollPane (createEvent);
        scroller2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        JSplitPane split2 = new JSplitPane (JSplitPane.VERTICAL_SPLIT, calendar, calendarChange);
        split2.setResizeWeight(.5);
        JSplitPane combo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split, split2);
        
        combo.setResizeWeight(.5);
        combo.setOneTouchExpandable(true);

        JSplitPane split3 = new JSplitPane (JSplitPane.VERTICAL_SPLIT, calendarChange, createEvent);
        JSplitPane combo2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split2, split3);
        
        combo2.setResizeWeight(.5);
        
        buildTopPanel();
        
        add(navigationPanel, BorderLayout.PAGE_START);
        add(combo, BorderLayout.CENTER);
		add(combo2, BorderLayout.EAST);
	}


	private void insertCalendar(JPanel calendar2,JPanel calendarChange) {
		 d = new JDialog();
         d.setModal(true);
        
        //calendar2.setPreferredSize(new Dimension(4300, 1200));
        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
       
        for (int x = 0; x < button.length; x++) {
                final int selection = x;
                button[x] = new JButton();
                button[x].setFocusPainted(false);
                button[x].setBackground(Color.white);
                if (x > 6)
                        button[x].addActionListener(new ActionListener() {
                        	 
                                public void actionPerformed(ActionEvent ae) {
                                        day = button[selection].getActionCommand();
                                        if(prevSelection != 0)
                                        	 if (((prevSelection+1)%7) == 1 || ((prevSelection+1)%7) == 0) //Week-end
                                             	button[prevSelection].setBackground(new Color(255, 220, 220));    
                                        else	
                                        	button[prevSelection].setBackground(new Color(255,255,255));                                      
                                        button[selection].setBackground(new Color(184,207,229));
                                        prevSelection = selection;
                                        d.dispose();                                      
                                }
                        });
                if (x < 7) {
                        button[x].setText(header[x]);
                        button[x].setForeground(Color.red);
                }	
                if (((x+1)%7) == 1 || ((x+1)%7) == 0){ //Week-end
                	button[x].setBackground(new Color(255, 220, 220));
                }
                calendar2.add(button[x]);
        }
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<< Previous");
        previous.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                        month--;
                        displayDate();
                }
        });
        p2.add(previous);
        p2.add(l);
        JButton next = new JButton("Next >>");
        next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                        month++;
                        displayDate();
                }
        });
        p2.add(next);
        calendarChange.add(p2);

        displayDate();  		
	}
	

    public void displayDate() {
            for (int x = 7; x < button.length; x++)
                    button[x].setText("");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                            "MMMM yyyy");
        
            java.util.Calendar cal = java.util.Calendar.getInstance();
             		
            cal.set(year, month, 1);
       
            int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
            int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
     
            for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++){
                    button[x].setText("" + day);
            }
            l.setText(sdf.format(cal.getTime()));       	
    }
    
    public String setPickedDate() {
        if (day.equals(""))
                return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
        
    }
    
    public void createEvent(JPanel createEvent2){
    	  JLabel label = new JLabel("Event Date");
          final JTextField date = new JTextField(10);
          JButton b = new JButton("Set Date");
          
          JLabel eName = new JLabel("Event Name");
          final JTextField name = new JTextField(20);
          
          JLabel eventDescription = new JLabel("Event Description");
          final JTextField eventDesc = new JTextField(30);	
          
          JLabel scenes = new JLabel("Scenes Involved");
          final JTextField scenesInvolved = new JTextField(20);
          
          JButton create = new JButton("create");
          
          createEvent2.add(b);
          createEvent2.add(label);
          createEvent2.add(date);
          createEvent2.add(eName);
          createEvent2.add(name);
          createEvent2.add(eventDescription);
          createEvent2.add(eventDesc);
          createEvent2.add(scenes);
          createEvent2.add(scenesInvolved);        
          createEvent2.add(create);
          
          create.addActionListener(new ActionListener(){
        	  public void actionPerformed(ActionEvent create){
        		  addDaysEvent(date,name,eventDesc,scenesInvolved);
        	  }
          });
          
          b.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent ae) {
                          date.setText(setPickedDate());
                  }
          });
          
    }

	protected void addDaysEvent(JTextField date, JTextField name,
			JTextField eventDesc, JTextField scenesInvolved) {
		// TODO Auto-generated method stub
		
	}

	private void buildTopPanel() {
		JButton button = new JButton();
		button = new JButton ("Edit Event");
		button.setActionCommand("edit");
		searchBox.addKeyListener(topPanelListener);
		navigationPanel.add(button);
		button = new JButton ("Remove Event");
		button.setActionCommand("remove");
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
	
}
