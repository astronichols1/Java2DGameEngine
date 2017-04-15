
public class WorldChanger {
	public int width;
	public int height;
	public int X;
	public int Y;
	public int[][] graphic;
	public String toWorld;
	public WorldChanger(int width, int height, int X, int Y, String toWorld){
		this.width = width;
		this.height = height;
		this.X = X;
		this.Y = Y;
		this.toWorld = toWorld;
		graphic = new int[width][height];
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				graphic[i][j] = (255 << 24) | (200 << 16 ) | (200<<8) | 0;
			}
		}
	}
}
