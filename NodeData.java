package api;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data{

	private int key;
	private String info;
	private int tag;
	//keyCounter to insure uniqeness of each key;
	private static int keyCounter=0;
	private HashMap<Integer, node_data> neighbor=new HashMap<Integer, node_data>();
	private double weight;

	public NodeData() {
		tag=0;
		info=" ";
		this.key=keyCounter;
		keyCounter++;
	}
	public NodeData(String info, int tag){
		this.info=info;
		this.tag=tag;
		this.key=keyCounter;
		keyCounter++;
	}

	public int getKey() {
		return key;
	}

	public geo_location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLocation(geo_location p) {
		// TODO Auto-generated method stub

	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double w) {
		this.weight=w;		
	}

	public String getInfo() {
	return info;
	}

	public void setInfo(String s) {
		this.info=s;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int t) {
		this.tag=t;
	}

	
	public Collection<node_data> getNi() {
		return neighbor.values();
	}

	
	public boolean hasNi(int key) {
		if(neighbor.containsKey(key)) return true;
		return false;
	}

	
	public void addNi(node_data t) {
      	if(t!=null) {
		neighbor.put(t.getKey(), t);
      	}
	}

	public void removeNode(node_data node) {
		if(hasNi(node.getKey())) {
		neighbor.remove(node.getKey());
		}	
	}
}
