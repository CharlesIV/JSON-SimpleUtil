/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reactnebula.simplejsonutil.exceptions;

/**
 *
 * @author Charles
 */
public class PrimitiveWrapperException extends RuntimeException {
    public final String VALUE_NAME, CLASS_NAME;
    public PrimitiveWrapperException(String valueName, String className) {
        super("Unable to put value: " + valueName + ", of type: " + className + ", which is not of primitive wrapper or String type");
        VALUE_NAME = valueName;
        CLASS_NAME = className;
    }
}
