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
