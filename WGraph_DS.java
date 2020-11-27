package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import ex1.node_info;
import ex1.WGraph_DS.nodeInfo;


public class WGraph_DS implements weighted_graph {

	private HashMap<Integer, node_info> vertices;
	public Collection<node_info> GraphVertices;
	int amountOfEdges = 0;
	int mc = 0;


	//empty constructor
	public WGraph_DS() {
		this.vertices = new HashMap<Integer, node_info>();
		this.GraphVertices = new ArrayList<node_info>();
		this.amountOfEdges = 0;
	}


//constructor for the copy function creating a deep copy
	public WGraph_DS(Collection<node_info> NewGraphVertices) {
		this.GraphVertices = NewGraphVertices;
		this.vertices = new HashMap<Integer, node_info>();
		for (node_info verts : GraphVertices) {
			addNode(verts.getKey());
		}
		this.amountOfEdges = this.getNeighbors().size();
		for (node_info verts : GraphVertices)
		{
			verts.setInfo(verts.getInfo());
			verts.setTag(verts.getTag());
			Iterator<node_info> ite = getV(verts.getKey()).iterator();
			while(ite.hasNext()) {
				node_info N=ite.next();
				connect(verts.getKey(),N.getKey(),((nodeInfo)verts).getWeight(N.getKey()));
			}
		}
	}


	public WGraph_DS(HashMap<Integer, node_info> vertices) {
		this.vertices = vertices;
	}


	public static class nodeInfo implements node_info, Comparable<node_info> {

		private int key;
		private String info;
		private double tag;
		private static int keyCounter = 0;
		public HashMap<Integer, node_info> neighbors = new HashMap<Integer, node_info>();
		//this seconed hashmap corresponds with the first one, so they both hsve the same keys
		public HashMap<Integer, Double> weights = new HashMap<Integer, Double>();


		//empty constructor
		public nodeInfo() {
			tag = 0;
			info = " ";
			this.key = keyCounter;
			keyCounter++;
			neighbors = new HashMap<Integer, node_info>();
			weights = new HashMap<Integer, Double>();
		}

		public nodeInfo(String info, int tag) {
			this.info = info;
			this.tag = tag;
			this.key = keyCounter;
			keyCounter++;
		}

		@Override
		public int getKey() {
			return key;
		}

		@Override
		public String getInfo() {
			return info;
		}

		@Override
		public void setInfo(String s) {
			info = s;
		}

		@Override
		public double getTag() {
			return tag;
		}

		@Override
		public void setTag(double t) {
			tag = t;
		}

		//retrieves the list of neighbors of this node
		public Collection<node_info> getNi() {

			return neighbors.values();
		}

		//checks if node has neighbors
		public boolean hasNi(int key) {
			if (neighbors.containsKey(key)) return true;
			return false;
		}

		//adds this node as a neighbor
		public void addNi(node_info t) {
			if (t != null) {
				double w = 0.0;
				neighbors.put(t.getKey(), t);
			}
		}

		//adds this neighbor to this node
		public void addNi(node_info t, double w) {
			if (t != null) {
				neighbors.put(t.getKey(), t);
				weights.put(t.getKey(), w);
			}
		}

		//removes this node from graph
		public void removeNode(node_info node) {
			if (hasNi(node.getKey())) {
				neighbors.remove(node.getKey());
			}
		}

		//ables to change weight of edge
		public void setWeight(int t, double w) {
			weights.put(t, w);

		}

		//retrieves the specific weight of this edge
		public double getWeight(int n){
			return weights.get(n);
		}

		//retrieves all weights of this nodes neighbors
		public Collection<Double> getWeights() {

			return weights.values();
		}

		//constructor
		public nodeInfo(int key) {

				this.key = key;
			keyCounter=key+1;
			tag = 0;
			info = " ";
			neighbors = new HashMap<Integer, node_info>();
			weights = new HashMap<Integer, Double>();

		}

		@Override
		public int compareTo(node_info node) {
			if (node.getTag() > this.tag) return 1;
			if (node.getTag() < this.tag) return -1;
			else return 0;
		}

	}


	@Override
	public node_info getNode(int key) {
		if (vertices.get(key) != null)
			return vertices.get(key);
		else return null;
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		if (vertices.get(node1) == null || vertices.get(node2) == null || node1 == node2) return false;
		if (((nodeInfo) vertices.get(node1)).hasNi(node2) && (((nodeInfo) vertices.get(node2)).hasNi(node1)))
			return true;
		return false;
	}

	@Override
	public double getEdge(int node1, int node2) {
		if (vertices.get(node1) != null && vertices.get(node2) != null) {
			nodeInfo n = (nodeInfo) vertices.get(node1);
			return n.weights.get(node2);
		} else return 0.0;
	}

	@Override
	public void addNode(int key) {
		if (!vertices.containsKey(key))
			vertices.put(key, new nodeInfo(key));
	}

	//adds a node to the graph
	public void addNode(nodeInfo n) {
		if (!vertices.containsValue(n) && n != null)
			vertices.put(n.getKey(), n);
	}

	@Override
	public void connect(int node1, int node2, double w) {
		if (vertices.containsKey(node1) && vertices.containsKey(node2) && node1 != node2) {
			if (!hasEdge(node1, node2)) {
				((nodeInfo) vertices.get(node1)).addNi(vertices.get(node2), w);
				((nodeInfo) vertices.get(node2)).addNi(vertices.get(node1), w);
				amountOfEdges++;
				mc++;
			} else {
				nodeInfo N1 = (nodeInfo) vertices.get(node1);
				nodeInfo N2 = (nodeInfo) vertices.get(node2);
				N1.setWeight(N2.getKey(), w);
				N2.setWeight(N1.getKey(), w);
			}
		}

	}

	@Override
	public Collection<node_info> getV() {
		return vertices.values();
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		node_info tempNode = getNode(node_id);
		Collection<node_info> nis = ((nodeInfo) tempNode).getNi();
		return nis;
	}

	//retrieves a list all neighbors in the graph
	public Collection<Integer> getNeighbors() {
		Collection<node_info> coll = vertices.values();
		Collection<Integer> counted = new ArrayList<Integer>();
		Collection<Integer> neighbors = new ArrayList<Integer>();
		for (node_info temp : coll) {
			Collection<node_info> collNi = getV(temp.getKey());
			for (node_info next : collNi) {
				if (!counted.contains(next.getKey())) {
					neighbors.add(next.getKey());
				}
			}
			counted.add(temp.getKey());
		}
		return neighbors;
	}

	@Override
	public node_info removeNode(int key) {
		if (vertices.containsKey(key)) {
			nodeInfo nodeToRemove = (nodeInfo) vertices.get(key);
			Collection<node_info> neighborsOfDel = nodeToRemove.getNi();
			for (node_info neighbor : neighborsOfDel) {
				nodeInfo Ni = (nodeInfo) neighbor;
				Ni.removeNode(nodeToRemove);
				amountOfEdges--;
			}
			vertices.remove(key);
			neighborsOfDel.clear();
			mc++;
			return nodeToRemove;
		} else return null;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		if (node1 != node2 && getNode(node1) != null && getNode(node2) != null && hasEdge(node1, node2)) {
			((nodeInfo) vertices.get(node1)).removeNode(getNode(node2));
			((nodeInfo) vertices.get(node2)).removeNode(getNode(node1));
			amountOfEdges--;
			mc++;
		}

	}

	@Override
	public int nodeSize() {
		return vertices.size();
	}

	@Override
	public int edgeSize() {
		return amountOfEdges;
	}

	@Override
	public int getMC() {
		return mc;
	}



public boolean equals(Object G1)
		{
		return equals((WGraph_DS)G1);
		}

public boolean equals(WGraph_DS G1)//checks if the graphs are equals
		{
		WGraph_DS G2=this;
		if(G1==null || G2==null)
		{
		System.err.println("There is nothing in this Graph. it is equal to Null");
		return false;
		}
		if(G1.nodeSize()!=G2.nodeSize())
		{
		System.err.println("These graphs have diffrent amounts of nodes");
		return false;
		}
		if(G1.edgeSize()!=G2.edgeSize())
		{
		System.err.println("These graphs have diffrent amounts of edges");
		return false;
		}
		Iterator<node_info> iteG1=G1.getV().iterator();
		while(iteG1.hasNext() )
		{
		node_info N1=iteG1.next();
		node_info N2=G2.getNode(N1.getKey());
			if(N1==null || N2==null)return false;
			if(!N1.getInfo().equals(N2.getInfo()) )
			{
				System.err.println("Node number"+N1.getKey()+"s info is not identical");
				return false;
			}
			if(N1.getTag()!=N2.getTag())
			{
				System.err.println("Node number"+N1.getKey()+"s tag is not identical");
				return false;
			}
		Iterator<node_info> iteNiG1=G1.getV(N1.getKey()).iterator();

		if(G1.getV(N1.getKey()).size()!=G2.getV(N2.getKey()).size())
		{
		System.err.println("Neighbor Size of node" + N1.getKey() +" is diffrent");
		return false;
		}
		while(iteNiG1.hasNext())
		{
		node_info Ni1=iteNiG1.next();
		node_info Ni2=G2.getNode(Ni1.getKey());
			if(N1==null || N2==null)return false;
			if(!N1.getInfo().equals(N2.getInfo()) )
			{
				System.err.println("Nodes Neighbor, number"+N1.getKey()+"s info is not identical");
				return false;
			}
			if(N1.getTag()!=N2.getTag())
			{
				System.err.println("Node Neighbor, number"+N1.getKey()+"s tag is not identical");
				return false;
			}
		if(G1.getEdge(N1.getKey(), Ni1.getKey())!=G2.getEdge(N2.getKey(), Ni2.getKey()))
		{
		System.err.println("These nodes have diffrent edges ");
		return false;
		}
		}
		}
		return true;

		}

}
