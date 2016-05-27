package uw.edu.tcss450.team6.cryptxt;

/**
 * The Cipher class provides methods to encrypt and decrypt Strings with
 * various Classical Ciphers.
 *
 * @author Jonathan Hughes
 * @date 19 April 2016
 */
public class Cipher {

    public Cipher () {
    }

    /**
     * @param input String to modify
     * @return String with all capital letters
     */
    public String strToCapLetters(String input) {
        String s = "";
        for (int i = 0; i < input.length(); i++) {
            Character ch = input.charAt(i);
            if(Character.isLetter(ch)) {
                s += Character.toUpperCase(ch);
            }
        }
        return s;
    }

    /**
     * @param input the plaintext
     * @param key the cipher's key
     * @return ciphertext encrypted with a caesar shift
     *         letter 'A' is 0, so shifting 3, would go to 3 or letter 'D', etc.
     */
    public String caesarShift(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            int k = Integer.parseInt(key);
            for (int i= 0; i < s.length(); i++) {
                char c = s.charAt(i);
                c = (char) (c + k);
                if (c > 'Z') {
                    c = (char) (c - 26);
                }
                r += c;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the ciphertext
     * @param key the cipher's key
     * @return plaintext decrypted by a caesar shift
     */
    public String caesarShiftUndo(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            int k = Integer.parseInt(key);
            for (int i= 0; i < s.length(); i++) {
                char c = s.charAt(i);
                c = (char) (c - k);
                if (c < 'A') {
                    c = (char) (c + 26);
                }
                r += c;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the plaintext
     * @param key the cipher's key
     * @return ciphertext encrypted by a vigenere cipher
     *         uses a word as they key, with each letter
     *         being the amount to shift each character in the plaintext.
     *        (banana = 1, 0, 13, 0, 13, 0 would shift the first letter by 1,
     *        second letter by 0, third by 13, etc., and repeat on the 7th
     *        letter (since banana is 6 letters long)
     */
    public String vigenere(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            String ks = strToCapLetters(key);
            int len = ks.length();
            for (int i= 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int k = ks.charAt(i % len) - 'A';
                c = (char) (c + k);
                if (c > 'Z') {
                    c = (char) (c - 26);
                }
                r += c;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the ciphertext
     * @param key the cipher's key
     * @return plaintext decrypted by a vigenere cipher
     */
    public String vigenereUndo(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            String ks = strToCapLetters(key);
            int len = ks.length();
            for (int i= 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int k = ks.charAt(i % len) - 'A';
                c = (char) (c - k);
                if (c < 'A') {
                    c = (char) (c + 26);
                }
                r += c;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the plaintext
     * @param key the cipher's key
     * @return ciphertext encrypted by a substitution cipher
     *         swaps each letter of an alphabet with
     *         another letter, such as below would change a to z, h to s, etc.:
     *         a b c d e f g h i j k l m n o p q r s t u v w x y z
     *         z y x w v u t s r q p o n m l k j i h g f e d c b a
     */
    public String substitution(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            String ks = strToCapLetters(key);
            for (int i= 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int x = ks.indexOf(""+c);
                char d = (char) (x + 'A');
                r += d;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the ciphertext
     * @param key the cipher's key
     * @return plaintext decrypted by a substitution cipher
     */
    public String substitutionUndo(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            String ks = strToCapLetters(key);
            for (int i= 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int x = c - 'A';
                char d = ks.charAt(x);
                r += d;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the plaintext
     * @param key the cipher's key
     * @return ciphertext encrypted by a permutation cipher
     *         swaps the placement of letters in a certain
     *         pattern (for example, put the 1st letter in the 4th position,
     *         2nd letter in the 6th position, etc.) If the key is
     *         {3,5,1,6,4,2}, then “thread” would encrypt to “ratdeh” with
     *         ‘r’ being the 3rd letter, ‘a’ being the 5th etc.
     */
    public String permutation(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            int size = key.length();

            while (s.length() % size != 0) {
                s += (char)((Math.random() * 26) + 'A');
            }

            int len = s.length();

            for (int i= 0; i < len; i++) {
                int x = key.charAt(i % size) - 48;
                char c = s.charAt(((i / size) * size) + x - 1);
                r += c;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the ciphertext
     * @param key the cipher's key
     * @return plaintext decrypted by a permutation cipher
     */
    public String permutationUndo(String input, String key) {
        String r = "";
        try {
            String s = strToCapLetters(input);
            int size = key.length();

            while (s.length() % size != 0) {
                s += (char)((Math.random() * 26) + 'A');
            }


            int len = s.length();

            for (int i= 0; i < len; i++) {
                int x = (i / size) * size;
                int y = (i % size) + 1;
                int z = key.indexOf(y + '0');
                char c = s.charAt(x + z);
                r += c;
            }
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the plaintext
     * @param key the cipher's key
     * @return plaintext encrypted by a substitution and permutation cipher
     */
    public String advanced(String input, String key) {
        String r = "";
        try {
            String key1 = key.substring(0, 26);
            String key2 = key.substring(26);
            String input2 = substitution(input, key1);
            r = permutation(input2, key2);
        } catch (Exception e) {
            r = "Invalid key";
        }
        return r;
    }

    /**
     * @param input the ciphertext
     * @param key the cipher's key
     * @return plaintext decrypted by a substitution and permutation cipher
     */
    public String advancedUndo(String input, String key) {
        String r = "";
        try {
            String key1 = key.substring(0, 26);
            String key2 = key.substring(26);
            String input2 = substitutionUndo(input, key1);
            r = permutationUndo(input2, key2);
        } catch (Exception e) {
                r = "Invalid key";
        }
        return r;
    }
}

