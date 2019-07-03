import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputHandler {
private static boolean[] k = new boolean[221];
private static int mX = 0;
private static int mY = 0;
	
	public static void process(AWTEvent e) {
		boolean down = false;
		
        switch (e.getID())
        {
            case KeyEvent.KEY_PRESSED:
                down = true;
            case KeyEvent.KEY_RELEASED:
                k[((KeyEvent) e).getKeyCode()] = down;
                break;
            case MouseEvent.MOUSE_PRESSED:
                down = true;
            case MouseEvent.MOUSE_RELEASED:
                k[((MouseEvent) e).getButton()] = down;
            case MouseEvent.MOUSE_MOVED:
            case MouseEvent.MOUSE_DRAGGED:
                mX = ((MouseEvent) e).getX(); 
                mY = ((MouseEvent) e).getY();
        }
	}
	
	public static boolean keyPressed(char key) {
		return k[KeyEvent.getExtendedKeyCodeForChar(key)];
	}
	
	public static boolean keyCodePressed(int code) {
		return k[code];
	}
	
	public static int mouseX() {
		return mX;
	}
	
	public static int mouseY() {
		return mY;
	}
}
