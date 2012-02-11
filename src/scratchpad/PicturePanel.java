package scratchpad;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.swing.JPanel;


public class PicturePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap <Point, Queue<Point>> lines;
	private Set <Point> xPoints;
	
	public PicturePanel ()
	{
		super();
		this.setOpaque(true);
		lines = new HashMap <Point, Queue<Point>> ();
		xPoints = new HashSet <Point> ();
	}
	
	public void addPoints (Point p1, Point p2)
	{
		if (lines.get(p1) == null)
		{
			lines.put(p1, new LinkedList<Point> ());
		}
		lines.get(p1).add(p2);
	}
	
	public void addX (Point p1)
	{
		xPoints.add(p1);
	}
	
    @Override
    public void paintComponent(Graphics graphic2d) {
    	super.paintComponent(graphic2d);
    	Graphics2D g2 = (Graphics2D) graphic2d;
    	if (!lines.isEmpty())
        {
        	for (Point point: lines.keySet())
        	{
        		Iterator <Point> it = lines.get(point).iterator();
        		Point prev = it.next();
        		Point current = null;
        		while (it.hasNext())
        		{
        			System.out.println ("IT HAS");
        			current = it.next();
        			g2.drawLine(prev.x, prev.y, current.x, current.y);
        			prev = current;
        		}
        	}
        }
    	if (!xPoints.isEmpty())
    	{
    		Iterator <Point> it = xPoints.iterator();
    		while (it.hasNext())
    		{
    			Point p = it.next();
    			g2.fillOval(p.x, p.y, 5, 5);
    		}
    	}
    }	

	
	
}
