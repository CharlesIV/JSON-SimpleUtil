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
