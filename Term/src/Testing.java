import static org.junit.Assert.assertEquals;

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

		HashMap<String, AdjList.Place> myMap = myGPS.allPlaces();
		System.out.println("All the city we have right now!");
		for (String key : myMap.keySet()) {
			System.out.println(key.toString());
		}
		System.out.println("\n");

	}

	@Test
	public void testNeighbor() {
		AdjList myGPS = new AdjList();
		AdjList.Place IndytoTerre = myGPS.adj.get("Indianapolis").neighboring.get("Terre Haute");
		
		System.out.println("Start Position: "+myGPS.adj.get("Indianapolis").name);
		System.out.println("End Position: "+IndytoTerre.name);
		System.out.println(IndytoTerre.distance);
		System.out.println(IndytoTerre.time);
		System.out.println(IndytoTerre.moneyspend);
		System.out.println("\n");
		
		AdjList.Place IndytoNewYork = myGPS.adj.get("Indianapolis").neighboring.get("New York");
		
		System.out.println("Start Position: "+myGPS.adj.get("Indianapolis").name);
		System.out.println("End Position: "+IndytoNewYork.name);
		System.out.println(IndytoNewYork.distance);
		System.out.println(IndytoNewYork.time);
		System.out.println(IndytoNewYork.moneyspend);
		
		System.out.println("\n");
		AdjList.Place NewYorktoIndy = myGPS.adj.get("New York").neighboring.get("Indianapolis");
		
		System.out.println("Start Position: "+myGPS.adj.get("New York").name);
		System.out.println("End Position: "+NewYorktoIndy.name);
		System.out.println(NewYorktoIndy.distance);
		System.out.println(NewYorktoIndy.time);
		System.out.println(NewYorktoIndy.moneyspend);
	}
	
	@Test
	public void testPositionAndInterest() {
		AdjList myGPS = new AdjList();
		AdjList.Place Indy = myGPS.adj.get("Indianapolis");
		
		assertEquals(30,Indy.getX());
		assertEquals(60,Indy.getY());
		assertEquals(3.10,Indy.getRate(),0.01);
		
		AdjList.Place Terre = myGPS.adj.get("Terre Haute");
		
		assertEquals(40,Terre.getX());
		assertEquals(80,Terre.getY());
		assertEquals(4.1,Terre.getRate(),0.01);
		
		AdjList.Place NewYork = myGPS.adj.get("New York");
		
		assertEquals(50,NewYork.getX());
		assertEquals(100,NewYork.getY());
		assertEquals(5.1,NewYork.getRate(),0.01);
	}
}
