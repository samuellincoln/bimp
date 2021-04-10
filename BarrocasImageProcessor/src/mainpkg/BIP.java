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
public class BIP extends JFrame implements Cloneable, ActionListener, MouseListener, MouseMotionListener {
	//private File currentfile;
	private JLabel currentimage;
	private BufferedImage bi = null;
	private OperationsLog operationslog = new OperationsLog ();
	private static String IMAGEFORMAT = "jpg";
	private static String DEFAULTPATH = "C:\\Users\\Samuel\\Desktop\\Samuel012.jpg";

	private JButton buttonchoosefile = new JButton ("Choose File");
	private JButton buttonloadimage = new JButton ("Load Image");
	private JButton buttonsumtoimage = new JButton ("Sum to Image");
	private JButton buttonsubtoimage = new JButton ("Sub to Image");
	private JButton buttonmultoimage = new JButton ("Mul to Image");
	private JButton buttonfibimage = new JButton ("Fib. Like Operator");
	private JButton buttonconsecutiveimage = new JButton ("Consecutive Sum");
	private JButton buttonoddpixels = new JButton ("Decrease Figure Size (Only Odd Pixels)");
	private JButton buttonshowfstimage = new JButton ("Show first loaded Image");
	private JButton buttonshowwave = new JButton ("Show Wave Representation for Image");
	private JButton buttonreplace = new JButton ("Replace Color by Color");
	private JButton buttoninformation = new JButton ("Show Image Info");
	private JButton buttonincrease = new JButton ("Increase Figure Size");
	private JButton buttoncustom = new JButton ("Custom");
	private JTextArea filepatharea = new JTextArea (DEFAULTPATH);
	private JTextArea valuearea = new JTextArea ("25");

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	//private Vector <Color> sequencedimages;
	public BIP (String str, String pathin) {
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
		buttonfibimage.setEnabled(b);
		buttonconsecutiveimage.setEnabled(b);
		buttonoddpixels.setEnabled(b);
		buttonshowwave.setEnabled(b);
		buttonreplace.setEnabled (b);
		buttoninformation.setEnabled (b);
		buttoncustom.setEnabled(b);
		buttonincrease.setEnabled(b);
	}
	private void initGUI (String pathin, String pathout) {
		//this.getContentPane().setLayout(new GridLayout ());
		this.setSize(1100, 700);
		this.getContentPane().setLayout(new BoxLayout (this.getContentPane(), BoxLayout.PAGE_AXIS));
		//this.getContentPane().setLayout(new GroupLayout (this.getContentPane()));		
		/**Declarations**/
		this.currentimage = new JLabel (new ImageIcon (pathin));

		/**Listeners*/
		buttonchoosefile.addActionListener(this);
		buttonloadimage.addActionListener(this);
		buttonsumtoimage.addActionListener(this);
		buttonsubtoimage.addActionListener(this);
		buttonmultoimage.addActionListener(this);
		buttonshowfstimage.addActionListener(this);
		buttonfibimage.addActionListener(this);
		buttonconsecutiveimage.addActionListener(this);
		buttonoddpixels.addActionListener(this);
		buttonshowwave.addActionListener(this);
		buttonreplace.addActionListener(this);
		buttoninformation.addActionListener(this);
		buttoncustom.addActionListener(this);
		buttonincrease.addActionListener(this);

		/**Buttons enabled*/
		setEnabledButtons (false);

		/**Panel 1*/
		JPanel panelline1 = new JPanel ();
		panelline1.add (buttonloadimage);
		panelline1.add (filepatharea);
		panelline1.add (this.buttonchoosefile);
		//panelline1.setLayout(new BoxLayout (panelline1, BoxLayout.Y_AXIS));
		/**Panel 2*/
		JPanel panelline2 = new JPanel ();
		panelline2.add (buttonsumtoimage);
		panelline2.add (buttonsubtoimage);
		//panelline2.add (buttonmultoimage);
		//panelline2.add (buttonshowfstimage);
		//panelline2.add (buttonfibimage);
		//panelline2.add (buttonconsecutiveimage);
		panelline2.add (buttonoddpixels);
		//panelline2.add (buttonincrease);
		panelline2.add (valuearea); //Value to be added, subtracted, multiplied or divided to the image
		//panelline2.setLayout(new BoxLayout (panelline1, BoxLayout.Y_AXIS));
		/**Panel 3*/
		JPanel panelline3 = new JPanel ();
		//panelline3.add (buttonshowwave);
		//panelline3.add (buttonreplace);
		panelline3.add (buttoninformation);
		panelline3.add (buttoncustom);
		//panelline3.setLayout(new BoxLayout (panelline1, BoxLayout.Y_AXIS));
		/**Panel 4*/
		JPanel panelline4 = new JPanel ();
		//panelline4.setPreferredSize(new Dimension (500, 500));
		ImageIcon ii = new ImageIcon (getChosenFile (this.getContentPane(), pathin).getPath());
		//ImageIcon sii = new ImageIcon (ii.getImage().getScaledInstance (200, 200, Image.SCALE_AREA_AVERAGING));
		JLabel labelii = new JLabel (ii);
		//labelii.setSize(200, 200);
		//labelii.setPreferredSize (new Dimension (400, 400));
		//JScrollPane sc = new JScrollPane (labelii);
		//sc.setSize (200, 200);
		//sc.setVisible(true);
		//sc.setPreferredSize (new Dimension (500, 500));
		//sc.set
		//panelline4.add(labelii);
		panelline4.add(labelii);
		/*
		JScrollPane scrpanelline4 = new JScrollPane ();
		scrpanelline4.add (panelline4);
		scrpanelline4.setPreferredSize(new Dimension (400, 400));
		scrpanelline4.setVisible(true);
		*/
		this.getContentPane().add (panelline1);
		this.getContentPane().add (panelline2);
		this.getContentPane().add (panelline3);
		this.getContentPane().add (panelline4);

		//this.getContentPane().add(new JLabel ("File path -->"));
		//this.getContentPane().add(new JTextArea (pathin));
		//this.getContentPane().add(enlightimage);
		
		//this.getContentPane().add(new JPanel (new JLabel (new ImageIcon (getChosenFile (this.getContentPane(), pathin).getPath()))));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible (true);
	}
/***MAIN METHOD*/
	public static void main (String [] args) {
		String path = DEFAULTPATH;
		BIP dip = new BIP ("Barrocas' Image Processor", path);
		//dip.createComplementarFile (path);
		dip.initGUI (path + "", "");
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
			if (e.getActionCommand().contains ("Choose")) {
				JFileChooser chooser = new JFileChooser ();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        int i= chooser.showOpenDialog (null);
		         cfpa = chooser.getSelectedFile();
				this.filepatharea.setText (cfpa.getPath());
			}
			else if (e.getActionCommand().contains ("Show first")) {
				JFrame newframe = new JFrame ("First Loaded Image");
				PixelCoordEnv pce = this.operationslog.pixelCoordEnvAt(0);
				newframe.getContentPane().add(new JLabel (new ImageIcon (pce.getBufferedImage())));
				newframe.setSize (pce.getBufferedImage().getWidth(), pce.getBufferedImage().getHeight());
				newframe.setVisible (true);
			}
			else if (e.getActionCommand().contains ("Custom")) {
				CustomEditing ce = new CustomEditing (new PixelCoordEnv (bi), this.filepatharea.getText());
			}
			else if (e.getActionCommand().contains ("Info")) {
				JFrame newframe = new JFrame ("Information about the image");
				//PixelCoordEnv pce = this.operationslog.pixelCoordEnvAt(0);
				PixelCoordEnv pce = new PixelCoordEnv (bi);
				newframe.getContentPane().add(new JScrollPane (new JTextArea (pce.toString())));
				//newframe.getContentPane().add(new JLabel (new ImageIcon (pce.getBufferedImage())));
				newframe.setSize (pce.getBufferedImage().getWidth(), pce.getBufferedImage().getHeight());
				newframe.setVisible (true);
			}
			else if (e.getActionCommand().contains ("Replace")) {
				JFrame newframe = new JFrame ("Replace");
				PixelCoordEnv pce = new PixelCoordEnv (bi);
				BufferedImage retimage = pce.replacedColorByColor(0, 1, bi).getBufferedImage();
				this.operationslog.put (new PixelCoordEnv (retimage));
				this.bi = retimage;
				//retimage.flush();
				try {
					Object dip2 = this.clone();
					//this.currentfile = new File (((DIP)dip2).currentfile.getPath());
					//CustomEditing ce = new CustomEditing (pce);
					this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), retimage, this, this));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else if (e.getActionCommand().contains ("Wave")) {
				JFrame newframe = new JFrame ("Wave Representation");
				PixelCoordEnv pce = new PixelCoordEnv (bi);
				//pce.mountWaveGraphics();
				newframe.getContentPane().add (new JLabel (new ImageIcon (pce.getWaveGraphic().getBackgroundImage())));
				newframe.setSize (1000, 600);
				newframe.setVisible(true);
				/*PixelCoordEnv pce = this.operationslog.pixelCoordEnvAt(0);
				newframe.getContentPane().add(new JLabel (new ImageIcon (pce.getBufferedImage())));
				newframe.setSize (pce.getBufferedImage().getWidth(), pce.getBufferedImage().getHeight());
				newframe.setVisible (true);*/
			}
			else if (e.getActionCommand().contains ("Load")) {
				bi = ImageIO.read (cfpa);
				PixelCoordEnv pce = new PixelCoordEnv (bi);
				this.operationslog.put (pce);
				//CustomEditing ce = new CustomEditing (pce);
				this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), bi, this, this));
				setEnabledButtons (true);
				this.buttoninformation.setEnabled(false);
			}
			else if (e.getActionCommand().contains ("Sum")) {
				System.out.println ("Summing value to image");
				BufferedImage retimage = this.replacedColorsByTheirsSums (bi/*cfpa.getPath()*/, value).getBufferedImage();
				PixelCoordEnv pce = new PixelCoordEnv (retimage);
				//CustomEditing ce = new CustomEditing (pce);
				this.operationslog.put (pce);
				this.bi = retimage;
				//retimage.flush();
				try {
					Object dip2 = this.clone();
					//this.currentfile = new File (((DIP)dip2).currentfile.getPath());
					this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), retimage, this, this));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand().contains ("Sub")) {
				System.out.println ("Subtracting value to image");
				BufferedImage retimage = this.replacedColorsByTheirsSums (bi/*cfpa.getPath()*/, -value).getBufferedImage();
				PixelCoordEnv pce = new PixelCoordEnv (retimage);
				//CustomEditing ce = new CustomEditing (pce);
				this.operationslog.put (pce);
				this.bi = retimage;
				try {
					Object dip2 = this.clone();
					this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), retimage, this, this));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand().contains ("Fib.")) {
				System.out.println ("Applying Fibonacci-like Operator");
				BufferedImage retimage = this.replacedColorsByFibonacciLikeOperation(bi).getBufferedImage();
				PixelCoordEnv pce = new PixelCoordEnv (retimage);
				//CustomEditing ce = new CustomEditing (pce);
				this.operationslog.put (pce);
				this.bi = retimage;
				try {
					Object dip2 = this.clone();
					this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), retimage, this, this));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand().contains ("Odd")) {
				System.out.println ("Applying Odd Pixel Filter");
				BufferedImage retimage = CustomEditing.filterOddPixels(bi, 0).getBufferedImage();
				PixelCoordEnv pce = new PixelCoordEnv (retimage);
				//CustomEditing ce = new CustomEditing (pce);
				this.operationslog.put (pce);
				this.bi = retimage;
				try {
					Object dip2 = this.clone();
					this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), retimage, this, this));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand().contains ("Increase")) {
				System.out.println ("Increasing Resolution");
				BufferedImage retimage = this.increaseImageResolution (bi, 0).getBufferedImage();
				PixelCoordEnv pce = new PixelCoordEnv (retimage);
				//CustomEditing ce = new CustomEditing (pce);
				this.operationslog.put (pce);
				this.bi = retimage;
				try {
					Object dip2 = this.clone();
					this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), retimage, this, this));
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand().contains ("Consecutive")) {
				System.out.println ("Applying Consecutive Sum Operator");
				BufferedImage retimage = this.replacedColorsBySumOfConsecutivePixels(bi).getBufferedImage();
				PixelCoordEnv pce = new PixelCoordEnv (retimage);
				//CustomEditing ce = new CustomEditing (pce);
				this.operationslog.put (pce);
				this.bi = retimage;
				try {
					Object dip2 = this.clone();
					this.setContentPane(CustomEditing.updateImageOnContentPane (this.getContentPane(), retimage, this, this));
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
	/*public PixelCoordEnv replacedFigureByPixelIndexesMultipleBy (BufferedImage bi, int divisor) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		if (w % divisor != 0 || h % divisor != 0) {
			try {
				throw new Exception ("Cannot apply method... Divisor not being dividable by width or height");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/
	public PixelCoordEnv increaseImageResolution (BufferedImage bi, int initialpos) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		BufferedImage bi2 = new BufferedImage (2*w, 2*h, BufferedImage.TYPE_INT_RGB);
		for (int i = initialpos; i < 2*w; i++) {
			for (int j = initialpos; j < 2*h; j++) {
				bi2.setRGB(i, j,
						new Color (new Color (bi.getRGB(i/2, j/2)).getRed()
						, new Color (bi.getRGB(i/2, j/2)).getGreen()
						, new Color (bi.getRGB(i/2, j/2)).getBlue()
				).getRGB());
			}
		}
		return new PixelCoordEnv (bi2);
	}
	public PixelCoordEnv replacedColorsBySumOfConsecutivePixels (BufferedImage bi) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		int aux = 0;
		BufferedImage bi2 = new BufferedImage (w, h, BufferedImage.TYPE_INT_RGB);
		Color currentcolor = new Color (0, 0, 0);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (i != 0 && j != 0) {
					int summedred = sumColors (new Color (bi.getRGB(i, j)).getRed(), currentcolor.getRed());
					int summedgreen = sumColors (new Color (bi.getRGB(i, j)).getRed(), currentcolor.getGreen());
					int summedblue = sumColors (new Color (bi.getRGB(i, j)).getRed(), currentcolor.getBlue());
	
					bi2.setRGB(i, j, new Color (summedred, summedgreen, summedblue).getRGB());
					//currentcolor = new Color (summedred, summedgreen, summedblue);
				}
			}
		}
		return new PixelCoordEnv (bi2);
	}
	public PixelCoordEnv replacedColorsByFibonacciLikeOperation (BufferedImage bi) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		int aux = 0;
		BufferedImage bi2 = new BufferedImage (w, h, BufferedImage.TYPE_INT_RGB);
		Color currentcolor = new Color (0, 0, 0);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int summedred = sumColors (new Color (bi.getRGB(i, j)).getRed(), currentcolor.getRed());
				int summedgreen = sumColors (new Color (bi.getRGB(i, j)).getRed(), currentcolor.getGreen());
				int summedblue = sumColors (new Color (bi.getRGB(i, j)).getRed(), currentcolor.getBlue());

				bi2.setRGB(i, j, new Color (summedred, summedgreen, summedblue).getRGB());
				currentcolor = new Color (summedred, summedgreen, summedblue);
			}
		}
		return new PixelCoordEnv (bi2);
	}
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

	private Color colorFromPosition (BufferedImage bi, int x, int y) {
		return new Color(bi.getRGB (x, y));
	}
	private Color colorFromPosition (File file, int x, int y) throws IOException {
		BufferedImage bi = ImageIO.read(file);
		return colorFromPosition (bi, x, y);
	}
	/*public void replaceNearBlackByWhite (String path) {
		replaceIntervalByWhite (path, 0, 0, 0, 7, 7, 7, 255, 255, 255);
	}*/
	/*public void replaceWhiteByBlack (String path) {
		replaceColorOnImage (path, 255, 255, 255, 0, 0, 0);
	}
	public void replaceBlackByWhite (String path) {
		replaceColorOnImage (path, 0, 0, 0, 255, 255, 255);
	}*/
	/*private BufferedImage sumColorsOnBufferedImage (String path, int red, int green, int blue, BufferedImage bi, int amount) {
		return replaceColorOnBufferedImage (path, red, green, blue, red + amount, green + amount, blue + amount, bi);
	}*/
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
	/*public void replaceColorOnImage (String path
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
	}*/
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
