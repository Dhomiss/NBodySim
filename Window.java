import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.teajey.math.Vector2D;

public class Window extends JPanel {
	private static final long serialVersionUID = 5547485061485927470L;
	
	public static boolean drawVelocity = false;
	public static boolean drawPaths = true;
	
	private Scene scene;
	private double camZoom;
	private Vector2D camPos;
	
	public Window(String title, int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(null);
		this.setBounds(0, 0, width, height);
		this.setIgnoreRepaint(true);
		this.setFocusable(true);
		
		JFrame frame = new JFrame(title);
		frame.setContentPane(this);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
		enableEvents(KeyEvent.KEY_EVENT_MASK | MouseEvent.MOUSE_EVENT_MASK | MouseEvent.MOUSE_MOTION_EVENT_MASK);
		
		this.camZoom = this.getHeight() * 0.1;
		this.camPos = new Vector2D(5, 5);//this.getWidth() / 2, this.getHeight() / 2);
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (this.scene != null) this.scene.drawGraphics(this.camZoom, this.camPos);
		g.drawImage(this.scene.getImage(), 0, 0, null);
	}
	
	public void viewScene(Scene scene) {
		this.scene = scene;
	}
	
	@Override
	public void processEvent(AWTEvent e) {
		InputHandler.process(e);
	}
	
	public static int drawPos(double pos, double scale, Vector2D pan) {
		return (int)((pos + pan.x) * scale);
	}
}
