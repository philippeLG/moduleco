/*
 * Source File Name: Canevas.java Copyright: Copyright (c)enst-bretagne @author
 * frederic.falempin@enst-bretagne.fr
 * 
 * @version 1.2 august,5, 2002
 * @version 1.4 February, 2004
 */
package modulecoGUI.grapheco; // Level 3 Class
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
//	import java.awt.Color;
import java.awt.Rectangle;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener; // DP 17/10/2003
import java.awt.print.PrinterJob;
import java.awt.print.Printable;
//import java.awt.print.PageFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JComponent;
import modulecoGUI.grapheco.encoders.PngEncoderB;
import modulecoGUI.grapheco.encoders.GIFEncoder;
/**
 * This abstract class can by inherited to create easily CAgentRepresentations
 * using double buffering. Anyway, it makes a popupMenu, whith some useful menu
 * items. You can add your custom menu items, since the variable popupMenu is
 * accessible.
 */
public abstract class CBufferedCanvas extends JComponent
		implements
			MouseListener,
			MouseMotionListener,
			ActionListener,
			Printable {
	protected BufferedImage bufferedImage;
	protected Graphics bufferedImageGraphics;
	/**
	 * The popup menu variable that can be used by inheriting classes in order
	 * to add custom menu items in it.
	 */
	protected PopupMenu popupMenu;
	protected MenuItem menuItemSavePNG, menuItemSaveGIF, menuItemPrint,
			menuItemRepresentation;
	protected Dimension dimension;
	protected CentralControl centralControl;
	private boolean updateSize = true;
	public boolean drawAble = false; // DP 20/08/2002
	public CBufferedCanvas() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this); // DP 17/10/2003
		popupMenu = new PopupMenu();
		add(popupMenu);
		/**
		 * Item: Save Image
		 */
		//menuItemSavePNG = new MenuItem("Save PNG Image");
		//menuItemSavePNG.addActionListener(this);
		//popupMenu.add(menuItemSavePNG);
		menuItemSaveGIF = new MenuItem("Save Image");
		menuItemSaveGIF.addActionListener(this);
		popupMenu.add(menuItemSaveGIF);
		/**
		 * Item: Print Image
		 */
		menuItemPrint = new MenuItem("Print Image");
		menuItemPrint.addActionListener(this);
		popupMenu.add(menuItemPrint);
		/**
		 * Item: Circle/Latice
		 */
		menuItemRepresentation = new MenuItem("Circle/Lattice");
		menuItemRepresentation.addActionListener(this);
		popupMenu.add(menuItemRepresentation);
		//System.out.println("CBufferedCanvas.Constructor");
	}
	protected abstract void draw(Graphics g);
	public void setCentralControl(CentralControl centralControl) {
		this.centralControl = centralControl;
		//System.out.println("CBufferedCanvas.setCentralControl()");
	}
	public void paint(Graphics g) {
		//System.out.println("CBufferedCanvas.paint()");
		// Pas indispensable sauf en multiple graphiques ?
		if (updateSize) {
			updateSize = false;
			bufferedImage = (BufferedImage) createImage(dimension.width,
					dimension.height);
			bufferedImageGraphics = bufferedImage.createGraphics();
			draw(bufferedImageGraphics);
		} else
			draw(bufferedImageGraphics);
		g.drawImage(bufferedImage, 0, 0, this);
	}
	//else
	//System.out.println("CBufferedCanvas.paint()- drawAble ="+drawAble);
	//}
	/*
	 * update an existing image
	 */
	public void updateImage() {
		//System.out.println("updateImage step5 : CBufferedCanvas from EPanel
		// /(graphique)");
		Graphics g = getGraphics();
		//if(drawAble)// DP 20/08/2002)
		paint(g);
	}
	public void resetImage() {
		//System.out.println("CBufferedCanvas.resetImage()");
		updateSize = true;
		repaint();
	}
	public void setSize(Dimension dimension) {
		updateSize = true;
		this.dimension = dimension;
		super.setSize(dimension);
	}
	public void setSize(int x, int y) {
		updateSize = true;
		this.dimension = new Dimension(x, y);
		super.setSize(x, y);
	}
	public void setBounds(int x, int y, int width, int height) {
		updateSize = true;
		this.dimension = new Dimension(width, height);
		super.setBounds(x, y, width, height);
	}
	public void setBounds(Rectangle rectangle) {
		updateSize = true;
		this.dimension = new Dimension(rectangle.width, rectangle.height);
		super.setBounds(rectangle);
	}
	public Dimension getPreferredSize() {
		if (dimension == null)
			return super.getPreferredSize();
		else
			return dimension;
	}
	public Dimension getMinimumSize() {
		return new Dimension(20, 20);
	}
	public Dimension getMaximumSize() {
		return new Dimension(600, 600);
	}
	public int print(Graphics g, java.awt.print.PageFormat pf, int pi)
			throws java.awt.print.PrinterException {
		if (pi >= 1) {
			return Printable.NO_SUCH_PAGE;
		}
		draw((java.awt.Graphics2D) g);
		return Printable.PAGE_EXISTS;
	}
	public void mouseClicked(MouseEvent e) {
		popupMenu.show(this, e.getX(), e.getY());
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
	} // DP 17/10/2003
	public void mouseMoved(MouseEvent e) {
	}// DP 17/10/2003
	/**
	 * Save PNG Image
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuItemSavePNG)) {
			/**
			 * Encode the image.
			 */
			PngEncoderB encoder = new PngEncoderB(bufferedImage);
			/**
			 * Set the output file name and path.
			 * <p>
			 * We store PNG images in /bin/outputs/$MODEL/$MODEL.png
			 * <p>
			 * name = MG
			 * <p>
			 * directory = /bin/outputs/MG
			 * <p>
			 * pathname = /bin/outputs/MG/MG.png
			 */
			String name = centralControl.getEWorld().pack().substring(
					centralControl.getEWorld().pack().lastIndexOf('.') + 1);
			String directoryName = CentralControl.getModulecoPathRoot()
					+ "outputs" + File.separator + name;
			String pathname = directoryName + File.separator + name + ".png";
			/**
			 * Create the file $MODEL.png in /bin/outputs/$MODELS
			 */
			try {
				File output = new File(pathname);
				/**
				 * If the directory /bin/outputs/$MODELS does nos exist, create
				 * it.
				 */
				if (!output.getParentFile().isDirectory())
					output.getParentFile().mkdir();
				/**
				 * If the file $MODEL.png does not exist, create it.
				 */
				if (!output.isFile())
					output.createNewFile();
				/**
				 * Store the PNG picture in the file
				 */
				FileOutputStream fileOutputStream = new FileOutputStream(output);
				fileOutputStream.write(encoder.pngEncode());
				fileOutputStream.flush();
				fileOutputStream.close();
				/**
				 * Display a success message
				 */
				System.out.println("Image " + name + " successfully stored in "
						+ pathname);
			} catch (IOException ex) {
			}
		}
		/**
		 * Save GIF Image
		 */
		if (e.getSource().equals(menuItemSaveGIF)) {
			/**
			 * Encode the image.
			 */
			GIFEncoder encoder = null;
			try {
				encoder = new GIFEncoder(bufferedImage);
			} catch (java.awt.AWTException ex) {
			}
			// centralControl.getFileDialog().setTitle("Save GIF Image - Choose
			// filename");
			// centralControl.getFileDialog().setMode(java.awt.FileDialog.SAVE);
			//centralControl.getJFileChooser().setVisible(true);
			/**
			 * Set the output file name and path.
			 * <p>
			 * We store PNG images in /bin/outputs/$MODEL/$MODEL.gif
			 * <p>
			 * name = MG
			 * <p>
			 * directory = /bin/outputs/MG
			 * <p>
			 * pathname = /bin/outputs/MG/MG.gif
			 */
			String name = centralControl.getEWorld().pack().substring(
					centralControl.getEWorld().pack().lastIndexOf('.') + 1);
			String directoryName = CentralControl.getModulecoPathRoot()
					+ "outputs" + File.separator + name;
			String pathname = directoryName + File.separator + name + ".gif";
			/**
			 * Create the file $MODEL.gif in /bin/outputs/$MODELS
			 */
			try {
				File output = new File(pathname);
				/**
				 * If the directory /bin/outputs/$MODELS does nos exist, create
				 * it.
				 */
				if (!output.getParentFile().isDirectory())
					output.getParentFile().mkdir();
				/**
				 * If the file $MODEL.gif does not exist, create it.
				 */
				if (!output.isFile())
					output.createNewFile();
				/**
				 * Store the PNG picture in the file
				 */
				FileOutputStream fileOutputStream = new FileOutputStream(output);
				encoder.Write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
				/**
				 * Display a success message
				 */
				System.out.println("Image successfully stored in " + pathname);
			} catch (IOException ex) {
			}
		}
		/**
		 * Menu print
		 */
		if (e.getSource().equals(menuItemPrint)) {
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.validatePage(printJob.defaultPage());
			printJob.setPrintable(this, printJob.pageDialog(printJob
					.defaultPage()));
			if (printJob.printDialog()) {
				try {
					printJob.print();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		// Add DP 06/2002
		if (e.getSource().equals(menuItemRepresentation)) {
			//System.out.println ("menuItemRepresentation");
			centralControl.inverseLeftRepresentation();
		}
	}
	public void setDrawAble(boolean d) {// DP 20/08/2002
		//System.out.println("CBufferedCanvas.setDrawAble : "+d);//test DP
		// 20/08/2002
		drawAble = d;
		//resetImage();
	}
}