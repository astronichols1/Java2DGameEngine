import java.awt.event.MouseEvent;

public class MouseHandler {
	static int mouseX = 0;
	static int mouseY = 0;
	public static void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}
}
