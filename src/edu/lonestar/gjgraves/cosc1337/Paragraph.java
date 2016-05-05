package edu.lonestar.gjgraves.cosc1337;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${Gjvon} on 5/4/2016.
 */
class Paragraph extends DocObject {
    /**
     * Pattern finds the end of sentences using punctuations
     */
    private Pattern SENTENCE_ENDER = Pattern.compile("[\\.\\?!]\"?+\\s+|[\\.\\?!]\"?+$");

    Paragraph(Object src) {

        super(src instanceof String[] ? src : null);
        this.objectDelimiter = System.lineSeparator();
    }

    @Override
    public boolean parse() {
        if (source != null) {
            String[] lines = (String[]) source;
            StringBuilder currentSentence = new StringBuilder();
            for (String l : lines) {
                Matcher m = SENTENCE_ENDER.matcher(l);
                int lastFind = 0;
                while (m.find()) {
                    currentSentence.append(l.substring(lastFind, m.start() + 1));
                    Sentence sentence = new Sentence(currentSentence.toString());
                    objects.add(sentence);
                    currentSentence = new StringBuilder();
                    lastFind = m.end();
                }
                if (currentSentence.length() == 0 || (currentSentence.length() > 0 && lastFind == 0)) {
                    currentSentence.append(l.substring(lastFind) + " ");
                }
            }
            return true;
        } else
            return false;
    }




}
