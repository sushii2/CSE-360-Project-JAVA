import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class textAnalyzer {

    // this function is from 
    public static <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }
    


    public static void main(String[] args) throws Exception {
        int numWordProcessed = 0;
        int numBlankLinesRemoved = 0;
        int out_numLines = 0;
        int out_avrgWordsPerLine = 0;
        int out_avrgLineLength = 0;
        File rfile = new File(args[0]);
        File wfile = new File("textAnalyzer_out.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rfile)));
        BufferedWriter bw = new BufferedWriter(new PrintWriter(wfile));
        String[] wordList = {};
        String line = null;
        // FIRST: we read each line of the input
        while((line = br.readLine()) != null) {
            if(line.equals("")) {   // if there is no character in this line
                numBlankLinesRemoved++;
            } else {    // if there exist some characters in this line
                String[] wordsInLine = line.split("\\s+");
                if(wordsInLine.length == 0) {
                    numBlankLinesRemoved++;
                } else {
                    numWordProcessed += wordsInLine.length;
                    wordList = concatenate(wordList, wordsInLine);
                }
            }
        }

        // SECOND: we print the words into the output file
        // we set a counter to count the number of characters in each line of the output file
        int counter = 0;
        for(int i=0; i<wordList.length; i++) {
            if(counter+wordList[i].length() >= 80 ) {
                bw.append("\n" + wordList[i]);
                counter = wordList[i].length();
                out_numLines += 1;
                out_avrgLineLength += wordList[i].length();
            } else {
                if(counter == 0) {
                    out_numLines += 1;
                } else {
                    bw.append(" ");
                    out_avrgLineLength += 1;
                }
                bw.append(wordList[i]);
                out_avrgLineLength += wordList[i].length();
                counter = counter + wordList[i].length() + 1;
            }
        }
        
        br.close();
        bw.close();



        System.out.println("numWordProcessed: " + numWordProcessed);
        System.out.println("numBlankLinesRemoved: " + numBlankLinesRemoved);

        System.out.println("out_numLines: " + out_numLines);

        out_avrgWordsPerLine = numWordProcessed / out_numLines;
        System.out.println("out_avrgWordsPerLine: " + out_avrgWordsPerLine);

        out_avrgLineLength = (out_avrgLineLength) / out_numLines;
        System.out.println("out_avrgLineLength: " + out_avrgLineLength);

        System.out.println(Arrays.toString(wordList));
    }
}

