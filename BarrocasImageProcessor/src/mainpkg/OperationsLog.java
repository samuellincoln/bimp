package mainpkg;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class OperationsLog {
	private Vector <PixelCoordEnv> pces;
	public OperationsLog () {
		this.pces = new Vector <PixelCoordEnv> ();
	}
	public Vector <PixelCoordEnv> getPixelCoordEnvs () {
		return pces;
	}
	public PixelCoordEnv pixelCoordEnvAt (int i) {
		return this.pces.elementAt(i);
	}
	public void put (PixelCoordEnv pce) {
		this.pces.addElement (pce);
	}
	/*private Vector <BufferedImage> images;
	private Vector <String> commands;
	public OperationLogs (Vector <BufferedImage> image, Vector <String> commands) {
		if (image.size() != commands.size()) {
			try {
				throw new Exception ("Different sizes... Not accepted");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.images = image;
	}
	public OperationLogs () {
		this (new Vector <BufferedImage> (), new Vector <String> ());
	}
	public BufferedImage getImageAt (int i) {
		return images.elementAt(i);
	}
	public String getCommandAt (int i) {
		return commands.elementAt(i);
	}
	public BufferedImage getCurrentImage () {
		return images.lastElement();
	}
	public String getCurrentCommand () {
		return commands.lastElement();
	}
	public void addOperationLog (BufferedImage bi, String command) {
		this.images.addElement(bi);
		this.commands.addElement(command);
	}*/
}
