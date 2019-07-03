public class Simulator {
	public static double G = 0.00000000001;
	public static boolean absorption = true;
	
	private Scene scene;
	private Window window;
	
	private boolean running;
	private boolean pause;
	
	public Simulator() {
		this.running = false;
		this.pause = false;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public void playScene(Scene scene) {
		this.scene = scene;
		this.scene.setSimulator(this);
	}

	public void viewOn(Window window) {
		this.window = window;
	}

	public void start() {
		this.running = true;
		long startTime = System.currentTimeMillis();
		long delta = 0;
		
		while(running) {
			startTime = System.currentTimeMillis();
			if (!this.pause) this.scene.update();
			this.window.repaint();
			delta = System.currentTimeMillis() - startTime;
		}
	}

	public Window getWindow() {
		return window;
	}
}
