package datastruc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * Maintain A* algorithm
 *
 * @author zhuangr.
 *         Created Feb 21, 2015.
 */
public class AStar {
	String start;
	AStarNode current = null;
	String destination;
	double distance = 0;
	AdjList cities = new AdjList();
	PriorityQueue<AStarNode> frontier = new PriorityQueue<>();
	Hashtable<String, Double> costsofar = new Hashtable<>();
	ArrayList<AStarNode> camefrom = new ArrayList<>();

	/**
	 * Constructor for path from start to destination
	 *
	 * @param start The place user wants to start
	 * @param destination The place user wants to stop
	 */
	public AStar(String start, String destination) {

		this.start = start;
		this.destination = destination;
		this.frontier.offer(new AStarNode(start, 0.0));
		this.costsofar.put(start, 0.0);
	}

	/**
	 * Constructor for trip planer
	 *
	 * @param start The place user wants to start
	 * @param distance The distance user wants to travel
	 */
	public AStar(String start, double distance) {

		this.start = start;
		this.frontier.offer(new AStarNode(start, 0.0));
		this.costsofar.put(start, 0.0);
		this.distance = distance;

	}

	/**
	 * Find the most interesting path the users can travel within his distance limit
	 *
	 * @return If the path can be found return true, else false.
	 */
	public boolean tripPlaner() {
		ArrayList<String> myString = new ArrayList<>();
		PriorityQueue<AStarNode> reversed = new PriorityQueue<AStarNode>(
				this.frontier.size(), Collections.reverseOrder());
		reversed.addAll(this.frontier);

		double newcost = 0.0;

		while (reversed.isEmpty() == false) {

			this.current = reversed.poll();
			if (this.getTotalDistance() > this.distance) {
				this.camefrom.remove(this.camefrom.size() - 1);
				return true;
			}

			for (String next : this.cities.allNeighbor(this.current.name)
					.keySet()) {

				double currentcost = this.costsofar.get(this.current.name);
				double acttonext = this.cities.allNeighbor(this.current.name)
						.get(next).interestrating;
				newcost = currentcost + acttonext;

				if (this.costsofar.containsKey(next) != true
						|| newcost > this.costsofar.get(next)) {

					this.costsofar.put(next, newcost);

					if (myString.contains(next) != true)
						reversed.add(new AStarNode(next, newcost));
					if (this.camefrom.contains(this.current) != true) {
						myString.add(this.current.name);
						this.camefrom.add(this.current);
					}

				}

			}

		}
		return false;
	}

	/**
	 * Find the shortest path based on the user required start and destination place.
	 *
	 * @return True if the path is found, else false.
	 */
	public boolean findPath() {
		double newcost = 0.0;
		while (this.frontier.isEmpty() == false) {

			this.current = this.frontier.poll();

			if (this.current.name.equals(this.destination)) {

				this.camefrom.add(this.current);
				return true;
			}

			for (String next : this.cities.allNeighbor(this.current.name)
					.keySet()) {

				double currentcost = this.costsofar.get(this.current.name);
				double acttonext = this.cities.allNeighbor(this.current.name)
						.get(next).distance;
				newcost = currentcost + acttonext;
				// newcost = currentcost + heurtonext;
				double nexttoDestination = Heuristic(next, this.destination);
				double priority = newcost + nexttoDestination;

				if (this.costsofar.containsKey(next) != true
						|| newcost < this.costsofar.get(next)) {
					this.costsofar.put(next, newcost);
					this.frontier.add(new AStarNode(next, priority));
					if (this.camefrom.contains(this.current) != true)
						this.camefrom.add(this.current);

				}

			}

		}
		return false;
	}

	/**
	 * Find the path that uses shortest time based on the user required start and destination place
	 *
	 * @return True if the path is found, else false
	 */
	public boolean shortestTimePath() {
		double newtime = 0.0;
		while (this.frontier.isEmpty() == false) {

			this.current = this.frontier.poll();

			if (this.current.name.equals(this.destination)) {

				this.camefrom.add(this.current);
				return true;
			}

			for (String next : this.cities.allNeighbor(this.current.name)
					.keySet()) {

				double currentcost = this.costsofar.get(this.current.name);
				double acttonext = this.cities.allNeighbor(this.current.name)
						.get(next).time;
				newtime = currentcost + acttonext;
				double nexttoDestination = Heuristic(next, this.destination);
				double priority = newtime + nexttoDestination;

				if (this.costsofar.containsKey(next) != true
						|| newtime < this.costsofar.get(next)) {
					this.costsofar.put(next, newtime);
					this.frontier.add(new AStarNode(next, priority));
					if (this.camefrom.contains(this.current) != true)
						this.camefrom.add(this.current);
				}
			}
		}
		return false;
	}

	/**
	 * Find the path the cost the least based on user required start and destination place.
	 *
	 * @return True if the path is found, else false.
	 */
	public boolean cheapestPath() {
		double newMoneyCost = 0.0;
		while (this.frontier.isEmpty() == false) {

			this.current = this.frontier.poll();
			if (this.current.name.equals(this.destination)) {

				this.camefrom.add(this.current);
				return true;
			}
			for (String next : this.cities.allNeighbor(this.current.name)
					.keySet()) {

				double currentcost = this.costsofar.get(this.current.name);
				double acttonext = this.cities.allNeighbor(this.current.name)
						.get(next).moneyspend;
				newMoneyCost = currentcost + acttonext;
				double nexttoDestination = Heuristic(next, this.destination);
				double priority = newMoneyCost + nexttoDestination;

				if (this.costsofar.containsKey(next) != true
						|| newMoneyCost < this.costsofar.get(next)) {

					this.costsofar.put(next, newMoneyCost);
					this.frontier.add(new AStarNode(next, priority));
					if (this.camefrom.contains(this.current) != true)
						this.camefrom.add(this.current);
				}
			}
		}
		return false;
	}

	/**
	 * Heuristic function to estimate the distance from the place to the destination
	 *
	 * @param next The place 
	 * @param goal The destination
	 * @return The heuristic distance
	 */
	public double Heuristic(String next, String goal) {
//		return (Math.abs((this.cities.adj.get(goal).x - this.cities.adj
//				.get(next).x)) + Math
//				.abs((this.cities.adj.get(goal).y - this.cities.adj.get(next).y)));
		return Math.sqrt((this.cities.adj.get(goal).x - this.cities.adj
				.get(next).x)^2 + (this.cities.adj.get(goal).y - this.cities.adj.get(next).y)^2);
	}

	/**
	 * Get the total time of the path
	 *
	 * @return The total travelling time of the path
	 */
	public double getTotalTime() {
		double totalTime = 0;
		for (int i = 0; i < this.camefrom.size() - 1; i++)
			totalTime = totalTime
					+ this.cities.adj.get(this.camefrom.get(i).name).neighboring
							.get(this.camefrom.get(i + 1).name).time;
		return totalTime;
	}

	/**
	 * Get the total distance of the path
	 *
	 * @return The total distance of the path
	 */
	public double getTotalDistance() {
		double totalDistance = 0;
		for (int i = 0; i < this.camefrom.size() - 1; i++)
			totalDistance = totalDistance
					+ this.cities.adj.get(this.camefrom.get(i).name).neighboring
							.get(this.camefrom.get(i + 1).name).distance;
		return totalDistance;

	}

	/**
	 * Get the total cost of the path
	 *
	 * @return The total travelling spend of the path
	 */
	public double getTotalSpend() {
		double totalSpend = 0;
		for (int i = 0; i < this.camefrom.size() - 1; i++)
			totalSpend = totalSpend
					+ this.cities.adj.get(this.camefrom.get(i).name).neighboring
							.get(this.camefrom.get(i + 1).name).moneyspend;
		return totalSpend;

	}

	/**
	 * Get the total interesting index of the path
	 *
	 * @return The total interesting index of the path
	 */
	public float getTotalInterestingIndex() {
		float totalInterest = 0;
		for (int i = 0; i < this.camefrom.size() - 1; i++)
			totalInterest = totalInterest
					+ this.cities.adj.get(this.camefrom.get(i).name).neighboring
							.get(this.camefrom.get(i + 1).name).interestrating;
		return totalInterest;

	}

	class AStarNode implements Comparable<AStarNode> {

		double constant;
		String name;

		public AStarNode(String name, double constant) {
			this.constant = constant;
			this.name = name;
		}

		@Override
		public int compareTo(AStarNode o) {
			if (this.constant < o.constant)
				return -1;
			else if (this.constant == o.constant)
				return 0;
			else
				return 1;
		}

	}

}