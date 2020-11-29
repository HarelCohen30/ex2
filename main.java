package api;

import api.NodeData;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.geo_location;
import api.node_data;
import api.edge_data;
import api.EdgeData;

public class Main_Test 
{
  
	private static int _errors = 0, _tests = 0,_number_of_exception=0;
    private static String _log = "";
    
	 public static void main(String[] args)
	 {
	      System.out.println("Running tests for Ex1 - this may take up to 15 seconds!");
	      long start = new Date().getTime();
		     Test0();// Graph with 1 node 
		     Test1();// Graph with 2 nodes
			 Test2();// Copy and remove check
			 Test3();// remove and shortestPathDist check
			 Test4();// Connectivity Test(10-nodes 45 edges)
		     Test5();// Connectivity Test(100-nodes 500 edges)
			 Test6();// Connectivity Test and running time test (1000000-nodes)
			 Test7();// null Check;
			 Test8();// Write to file test;
			 Test9();// load file test;
			 Test10();// load and Write to file test;
		 long end = new Date().getTime();
	        double dt = (end-start)/1000.0;
	        boolean t = dt<15;
	        test("runtime test: ",t, true);
	        System.out.println(_log);
	        double g = 100.0*(_tests-_errors)/_tests;
	        g = g - _number_of_exception*10;
	        g = Math.max(g,25);
	        System.out.println("number of Errors: "+_errors+" of "+_tests+" tests, "+_number_of_exception
	                +" exceptions, time: "+dt+" seconds");
	        System.out.println("grade: "+(int)g);
	 }

		// public static void PrintNodeList(List<node_info> L)// print the NodeList. I built this method for my own Test
		// {
		// 	if(L!=null && L.size()>0)
		// 	{
		// 		for (int i = 0; i < L.size(); i++)
		// 		{
		// 			NodeInfo N=new NodeInfo(L.get(i));
		// 			N.PrintNode();
		// 			if(i+1<L.size())System.out.print("-->");
		// 		}
		// 		System.out.println();
		// 	}
		// }
		public static DWGraph_DS GraphBuilder(int Num,int Edge)
		{
			DWGraph_DS G=new DWGraph_DS();
			NodeData N=new NodeData();
			int S=N.getKey();
			G.addNode(N);
			for (int i = 1; i < Num; i++)
			{
				N=new NodeData();
				G.addNode(N);
			}
			if(Edge>((Num-1)*Num)/2)
			{
				Edge=(Num*Num-Num)/2;
				System.out.println("To much Edges");
				System.out.println("Num of Edges Change To: "+Edge);
			}
			if(Num>1)
			{
				for (int i = 0; i < Edge; i++)
				{
					int Rand1=(int)(Math.random()*Num)+S;
					int Rand2=(int)(Math.random()*Num)+S;
					double RandW=(int)(Math.random()*100+1);
					while(G.hasEdge(Rand1, Rand2) || Rand1==Rand2)
					{
						Rand1=(int)(Math.random()*Num)+S;
						Rand2=(int)(Math.random()*Num)+S;
					}
				//System.out.println("Node with key: "+Rand1+" is Connected to Node with key: "+Rand2+" the edge price is : "+RandW);
					G.connect(Rand1, Rand2, RandW);
				}
			}
			return G;
		}
		
		public static DWGraph_DS GraphBuilder(int Num)//Crate a connected Graph by connecting the first two nodes to all the other nodes
		{
			DWGraph_DS G=new DWGraph_DS();
			NodeData N=new NodeData();
			G.addNode(N);
			int S=N.getKey();
			for (int i = 1; i < Num; i++)
			{
			 N=new NodeData();
				G.addNode(N);
			}
			if(Num>1)
			{
				for (int i = S+2; i <S+Num; i++)
				{
					double RandW=(int)(Math.random()*100+1);
				//System.out.println("Node with key: "+Rand1+" is Connected to Node with key: "+Rand2+" the edge price is : "+RandW);
					G.connect(S, i, RandW);
					G.connect(S+1, i, RandW);
				}
			}
			return G;
		}
		public static void Test0()
		{
			DWGraph_DS G1=GraphBuilder(1,0);
			//G1.PrintGData();
			 DWGraph_Algo W=new DWGraph_Algo();
			 W.init(G1);
			 boolean flag=W.isConnected();
			 test("Test0 ", flag, true);
		}
		public static void Test1()
		{
			DWGraph_DS G1=GraphBuilder(2,0);
			DWGraph_Algo W=new DWGraph_Algo();
			 W.init(G1);
			 boolean flag=W.isConnected();
			 test("Test1(a) ", flag, false);
			 
			 
			 DWGraph_DS G2=GraphBuilder(2,1);
			 DWGraph_Algo W2=new DWGraph_Algo();
			 W2.init(G2);
			// G1.PrintGData();
			flag=W2.isConnected();
			 test("Test1(b) ", flag, true);
		}
		public static void Test2()
		{
			NodeData N1=new NodeData();
			NodeData N2=new NodeData();
			NodeData N3=new NodeData();
			NodeData N4=new NodeData();
			NodeData N5=new NodeData();
			DWGraph_DS G=new DWGraph_DS();
			G.addNode(N1);
			G.addNode(N2);
			G.addNode(N3);
			G.addNode(N4);
			G.addNode(N5);
			 G.connect(N2.getKey(), N4.getKey(),1.5);
			 G.connect(N2.getKey(), N3.getKey(),1.5);
			 G.connect(N2.getKey(), N5.getKey(),7.5);
			 G.connect(N3.getKey(), N5.getKey(),1.5);
			 G.connect(N1.getKey(), N5.getKey(),25);
			 G.connect(N1.getKey(), N3.getKey(),8.5);
			 G.connect(N1.getKey(), N2.getKey(),2.5);
			 DWGraph_Algo W=new DWGraph_Algo();
			 W.init(G);
			 boolean flag=W.isConnected();
			 test("Test2(a) ", flag, true);
			 test("Test2(b) ", 7, W.getGraph().edgeSize());
			 DWGraph_DS G1=(DWGraph_DS) W.copy();
			 flag=G.equals(G1);
			 test("Test2(C) ", flag, true);
			 W.init(G1);
			 G.removeEdge(N1.getKey(), N2.getKey());
			 test("Test2(D) ", 6, G.edgeSize());
			 G.removeNode(N1.getKey());
			 test("Test2(E) ", 4, G.edgeSize());
			 flag=G.equals(G1);
			 test("Test2(F) ", flag, false);
		}
		public static void Test3()
		{
			NodeData N1=new NodeData();
			NodeData N2=new NodeData();
			NodeData N3=new NodeData();
			NodeData N4=new NodeData();
			NodeData N5=new NodeData();
			DWGraph_DS G=new DWGraph_DS();
			G.addNode(N1);
			G.addNode(N2);
			G.addNode(N3);
			G.addNode(N4);
			G.addNode(N5);
			 G.connect(N2.getKey(), N4.getKey(),1.5);
			 G.connect(N2.getKey(), N3.getKey(),1.5);
			 G.connect(N2.getKey(), N5.getKey(),7.5);
			 G.connect(N3.getKey(), N5.getKey(),1.5);
			 G.connect(N1.getKey(), N5.getKey(),25);
			 G.connect(N1.getKey(), N3.getKey(),8.5);
			 G.connect(N1.getKey(), N2.getKey(),2.5);
			 DWGraph_Algo W=new DWGraph_Algo();
			 W.init(G);
			 W.shortestPath(N1.getKey(), N5.getKey());
			 int L= W.shortestPath(N5.getKey(), N5.getKey()).size();
			 test("Test3(a) ", 1,L);
			 test("Test3(b) ",8.5 ,W.getGraph().getEdge(N1.getKey(), N3.getKey()).getWeight());
			  L=W.shortestPath(N1.getKey(), N5.getKey()).size();
			 test("Test3(C) ", 4,L);
			 W.getGraph().connect(N1.getKey(), N5.getKey(), 2);
			 test("Test3(d) ", 2, W.shortestPathDist(N1.getKey(), N5.getKey()));
		}
		public static void Test4()
		{
			DWGraph_DS G1=GraphBuilder(10,45);
			 DWGraph_Algo W=new DWGraph_Algo();
			 W.init(G1);
			 //G1.PrintGData();
			 boolean flag=W.isConnected();
			 test("Test4 ", flag, true);
		}
		public static void Test5()
		{
			DWGraph_DS G1=GraphBuilder(100,500);
			 DWGraph_Algo W=new DWGraph_Algo();
			 W.init(G1);
			 //G1.PrintGData();
			 boolean flag=W.isConnected();
			 test("Test5 ", flag, true);
			
		}
		public static void Test6()
		{
			DWGraph_DS G1=GraphBuilder(1000000);
			//G1.PrintGData();
			 DWGraph_Algo W=new DWGraph_Algo();
			 W.init(G1);
			 //G1.PrintGData();
			 boolean flag=W.isConnected();
			 test("Test6(a) ", flag, true);
			 Iterator<node_info> ite=W.getGraph().getV().iterator();
			 node_info S=ite.next();
			 W.getGraph().removeNode(S.getKey());
			//G1.PrintGData();
			  flag=W.isConnected();
			 test("Test6(b) ", flag, true);
			 test("Test6(C)",W.getGraph().edgeSize(),999998);
		}
		public static void Test7()
		{
			DWGraph_DS G1=new DWGraph_DS ();
			DWGraph_Algo W=new DWGraph_Algo();
			W.init(G1);
			node_info n=W.getGraph().getNode(0);
			test("Test7(a) ", W.isConnected(), true);
			boolean flag=true;
			if(n==null)flag=false;
			test("Test7(b) ", flag, false);
		}
		public static void Test8()
		{
			DWGraph_Algo W=new DWGraph_Algo();
			 W.init(GraphBuildA());
			 W.isConnected();
			 boolean flag=W.save("Test8.txt");
			 test("Test8(a) ",flag,true);
		}
		public static void Test9()
		{
			DWGraph_Algo W=new DWGraph_Algo();
			 boolean flag=W.load("Test8.txt");
			 test("Test9(a) ",flag,true);
			 test("Test9(b) ",W.getGraph().nodeSize(),5);
		}
		public static void Test10()
		{
			 DWGraph_Algo W=new DWGraph_Algo();
			 W.init(GraphBuildA());
			 W.isConnected();
			W.save("Test8.txt");
			DWGraph_Algo W1=new DWGraph_Algo();
			W1.load("Test8.txt");
			DWGraph_DS G=(DWGraph_DS)W.getGraph();
			DWGraph_DS G1=(DWGraph_DS)W1.getGraph();
			boolean flag=G.equals(G1);
			 test("Test10(a) ",flag,true);
		}
		public static WGraph_DS GraphBuildA()
		{
			NodeData N1=new NodeData();
			NodeData N2=new NodeData();
			NodeData N3=new NodeData();
			NodeData N4=new NodeData();
			NodeData N5=new NodeData();
			DWGraph_DS G=new DWGraph_DS();
			G.addNode(N1);
			G.addNode(N2);
			G.addNode(N3);
			G.addNode(N4);
			G.addNode(N5);
			 G.connect(N2.getKey(), N4.getKey(),1.5);
			 G.connect(N2.getKey(), N3.getKey(),1.5);
			 G.connect(N2.getKey(), N5.getKey(),7.5);
			 G.connect(N3.getKey(), N5.getKey(),1.5);
			 G.connect(N1.getKey(), N5.getKey(),25);
			 G.connect(N1.getKey(), N3.getKey(),8.5);
			 G.connect(N1.getKey(), N2.getKey(),2.5);
			 return G;
		}
		
	    private static void test(String test, boolean val, boolean req) {
	        test(test, ""+val, ""+req);
	    }
	    private static void test(String test, int val, int req) {
	        test(test, ""+val, ""+req);
	    }
	    private static void test(String test, double val, double req) {
	        test(test, ""+val, ""+req);
	    }
	    private static void test(String test, String val, String req) {
	        boolean ans = true;
	        ans = val.equals(req);
	        String tt = _tests+") "+test+"  pass: "+ans;
	        _log += "\n"+tt;
	        if(!ans) {
	            _errors++;
	            String err = "  ERROR("+_errors+") "+val+"!="+req;
	            System.err.println(tt+err);
	            _log += err;
	        }
	        _tests++;
	    }
}
