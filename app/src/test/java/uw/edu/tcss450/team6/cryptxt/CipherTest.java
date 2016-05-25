package uw.edu.tcss450.team6.cryptxt;

import org.junit.Before;
import org.junit.Test;
import uw.edu.tcss450.team6.cryptxt.Cipher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jon on 5/24/2016.
 */
public class CipherTest {

    private Cipher cipher;
    private String input;

    @Before
    public void setUp() {
        cipher = new Cipher();
        input = "HELLOWORLD";
    }

    @Test
    public void testShiftCipher() {
        String key = "4";
        String s1 = cipher.caesarShift(input, key);
        String s2 = cipher.caesarShiftUndo(s1, key);
        assertEquals(input, s2);
    }

    @Test
    public void testVigenereCipher() {
        String key = "apple";
        String s3 = cipher.vigenere(input, key);
        String s4 = cipher.vigenereUndo(s3, key);
        assertEquals(input, s4);
    }

    @Test
    public void testSubstitutionCipher() {
        String key = "zxcvbnmasdfghjklqwertyuiop";
        String s5 = cipher.substitution(input, key);
        String s6 = cipher.substitutionUndo(s5, key);
        assertEquals(input, s6);
    }

    @Test
    public void testPermutationCipher() {
        String key = "3241";
        String s7 = cipher.permutation(input, key);
        String s8 = cipher.permutationUndo(s7, key);
        assertTrue(s8.startsWith(input));
    }

    @Test
    public void testAdvancedCipher() {
        String key = "zxcvbnmasdfghjklqwertyuiop563241";
        String s9 = cipher.advanced(input, key);
        String s10 = cipher.advancedUndo(s9, key);
        assertTrue(s10.startsWith(input));
    }
}
