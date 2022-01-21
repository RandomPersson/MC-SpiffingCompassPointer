package pl.org.mensa.rp.mc.SpiffingCompassPointer;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class CrazyCompass implements Runnable {
	public static int steps;
	static double stepangle;
	static double x[], z[];
	
	static {
		redoSteps(42);
	}
	
	int step;
	
	public CrazyCompass(JavaPlugin plugin) {
		step = 0;
	}
	
	public Location nextPosition(Location location) {
		return location.add(x[step], 0, z[step]);
	}

	@Override
	public void run() {
		++step;
		step %= steps;
	}
	
	public static void redoSteps(int steps) {
		CrazyCompass.steps = steps;
		stepangle = 2*Math.PI/steps;
		x = new double[steps];
		z = new double[steps];
		
		for (int i=0; i<steps; ++i) {
			x[i] = 32*Math.cos(i*stepangle);
			z[i] = 32*Math.sin(i*stepangle);
		}
	}
}
