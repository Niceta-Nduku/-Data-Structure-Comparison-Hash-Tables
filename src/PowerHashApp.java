import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*; 
import java.util.*;

/**
* This is the main application that creats a hashtable from data in a file
* searches the table and displays the search statistic  
* @author Niceta Nduku NDKNIC001
*/
public class PowerHashApp { 

  private static HashTable powerHash;// AVL tree to be used in class
  private static ArrayList<String> keys;


  /**
  * This is the method that reads from the CVS file and captures all the required data into a Hash tables.
  * It updates the variable HashTable powerHash with data from the CSV file.
  * @param size size of table
  * @param probe the collision resolution to be used
  * @param file the file which the data will come from  
  * @exception IOException
  * @exception FileNotFoundException
  * @see IOException
  * @see FileNotFoundException
  */    
	public static void getData(int size, String probe, String file) throws IOException, FileNotFoundException {

		powerHash= new HashTable(size,probe);
		// read from the CVS file 
		BufferedReader bRead = new BufferedReader(new FileReader("../"+file));
		    
		String ignoreline = bRead.readLine();//read the first line in the csv file and do nothing with it
		String line = bRead.readLine(); // the first line that contains the required data

		// initialise the values to be stored in the powerData object
		String dateTime;
		String power;
		String voltage;

    keys = new ArrayList<String>();
	
		while (line!=null){

			String[] allValues = line.split(",");// split at each comma to have an array of all the values
			dateTime = allValues[0];
			power = allValues[1];
			voltage = allValues[3];

      keys.add(dateTime);

      powerHash.insert(new PowerData(dateTime,power,voltage));

			line = bRead.readLine(); 
			   
		}
    Collections.shuffle(keys,new Random(100));
  }

  /**
  * Output displays contents from the hash table to the user
  * It will also search for k number of keys in the hashtable and 
  * do the statistics for the number of search probes 
  * @param k the number of searh keys          
  */
  public static void output(int k){

    System.out.println("Insertion probe count: "+ powerHash.getInsertProbe());
    System.out.println("Load factor: "+powerHash.getLFactor());

    if (k!=0){

      int[] probes = new int[k];

      for(int i=0;i<k;i++){
        powerHash.search(keys.get(i));
        probes[i] = powerHash.getSearchProbe();
      }

      System.out.println("K = "+ k);

      Arrays.sort(probes);      

      int sum = Arrays.stream(probes).sum();
      double average = (double) sum/probes.length;
      
      System.out.println("Total number of search probes: " + sum);
      System.out.printf("Average number of search probes: %.2f \n", average);
      System.out.println("Maximum search probe: "+probes[probes.length-1]);
      System.out.println("");
    }
  }

  /**
  * This is the main method that runs the application 
  * based on the user input
  * @param args String arguments          
  */
 	public static void main(String [] args) throws IOException {	

    if (args[0].equals("test")){

      String file = args[1]; 

      int[] size = {653, 727, 859, 937, 1009};

      for (int t=0; t<5;t++){

        getData(size[t],"l", file);
        output(400);
        getData(size[t],"q", file);
        output(400);
        getData(size[t],"c", file);
        output(400);
      }
    }
  
  	else {

      int size = Integer.parseInt(args[0]);
      String probe = args[1];  
      String file = args[2];   
      int k = Integer.parseInt(args[3]);

      if (isPrime(size)!= true){

        size =nextPrime(size);

      }

      getData(size, probe, file);

      output(k);
    }	    	
  }

  /**
  * Internal method to find a prime number at least as large as n.
  * @param n the starting number (must be positive).
  * @return a prime number larger than or equal to n.
  */
  private static int nextPrime( int n ) {
    if( n % 2 == 0 )
      n++;

    for( ; !isPrime( n ); n += 2 ) ;

    return n;
  }

  /**
   * Internal method to test if a number is prime.
   * Not an efficient algorithm.
   * @param n the number to test.
   * @return the result of the test. 
   */
  private static boolean isPrime( int n ) {
    if( n == 2 || n == 3 )
      return true;

    if( n == 1 || n % 2 == 0 )
      return false;

    for( int i = 3; i * i <= n; i += 2 )
      if( n % i == 0 )
        return false;

    return true;
  }
}