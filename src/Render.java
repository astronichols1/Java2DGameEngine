import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Render extends UpdateDraw {
	public static void fromArray(int[][] image, double positionX, double positionY){
		int X = (int)Math.round(positionX);
		int Y = (int)Math.round(positionY);
		for(int i=X; i<image.length+X; i++){
			for(int j=Y; j<image[0].length+Y; j++){
				if(i>0 && i<Draw.width &&
				   j>0 && j<Draw.height){
					
					int argb = image[i-X][j-Y];
					int mask = Draw.image.getRGB(i, Draw.height-j);
					int lightMask = Shade.image[i][j];
					
					int alpha = 0xFF & (argb >> 24);
					int red = ((0xFF & ( argb >> 16)));
					int blue = ((0xFF & (argb >> 0 )));
					int green = ((0xFF & (argb >> 8 )));
					
					int Balpha = 0xFF & (mask >> 24);
					int Bred = ((0xFF & ( mask >> 16)));
					int Bblue = ((0xFF & (mask >> 0 )));
					int Bgreen = ((0xFF & (mask >> 8 )));
					
					int imageAlpha = alpha;
					red = (red * alpha / 255) + (Bred * Balpha * (255 - alpha) / (255*255));
					green = (green * alpha / 255) + (Bgreen * Balpha * (255 - alpha) / (255*255));
					blue = (blue * alpha / 255) + (Bblue * Balpha * (255 - alpha) / (255*255));
					alpha = alpha + (Balpha * (255 - alpha) / 255);
					
					int lightAlpha = 0xFF & (lightMask >> 24);
					int lightRed = ((0xFF & ( lightMask >> 16)));
					int lightBlue = ((0xFF & (lightMask >> 0 )));
					int lightGreen = ((0xFF & (lightMask >> 8 )));
					
					red = imageAlpha>5 ? (lightRed * lightAlpha / 255) + (red * alpha * (255 - lightAlpha) / (255*255)) : red;
					green = imageAlpha>5 ? (lightGreen * lightAlpha / 255) + (green * alpha * (255 - lightAlpha) / (255*255)) : green;
					blue = imageAlpha>5 ? (lightBlue * lightAlpha / 255) + (blue * alpha * (255 - lightAlpha) / (255*255)) : blue;
					//alpha = lightAlpha + (alpha * (255 - lightAlpha) / 255);
					
					argb = (alpha << 24) | (red << 16 ) | (green<<8) | blue;
					Draw.setPixel(i, j, argb);
					//System.out.println((Draw.image.getRGB(i, j) >> 24) & 0xFF);
				}
			}
		}
	}
	
	public static void fromArrayNoLight(int[][] image, double positionX, double positionY){
		int X = (int)Math.round(positionX);
		int Y = (int)Math.round(positionY);
		for(int i=X; i<image.length+X; i++){
			for(int j=Y; j<image[0].length+Y; j++){
				if(i>0 && i<Draw.width &&
				   j>0 && j<Draw.height){
					
					int argb = image[i-X][j-Y];
					int mask = Draw.image.getRGB(i, Draw.height-j);
					
					int alpha = 0xFF & (argb >> 24);
					int red = ((0xFF & ( argb >> 16)));
					int blue = ((0xFF & (argb >> 0 )));
					int green = ((0xFF & (argb >> 8 )));
					
					int Balpha = 0xFF & (mask >> 24);
					int Bred = ((0xFF & ( mask >> 16)));
					int Bblue = ((0xFF & (mask >> 0 )));
					int Bgreen = ((0xFF & (mask >> 8 )));
					
					red = (red * alpha / 255) + (Bred * Balpha * (255 - alpha) / (255*255));
					green = (green * alpha / 255) + (Bgreen * Balpha * (255 - alpha) / (255*255));
					blue = (blue * alpha / 255) + (Bblue * Balpha * (255 - alpha) / (255*255));
					alpha = alpha + (Balpha * (255 - alpha) / 255);
					
					argb = (alpha << 24) | (red << 16 ) | (green<<8) | blue;
					Draw.setPixel(i, j, argb);
					//System.out.println((Draw.image.getRGB(i, j) >> 24) & 0xFF);
				}
			}
		}
	}

	public static void fromLargerArray(int[][] image, double positionX, double positionY){
		int X = (int)Math.round(positionX);
		int Y = (int)Math.round(positionY);
		for(int i=1; i<Draw.width; i++){
			for(int j=1; j<Draw.height; j++){
				int argb = image[i+X][j+Y];
				int lightMask = Shade.image[i][j];
				
				int alpha = 0xFF & (argb >> 24);
				int red = ((0xFF & ( argb >> 16)));
				int blue = ((0xFF & (argb >> 0 )));
				int green = ((0xFF & (argb >> 8 )));
				
				int lightAlpha = 0xFF & (lightMask >> 24);
				int lightRed = ((0xFF & ( lightMask >> 16)));
				int lightBlue = ((0xFF & (lightMask >> 0 )));
				int lightGreen = ((0xFF & (lightMask >> 8 )));

				red = (lightRed * lightAlpha / 255) + (red * alpha * (255 - lightAlpha) / (255*255));
				green = (lightGreen * lightAlpha / 255) + (green * alpha * (255 - lightAlpha) / (255*255));
				blue = (lightBlue * lightAlpha / 255) + (blue * alpha * (255 - lightAlpha) / (255*255));
				alpha = lightAlpha + (alpha * (255 - lightAlpha) / 255);
				
				argb = (alpha << 24) | (red << 16 ) | (green<<8) | blue;
				Draw.setPixel(i, j, argb);
				//Draw.setPixel(i, j, Shade.image[i][j]*40000);
			}
		}
	}
	
	public static void updated(){
		Draw.clear();
	}
	
	public static void drawLightCollisions(int[][] image, double positionX, double positionY){
		int X = (int) (positionX);
		int Y = (int) (positionY);
		for(int i=X-1; i<image.length+X+1; i++){
			for(int j=Y-1; j<image[0].length+Y+1; j++){
				if(i>0 && i<Draw.width &&
				   j>0 && j<Draw.height){
					Shade.lmBlock[i][j] = 1;
				}
			}
		}
	}
	
	public static void drawLights(LightSource light, double positionX, double positionY){
		int X = (int) (positionX);
		int Y = (int) (positionY);
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try {
			for (int iS=X; iS<light.lightmap.length+X; iS++) {
				final int i = iS;
				exec.submit(new Runnable() {
					@Override
					public void run() {
						for(int j=Y; j<light.lightmap[0].length+Y; j++){
							if(i>0 && i<Draw.width &&
							   j>0 && j<Draw.height){
								int argb = light.lightmap[i-X][j-Y];
								int lightMask = Shade.image[i][j];
								
								double downscaleBrt = light.castShadows ? 0.7 : 1;
								double downscaleAlph = light.castShadows ? Shade.ambient+light.brightness*0.5 : argb;
								int alpha = (0xFF & ((int)(downscaleAlph) >> 24));
								int red = (int) ((0xFF & ( argb >> 16))*downscaleBrt);
								int blue = (int) ((0xFF & (argb >> 0 ))*downscaleBrt);
								int green = (int) ((0xFF & (argb >> 8 ))*downscaleBrt);
								
								int lightAlpha = 0xFF & (lightMask >> 24);
								int lightRed = (int) ((0xFF & ( lightMask >> 16)));
								int lightBlue = (int) ((0xFF & (lightMask >> 0 )));
								int lightGreen = (int) ((0xFF & (lightMask >> 8 )));

								red = red>lightRed ? red : lightRed;
								green = green>lightGreen ? green : lightGreen;
								blue = blue>lightBlue ? blue : lightBlue;
								alpha = alpha<lightAlpha ? alpha : lightAlpha;
								
								Shade.image[i][j] = (alpha << 24) | (red << 16 ) | (green<<8) | blue;
								//System.out.println((Draw.image.getRGB(i, j) >> 24) & 0xFF);
							}
						}
					}
				});
			}
		} finally {exec.shutdown();}
		try {exec.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);} catch (InterruptedException e) {e.printStackTrace();}
		
		//Draw dynamic shadows
		exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try {
		    for (int sc=0; sc<=light.cast*2; sc+=2) {
		    	final int sc2 = sc;
		        exec.submit(new Runnable() {
		            @Override
		            public void run() {
		            	drawLightShadow(light, light.cast, light.cast, sc2, 0, (int)positionX, (int)positionY);
		    			drawLightShadow(light, light.cast, light.cast, sc2, light.cast*2, (int)positionX, (int)positionY);
		    			drawLightShadow(light, light.cast, light.cast, 0, sc2, (int)positionX, (int)positionY);
		    			drawLightShadow(light, light.cast, light.cast, light.cast*2, sc2, (int)positionX, (int)positionY);
		            }
		        });
		    }
		} finally {
		    exec.shutdown();
		}
		 try {
				exec.awaitTermination(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				System.out.println("ERROR!: MonitorFailed!");
			}
		
		/*if(light.castShadows) for(int sc=0; sc<=light.cast*2; sc+=2){
			drawLightShadow(light, light.cast, light.cast, sc, 0, (int)positionX, (int)positionY);
			drawLightShadow(light, light.cast, light.cast, sc, light.cast*2, (int)positionX, (int)positionY);
			drawLightShadow(light, light.cast, light.cast, 0, sc, (int)positionX, (int)positionY);
			drawLightShadow(light, light.cast, light.cast, light.cast*2, sc, (int)positionX, (int)positionY);
		}*/
	}
	static double time = 0;
	public static void drawLightShadow(LightSource light, int x0, int y0, int x1, int y1, int offX, int offY){
		int dx = Math.abs(x1-x0);
		int dy = Math.abs(y1-y0);
		
		int sx = x0<x1 ? 1 : -1;
		int sy = y0<y1 ? 1 : -1;
		
		int err = dx-dy;
		int e2;
		
		//
		int argb;
		int lightMask;
		
		int alpha;
		int red;
		int blue;
		int green;
		
		int lightAlpha;
		int lightRed;
		int lightBlue;
		int lightGreen;
		//
		
		while(true){
			int lightValue = (x0<0 || x0>=light.cast*2 || y0<0 || y0>=light.cast*2) ? Shade.ambient : light.lightmap[x0][y0];
			if(lightValue==Shade.ambient) break;
			int screenX = x0+offX;
			int screenY = y0+offY;
			if(screenX>=0 && screenX<Shade.image.length && screenY>=0 && screenY<Shade.image[0].length){
				argb = lightValue;
				lightMask = Shade.image[screenX][screenY];
				
				alpha = 0xFF & (argb >> 24);
				red = (0xFF & ( argb >> 16));
				blue = (0xFF & (argb >> 0 ));
				green = (0xFF & (argb >> 8 ));
				
				lightAlpha = 0xFF & (lightMask >> 24);
				lightRed = (0xFF & ( lightMask >> 16));
				lightBlue = (0xFF & (lightMask >> 0 ));
				lightGreen = (0xFF & (lightMask >> 8 ));

				red = red>lightRed ? red : lightRed;
				green = green>lightGreen ? green : lightGreen;
				blue = blue>lightBlue ? blue : lightBlue;
				alpha = alpha<lightAlpha ? alpha : lightAlpha;
				
				Shade.image[screenX][screenY] = (alpha << 24) | (red << 16 ) | (green<<8) | blue;
			}
			
			if(x0 == x1 && y0 == y1) break;

			if(screenX>=0 && screenX<Shade.lmBlock.length && screenY>=0 && screenY<Shade.lmBlock[0].length)
				if(Shade.lmBlock[screenX][screenY] == 1) {break;}
			
			
			e2 = 2*err;
			if(e2> -1*dy){
				err -= dy;
				x0 += sx;
			}
			if(e2<dx){
				err += dx;
				y0 += sy;
			}
		}
	}
}
