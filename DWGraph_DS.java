package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class DWGraph_DS implements directed_weighted_graph {

	private HashMap<Integer, node_data> src_vertices;
	private HashMap<Integer, node_data> dest_vertices;
	public Collection<node_data> GraphVertices;
	int nodeCounter = 0;
	int edgeCounter=0;
	int mc = 0;

	//empty constructor
	public DWGraph_DS() {
		this.src_vertices = new HashMap<Integer, node_data>();
		this.dest_vertices = new HashMap<Integer, node_data>();
		this.GraphVertices = new ArrayList<node_data>();
	}
	
	//Maurice, Please check this constructor again/////////////////////////////
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
		if (src_vertices.get(key) != null)
			return src_vertices.get(key);
		else if (dest_vertices.get(key) != null)
			return dest_vertices.get(key);
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
		boolean ForCountNodes=false;
		if (!src_vertices.containsValue(n) && n != null)
		{
			src_vertices.put(n.getKey(), n);
			ForCountNodes=true;
		}
		if (!dest_vertices.containsValue(n) && n != null)
		{
			dest_vertices.put(n.getKey(), n);
			ForCountNodes=true;
		}
		if (ForCountNodes)//the node added to graph
			nodeCounter++;
	}

	public void connect(int src, int dest, double w) {
		if(src_vertices.containsKey(src) && dest_vertices.containsKey(dest) && src!=dest ){
			if(!hasEdge(src, dest)) {
				((NodeData) src_vertices.get(src)).addNi(dest_vertices.get(dest));
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
		if (src_vertices.size() > dest_vertices.size())
		{
			return src_vertices.values();
		}
		return dest_vertices.values();
	}

	public Collection <edge_data> getE(int node_id) {
		NodeData src = (NodeData) src_vertices.get(node_id);
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

	//Maurice, Please check this method again/////////////////////////////
	public node_data removeNode(int key){
		if (src_vertices.containsKey(key))
		{
			NodeData nodeToRemove = (NodeData) src_vertices.get(key);
			Collection<node_data> neighborsOfDel = nodeToRemove.getNi();
			for (node_data neighbor : neighborsOfDel)
			{
				NodeData Ni = (NodeData) neighbor;
				Ni.removeNode(nodeToRemove);
				edgeCounter--;
			}
			NodeData nodeToRemove2 = (NodeData) dest_vertices.get(key);
			Collection<node_data> neighborsOfDel2 = nodeToRemove2.getNi();
			for (node_data neighbor : neighborsOfDel2)
			{
				NodeData Ni = (NodeData) neighbor;
				Ni.removeNode(nodeToRemove2);
				edgeCounter--;
			}
			src_vertices.remove(key);
			dest_vertices.remove(key);
			neighborsOfDel.clear();
			neighborsOfDel2.clear();
			mc++;
			return nodeToRemove;
		} 
		else return null;

	}

	public edge_data removeEdge(int src, int dest) {
		if (src != dest && getNode(src) != null && getNode(dest) != null && hasEdge(src, dest)) {
			((NodeData) src_vertices.get(src)).removeNode(getNode(dest));
			edgeCounter--;
			mc++;
		}
		return null;
	}

	public int nodeSize() {
		return nodeCounter;
	}

	public int edgeSize() {
		return edgeCounter;
	}

	public int getMC() {
		return mc;
	}

	public boolean hasEdge(int src, int dest) {
		if (src_vertices.get(src) == null || dest_vertices.get(dest) == null || src == dest) return false;
		if (((NodeData) src_vertices.get(src)).hasNi(dest))
			return true;
		return false;
	}

}
