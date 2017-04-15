import java.awt.Color;
import java.awt.image.BufferedImage;

public class Player {
	int height = 40;
	int width = 40;
	double X;
	double Y;
	int[][] graphic = new int[width][height];
	double moveSpeed = 2;
	
	public Player(double X, double Y){
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				int argb = (90 << 24) | (255 << 16 ) | (255<<8) | 255;
				graphic[i][j] = argb;
			}
		}
		this.X = X;
		this.Y = Y;
	};
	
	public void updatePosition(double x, double y){
		for(int i=0; i<LayerManager.objects.length; i++){
			System.out.println(X + ":" + LayerManager.objects[i].X);
			if(!LayerManager.objects[i].collision);
			else if(X+width>LayerManager.objects[i].X-LayerManager.ground.X && X+width<LayerManager.objects[i].X+LayerManager.objects[i].width-LayerManager.ground.X &&
			   Y>LayerManager.objects[i].Y-LayerManager.ground.Y && Y<LayerManager.objects[i].Y+LayerManager.objects[i].height-LayerManager.ground.Y) {X--; return;}
			
			else if(X<LayerManager.objects[i].X+LayerManager.objects[i].width-LayerManager.ground.X && X+width>LayerManager.objects[i].X-LayerManager.ground.X &&
			        Y>LayerManager.objects[i].Y-LayerManager.ground.Y && Y<LayerManager.objects[i].Y+LayerManager.objects[i].height-LayerManager.ground.Y) {X++; return;}
			
			else if(Y+width>=LayerManager.objects[i].Y-LayerManager.ground.Y && Y+height<=LayerManager.objects[i].Y+LayerManager.objects[i].height-LayerManager.ground.Y &&
			        X>=LayerManager.objects[i].X-LayerManager.ground.X && X<=LayerManager.objects[i].X+LayerManager.objects[i].width-LayerManager.ground.X) {Y--; return;}
			
			else if(Y<=LayerManager.objects[i].Y+LayerManager.objects[i].height-LayerManager.ground.Y && Y+height>=LayerManager.objects[i].Y-LayerManager.ground.Y &&
			        X>=LayerManager.objects[i].X-LayerManager.ground.X && X<=LayerManager.objects[i].X+LayerManager.objects[i].width-LayerManager.ground.X) {Y++; return;}
			
		}
		for(int i=0; i<LayerManager.wChange.length; i++){
			System.out.println("WLOBJECT");
			if(X+width>LayerManager.wChange[i].X-LayerManager.ground.X && X+width<LayerManager.wChange[i].X+LayerManager.wChange[i].width-LayerManager.ground.X &&
			   Y>LayerManager.wChange[i].Y-LayerManager.ground.Y && Y<LayerManager.wChange[i].Y+LayerManager.wChange[i].height-LayerManager.ground.Y) {X--; return;}
			
			else if(X<LayerManager.wChange[i].X+LayerManager.wChange[i].width-LayerManager.ground.X && X+width>LayerManager.wChange[i].X-LayerManager.ground.X &&
			        Y>LayerManager.wChange[i].Y-LayerManager.ground.Y && Y<LayerManager.wChange[i].Y+LayerManager.wChange[i].height-LayerManager.ground.Y) {X++; return;}
			
			else if(Y+width>=LayerManager.wChange[i].Y-LayerManager.ground.Y && Y+height<=LayerManager.wChange[i].Y+LayerManager.wChange[i].height-LayerManager.ground.Y &&
			        X>=LayerManager.wChange[i].X-LayerManager.ground.X && X<=LayerManager.wChange[i].X+LayerManager.wChange[i].width-LayerManager.ground.X) {Y--; return;}
			
			else if(Y<=LayerManager.wChange[i].Y+LayerManager.wChange[i].height-LayerManager.ground.Y && Y+height>=LayerManager.wChange[i].Y-LayerManager.ground.Y &&
			        X>=LayerManager.wChange[i].X-LayerManager.ground.X && X<=LayerManager.wChange[i].X+LayerManager.wChange[i].width-LayerManager.ground.X) {Y++; return;}
			
		}
		if(X>Draw.width-LayerManager.ground.playerScrollBoundtry && LayerManager.ground.X<LayerManager.ground.width-Draw.width-1 && x>this.X){
			LayerManager.ground.X += moveSpeed;
		}
		else if(X<LayerManager.ground.playerScrollBoundtry && LayerManager.ground.X>0 && x<this.X){
			LayerManager.ground.X += -moveSpeed;
		}
		else if(X<Draw.width-width && X>0) this.X = x;
		//
		if(X>=Draw.width-width) X--;
		if(X<=0) X++;
		//
		if(Y>Draw.height-LayerManager.ground.playerScrollBoundtry && LayerManager.ground.Y<LayerManager.ground.height-Draw.height-1 && y>this.Y){
			LayerManager.ground.Y += moveSpeed;
		}
		else if(Y<LayerManager.ground.playerScrollBoundtry && LayerManager.ground.Y>0 && y<this.Y){
			LayerManager.ground.Y += -moveSpeed;
		}
		else if(Y<Draw.height-height && Y>1) this.Y = y;
		//
		if(Y>=Draw.height-height) Y--;
		if(Y<=1) Y++;
	}
	
	public void move(int key){
		switch(key){
		case 'w':
			//System.out.println(key);
			updatePosition(X, Y+moveSpeed);
			break;
		case 'a':
			//System.out.println(key);
			updatePosition(X-moveSpeed, Y);
			break;
		case 's':
			//System.out.println(key);
			updatePosition(X, Y-moveSpeed);
			break;
		case 'd':
			//System.out.println(key);
			updatePosition(X+moveSpeed, Y);
			break;
		default:
			break;
		}
	}
	
}
