/***
 * Program: Dictionary.java
 * Author: Nicklas Ellison
 * Description: This file uses a hashtable to impliment a (partial) dictionary object. There currently isn't a way to remove elements but that wasn't necessary
 *              for the purposes of the assignment. It is implimeneted with a majority of the Dictionary ADT methods discussed in class.
 */


import java.util.Hashtable;

public class Dictionary implements DictionaryADT{
	
	private Hashtable<Integer, String> dict;													// The dictionary itself is treated as a hashtable.
	private int size;																			// This value is the total size of the dictionary by elements.
	
	public Dictionary() {
		this.dict = new Hashtable<Integer, String>();
		this.size = 0;																			// The constructor initializes the size as 0.
	}
	
	/**
	 * getDictionary: getter method that returns the hashtable.
	 */
	public Hashtable<Integer, String> getDictionary(){
        return this.dict;
    }
	
	/**
	 * size: getter method that returns the total size of the dictionary.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * find: An element from the dictionary is found based on it's key.
	 * @param key
	 */
	public String find(int key) {
		return this.dict.get(key);
	}
	
	/**
	 * insert: adds elements to the dictionary object
	 * @param key, value
	 */
	public void insert(int key, String value) {
		this.dict.put(key, value);																// Hashtable put method used to place value along with key
		this.size += 1;																			// The total size of the dictionary increases
	}
	
}
