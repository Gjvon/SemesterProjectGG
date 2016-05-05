package edu.lonestar.gjgraves.cosc1337;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gjvon on 5/4/2016.
 */
public class Paragraph extends DocObject {
    /**
     * Pattern finds the end of sentences using punctuations
     */
    private Pattern SENTENCE_ENDER = Pattern.compile("[\\.\\?!]\"?+\\s+|[\\.\\?!]\"?+$");

    public Paragraph() {

    }

    @Override
    public boolean parse() {
        if (source != null) {
            String text = ((String) source).trim().replaceAll("\\s+", " ");
            String[] words = text.split(" ");
            objects = new ArrayList<Object>(Arrays.asList(words));
            return true;
        } else
            return false;
    }

    public Paragraph(Object anObject) {

    }


}
