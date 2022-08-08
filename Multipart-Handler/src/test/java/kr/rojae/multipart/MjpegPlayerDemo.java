package kr.rojae.multipart;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class MjpegPlayerDemo {

	public static void main(String[] args) throws IOException {
		URL url = new URL(args[0]);
		URLConnection conn = url.openConnection();
		MultipartInput mpm = new MultipartInput(conn.getInputStream(), conn.getContentType());
		Frame frame = new Frame("MjpegPlayerDemo");
		frame.setIgnoreRepaint(true);
		frame.setSize(640, 480);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		PartInput part = mpm.nextPart();
		while (part != null) {
			try {
				InputStream in = part.getInputStream();
				BufferedImage img = ImageIO.read(in);
				Graphics g = frame.getGraphics();
				g.drawImage(img, 0, 0, null);
				g.dispose();
			} catch (IIOException e) {
				System.out.println("Invalid frame: " + e.getMessage());
			}
			part = mpm.nextPart();
		}
	}
}
