package datastruc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * TODO Maintain a adjacency list
 * 
 * 
 * The AdjList itself has a main HashMap which stores all the places we have right now
 * Each place itself has a HashMap which stores all the neighboring places it has.
 *
 * @author zhuangr. Created Feb 10, 2015.
 */
public class AdjList {

	/**
	 * TODO Main HashMap which stores all the cities
	 */
	public HashMap<String, Place> adj = new HashMap<>();

	// public Place p;

	/**
	 * TODO Ceate a Adjacency list from reading files
	 * The first file has all the name of the places and its position
	 * The second file has all the edges from the start to the destination and its cost
	 */
	public AdjList() {

		try {
			BufferedReader readposition = new BufferedReader(new FileReader("cities3"));
			int numberOfCities = Integer.parseInt(readposition.readLine().toString());
			for (int i = 0; i < numberOfCities; i++) {
			this.addPlace(readposition.readLine().toString(),
					Integer.parseInt(readposition.readLine().toString()),
					Integer.parseInt(readposition.readLine().toString()),
					Float.parseFloat(readposition.readLine().toString()));
			}
			
			BufferedReader readEdges = new BufferedReader(new FileReader("edges3"));
			int numberOfEdges = Integer.parseInt(readEdges.readLine().toString());
			for (int i = 0; i < numberOfEdges; i++) {
				this.neighbors(readEdges.readLine().toString(), 
						readEdges.readLine().toString(), 
						Integer.parseInt(readEdges.readLine().toString()), 
						Float.parseFloat(readEdges.readLine().toString()),
						Double.parseDouble(readEdges.readLine().toString()));
			}

		} catch (IOException error) {
			// this.console.setText("File not Found");
		}

	}

	/**
	 * TODO Add the new place into the adjacency list
	 * 
	 * @param newPlace
	 *            New place that need to be added
	 * @param x The x coordinate of the place
	 * @param y The y coordinate of the place
	 * @param rate The interesting rate of the place
	 *
	 */

	public void addPlace(String newPlace,int x, int y,float rate) {
		if (this.adj.containsKey(newPlace) == false) {
			this.adj.put(newPlace, new Place(newPlace,x,y, 0,0,0, rate, new HashMap<String,Place>()));
		}
	}


	/**
	 * TODO Add neighbors of a Place
	 *
	 * @param place Start Position
	 * @param neighbor End Position
	 * @param distance Distance
	 * @param time Travel Time
	 * @param moneyspend MoneySpend
	 */
	public void neighbors(String place, String neighbor,int distance,float time, double moneyspend) {
//		if (this.adj.containsKey(place) == false) {
//			this.adj.put(place, new Place(place, 0,0,0, 0, 0,0, new HashMap<String,Place>()));
//		}
//		if (this.adj.containsKey(neighbor) == false) {
//			this.adj.put(neighbor, new Place(neighbor, 0, 0, 0,0, new HashMap<String,Place>()));
//		}
		this.adj.get(place).addNeighbor(neighbor,this.adj.get(neighbor).x,this.adj.get(neighbor).y, distance, time, moneyspend,this.adj.get(neighbor).interestrating,
				this.adj.get(neighbor).neighboring);
		this.adj.get(neighbor).addNeighbor(place,this.adj.get(place).x,this.adj.get(place).y, distance, time, moneyspend,this.adj.get(place).interestrating,
				this.adj.get(place).neighboring);	
	}

	/**
	 * TODO Check if the place exists
	 *
	 * @param place
	 *            Place to be checked
	 * @return True if the place exists, else false.
	 */
	public boolean hasPlace(String place) {
		if (this.adj.containsKey(place))
			return true;
		return false;
	}

	/**
	 * TODO Chech if a specific edge exists
	 *
	 * @param place
	 *            Place to be checked
	 * @param neighbor
	 *            Neighbor to be checked
	 * @return True if the edge exists, else false.
	 */
	public boolean hasEdge(String place, String neighbor) {
		if (this.adj.containsKey(place))
			return this.adj.get(place).hasEdge(neighbor);
		return false;
	}

	/**
	 * TODO Get all the places in the adjacency list
	 *
	 * @return The hashmap containning all the places
	 */
	public HashMap<String, Place> allPlaces() {
		return this.adj;
	}

	/**
	 * TODO Get all the neighbor of a place
	 *
	 * @param place
	 *            Place to be checked
	 * @return The hashmap containning all the neighbor
	 */
	public HashMap<String, Place> allNeighbor(String place) {
		if (this.adj.containsKey(place))
			return this.adj.get(place).allNeighbor();
		return null;
	}
	/**
	 * TODO Return the x coordinate of the place
	 *
	 * @param place The name of the place
	 * @return The x coordinate of the place
	 */
	public int getX(String place){
		return this.adj.get(place).getX();
	}
	/**
	 * TODO Return the y coordinate of the place
	 *
	 * @param place The name of the place
	 * @return The y coordinate of the place
	 */
	public int getY(String place){
		return this.adj.get(place).getY();
	}
	/**
	 * TODO Get the distance between two places
	 *
	 * @param start Name of the Start Position
	 * @param destination Name of the Destination
	 * @return The distance between the two places
	 */
	public double getDistance(String start, String destination){
		return this.adj.get(start).neighboring.get(destination).distance;
	}
	/**
	 * TODO Get the travel time between two places
	 *
	 * @param start Name of the Start Position
	 * @param destination Name of the Destination
	 * @return The travel time between the two places
	 */
	public double getTravelTime(String start, String destination){
		return this.adj.get(start).neighboring.get(destination).time;
	}
	/**
	 * TODO Get the money spend between two places
	 *
	 * @param start Name of the Start Position
	 * @param destination Name of the Destination
	 * @return The money spend between the two places
	 */
	public double getMoneyspend(String start, String destination){
		return this.adj.get(start).neighboring.get(destination).moneyspend;	
	}
	/**
	 * TODO Get the interesting rate of the place
	 *
	 * @param place Name of the place
	 * @return Interesting Rate
	 */
	public float getRate(String place){
		return this.adj.get(place).getRate();
	}
	
	
	

//	class Place implements Comparable<Place>{
	class Place{
		public String name;
		public int x;
		public int y;
		public double distance;
		public double time;
		public double moneyspend;
		public float interestrating;
		public HashMap<String, Place> neighboring = new HashMap<>();

		
		
		public Place(String name,int x, int y, double distance, double time, double moneyspend,float interestrating,
				HashMap<String, Place> neighboring) {
			this.name = name;
			this.x=x;
			this.y=y;
			this.distance = distance;
			this.time = time;
			this.moneyspend = moneyspend;
			this.interestrating= interestrating;
			this.neighboring = neighboring;
		}
		public int getX(){
			return this.x;
		}
		public int getY(){
			return this.y;
		}
		public float getRate(){
			return this.interestrating;
		}
		
		public void addNeighbor(String newNeighbor, int x, int y, int distance, float time,
				double moneyspend,float interestrating, HashMap<String, Place> neighboring) {
			
			Place a = new Place(newNeighbor, x,y,distance, time,
					moneyspend,interestrating, neighboring);
			this.neighboring.put(newNeighbor, a);
			
		}

		public boolean hasEdge(String neighbor) {
			return this.neighboring.containsKey(neighbor);
		}

		public HashMap<String, Place> allNeighbor() {
			return this.neighboring;
		}
//		@Override
//		public int compareTo(Place o) {
//			// TODO Auto-generated method stub.
//			if(this.distance<o.distance)
//				return -1;
//			else if(this.distance==o.distance)
//				return 0;
//			else 
//				return 1;
//		}

	}
}
