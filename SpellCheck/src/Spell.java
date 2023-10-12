/***
 * Program: Spell.java
 * Author: Nicklas Ellison
 * Description: This file is used to create a "Spell" object that checks the spelling of individual words in a given file and prints suggestions to the console
 *              for misspelled words. These suggestions are found through 4 methods:
 *              	1. Substituting each of the letters with other letters from the alphabet.
 *                  2. Omitting each of the letters. 
 *                  3. Inserting letters into every position of the word.
 *                  4. Swaping the letter order of the words.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Spell {
	
	// Note: I would've included a getter method for "dictionary" but the test file doesn't use dictionary that way, thus making it public is required.
	Dictionary dictionary;																	// dictionary from Dictionary.java used
	private ArrayList<String> wordsToCheck;													// List of words that are spell checked.
	
	/**
	 * Spell: Constructor for the spell object. It takes two file names as input and puts the dictionary file into a dictionary date type and the file to check
	 *        into a list of Strings.
	 * @param s: the first argument from the command line (should be the dictionary file)
	 * @param k: the first argument from the command line (should be the spell check file)
	 */
    public Spell(String s, String k){
        
    	// This try catch branch fails if the given file does not exist.
    	try {
    		File dictWords = new File(s);													// File object created from dictionary file
    		Scanner sc = new Scanner(dictWords);											// New Scanner "sc" scans the file object
    		
    		this.dictionary = new Dictionary();												// New Dictionary made for new spell object.
    		
    		int keys = 0;																	// Inital key value is 0.
    		
    		// Each line of the dictionary file is inserted as a dictionary element while each of the keys are just sequential integers (arbitrary).
    		while (sc.hasNextLine()) {
    			dictionary.insert(keys, sc.nextLine().toLowerCase());						// The dictionary elements are lowercase as to avoid case issues later.
    			keys++;																		// Each key is incremented
    		}	
    		
    		sc.close();																		// The scanner is close.
    	}
    	catch (FileNotFoundException e) {													// The below occurs if the given dictionary file doesn't exist.
    		System.out.print("Invalid dictionary file");
    		e.printStackTrace();
    	}
    	
    	// This try catch branch fails if the given file does not exist.
    	// The below code operates the same as the above try catch branch but uses a list instead of dictionary.
    	try {
    		File checkWords = new File(k);
    		Scanner sc = new Scanner(checkWords);
    		
    		wordsToCheck = new ArrayList<String>();
    		
    		while (sc.hasNextLine()) {
    			wordsToCheck.add(sc.nextLine());
    		}	
    		
    		sc.close();
    	}
    	catch (FileNotFoundException e) {
    		System.out.print("Invalid check file");
    		e.printStackTrace();
    	}
       
    }
    
    /**
     * main: Takes in files and uses the other methods to spell check the given words.
     * @param args: There should be 2 command line arguments the first for the dictionary, the second for the file that is spell checked. 
     */
    public static void main(String[] args) {
    	
        Spell spell = new Spell(args[0], args[1]);											// Spell object created with argument "0" and "1" (given files).
        
        // For each string in spell.wordsToCheck (list of words in the spell check file), the string (word) is spell checked using the "checkSpelling" method.
        for (String word : spell.wordsToCheck) {
        	spell.checkSpelling(word);
        }
    }
    
    /**
     * checkSpelling: the given word is checked to see if it is in the dictionary, if not corrections are suggested.
     * @param word: individual word to be spell checked
     * @return true is returned if the word is spelled correctly, false is returned if the word was spelled incorrectly.
     */
    public boolean checkSpelling(String word){
       if(dictionary.getDictionary().contains(word.toLowerCase())) {						// If the word (in lower case) is in the dictionary, it must be spelled correctly.
    	   System.out.print(word + ": Correct Spelling\n");									// Positive output is print to the screen.
    	   return true;																		// true is returned (spelling is correct).
       }
       else {																				// If the word is not in the dictionary, it is spelled wrong.
    	   suggestCorrections(word);														// The word is sent into the suggestCorrections method.
    	   return false;																	// false is returned (spelling is incorrect).
       }
    }
    
    /**
     * suggestCorrections: Spelling suggestions are given to the user through 4 methods: substituting letters, ommiting letters, inserting letters, and swaping
     *                     the order of letters in the given word.
     * @param word: individual incorrectly spelled word to be checked.
     * @return true is returned if there are suggestions in the dictionary, false is returned if there are no suggestions available.
     */
    public boolean suggestCorrections(String word) {
    	System.out.println(word + ": Incorrect Spelling");									// Negative output is printed
    	
    	String wordToTest = word.toLowerCase();												// The given word is made all lower case.
    	
    	String substitution = correctSpellingSubstitution(wordToTest);						// Suggested word from the substitution method
    	String omission = correctSpellingWithOmission(wordToTest);							// Suggested word from the omission method
    	ArrayList<String> insertionWords = correctSpellingWithInsertion(wordToTest);		// Suggested words from the insertion method
    	String reversal = correctSpellingWithReversal(wordToTest);							// Suggested word from the reversal (swapping) method
    	
    	
    	String correctionsStr = "";															// The corrections string to be printed is initialls empty.
    	
    	ArrayList<String> corrections = new ArrayList<String>();							// List of the suggested corrections is initialized.
    	
    	// The below adds all the suggested corrections to the "corrections" array list.
    	corrections.add(substitution);
    	corrections.add(omission);
    	
    	// Since the insertion method gets many word suggestions, a for each loop is used.
    	for (String s : insertionWords) {
    		corrections.add(s);
    	}
    	
    	corrections.add(reversal);
    	
    	// The correction string to be printed is built from the corrections list
    	for (String s: corrections) {
    		if (s != "") {
    			correctionsStr += s + ", ";													// Each not empty string is added to the correction string with a comma seperating suggested words.
    		}
    	}
    		
    	if (correctionsStr != "") {															// If there are suggestions
    		
    		correctionsStr = correctionsStr.substring(0, correctionsStr.length()-2);		// The last 2 characters, ", ", are removed to not have a hanging comma.
    		
    		System.out.println(word + " => " + correctionsStr);								// The suggestions for the word are then printed to the screen.
    		return true;																	// A true value is returned (there are suggestions).
    	}
    	
    	return false;																		// A false value is returned (there are no suggestions).
    }

    /**
     * correctSpellingSubstitution: This method spell checks the word by substituting each of the letters in the word with other letters from the alphabet.
     * @param word
     * @return The suggested word is returned 
     */
    private String correctSpellingSubstitution(String word) {
    	
    	final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();					// An unchangable char array of the alphabet is created.
    	
    	char[] newWordArr = word.toCharArray();												// A character array is created to be edited as such below.

    	for(int i = 0; i < newWordArr.length; i++) {										// Every letter in the word array is looped through.
    		for (char c : alphabet) {														// Each letter from the alphabet is swaped in for each letter of the word.
    			newWordArr[i] = c;															// Letter c in swapped in for letter at position i.
    			String newWord = String.valueOf(newWordArr);								// The elemenets of the array are turned into a string.
    			if(dictionary.getDictionary().contains(newWord)) {							// The word is returned if it is in the dictionary (it is spelled correctly).
    				return newWord;
    			}
    		}
    		newWordArr = word.toCharArray();												// The word array is reset so the changed letter returns to what it was prior to swaps.
    	}
    	
        return "";																			// A blank character is returned if there are no suggestions.
    }

    /**
     * correctSpellingWithOmission: This method spell checks the word by removing each of the letters in the word to see if the result is a real word.
     * @param word
     * @return The suggested word is returned 
     */
    private String correctSpellingWithOmission(String word) {
    	
    	char[] newWordArr = word.toCharArray();												// A character array is created to be edited as such below.
    	
    	for(int i = 0; i < newWordArr.length; i++) {										// The letter in the array is looped through.
    		newWordArr[i] = ' ';															// The letter is replaced by a blank character.
    		String newWord = String.valueOf(newWordArr);									// The elemenets of the array are turned into a string.
    		newWord = newWord.replaceAll("\\s+", "");										// Regex is used to remove the blank character.
			if(dictionary.getDictionary().contains(newWord)) {								// The word is returned if it is in the dictionary (it is spelled correctly).
				return newWord;
			}
			newWordArr = word.toCharArray();												// Array is reset to the removed letter is returned for next loop.
    	}
    	
    	return "";																			// A blank character is returned if there are no suggestions.
    }

    /**
     * correctSpellingWithInsertion: This method spell checks the word by adding new letters to each position of the string (including the front and end).
     * @param word
     * @return List of suggested words is returned 
     */
    private ArrayList<String> correctSpellingWithInsertion(String word) {
    	
    	ArrayList<String> insertionWords = new ArrayList<String>();							// New list is created to be returned
    	
    	final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();					// An unchangable char array of the alphabet is created.
    	
    	String frontSegment, backSegment;													// Two new strings for the front and back segements of the new word are created
    	
    	int position = word.toCharArray().length;											// The length of the word to be given suggestions is stored in an integer.
    	
    	// The for loop iterates for all the letters of the word + 1. 
    	// The + 1 is to accomidate for a letter needing to being placed at the end of the word.
    	for(int i = 0; i < position + 1; i++) {
    		
    		frontSegment = word.substring(0, i);											// The front segment of the new word is between 0 (before the word) and i
    		backSegment = word.substring(i, word.length());									// The back segment of the new word is between i and the end of the word.
    		
    		for (char c : alphabet) {
    			String newWord = frontSegment + c + backSegment;							// Each letter of the alphabet is placed between the front and back segments.
    			if(dictionary.getDictionary().contains(newWord)) {							// If this newWord string results in a correctly spelt word, it is added to the array list.
    				insertionWords.add(newWord);
    			}
    		}
    	}
    	
    	return insertionWords;																// The array list is returned.
    }
    
    /**
     * correctSpellingWithReversal: This method spell checks the word by swaping each of the letters with the one to the right to see if the result is a real word.
     * @param word
     * @return The suggested word is returned 
     */
    private String correctSpellingWithReversal(String word) {
    	
    	char[] newWordArr = word.toCharArray();
    	char swapF, swapB;																	// Characters swapF (front) and swapB (back) are initialized.
    	
    	// The for loop is looped for the length of the word minus 1 as the last letter does not need to be compared with anything other than the one prior.
    	for(int i = 0; i < newWordArr.length - 1; i++) {				
    		swapF = newWordArr[i];															// swapF is declared as the letter at position i
    		swapB = newWordArr[i + 1];														// swapB is declared as the letter at position i + 1 (the following letter to swapF)
    		
    		// The characters are swapped to the oposite positions
    		newWordArr[i] = swapB;
    		newWordArr[i + 1] = swapF;
    		
    		String newWord = String.valueOf(newWordArr);									// The char array is converted to a string.
    		
			if(dictionary.getDictionary().contains(newWord)) {								// The newWord is returned if it is a valid word.
				return newWord;
			}
			
			newWordArr = word.toCharArray();												// The word array is reset for the next swap.
    	}
    	
    	return "";																			// A blank string is returned if no suggestion was found.
    }

}