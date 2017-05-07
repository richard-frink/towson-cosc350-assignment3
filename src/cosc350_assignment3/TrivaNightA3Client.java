/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc350_assignment3;

import java.util.*;

/** This is referred to as R2:
 * Order of input (with examples)
 * 
 * 
 * R2 (client: node 6) asks the user to enter the information in the DVR message to be sent to R1 (server: node 7) as follows:
 * 
 * Enter number of entries in DVR message: 4
 * 
 * Enter destinations in DVR message separated by a single space: 6 3 2 5
 * 
 * Enter distances to each destination in DVR message separated by a single space: 0 2 8 28
 * 
 * R2 (client: node 6) uses the preceding information to form the DVR message, which consists of the pairs
 * of (destination, distance) values (6, 0), (3, 2), (2, 8), (5, 28).
 * 
 * R2 (client: node 6) makes a TCP socket connection to R1 (server: node 7) on port 6789 and sends a message M containing
 * the DVR message to R1. The message M consists of the number of entries in the DVR message followed by the (destination, distance)
 * pairs in the DVR message. 
 */
public class TrivaNightA3Client {
    public static void main(String[] args) {
        
        int[][] entries;
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of entries in DVR message: ");
        entries = new int[scanner.nextInt()][2];
        
        System.out.print("Enter destinations in DVR message separated by a single space: ");
        for(int i = 0; i < entries.length; i++){
            entries[i][0] = scanner.nextInt();
        }
        
        System.out.print("Enter distances to each destination in DVR message separated by a single space: ");
        for(int i = 0; i < entries.length; i++){
            entries[i][1] = scanner.nextInt();
        }
        
        System.out.print("Sending the following pairs to the server... \n");
        printPairs(entries);
        
        // form the dvr/tcp message to send to the server
        // message should be the number of entries, followed by the pairs of destinations and distances
        
        
    }
    
    private static void printPairs(int[][] pairs){
        for(int i = 0; i < pairs.length; i++){
            int[] ab = new int[2];
            for(int j = 0; j < pairs[i].length; j++){
                ab[j] = pairs[i][j];
            }
            System.out.print("(" + ab[0] + "," + ab[1] + ") ");
        }
    }
}