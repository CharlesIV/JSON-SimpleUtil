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
import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonObjectArray extends JsonData {
    
    ArrayList<JsonData> jObjects = new ArrayList<>();
    private final String name;
    
    private JsonObjectArray() {
        sb.append("[\n");
        name = "";
    }
    
    JsonObjectArray(String name) {
        this.name = name;
        sb.append(indent);
        sb.append('"').append(name).append('"');
        sb.append(":");
        sb.append("[\n");
    }
    
    static void insertIndent(JsonObjectArray jo, String indent) {
        if(indent.length()==0)
            return;
        String object = jo.sb.toString();
        int index = object.indexOf("\n", 1);
        int count = 1;
        while(index!=-1) {
            int nextIndex = object.indexOf("\n", index+1);
            if(nextIndex==-1)
                return;
            
            jo.sb.insert(index+count*indent.length(), indent);
            count++;
            index = nextIndex;
        }
    } 
    
    public JsonObject putObject() {
        JsonObject jo = new JsonObject("");
        jo.sb.insert(0, indent);
        jo.indent=indent;
        jObjects.add(jo);
        return jo;
    }
    
    public void putObject(JsonObject jo) {
        int beginning = jo.sb.indexOf("{");
        jo.sb.replace(0, beginning, "");
        jo.sb.insert(0, indent);
        jo.indent=indent;
        
        for(int i = 0; i < jo.jIndex.size(); i++)
            jo.jIndex.set(i, jo.jIndex.get(i)-(beginning-indent.length()));
        
        JsonObject.insertIndent(jo, indent.substring(0, indent.length()-1));
        for(int i = 0; i < jo.jIndex.size(); i++) {
            JsonData jd = jo.jObjects.get(i);
            if(jd instanceof JsonObject)
                JsonObject.insertIndent((JsonObject)jd, indent.substring(0, indent.length()-1));
        }
        jObjects.add(jo);
    }
    
    public JsonObjectArray putObjectArray() {
        JsonObjectArray joa = new JsonObjectArray();
        joa.sb.insert(0, indent);
        joa.indent = indent+TAB;
        jObjects.add(joa);
        return joa;
    }
    
    public void putObjectArray(JsonObjectArray joa) {
        insertIndent(joa, indent);
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
        JsonParser p = new JsonParser();
        p.json = writeLast();
        boolean blank = name.isEmpty();
        
        try {
            if(blank)  {
                sb.insert(0, "\"temp\":");
                return p.parseObjectArray("temp");
            }
            return p.parseObjectArray(name);
        } catch(JsonValueNotFoundException e) {}
        finally {
            if(blank)
                sb.subSequence(7, sb.length());
        }
        return new JsonObject[0];
    }
    
    @Override
    protected String write() {
        StringBuilder written = new StringBuilder();
        written.append(sb);
        jObjects.forEach(jo -> {
            if(jo == jObjects.get(jObjects.size()-1)) 
                written.append(jo.writeLast());
            else
                written.append(jo.write());
        });
        written.append(indent).append("],\n");
        return written.toString();
    }
    
    @Override
    protected String writeLast() {
        StringBuilder written = new StringBuilder();
        written.append(sb);
        jObjects.forEach(jo -> {
            if(jo == jObjects.get(jObjects.size()-1)) 
                written.append(jo.writeLast());
            else
                written.append(jo.write());
        });
        written.append(indent).append("]\n");
        return written.toString();
    }
}
