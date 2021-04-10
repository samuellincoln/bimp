package mainpkg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CustomEditing extends JFrame implements ActionListener, MouseListener, MouseMotionListener, TextListener {

	private enum PixelCategory {
		SOURCE
		, EDGE1
		, EDGE2
	}
	private enum Command {
		COORD
		, COLOR
		, SINGLEPIXEL
		, RECTANGLE
		, LINE
	}
	private enum MousePreviousState {
		RECPRESSED
		, RECDRAGGED
		, RECRELEASED
	}
	private String PIXELCOLORMBY = "colorsmultipleof";
	private String PIXELCOORDMBY = "coordsmultipleof";
	private String RECTANGLE = "rectangle";
	private String LINE = "line";

	//private String MULOFCOMM = "multipleof";

	PixelCategory pc;
	private MousePreviousState mouseprevstate;
	private PixelCoordEnv pce;
	private boolean sourcepixelwaschosen;
	private int pixelmul;
	private Command currentcommand;
	private JButton replacesmooth;
	private JButton plotwave;
	private JTextArea pixelvalues;
	private JTextArea pixelcoord;
	private Coordinate sourcepixel;
	private Coordinate edgepixel1;
	private Coordinate edgepixel2;
	private Coordinate coorddif;
	private Color currentinspixel;
	private Color sourcepixelcolor;
	private Color targetpixelcolor;
	private JTextArea insertedpixels;
	private TextField sourcepixelarea;
	private TextField targetpixelarea;
	private TextField smoothrange;
	private JButton converto;
	private JButton buttonopenfile;
	private JButton buttonsavefile;
	private JTextField pathtext;
	private String filepath;

	public CustomEditing (PixelCoordEnv pce, String filepath) {
		/*this.pc = PixelCategory.SOURCE;
		this.pce = pce;
		this.sourcepixelwaschosen = false;*/
		this.filepath = filepath;
		this.pixelmul = -1;
		instVariables (pce);
		instComponents ();
		setListeners ();
		editableDraggedPixelsArea (false);
		mountContainer ();
	}
	/*public CustomEditing (PixelCoordEnv pce) {
		this (pce, false);
	}*/
	private void instVariables (PixelCoordEnv pce) {
		this.pc = PixelCategory.SOURCE;
		this.pce = pce;
		this.sourcepixelwaschosen = false;
		this.coorddif = new Coordinate (-1, -1);
	}
	private void instComponents () {
		this.replacesmooth = new JButton ("Smooth Pixel Transitions");
		this.plotwave = new JButton ("Plot Wave");
		this.pixelvalues = new JTextArea ("Pixel Values");
		this.pixelcoord = new JTextArea ("Pixel Coordinates");
		this.sourcepixelarea = new TextField ("Source Pixel");
		this.targetpixelarea = new TextField ("Target Pixel");
		this.converto = new JButton ("Convert to");
		this.insertedpixels = new JTextArea (20, 20);
		this.currentinspixel = new Color (0, 0, 0);
		this.sourcepixelcolor = new Color (0, 0, 0);
		this.targetpixelcolor = new Color (0, 0, 0);
		this.smoothrange = new TextField ("5");
		//this.buttonopenfile = new JButton ("Choose file to open");
		this.buttonsavefile = new JButton ("Save file");
		this.pathtext = new JTextField ("Path to save file");
		this.pathtext.setText (this.filepath);
		this.insertedpixels.setEnabled (false);
		this.replacesmooth.setEnabled (true);
		this.smoothrange.setEnabled (true);
	}
	private void setListeners () {
		this.replacesmooth.addActionListener (this);
		this.converto.addActionListener (this);
		this.sourcepixelarea.addTextListener(this);
		this.buttonsavefile.addActionListener(this);
		this.plotwave.addActionListener(this);
	}

	private void editableDraggedPixelsArea (boolean b) {
		this.pixelvalues.setEditable (b);
		this.pixelcoord.setEditable (b);
	}

	private void mountContainer () {
		this.getContentPane().setLayout (new BoxLayout (this.getContentPane(), BoxLayout.Y_AXIS));
		//this.getContentPane().setLayout (new GridLayout ());

		JPanel panel1 = new JPanel ();
		panel1.add (pixelvalues);
		panel1.add (pixelcoord);
		panel1.add (buttonsavefile);
		//panel1.add (pathtext);
		ImageIcon imageicon = new ImageIcon (this.pce.getBufferedImage());
		JLabel figure = new JLabel (imageicon);
		//JLabel figure = new JLabel (new ImageIcon (imageicon.getImage().getScaledInstance(200,  200, Image.SCALE_SMOOTH)));

		figure.addMouseListener(this);
		figure.addMouseMotionListener (this);
		JScrollPane fs = new JScrollPane (figure);
		fs.setVisible(true);
		fs.setEnabled(true);
		fs.setPreferredSize(new Dimension (300,  300));

		JPanel panel2 = new JPanel ();
		panel2.add (this.sourcepixelarea);
		panel2.add (this.converto);
		panel2.add (this.targetpixelarea);
		JPanel panel2p5 = new JPanel ();
		panel2p5.add (this.replacesmooth);
		panel2p5.add (this.smoothrange);

		JPanel panel3 = new JPanel ();
		//panel3.setPreferredSize (new Dimension (400, 400));
		panel3.add (figure);
		JScrollPane panel3sp = new JScrollPane (panel3);
		panel3sp.setSize (200, 200);
		panel3sp.setVisible(true);
		
		JPanel panel3w = new JPanel ();
		panel3w.add (this.plotwave);
		JPanel panel4 = new JPanel ();
		panel4.add (this.insertedpixels);

		//this.getContentPane().setLayout (new GridLayout ());

		//this.getContentPane().add (sp);
		this.getContentPane().add (panel1);
		this.getContentPane().add (panel2);
		this.getContentPane().add (panel2p5);
		this.getContentPane().add (panel3w);
		this.getContentPane().add (panel3sp);
		this.getContentPane().add (panel4);
		//this.setPreferredSize (new Dimension (500, 500));
		this.setSize (600, 700);
		this.setVisible (true);
	}
	public static PixelCoordEnv filterOddPixels (BufferedImage bi, int initialpos) {
		return workOnIntermediatePixels (bi, initialpos, 2, "FILTER", new Color (0, 0, 0));
	}
	public static PixelCoordEnv workOnIntermediatePixels (BufferedImage bi, int initialpos, int mby, String op, Color c) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		if (w % mby != 0 || h % mby != 0) {
			try {throw new Exception ("Not appliable");} catch (Exception e) {e.printStackTrace();}
		}
		if (op.equals ("FILTER")) {
			BufferedImage bi2 = new BufferedImage (w/mby, h/mby, BufferedImage.TYPE_INT_RGB);
			for (int i = initialpos; i < w; i++) {
				for (int j = initialpos; j < h; j+=mby) {
					bi2.setRGB(i/mby, j/mby,
							new Color (new Color (bi.getRGB(i, j)).getRed()
							, new Color (bi.getRGB(i, j)).getGreen()
							, new Color (bi.getRGB(i, j)).getBlue()
					).getRGB());
				}
			}
			return new PixelCoordEnv (bi2);
		}
		else {
			BufferedImage bi2 = bi;
			for (int i = initialpos; i < w; i++) {
				for (int j = initialpos; j < h; j+=mby) {
					bi2.setRGB(i, j, new Color (c.getRed(), c.getGreen(), c.getBlue()).getRGB());
				}
			}
			return new PixelCoordEnv (bi2);
		}
	}
	
	private BufferedImage makeImageFromEdges (int x1, int y1, int x2, int y2, BufferedImage bi) {
		BufferedImage bci = new BufferedImage (x2 - x1, y2 - y1, BufferedImage.TYPE_INT_RGB);
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				bci.setRGB(i, j, bi.getRGB(i, j));
			}
		}
		return bci;
	}

	private BufferedImage makeImageReplacementFromEdges 
		(
			int ax1, int ay1, int ax2, int ay2
			, int adpos1, int adpos2
			, BufferedImage bi
		) {
		
		for (int i = ax1; i <= ax2; i++) {
			for (int j = ay1; j <= ay2; j++) {
				bi.setRGB(i + adpos1, j + adpos2, bi.getRGB(i, j));
			}
		}
		return bi;
	}
	/*private Vector <Coordinate> magicWandTool (Coordinate initialposition, Coordinate targetposition, PixelCoordEnv pce) {
		
	}*/
	//replaceIntervalByColor (Coordinate coordleft, Coordinate coordright, Coordinate coordnew, BufferedImage bi)
	public BufferedImage replaceIntervalByColor (
		Coordinate coordleft, Coordinate coordright, Coordinate coordnew, BufferedImage bi
	) {
		return replaceIntervalByColor (
				coordleft.getX(), coordleft.getY()
				, coordright.getX(), coordright.getY()
				, coordnew.getX(), coordnew.getY()
				, bi
				);
	}
	public BufferedImage replaceIntervalByColor (int x1, int y1, int x2, int y2, int xnew, int ynew, BufferedImage bi) {
		Color colorleft = new Color (bi.getRGB(x1, y1));
		Color colorright = new Color (bi.getRGB(x2, y2));
		Color colornew = new Color (bi.getRGB(xnew, ynew));
		return
			replaceIntervalByColor (
				colorleft.getRed(), colorleft.getGreen(), colorleft.getBlue()
				, colorright.getRed(), colorright.getGreen(), colorright.getBlue()
				, colornew.getRed(), colornew.getGreen(), colornew.getBlue()
				, bi
			);
	}
	private int max (int x, int y) {
		return x > y? x : y;
	}
	private int min (int x, int y) {
		return x < y? x : y;
	}
	//TODO test method below (23/12/2019)
	public BufferedImage replaceRectangleByRectangle (
			int xleft, int yleft
			, int xright, int yright
			, int xleftnew, int yleftnew
			, BufferedImage bi
	) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		int xrightnew = xright - xleft + xleftnew;
		int yrightnew = yright - yleft + yleftnew;
		for (int i = 0; i < xright - xleft; i++) {
			for (int j = 0; j < yright - yleft; j++) {
				//JOptionPane.showMessageDialog(null, "blablabla");
				Color c = new Color (bi.getRGB (i + xleft, j + yleft));
				Color cnew = new Color (bi.getRGB(i + xleftnew, j + yleftnew));//new Color (rnew, gnew, bnew);
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				bi.setRGB (i + xleft, j + yleft, new Color (cnew.getRed(), cnew.getGreen(), cnew.getBlue()).getRGB());
				//bi = replaceColorOnBufferedImage (r, g, b, rnew, gnew, bnew, bi);
				//bi2 = bi;
				//replaceColorOnImage (path, 0, 0, 0, i, j, k);
			}
		}
		return bi;
	}
	public BufferedImage replaceRectangleByColor (
			int xleft, int yleft
			, int xright, int yright
			, int rnew, int gnew, int bnew
			, BufferedImage bi
	) {
		int w = bi.getWidth();
		int h = bi.getHeight();
		for (int i = xleft; i < xright; i++) {
			for (int j = yleft; j < yright; j++) {
				//JOptionPane.showMessageDialog(null, "blablabla");
				Color c = new Color (bi.getRGB (i, j));
				Color cnew = new Color (rnew, gnew, bnew);
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				bi.setRGB (i, j, new Color (cnew.getRed(), cnew.getGreen(), cnew.getBlue()).getRGB());
				//bi = replaceColorOnBufferedImage (r, g, b, rnew, gnew, bnew, bi);
				//bi2 = bi;
				//replaceColorOnImage (path, 0, 0, 0, i, j, k);
			}
		}
		return bi;
	}
	public BufferedImage replaceIntervalByColor (
			int rleft, int gleft, int bleft
			, int rright, int gright, int bright
			, int rnew, int gnew, int bnew
			, BufferedImage bi
	) {
		//File file = new File (input);
		//bi = ImageIO.read(file);
		BufferedImage bi2 = new BufferedImage (bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
		int rmin = min (rleft, rright);
		int rmax = max (rleft, rright);
		int gmin = min (gleft, rright);
		int gmax = max (gleft, rright);
		int bmin = min (bleft, rright);
		int bmax = max (bleft, rright);

		for (int i = rmin; i <= rmax; i++) {
			for (int j = gmin; j <= gmax; j++) {
				for (int k = bmin; k <= bmax; k++) {
					//JOptionPane.showMessageDialog(null, "blablabla");
					bi = replaceColorOnBufferedImage (i, j, k, rnew, gnew, bnew, bi);
					//bi2 = bi;
					//replaceColorOnImage (path, 0, 0, 0, i, j, k);
				}
			}
		}
		return bi;
	}
	private Vector <String> tokenize (String str) {
		StringTokenizer st = new StringTokenizer (str);
		Vector <String> vec = new Vector <String> ();
		while (st.hasMoreTokens()) {
			String currenttok = st.nextToken();
			vec.addElement(currenttok);
		}
		return vec;
	}
	private void processCommand (String str, boolean issource, BufferedImage bi) {
		StringTokenizer st = new StringTokenizer (str);
		Vector <String> vec = tokenize (str);
		/*while (st.hasMoreTokens()) {
			String currenttok = st.nextToken();
			vec.addElement(currenttok);
		}*/
		if (vec.elementAt(0).equals (this.PIXELCOLORMBY)
			|| vec.elementAt(0).equals (this.PIXELCOORDMBY)
			) {
			this.pixelmul = I (vec.elementAt(1));
			this.currentcommand = vec.elementAt(0).equals (this.PIXELCOORDMBY)? Command.COORD : Command.COLOR;
		}
		else if (vec.elementAt(0).equals (this.RECTANGLE)) {
			this.currentcommand = Command.RECTANGLE;
		}
		else {
			if (issource) {
				this.sourcepixelcolor = new Color (I (vec.elementAt(0)), I (vec.elementAt(1)), I (vec.elementAt(2)));
				this.currentcommand = Command.SINGLEPIXEL;
			}
			else if (this.currentcommand.equals (Command.SINGLEPIXEL)) {
				this.targetpixelcolor = new Color (I (vec.elementAt(0)), I (vec.elementAt(1)), I (vec.elementAt(2)));
			}
			else {
				//this.targetpixelcolor = new Color (I (vec.elementAt(0)), I (vec.elementAt(1)), I (vec.elementAt(2)));
				/*int r = new Color (bi.getRGB (I (vec.elementAt(0)), I (vec.elementAt(1)))).getRed();
				int g = new Color (bi.getRGB (I (vec.elementAt(0)), I (vec.elementAt(1)))).getGreen();
				int b = new Color (bi.getRGB (I (vec.elementAt(0)), I (vec.elementAt(1)))).getBlue();
				this.targetpixelcolor = new Color (r, g, b);*/
			}
		}
	}
	private Integer I (String str) {
		return Integer.parseInt (str);
	}
	private boolean belongsToCoordSet (int x, int y, Vector <Coordinate> coords) {
		int size = coords.size();
		for (int i = 0; i < size; i++) {
			if (coords.elementAt(i).getX() == x && coords.elementAt(i).getY() == y) {
				return true;
			}
		}
		return false;
	}

	private BufferedImage replaceColorOnBufferedImage (
			int oldred, int oldgreen, int oldblue
			, int newred, int newgreen, int newblue
			, BufferedImage bi
	) {
		return replaceColorOnBufferedImage (
				oldred, oldgreen, oldblue, newred, newgreen, newblue, bi, new Vector <Coordinate> ()
		);
	}
	public BufferedImage uniformizeColorOnBufferedImage (Color c, int rrange, int grange, int brange, BufferedImage bi) {
		return replaceIntervalOnBufferedImage (
				c.getRed(), c.getGreen(), c.getBlue()
				, rrange, grange, brange
				, c.getRed(), c.getGreen(), c.getBlue()
				, bi
				, new Vector <Coordinate> ()
		);
	}
	private boolean condrange (Color previousc, Color c, int rrange, int grange, int brange, int i, int j, boolean relaxed, boolean horizontal) {
		boolean condred = previousc.getRed() + rrange <= c.getRed();// || previousc.getRed() >= c.getRed() - rrange;
		boolean condgreen = previousc.getGreen() + grange <= c.getGreen();// + rrange || previousc.getGreen() >= c.getGreen() - rrange;
		boolean condblue = previousc.getBlue() + brange <= c.getBlue();// + rrange || previousc.getBlue() >= c.getBlue() - rrange;
		boolean minicond = horizontal? j != 0 : i != 0;
		if (!relaxed)
			return (condred && condgreen && condblue) && minicond;
		else
			return (condred) && minicond;
	}
	public BufferedImage smoothBufferedImage (
			int range
			, BufferedImage bi
			, boolean relaxed
			, boolean horizontal
	) {
		return smoothBufferedImage (range, range, range, bi, relaxed, horizontal, new Vector <Coordinate> ());
	}
	public BufferedImage smoothRedBufferedImage (
			int range
			, BufferedImage bi
			, boolean relaxed
			, boolean horizontal
	) {
		return smoothBufferedImage (range, 0, 0, bi, relaxed, horizontal, new Vector <Coordinate> ());
	}
	public BufferedImage smoothBufferedImage (
			int rrange, int grange, int brange
			, BufferedImage bi
			, boolean relaxed
			, boolean horizontal
			, Vector <Coordinate> coordexceptions
	) {
		int xmax = bi.getWidth();
		int ymax = bi.getHeight();
		int aux = 0;
		System.out.println (xmax + " " + ymax);
		BufferedImage bi2;
		bi2 = new BufferedImage (xmax, ymax, BufferedImage.TYPE_INT_RGB);
		Color previouscolor = new Color (bi.getRGB (0, 0));
		if (horizontal) {
			for (int i = 0; i < xmax; i++) {
				for (int j = 0; j < ymax; j++) {
					Color c = new Color(bi.getRGB (i, j));
					if (condrange (previouscolor, c, rrange, grange, brange, i, j, relaxed, horizontal)) {
						Color newcolor = new Color (
							(previouscolor.getRed() + c.getRed())/2
							, ((relaxed? c.getGreen() : previouscolor.getGreen()) + c.getGreen())/2
							, ((relaxed? c.getBlue() : previouscolor.getBlue()) + c.getBlue())/2
						);
						bi.setRGB(i, j, newcolor.getRGB());
						//System.out.println ("cheguei aqui");
					}
					previouscolor = new Color (c.getRed(), c.getGreen(), c.getBlue());
					aux++;
				}
			}
		}
		else {
			for (int j = 0; j < ymax; j++) {
				for (int i = 0; i < xmax; i++) {
					Color c = new Color(bi.getRGB (i, j));
					if (condrange (previouscolor, c, rrange, grange, brange, i, j, relaxed, horizontal)) {
						Color newcolor = new Color (
								(previouscolor.getRed() + c.getRed())/2
								, ((relaxed? c.getGreen() : previouscolor.getGreen()) + c.getGreen())/2
								, ((relaxed? c.getBlue() : previouscolor.getBlue()) + c.getBlue())/2
						);
						bi.setRGB(i, j, newcolor.getRGB());
						//System.out.println ("cheguei aqui");
					}
					previouscolor = new Color (c.getRed(), c.getGreen(), c.getBlue());
					aux++;
				}
			}
		}
		return bi;
	}
	public BufferedImage replaceIntervalOnBufferedImage (
			int oldred1, int oldgreen1, int oldblue1
			, int rrange, int grange, int brange
			, int newred, int newgreen, int newblue
			, BufferedImage bi
			, Vector <Coordinate> coordexceptions
	) {
		int xmax = bi.getWidth();
		int ymax = bi.getHeight();
		int aux = 0;
		System.out.println (xmax + " " + ymax);
		BufferedImage bi2;
		bi2 = new BufferedImage (xmax, ymax, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				Color c = new Color(bi.getRGB (i, j));
				if (
					c.getRed() >= oldred1 - rrange && c.getGreen() >= oldgreen1 - grange && c.getBlue() >= oldblue1 - brange
					&& c.getRed() <= oldred1 + rrange && c.getGreen() <= oldgreen1 + grange && c.getBlue() <= oldblue1 + brange
						//&& !belongsToCoordSet (i, j, coordexceptions)
				) {
					//JOptionPane.showMessageDialog (null, "We found a similar color here... " + oldred + " " + oldgreen + " " + oldblue);
					bi.setRGB(i, j, new Color (newred, newgreen, newblue).getRGB());
					//bi2 = bi;
				}
				aux++;
			}
		}
		return bi;
	}

	private BufferedImage replaceColorOnBufferedImage (
			int oldred, int oldgreen, int oldblue
			, int newred, int newgreen, int newblue
			, BufferedImage bi
			, Vector <Coordinate> coordexceptions
	) {
		int xmax = bi.getWidth();
		int ymax = bi.getHeight();
		int aux = 0;
		System.out.println (xmax + " " + ymax);
		BufferedImage bi2;
		bi2 = new BufferedImage (xmax, ymax, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < xmax; i++) {
			for (int j = 0; j < ymax; j++) {
				Color c = new Color(bi.getRGB (i, j));
				if (c.getRed() == oldred && c.getGreen() == oldgreen && c.getBlue() == oldblue
						//&& !belongsToCoordSet (i, j, coordexceptions)
				) {
					//JOptionPane.showMessageDialog (null, "We found a similar color here... " + oldred + " " + oldgreen + " " + oldblue);
					bi.setRGB(i, j, new Color (newred, newgreen, newblue).getRGB());
					//bi2 = bi;
				}
				aux++;
			}
		}
		return bi;
	}

	private void logicOfConvert () {
		processCommand (sourcepixelarea.getText().toString(), true, this.pce.getBufferedImage());
		processCommand (targetpixelarea.getText().toString(), false, this.pce.getBufferedImage());
		Color s = this.sourcepixelcolor;
		Color t = this.targetpixelcolor;
		if (this.currentcommand == Command.COORD) {
			BufferedImage bi = workOnIntermediatePixels (this.pce.getBufferedImage(), 0, this.pixelmul, "OTHER", this.targetpixelcolor).getBufferedImage();
			Container c = (updateImageOnContentPane (this.getContentPane(), bi, this, this));
			this.setContentPane(c);
		}
		else if (this.currentcommand == Command.RECTANGLE) {
			Vector <String> vec1 = tokenize (this.sourcepixelarea.getText().toString());
			Vector <String> vec2 = tokenize (this.targetpixelarea.getText().toString());
			Coordinate coord1 = new Coordinate (I (vec1.elementAt(1)), I (vec1.elementAt(2)));
			Coordinate coord2 = new Coordinate (I (vec1.elementAt(3)), I (vec1.elementAt(4)));
			BufferedImage bi = 
					this.replaceRectangleByRectangle(
							coord1.getX(), coord1.getY()
							, coord2.getX(), coord2.getY()
							, I (vec2.elementAt(0)), I (vec2.elementAt(1))
							, this.pce.getBufferedImage());
			Container c = (updateImageOnContentPane (this.getContentPane(), bi, this, this));
			this.setContentPane(c);
		}
		else {
			BufferedImage bi = replaceColorOnBufferedImage (
					s.getRed(), s.getGreen(), s.getBlue()
					, t.getRed(), t.getGreen(), t.getBlue()
					, this.pce.getBufferedImage()
				);
				Container c = (updateImageOnContentPane (this.getContentPane(), bi, this, this));
				this.setContentPane(c);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().contains ("Save")) {
			try {
				ImageIO.write(this.pce.getBufferedImage(), "png", new File (this.filepath));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand().contains ("Plot Wave")) {
			String str = JOptionPane.showInputDialog ("Value for x axis: ");
			int x = !str.equals("all")? Integer.parseInt (str) : 0;
			if (str.equals("all"))
				this.pce.generateGraphicsForAllPositions(this.pce.getBufferedImage(), true);
			else
				this.pce.generateGraphics(this.pce.getBufferedImage(), x, true);
		}
		else if (e.getActionCommand().contains ("Smooth")) {
			Vector <String> vec = tokenize (this.smoothrange.getText().toString());
			boolean relaxed = false;
			if (vec.elementAt(0).equals ("relaxed")) {
				BufferedImage bi = this.smoothRedBufferedImage(I (vec.elementAt(1)), this.pce.getBufferedImage(), true, true);
				Container cont = (updateImageOnContentPane (this.getContentPane(), bi, this, this));
				this.setContentPane(cont);
			}
			else if (vec.elementAt(0).equals ("relaxedvertical")) {
				BufferedImage bi = this.smoothBufferedImage(I (vec.elementAt(1)), this.pce.getBufferedImage(), true, false);
				Container cont = (updateImageOnContentPane (this.getContentPane(), bi, this, this));
				this.setContentPane(cont);
			}
			else if (vec.elementAt(0).equals ("vertical")) {
				BufferedImage bi = this.smoothBufferedImage(I (vec.elementAt(1)), this.pce.getBufferedImage(), false, false);
				Container cont = (updateImageOnContentPane (this.getContentPane(), bi, this, this));
				this.setContentPane(cont);
			}
			else {
				BufferedImage bi = this.smoothBufferedImage(I (vec.elementAt(0)), this.pce.getBufferedImage(), false, true);
				//bi = this.smoothBufferedImage(I (vec.elementAt(0)), this.pce.getBufferedImage(), false, false);
				Container cont = (updateImageOnContentPane (this.getContentPane(), bi, this, this));
				this.setContentPane(cont);
			}
			//Color c = new Color (I (vec.elementAt(0)), I (vec.elementAt(1)), I (vec.elementAt(2)));
		}
		else if (e.getActionCommand().contains ("Convert")) {
			logicOfConvert ();
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Component image = e.getComponent();
		int x = e.getX();
		int y = e.getY();
		System.out.println ("Mouse clicked");
		
		/**New Logic below*/
		Coordinate coord = new Coordinate (x, y);
		this.insertedpixels.append (this.currentinspixel.toString().substring (this.currentinspixel.toString().indexOf("Color")) + "\n");

		/**if (this.pc == PixelCategory.SOURCE) {
			Coordinate coord = new Coordinate (x, y);
			this.sourcepixel = coord;
			this.sourcepixelwaschosen = true;
			JOptionPane.showMessageDialog(null, coord.coordToString() + " chosen. Great! Now, please choose the first edge!");
			this.pc = PixelCategory.EDGE1;
		}
		else if (this.pc == PixelCategory.EDGE1) {
			this.edgepixel1 = new Coordinate (e.getX(), e.getY());
			JOptionPane.showMessageDialog(null, this.edgepixel1.coordToString() + " chosen. Great! Now, please choose the second edge!");
			this.pc = PixelCategory.EDGE2;
		}
		else {
			this.edgepixel2 = new Coordinate (e.getX(), e.getY());
			JOptionPane.showMessageDialog(null, this.edgepixel2.coordToString() + " chosen. Great! Now, let's process the resulting figure...");
			BufferedImage newimage = this.replaceIntervalByColor(edgepixel1, edgepixel2, sourcepixel, this.pce.getBufferedImage());
			this.pce = new PixelCoordEnv (newimage);
			this.setContentPane(CustomEditing.updateImageOnContentPane(this.getContentPane(), this.pce.getBufferedImage()));
			this.pc = PixelCategory.SOURCE;
		}*/
		/**if (this.sourcepixelwaschosen) {
			JOptionPane.showMessageDialog (null, "Source Pixel Already Chosen");
		}
		else {
			Coordinate coord = new Coordinate (x, y);
			this.sourcepixelcoord = coord;
			this.sourcepixelwaschosen = true;
		}*/
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		System.out.println ("Mouse pressed");
		this.mouseprevstate = MousePreviousState.RECPRESSED;
		//this.edgepixel1 = new Coordinate (e.getX(), e.getY());
		/**if (this.mouseprevstate == MousePreviousState.RECDRAGGED) {
			int x2 = this.edgepixel2.getX() - this.edgepixel1.getX();
			int y2 = this.edgepixel2.getY() - this.edgepixel1.getY();
			this.sourcepixelarea.setText (
					"rectangle " + e.getX() + " " + e.getY() + " " + (e.getX() + x2) + " " + (e.getY() + y2) + ""
			);
		}*/
	}

	public void mouseReleased(MouseEvent e) {
		if (this.mouseprevstate == MousePreviousState.RECDRAGGED) {
			this.edgepixel2 = new Coordinate (e.getX(), e.getY());
			/*this.sourcepixelarea.setText (
					"rectangle "
					+ this.edgepixel1.getX() + " "
					+ this.edgepixel1.getY() + " "
					+ this.edgepixel2.getX() + " "
					+ this.edgepixel2.getY() + " "
			);*/
			int x2 = this.edgepixel2.getX() - this.edgepixel1.getX();
			int y2 = this.edgepixel2.getY() - this.edgepixel1.getY();
			this.coorddif = new Coordinate (x2, y2);
			this.sourcepixelarea.setText (
					"rectangle x y " + "(x + " + x2 + ") (" + "y + " + y2 + ")"
			);
			this.targetpixelarea.setText (this.edgepixel1.getX() + " " + this.edgepixel1.getY());
			this.mouseprevstate = MousePreviousState.RECRELEASED;
		}
		else if (isValidCoord (this.coorddif)) { //Here, we check if our intention is really to paste a selected area
			//TODO
			this.sourcepixelarea.setText (
				"rectangle " + e.getX() + " " + e.getY() + " " + 
								(e.getX() + this.coorddif.getX()) 
						+ " " + (e.getY() + this.coorddif.getY())
						+ ""
			);
			this.coorddif = new Coordinate (-1, -1);
			logicOfConvert ();
		}
		System.out.println ("Mouse released");
		//this.mouseprevstate = MousePreviousState.RECRELEASED;
	}
	private boolean isValidCoord (Coordinate coord) {
		return !(this.coorddif.getX() == -1);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static Container updateImageOnContentPane (Container c, BufferedImage bi, MouseListener o, MouseMotionListener m) {
		Component [] components = c.getComponents();
		System.out.println ("Chamei para " + c.getClass());
		for (int i = 0; i < components.length; i++) {
			if (components [i] instanceof JLabel) {
				Icon icon = ((JLabel)components[i]).getIcon();
				if (icon instanceof ImageIcon) {
					//((JLabel)c.getComponent(i)).setIcon (new ImageIcon (path));
					System.out.println ("We are going to put the changed figure");
					c.remove (i);
					JLabel label = new JLabel (new ImageIcon (bi));
					JScrollPane scr = new JScrollPane (label);
					//JScrollPane label = new JScrollPane (new JLabel (new ImageIcon (bi)));
					label.addMouseListener (o);
					label.addMouseMotionListener (m);
					c.add (label);
				}
				else {
					System.out.println ("Not found");
				}
			}
			/*else {
				c.getComponents()[i] = updateImageOnContentPane ((components[i]), bi, o, m);
			}*/
			else if (components [i] instanceof JPanel) {
				//System.out.println ("JPanel");
				c.getComponents()[i] = updateImageOnContentPane (((JPanel)components[i]), bi, o, m);
			}
			else if (components [i] instanceof JScrollPane) {
				Component [] cc = ((JScrollPane)components[i]).getComponents();
				for (int j = 0; j < cc.length; j++) {
					if (cc[j] instanceof JLabel) {
						Icon icon = ((JLabel)cc[j]).getIcon();
						if (icon instanceof ImageIcon) {
							//((JLabel)c.getComponent(i)).setIcon (new ImageIcon (path));
							System.out.println ("We are going to (ScrollPane) put the changed figure");
							JLabel label = new JLabel (new ImageIcon (bi));
							//JScrollPane label = new JScrollPane (new JLabel (new ImageIcon (bi)));
							label.addMouseListener (o);
							label.addMouseMotionListener (m);
							JScrollPane scr = new JScrollPane (label);
							cc [j] = scr;
						}
						else {
							System.out.println ("Not found");
						}
					}
				}
			}
		}
		
		return c;
	}

	public static void showFileChooser (String title) {
		JFileChooser fc = new JFileChooser ();
		fc.setDialogTitle (title);
		int us = fc.showSaveDialog(new JFrame (title));
		if (us == JFileChooser.SAVE_DIALOG) {
		    File fileToSave = fc.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
	}
	public static void main (String [] args) {
		showFileChooser ("Save screen");
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if (!(this.mouseprevstate == MousePreviousState.RECDRAGGED)) {
			this.edgepixel1 = new Coordinate (e.getX(), e.getY());
		}
		Coordinate coords = new Coordinate (e.getX(), e.getY());
		Color c = new Color (this.pce.getBufferedImage().getRGB(e.getX(), e.getY()));
		this.pixelcoord.setText ("Coordinates :: " + coords.coordToString());
		this.pixelvalues.setText("Values :: " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		this.currentinspixel = c;
		System.out.println ("Mouse Dragged");
		this.mouseprevstate = MousePreviousState.RECDRAGGED;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Coordinate coords = new Coordinate (e.getX(), e.getY());
		Color c = new Color (this.pce.getBufferedImage().getRGB(e.getX(), e.getY()));
		this.pixelcoord.setText ("Coordinates :: " + coords.coordToString());
		this.pixelvalues.setText("Values :: " + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
		this.currentinspixel = c;
		//System.out.println ("Mouse Moved");
	}
	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		/**
	private String PIXELCOLORMBY = "colorsmultipleof";
	private String PIXELCOORDMBY = "coordsmultipleof";
		 * */
		if (e.getSource().toString().contains (this.PIXELCOORDMBY) 
				|| e.getSource().toString().contains (this.PIXELCOLORMBY)
				|| e.getSource().toString().contains (this.RECTANGLE)
			) {
			//int fstindex = e.getSource().toString().indexOf(this.PIXELCOORDMBY);
			//int lstindex = e.getSource().toString().indexOf(this.PIXELCOORDMBY + this.PIXELCOORDMBY.length());
			this.sourcepixelarea.setFont(new Font ("SansSerif", Font.BOLD, 12));
		}
		/*else if (e.getSource().toString().contains (this.RECTANGLE)) {
			this.sourcepixelarea.get
		}*/
		else {
			this.sourcepixelarea.setFont(new Font ("SansSerif", Font.PLAIN, 12));
		}
	}
}
