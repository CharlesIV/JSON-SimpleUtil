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

import com.reactnebula.simplejsonutil.exceptions.IncompatibleJsonObjectException;
import com.reactnebula.simplejsonutil.exceptions.InvalidNameException;
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonObjectArray extends JsonArrayable {
    
    ArrayList<JsonArrayable> jObjects = new ArrayList<>();
    private String name;
    
    JsonObjectArray(String name) {
        if(name.isEmpty())
            throw new InvalidNameException();
        this.name = name;
    } 
    
    public JsonObject putObject() {
        JsonObject jo = new JsonObject();
        jo.indent = jo.indent+indent;
        jObjects.add(jo);
        return jo;
    }
    
    public JsonObject putObject(String name) {
        JsonObject jo = new JsonObject(name);
        jo.indent = jo.indent+indent;
        jObjects.add(jo);
        return jo;
    }
    
    public void putObject(JsonObject jo) {
        JsonObject.insertIndent(jo, indent);
        jObjects.add(jo);
    }
    
    public void putObject(JsonObject jo, String name) {
        if(name.isEmpty())
            throw new InvalidNameException();
        JsonObject.insertIndent(jo, indent);
        jo.name = name;
        jObjects.add(jo);
    }
    
    public JsonObjectArray putObjectArray(String name) {
        JsonObjectArray joa = new JsonObjectArray(name);
        joa.indent = joa.indent + indent;
        jObjects.add(joa);
        return joa;
    }
    
    public void putObjectArray(JsonObjectArray joa, String name) {
        insertIndent(joa, indent);
        joa.name = name;
        jObjects.add(joa);
    }
    
    public Object[] toObjectArray(ObjectFactory factory) throws IncompatibleJsonObjectException {
        JsonObject[] jObjects = toJsonObjects();
        Object[] objects = new Object[jObjects.length];
        for(int i = 0; i < jObjects.length; i++)
            objects[i] = jObjects[i].toObject(factory);
        return objects;
    }
    
    public JsonObject[] toJsonObjects() {
        //to do
        return new JsonObject[0];
    }
    
    static void insertIndent(JsonObjectArray joa, String indent) {
        if(indent.length()==0)
            return;
        joa.indent = joa.indent + indent;
        for(JsonArrayable jd : joa.jObjects) {
            jd.indent = jd.indent + indent;
            if(jd instanceof JsonObject) {
                JsonObject.insertIndent(((JsonObject)jd), indent);
            } else {
                insertIndent((JsonObjectArray)jd, indent);
            }
        }
    }
    
    @Override
    protected String writeNameless() {
        StringBuilder written = new StringBuilder();
        written.append(writeLastNameless());
        written.insert(written.length()-1, ',');
        return written.toString();
    }
    
    @Override
    protected String writeLastNameless() {
        StringBuilder written = new StringBuilder();
        written.append('[');
        jObjects.forEach(jo -> {
            if(jo == jObjects.get(jObjects.size()-1)) 
                written.append(jo.writeLastNameless());
            else
                written.append(jo.writeNameless());
        });
        written.append(indent).append("]\n");
        return written.toString();
    }
    
    @Override
    protected String write() {
        StringBuilder written = new StringBuilder();
        written.append(writeLast());
        written.insert(written.length()-1, ',');
        return written.toString();
    }
    
    @Override
    protected String writeLast() {
        StringBuilder written = new StringBuilder();
        written.append(indent).append('\"').append(name).append(ARRAY_SEPERATOR).append('\n');
        jObjects.forEach(jo -> {
            if(jo == jObjects.get(jObjects.size()-1)) 
                written.append(jo.writeLastNameless());
            else
                written.append(jo.writeNameless());
        });
        written.append(indent).append("]\n");
        return written.toString();
    }
}
