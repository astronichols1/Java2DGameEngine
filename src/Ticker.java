
public class Ticker implements Runnable {

	boolean tickStatus = true;
	
	@Override
	public void run() {
		while(tickStatus){
			Motion.motionExec();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
