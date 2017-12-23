package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.fill(11);
		pg.ellipse(x, y, 5, 5);
		pg.popStyle();		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		// show rectangle with title
		// name city country code
		String name = getCity() + " " + getCountry() + " " + getCode();
		
		pg = AirportMap.getTitleGraphics();
		pg.beginDraw();
		pg.clear();
		
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y-20, pg.textWidth(name) + 6, 15);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x+3, y-20);
		
		pg.endDraw();
		// show routes
		
		
	}
	
	public String getCity() {
		return this.getStringProperty("city");
	}
	
	public String getCountry() {
		return getStringProperty("country");
	}
	
	public String getCode() {
		return getStringProperty("code");
	}
}
