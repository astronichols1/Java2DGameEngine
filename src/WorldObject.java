import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class WorldObject {
	public int width;
	public int height;
	public int[][] graphic;
	public int shadow = 1;
	public double X;
	public double Y;
	public boolean collision = true;
	public int animFrameIndex;
	public int animFrameCount;
	public boolean animated;
	public ArrayList<int[][]> animFrames;
	public File file;
	public ObjectMotion objectMotion = new ObjectMotion();
	
	public WorldObject(File file, double X, double Y){
		graphic = Operations.readImage(file);
		width = graphic.length;;
		height = graphic[0].length;
		this.X = X;
		this.Y = Y;
		this.file = file;
	}
	
	public WorldObject(File file, double X, double Y, boolean animated){
		graphic = Operations.readImage(file);
		width = graphic.length;;
		height = graphic[0].length;
		this.X = X;
		this.Y = Y;
		this.file = file;
		this.animated = animated;
		ArrayList<BufferedImage> tempFrames = new ArrayList<BufferedImage>();
		animFrames = new ArrayList<int[][]>();
		try{
			ImageReader r = ImageIO.getImageReadersByFormatName("gif").next();
			ImageInputStream s = ImageIO.createImageInputStream(file);
			r.setInput(s);
			int count = r.getNumImages(true);
			for(int i=0; i<count; i++){
				BufferedImage frame = r.read(i);
				tempFrames.add(frame);
			}
		} catch (IOException e) {
		    // An I/O problem has occurred
		}
		
		for(int frInd=0; frInd<tempFrames.size(); frInd++){
			int[][] temp = new int[tempFrames.get(frInd).getWidth()][tempFrames.get(frInd).getHeight()];
			for(int i=0; i<tempFrames.get(frInd).getWidth(); i++){
				for(int j=0; j<tempFrames.get(frInd).getHeight(); j++){
					temp[i][j] = tempFrames.get(frInd).getRGB(i, tempFrames.get(frInd).getHeight()-1-j);
				}
			}
			animFrames.add(temp);
		}
		animFrameCount = tempFrames.size();
	}
	
	public void setCollision(boolean c){
		c = collision;
	}
	
	public String getFileString(){
		return file.toString();
	}
	
	public String toString(){
		return X + " : " + Y;
	}
	
	public void animTick(){
		if(animated){
			graphic = animFrames.get(animFrameIndex);
			if(animFrameIndex<animFrameCount-1) animFrameIndex++;
			else animFrameIndex = 0;
		}
	}
	
}
