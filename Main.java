import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Random rand = new Random();
		Window window = new Window("Sim", 600, 600);
		Simulator sim = new Simulator();
		Scene scene = new Scene();
		switch(1) {
		case 0:
			for (int i = 0; i < 200; i++) {
				scene.addEntity(new Entity(scene, rand.nextDouble() * 0.01, 2 * (rand.nextDouble() - .5), 2 * (rand.nextDouble() - .5), (rand.nextDouble() - .5) * 0.00000001, (rand.nextDouble() - .5) * 0.00000001));
			}
			break;
		case 1:
			scene.addEntities(
					new Entity[] {
						//new Entity(scene, 0.01, 0, 0),
						//new Entity(scene, 0.001, 0, 0.5, 0.00047, 0)//,
						//new Entity(scene, 0.01, 0, 1, 0.00001, 0)
						new Entity(scene, 0.0001, 0, -0.4, 0.000000002, 0),
						new Entity(scene, 0.0001, 0, 0.9, -0.000000001, 0),
						new Entity(scene, 0.02, 0, 0)
						//new Entity(scene, 0.01, 0, 0.4, 0.000000014, 0),
						//new Entity(scene, 0.01, 0, -0.4, -0.000000014, 0)
					}
			);
			break;
		}
		sim.viewOn(window);
		sim.playScene(scene);
		window.viewScene(scene);
		sim.start();
		sim.setPause(true);
	}
}
