/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reactnebula.simplejsonutil;

/**
 *
 * @author Charles
 */
public class IncorrectParseTypeException extends RuntimeException {
    public IncorrectParseTypeException(String parseType, String value) {
        super("Cannot use parse type: " + parseType + ", for: " + value);
    }
}
