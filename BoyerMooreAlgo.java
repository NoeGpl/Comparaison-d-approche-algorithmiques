import java.util.*;

/**
 * Implementation of the Boyer-Moore algorithm for pattern searching
 * in a text.
 *
 * This class also contains functional testing and efficiency testing methods.
 */
class BoyerMooreAlgo {

    // Total number of possible characters
    int nb_caracteres = 256;
    
    // Counter for the number of character comparisons
    int cpt = 0;

    /**
     * Main method launching tests of the Boyer-Moore algorithm.
     */
    void principal() {
        testBoyerMooreAlgo();
        testBoyerMooreAlgoEfficiency();
    }

    /**
     * Generates a random text composed of uppercase letters.
     *
     * @param size The size of the text to generate
     * @return An ArrayList<Character> representing the generated text
     */
    ArrayList<Character> remplissage(int size) {
        ArrayList<Character> text = new ArrayList<>();
        Random randInt = new Random();

        for (int i = 0; i < size; i++) {
            char randomLetter = (char) ('A' + randInt.nextInt(26));
            text.add(randomLetter);
        }
        return text;
    }

    /**
     * Creates the bad character table used by the Boyer-Moore algorithm.
     * For each character, this table stores the last position
     * of that character in the pattern.
     *
     * @param pattern The pattern used to build the table
     * @return An integer array representing the bad character table
     */
    int[] creerTableMauvaisCar(String pattern) {
        int m = pattern.length();
        int[] badChar = new int[nb_caracteres];

        for (int i = 0; i < nb_caracteres; i++) {
            badChar[i] = -1;
        }

        for (int i = 0; i < m; i++) {
            char c = pattern.charAt(i);
            badChar[(int) c] = i;
        }

        return badChar;
    }

    /**
     * Searches for all occurrences of a pattern in a text
     * using the Boyer-Moore algorithm.
     *
     * @param text    The text in which to search the pattern
     * @param pattern The pattern to search for
     * @return A list containing the starting positions of the pattern in the text
     */
    ArrayList<Integer> boyerMooreAlgo(ArrayList<Character> text, String pattern) {

        ArrayList<Integer> occurrences = new ArrayList<>();

        int n = text.size();
        int m = pattern.length();

        if (m == 0 || n == 0 || m > n) {
            return occurrences;
        }

        int[] badChar = creerTableMauvaisCar(pattern);

        int s = 0; // shift of the pattern relative to the text

        while (s <= n - m) {
            int j = m - 1;

            // Compare from right to left
            while (j >= 0 && pattern.charAt(j) == text.get(s + j)) {
                cpt++;
                j--;
            }

            if (j >= 0 && pattern.charAt(j) != text.get(s + j)) {
                cpt++;
            }

            // Pattern found
            if (j < 0) {
                occurrences.add(s);

                if (s + m < n) {
                    char c = text.get(s + m);
                    int bcIndex = badChar[(int) c];
                    s = s + (m - bcIndex);
                } else {
                    s = s + 1;
                }
            } 
            // Bad character rule
            else {
                char c = text.get(s + j);
                int bcIndex = badChar[(int) c];

                int shift = j - bcIndex;
                if (shift < 1) {
                    shift = 1;
                }
                s = s + shift;
            }
        }

        return occurrences;
    }

    /**
     * Tests a specific case of the Boyer-Moore algorithm.
     *
     * @param text    The text in which to search the pattern
     * @param pattern The pattern to search for
     */
    void testCasBoyerMooreAlgo(ArrayList<Character> text, String pattern) {

        cpt = 0;
        ArrayList<Integer> result = boyerMooreAlgo(text, pattern);

        System.out.println("Text   : " + text);
        System.out.println("Pattern: " + pattern);
        System.out.println("Positions found: " + result);
        System.out.println("Cpt (character comparisons) = " + cpt);
        System.out.println("TEST OK\n");
    }

    /**
     * Tests the Boyer-Moore algorithm with multiple cases,
     * including normal and edge cases.
     */
    void testBoyerMooreAlgo() {

        ArrayList<Character> text1 = new ArrayList<>(Arrays.asList('t','u','t','o','t','o'));
        String pattern1 = "to";

        ArrayList<Character> text2 = new ArrayList<>(Arrays.asList('s','j','e','c','a','d'));
        String pattern2 = "cad";

        ArrayList<Character> text3 = new ArrayList<>(Arrays.asList('t','u','t','o','t','o','e','e','r','e'));
        String pattern3 = "e";

        // Edge cases
        ArrayList<Character> text4 = new ArrayList<>(Arrays.asList('o'));
        String pattern4 = "o";

        ArrayList<Character> text5 = new ArrayList<>(Arrays.asList('a','b','a','b','a','b','a'));
        String pattern5 = "aba";

        System.out.println("\n ||  TEST BOYER-MOORE ALGO  || ");

        System.out.println("\n *** NORMAL CASES ***");
        testCasBoyerMooreAlgo(text1, pattern1);
        testCasBoyerMooreAlgo(text2, pattern2);
        testCasBoyerMooreAlgo(text3, pattern3);

        System.out.println("\n *** EDGE CASES ***");
        testCasBoyerMooreAlgo(text4, pattern4);
        testCasBoyerMooreAlgo(text5, pattern5);
    }

    /**
     * Tests the efficiency of the Boyer-Moore algorithm by measuring
     * execution time and the number of character comparisons
     * for texts of increasing size.
     */
    void testBoyerMooreAlgoEfficiency() {

        ArrayList<Character> text;
        int n;
        long t1, t2, diffT;

        n = (int) Math.pow(2, 15);

        System.out.println("\n ||  BOYER-MOORE ALGO EFFICIENCY  ||\n");

        for (int i = 1; i <= 12; i++) {

            text = remplissage(n);
            String pattern = "ABE";

            cpt = 0;

            System.out.println("Text size = " + n);

            t1 = System.nanoTime();
            boyerMooreAlgo(text, pattern);
            t2 = System.nanoTime();

            diffT = t2 - t1;

            System.out.println("Time = " + diffT + " ns");
            System.out.println("Cpt = " + cpt);
            System.out.println("------------");

            n = n * 2;
        }
    }
}
