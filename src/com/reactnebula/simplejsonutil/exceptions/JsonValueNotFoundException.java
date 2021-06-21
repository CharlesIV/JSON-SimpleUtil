package com.reactnebula.simplejsonutil.exceptions;

/**
 *
 * @author Charles
 */
public class JsonValueNotFoundException extends Exception {

    public final String NAME;
    public JsonValueNotFoundException(String name) {
        super("Unable to find: " + name);
        this.NAME = name;
    }
    
    public String getName() {
        return NAME;
    }
}
