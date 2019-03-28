import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author niranjana
 * 
 */
public class CopyOfInteractiveDIALP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int noOfNodes, noOfEdges = 0, noOfServers, noOfClients = 0;

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
							"in_10"));

					/*new File(
							"//home//niranjana//Documents//Niranjana//Softwares//StudyMaterial//CS538//Project//"
									+ "SampleInputs//in_10_1"));*/
					while (scan.hasNext()) {

				if (readFirst3Lines) {
					// Read first line
					nextLine = scan.nextLine();
					split = nextLine.split(" ");
					noOfNodes = Integer.valueOf(split[0]);
					noOfEdges = Integer.valueOf(split[1]);
					System.out.println("Number of nodes is" + noOfNodes);
					System.out.println("Number of edges is" + noOfEdges);
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

			}

			// this finishes the scanning of file, now everything is available
			// as part of variables
			// now create the equations using the local variables

			FileWriter writer = new FileWriter(new File("lp1.lp"));
			
		//un comment and comment each of these in case if all the equations cannot be added to a single file
			
			System.out.println(
					edges[1][16]+"," + edges[16][20]+ ","+edges[20][32]
					);
			
			formEquation2(serverClient,writer,edges);
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



	private static void formEquation2(
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer, int[][] edges) throws IOException {
		// x ipjq = y jq ∀ i<j ∈ C ∀ q ∈ S

		StringBuilder builder = new StringBuilder();
		ArrayList<Integer> clientIds, serverIds;
		clientIds = serverClient.get("Clients");
		serverIds = serverClient.get("Servers");
		/*builder.append("Minimize \n obj : T \n Subject To \n ");*/
		writer.write("Minimize \n obj : T \n Subject To \n ");
		
		for (int i = 0; i < clientIds.size(); i++) {
			for (int j = 0; j < clientIds.size(); j++) {
				if (clientIds.get(i) < clientIds.get(j)) {
					for (int q = 0; q < serverIds.size(); q++) {
						for (int p = 0; p < serverIds.size(); p++) {
							
							if (p != serverIds.size()-1) {
								
								/*if(clientIds.get(i)== 1 && serverIds.get(p)==16 &&  serverIds.get(q)
										==20 && clientIds.get(j)==32  ){
									System.out.println(true);
								}*/
								
										
								if((edges[clientIds.get(i)][serverIds.get(p)]!=0 ||edges[serverIds.get(p)][clientIds.get(i)]!=0) 
										&& (edges[serverIds.get(p)][serverIds.get(q)]!=0 || edges[serverIds.get(q)][serverIds.get(p)]!=0 )
										&& (edges[serverIds.get(q)][clientIds.get(j)]!=0 || edges[clientIds.get(j)][serverIds.get(q)]!=0)){
									
									System.out.println(
											clientIds.get(i)+serverIds.get(p)+"," + serverIds.get(p) + serverIds.get(q)+ ","+serverIds.get(q) + ","+clientIds.get(j)
											);
								builder.append("3 X_" + clientIds.get(i) + "_"
										+ serverIds.get(p) + "_"
										+ clientIds.get(j) + "_"
										+ serverIds.get(q) );
										
								
								
									
										//	builder.append("("+3 +") + ");
//											builder.append("(d("+clientIds.get(i)+"::,"+edges[clientIds.get(i)][serverIds.get(p)]+ serverIds.get(p) +")");
/*										}else{
											builder.append("("+edges[clientIds.get(i)][serverIds.get(p)] +" + ");
										}
										
										if(edges[serverIds.get(p)][serverIds.get(q)]==0){
											builder.append(1000 +" + ");
//											builder.append("(d("+clientIds.get(i)+"::,"+edges[clientIds.get(i)][serverIds.get(p)]+ serverIds.get(p) +")");
										}else{
											builder.append(edges[serverIds.get(p)][serverIds.get(q)] +" + ");
										}
										if(edges[serverIds.get(q)][clientIds.get(j)]==0){
											builder.append(1000 +") + ");
//											builder.append("(d("+clientIds.get(i)+"::,"+edges[clientIds.get(i)][serverIds.get(p)]+ serverIds.get(p) +")");
										}else{
											builder.append(edges[serverIds.get(q)][clientIds.get(j)] +")"+ " + ");
										}
										
*/										/*builder.append("d("+serverIds.get(p)+","+serverIds.get(q) +") + " 
										+"d("+serverIds.get(q)+","+clientIds.get(j) +"))" +"+ ");*/
							}
							}
							
							else {
								if((edges[clientIds.get(i)][serverIds.get(p)]!=0 ||edges[serverIds.get(p)][clientIds.get(i)]!=0) 
										&& (edges[serverIds.get(p)][serverIds.get(q)]!=0 || edges[serverIds.get(q)][serverIds.get(p)]!=0 )
										&& (edges[serverIds.get(q)][clientIds.get(j)]!=0 || edges[clientIds.get(j)][serverIds.get(q)]!=0)){
								builder.append("3 X_" + clientIds.get(i) + "_"
										+ serverIds.get(p) + "_"
										+ clientIds.get(j) + "_"
										+ serverIds.get(q) );
										
								
								
									
											//builder.append("("+3 +") ");
								}
								/*builder.append("X_" + clientIds.get(i) + "_"
										+ serverIds.get(p) + "_"
										+ clientIds.get(j) + "_"
										+ serverIds.get(q) +"(d("+clientIds.get(i)+","+serverIds.get(p) +") + "
										+"d("+serverIds.get(p)+","+serverIds.get(q) +") + " 
										+"d("+serverIds.get(q)+","+clientIds.get(j) +"))");
								
								builder.append("X_" + clientIds.get(i) + "_"
										+ serverIds.get(p) + "_"
										+ clientIds.get(j) + "_"
										+ serverIds.get(q) );
										if(edges[clientIds.get(i)][serverIds.get(p)]==0){
											builder.append("("+1000 +" + ");
//											builder.append("(d("+clientIds.get(i)+"::,"+edges[clientIds.get(i)][serverIds.get(p)]+ serverIds.get(p) +")");
										}else{
											builder.append("("+edges[clientIds.get(i)][serverIds.get(p)] +" + ");
										}
										
										if(edges[serverIds.get(p)][serverIds.get(q)]==0){
											builder.append(1000 +" + ");
//											builder.append("(d("+clientIds.get(i)+"::,"+edges[clientIds.get(i)][serverIds.get(p)]+ serverIds.get(p) +")");
										}else{
											builder.append(edges[serverIds.get(p)][serverIds.get(q)] +" + ");
										}
										if(edges[serverIds.get(q)][clientIds.get(j)]==0){
											builder.append(1000 +")");
//											builder.append("(d("+clientIds.get(i)+"::,"+edges[clientIds.get(i)][serverIds.get(p)]+ serverIds.get(p) +")");
										}else{
											builder.append(edges[serverIds.get(q)][clientIds.get(j)] +")");
										}*/
							}
						}
						

					}
					if(!builder.toString().isEmpty()){
					builder.append(" - T <= 0  \n");
					writer.write(builder.toString());
//					System.out.println(builder.toString());
					builder.replace(0, builder.toString().length(), "");
					}
					
				}

			}

		}
	}
	
	private static void formEquation3(
			HashMap<String, ArrayList<Integer>> serverClient, FileWriter writer) throws IOException {
		// x ipjq = y jq ∀ i<j ∈ C ∀ q ∈ S

		System.out.println("X_i_p_j_q");
		StringBuilder builder = new StringBuilder();
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
		// x ipjq = y jq ∀ i<j ∈ C ∀ q ∈ S

		System.out.println("X_i_p_j_q");
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
		System.out.println("sum(Y_i_p) =1 for all i  and p belongs to server");
		ArrayList<Integer> clientIds, serverIds;
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
	//.	builder.append("End");
		writer.write("End");
		
	}

}