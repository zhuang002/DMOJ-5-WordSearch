/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordsearch;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class WordSearch {

    static Scanner sc=new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        for (int i=0;i<1;i++) {
            WordSearchProcess wordSearchProcess=new WordSearchProcess(sc);
            wordSearchProcess.doIt();
        }
    }

    

    
}

class WordSearchProcess {
    char[][] puzzle;
    int rows;
    int cols;
    char[][] mask;
    HashMap<String, int[]> strings = new HashMap();
    Scanner sc;
    
    public WordSearchProcess(Scanner scanner) {
        this.sc=scanner;
    }
    public void doIt() {
        readInData();
        buildStrings();
        processDictionary();
        printOutput();
        
    }
    
    private void readInData() {
        rows = sc.nextInt();
        cols = sc.nextInt();
        sc.nextLine();
        puzzle = new char[rows][cols];
        mask = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String row = sc.nextLine();
            for (int j = 0; j < cols; j++) {
                puzzle[i][j] = row.charAt(j);
                mask[i][j] = 0;
            }
        }
    }
    
    private void buildStrings() {
        //horizontal        
        int i, j;
        for (i = 0; i < rows; i++) {
            String s = "";
            for (j = 0; j < cols; j++) {
                
                    s += puzzle[i][j];
            }
            int[] desc = new int[3];
            desc[0] = i;
            desc[1] = 0;
            desc[2] = 0;
            strings.put(s, desc);
        }

        //vertical
        
        for (j = 0; j < cols; j++) {
            String s = "";
            for (i = 0; i < rows; i++) {
                
                    s += puzzle[i][j];
            }
            int[] desc = new int[3];
            desc[0] = 0;
            desc[1] = j;
            desc[2] = 1;
            strings.put(s, desc);
        }

        // \ direction starting from first column
        
        for (i = 0; i < rows; i++) {
            String s = "";
            j = 0;
            int k = i;
            while (j < cols && k < rows) {
                
                    s += puzzle[k][j];
                k++;
                j++;
            }
            int[] desc = new int[3];
            desc[0] = i;
            desc[1] = 0;
            desc[2] = 2;
            strings.put(s, desc);
        }

        // \ direction starting from first row
        
        for (j = 0; j < cols; j++) {
            String s = "";
            i = 0;
            int k = j;
            while (i < rows && k < cols) {
               
                    s += puzzle[i][k];
                k++;
                i++;
            }
            int[] desc = new int[3];
            desc[0] = 0;
            desc[1] = j;
            desc[2] = 2;
            strings.put(s, desc);
        }

        // direction / starting from first column
        
        for (i = 0; i < rows; i++) {
            String s = "";
            j = 0;
            int k = i;
            while (j < cols && k >= 0) {
                
                    s += puzzle[k][j];
                k--;
                j++;
            }
            int[] desc = new int[3];
            desc[0] = i;
            desc[1] = 0;
            desc[2] = 3;
            strings.put(s, desc);
        }

        // direction / start from last row
        
        for (j = 0; j < cols; j++) {
            String s = "";
            i = rows - 1;
            int k = j;
            while (i >= 0 && k < cols) {
                
                    s += puzzle[i][k];
                k++;
                i--;
            }
            int[] desc = new int[3];
            desc[0] = rows-1;
            desc[1] = j;
            desc[2] = 3;
            strings.put(s, desc);
        }
    }

    private String reverseWord(String word) {
        String s = "";
        for (int i = 0; i < word.length(); i++) {
            s = word.charAt(i) + s;
        }
        return s;
    }

    private void processDictionary() {
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            String word = sc.nextLine();
            word=trimWord(word);
            maskWord(word);
            maskWord(reverseWord(word));
        }
    }

    private void printOutput() {
        String message = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mask[i][j] == 0) {
                    message += puzzle[i][j];
                }
            }
        }
        System.out.println(message);
    }
    
    private void maskWord(String word) {
        for (String s : strings.keySet()) {
            int offset = s.indexOf(word);
            if (offset >= 0) {
                int[] indexes = strings.get(s);
                int i;
                int j;
                switch (indexes[2]) {
                    case 0: // horizontal 
                        i = indexes[0];
                        j = indexes[1] + offset;
                        for (int l = 0; l < word.length(); l++) {
                            mask[i][j] = 1;
                            j++;
                        }
                        break;
                    case 1: //vertical
                        i = indexes[0] + offset;
                        j = indexes[1];
                        for (int l = 0; l < word.length(); l++) {
                            mask[i][j] = 1;
                            i++;
                        }
                        break;
                    case 2: // direction \
                        i = indexes[0] + offset;
                        j = indexes[1] + offset;
                        for (int l = 0; l < word.length(); l++) {
                            mask[i][j] = 1;
                            i++;
                            j++;
                        }
                        break;
                    case 3: //direction /
                        i = indexes[0] - offset;
                        j = indexes[1] + offset;
                        for (int l = 0; l < word.length(); l++) {
                            mask[i][j] = 1;
                            i--;
                            j++;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private String trimWord(String word) {
        String s="";
        for (int i=0;i<word.length();i++) {
            char c=word.charAt(i);
            if (Character.isAlphabetic(c)) {
                s+=c;
            }
        }
        return s;
    }
}
