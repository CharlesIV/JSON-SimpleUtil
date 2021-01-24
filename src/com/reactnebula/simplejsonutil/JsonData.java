package com.reactnebula.simplejsonutil;

/**
 *
 * @author Charles
 */
public abstract class JsonData {
    StringBuilder sb = new StringBuilder();
    static final String TAB = "\t";
    String indent = TAB;
    
    abstract String write();
    abstract String writeLast();
}
