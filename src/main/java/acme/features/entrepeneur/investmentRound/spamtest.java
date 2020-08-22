
package acme.features.entrepeneur.investmentRound;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class spamtest {

	public static void main(final String[] args) {
		String test = "sexsex hola sex hola";
		spamtest.spamChecker(test);
	}

	public static boolean spamChecker(final String str) {
		String strFormatted = str.toLowerCase().trim().replaceAll("\\s+", " ");

		Double spamThreshold = 2.5;
		Set<String> spamWords = new HashSet<>();
		spamWords.addAll(Arrays.asList("sex, hard core, viagra, cialis, nigeria, you've won, million dollar".toString().split(", ")));
		spamWords.addAll(Arrays.asList("sexo, duro, viagra, cialis, nigeria, has ganado, millón de dólares".toString().split(", ")));

		int spamWordsCounter = 0;

		boolean isSpam = false;

		for (String word : spamWords) {
			if (strFormatted.contains(word)) {
				int i = 0;
				while ((i = str.indexOf(word, i)) != -1) {
					spamWordsCounter++;
					i++;

				}
			}
		}

		if (spamWordsCounter > 0) {
			double spamPercentage = Double.valueOf(spamWordsCounter) / strFormatted.split(" ").length * 100;
			isSpam = spamPercentage >= spamThreshold;
		}

		return isSpam;
	}
}
