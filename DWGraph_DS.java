package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class DWGraph_DS implements directed_weighted_graph {

	private HashMap<Integer, node_data> vertices;
	public Collection<node_data> GraphVertices;
	int amountOfEdges = 0;
	int edgeCounter=0;
	int mc = 0;

	//empty constructor
	public DWGraph_DS() {
		this.vertices = new HashMap<Integer, node_data>();
		this.GraphVertices = new ArrayList<node_data>();
		this.amountOfEdges = 0;
	}


	//constructor for the copy function creating a deep copy
	public DWGraph_DS(Collection<node_data> NewGraphVertices) {
		this.GraphVertices = NewGraphVertices;
		this.vertices = new HashMap<Integer, node_data>();
		for (node_data verts : GraphVertices) {
			addNode(verts);
		}
		this.amountOfEdges = this.getNeighbors().size();
		for (node_data verts : GraphVertices)
		{
			verts.setInfo(verts.getInfo());
			verts.setTag(verts.getTag());
			Iterator<node_data> ite = getV(verts.getKey()).iterator();
			while(ite.hasNext()) {
				node_data N=ite.next();
				connect(verts.getKey(),N.getKey(),((NodeData)verts).getWeight(N.getKey()));
			}
		}
	}



	public node_data getNode(int key) {
		if (vertices.get(key) != null)
			return vertices.get(key);
		else return null;
	}

	public edge_data getEdge(int src, int dest) {
		if(hasEdge(src,dest))
		{
			edge_data edge=new EdgeData(src,dest);
			return edge;
		}
		else return null;
	}

	public void addNode(node_data n) {
		if (!vertices.containsValue(n) && n != null)
			vertices.put(n.getKey(), n);
	}

	public void connect(int src, int dest, double w) {
		if(vertices.containsKey(src) && vertices.containsKey(dest) && src!=dest ){
			if(!hasEdge(src, dest)) {
				((NodeData) vertices.get(src)).addNi(vertices.get(dest));
				edgeCounter++;
				mc++;
			}	
			else {
				EdgeData edge = new EdgeData(src,dest);
				edge.setWeight(w);
			}
		}
	}

	public Collection <node_data> getV() {
		return vertices.values();
	}

	public Collection <edge_data> getE(int node_id) {
		NodeData src = (NodeData) vertices.get(node_id);
		Collection<edge_data> coll = new  ArrayList<edge_data>();
		Iterator<node_data> iter = src.getNi().iterator();
		while (iter.hasNext())
		{
			node_data b = iter.next();
			edge_data edge = new EdgeData(node_id,b.getKey());
			coll.add(edge);
		}
		return coll;
	}


	public node_data removeNode(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	public edge_data removeEdge(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	public int nodeSize() {
		return vertices.size();
	}

	public int edgeSize() {
		return edgeCounter;
	}

	public int getMC() {
		return mc;
	}

	public boolean hasEdge(int node1, int node2) {/////////////////
		if (vertices.get(node1) == null || vertices.get(node2) == null || node1 == node2) return false;
		if (((NodeData) vertices.get(node1)).hasNi(node2) && (((NodeData) vertices.get(node2)).hasNi(node1)))
			return true;
		return false;
	}

}
