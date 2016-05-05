package edu.lonestar.gjgraves.cosc1337;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by Gjvon on 4/30/2016.
 */
public class Document extends DocObject implements FileMetrics {
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

    //Default constructor that will not be accessed by anything other than this class
    private Document(Scanner s, String str) {
        super(s instanceof Scanner ? s : null);
        this.objectDelimiter = System.lineSeparator() + System.lineSeparator();
        FILE_NAME = str;


    }

    /**
     * @return name of file
     */
    public String getName() {
        return FILE_NAME;
    }


    public ArrayList getLines() {
        return null;
    }

    /**
     * Sort lines in alphabetical order
     *
     * @return alphabetically sorted lines
     */
    public TreeSet getSortedSentences() throws FileNotFoundException {
        TreeSet<String> ts = new TreeSet();
        File file = new File(FILE_NAME);
        Scanner ns = new Scanner(file);
        if (ns != null) {
            while (ns.hasNext()) {
                String line = ns.nextLine().trim();
                ts.add(line);
            }
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
        try {
            File fileIn = new File(pathName);
            Scanner s = new Scanner(fileIn);
            Document myDocument = new Document(s, pathName);
            //String t = myDocument.toString().replaceAll("(" + System.lineSeparator() + ")+$", "");
            //System.out.print( myDocument.getLineCount());
            //System.out.print(t);
            return myDocument; //return a reference to the object
        } catch (FileNotFoundException e) {
            System.out.println("No file");
        }
        /**
         * will never be returned. Program will halt if file does not exist. This is a holder for the compiler
         * without it our program will not compile
         */
        return null;

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
    //Accessor methods

    /**
     * @return number of lines
     */
    @Override
    public int getLineCount() {
        return lineCount;
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
