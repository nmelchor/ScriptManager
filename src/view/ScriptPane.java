package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;


public class ScriptPane extends JTextPane 
{
	private JTabbedPane parent;
	private PopupMenuListener ml;
	
	public ScriptPane (JTabbedPane parent)
	{
		super();
		this.parent = parent;
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
