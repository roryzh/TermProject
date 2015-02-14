import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * TODO Maintain a adjacency list which stores places' information
 *
 * @author zhuangr. Created Feb 10, 2015.
 */
public class AdjList {

	public HashMap<String, Place> adj = new HashMap<String, Place>();

	// public Place p;

	public AdjList() {

		try {
			BufferedReader readposition = new BufferedReader(new FileReader("position"));
			int numberOfCities = Integer.parseInt(readposition.readLine().toString());
			for (int i = 0; i < numberOfCities; i++) {
			this.addPlace(readposition.readLine().toString(),
					Integer.parseInt(readposition.readLine().toString()),
					Integer.parseInt(readposition.readLine().toString()),
					Float.parseFloat(readposition.readLine().toString()));
			}
			
			BufferedReader read = new BufferedReader(new FileReader("cities"));
			int numberOfedges = Integer.parseInt(read.readLine().toString());
			for (int i = 0; i < numberOfedges; i++) {
				this.neighbors(read.readLine().toString(), 
						read.readLine().toString(), 
						Integer.parseInt(read.readLine().toString()), 
						Float.parseFloat(read.readLine().toString()),
						Double.parseDouble(read.readLine().toString()));
			}

		} catch (IOException error) {
			// this.console.setText("File not Found");
		}

	}

	/**
	 * TODO Add the new place into the adjacency list
	 * 
	 * @param newPlace
	 *            New place to be added
	 *
	 */

	public void addPlace(String newPlace,int x, int y,float rate) {
		if (this.adj.containsKey(newPlace) == false) {
			this.adj.put(newPlace, new Place(newPlace,x,y, 0,0,0, rate, new HashMap<String,Place>()));
		}
	}

	/**
	 * TODO Add a neighbor place into a existing or not existing place
	 *
	 * @param place
	 *            The original place
	 * @param neighbor
	 *            The neighbor to be added to the place
	 * @param distance
	 *            The distance between these two places
	 * @param time
	 *            The time cost to travel between these two places
	 * @param moneyspend
	 *            The money cost to travel between these two places
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

	class Place {
		public String name;
		public int x;
		public int y;
		public int distance;
		public float time;
		public double moneyspend;
		public float interestrating;
		public HashMap<String, Place> neighboring = new HashMap<String,Place>();

		
		
		public Place(String name,int x, int y, int distance, float time, double moneyspend,float interestrating,
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

	}
}
