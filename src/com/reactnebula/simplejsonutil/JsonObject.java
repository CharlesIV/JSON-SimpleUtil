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
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonObject extends JsonValue {
    
    ArrayList<JsonData> jObjects = new ArrayList<>();
    ArrayList<Integer> jIndex = new ArrayList<>();
    boolean isParsedObject;
    
    public JsonObject(String name) {
        if(!name.equals(""))
            sb.append(TAB).append('"').append(name).append(SEPERATOR);
        sb.append("{\n");
        indent = TAB;
    }
    
    public void putObject(JsonObject jo) {
        jObjects.add(jo);
        if(sb.charAt(sb.length()-2)!=',')
            sb.append(",\n");
        jIndex.add(sb.length());
        jo.sb.insert(0, indent+TAB);
        for(int i = 0; i < jo.jIndex.size();i++)
            jo.jIndex.set(i, jo.jIndex.get(i)+indent.length()+1);
        jo.indent = indent+TAB;
        insertIndent(jo, indent);
    }
    
    public static void insertIndent(JsonObject jo, String indent) {
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
            
            for(int i = 0; i < jo.jIndex.size(); i++) {
                int jIndex = jo.jIndex.get(i);
                if(jIndex > index)
                    jo.jIndex.set(i, jIndex+indent.length());
            }
            index = nextIndex;
        }
    }
    
    public JsonObject putObject(String name) {
        JsonObject jo = new JsonObject(name);
        jo.sb.insert(0, indent);
        jo.indent += indent;
        jObjects.add(jo);
        jIndex.add(sb.length());
        return jo;
    }
    
    public JsonObjectArray putObjectArray(String name) {
        JsonObjectArray joa = new JsonObjectArray(name);
        joa.sb.insert(0, indent);
        joa.indent = indent+TAB;
        jObjects.add(joa);
        jIndex.add(sb.length());
        return joa;
    }
    
    @Override
    protected void appendBeginning(String name) {
        sb.append(indent);
        super.appendBeginning(name);
    }
    
    /**
     * Creates an object from the data in this JsonObject 
     * @param factory defines how a JsonObject should be converted to an object
     * @return an object that is the same type as defined by the factory
     * @throws IncompatibleJsonObjectException 
     */
    public Object toObject(ObjectFactory factory) throws IncompatibleJsonObjectException {
        return factory.fromJson(this);
    }
    
    /**
     * A method that returns an exact copy of the current 
     * JsonObject at the time of calling this method.
     * 
     * @return a new JsonObject
     */
    public JsonObject copyOf() {
        JsonObject jo = new JsonObject("");
        jo.jObjects.addAll(jObjects);
        jo.jIndex.addAll(jIndex);
        jo.sb = new StringBuilder();
        jo.indent = indent;
        jo.sb.append(sb);
        return jo;
    }
    
    @Override
    String writeLast() {
        String written = write();
        return written.substring(0, written.length()-2).concat("\n");
    }
    
    @Override
    String write() {
        StringBuilder written = new StringBuilder();
        written.append(sb);
        
        int posOffset = 0;
        for(int i = 0; i < jObjects.size(); i++) {
            String object = jObjects.get(i).write();
            written.insert(jIndex.get(i)+posOffset, object);
            posOffset+=object.length();
        }
        int index = written.indexOf("{");
        if(!written.toString().substring(index).replace('{', ' ').trim().isEmpty()) //can replace with isBlank() in newer version
            written.deleteCharAt(written.length()-2);
        
        written.append(indent).append("},\n");
        return written.toString();
    }
}
