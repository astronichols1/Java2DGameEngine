
public class UpdateDraw implements Runnable {

	public boolean drawCondition = true;
	@Override
	public void run() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(drawCondition){
			LayerManager.reDraw();
			Draw.update();
		}
	}

}
