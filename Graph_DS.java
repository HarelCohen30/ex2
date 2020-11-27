package ex0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Graph_DS implements graph{
	private static Integer keyCounter=0;
	private HashMap<Integer, node_data> vertices;
	public Collection<node_data> GraphVertices;
	public Collection<node_data> GraphEdges;
	public int mc=0;
	int edgeCounter=0;
	//empty constructor
	public Graph_DS() {
		this.vertices=new HashMap<Integer, node_data>();
		this.GraphVertices =new ArrayList <node_data>();
		this.GraphEdges = new ArrayList <node_data>();
		this.edgeCounter = 0;
	}
//constructor that initializes all parameter for copy function
	public Graph_DS(Collection<node_data> NewGraphVertices, Collection<node_data> NewGraphEdges) {
		this.GraphVertices =NewGraphVertices;
		this.GraphEdges = NewGraphEdges;
		this.edgeCounter = this.GraphEdges.size();
		this.vertices=new HashMap<Integer, node_data>();
		for(node_data verts: GraphVertices) {
			vertices.put(keyCounter,verts);
		}
	}
	//constructor that initializes HashMap
	public Graph_DS(HashMap<Integer, node_data> vertices) {
		this.vertices=vertices;
	}
	@Override
	public node_data getNode(int key) {
		if(vertices.get(key)!=null)
			return vertices.get(key) ;
		else return null;

	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		if(vertices.get(node1)==null ||vertices.get(node2)==null || node1==node2) return false; 
		if(vertices.get(node1).hasNi(node2) && (vertices.get(node2).hasNi(node1)) ) return true;
		return false;
	}


	@Override
	public void addNode(node_data n) {
		if(n!=null) {
			vertices.put(n.getKey(), n);
			mc++;
		}
	}

	@Override
	public void connect(int node1, int node2) {
		if(vertices.containsKey(node1) && vertices.containsKey(node2) && node1!=node2 ){
			if(!hasEdge(node1, node2)) {
				vertices.get(node1).addNi(vertices.get(node2));
				vertices.get(node2).addNi(vertices.get(node1));
				edgeCounter++;
				mc++;
			}		
		}
	}
	@Override
	public Collection<node_data> getV() {
		return vertices.values();
	}

	@Override
	public Collection<node_data> getV(int node_id) {
		return vertices.get(node_id).getNi();
	}

	@Override
	public node_data removeNode(int key) {
		if(vertices.containsKey(key)) {
			node_data nodeToRemove=vertices.get(key);
			Collection<node_data> neiborsOfDelete= nodeToRemove.getNi();
			for(node_data neighbor: neiborsOfDelete) {
				neighbor.removeNode(nodeToRemove);
				edgeCounter--;
			}
			vertices.remove(key);
			neiborsOfDelete.clear();
			mc++;
			return nodeToRemove;
		}
		else return null;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		if(node1!=node2 && getNode(node1)!=null && getNode(node2)!=null && hasEdge(node1,node2)) {
			vertices.get(node1).removeNode(getNode(node2));
			vertices.get(node2).removeNode(getNode(node1));
			edgeCounter--;
			mc++;
		}
	}

	@Override
	public int nodeSize() {
		return vertices.size();
	}

	@Override
	public int edgeSize() {
		return edgeCounter;
	}

	@Override
	public int getMC() {
		return mc;
	}


}
