import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/** 
 *  
 */ 

/** 
 * @author niranjana 
 *  
 */ 
public class InteractiveDIALP { 

	static String path="";
	private static List<Vertex> nodes;
	private static List<Edge> edges1;

	/** 
	 * @param args 
	 */ 
	public static void main(String[] args) { 
		// TODO Auto-generated method stub 

		int noOfNodes=0, noOfEdges = 0, noOfServers, noOfClients = 0; 
		int k=1;
		boolean readFirst3Lines = true, isSecond = true, isThird = true; 
		String nextLine = ""; 
		String[] split; 
		HashMap<String, ArrayList<Integer>> serverClient = new HashMap<String, ArrayList<Integer>>(); 
		ArrayList<Integer> clients = new ArrayList<Integer>(); 
		ArrayList<Integer> servers = new ArrayList<Integer>(); 
		int[][] edges = new int[1000][1000]; 
		try { 
			Scanner scan = new Scanner( 
					new File( 
							"in_10_1")); 

			while (scan.hasNext()) { 

				if (readFirst3Lines) { 
					// Read first line 
					nextLine = scan.nextLine(); 
					split = nextLine.split(" "); 
					noOfNodes = Integer.valueOf(split[0]); 
					noOfEdges = Integer.valueOf(split[1]); 
					nodes = new ArrayList<Vertex>();
					edges1 = new ArrayList<Edge>();
					for (int i = 0; i <= noOfNodes; i++) {
						Vertex location = new Vertex("Node_" + i, "Node_" + i);
						nodes.add(location);
					}
					/*System.out.println("Number of nodes is" + noOfNodes); 
                    System.out.println("Number of edges is" + noOfEdges);*/ 
					// End reading first line 

					// Start reading second line 
					nextLine = scan.nextLine(); 
					split = nextLine.split(" "); 
					noOfClients = Integer.valueOf(split[0]); 
					for (int i = 1; i <= noOfClients; i++) { 

						clients.add(Integer.valueOf(split[i])); 
					} 
					serverClient.put("Clients", clients); 

					// End reading second line 

					// Start reading third line 
					nextLine = scan.nextLine(); 
					split = nextLine.split(" "); 
					noOfServers = Integer.valueOf(split[0]); 
					for (int i = 1; i <= noOfServers; i++) { 

						servers.add(Integer.valueOf(split[i])); 
					} 
					serverClient.put("Servers", servers); 
					readFirst3Lines = false; 
					// end reading third line 

					edges = new int[noOfEdges][noOfEdges]; 
				} 

				// Read the edge connections to a 2 dimensional array 
				nextLine = scan.nextLine(); 

				split = nextLine.split(" "); 

				edges[Integer.valueOf(split[0])][Integer.valueOf(split[1])] = 1; 
				addLane("Edge_"+ k , Integer.valueOf(split[0]), Integer.valueOf(split[1]), 1);
				k++;
				addLane("Edge_"+ k , Integer.valueOf(split[1]), Integer.valueOf(split[0]), 1);
				k++;

			} 

			// this finishes the scanning of file, now everything is available 
			// as part of variables 
			// now create the equations using the local variables 

			FileWriter writer = new FileWriter(new File("lp1.lp")); 

			//un comment and comment each of these in case if all the equations cannot be added to a single file 

			/*System.out.println( 
                    edges[1][16]+"," + edges[16][20]+ ","+edges[20][32] 
                    ); */

			formEquation2(serverClient,writer,edges,noOfNodes); 
			//             
			 formEquation3(serverClient,writer); 
             formEquation4(serverClient,writer); 
             formEquation5(serverClient,writer); 
             formEquation6(serverClient,writer); 
             formEquation7(serverClient,writer); 
             formEquation8(serverClient,writer); 

			writer.flush(); 

			writer.close(); 

		} catch (FileNotFoundException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 

	} 

	private static void addLane(String laneId, int sourceLocNo, int destLocNo,
			int duration) {
		Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);

		edges1.add(lane);

	}




	private static void formEquation2( 
			HashMap<String, ArrayList<Integer>> serverClient, 
			FileWriter writer, int[][] edges, int noOfNodes) throws IOException { 
		// x ipjq = y jq ? i<j ? C ? q ? S 

		StringBuilder builder = new StringBuilder(); 
		ArrayList<Integer> clientIds, serverIds; 
		clientIds = serverClient.get("Clients"); 
		serverIds = serverClient.get("Servers"); 
		int counter=0;
		int mincount=1000;
		HashMap<Integer, BestPath> clientPaths = new LinkedHashMap<Integer, BestPath>();
		HashMap<Integer, ArrayList<BestPath>> allPaths = new LinkedHashMap<Integer, ArrayList<BestPath>>();


		Graph graph = new Graph(nodes, edges1);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		writer.write("Minimize \n obj : T \n Subject To \n ");
		HashMap<Integer, Boolean> assignedClients = new HashMap<Integer, Boolean>();

		BestPath [][] differentPaths  = new BestPath [clientIds.size()][10000]; 


		for (int i = 0; i < clientIds.size(); i++) {
			int o=0;
			mincount=1000;
			for(int j=0;j<serverIds.size();j++){
				counter=0;
				/*System.out
				.println("Path from "+ clientIds.get(i) +"and "+serverIds.get(j));*/
				dijkstra.execute(nodes.get(clientIds.get(i)));
				LinkedList<Vertex> path = dijkstra.getPath(nodes.get(serverIds.get(j)));
				if(path!=null){
					for(Vertex vertex: path){
						/*System.out
						.println(vertex);*/

						counter++;

					}

					for(int m=0;m<serverIds.size();m++){

						/*System.out
    				.println("Path from server "+ serverIds.get(j) +"and "+serverIds.get(m));*/
						dijkstra.execute(nodes.get(serverIds.get(j)));
						LinkedList<Vertex> path1 = dijkstra.getPath(nodes.get(serverIds.get(m)));
						if(path1!=null){
							for(Vertex vertex: path1){
								/*System.out
    						.println(vertex);*/

								counter++;

							}

							for(int n=0;n<clientIds.size();n++){

								/*System.out
    				.println("Path from "+ clientIds.get(i) +"and "+serverIds.get(j));*/
								if(clientIds.get(i)<clientIds.get(n))
								{
									dijkstra.execute(nodes.get(serverIds.get(m)));
								
								LinkedList<Vertex> path2 = dijkstra.getPath(nodes.get(clientIds.get(n)));
								if(path2!=null){
									for(Vertex vertex: path2){
										/*System.out
    						.println(vertex);*/

										counter++;

									}
                                
                                   
									differentPaths[i][o]= new BestPath(clientIds.get(i), serverIds.get(j),serverIds.get(m),clientIds.get(n), counter);
								     o++;

									if(counter<mincount)
									{   mincount=counter;

									BestPath bestPath = new BestPath(clientIds.get(i), serverIds.get(j),serverIds.get(m),clientIds.get(n), mincount);
									clientPaths.put(clientIds.get(i),bestPath);
									}

								}
							}
							}



						}
						}

				}
				




			}
			
			
		}

		Iterator<Integer> itr = clientPaths.keySet().iterator();
		BestPath paths ;
		while(itr.hasNext()){
			paths = clientPaths.get(itr.next());
			System.out.println(paths.getDistance() +", client= "+ paths.getSource() +", server= "+paths.getDestination()
					+", server= "+ paths.getSource1() +", client ="+paths.getDestination1());
			 
		}
		
		int o=0;
		boolean isFirst=true;
		BestPath pathx;
		for (int i = 0; i < clientIds.size()-1; i++) {
			pathx = differentPaths[i][o];
			while(pathx!=null){
				
				pathx = differentPaths[i][o];
				if(isFirst){
				builder.append(pathx.getDistance() +" X_" + pathx.getSource() + "_"
										+ pathx.getDestination() + "_"
										+ pathx.getDestination1() + "_"
										+ pathx.getSource1());
				isFirst= false;
				}else{
					builder.append(" + "+ pathx.getDistance() +" X_" + pathx.getSource() + "_"
							+ pathx.getDestination() + "_"
							+ pathx.getDestination1() + "_"
							+ pathx.getSource1());
				}
								o++;
								pathx = differentPaths[i][o];
							} 
			isFirst=true;
			o=0;
			builder.append(" - T <= 0  \n");
						}
	
					
					writer.write(builder.toString());
//					System.out.println(builder.toString());
					builder.replace(0, builder.toString().length(), "");
				}

		
	



	private static int findServersPath(int sourceServer, int destServer, int destClient, int[][] edges, int lastServerId) {

		if(sourceServer==destServer){
			return 0;
		}else if(destServer>lastServerId){
			return 0;
		}else if(edges[sourceServer][destClient]==1||edges[destClient][sourceServer]==1){
			path +=sourceServer + "," +destClient +","; 
			return 101;
		}
		else if(edges[sourceServer][destServer]==1){
			path +=sourceServer + ","+destServer +","; 
			return 1+findServersPath(destServer,destServer+1,destClient,edges,lastServerId);
		}else if(edges[destServer][sourceServer]==1){
			path +=destServer+","+sourceServer +","; 
			return 1+findServersPath(sourceServer,sourceServer+1,destClient,edges,lastServerId);
		}
		else{
			int l=+ findServersPath(sourceServer,destServer+1,destClient,edges,lastServerId);
			return l;
		}


	}



	private static void formEquation3( 
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer) throws IOException { 
		// x ipjq = y jq ? i<j ? C ? q ? S 

		/*        System.out.println("X_i_p_j_q"); 
		 */        StringBuilder builder = new StringBuilder(); 
		 ArrayList<Integer> clientIds, serverIds; 
		 clientIds = serverClient.get("Clients"); 
		 serverIds = serverClient.get("Servers"); 
		 for (int i = 0; i < clientIds.size(); i++) { 
			 for (int j = 0; j < clientIds.size(); j++) { 
				 if (clientIds.get(i) < clientIds.get(j)) { 
					 for (int q = 0; q < serverIds.size(); q++) { 
						 for (int p = 0; p < serverIds.size(); p++) { 
							 if (p != serverIds.size()-1) { 
								 builder.append("X_" + clientIds.get(i) + "_" 
										 + serverIds.get(p) + "_" 
										 + clientIds.get(j) + "_" 
										 + serverIds.get(q) + " + "); 
							 } else { 
								 builder.append("X_" + clientIds.get(i) + "_" 
										 + serverIds.get(p) + "_" 
										 + clientIds.get(j) + "_" 
										 + serverIds.get(q)); 
							 } 
						 } 
						 builder.append(" - Y_" + clientIds.get(j) + "_" + serverIds.get(q) + " = 0  \n"); 
						 writer.write(builder.toString()); 
						 builder.replace(0, builder.toString().length(), ""); 

					 } 
				 } 

			 } 

		 } 
	} 

	private static String formEquation4( 
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer) throws IOException { 
		// x ipjq = y jq ? i<j ? C ? q ? S 

		/*System.out.println("X_i_p_j_q");*/ 
		StringBuilder builder = new StringBuilder(); 
		ArrayList<Integer> clientIds, serverIds; 
		clientIds = serverClient.get("Clients"); 
		serverIds = serverClient.get("Servers"); 
		for (int i = 0; i < clientIds.size(); i++) { 
			for (int j = 0; j < clientIds.size(); j++) { 
				if (clientIds.get(i) < clientIds.get(j)) { 
					for (int p = 0; p < serverIds.size(); p++) { 
						for (int q = 0; q < serverIds.size(); q++) { 
							if (q != serverIds.size()-1) { 
								builder.append("X_" + clientIds.get(i) + "_" 
										+ serverIds.get(p) + "_" 
										+ clientIds.get(j) + "_" 
										+ serverIds.get(q) + " + "); 
							} else { 
								builder.append("X_" + clientIds.get(i) + "_" 
										+ serverIds.get(p) + "_" 
										+ clientIds.get(j) + "_" 
										+ serverIds.get(q)); 
							} 
						} 
						builder.append(" - Y_" + clientIds.get(i) + "_" + serverIds.get(p) + " = 0\n"); 
						writer.write(builder.toString()); 
						builder.replace(0, builder.toString().length(), ""); 

					} 
				} 

			} 

		} 

		return null; 
	} 


	private static void formEquation5( 
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer) throws IOException { 
		/*        System.out.println("sum(Y_i_p) =1 for all i  and p belongs to server"); 
		 */        ArrayList<Integer> clientIds, serverIds; 
		 clientIds = serverClient.get("Clients"); 
		 serverIds = serverClient.get("Servers"); 
		 StringBuilder builder = new StringBuilder(); 
		 for (int p = 0; p < serverIds.size(); p++) { 

			 for (int i = 0; i < clientIds.size(); i++) { 
				 if (i != clientIds.size() - 1) { 
					 builder.append("Y" + "_" + clientIds.get(i) + "_" 
							 + serverIds.get(p) + " + "); 
				 } else { 
					 builder.append("Y" + "_" + clientIds.get(i) + "_" 
							 + serverIds.get(p)); 
				 } 
			 } 
			 builder.append("  = 1 \n"); 
			 writer.write(builder.toString()); 
			 builder.replace(0, builder.toString().length(), ""); 
		 } 

	} 



	private static void formEquation6( 
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer) throws IOException { 
		StringBuilder builder = new StringBuilder(); 
		ArrayList<Integer> clientIds, serverIds; 
		clientIds = serverClient.get("Clients"); 
		serverIds = serverClient.get("Servers"); 
		writer.write("Bounds\n"); 
		for (int i = 0; i < clientIds.size(); i++) { 
			for (int j = 0; j < clientIds.size(); j++) { 
				if (clientIds.get(i) < clientIds.get(j)) { 
					for (int p = 0; p < serverIds.size(); p++) { 
						for (int q = 0; q < serverIds.size(); q++) { 
							builder.append("X_" + clientIds.get(i) + "_" 
									+ serverIds.get(p) + "_" 
									+ clientIds.get(j) + "_" 
									+ serverIds.get(q) + " >= 0 \n"); 
							writer.write(builder.toString()); 
							builder.replace(0, builder.toString().length(), ""); 
						} 

					} 
				} 

			} 

		} 

	} 

	private static void formEquation7( 
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer) throws IOException { 
		StringBuilder builder = new StringBuilder(); 
		ArrayList<Integer> clientIds, serverIds; 
		clientIds = serverClient.get("Clients"); 
		serverIds = serverClient.get("Servers"); 
		for (int i = 0; i < clientIds.size(); i++) { 
			for (int p = 0; p < serverIds.size(); p++) { 
				builder.append("Y_" + clientIds.get(i) + "_" 
						+ serverIds.get(p) + " >= 0 \n"); 
				writer.write(builder.toString()); 
				builder.replace(0, builder.toString().length(), ""); 
			} 

		} 
	} 


	private static void formEquation8( 
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer) throws IOException { 


		StringBuilder builder = new StringBuilder(); 
		ArrayList<Integer> clientIds, serverIds; 
		clientIds = serverClient.get("Clients"); 
		serverIds = serverClient.get("Servers"); 

		builder.append("Generals \n"); 

		for (int i = 0; i < clientIds.size(); i++) { 
			for (int j = 0; j < clientIds.size(); j++) { 
				if (clientIds.get(i) < clientIds.get(j)) { 
					for (int p = 0; p < serverIds.size(); p++) { 
						for (int q = 0; q < serverIds.size(); q++) { 
							builder.append("X_" + clientIds.get(i) + "_" 
									+ serverIds.get(p) + "_" 
									+ clientIds.get(j) + "_" 
									+ serverIds.get(q) + " \n"); 
							writer.write(builder.toString()); 
							builder.replace(0, builder.toString().length(), ""); 
						} 

					} 
				} 

			} 

		} 
		//.    builder.append("End"); 
		writer.write("End");
	}
}