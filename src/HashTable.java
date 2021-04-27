import java.util.*;

/**
*	HashTable is a class that creates a hash set with the option of 3 diffrerent collision resolution methods.
*	@author Niceta Nduku 
*/
public class HashTable{

	private static int tableSize; 
	private static Entry [] powerHash; // array to be used for linear and quadratic probing 
	private boolean linear = false; // true if linear probing is being used
	private boolean quadratic = false;
	private boolean chaining = false;
	private static int occupied; // the number of slots filled in the table
	private static int insertProbe;
	private static int searchProbe;
	private static int probe; 

	/**
	*	To create a new hash table, the table size must be specified and the collison resolution scheme to be used. 
	*	@param tableSize the size of the hash table 
	*	@param collisionProbe the collision resolution to be used specified by 
		l for linear, q for quadratic and c for chaining.
	*/


	public HashTable(int tableSize, String collisionProbe){

		this.tableSize = tableSize; 

		System.out.println("Table Size: "+this.tableSize);

		occupied=0;
		insertProbe=0;
		searchProbe=0;
		probe=0;

		
		if (collisionProbe.equals("l")){
			linear = true;
			System.out.println("Linear");
		}

		else if (collisionProbe.equals("q")){
			quadratic = true;
			System.out.println("Quadratic");
		}

		else{
			chaining = true;
			System.out.println("Chaining");
		}

	

		//if (linear || quadratic){
		powerHash = new Entry[this.tableSize];
		
		for (int i = 0; i < this.tableSize; i++)

			powerHash[i] = null;
	}

	/**
	* Insert adds the powerData object into the hashTable based on the resolution chosen
	* @param d the data item to be added
	*/
	public void insert(PowerData d){

		Entry item = new Entry(d);

		String key = d.getDateTime();
		int hashIndex = hash(key);

		if (linear){

			hashIndex=linearPos(hashIndex, key);
    		powerHash[hashIndex] = item;
    		occupied++;

        	insertProbe += probe;
        	probe = 0;

		}

		else if (quadratic){	

			hashIndex=quadraticPos(hashIndex,key);

        	powerHash[hashIndex] = item;
    		occupied++;

        	insertProbe += probe;
        	probe = 0;
		}

		else {
			if (powerHash[hashIndex] == null){

				powerHash[hashIndex] = item ;//at that index add the first Entry
				occupied++;
			}

			else{

				Entry hold = powerHash[hashIndex];//the head

				probe++;				
				while (hold.getNext() != null && hold.key != key){
					probe++;
					hold = hold.getNext();
                }

                hold.setNext(item);
                occupied++;

                insertProbe += probe;
        		probe = 0;
			}

		}
	}

	/**
	* Search looks for a data item based on a matching key. 
	* @param key the string key that is to be searched for
	* @return PowerData object
	*/
	public PowerData search(String key){

		int hashIndex = hash(key);

		if (linear){

        	hashIndex=linearPos(hashIndex, key);

        	searchProbe = probe;
        	probe=0;

        	return (powerHash[hashIndex]).data;
		}

		else if (quadratic){	

			hashIndex=quadraticPos(hashIndex, key);

        	searchProbe = probe;
        	probe=0;

        	return (powerHash[hashIndex]).data;
		}
		else {

			Entry hold = powerHash[hashIndex];//the head

			while (hold.getNext() != null && hold.key != key){
				probe++;
				hold = hold.getNext();
            }

            searchProbe = probe;
        	probe=0;

			return hold.data;
		}

	}
	/**
	* This is the hash function.
	* It takes in the key in string format and returns an integer to be used as an index
	* @param key String 
	* @return integer of hashed string
	*/
	public int hash(String key){

		return Math.abs(key.hashCode( ) % tableSize);
	}

	public int linearPos(int hashIndex, String key){

		while (powerHash[hashIndex] != null && powerHash[hashIndex].key != key){
			probe++;
			hashIndex = (hashIndex + 1) % tableSize;
            
    	}
    	return hashIndex;
	}

	/**
	* The quadratic probing method.
	* @param hashIndex the initial index.
	* @param key the key being inserted or searched for. 
	* @return the next index that was quadratically probed.
	*/
	public int quadraticPos(int hashIndex, String key){
		int i=1;

		while (powerHash[hashIndex] != null && powerHash[hashIndex].key != key){
			probe++;
			hashIndex = (hashIndex + 2*i - 1)%tableSize;
			i++;            
        }
        return hashIndex;
	}

	/**
	* This gives the load factor
	* That is the fraction of the table that is full
	* @return The load factor to 2 sf in a string format
	*/
	public String getLFactor(){

		double loadFactor= (double) occupied/tableSize;

		return String.format("%.2f", loadFactor);
	}

	/**
	* This gives the number of probes during insertion
	* @return The number of insert probes
	*/
	public int getInsertProbe(){

		return insertProbe;
	}

	/**
	* This gives the number of probes done when searching for a key
	* @return The number of search probes
	*/
	public int getSearchProbe(){

		return searchProbe;
	}

	/**
	* This is an inner class that hold the objects to be stored in the hash table
	* Each entry object contains a data item, a key and when seperate chaining is used
	* will have a next entry
	*/
	private class Entry {

		PowerData data;
		Entry next;
		String key;

		/**
		*	New Entry object. 
		*	key will be the PowerData's dateTime string
		*	@param d PowerData item
		*/
		public Entry ( PowerData d ) {

			data = d;
			key=d.getDateTime();
		}

		/**
		* getNext will return the address of the entry object that the current
		* is storing
		* @return an entry object
		*/
		public Entry getNext() {

            return next;
        } 

        /**
        * set Next with set next as the next entry object
        * that is the address of the entry
        * @param next and entry object.
        */
     	public void setNext(Entry next) {

            this.next = next;
        }
	}
}

