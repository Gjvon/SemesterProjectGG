package edu.lonestar.gjgraves.cosc1337;

import java.util.ArrayList;

/**
 * Created by ${Gjvon} on 5/4/2016.
 */
public abstract class DocObject {

    Object source;
    ArrayList<Object> objects;
    String objectDelimiter;
    boolean parsed;

    DocObject(Object source) {

        this.source = source;
        this.objects = new ArrayList<Object>();
        this.parsed = false;
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
        return result.toString().replaceAll(" $", "");
    }
}