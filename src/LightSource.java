import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.Random;

public class LightSource {
	public int brightness;
	public int cast;
	public double X;
	public double Y;
	public boolean castShadows;
	public int[][] lightmap;
	
	public LightSource(int cast, int brightness, double X, double Y, boolean castShadows, int r, int g, int b){
		this.X = X-cast;
		this.Y = Y-cast;
		this.brightness = brightness;
		this.cast = cast;
		this.castShadows = castShadows;
		lightmap = new int[cast*2][cast*2];
		for(int i=0; i<cast; i++){
			for(int j=0; j<cast; j++){
				float cdist = (float)Math.sqrt((i-cast) * (i-cast) + (j-cast) * (j-cast));
				
				float colorMult = (float) (Math.pow(2.718, -cdist*4.0/cast));
				int alpha = 255 - (int) (brightness * colorMult);
				alpha = (alpha>=Shade.shadeAlpha) ? Shade.shadeAlpha : alpha;
				int red = (int) (r * colorMult);
				int blue = (int) (b * colorMult);
				int green = (int) (g * colorMult);
				
				if(cdist<cast) lightmap[i][j] = ((alpha) << 24) | (red << 16 ) | (green<<8) | blue;
				else lightmap[i][j] = ((Shade.shadeAlpha) << 24);
			}
		}
		for(int i=0; i<cast*2; i++){
			for(int j=0; j<cast*2; j++){
				lightmap[cast*2-1-i][cast*2-1-j] = lightmap[i][j];
			}
		}
		for(int i=0; i<cast; i++){
			for(int j=0; j<cast; j++){
				lightmap[cast*2-1-i][j] = lightmap[i][j];
			}
		}
		for(int i=0; i<cast; i++){
			for(int j=0; j<cast; j++){
				lightmap[i][cast*2-1-j] = lightmap[i][j];
			}
		}
	}
	Random r = new Random();
	int flickerDelay = 0;
	private boolean setFlicker = false;
	public int getRandMotion(){
		if(setFlicker){
			if(flickerDelay<=2) {flickerDelay++;}
			else {flickerDelay+=-4; return r.nextInt(16)-8;}
		}
		return 0;
	}
	public void setFlicker(boolean set){
		setFlicker = set;
	}
	
	public int getLightValue(int x, int y){
		if(x<0 || x>=cast*2 || y<0 || y>=cast*2) return Shade.ambient;
		return lightmap[x][y];
	}
	
	public String toString(){
		return X + " : " + Y;
	}
}
