package edu.lonestar.gjgraves.cosc1337;

/**
 * Created by Gjvon on 5/4/2016.
 */
public interface FileMetrics {
    /**
     * Number of lines in the file.
     *
     * @return int
     */
    int getLineCount();

    /**
     * Number of paragraphs in the file.
     *
     * @return int
     */
    int getParagraphCount();

    /**
     * Number of words in the file.
     *
     * @return int
     */
    int getWordCount();

    /**
     * Number of sentences in the file.
     *
     * @return int
     */
    int getSentenceCount();
}
