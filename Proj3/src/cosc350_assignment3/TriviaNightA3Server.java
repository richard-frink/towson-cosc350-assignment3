/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc350_assignment3;

import java.util.*;
import java.io.*;
import java.net.*;

/** This is referred to as R1:
 * Order of input (with examples)
 * 
 * 
 * R1 (server: node 7) first asks the user to enter R1’s neighbor distances and R1’s routing table as follows.
 * 
 * Enter number of neighbors: 2
 * Enter neighbor IDs separated by a single space: 6 4
 * Enter distances to each neighbor separated by a single space: 5 3
 * Enter number of destinations: 6
 * Enter destinations separated by a single space: 7 4 6 3 2 5
 * Enter next hop for each destination separated by a single space: 0 4 6 4 6 6
 * Enter distance to each destination separated by a single space: 0 3 5 14 13 19
 * 
 * prints out the R1 routing table
 * 
 * makes tcp connection to R2
 * 
 * DVR algorithm happens
 * 
 * R1 prints its updated routing table
 */
public class TriviaNightA3Server {
    public static void main(String[] args) throws Exception {
        
        int[][] rTable; // this includes next hop and distances
        int[] neighbors;
        int[] neighborDist;
        int[] destinations;
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Follow the input exactly as the assignment lists it.\n");
        
        System.out.print("Enter number of neighbors: ");
        neighbors = new int[scanner.nextInt()];
        
        System.out.print("Enter neighbor IDs separated by a single space: ");
        for(int i = 0; i < neighbors.length; i++){
            neighbors[i] = scanner.nextInt();
        }
        
        System.out.print("Enter distances to each neighbor separated by a single space: ");
        for(int i = 0; i < neighbors.length; i++){
            int useless = scanner.nextInt();
        }
        
        System.out.print("Enter number of destinations: ");
        destinations = new int[scanner.nextInt()];
        rTable = new int[destinations.length][3];
        
        System.out.print("Enter destinations separated by a single space: ");
        for(int i = 0; i < destinations.length; i++){
            destinations[i] = scanner.nextInt();
            rTable[i][0] = destinations[i];
        }
        
        System.out.print("Enter next hop for each destination separated by a single space: ");
        for(int i = 0; i < rTable.length; i++){
            rTable[i][1] = scanner.nextInt();
        }
        
        System.out.print("Enter distance to each destination separated by a single space: ");
        for(int i = 0; i < rTable.length; i++){
            rTable[i][2] = scanner.nextInt();
        }
        
        printRoutingTable(rTable);
        
        // TCP connection is made and receives client's data
        ServerSocket welcomeSocket = new ServerSocket(6789);
        String fromClient;
        
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			fromClient = inFromClient.readLine();
			System.out.println("Received: " + fromClient);

			String[] pairsInString = fromClient.split(" ");        
	        int[][] arrayFromClient = parseStringPairs(pairsInString);
	        
	        // The number of entries is implicitly received by taking 'fromClient.length'
	        updateRoutingTable(6, rTable, arrayFromClient);
	        
	        printRoutingTable(rTable);
		}
		
    }
    
    private static int[][] parseStringPairs(String[] pairs)
    {
    	int[][] goodForm = new int[pairs.length][2];
    	for(int i = 0; i < pairs.length; i++)
    	{
    		String temp = pairs[i];
    		char num1 = temp.charAt(1);
    		char num2 = temp.charAt(3);
    		goodForm[i][0] = Character.getNumericValue(num1);
    		goodForm[i][1] = Character.getNumericValue(num2);
    	}
    	return goodForm;
    }
    
    private static void printRoutingTable(int[][] table){
        System.out.printf("%15s%15s%15s\n\n", "Destination", "Next Hop", "Distance");
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table[i].length; j++){
                System.out.printf("%15d", table[i][j]);
            }
            System.out.print("\n\n");
        }
    }
    
    private static void updateRoutingTable(int node, int[][] table, int[][] fromNeighbor){
        int distToNode = -999;
        for(int i = 0; i < table.length; i++){
            if(table[i][0] == 6) distToNode = table[i][2];
        }
        for(int i = 0; i < fromNeighbor.length; i++){
            for(int j = 0; j < table.length; j++){
                if(fromNeighbor[i][0] == node) break;
                else if(fromNeighbor[i][0] == table[j][0]){
                    if(table[j][2] >= distToNode + fromNeighbor[i][1])
                        table[j][1] = node;
                    table[j][2] = distToNode + fromNeighbor[i][1];
                }
            }
        }
    }
}
