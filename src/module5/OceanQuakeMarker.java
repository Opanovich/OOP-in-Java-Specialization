package module5;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	private boolean clicked;
	private List<AbstractMarker> threatCities;
	private static UnfoldingMap map;
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		threatCities = new ArrayList<>();
		// setting field in earthquake marker
		isOnLand = false;
	}
	
	public static void setMap(UnfoldingMap map) {
		OceanQuakeMarker.map = map;
	}
	
	public void addThreat(AbstractMarker city) {
		if (!threatCities.contains(city)) {
			threatCities.add(city);
		}
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void setClicked(boolean set) {
		clicked = set;
	}
	
	/** Draw the earthquake as a square */
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.rect(x-radius, y-radius, 2*radius, 2*radius);
		if (clicked) {
			for (AbstractMarker city : threatCities) {
				ScreenPosition pos = city.getScreenPosition(map);
				pg.stroke(pg.color(200, 0, 0));
				pg.line(x, y, pos.x - 200, pos.y - 50);
			}
		}
		pg.popStyle();
	}
}
