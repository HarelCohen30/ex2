package api;

import java.util.List;

import gameClient.CL_Agent;
import gameClient.CL_Pokemon;

public class Game_Server_Ex2 implements game_service {

	private dw_graph_algorithms graph;
	private List<CL_Agent> agents;
	private List<CL_Pokemon> pokemons;
	
	@Override
	public String getGraph() {
		String file = new String();
		if(graph.save(file))
		{
			return file;
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPokemons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAgents() {
		String s =agents.toString();
		return s;
	}

	@Override
	public boolean addAgent(int start_node) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long startGame() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long stopGame() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long chooseNextEdge(int id, int next_node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long timeToEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
