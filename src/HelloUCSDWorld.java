import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * Hello World!
 * 
 * This is the basic stub to start creating interactive maps.
 */
public class HelloUCSDWorld extends PApplet {

	UnfoldingMap map;

	public void setup() {
		size(800, 600, OPENGL);

		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleTerrainProvider());
		map.zoomAndPanTo(14, new Location(32.881, -117.238)); // UCSD
		background(0);
		
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		
		map.draw();
		fill(200, 200, 200);
		rect(100, 100, 25, 25);
	}
	
	public void mouseClicked() {
		if (mouseX >= 100 && mouseX <= 125) {
			background(200, 200, 200);
		}
	}

}
