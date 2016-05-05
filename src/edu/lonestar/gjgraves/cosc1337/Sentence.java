package edu.lonestar.gjgraves.cosc1337;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gjvon on 5/4/2016.
 */
public class Sentence extends DocObject {

    Sentence(Object src) {
        super(src instanceof String ? src : null);
        /**
         * initialize delimiter value within the constructor
         */
        this.objectDelimiter = " ";
    }

    /**
     * @return true is source is not a null value
     * if a source does not exists, nothing happens here and false will be returned.
     */
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


}
