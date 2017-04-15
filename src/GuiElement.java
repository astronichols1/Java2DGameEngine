import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

public class GuiElement {
		public int width;
		public int height;
		public int[][] graphic;
		public double X;
		public double Y;
		public boolean isStatic = false;
		public int animFrameIndex;
		public int animFrameCount;
		public boolean animated;
		
		public GuiElement(String text, Color color, double X, double Y, boolean isStatic){
			this.isStatic = isStatic;
			BufferedImage buff = new BufferedImage(170, 30,
					BufferedImage.TYPE_INT_RGB);

			Graphics graphics = buff.getGraphics();
			graphics.setColor(Color.BLACK);
			graphics.fillRect(0, 0, 200, 50);
			graphics.setColor(color);
			graphics.setFont(new Font("Garamond", Font.ITALIC, 16));
			graphics.drawString(text, 10, 25);
			graphic = new int[buff.getWidth()][buff.getHeight()];
			width = graphic.length;
			height = graphic[0].length;
			this.X = X;
			this.Y = Y;
			for(int i=0; i<width; i++){
				for(int j=0; j<height; j++){
					graphic[i][j] = (buff.getRGB(i, height-1-j)==((255 << 24) | (0 << 16) | (0<<8) | 0)) ? ((0 << 24) | (0 << 16 ) | (0<<8) | 0) : buff.getRGB(i, height-1-j);
				}
			}
		}

		public GuiElement(File file, int numItem, Color color, double X, double Y, boolean isStatic){
			this.isStatic = isStatic;
			width = 48;
			height = 48;
			String text = Integer.toString(numItem);
			int[][] iconBuff = Operations.readImage(file);
			BufferedImage buff = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			Graphics graphics = buff.getGraphics();
			graphics.setColor(Color.BLACK);
			graphics.fillRect(0, 0, 200, 50);
			graphics.setColor(color);
			graphics.setFont(new Font("Colsolas", Font.PLAIN, 12));
			graphics.drawString(text, 0, 9);
			graphic = new int[buff.getWidth()][buff.getHeight()];
			this.X = X;
			this.Y = Y;
			for(int i=0; i<width; i++){
				for(int j=0; j<height; j++){
					graphic[i][j] = iconBuff[i][j];
				}
			}
			for(int i=0; i<width; i++){
				for(int j=36; j<height; j++){
					graphic[i][j] = (buff.getRGB(i, height-1-j)==((255 << 24) | (0 << 16) | (0<<8) | 0)) ? iconBuff[i][j] : buff.getRGB(i, height-1-j);
				}
			}
		}
}
