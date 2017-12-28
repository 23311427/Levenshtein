import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

// Name: Tony Fu
// Program use: Takes a command-line argument and opens up the dictionary text file with each word on a new line.
// Prints out the network size of a word.
public class NetworkSize
{
	// the dictionary is a text file given by a command-line argument
	public static void main(String[] args)
	{
		String fileName = args[0];
		Scanner scan = null;
		ArrayList<String> network = new ArrayList<String>();
		network.add("listy");
		
		try {
			scan = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// iterate through every word in the network and checking it with each dictionary word.
		for (int j = 0; j < network.size(); j++)
		{
			// currWord is the word we're trying to find friends for
			// curr is the char array of the String.
			// currAlphabet is an integer array that indicates how many times a letter was used
			String currWord = network.get(j).toLowerCase();
			char[] curr = currWord.toCharArray();
			int[] currAlphabet = new int[26];
			// this for loop sets the values for currAphabet.
			for (int i = 0; i < curr.length; i++)
			{
				currAlphabet[curr[i]-97] += 1;
			}
			// iterates through the given dictionary.
			while (scan.hasNextLine())
			{
				String word = scan.nextLine().toLowerCase();
				// if the word is the currWord, we don't need to do anything.
				// if the word is already in our network we don't need to add it again.
				// if the word's length is longer by 1 or more, we can skip this word.
				if (currWord.equals(word) || network.contains(word) || Math.abs(currWord.length()-word.length()) > 1 )
				{
					continue;
				}
				
				// wordArray is the char[] array version of the word
				// wordAlphabet is the integer[] array that looks at how many letters are used in the scanned word
				char[] wordArray = word.toCharArray();
				int[] wordAlphabet = new int[26];
				// this loop sets the values to wordAlphabet
				for (int i = 0; i < wordArray.length; i++)
				{
					wordAlphabet[wordArray[i]-97] += 1;
				}
				
				// go through the current word and see if the word from the dictionary is a friend.
				// 3 cases: For case A and C we can use the smaller word and see if all its letters appear in the bigger word
				//          For case B we must check sequentially.
				//    A) if the current word is smaller than the scanned word, then it's the insertion case for
				//       levenshtein
				//    B) if the current word is the same length as the scanned word, then it's the substitution case.
				//    C) if the current word is bigger than the scanned word, then it's the deletion case
				int letterMatch = 0;
				if (curr.length < wordArray.length)
				{
					for (int i = 0 ; i < curr.length; i++)
					{
						// it the letters in the curr match up at the same letter with the scanned word, there is a match.
						if (currAlphabet[curr[i]-97] > 0 && wordAlphabet[curr[i]-97] > 0)
						{
							letterMatch++;
						}
					}
				}
				else if (curr.length == wordArray.length)
				{
					for (int i = 0; i < curr.length; i++)
					{
						if (currWord.charAt(i) == word.charAt(i))
						{
							letterMatch++;
						}
					}
				}
				else
				{
					for (int i = 0 ; i < wordArray.length; i++)
					{
						// it the letters in the curr match up at the same letter with the scanned word, there is a match.
						if (currAlphabet[wordArray[i]-97] > 0 && wordAlphabet[wordArray[i]-97] > 0)
						{
							letterMatch++;
						}
					}
				}
				
				// case 1: the scanned word's length is smaller than the current word's or equal to the current word's
				// in this case the required matches must be one smaller than the current word's length
				// case 2: the scanned word is bigger, the letter matches must be the same as the current word's length
				if (letterMatch == curr.length - 1 && (wordArray.length < curr.length || wordArray.length == curr.length))
				{
					network.add(word);
				}
				else
				{
					if (letterMatch == curr.length)
					{
						network.add(word);
					}
				}
				
			
			}
			// after all words in the dictionary was scanned. reconstruct the scanner so it starts again.
			try {
				scan = new Scanner(new File(fileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		// prints out our network size
		System.out.println(network.size());
	}
}
