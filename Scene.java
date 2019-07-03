import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import com.teajey.math.Vector2D;

public class Scene {
	private ArrayList<Entity> entities;
	private ArrayList<Entity> entitiesForRemoval;
	private ArrayList<Entity> entitiesToAdd;
	private ArrayList<Force> forces;
	private Simulator sim;
	private BufferedImage image;
	private BufferedImage pathsImage;
	
	public Scene() {
		this.entities = new ArrayList<>();
		this.entitiesForRemoval = new ArrayList<>();
		this.entitiesToAdd = new ArrayList<>();
		this.forces = new ArrayList<>();
		this.image = null;
		this.sim = null;
	}
	
	public void drawGraphics(double scale, Vector2D pan) {
		Graphics g = this.image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.sim.getWindow().getWidth(), this.sim.getWindow().getHeight());
		if (Window.drawPaths) drawPathsImage(scale, pan);
		g.drawImage(this.pathsImage, 0, 0, null);
		for (Entity e : this.entities) {
			e.draw(g, scale, pan);
		}
	}
	
	public void addEntity(Entity e) {
		this.entitiesToAdd.add(e);
	}
	
	public void addEntities(ArrayList<Entity> e) {
		this.entitiesToAdd.addAll(e);
	}
	
	public void addEntities(Entity[] e) {
		this.entitiesToAdd.addAll(Arrays.asList(e));
	}
	
	public void removeEntity(Entity e) {
		e.setToBeRemoved(true);
		this.entitiesForRemoval.add(e);
	}
	
	public void removeEntity(int i) {
		this.entities.get(i).setToBeRemoved(true);
		this.entitiesForRemoval.add(this.entities.get(i));
	}

	public void update(long delta) {
		if (InputHandler.keyCodePressed(1)) {
			//this.addForce(new Force(InputHandler.mouseX(), InputHandler.mouseY(), -100000 * Simulator.G));
			//this.addEntity(new Entity(this, 0.001, InputHandler.mouseX(), InputHandler.mouseY()));
		}
		for (Entity e : this.entities) e.bendSpacetime();
		for (Entity e : this.entities) {
			for (Force f : this.forces) {
				e.force(f);
			}
			e.update(delta);
			/*if (this.entities.indexOf(e) == 0)
				System.out.println(e.getVel());*/
		}
		this.clearForces();
		if (this.entitiesForRemoval.size() > 0) {
			this.entities.removeAll(this.entitiesForRemoval);
			this.entitiesForRemoval.clear();
		}
		if (this.entitiesToAdd.size() > 0) {
			this.entities.addAll(this.entitiesToAdd);
			this.entitiesToAdd.clear();
		}
		//if (this.entities.size() < 2) System.out.println(this.entities);
	}
	
	public void update() {
		if (InputHandler.keyCodePressed(1)) {
			//this.addForce(new Force(InputHandler.mouseX(), InputHandler.mouseY(), -100000 * Simulator.G));
			//this.addEntity(new Entity(this, 0.001, InputHandler.mouseX(), InputHandler.mouseY()));
		}
		for (Entity e : this.entities) e.bendSpacetime();
		for (Entity e : this.entities) {
			for (Force f : this.forces) {
				e.force(f);
			}
			e.update();
			/*if (this.entities.indexOf(e) == 0)
				System.out.println(e.getVel());*/
		}
		this.clearForces();
		if (this.entitiesForRemoval.size() > 0) {
			this.entities.removeAll(this.entitiesForRemoval);
			this.entitiesForRemoval.clear();
		}
		if (this.entitiesToAdd.size() > 0) {
			this.entities.addAll(this.entitiesToAdd);
			this.entitiesToAdd.clear();
		}
		//if (this.entities.size() < 2) System.out.println(this.entities);
	}
	
	public void addForce(Force f) {
		this.forces.add(f);
	}
	
	private void clearForces() {
		this.forces.clear();
	}

	public void setSimulator(Simulator sim) {
		this.sim = sim;
		this.image = new BufferedImage(this.sim.getWindow().getWidth(), this.sim.getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.pathsImage = new BufferedImage(this.sim.getWindow().getWidth(), this.sim.getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
	}
	
	public Simulator getSimulator() {
		return this.sim;
	}
	
	private void drawPathsImage(double scale, Vector2D pan) {
		Graphics g = this.pathsImage.getGraphics();
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, this.sim.getWindow().getWidth(), this.sim.getWindow().getHeight());
		g.setColor(Color.WHITE);
		//g.drawLine(300, 300, 300 + (int)(Math.sin(System.currentTimeMillis() / 100) * 10), 300 + (int)(Math.cos(System.currentTimeMillis() / 100) * 10));
		//System.out.println((int)(Math.sin(System.currentTimeMillis() / 1000) * 10));
		for (Entity e : this.entities) {
			if (!e.getPos().equalTo(e.getLastPos())) {
				int drawX = Window.drawPos(e.getPos().x, scale, pan);
				int drawY = Window.drawPos(e.getPos().y, scale, pan);
				int lastDrawX = Window.drawPos(e.getLastPos().x, scale, pan);
				int lastDrawY = Window.drawPos(e.getLastPos().y, scale, pan);
				g.drawLine(drawX, drawY, lastDrawX, lastDrawY);
				//System.out.printf("drawX: %d, drawY: %d, lastDrawX: %d, lastDrawY: %d\n", drawX, drawY, lastDrawX, lastDrawY);
			}
		}
	}

	public BufferedImage getImage() {
		return image;
	}
	
	public ArrayList<Entity> getEntities() {
		return this.entities;
	}
}
