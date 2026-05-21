import java.util.*;

/**
 * Knuth-Morris-Pratt substring search algorithm
 */
class KMPAlgo {
    
    int cpt = 0; // Variable for counting comparisons
    
    /**
     * Main method launching KMP algorithm tests
     */
    void principal() {
        testKMPAlgo();
        testKMPAlgoEfficiency();
    }

    /**
     * Searches for all occurrences of a pattern in a text
     * using the KMP algorithm.
     *
     * @param text    The text in which to search the pattern
     * @param pattern The pattern to search for
     * @return An ArrayList<Integer> containing the starting positions of the pattern in the text
     */
    ArrayList<Integer> kmpAlgo(ArrayList<Character> text, String pattern) {

        ArrayList<Integer> result = new ArrayList<>();

        int n = text.size();
        int m = pattern.length();
        boolean emptyOrTooShort = false;

        if (m == 0 || n < m) {
            emptyOrTooShort = true;
        }

        int[] lps = LPS(pattern);

        int i = 0; // index in the text
        int j = 0; // index in the pattern
        if (!emptyOrTooShort) {
            while (i < n) {
                
                cpt++;

                if (text.get(i) == pattern.charAt(j)) {
                    i++;
                    j++;
                }

                if (j == m) {
                    // Starting position of the pattern
                    result.add(i - m + 1);
                    j = lps[j - 1];
                        
                } else if (i < n && text.get(i) != pattern.charAt(j)) {
                    
                    cpt++;
                    
                    if (j != 0) {
                        j = lps[j - 1];
                    } else {
                        i++;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Computes the LPS (Longest Prefix Suffix) array for the pattern.
     * This array indicates, for each position of the pattern, the length
     * of the longest proper prefix which is also a suffix.
     *
     * @param pattern The pattern used to build the LPS array
     * @return An integer array representing the LPS values
     */
    int[] LPS(String pattern) {

        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0; // length of the current longest prefix-suffix
        int i = 1;

        while (i < m) {

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * Tests a specific case of the KMP algorithm.
     * 
     * @param text    The text as an ArrayList<Character> in which to search the pattern
     * @param pattern The pattern to search for in the text
     * 
     * This method calls kmpAlgo to find all occurrences of the pattern
     * and prints the positions found along with a confirmation message.
     */
    void testCasKmpAlgo(ArrayList<Character> text, String pattern) {

        ArrayList<Integer> result = kmpAlgo(text, pattern);

        System.out.println("Positions found: " + result);
        System.out.println(" TEST OK ");
    }
    
    /**
     * Tests the KMP algorithm with multiple cases.
     *
     * It tests normal cases and edge cases,
     * then prints the positions found.
     */
    void testKmpAlgo() {

        // Normal cases
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

        System.out.println("\n ||  TEST KMP ALGO  || ");

        System.out.println("\n *** NORMAL CASES ***");
        testCasKMPAlgo(text1, pattern1);
        testCasKMPAlgo(text2, pattern2);
        testCasKMPAlgo(text3, pattern3);

        System.out.println("\n *** EDGE CASES ***");
        testCasKMPAlgo(text4, pattern4);
        testCasKMPAlgo(text5, pattern5);
    }
     
    /**
     * Creates an ArrayList of given size filled with random letters
     * 
     * @param size The size of the array to fill
     * @return text The ArrayList of size 'size' filled with random letters
     */
    ArrayList<Character> remplissage(int size){
        
        ArrayList<Character> text = new ArrayList<>();
        int i =0;
        
        while (i < size){
            Random randInt = new Random();
            char randomLetter = (char) ('A' + randInt.nextInt(26));

            text.add(Character.valueOf(randomLetter));
            i++;
        }
        return text;
    }

    /**
     * Tests the efficiency of the KMP algorithm by measuring the number of comparisons
     * and execution time for progressively larger texts.
     * Texts are generated randomly using the remplissage method.
     * The searched pattern is "ABE".
     */
    void testKmpAlgoEfficiency() {

        ArrayList<Character> text;
        int n;
        long t1, t2, diffT;

        n = (int) Math.pow(2, 15); // Starting size

        System.out.println("\n ||  KMP ALGO EFFICIENCY  ||\n");

        for (int i = 1; i <= 12; i++) {

            text = remplissage(n); // Generate random text
            String pattern = "ABE"; // Fixed pattern

            cpt = 0; // Reset global counter

            System.out.println("Text size = " + n);

            // Measure execution time
            t1 = System.nanoTime();
            kmpAlgo(text, pattern);
            t2 = System.nanoTime();

            diffT = t2 - t1;

            System.out.println("Time = " + diffT + " ns");
            System.out.println("Cpt = " + cpt);
            System.out.println("------------");

            n = n * 2; // Double the text size
        }
    }
}
