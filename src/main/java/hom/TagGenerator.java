package hom;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TagGenerator implements Poems {
	String material;

	static Set<String> words = new HashSet<String>();

	static Random rand = new Random();

	static int minNumberOfWords = 3;
	static int maxNumberOfWords = 7;

	public TagGenerator() {

		int whichPoem = rand.nextInt(8) + 1;
		choosePoem(whichPoem);

		String[] wordsArray = material.split("\\s+");
		words.addAll(Arrays.asList(wordsArray));
	}

	private static String processWord(String word) {
		String first = word.substring(0,1).toUpperCase();
		if(word.length()>1) {
			return first + word.substring(1,word.length());
		} else
			return first;
	}

	private static String getRandomWord(Set<String> words) {
		int i = 0;
		int stop = rand.nextInt(words.size());
		for(String word : words) {
			if(i == stop)
				return word;
			i++;
		}
		return words.toString();
	}

	public String generateTag() {
		String tag = "";
		String word;
		int i = 0;

		while(i < rand.nextInt(maxNumberOfWords - minNumberOfWords + 1) + minNumberOfWords) {

			word = getRandomWord(words);
			word = processWord(word);

			tag += word;
			i++;
		}

		return tag;
	}

	void choosePoem(int whichOne) {
		switch(whichOne) {
		case 1: material = ONE; break;
		case 2: material = TWO; break;
		case 3: material = THREE; break;
		case 4: material = FOUR; break;
		case 5: material = FIVE; break;
		case 6: material = SIX; break;
		default: material = OHOH;
		}
	}

	void displayAvailableWords() {
		for(String word : words)
			System.out.println(word);
	}

}
