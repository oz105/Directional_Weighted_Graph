//package gameClient;
//
//<<<<<<< HEAD
//import Server.Agent_Graph_Algo;
//import Server.Game_Server_Ex2;
//import api.game_service;
//import api.edge_data;
//import api.directed_weighted_graph;
////import Server.DWGraph;
//=======
//import Server.Game_Server_Ex2;
//import api.directed_weighted_graph;
//import api.edge_data;
//import api.game_service;
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//public class Ex2_Client implements Runnable{
//	private static MyFrame _win;
//	private static Arena _ar;
//	public static void main(String[] a) {
//		Thread client = new Thread(new Ex2_Client());
//		client.start();
//	}
//
//	@Override
//	public void run() {
//		int scenario_num = 11;
//<<<<<<< HEAD
//		//int id = 999;
//
//		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//		//game.login(id);
//=======
//		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//	//	int id = 999;
//	//	game.login(id);
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//		String g = game.getGraph();
//		String pks = game.getPokemons();
//		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
//		init(game);
//
//		game.startGame();
//		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
//		int ind=0;
//		long dt=100;
//
//		while(game.isRunning()) {
//<<<<<<< HEAD
//			moveAgents(game, gg);
//=======
//			moveAgants(game, gg);
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//			try {
//				if(ind%1==0) {_win.repaint();}
//				Thread.sleep(dt);
//				ind++;
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//		String res = game.toString();
//
//		System.out.println(res);
//		System.exit(0);
//	}
//	/**
//<<<<<<< HEAD
//	 * Moves each of the robots along the edge,
//	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
//=======
//	 * Moves each of the agents along the edge,
//	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//	 * @param game
//	 * @param gg
//	 * @param
//	 */
//<<<<<<< HEAD
//	private static void moveAgents(game_service game, directed_weighted_graph gg) {
//		String lg = game.move();
//		List<CL_Agent> log = Agent_Graph_Algo.getAgents(lg, gg);
//		_ar.setAgents(log);
//		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
//		String fs =  game.getPokemons();
//		List<CL_Pokemon> ffs = Agent_Graph_Algo.json2Pokemons(fs);
//		_ar.setPokemons(ffs);
//		for(int i=0;i<log.size();i++) {
//			CL_Agent robot = log.get(i);
//			int id = robot.getID();
//			int dest = robot.getNextNode();
//			int src = robot.getSrcNode();
//			double v = robot.getValue();
//			if(dest==-1) {
//				dest = nextNode(gg, src);
//				game.chooseNextEdge(robot.getID(), dest);
//=======
//	private static void moveAgants(game_service game, directed_weighted_graph gg) {
//		String lg = game.move();
//		List<CL_Agent> log = Arena.getAgents(lg, gg);
//		_ar.setAgents(log);
//		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
//		String fs =  game.getPokemons();
//		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
//		_ar.setPokemons(ffs);
//		for(int i=0;i<log.size();i++) {
//			CL_Agent ag = log.get(i);
//			int id = ag.getID();
//			int dest = ag.getNextNode();
//			int src = ag.getSrcNode();
//			double v = ag.getValue();
//			if(dest==-1) {
//				dest = nextNode(gg, src);
//				game.chooseNextEdge(ag.getID(), dest);
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
//			}
//		}
//	}
//	/**
//	 * a very simple random walk implementation!
//	 * @param g
//	 * @param src
//	 * @return
//	 */
//	private static int nextNode(directed_weighted_graph g, int src) {
//		int ans = -1;
//		Collection<edge_data> ee = g.getE(src);
//		Iterator<edge_data> itr = ee.iterator();
//		int s = ee.size();
//		int r = (int)(Math.random()*s);
//		int i=0;
//		while(i<r) {itr.next();i++;}
//		ans = itr.next().getDest();
//		return ans;
//	}
//	private void init(game_service game) {
//<<<<<<< HEAD
//
//=======
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//		String g = game.getGraph();
//		String fs = game.getPokemons();
//		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
//		//gg.init(g);
//		_ar = new Arena();
//		_ar.setGraph(gg);
//<<<<<<< HEAD
//		_ar.setPokemons(Agent_Graph_Algo.json2Pokemons(fs));
//		_win = new MyFrame("test Ex2");
//		_win.update(_ar);
//		_win.setSize(1000, 700);
//=======
//		_ar.setPokemons(Arena.json2Pokemons(fs));
//		_win = new MyFrame("test Ex2");
//		_win.setSize(1000, 700);
//		_win.update(_ar);
//
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//
//		_win.show();
//		String info = game.toString();
//		JSONObject line;
//		try {
//			line = new JSONObject(info);
//			JSONObject ttt = line.getJSONObject("GameServer");
//			int rs = ttt.getInt("agents");
//			System.out.println(info);
//			System.out.println(game.getPokemons());
//<<<<<<< HEAD
//			int src_node = 0;  // arbitrary node, you should start at one of the fruits
//			ArrayList<CL_Pokemon> cl_fs = Agent_Graph_Algo.json2Pokemons(game.getPokemons());
//			for(int a = 0;a<cl_fs.size();a++) { Agent_Graph_Algo.updateEdge(cl_fs.get(a),gg);}
//=======
//			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
//			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
//			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//			for(int a = 0;a<rs;a++) {
//				int ind = a%cl_fs.size();
//				CL_Pokemon c = cl_fs.get(ind);
//				int nn = c.get_edge().getDest();
//				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
//
//				game.addAgent(nn);
//			}
//		}
//		catch (JSONException e) {e.printStackTrace();}
//<<<<<<< HEAD
//
//=======
//>>>>>>> 2f12736a083a7ca63b4def9b5e945f2ab287759d
//	}
//}
