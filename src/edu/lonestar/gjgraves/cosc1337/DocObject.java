package edu.lonestar.gjgraves.cosc1337;

import java.util.ArrayList;

/**
 * Created by ${Gjvon} on 5/4/2016.
 */
public abstract class DocObject {
    //source is the scanner object
    protected Object source;
    //array of objects
    protected ArrayList<Object> objects;
    protected String objectDelimiter;
    protected boolean parsed;

    /**
     * Default constructor
     *
     * @param source is the scanner object
     */
    public DocObject(Object source) {

        this.source = source;
        this.objects = new ArrayList<Object>();
        this.parsed = false;
    }


    public abstract boolean parse();

    @Override
    /**
     * String method to check for parsing
     */
    public final String toString() {
        if (!parsed)
            this.parsed = parse();
        StringBuilder result = new StringBuilder();
        int objectCt = objects.size();
        for (int i = 0; i < objectCt; i++) {
            result.append(objects.get(i).toString());
            if (i < (objectCt - 1))
                result.append(this.objectDelimiter);
        }
        String out = result.toString().replaceAll(" $", "");
        return out;
    }
}