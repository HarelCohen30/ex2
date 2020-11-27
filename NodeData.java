package ex0;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data {

	private int key;
	private String info;
	private int tag;
	//keyCounter to insure uniqeness of each key;
	private static int keyCounter=0;
	
	private HashMap<Integer, node_data> neighbor;
	//empty constructor
	public NodeData() {
		tag=0;
		info=" ";
		this.key=keyCounter;
		keyCounter++;
		neighbor= new HashMap<Integer, node_data>();
	}
	public NodeData(String info, int tag){
		this.info=info;
		this.tag=tag;
		this.key=keyCounter;
		keyCounter++;
	}
	@Override
	public int getKey() {
		return key;
	}

	@Override
	public Collection<node_data> getNi() {
		
		return neighbor.values();
	}

	@Override
	public boolean hasNi(int key) {
		if(neighbor.containsKey(key)) return true;
		return false;
	}

	@Override
	public void addNi(node_data t) {
      	if(t!=null) {
		neighbor.put(t.getKey(), t);
      	}
	}

	@Override
	public void removeNode(node_data node) {
		if(hasNi(node.getKey())) {
		neighbor.remove(node.getKey());
		}
		
		
	}

	@Override
	public String getInfo() {
		
		return info;
	}

	@Override
	public void setInfo(String s) {
		info=s;
	}

	@Override
	public int getTag() {
		
		return tag;
	}

	@Override
	public void setTag(int t) {
		tag=t;
		
	}

}
