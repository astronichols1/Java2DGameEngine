import java.awt.event.KeyEvent;

public class KeyHandler {
	
	
	public static void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyChar());
		Motion.key = (e.getKeyChar());
	}

	public static void keyReleased(KeyEvent e) {
		Motion.releaseKey = (e.getKeyChar());
	}

	public static void keyTyped(KeyEvent e) {

	}
	
}
