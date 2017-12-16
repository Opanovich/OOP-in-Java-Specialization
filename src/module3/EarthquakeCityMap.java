package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PFont;
//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
//import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = true;
	
	
	//		Earthquake Magnitude Classes
	//
	// Earthquakes are also classified in categories ranging from minor to great, depending on their magnitude.
	//
	// Class	Magnitude
	// Great	8 or more
	// Major	7 - 7.9
	// Strong	6 - 6.9
	// Moderate	5 - 5.9
	// Light	4 - 4.9
	// Minor	3 -3.9
	
	// Less than this threshold is a great earthquake
	public static final float THRESHOLD_GREAT = 8;
	public static final float THRESHOLD_MAJOR = 7;
	public static final float THRESHOLD_STRONG = 6;
	public static final float THRESHOLD_MODERATE = 5;
	public static final float THRESHOLD_LIGHT = 4;
	public static final float THRESHOLD_MINOR = 3;

	// Font size
	private int fontSize;
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	// private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.atom";

	// font
	PFont myFont;
	
	public void setup() {
		size(950, 600, OPENGL);

		fontSize = 14;
		myFont = createFont("Georgia", fontSize);
		textFont(myFont);
		
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    //earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    
	    for (PointFeature eq : earthquakes) {
	    	markers.add(createMarker(eq));
	    }
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		// System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    // int yellow = color(255, 255, 0);
		// System.out.println(yellow);
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
		marker.setColor(colorOfMagnitude(mag));
	    marker.setRadius(sizeOfMagnitude(mag));
	    
	    // Finally return the marker
	    return marker;
	}
	
	private int colorOfMagnitude(float mag) {
		int colorLevel = (int) map(mag, 2, 8, 10, 255);
		int color = color(colorLevel, 100, 255 - colorLevel);
		return color;
	}
	
	private int sizeOfMagnitude(float mag) {
		return (int) map(mag, 2, 8, 5, 15);
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		int startX = 25;
		int startY = 100;
		fill(255);
		stroke(0);
		rect(startX, startY, 150, 300, 10);
		fill(50);
		text("Earthquake Key", startX + 25, startY + 2 * fontSize);
		
		
		int sizeOfCircle;
		stroke(100);
		
		startY += 5 * fontSize;
		fill(colorOfMagnitude((THRESHOLD_MODERATE + THRESHOLD_STRONG)/2));
		sizeOfCircle = sizeOfMagnitude((THRESHOLD_MODERATE + THRESHOLD_STRONG)/2);
		ellipse(startX + 15, startY - 5, sizeOfCircle, sizeOfCircle);
		fill(0);
		text("moderate 5+", startX + 35, startY);
		
		startY += 2 * fontSize;
		fill(colorOfMagnitude((THRESHOLD_MODERATE + THRESHOLD_LIGHT)/2));
		sizeOfCircle = sizeOfMagnitude((THRESHOLD_MODERATE + THRESHOLD_LIGHT)/2);
		ellipse(startX + 15, startY - 5, sizeOfCircle, sizeOfCircle);
		fill(0);
		text("light 4+", startX + 35, startY);
		
		startY += 2 * fontSize;
		fill(colorOfMagnitude((THRESHOLD_LIGHT + THRESHOLD_MINOR)/2));
		sizeOfCircle = sizeOfMagnitude((THRESHOLD_LIGHT + THRESHOLD_MINOR)/2);
		ellipse(startX + 15, startY - 5, sizeOfCircle, sizeOfCircle);
		fill(0);
		text("minor 3+", startX + 35, startY);
	}
}
