import java.util.*;

/**
 * Implementation of the Rabin-Karp algorithm for pattern searching
 * in a text, with functional testing and efficiency testing methods.
 */
class RabinKarpAlgo {

    // Counter for the number of character comparisons
    int cpt = 0;

    // Base used for hash calculation
    int base = 101;

    /**
     * Main method launching tests of the Rabin-Karp algorithm
     */
    void principal() {
        testRabinKarpAlgo();
        testRabinKarpAlgoEfficiency();
    }

    /**
     * Computes the hash of a word
     *
     * @param word The word for which to calculate the hash
     * @return The hash value of the word
     */
    Double calculHach(String word) {
        Double hash = 0.0;
        for (int i = 0; i < word.length(); i++) {
            hash = hash + word.charAt(i) * Math.pow(base, (word.length() - i));
        }
        return hash;
    }

    /**
     * Searches for all occurrences of a pattern in a text
     * using the Rabin-Karp algorithm
     *
     * @param text    The text in which to search the pattern
     * @param pattern The pattern to search for
     * @return A list containing the starting positions of the pattern in the text
     */
    ArrayList<Integer> rabinKarpAlgo(ArrayList<Character> text, String pattern) {

        ArrayList<Integer> occurrences = new ArrayList<>();

        int n = text.size();
        int m = pattern.length();

        if (m > n || m == 0) {
            return occurrences;
        }

        long hashPattern = 0;
        long hashText = 0;
        long power = 1;

        for (int i = 0; i < m - 1; i++) {
            power *= base;
        }

        // Compute initial hashes
        for (int i = 0; i < m; i++) {
            hashPattern += pattern.charAt(i) * Math.pow(base, m - 1 - i);
            hashText += text.get(i) * Math.pow(base, m - 1 - i);
        }

        // Traverse the text
        for (int k = 0; k <= n - m; k++) {

            // Compare hashes
            if (hashText == hashPattern) {

                boolean ok = true;
                for (int j = 0; j < m; j++) {

                    cpt++; // character comparison

                    if (text.get(k + j) != pattern.charAt(j)) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    occurrences.add(k);
                }
            }

            // Update hash
            if (k < n - m) {
                hashText = (hashText - text.get(k) * power) * base
                         + text.get(k + m);
            }
        }

        return occurrences;
    }

    /**
     * Generates a random text composed of uppercase letters
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
     * Tests a specific case of the Rabin-Karp algorithm
     *
     * @param text    The text in which to search the pattern
     * @param pattern The pattern to search for
     */
    void testCasRabinKarpAlgo(ArrayList<Character> text, String pattern) {

        cpt = 0;
        ArrayList<Integer> result = rabinKarpAlgo(text, pattern);

        System.out.println("Text   : " + text);
        System.out.println("Pattern: " + pattern);
        System.out.println("Positions found: " + result);
        System.out.println("Cpt (character comparisons) = " + cpt);
        System.out.println("TEST OK\n");
    }

    /**
     * Tests the Rabin-Karp algorithm with multiple cases
     */
    void testRabinKarpAlgo() {

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

        System.out.println("\n ||  TEST RABIN-KARP ALGO  || ");

        System.out.println("\n *** NORMAL CASES ***");
        testCasRabinKarpAlgo(text1, pattern1);
        testCasRabinKarpAlgo(text2, pattern2);
        testCasRabinKarpAlgo(text3, pattern3);

        System.out.println("\n *** EDGE CASES ***");
        testCasRabinKarpAlgo(text4, pattern4);
        testCasRabinKarpAlgo(text5, pattern5);
    }

    /**
     * Tests the efficiency of the Rabin-Karp algorithm by measuring
     * execution time and the number of comparisons
     * for texts of increasing size
     */
    void testRabinKarpAlgoEfficiency() {

        ArrayList<Character> text;
        int n;
        long t1, t2, diffT;

        n = (int) Math.pow(2, 15); // starting size

        System.out.println("\n ||  RABIN-KARP ALGO EFFICIENCY  ||\n");

        for (int i = 1; i <= 12; i++) {

            text = remplissage(n);
            String pattern = "ABE";

            cpt = 0;

            System.out.println("Text size = " + n);

            t1 = System.nanoTime();
            rabinKarpAlgo(text, pattern);
            t2 = System.nanoTime();

            diffT = t2 - t1;

            System.out.println("Time = " + diffT + " ns");
            System.out.println("Cpt  = " + cpt);
            System.out.println("------------");

            n = n * 2;
        }
    }
}
