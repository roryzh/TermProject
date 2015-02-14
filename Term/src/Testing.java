import java.util.HashMap;

import org.junit.Test;

public class Testing {

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	@Test
	public void testInitial() {
		AdjList myGPS = new AdjList();
//		for (String key : myGPS.allPlaces().keySet()) {
//			System.out.println(key + " " + myGPS.allPlaces().get(key).time);
//		}
		HashMap<String, AdjList.Place> myMap = myGPS.allPlaces();

		for (String key : myMap.keySet()) {
			System.out.println(key.toString());
		}

	}

	@Test
	public void testNeighbor() {
		AdjList myGPS = new AdjList();
		AdjList.Place IndytoTerre = myGPS.adj.get("Indianapolis").neighboring.get("Terre Haute");
		
		System.out.println(IndytoTerre.name);
		System.out.println(IndytoTerre.distance);
		System.out.println(IndytoTerre.time);
		System.out.println(IndytoTerre.moneyspend);
		System.out.println("\n");
		
		AdjList.Place IndytoNewYork = myGPS.adj.get("Indianapolis").neighboring.get("New York");
		
		System.out.println(IndytoNewYork.name);
		System.out.println(IndytoNewYork.distance);
		System.out.println(IndytoNewYork.time);
		System.out.println(IndytoNewYork.moneyspend);
		
		System.out.println("\n");
		AdjList.Place NewYorktoIndy = myGPS.adj.get("New York").neighboring.get("Indianapolis");
		
		System.out.println(NewYorktoIndy.name);
		System.out.println(NewYorktoIndy.distance);
		System.out.println(NewYorktoIndy.time);
		System.out.println(NewYorktoIndy.moneyspend);
	}
	@Test
	public void testPosition() {
		AdjList myGPS = new AdjList();
		AdjList.Place Indy = myGPS.adj.get("Indianapolis");
		
		System.out.println(Indy.x);
		System.out.println(Indy.y);
		System.out.println(Indy.interestrating);
		System.out.println("\n");
		
		AdjList.Place Terre = myGPS.adj.get("Terre Haute");
		
		System.out.println(Terre.x);
		System.out.println(Terre.y);
		System.out.println(Terre.interestrating);
		
		System.out.println("\n");
		AdjList.Place NewYork = myGPS.adj.get("New York");
		
		System.out.println(NewYork.x);
		System.out.println(NewYork.y);
		System.out.println(NewYork.interestrating);
	}
}
