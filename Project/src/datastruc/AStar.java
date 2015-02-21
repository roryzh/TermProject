package datastruc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class AStar {
	String start;
	AStarNode current = null;
	String destination;
	double distance = 0;
	AdjList cities = new AdjList();
	PriorityQueue<AStarNode> frontier = new PriorityQueue<>();
	Hashtable<String, Double> costsofar = new Hashtable<>();
	ArrayList<AStarNode> camefrom = new ArrayList<>();

	public AStar(String start, String destination) {

		this.start = start;
		this.destination = destination;
		this.frontier.offer(new AStarNode(start, 0.0));
		this.costsofar.put(start, 0.0);
	}

	public AStar(String start, double distance) {

		this.start = start;
		this.frontier.offer(new AStarNode(start, 0.0));
		this.costsofar.put(start, 0.0);
		this.distance = distance;

	}

	public boolean tripPlaner() {
		ArrayList<String> myString = new ArrayList<>();
		PriorityQueue<AStarNode> reversed = new PriorityQueue<AStarNode>(
				this.frontier.size(), Collections.reverseOrder());
		reversed.addAll(this.frontier);

		double newcost = 0.0;

		while (reversed.isEmpty() == false) {

			this.current = reversed.poll();
			// System.out.print(this.current.name);

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
					// System.out.println("currentcost:" + currentcost + "   " +
					// "CurrentToNe:" + acttonext
					// + "   =" + newcost +
					// "  "+"EstimateNtoD:"+nexttoDestination+"  " + priority +
					// " "+this.current.name+" "+next+"\n");

					if (this.camefrom.contains(this.current) != true) {
						myString.add(this.current.name);
						this.camefrom.add(this.current);
					}

				}

			}

		}
		return false;
	}

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
					// if (this.costsofar.containsKey(next) == true) {
					// this.costsofar.replace(next, newcost);
					// }

					this.costsofar.put(next, newcost);

					// int nexttoDestination=Heuristic(next, this.destination);
					// int priority = newcost +nexttoDestination;
					this.frontier.add(new AStarNode(next, priority));
					// System.out.println("currentcost:" + currentcost + "   " +
					// "CurrentToNe:" + acttonext
					// + "   =" + newcost +
					// "  "+"EstimateNtoD:"+nexttoDestination+"  " + priority +
					// " "+this.current.name+" "+next+"\n");
					if (this.camefrom.contains(this.current) != true)
						this.camefrom.add(this.current);

				}

			}

		}
		return false;
	}

	public boolean shortestTimePath() {
		double newtime = 0.0;
		while (this.frontier.isEmpty() == false) {

			this.current = this.frontier.poll();
			// System.out.print(this.current.name);

			if (this.current.name.equals(this.destination)) {

				this.camefrom.add(this.current);
				return true;
			}
			// this.frontier.clear();

			for (String next : this.cities.allNeighbor(this.current.name)
					.keySet()) {

				double currentcost = this.costsofar.get(this.current.name);
				// int acttonext = Heuristic(this.current.name, next);
				double acttonext = this.cities.allNeighbor(this.current.name)
						.get(next).time;
				newtime = currentcost + acttonext;
				// newcost = currentcost + heurtonext;
				double nexttoDestination = Heuristic(next, this.destination);
				double priority = newtime + nexttoDestination;

				if (this.costsofar.containsKey(next) != true
						|| newtime < this.costsofar.get(next)) {
					// if (this.costsofar.containsKey(next) == true) {
					// this.costsofar.replace(next, newcost);
					// }

					this.costsofar.put(next, newtime);

					// int nexttoDestination=Heuristic(next, this.destination);
					// int priority = newcost +nexttoDestination;
					this.frontier.add(new AStarNode(next, priority));
					// System.out.println("currentcost:" + currentcost + "   " +
					// "CurrentToNe:" + acttonext
					// + "   =" + newtime +
					// "  "+"EstimateNtoD:"+nexttoDestination+"  " + priority +
					// " "+this.current.name+" "+next+"\n");
					if (this.camefrom.contains(this.current) != true)
						this.camefrom.add(this.current);

				}

			}

		}
		return false;
	}

	public boolean cheapestPath() {
		double newMoneyCost = 0.0;
		while (this.frontier.isEmpty() == false) {

			this.current = this.frontier.poll();
			// System.out.print(this.current.name);

			if (this.current.name.equals(this.destination)) {

				this.camefrom.add(this.current);
				return true;
			}
			// this.frontier.clear();

			for (String next : this.cities.allNeighbor(this.current.name)
					.keySet()) {

				double currentcost = this.costsofar.get(this.current.name);
				// int acttonext = Heuristic(this.current.name, next);
				double acttonext = this.cities.allNeighbor(this.current.name)
						.get(next).moneyspend;
				newMoneyCost = currentcost + acttonext;
				// newcost = currentcost + heurtonext;
				double nexttoDestination = Heuristic(next, this.destination);
				double priority = newMoneyCost + nexttoDestination;

				if (this.costsofar.containsKey(next) != true
						|| newMoneyCost < this.costsofar.get(next)) {
					// if (this.costsofar.containsKey(next) == true) {
					// this.costsofar.replace(next, newcost);
					// }

					this.costsofar.put(next, newMoneyCost);

					// int nexttoDestination=Heuristic(next, this.destination);
					// int priority = newcost +nexttoDestination;
					this.frontier.add(new AStarNode(next, priority));
					// System.out.println("currentcost:" + currentcost + "   " +
					// "CurrentToNe:" + acttonext
					// + "   =" + newMoneyCost +
					// "  "+"EstimateNtoD:"+nexttoDestination+"  " + priority +
					// " "+this.current.name+" "+next+"\n");
					if (this.camefrom.contains(this.current) != true)
						this.camefrom.add(this.current);

				}

			}

		}
		return false;
	}

	public double Heuristic(String next, String goal) {
		return (Math.abs((this.cities.adj.get(goal).x - this.cities.adj
				.get(next).x)) + Math
				.abs((this.cities.adj.get(goal).y - this.cities.adj.get(next).y)));
		// return Math.sqrt(((this.cities.adj.get(goal).x
		// -this.cities.adj.get(next).x))^2+ ((this.cities.adj.get(goal).y -
		// this.cities.adj.get(next).y))^2);
	}

	public double getTotalTime() {
		double totalTime = 0;
		for (int i = 0; i < this.camefrom.size() - 1; i++)
			totalTime = totalTime
					+ this.cities.adj.get(this.camefrom.get(i).name).neighboring
							.get(this.camefrom.get(i + 1).name).time;
		return totalTime;

	}

	public double getTotalDistance() {
		double totalDistance = 0;
		for (int i = 0; i < this.camefrom.size() - 1; i++)
			totalDistance = totalDistance
					+ this.cities.adj.get(this.camefrom.get(i).name).neighboring
							.get(this.camefrom.get(i + 1).name).distance;
		return totalDistance;

	}

	public double getTotalSpend() {
		double totalSpend = 0;
		for (int i = 0; i < this.camefrom.size() - 1; i++)
			totalSpend = totalSpend
					+ this.cities.adj.get(this.camefrom.get(i).name).neighboring
							.get(this.camefrom.get(i + 1).name).moneyspend;
		return totalSpend;

	}

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
			// TODO Auto-generated method stub.
			if (this.constant < o.constant)
				return -1;
			else if (this.constant == o.constant)
				return 0;
			else
				return 1;
		}

	}

}