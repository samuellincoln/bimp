package mainpkg;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.*;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Vector;

public class PixelCoordEnv extends ApplicationFrame {
	private final int MAXSIZE = 2000;
	private enum ColorValue {
		RED
		, GREEN
		, BLUE
	}
	private ArrayList <ArrayList <Coordinate>> mappixelindexcoord;
	private ArrayList <Color> mappixelindexcolor;
	private ArrayList <Coordinate> [] mappixelindexcoordtoarray;
	private Color [] mappixelindexcolortoarray;
	//private HashMap <Integer, HashSet <Integer>> mappixelindexcoordindex;
	private BufferedImage bi;
	private JFreeChart wavefromim;
	/*private HistogramDataset createDataSetFromMaps (HashMap <Integer, HashSet <Integer>> map) {
		HistogramDataset h = new HistogramDataset ();
		h.
	}*/
	public PixelCoordEnv (BufferedImage bi) {
		this (bi, false, false);
	}
	public PixelCoordEnv (BufferedImage bi, boolean mount, boolean showgraphics) {
		super ("");
		this.bi = bi;
		this.mappixelindexcoord = new ArrayList <ArrayList <Coordinate>> ();
		this.mappixelindexcolor = new ArrayList <Color> ();
		/*if (showgraphics) {
			this.generateGraphics (bi);
		}*/
		if (mount) {
			this.mountPixelCoordEnv (bi);
		}
		else {
			this.bi = bi;
		}
		String str = this.toString();
		System.out.print ("");
		//this.mountWaveGraphics (this.mappixelindexcoordindex);
	}
	public BufferedImage getBufferedImage () {
		return this.bi;
	}
	public JFreeChart getWaveGraphic () {
		return this.wavefromim;
	}
	public boolean containsKeyOnIntegerCoord (Integer pindex) {
		return this.mappixelindexcoord.contains (pindex);
	}
	public boolean containsKeyOnIntegerColor (Integer pindex) {
		return this.mappixelindexcolor.contains (pindex);
	}
	private int getAbsoluteValueFromCoordinate (int x, int y, int w, int h) {
		return (x /*+ 1*/)*w + y;
	}
	private int getAbsoluteValueFromCoordinate (int x, int y, BufferedImage bi) {
		return getAbsoluteValueFromCoordinate (x, y, bi.getWidth(), bi.getHeight());
	}
	private int getAbsoluteValueFromCoordinate (Coordinate coord, int w, int h) {
		return getAbsoluteValueFromCoordinate (coord.getX(), coord.getY(), w, h);
	}
	private int getAbsoluteValueFromCoordinate (Coordinate coord, BufferedImage bi) {
		return getAbsoluteValueFromCoordinate (coord.getX(), coord.getY(), bi.getWidth(), bi.getHeight());
	}
	private HashSet <Integer> getIndexesForCoordinates (ArrayList <Coordinate> coords, BufferedImage bi) {
		int size = coords.size();
		HashSet <Integer> set = new HashSet <Integer> ();
		for (int i = 0; i < size; i++) {
			set.add (getAbsoluteValueFromCoordinate ((Coordinate)coords.toArray() [i], bi));
		}
		return set;
	}
	/*private int pixelIndex (Color c) {
		return pixelIndex (c.getRed(), c.getGreen(), c.getBlue());
	}*/
	/*private boolean coordsetContainsCoord (int x, int y, ArrayList <Coordinate> coords) {
		int size = coords.size();
		for (int i = 0; i < size; i++) {
			if (((Coordinate)coords.get (i)).getX() == x && ((Coordinate)coords.get(i)).getY() == y) {
				return true;
			}
		}
		return false;
	}
	private boolean coordsetContainsCoord (Coordinate coord, ArrayList <Coordinate> coords) {
		return coordsetContainsCoord (coord.getX(), coord.getY(), coords);
	}*/
	/*private int coordIndex (Coordinate coord) {
		//Object [] o = this.mappixelindexcoord.keySet().toArray();
		int size = this.mappixelindexcoord.size();
		for (int i = 0; i < size; i++) {
			ArrayList <Coordinate> coords = this.mappixelindexcoord.get((Integer)i);
			if (coordsetContainsCoord (coord, coords)) {
				return (Integer)i;
			}
		}
		try {
			throw new Exception ("No coordinate index found... Something seems wrong");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}*/
	/*private int pixelIndex (int r, int g, int b) {
		//Object [] o = this.mappixelindexcolor.keySet().toArray();
		int size = this.mappixelindexcolor.size();
		for (int i = 0; i < size; i++) {
			Color cc = this.mappixelindexcolor.get((Integer)i);
			if (cc.getRed() == r && cc.getGreen() == g && cc.getBlue() == b) {
				return (Integer)i;
			}
		}
		try {
			throw new Exception ("No pixel index found... Something seems wrong");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}*/
	private boolean similarColors (int r1, int g1, int b1, int r2, int g2, int b2) {
		return r1 == r2 && g1 == g2 && b1 == b2;
	}
	private boolean similarColors (Color c1, Color c2) {
		if (c1 == null || c2 == null) {
			try {
				throw new Exception ("c1 or c2 being null... not expected. Something is wrong");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return similarColors (c1.getRed(), c1.getGreen(), c1.getBlue(), c2.getRed(), c2.getGreen(), c2.getBlue());
	}
	/*private Color getColor (Integer pindex) {
		//Object [] o = this.mappixelindexcoord.keySet().toArray();
		//int size = this.mappixelindexcoord.size();
		int size = this.mappixelindexcolor.size();
		Object [] o = mappixelindexcolor.toArray();
		for (int i = 0; i < size; i++) {
			Integer pix = (Integer) o [i];
			if (pix == pindex) {
				return mappixelindexcolor.get (pix);
			}
		}
		try {
			throw new Exception ("Did not find the color here... Not expected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	private int fromColorToPixelIndex (Color c, ArrayList <Color> map) {
		Object [] o = map.toArray();
		for (int i = 0; i < o.length; i++) {
			Color cc = map.get (i);
			if (cc != null) {
				if (similarColors (c, cc)) {
					return i;
				}
			}
		}
		return -1;
	}
	private void update (Integer pixel, Coordinate coord, Color color, BufferedImage bi) {
		int pixelindex = this.fromColorToPixelIndex (color, this.mappixelindexcolor);
		if (pixelindex == -1) {
			//Color does not exist here...
			ArrayList <Coordinate> coords = new ArrayList <Coordinate> ();
			ArrayList <Integer> coordindexes = new ArrayList <Integer> (/*this.getIndexesForCoordinates(coords, bi)*/);
			coords.add (coord);
			coordindexes.add (getAbsoluteValueFromCoordinate (coord, bi));
			this.mappixelindexcolor.add (/*pixel, */color);
			this.mappixelindexcoord.add (/*pixel, */coords);
			//this.mappixelindexcoordindex.put (pixel, coordindexes);
		}
		else {
			ArrayList <Coordinate> coords = this.mappixelindexcoord.get (pixelindex);
			coords.add (coord);
			this.mappixelindexcoord.set (pixelindex, coords);
			this.mappixelindexcolor.set (pixelindex, color);
			//this.mappixelindexcoordindex.keySet().toArray() [pixelindex] = getIndexesForCoordinates (coords, bi);
		}
		/*int pixelindex = this.colorAlreadyExists (color, this.mappixelindexcolor);
		if (pixelindex != -1 this.containsKeyOnIntegerCoord (pixel)) {
			Color c = this.getColor (pixel);
			if (!similarColors (c, color)) {
				try {
					throw new Exception ("Inconsistent put call!! Same pixel index for a different color!!! NOT ALLOWED!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			HashSet <Coordinate> coords = this.getCoordinates (pixelindex);
			HashSet <Integer> coordindexes = this.getIndexesForCoordinates(coords, bi);
			coords.add (coord);
			coordindexes.add (getAbsoluteValueFromCoordinate (coord, bi));
			this.mappixelindexcoord.remove (pixelindex);
			this.mappixelindexcoord.put (pixel, coords);
			this.mappixelindexcoordindex.put (pixel, coordindexes);
		}
		else {
			if (this.containsKeyOnIntegerColor(pixel)) {
				try {
					throw new Exception ("Inconsistent maps! Pixel " + pixel + " not existent on mapcoord but existing on mapcolor!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			HashSet <Coordinate> coords = new HashSet <Coordinate> ();
			HashSet <Integer> coordindexes = new HashSet <Integer> (this.getIndexesForCoordinates(coords, bi));
			coords.add (coord);
			coordindexes.add (getAbsoluteValueFromCoordinate (coord, bi));
			this.mappixelindexcolor.put (pixel, color);
			this.mappixelindexcoord.put (pixel, coords);
			this.mappixelindexcoordindex.put (pixel, coordindexes);
		}*/
	}
	/*public void mountWaveGraphics () {
		mountWaveGraphics (this.mappixelindexcoordindex);
	}
	private void mountWaveGraphics (HashMap <Integer, HashSet <Integer>> mappixelindexcoordindex) {
		Object [] keys = mappixelindexcoordindex.keySet().toArray();
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		System.out.println (keys.length);
		for (int i = 0; i < keys.length; i++) {
			ds.addValue((double)mappixelindexcoordindex.get(keys[i]).size(), "" + i, "" + keys[i]);
			//ds.addValue((double)mappixelindexcoordindex.get((String)keys[i]).size(), "", "");
			System.out.println (i);
		}
		JFreeChart jfc = ChartFactory.createLineChart("Picture as a wave", "Pixel Index", "Occurrences", ds, PlotOrientation.VERTICAL, true, true, false);
		//ChartUtilities
		this.wavefromim = jfc;
		//return new ImageIcon (ChartUtilities.writeChartAsPNG (arquivo, grafico, 550, 400));
	}*/
	private void mountPixelCoordEnv (BufferedImage bi) {
		//this.mappixelindexcoordindex = new LinkedHashMap <Integer, HashSet <Integer>> ();
		int xmax = bi.getWidth();
		int ymax = bi.getHeight();

		int aux = 0;
		//System.out.println (xmax + " " + ymax);
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				this.update (aux, new Coordinate (i, j), new Color (bi.getRGB(i, j)), bi);
				System.out.println (i + " " + j);
				//Color c = new Color(bi.getRGB (i, j));
				//bi.setRGB(i, j, new Color (oldred + amount, oldgreen + amount, oldblue + amount).getRGB());
				aux++;
			}
		}
		//this.mappixelindexcolortoarray = (Color []) this.mappixelindexcolor.toArray();
		//this.mappixelindexcoordtoarray = (ArrayList <Coordinate> []) this.mappixelindexcoord.toArray();
	}
	
	/**Print methods below*/
	private String colorToString (Color c) {
		if (c == null) {
			try {
				throw new Exception ("c cannot be null... something is wrong");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "C [" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + "]";
	}
	private String coordsToString (ArrayList <Coordinate> coords) {
		String str = "";
		int size = coords.toArray().length;
		for (int i = 0; i < size; i++) {
			str += ((Coordinate)coords.toArray()[i]).coordToString();
			if (i != size - 1) {
				str += " ;; ";
			}
		}
		return str;
	}
	public String toString () {
		int size = this.mappixelindexcolor.size();
		String str = "";
		for (int i = 0; i < size; i++) {
			Color c = this.mappixelindexcolor.get(i);
			ArrayList <Coordinate> coords = this.mappixelindexcoord.get(i);
			if (c != null && coords != null) {
				str += "Pixel " + i + " :: " + colorToString(c) + " :: " + coords.size()/*coordsToString (coords)*/ + " occurrences\n";
				System.out.print ("");
			}
		}
		return str;
	}
	public PixelCoordEnv replacedColorByColor (int colorindex1, int colorindex2, BufferedImage bi) {
		Color color1 = this.mappixelindexcolor.get(colorindex1);
		ArrayList <Coordinate> coords2 = this.mappixelindexcoord.get(colorindex2);
		//int size1 = coords1.size();
		int size2 = coords2.size();

		for (int i = 0; i < size2; i++) {
			Coordinate coord = (Coordinate)coords2.get (i);
			int x = coord.getX();
			int y = coord.getY();
			bi.setRGB(x, y, color1.getRGB());
		}
		return new PixelCoordEnv (bi);
	}
	private int colorValue (Color c, ColorValue cvalue) {
		return cvalue == ColorValue.RED? c.getRed() : (cvalue == ColorValue.GREEN? c.getGreen() : c.getBlue());
	}
	private int colorValue (BufferedImage bi, int x, int y, ColorValue cvalue) {
		return colorValue (new Color (bi.getRGB(x, y)), cvalue);
	}
	private ArrayList <Integer> colorValues (int position, boolean xaxis, ColorValue cvalue, BufferedImage bi) {
		int length = xaxis? bi.getWidth() : bi.getHeight();
		ArrayList <Integer> array = new ArrayList <Integer> ();
		for (int i = 0; i < length; i++) {
			if (xaxis) {
				array.add(colorValue (bi, i, position, cvalue));
			}
			else {
				array.add(colorValue (bi, position, i, cvalue));
			}
		}
		return array;
	}
	public void generateGraphics (int position, boolean xaxis) {
		this.generateGraphics(this.bi, position, xaxis);
	}
	public void generateGraphicsForAllPositions (BufferedImage bi, boolean xaxis) {
	    final XYSeries series1 = new XYSeries ("Red");
	    final XYSeries series2 = new XYSeries ("Green");
	    final XYSeries series3 = new XYSeries ("Blue");
		int lengthaxis = xaxis? bi.getHeight() : bi.getWidth();
	    int aux = 0;
	    for (int j = 0; j < lengthaxis; j++) {
		    ArrayList <Integer> al1 = colorValues (j, xaxis, ColorValue.RED, bi);
		    ArrayList <Integer> al2 = colorValues (j, xaxis, ColorValue.GREEN, bi);
		    ArrayList <Integer> al3 = colorValues (j, xaxis, ColorValue.BLUE, bi);
		    int size1 = al1.size();
		    int size2 = al2.size();
		    int size3 = al3.size();
			int length = xaxis? bi.getHeight() : bi.getWidth(); //The contrary of that on colorValues
		    for (int i = 0; i < size1; i++) {
		    	series1.add (aux, al1.get(i));
		    	series2.add (aux, al2.get(i));
		    	series3.add (aux, al3.get(i));
		    	aux++;
		    }
		    System.out.println ("Position " + aux);
		}
	    XYSeriesCollection ds = new XYSeriesCollection ();
	    ds.addSeries(series1);
	    ds.addSeries(series2);
	    ds.addSeries(series3);

		final JFreeChart chart =
				ChartFactory.createXYLineChart ("Plotted Colors for all positions " + " from " + (xaxis? "x axis" : "y axis"), "Index of color", "Value of colord", ds,
			    		PlotOrientation.VERTICAL, true, true, false
				);
		final ChartPanel chartPanel = new ChartPanel (chart);
		chartPanel.setPreferredSize (new java.awt.Dimension(500, 270));

		this.getContentPane().add (chartPanel);
	    this.pack ();
	    //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    //this.setVisible (true);
	    JFrame frame = new JFrame ();
	    frame.add (chartPanel);
	    System.out.println ("For all positions");
	    frame.setSize (new java.awt.Dimension(500, 270));
	    frame.setVisible (true);
	}
	public void generateGraphics (BufferedImage bi, int position, boolean xaxis) {
		/***
		 * Method adapted from https://stackoverflow.com/questions/16714738/xy-plotting-with-java/16718736
		 * */
	    final XYSeries series1 = new XYSeries ("Red");
	    final XYSeries series2 = new XYSeries ("Green");
	    final XYSeries series3 = new XYSeries ("Blue");
	    ArrayList <Integer> al1 = colorValues (position, xaxis, ColorValue.RED, bi);
	    ArrayList <Integer> al2 = colorValues (position, xaxis, ColorValue.GREEN, bi);
	    ArrayList <Integer> al3 = colorValues (position, xaxis, ColorValue.BLUE, bi);
	    int size1 = al1.size();
	    int size2 = al2.size();
	    int size3 = al3.size();
	    int aux = 0;
		int length = xaxis? bi.getHeight() : bi.getWidth(); //The contrary of that on colorValues
	    for (int i = 0; i < size1; i++) {
	    	series1.add (i, al1.get(i));
	    }
	    for (int i = 0; i < size2; i++) {
	    	series2.add (i, al2.get(i));
	    }
	    for (int i = 0; i < size3; i++) {
	    	series3.add (i, al3.get(i));
	    }
	    XYSeriesCollection ds = new XYSeriesCollection ();
	    ds.addSeries(series1);
	    ds.addSeries(series2);
	    ds.addSeries(series3);

		final JFreeChart chart =
				ChartFactory.createXYLineChart ("Plotted Colors for position " + position + " from " + (xaxis? "x axis" : "y axis"), "Index of color", "Value of colord", ds,
			    		PlotOrientation.VERTICAL, true, true, false
				);
		final ChartPanel chartPanel = new ChartPanel (chart);
		chartPanel.setPreferredSize (new java.awt.Dimension(500, 270));

		this.getContentPane().add (chartPanel);
	    this.pack ();
	    //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    //this.setVisible (true);
	    JFrame frame = new JFrame ();
	    frame.add (chartPanel);
	    frame.setSize (new java.awt.Dimension(500, 270));
	    frame.setVisible (true);
	    /**
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
    * */
	}
}
