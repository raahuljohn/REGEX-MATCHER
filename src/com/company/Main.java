package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    public static String findLCS(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        int lcsLength = dp[m][n];
        char[] lcsChars = new char[lcsLength];
        int i = m, j = n;

        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcsChars[lcsLength - 1] = str1.charAt(i - 1);
                lcsLength--;
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return new String(lcsChars);
    }
    private static boolean matchesPattern(String word, String regexPattern) {

        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        return Pattern.matches(String.valueOf(pattern), word);
    }


    public static void main(String[] args) throws IOException {
        ArrayList<String> wordsfrominput = new ArrayList<>();
        ArrayList<String> matchedWords = new ArrayList<>();
        List<String> newmatchedWords;

       String fileName = "/Users/ravikumar/Documents/ALGO_PROJECT/src/input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
               // System.out.println(line);
                //case-insensitive
                wordsfrominput.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  System.out.println("-------------");
      //  System.out.println(wordsfrominput);

        for (int i = 0; i < wordsfrominput.size(); i++) {

            String text = wordsfrominput.get(i);
            String regexPattern = wordsfrominput.get(wordsfrominput.size()-1);

            if (matchesPattern(text, regexPattern)) {
                matchedWords.add(text);
                if (matchedWords.size() == 3) {
                    break;
                }
            }
        }

       // System.out.println("Matched words: " + matchedWords);

        //arranging alphabetically
        Collections.sort(matchedWords);

        //atmost 3 words
        newmatchedWords= matchedWords.subList(0,3);

       // System.out.println("------");
       // System.out.println(newmatchedWords);
        String lcs = newmatchedWords.get(0);
        for (int i = 1; i < newmatchedWords.size(); i++) {
            lcs = findLCS(lcs, newmatchedWords.get(i));
        }
        System.out.println(lcs);

        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        //    System.out.println(longestCommonSubsequence);
        writer.write(lcs);
        writer.close();
    }
}
