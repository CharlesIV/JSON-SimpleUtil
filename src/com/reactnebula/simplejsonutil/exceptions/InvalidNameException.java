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
public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super("Cannot have empty name");
    }
}
