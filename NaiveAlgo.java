import java.util.*;

/**
 * Class NaiveAlgo
 *
 * This class implements the naive string matching algorithm.
 * It contains a main method for testing the algorithm and the method
 * naiveAlgo which returns all positions of the pattern in the text.
 */
class NaiveAlgo {
    int cpt = 0; // Variable for counting comparisons

/**
 * Main test method.
 * Creates a text and a pattern, then searches for all occurrences
 * of the pattern in the text using the naive algorithm. Prints the positions found.
 */
    void principal() {
        testNaiveAlgo();
        testNaiveAlgoEfficiency();
    }

/**
 * Naive string matching algorithm.
 *
 * Iterates through the text character by character and compares each
 * substring of length equal to the pattern with the pattern. Returns
 * a list of starting positions where the pattern appears.
 *
 * @param text    The text to search in, as an ArrayList<Character>
 * @param pattern The pattern to search for
 * @return An ArrayList<Integer> containing all positions where the pattern occurs
 */
    ArrayList<Integer> naiveAlgo(ArrayList<Character> text, String pattern) {

        ArrayList<Integer> ret = new ArrayList<>();
        int n = text.size();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) { // Traverse the text
            boolean match = true;

            for (int j = 0; j < m; j++) { // Compare character by character
                cpt++; // count each comparison
                if (text.get(i + j) != pattern.charAt(j)) {
                    match = false;
                    break;
                }
            }

            if (match) {  
                ret.add(i + 1);
            }
        }
        return ret;
    }

/**
 * Tests the naive algorithm with multiple cases.
 * 
 * It tests both normal cases and edge cases, and prints the results.
 * Normal cases include several texts and patterns.
 * Edge cases include very short texts or patterns.
 */
    void testNaiveAlgo() {

        // Normal Cases
        ArrayList<Character> text1 = new ArrayList<>(Arrays.asList('t','u','t','o','t','o'));
        String pattern1 = "to";

        ArrayList<Character> text2 = new ArrayList<>(Arrays.asList('s','j','e','c','a','d'));
        String pattern2 = "cad";

        ArrayList<Character> text3 = new ArrayList<>(Arrays.asList('t','u','t','o','t','o','e','e','r','e'));
        String pattern3 = "e";

        // Edge Case
        ArrayList<Character> text4 = new ArrayList<>(Arrays.asList('o'));
        String pattern4 = "o";

        System.out.println("\n ||  TEST NAIVE ALGO  || ");

        System.out.println("\n *** NORMAL CASES ***");
        testCasNaiveAlgo(text1, pattern1);
        testCasNaiveAlgo(text2, pattern2);
        testCasNaiveAlgo(text3, pattern3);

        System.out.println("\n *** EDGE CASES ***");
        testCasNaiveAlgo(text4, pattern4);
    }

/**
 * Tests a specific case of the naive algorithm.
 * 
 * @param text    The text as an ArrayList<Character> to search the pattern in
 * @param pattern The pattern to search for in the text
 * 
 * This method calls naiveAlgo to find all occurrences of the pattern
 * and prints the positions found along with a confirmation message.
 */
    void testCasNaiveAlgo(ArrayList<Character> text, String pattern) {

        ArrayList<Integer> resultat = naiveAlgo(text, pattern);

        System.out.println("Positions found: " + resultat);
        System.out.println(" TEST OK ");
    }
    
/**
 * Tests the efficiency of the naive algorithm by measuring the number of comparisons
 * and the execution time for progressively larger texts.
 * The texts are generated randomly using the remplissage method.
 * The searched pattern is fixed ("ABE").
 */
    void testNaiveAlgoEfficiency() {

        ArrayList<Character> text;
        int n;
        long t1, t2, diffT;

        n = (int) Math.pow(2, 15); // Starting size

        System.out.println("\n ||  NAIVE ALGO EFFICIENCY  ||\n");

        for (int i = 1; i <= 12; i++) {

            text = remplissage(n); // Generate random text

            String pattern = "ABE"; // Fixed pattern

            cpt = 0; // Reset global counter

            System.out.println("Text size = " + n);

            // Measure execution time
            t1 = System.nanoTime();
            naiveAlgo(text, pattern);
            t2 = System.nanoTime();

            diffT = t2 - t1;

            System.out.println("Time = " + diffT + " ns");
            System.out.println("Cpt = " + cpt);
            System.out.println("------------");

            n = n * 2; // Double the text size
        }
    }

/**
 * Creates an ArrayList of a given size filled with random letters.
 * 
 * @param taille The size of the array to fill
 * @return text  The ArrayList of size 'taille' filled with random letters
 */
    ArrayList<Character> remplissage(int taille) {
        ArrayList<Character> text = new ArrayList<>();
        int i = 0;
        while (i < taille) {
            Random randInt = new Random();
            char randomLetter = (char) ('A' + randInt.nextInt(26));

            text.add(Character.valueOf(randomLetter));
            i++;
        }
        return text;
    }
}
