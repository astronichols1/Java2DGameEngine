import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

public class Operations {
	public static int[][] readImage(File file){
		BufferedImage buff = null;
		try {
			buff = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("ERROR: Could not read image from file: "+file);
			e.printStackTrace();
		}
		int width = buff.getWidth();
		int height = buff.getHeight();
		int[][] imageArray = new int[width][height];
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				imageArray[i][j] = buff.getRGB(i, height-1-j);
			}
		}
		return imageArray;
	}
	
	public static void writeWorldConfigFile(String file, String write){
		try {
			PrintWriter w = new PrintWriter(file, "UTF-8");
			w.print(write);
			w.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
