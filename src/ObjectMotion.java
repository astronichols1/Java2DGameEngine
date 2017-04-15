
public class ObjectMotion {

	double distanceX = 0;
	double distanceY = 0;
	double movedX = 0;
	double movedY = 0;
	double speed = 0;
	
	public boolean patrol = false;
	
	public void move(double speed, double distanceX, double distanceY){
		this.speed = speed;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}
	public void move(double speed, double distanceX, double distanceY, boolean patrol){
		movedX=0;
		movedY=0;
		this.speed = speed;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		this.patrol = patrol;
	}
	
	public double[] update(){
		double outX = 0;
		if(Math.abs(movedX)<Math.abs(distanceX)){
			if(distanceX<0){
				movedX -= speed * Math.cos(Math.atan(Math.abs(distanceY/distanceX)));
				outX -= speed * Math.cos(Math.atan(Math.abs(distanceY/distanceX)));
			}
			else{
				movedX += speed * Math.cos(Math.atan(Math.abs(distanceY/distanceX)));
				outX += speed * Math.cos(Math.atan(Math.abs(distanceY/distanceX)));
			}
		} 
		else endMotionTrigger();
		double outY = 0;
		if(Math.abs(movedY)<Math.abs(distanceY)){
			if(distanceY<0){
				movedY -= speed * Math.sin(Math.atan(Math.abs(distanceY/distanceX)));
				outY -= speed * Math.sin(Math.atan(Math.abs(distanceY/distanceX)));
			}
			else{
				movedY += speed * Math.sin(Math.atan(Math.abs(distanceY/distanceX)));
				outY += speed * Math.sin(Math.atan(Math.abs(distanceY/distanceX)));
			}
		} 
		double[] out = {outX, outY};
		return out;
	}
	
	private void endMotionTrigger(){
		if(patrol){
			move(-speed, distanceX, distanceY, true);
		}
	}
}
