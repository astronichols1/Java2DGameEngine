import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Shade {
	public static int shadeAlpha = 240;
	public static double shadeAlphaMultiplier = shadeAlpha/255.0;
	static int width = Draw.width;
	static int height = Draw.height;
	public static int[][] image = new int[width][height];
	public static int[][] lmBlock = new int[width][height];
	static int red = 0;
	static int green = 0;
	static int blue = 0;
	static int ambient = (shadeAlpha << 24) | (red << 16 ) | (green<<8) | blue;
	public Shade(int shadeAlpha, int red, int green, int blue){
		this.shadeAlpha = shadeAlpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public Shade(){}
	public static void drawShade(){
		ExecutorService exec = Executors.newFixedThreadPool(8);
		try {
			for(int i=0; i<width; i++){
				final int i2 = i;
				exec.submit(new Runnable() {
					@Override
					public void run() {
						for(int j=0; j<height; j++){
							image[i2][j] = (shadeAlpha << 24) | (red << 16 ) | (green<<8) | blue;
							lmBlock[i2][j] = 0;
						}
					}
				});
			}
		} finally {exec.shutdown();}
		try {exec.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);} catch (InterruptedException e) {e.printStackTrace();}
	}
}
