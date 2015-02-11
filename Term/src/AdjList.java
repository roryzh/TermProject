import java.util.HashMap;


/**
 * TODO Maintain a adjacency list which stores places' information
 *
 * @author zhuangr.
 *         Created Feb 10, 2015.
 */
public class AdjList {
	
	public HashMap<String, Place> adj;
	public Place p;
	
	
	/**
	 * TODO Add the new place into the adjacency list
	 * @param newPlace New place to be added
	 *
	 */
	public void addPlace(String newPlace){
		if(this.adj.containsKey(newPlace)==false){
			this.adj.put(newPlace, new Place(newPlace,0,0,0,null));
		}			
	}
	
	/**
	 * TODO Add a neighbor place into a existing or not existing place
	 *
	 * @param place The original place
	 * @param neighbor The neighbor to be added to the place
	 * @param distance The distance between these two places
	 * @param time The time cost to travel between these two places
	 * @param moneyspend The money cost to travel between these two places
	 */
	public void addNeighbor(String place, String neighbor,int distance, float time, double moneyspend){
		if(this.adj.containsKey(place)==false){
			this.adj.put(place,new Place(place,0,0,0,null));
		}
		if(this.adj.containsKey(neighbor)==false){
			this.adj.put(neighbor,new Place(neighbor,0,0,0,null));
		}
		
		this.adj.get(place).addNeighbor(neighbor, distance, time, moneyspend,this.adj.get(neighbor).neigbouring);
		
	}
	/**
	 * TODO Check if the place exists
	 *
	 * @param place Place to be checked
	 * @return True if the place exists, else false.
	 */
	public boolean hasPlace(String place){
		if(this.adj.containsKey(place))
			return true;
		return false;
	}
	
	/**
	 * TODO Chech if a specific edge exists
	 *
	 * @param place Place to be checked
	 * @param neighbor Neighbor to be checked
	 * @return True if the edge exists, else false.
	 */
	public boolean hasEdge(String place, String neighbor){
		if(this.adj.containsKey(place))
			return this.adj.get(place).hasEdge(neighbor);
		return false;
	}
	
	/**
	 * TODO Get all the places in the adjacency list
	 *
	 * @return The hashmap containning all the places
	 */
	public HashMap<String, Place> allPlaces(){
		return this.adj;
	}	
	/**
	 * TODO Get all the neighbor of a place
	 *
	 * @param place Place to be checked
	 * @return The hashmap containning all the neighbor
	 */
	public HashMap<String, Place> allNeighbor(String place){
		if(this.adj.containsKey(place))
			return this.adj.get(place).allNeighbor();
		return null;
	}
	
	
	class Place{
		String name;
		int distance;
		float time;
		double moneyspend;
		HashMap<String, Place> neigbouring;
		
		public Place(String name, int distance, float time, double moneyspend,HashMap<String, Place> neigbouring){
			this.name=name;
			this.distance=distance;
			this.time=time;
			this.moneyspend=moneyspend;	
			this.neigbouring=neigbouring;
		}
		public void addNeighbor(String newNeighbor, int distance,float time,double moneyspend,HashMap<String, Place> neigbouring){
			this.neigbouring.put(newNeighbor, new Place(newNeighbor,distance,time,moneyspend,neigbouring));
		}
		
		public boolean hasEdge(String neighbor){
			return this.neigbouring.containsKey(neighbor);
		}
		public HashMap<String, Place> allNeighbor(){
			return this.neigbouring;
		}
		
	}
}
