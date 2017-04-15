import java.io.File;

public class Ground {
	public int[][] image;
	public int width;
	public int height;
	public double X;
	public double Y;
	public int playerScrollBoundtry = 150;
	
	public Ground(File file, double X, double Y){
		image = Operations.readImage(file);
		width = image.length;
		height = image[0].length;
		this.X = X;
		this.Y = Y;
		System.out.println(width);
		System.out.println(height);
	}
	
	public void loadNewWorld(File file, double X, double Y){
		image = Operations.readImage(file);
		width = image.length;
		height = image[0].length;
		this.X = X;
		this.Y = Y;
	}
}
