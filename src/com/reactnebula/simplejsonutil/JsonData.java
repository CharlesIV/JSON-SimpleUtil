/*
 * Copyright (C) 2021 Charles
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.reactnebula.simplejsonutil;

/**
 *
 * @author Charles
 */
public abstract class JsonData {
    protected static final String TAB = "\t";
    protected static final String SEPERATOR = "\":";
    protected static final String ARRAY_SEPERATOR = "\":[";
    protected static final String STRING_ARRAY_SEPERATOR = "\", \"";
    
    protected String indent = "";
    
    protected abstract String write();
    protected abstract String writeLast();
    
    /**
     * A method that returns an exact copy of the current 
     * JSON at the time of calling this method.
     * 
     * @return a new object
     */
    public abstract JsonData copyOf();
    
    /**
     * Overridden method that returns a String with the JSON stored by the object.
     * @return 
     */
    @Override
    public String toString() {
        return writeLast();
    }
}
