package edu.lonestar.gjgraves.cosc1337;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gjvon on 4/30/2016.
 */
public class Document extends DocObject implements FileMetrics {

    //End of sentence patterns
    static final Pattern END_OF_SENTENCE_PATTERN = Pattern.compile("[\\.\\?!]\"?+\\s+|[\\.\\?!]\"?+$");
    /**
     * Initial name of document
     */
    public String FILE_NAME;
    /**
     * count of lines
     */
    private int lineCount;
    /**
     * count of paragraphs
     */
    private int paragraphCount;
    /**
     * count of words
     */
    private int wordCount;
    /**
     * count of sentences
     */
    private int sentenceCount;
    //words inside each paragraph
    private int paragraphWords;
    //array of lines within a file
    private ArrayList<String> lines;

    //Default constructor that will not be accessed by anything other than this class
    private Document(Scanner s, String str) {
        super(s instanceof Scanner ? s : null);
        this.objectDelimiter = System.lineSeparator() + System.lineSeparator();
        FILE_NAME = str;
        lines = new ArrayList<String>();

    }

    /**
     * @return name of file
     */
    public String getName() {
        return FILE_NAME;
    }


    public ArrayList<String> getLines() {
        return lines;
    }

    /**
     * Sort lines in alphabetical order
     *
     * @return alphabetically sorted lines
     */
    public TreeSet getSortedSentences() throws FileNotFoundException {
        TreeSet<String> ts = new TreeSet();

        for (String s : lines) {
            ts.add(s);
        }
        System.out.println(ts);
        return ts;
    }

    /**
     * Static Factory Design:
     *
     * @param pathName get the name of file location..
     * @return Object instead of returning an instance of the object that the main method can access.
     * the default constructor is not available outside this class.
     * @throws FileNotFoundException if file was not found. Check for spelling and syntax.
     */
    public static Document getDocumentFromPath(String pathName) throws FileNotFoundException {
        File fileIn = new File(pathName);
        Scanner s = new Scanner(fileIn);
        Document myDocument = new Document(s, pathName);
        //while there are still lines to read, handle empty or non-empty lines
        while (s.hasNextLine()) {
            //trim to rid of trailing white space
            String nLine = s.nextLine().trim();
            myDocument.lines.add(nLine);
            if (nLine.length() == 0) {
                myDocument.handleEmptyLine();
            } else {
                myDocument.handleNonEmptyLine(nLine);
            }
        }

        return myDocument; //return a reference to the object
    }


    @Override
    public boolean parse() {
        Scanner scanner = (Scanner) source;
        boolean inParagraph = false;
        /**
         * lines will hold the lines of strings.
         * Each line will be put into a container via the ArrayList.
         */
        ArrayList<String> lines = new ArrayList<String>();

        if (scanner != null) { //if scanner can be read, continue. This will loop throughout the entire file.
            //if the scanner has a neext line, run the block of code
            while (scanner.hasNext()) {
                /**
                 * Put each line of code into the ArrayList objects. Objects was inherited by the DocObject class.
                 */
                String line = scanner.nextLine().trim();
                if (line.length() == 0) {
                    /**
                     * If the program is reading inside a paragraph, run this block of code.
                     * The program knows if we are inside a paragraph. There will be no blank lines.
                     * Black lines: line.length == 0 (See above)
                     */
                    if (inParagraph) {  //in paragraph
                        //add paragraph lines into array
                        objects.add(new Paragraph(lines.toArray(new String[lines.size()])));
                        inParagraph = false;
                        //clear memory when we are finished. ArrayList lines is no longer needed.
                        lines.clear();
                    }
                } else {
                    /**
                     * If we were not in the paragraph, add the empty line to lines and change inParagraph to true.
                     * This is assuming that paragraphs are separated by an empty line.
                     */
                    lines.add(line);
                    inParagraph = true;
                }
            }
            if (inParagraph)
                objects.add(new Paragraph(lines.toArray(new String[lines.size()])));
            // collect FileMetrics data now
            /**
             * This is where we gain access to the number of objects that are actually in a file:
             * Number of paragraphs, sentences, words, and lines.
             */
            paragraphCount = objects.size();
            for (int i = 0; i < paragraphCount; i++) {
                Paragraph p = (Paragraph) objects.get(i);
                lineCount += ((String[]) p.source).length; // original lines array
                p.parsed = p.parse();
                int sentences = p.objects.size();
                sentenceCount += sentences;
                for (int j = 0; j < sentences; j++) {
                    Sentence s = (Sentence) p.objects.get(j);
                    s.parsed = s.parse();
                    int words = s.objects.size();
                    wordCount += words;
                }
            }
            // lineCount needs intervening blank lines so add in now
            lineCount += paragraphCount - 1;

            return true;
        } else
            return false;
    }

    //if the line is not empty and has string objects
    public void handleNonEmptyLine(String line) {
        line = line.replaceAll("\\s+", " ");
        sentenceCount += countSentences(line);
        String[] words = line.split(" ");
        paragraphWords += words.length;
    }

    /**
     * Processes empty lines when found; accumulating or resetting the
     * appropriate counters.
     */
    public void handleEmptyLine() {
        if (paragraphWords > 0) {
            wordCount += paragraphWords;
            paragraphCount++;
            paragraphWords = 0;
        }
    }

    //count the number of sentences on each line.
    private int countSentences(String line) {
        int sentences = 0;
        Matcher m = END_OF_SENTENCE_PATTERN.matcher(line);
        while (m.find())
            sentences++;
        return sentences;
    }


    //Accessor methods

    /**
     * @return number of lines
     */
    @Override
    public int getLineCount() {
        return lines.size();
    }

    /**
     * @return number of paragraphs
     */
    @Override
    public int getParagraphCount() {
        return paragraphCount;
    }

    /**
     * @return number of words
     */
    @Override
    public int getWordCount() {
        return wordCount;
    }

    /**
     * @return number of sentences
     */
    @Override
    public int getSentenceCount() {
        return sentenceCount;
    }
}
