import java.awt.Color;
import java.awt.Graphics;

import com.teajey.math.Vector2D;

public class Entity {
	private Scene scene;
	private Vector2D pos;
	private Vector2D lastPos;
	private Vector2D kineticEnergy;
	private Vector2D vel;
	private double mass;
	private Color color;
	private boolean toBeRemoved;
	
	public Entity(Scene scene, double mass, double x, double y, double dx, double dy) {
		this.scene = scene;
		this.pos = new Vector2D(x, y);
		this.kineticEnergy = new Vector2D(dx, dy);
		this.vel = new Vector2D();
		this.mass = mass;
		this.color = Color.WHITE;
		this.toBeRemoved = false;
	}
	
	public Entity(Scene scene, double mass, double x, double y) {
		this(scene, mass, x, y, 0, 0);
	}
	
	public Entity(Scene scene, double mass) {
		this(scene, mass, 0, 0);
	}
	
	public Entity(Scene scene) {
		this(scene, 10);
	}
	
	public void draw(Graphics g, double scale, Vector2D pan) {
		int drawX = Window.drawPos(this.pos.x, scale, pan);
		int drawY = Window.drawPos(this.pos.y, scale, pan);
		int diameter = (int)(this.getRadius() * 2 * scale);
		g.setColor(this.color);
		g.fillOval(drawX - diameter / 2, drawY - diameter / 2, diameter, diameter);
		if (Window.drawVelocity) {
			g.setColor(Color.GREEN);
			Vector2D magnifiedVel = new Vector2D(this.kineticEnergy.x, this.kineticEnergy.y);
			magnifiedVel.mult(200);
			Vector2D drawVel = new Vector2D(this.pos.x + magnifiedVel.x, this.pos.y + magnifiedVel.y);
			int drawDx = Window.drawPos(drawVel.x, scale, pan);
			int drawDy = Window.drawPos(drawVel.y, scale, pan);
			g.drawLine(drawX, drawY, drawDx, drawDy);
		}
	}
	
	public double getRadius() {
		return Math.sqrt(this.mass / Math.PI) / 2;
	}

	public void update(long delta) {
		this.lastPos.set(this.pos);
		this.vel.set(Vector2D.div(this.kineticEnergy, this.mass));
		this.pos.add(Vector2D.mult(this.vel, delta));
		if (Simulator.absorption) {
			for (Entity e : this.scene.getEntities()) {
				if (this != e && !this.isToBeRemoved() && !e.isToBeRemoved() && this.collidesWith(e)) {
					this.absorb(e);
				}
			}
		}
	}
	
	public void update() {
		this.lastPos = new Vector2D(this.pos.x, this.pos.y);
		this.vel.set(Vector2D.div(this.kineticEnergy, this.mass));
		this.pos.add(this.vel);
		if (Simulator.absorption) {
			for (Entity e : this.scene.getEntities()) {
				if (this != e && !this.isToBeRemoved() && !e.isToBeRemoved() && this.collidesWith(e)) {
					this.absorb(e);
				}
			}
		}
	}
	
	public void absorb(Entity e) {
		if (this.mass >= e.getMass()) {
			//this.pos.set(Vector2D.div(Vector2D.add(this.pos, e.getPos()), 2));
			//this.setMass(this.getMass() + e.getMass());
			double overlappingRadii = Math.abs(this.pos.dist(e.getPos()) - (this.getRadius() + e.getRadius()));
			e.giveMassTo(this, e.getMass() - 4 * Math.PI * Math.pow(e.getRadius() - overlappingRadii, 2));
			//this.scene.removeEntity(e);
		}
	}
	
	public void giveMassTo(Entity e, double amount) {
		double kineticEnergyLost = amount / this.getMass();
		if (amount > this.mass) {
			e.setMass(e.getMass() + this.mass);
			this.setMass(0);
			e.setKineticEnergy(this.getKineticEnergy());
			this.setKineticEnergy(new Vector2D());
		} else {
			e.setMass(e.getMass() + amount);
			this.setMass(this.getMass() - amount);
			e.getKineticEnergy().add(Vector2D.mult(this.getKineticEnergy(), kineticEnergyLost));
			this.kineticEnergy.mult(1 - kineticEnergyLost);
		}
	}

	public void force(Force f) {
		if (!this.pos.equalTo(f.getPos())) {
			Vector2D addendum = /*Vector2D.sub(this.pos, f.getPos());*/new Vector2D(this.pos.x - f.getPosX(), this.pos.y - f.getPosY());
			addendum.setMag(f.getScale() * (1 / Math.pow(this.pos.dist(f.getPos()), /*f.getDist()*/2)));// / this.mass);
			//addendum.setMag(0.0001);
			this.kineticEnergy.add(addendum);
		}
	}
	
	public void bendSpacetime() {
		this.scene.addForce(new Force(this.pos.x, this.pos.y, -Simulator.G * .001));
		//this.scene.addForce(new Force(this.pos.x, this.pos.y, this.mass * Simulator.G, 8));
	}

	public Vector2D getPos() {
		return pos;
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}
	
	public Vector2D getLastPos() {
		return lastPos;
	}

	public Vector2D getVel() {
		return vel;
	}

	public void setVel(Vector2D vel) {
		this.vel = vel;
	}
	
	public void setVel(double x, double y) {
		this.vel.set(x, y);
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
		if (this.mass <= 0) {
			this.scene.removeEntity(this);
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Vector2D getKineticEnergy() {
		return kineticEnergy;
	}

	public void setKineticEnergy(Vector2D kineticEnergy) {
		this.kineticEnergy = kineticEnergy;
	}

	public boolean collidesWith(Entity e) {
		if (this.pos.dist(e.getPos()) < this.getRadius() + e.getRadius()) {
			return true;
		}
		return false;
	}

	public boolean isToBeRemoved() {
		return toBeRemoved;
	}

	public void setToBeRemoved(boolean toBeRemoved) {
		this.toBeRemoved = toBeRemoved;
	}
}
