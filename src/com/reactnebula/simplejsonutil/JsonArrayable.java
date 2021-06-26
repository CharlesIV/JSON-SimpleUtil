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
abstract class JsonArrayable extends JsonData {
    protected abstract String writeNameless();
    protected abstract String writeLastNameless();
}
