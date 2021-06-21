package com.reactnebula.simplejsonutil;

/**
 *
 * @author Charles
 */
public abstract class JsonData {
    static final String TAB = "\t";
    static final String SEPERATOR = "\":";
    static final String ARRAY_SEPERATOR = "\":[";
    static final String VALUE_BREAK = "\n\t";
    static final String STRING_ARRAY_SEPERATOR = "\", \"";
    
    StringBuilder sb = new StringBuilder();
    String indent = TAB;
    
    abstract String write();
    abstract String writeLast();
}
