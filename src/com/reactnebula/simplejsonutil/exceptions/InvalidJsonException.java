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
public class InvalidJsonException extends RuntimeException {
    public final String JSON;
    
    public InvalidJsonException(String reason, String json) {
        super(reason);
        JSON = json;
    }
    
    public String getJSON() {
        return JSON;
    }
}
