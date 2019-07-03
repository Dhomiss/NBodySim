import com.teajey.math.Vector2D;

public class Force {
	private Vector2D pos;
	private double scale;
	private double dist;
	
	public Force(double x, double y, double s, double d) {
		this.pos = new Vector2D(x, y);
		this.scale = s;
		this.dist = d;
	}
	
	public Force(double x, double y, double s) {
		this(x, y, s, 2);
	}

	public double getScale() {
		return this.scale;
	}

	public Vector2D getPos() {
		return this.pos;
	}

	public double getPosX() {
		return this.pos.x;
	}

	public double getPosY() {
		return this.pos.y;
	}

	public double getDist() {
		return this.dist;
	}
}
