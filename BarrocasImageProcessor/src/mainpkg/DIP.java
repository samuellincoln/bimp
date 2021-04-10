package mainpkg;
import javax.swing.*;

import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
public class DIP extends JFrame implements Cloneable, ActionListener {
	//private File currentfile;
	private JLabel currentimage;
	private BufferedImage bi = null;
	private OperationsLog operationslog = new OperationsLog ();
	private static String IMAGEFORMAT = "jpg";
	private static String DEFAULTPATH = "C:\\Users\\Samuel\\Desktop\\strangerthings - Copia.jpg";

	private JButton buttonloadimage = new JButton ("Load Image");
	private JButton buttonsumtoimage = new JButton ("Sum to Image");
	private JButton buttonsubtoimage = new JButton ("Sub to Image");
	private JButton buttonmultoimage = new JButton ("Mul to Image");
	private JButton buttonshowfstimage = new JButton ("Show first loaded Image");
	private JTextArea filepatharea = new JTextArea (DEFAULTPATH);
	private JTextArea valuearea = new JTextArea ("25");

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	//private Vector <Color> sequencedimages;
	public DIP (String str, String pathin) {
		super (str);
		this.operationslog = new OperationsLog ();
		//this.currentfile = new File (pathin);
		String cp = pathin.substring (0, pathin.length() - 4) + "BIP." + IMAGEFORMAT;
	}
	/*private void createComplementarFile (String path) {
		try {
			File fw = File.createTempFile(prefix, suffix) (this.complpathin);
			//fw.create
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	private void setEnabledButtons (boolean b) {
		buttonsumtoimage.setEnabled(b);
		buttonsubtoimage.setEnabled(b);
		buttonmultoimage.setEnabled(b);
		buttonshowfstimage.setEnabled(b);
	}
	private void initGUI (String pathin, String pathout) {
		this.getContentPane().setLayout(new FlowLayout ());
		/**Declarations**/
		this.currentimage = new JLabel (new ImageIcon (pathin));

		/**Listeners*/
		buttonloadimage.addActionListener(this);
		buttonsumtoimage.addActionListener(this);
		buttonsubtoimage.addActionListener(this);
		buttonmultoimage.addActionListener(this);
		buttonshowfstimage.addActionListener(this);
		/**Buttons enabled*/
		setEnabledButtons (false);
		/**Panel 1*/
		JPanel panelline1 = new JPanel ();
		panelline1.add (buttonloadimage);
		panelline1.add (filepatharea);
		/**Panel 2*/
		JPanel panelline2 = new JPanel ();
		panelline2.add (buttonsumtoimage);
		panelline2.add (buttonsubtoimage);
		panelline2.add (buttonmultoimage);
		panelline2.add (buttonshowfstimage);
		panelline2.add (valuearea); //Value to be added, subtracted, multiplied or divided to the image		
		
		this.getContentPane().add (panelline1);
		this.getContentPane().add (panelline2);
		
		//this.getContentPane().add(new JLabel ("File path -->"));
		//this.getContentPane().add(new JTextArea (pathin));
		//this.getContentPane().add(enlightimage);
		this.getContentPane().add(new JLabel (new ImageIcon (getChosenFile (this.getContentPane(), pathin).getPath())));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);
		this.setVisible (true);
	}
/***MAIN METHOD*/
	public static void main (String [] args) {
		String path = DEFAULTPATH;
		DIP dip = new DIP ("Barrocas' Image Processor", path);
		//dip.createComplementarFile (path);
		dip.initGUI (path + "", "");
	}
/**ABOVE :: MAIN METHOD******/
	private Container updateImageOnContentPane (Container c, BufferedImage bi) {
		Component [] components = c.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components [i] instanceof JLabel) {
				Icon icon = ((JLabel)components[i]).getIcon();
				if (icon instanceof ImageIcon) {
					//((JLabel)c.getComponent(i)).setIcon (new ImageIcon (path));
					c.remove (i);
					c.add (new JLabel (new ImageIcon (bi)));
				}
			}
		}
		return c;
	}
	private File getChosenFile (Container c, String path) {
		Component [] components = c.getComponents();
		String str = "";
		String retstr = "";
		for (int i = 0; i < components.length; i++) {
			if (components [i] instanceof JTextArea) {
				str = ((JTextArea)components[i]).getText();
				if (str.contains ("\\") || str.contains ("/")) { //In this situation, the text area is a file path
					retstr = str;
				}
			}
		}
		return new File (retstr);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			Integer value = Integer.parseInt(valuearea.getText());
			File cfpa = new File (filepatharea.getText());
			if (e.getActionCommand().contains ("Show first")) {
				JFrame newframe = new JFrame ("First Loaded Image");
				PixelCoordEnv pce = this.operationslog.pixelCoordEnvAt(0);
				newframe.getContentPane().add(new JLabel (new ImageIcon (pce.getBufferedImage())));
				newframe.setSize (pce.getBufferedImage().getWidth(), pce.getBufferedImage().getHeight());
				newframe.setVisible (true);
			}
			else if (e.getActionCommand().contains ("Load")) {
				bi = ImageIO.read (cfpa);
				this.operationslog.put (new PixelCoordEnv (bi));
				this.setContentPane(updateImageOnContentPane (this.getContentPane(), bi));
				setEnabledButtons (true);
			}
			else if (e.getActionCommand().contains ("Sum")) {
				System.out.println ("Summing value to image");
				BufferedImage retimage = this.replacedColorsByTheirsSums (bi/*cfpa.getPath()*/, value).getBufferedImage();
				//retimage.flush();
				try {
					Object dip2 = this.clone();
					//this.currentfile = new File (((DIP)dip2).currentfile.getPath());
					this.setContentPane(updateImageOnContentPane (this.getContentPane(), retimage));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand().contains ("Sub")) {
				System.out.println ("Subtracting value to image");
				BufferedImage retimage = this.replacedColorsByTheirsSums (bi/*cfpa.getPath()*/, -value).getBufferedImage();
				try {
					Object dip2 = this.clone();
					//this.currentfile = new File (((DIP)dip2).currentfile.getPath());
					this.setContentPane(updateImageOnContentPane (this.getContentPane(), retimage));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			/*else if (e.getActionCommand().contains ("Open")) {
				System.out.println ("Opening File");
				this.setContentPane(updateImageOnContentPane (this.getContentPane(), bi));
				//JFrame frame = new JFrame ("Image");
				//frame.getContentPane().add (new JLabel (new ImageIcon (bi)));
				//frame.setSize (500, 500);
				//frame.setVisible (true);
			}*/
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}	
//@Override
	private void buildGUI (JFrame f) {
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout ());
		
	}
	private Vector <Color> sequenceImages (BufferedImage bi) {
		Vector <Color> retc = new Vector <Color> ();
		int xmax = bi.getWidth();
		int ymax = bi.getHeight();
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				retc.addElement (new Color (bi.getRGB(xmax, ymax)));
			}
		}
		return retc;
	}
	private Vector <Integer> getOccurrencesFromColorOnImage (Color c, Vector <Color> vc) {
		Vector <Integer> retoc = new Vector <Integer> ();
		int size = vc.size();
		for (int i = 0; i < size; i++) {
			Color cc = vc.elementAt(i);
			boolean condition = cc.getRed() == c.getRed() && cc.getBlue() == c.getBlue() && cc.getGreen() == c.getGreen();
			if (condition) {
				retoc.addElement (i);
			}
		}
		return retoc;
	}
	/*private Graphics2D graphicsForColor (Color c, Vector <Color> occurrences, BufferedImage bi) {
		Graphics2D g = bi.createGraphics();
		g.
	}*/
	/*public void processimage (String path) {
		File input = new File (path);
		try {
			BufferedImage bi = ImageIO.read(input);

			Graphics2D g2d = bi.createGraphics();
			//g2d.drawLine(0, 500, 0, 500);
			//g2d.
			//ImageIO.write(bi, "png", input);
			//BufferedImage bi2 = new BufferedImage (bi);
			//ImageIO.write(im, formatName, output)
			int xmax = bi.getWidth();
			int ymax = bi.getHeight();
			processimage (path, );
	}*/
	private int sumColors (int r, int amount) {
		return r + amount > 255? 255 : (r + amount < 0? 0 : r + amount);
	}
	public BufferedImage replacedColorsByTheirSubs (String input, int amount) {
		return replacedColorsByTheirSubs (input, -amount);
	}
	/*public PixelCoordEnv replacedIndividualPixel () {
		
	}*/
	public PixelCoordEnv replacedColorsByTheirsSums (BufferedImage bi/*String input*/, int amount) {
		//BufferedImage bi;
		BufferedImage bi2 = new BufferedImage (1, 1, BufferedImage.TYPE_INT_RGB);
		//File file = new File (input);
		//bi = ImageIO.read(file);
		int w = bi.getWidth();
		int h = bi.getHeight();
		bi2 = new BufferedImage (w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi2.createGraphics();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				bi2.setRGB(i, j, new Color (
						sumColors (new Color (bi.getRGB(i, j)).getRed(), amount)
						, sumColors (new Color (bi.getRGB(i, j)).getGreen(), amount)
						, sumColors (new Color (bi.getRGB(i, j)).getBlue(), amount)
						//new Color (bi.getRGB(i, j)).getRed() + amount > 255? 255 : new Color (bi.getRGB(i, j)).getRed() + amount
						//, new Color (bi.getRGB(i, j)).getGreen() + amount > 255? 255 : new Color (bi.getRGB(i, j)).getGreen() + amount
						//, new Color (bi.getRGB(i, j)).getBlue() + amount > 255? 255 : new Color (bi.getRGB(i, j)).getBlue() + amount
				).getRGB());
			}
		}
		//ImageIO.write (bi2, IMAGEFORMAT, file);
		return new PixelCoordEnv (bi2);
	}

	public void replaceIntervalByWhite (
			String input
			, int rleft, int gleft, int bleft
			, int rright, int gright, int bright
	) {
		BufferedImage bi;
		try {
			File file = new File (input);
			bi = ImageIO.read(file);
			for (int i = rleft; i <= rright; i++) {
				for (int j = gleft; j <= gright; j++) {
					for (int k = bleft; k <= bright; k++) {
						bi = replaceColorOnBufferedImage (input, i, j, k, 255, 255, 255, bi);
						//replaceColorOnImage (path, 0, 0, 0, i, j, k);
					}
				}
			}
			ImageIO.write (bi, IMAGEFORMAT, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Color colorFromPosition (BufferedImage bi, int x, int y) {
		return new Color(bi.getRGB (x, y));
	}
	private Color colorFromPosition (File file, int x, int y) throws IOException {
		BufferedImage bi = ImageIO.read(file);
		return colorFromPosition (bi, x, y);
	}
	public void replaceNearBlackByWhite (String path) {
		replaceIntervalByWhite (path, 0, 0, 0, 7, 7, 7);
	}
	public void replaceWhiteByBlack (String path) {
		replaceColorOnImage (path, 255, 255, 255, 0, 0, 0);
	}
	public void replaceBlackByWhite (String path) {
		replaceColorOnImage (path, 0, 0, 0, 255, 255, 255);
	}
	/*private BufferedImage sumColorsOnBufferedImage (String path, int red, int green, int blue, BufferedImage bi, int amount) {
		return replaceColorOnBufferedImage (path, red, green, blue, red + amount, green + amount, blue + amount, bi);
	}*/
	private BufferedImage replaceColorOnBufferedImage (String path
			, int oldred, int oldgreen, int oldblue
			, int newred, int newgreen, int newblue, BufferedImage bi) {
		int xmax = bi.getWidth();
		int ymax = bi.getHeight();
		int aux = 0;
		System.out.println (xmax + " " + ymax);
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				Color c = new Color(bi.getRGB (i, j));
				if (c.getRed() == oldred && c.getGreen() == oldgreen && c.getBlue() == oldblue) {
					bi.setRGB(i, j, new Color (newred, newgreen, newblue).getRGB());
				}
				aux++;
			}
		}
		return bi;
	}
	private BufferedImage sumColorIndexesOnBufferedImage (String path
			, int oldred, int oldgreen, int oldblue
			, int amount, BufferedImage bi) {
		int xmax = bi.getWidth();
		int ymax = bi.getHeight();
		int aux = 0;
		System.out.println (xmax + " " + ymax);
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				Color c = new Color(bi.getRGB (i, j));
				bi.setRGB(i, j, new Color (oldred + amount, oldgreen + amount, oldblue + amount).getRGB());
				aux++;
			}
		}
		return bi;
	}
	public void subColorIndexes (String path, int oldred, int oldgreen, int oldblue, int amounttosub) {
		sumColorIndexes (path, oldred, oldgreen, oldblue, -amounttosub);
	}
	public void sumAllColorIndexes (String path) {
		
	}
	public void sumColorIndexes (String path, int oldred, int oldgreen, int oldblue, int amounttosum) {
		FileReader input;
		try {
			BufferedImage bi = ImageIO.read (new File (this.filepatharea.getText()));
			bi = sumColorIndexesOnBufferedImage (path, oldred, oldgreen, oldblue, amounttosum, bi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void replaceColorOnImage (String path
			, int oldred, int oldgreen, int oldblue
			, int newred, int newgreen, int newblue
	) {
		
		File input = new File (path);
		try {
			BufferedImage bi = ImageIO.read(input);
			//g2d.drawLine(0, 500, 0, 500);
			//g2d.
			//ImageIO.write(bi, "png", input);
			//BufferedImage bi2 = new BufferedImage (bi);
			//ImageIO.write(im, formatName, output)
			bi = replaceColorOnBufferedImage (path, oldred, oldgreen, oldblue, newred, newgreen, newblue, bi);
			ImageIO.write(bi, IMAGEFORMAT, input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
