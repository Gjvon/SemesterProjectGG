package edu.lonestar.gjgraves.cosc1337;

import java.io.FileNotFoundException;
import java.util.TreeSet;

/**
 * Created by Gjvon on 5/4/2016.
 */
public class SemesterProjectGG {
    public final String TITLE = "Semester Project";
    public static void main(String[] args) throws FileNotFoundException {
        Document s = Document.getDocumentFromPath("C:\\Users\\Mitnov\\Desktop\\text.txt");
        System.out.print(s.getWordCount());
    }
}
