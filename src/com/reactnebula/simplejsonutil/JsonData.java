package com.reactnebula.simplejsonutil;

/**
 *
 * @author Charles
 */
public abstract class JsonData {
    StringBuilder sb = new StringBuilder();
    
    abstract String write();
    abstract String writeLast();
}
