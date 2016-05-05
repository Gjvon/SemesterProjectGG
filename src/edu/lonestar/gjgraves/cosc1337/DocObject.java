package edu.lonestar.gjgraves.cosc1337;

import java.util.ArrayList;

/**
 * Created by Gjvon on 5/4/2016.
 */
public abstract class DocObject {

    protected Object source;
    protected ArrayList<Object> objects;
    protected String objectDelimiter;
    protected boolean parsed;

    public DocObject(Object source) {

        this.source = source;
        this.objects = new ArrayList<Object>();
        this.parsed = false;
    }

    public DocObject() {
    }

    public abstract boolean parse();

    @Override
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