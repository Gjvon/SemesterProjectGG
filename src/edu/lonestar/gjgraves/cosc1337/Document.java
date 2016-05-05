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
    private int lineCount = 0;
    private int paragraphCount = 0;
    private int wordCount = 0;
    private int sentenceCount = 0;
    private Scanner scanner;

    private Document(Scanner s, String str) {

        FILE_NAME = str;
        scanner = s;


    }

    public String getName() {
        return null;
    }


    public ArrayList getLines() {
        return null;
    }

    /**
     * Sort lines in alphabetical order
     * @return alphabetically sorted lines
     */
    public TreeSet getSortedSentences() {
        TreeSet<String> ts = new TreeSet();
        if (scanner != null) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                ts.add(line);
            }
        }
        System.out.println(ts);
        return ts;
    }

    public static Document getDocumentFromPath(String pathName) throws FileNotFoundException {
        try {
            File fileIn = new File(pathName);
            Scanner s = new Scanner(fileIn);
            Document myDocument = new Document(s, pathName);
            myDocument.parse();
            myDocument.getSortedSentences();
            return myDocument;
        } catch (FileNotFoundException e) {
            System.out.println("No file");
        }
        return null;

    }

    @Override
    public boolean parse() {
        Scanner scanner = (Scanner) source;
        boolean inParagraph = false;
        ArrayList<String> lines = new ArrayList<String>();
        if (scanner != null) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (line.length() == 0) {
                    if (inParagraph) {
                        objects.add(new Paragraph(lines.toArray(new String[lines.size()])));
                        inParagraph = false;
                        lines.clear();
                    }
                } else {
                    lines.add(line);
                    inParagraph = true;
                }
            }
            if (inParagraph)
                objects.add(new Paragraph(lines.toArray(new String[lines.size()])));
            // collect FileMetrics data now
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
