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

