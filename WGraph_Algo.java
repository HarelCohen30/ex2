package ex1;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;
import java.lang.*;
import ex1.WGraph_DS.nodeInfo;

public class WGraph_Algo implements weighted_graph_algorithms {
	int nodeCounter=0;
	HashMap<node_info,node_info> fathers; 
	private Formatter x;
	Scanner scan;

	//empty constructor
	public WGraph_Algo() {
		weighted_graph gr=new WGraph_DS();
		fathers =new HashMap<node_info,node_info>();
	}

	public weighted_graph gr;	


	@Override
	public void init(weighted_graph g) {
		this.gr=g;

	}

	@Override
	public weighted_graph getGraph() {

		return gr;
	}

	@Override
	public weighted_graph copy() {
		Collection<node_info> NewGraphVertices = gr.getV();
	//	Collection<Integer> NewGraphEdges = ((WGraph_DS) gr).getNeighbors();
	//	Collection<Double> NewGraphWeights = ((WGraph_DS) gr).getGraphweights();
		weighted_graph NewGraph = new WGraph_DS(NewGraphVertices);
		return NewGraph;
	}


	public void Dijkstra(node_info node)// standard Dijkstra Algo. registers all tags of nodes in src branch of graph as dist from src.
	{

		fathers.put(node,null);
		//initializes all tags to infinity and set info to "unvisited"
		double inf = Double.POSITIVE_INFINITY;
		Iterator<node_info> ite=gr.getV().iterator();
		while(ite.hasNext())
		{
			node_info N=ite.next();
			N.setTag(inf);
			N.setInfo("unvisited");
		}
        //initializes the priority queue and strart first node
		Queue<node_info> que = new PriorityQueue<node_info>();
		node.setInfo("visited");
		que.add(node);
		node.setTag(0);
		nodeCounter++;
        //standard algo for going through all nodes in graph
		while(!que.isEmpty())
		{			
			node_info top=que.poll();
			top.setInfo("visited");
			Collection<node_info> Nis = gr.getV(top.getKey());				
			Nis = ((nodeInfo)top).getNi();
			for(node_info nex:Nis)
			{	

				if(nex.getInfo().equals("unvisited") )

				{
					que.add(nex);
					nodeCounter++;
					nex.setInfo("visiting");
				}
				//resets tags if smaller dist
				if(nex.getTag()>top.getTag()+(gr.getEdge(top.getKey(), nex.getKey()))) {
					double n=gr.getEdge(top.getKey(), nex.getKey())+top.getTag();
					nex.setTag(n);
					fathers.put(nex,top);
				}							
			}

		}
	}

	//standard BFS algo. i added because it is more efficient, and could be used for isConnected function
	public void BFS(node_info node)// standard BFS Algo. registers all tags of nodes in src branch of graph as dist from src.
	{
		Iterator<node_info> ite=gr.getV().iterator();
		while(ite.hasNext())
		{
			node_info N=ite.next();
			N.setTag(-1);
			N.setInfo("unvisited");
		}
		Queue<node_info> que = new LinkedList<node_info>();
		node.setInfo("visited");
		que.add(node);
		node.setTag(0);
		nodeCounter++;

		while(!que.isEmpty())
		{			
			node_info top=que.poll();
			top.setInfo("visited");
			Collection<node_info> Nis  = gr.getV(top.getKey());				
			for(node_info nex:Nis)
			{	

				if(nex.getInfo().equals("unvisited") )

				{
					que.add(nex);
					nodeCounter++;
					nex.setInfo("visiting");
				}

			}

		}
	}
	@Override
	public boolean isConnected() {

		if(gr.nodeSize()==1) return true;
		else if(gr.nodeSize()==2 && gr.edgeSize()==1)return true;
		else if(gr.edgeSize()<gr.nodeSize()-1)return false;
		else
		{
			Iterator<node_info> ite=gr.getV().iterator();
			nodeCounter=0;
			ite=gr.getV().iterator();
			if(ite.hasNext()) {
				BFS(ite.next());
			}
			if(nodeCounter==gr.nodeSize())return true;
			else return false;
		}
	}

	@Override
	public double shortestPathDist(int src, int dest) {
if(src==dest) return 1;
		nodeCounter=0;

		Dijkstra(gr.getNode(src));
		return gr.getNode(dest).getTag();
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {

		Stack <node_info> st= new Stack<node_info>();
		Dijkstra(gr.getNode(src));
		node_info n=gr.getNode(dest);
		while(n!=null) {
			st.add(n);
			n=fathers.get(n);
		}
		//flips the order to be correct
		List<node_info> temp = new ArrayList<node_info>(st.size());
		while(!st.isEmpty()) {
			temp.add(st.pop());
		}
		return temp;
	}

	@Override
	public boolean save(String file) {
		//	boolean flag=true;
		try {
			x=new Formatter(file);
		}
		catch(Exception e) {
			System.out.println("you have an error");
			return false;
		}
		x.format("%s%s%s%s%s%s%s%s%s%s%s","amountOfNodes: ",",",gr.nodeSize(),",","amountOfEdges: ",",",gr.edgeSize(),",","mc: ",",",gr.getMC());
		x.format("%n", "");	
		Collection<node_info> col=gr.getV();
		for(node_info node: col) {
			x.format("%s%s%s%s%s" ,node.getKey(),",",node.getInfo(),",",node.getTag());
			Collection<node_info> listOfNi=((nodeInfo)node).getNi();
			for(node_info nis:listOfNi) {
				x.format("%s%s",",", nis.getKey());
				x.format("%s%s",",", gr.getEdge(node.getKey(),nis.getKey()));
			}
			x.format("%n", "");	
		}
		x.close();

		return true;

	}
	@Override
	public boolean load(String file)  {
		File f=new File(file);
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ln=scan.nextLine();
		String[] el=ln.split(",");
		int vertices=Integer.parseInt(el[1]);
		int edges=Integer.parseInt(el[3]);
		int mcCounter=Integer.parseInt(el[5]);		
		weighted_graph G=new WGraph_DS();
		while(scan.hasNextLine()) {
			ln=scan.nextLine();
			el=ln.split(",");
			int i=0;
			G.addNode(Integer.parseInt(el[0]));
			node_info n=G.getNode(Integer.parseInt(el[0]));
			n.setInfo(el[1]);
			n.setTag(Double.parseDouble(el[2]));
			i=3;
			while(i<el.length) {
				G.connect(n.getKey(),Integer.parseInt(el[i]),Double.parseDouble(el[i+1]));
				i=i+2;
			}	
		}
		scan.close();
		gr=G;

		if(vertices==G.nodeSize() && edges==G.edgeSize() && mcCounter==G.getMC()) {
			
			return true;
		}
		else return false;
	}

}
