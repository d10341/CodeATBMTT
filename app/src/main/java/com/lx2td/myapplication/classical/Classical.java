package com.lx2td.myapplication.classical;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Classical {
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String ceasarCipher(String plaintext, int key){
        plaintext = plaintext.toUpperCase();
        String ciphertext = "";
        for(char c : plaintext.toCharArray())
        {
            if (c == ' ') ciphertext += " ";
            else ciphertext += chars.charAt((chars.indexOf(c)+key)%26);
        }
        return ciphertext;
    }

    public static String ceasarDecipher(String ciphertext, int key){
        ciphertext = ciphertext.toUpperCase();
        String plaintext = "";
        for(char c : ciphertext.toCharArray())
        {
            if (c == ' ') plaintext += " ";
            else{
                int tmp = (chars.indexOf(c)-key)%26;
                if (tmp < 0) tmp = 26 + tmp;
                plaintext += chars.charAt(tmp);
            }
        }
        return plaintext;
    }

    public static String vigenereLoopCipher(String plaintext, String key){
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        String ciphertext = "";
        String k = key;
        while (k.length() < plaintext.length())
        {
            k += key;
        }
        for(int i =0; i < plaintext.length(); i++)
        {
            if (plaintext.charAt(i) == ' ') ciphertext += " ";
            else ciphertext += chars.charAt((chars.indexOf(plaintext.charAt(i))+chars.indexOf(k.charAt(i)))%26);
        }
        return ciphertext;
    }

    public static String vigenereLoopDecipher(String ciphertext, String key){
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        String plaintext = "";
        String k = key;
        while (k.length() < ciphertext.length())
        {
            k += key;
        }
        for(int i =0; i < ciphertext.length(); i++)
        {
            if (ciphertext.charAt(i) == ' ') plaintext += " ";
            else{
                int tmp = (chars.indexOf(ciphertext.charAt(i))-chars.indexOf(k.charAt(i)))%26;
                if (tmp < 0) tmp = 26 + tmp;
                plaintext += chars.charAt(tmp);
            }
        }
        return plaintext;
    }

    public static String vigenereAutoCipher(String plaintext, String key){
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        String ciphertext = "";
        String k = key;
        while (k.length() < plaintext.length())
        {
            k += plaintext;
        }
        for(int i =0; i < plaintext.length(); i++)
        {
            if (plaintext.charAt(i) == ' ') ciphertext += " ";
            else ciphertext += chars.charAt((chars.indexOf(plaintext.charAt(i))+chars.indexOf(k.charAt(i)))%26);
        }
        return ciphertext;
    }

    public static String vigenereAutoDecipher(String ciphertext, String key){
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        String plaintext = "";
        String k = key;
        while (k.length() < ciphertext.length())
        {
            k += ciphertext;
        }
        for(int i =0; i < ciphertext.length(); i++)
        {
            if (ciphertext.charAt(i) == ' ') plaintext += " ";
            else{
                int tmp = (chars.indexOf(ciphertext.charAt(i))-chars.indexOf(k.charAt(i)))%26;
                if (tmp < 0) tmp = 26 + tmp;
                plaintext += chars.charAt(tmp);
            }
        }
        return plaintext;
    }

    public static String singlecharacterCipher(String plaintext, String key){
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        String ciphertext = "";
        Map<Character, Character> map = new HashMap<>();
        for(int i = 0; i < chars.length(); i ++){
            map.put(chars.charAt(i), key.charAt(i));
        }
        for(char c : plaintext.toCharArray()){
            if (c == ' '){
                ciphertext += ' ';
                continue;
            }
            ciphertext += map.get(c);
        }
        return ciphertext;
    }

    public static String singlecharacterDecipher(String ciphertext, String key){
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        String plaintext = "";
        Map<Character, Character> map = new HashMap<>();
        for(int i = 0; i < chars.length(); i ++){
            map.put(key.charAt(i), chars.charAt(i));
        }
        for(char c : ciphertext.toCharArray()){
            if (c == ' '){
                plaintext += ' ';
                continue;
            }
            plaintext += map.get(c);
        }
        return plaintext;
    }

    public static String railfenceCipher(String plaintext, int key, char[][] matrix){
        plaintext = plaintext.toUpperCase();
        String ciphertext = "";
        int r=key, len=plaintext.length();
        int c = len / key;
        if(len%key!=0) c += 1;
        char mat[][]=new char[r][c];
        int k=0;
        for(int i=0;i< c;i++)
        {
            for(int j=0;j< r;j++)
            {
                if(k!=len)
                    mat[j][i]=plaintext.charAt(k++);
                else
                    mat[j][i]=' ';
            }
        }
        for(int i=0;i< r;i++)
        {
            for(int j=0;j< c;j++)
            {
                ciphertext+=mat[i][j];
            }
        }
        System.arraycopy(mat, 0, matrix, 0, mat.length);
        return ciphertext;
    }

    public static String railfenceDecipher(String ciphertext, int key, char[][] matrix){
        ciphertext = ciphertext.toUpperCase();
        String plaintext = "";
        int c=key, len=ciphertext.length();
        int r = len / key;
        if(len%key!=0){
            r += 1;
            char mat[][]=new char[r][c];
            int k=0;
            int mid;
            if ((c *r) - len - 1 == 0) mid = (c *r) - len;
            else mid = (c *r) - len - 1;
            for(int i = 0; i < mid; i++)
            {
                for(int j=0;j < r;j++)
                {
                    mat[j][i]=ciphertext.charAt(k++);
                }
            }
            for(int i=mid; i < c;i++)
            {
                for(int j=0; j < r - 1; j++)
                {
                    if(k == len) mat[j][i]= ' ';
                    else mat[j][i]=ciphertext.charAt(k++);
                }
            }
            for(int i=0;i< r;i++)
            {
                for(int j=0;j< c;j++)
                {
                    plaintext += mat[i][j];
                }
            }
            System.arraycopy(mat, 0, matrix, 0, mat.length);
        }
        else{
            char mat[][]=new char[r][c];
            int k=0;
            for(int i=0; i < c;i++)
            {
                for(int j=0; j < r; j++)
                {
                    if(k == len) mat[j][i]= ' ';
                    else mat[j][i]=ciphertext.charAt(k++);
                }
            }
            for(int i=0;i< r;i++)
            {
                for(int j=0;j< c;j++)
                {
                    plaintext += mat[i][j];
                }
            }
            System.arraycopy(mat, 0, matrix, 0, mat.length);
        }
        return plaintext;
    }

    public static String playfairCipher(String plaintext, String key, char[][] matrix){
        plaintext = plaintext.toUpperCase();
        String ciphertext = "";
        Playfair pf = new Playfair(key, plaintext);
        pf.cleanPlayFairKey();
        pf.generateCipherKey();
        ciphertext = pf.encryptMessage().toUpperCase();
        System.arraycopy(pf.matrix, 0, matrix, 0, pf.matrix.length);
        return ciphertext;
    }

    public static String playfairDecipher(String ciphertext, String key, char[][] matrix){
        ciphertext = ciphertext.toUpperCase();
        String plaintext = "";
        Playfair pf = new Playfair(key, ciphertext);
        pf.cleanPlayFairKey();
        pf.generateCipherKey();
        plaintext = pf.decryptMessage().toUpperCase();
        System.arraycopy(pf.matrix, 0, matrix, 0, pf.matrix.length);
        return plaintext;
    }
}
class Playfair {
    String key;
    String plainText;
    char[][] matrix = new char[5][5];

    public Playfair(String key, String plainText)
    {
        // convert all the characters to lowercase
        this.key = key.toLowerCase();

        this.plainText = plainText.toLowerCase();
    }

    // function to remove duplicate characters from the key
    public void cleanPlayFairKey()
    {
        LinkedHashSet<Character> set
                = new LinkedHashSet<Character>();

        String newKey = "";

        for (int i = 0; i < key.length(); i++)
            set.add(key.charAt(i));

        Iterator<Character> it = set.iterator();

        while (it.hasNext())
            newKey += (Character)it.next();

        key = newKey;
    }

    // function to generate playfair cipher key table
    public void generateCipherKey()
    {
        Set<Character> set = new HashSet<Character>();

        for (int i = 0; i < key.length(); i++)
        {
            if (key.charAt(i) == 'j')
                continue;
            set.add(key.charAt(i));
        }

        // remove repeated characters from the cipher key
        String tempKey = new String(key);

        for (int i = 0; i < 26; i++)
        {
            char ch = (char)(i + 97);
            if (ch == 'j')
                continue;

            if (!set.contains(ch))
                tempKey += ch;
        }

        // create cipher key table
        for (int i = 0, idx = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrix[i][j] = tempKey.charAt(idx++);
    }

    // function to preprocess plaintext
    public String formatPlainText()
    {
        String message = "";
        int len = plainText.length();

        for (int i = 0; i < len; i++)
        {
            // if plaintext contains the character 'j',
            // replace it with 'i'
            if (plainText.charAt(i) == 'j')
                message += 'i';
            else
                message += plainText.charAt(i);
        }

        // if two consecutive characters are same, then
        // insert character 'x' in between them
        for (int i = 0; i < message.length(); i += 2)
        {
            if (message.charAt(i) == message.charAt(i + 1))
                message = message.substring(0, i + 1) + 'x'
                        + message.substring(i + 1);
        }

        // make the plaintext of even length
        if (len % 2 == 1)
            message += 'x'; // dummy character

        return message;
    }

    // function to group every two characters
    public String[] formPairs(String message)
    {
        int len = message.length();
        String[] pairs = new String[len / 2];

        for (int i = 0, cnt = 0; i < len / 2; i++)
            pairs[i] = message.substring(cnt, cnt += 2);

        return pairs;
    }

    // function to get position of character in key table
    public int[] getCharPos(char ch)
    {
        int[] keyPos = new int[2];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {

                if (matrix[i][j] == ch)
                {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }

    public String encryptMessage()
    {
        String message = formatPlainText();
        String[] msgPairs = formPairs(message);
        String encText = "";

        for (int i = 0; i < msgPairs.length; i++)
        {
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // if both the characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            }

            // if both the characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            }

            // if both the characters are in different rows
            // and columns
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            // get the corresponding cipher characters from
            // the key matrix
            encText = encText + matrix[ch1Pos[0]][ch1Pos[1]]
                    + matrix[ch2Pos[0]][ch2Pos[1]];
        }

        return encText;
    }

    public String decryptMessage()
    {
        String message = formatPlainText();
        String[] msgPairs = formPairs(message);
        String encText = "";

        for (int i = 0; i < msgPairs.length; i++)
        {
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // if both the characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] - 1) % 5;
                ch2Pos[1] = (ch2Pos[1] - 1) % 5;
            }

            // if both the characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                ch1Pos[0] = (ch1Pos[0] - 1) % 5;
                ch2Pos[0] = (ch2Pos[0] - 1) % 5;
            }

            // if both the characters are in different rows
            // and columns
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            if(ch1Pos[1] < 0) ch1Pos[1] += 5;
            if(ch2Pos[1] < 0) ch2Pos[1] += 5;
            if(ch1Pos[0] < 0) ch1Pos[0] += 5;
            if(ch2Pos[0] < 0) ch2Pos[0] += 5;

            // get the corresponding cipher characters from
            // the key matrix
            encText = encText + matrix[ch1Pos[0]][ch1Pos[1]]
                    + matrix[ch2Pos[0]][ch2Pos[1]];
        }

        return encText;
    }
}
