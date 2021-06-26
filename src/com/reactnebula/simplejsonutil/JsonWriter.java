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

import com.reactnebula.simplejsonutil.exceptions.InvalidNameException;
import com.reactnebula.simplejsonutil.exceptions.PrimitiveWrapperException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonWriter {
    ArrayList<JsonData> data = new ArrayList<>();
    
    public JsonObject putObject(String name) {
        JsonObject jo = new JsonObject(name);
        data.add(jo);
        return jo;
    }
    
    public void putObject(JsonObject jo) {
        if(jo.name.isEmpty())
            throw new InvalidNameException();
        data.add(jo);
    }
    
    public void putObject(JsonObject jo, String name) {
        jo.name = name;
        data.add(jo);
    }
    
    public JsonObjectArray putObjectArray(String name) {
        JsonObjectArray joa = new JsonObjectArray(name);
        data.add(joa);
        return joa;
    }
    
    public void put(String name, byte number) {
        JsonValue jd = new JsonValue();
        jd.put(name, number);
        data.add(jd);
    }
    
    public void put(String name, short number) {
        JsonValue jd = new JsonValue();
        jd.put(name, number);
        data.add(jd);
    }
    
    public void put(String name, int number) {
        JsonValue jd = new JsonValue();
        jd.put(name, number);
        data.add(jd);
    }
    
    public void put(String name, float number) {
        JsonValue jd = new JsonValue();
        jd.put(name, number);
        data.add(jd);
    }
    
    public void put(String name, double number) {
        JsonValue jd = new JsonValue();
        jd.put(name, number);
        data.add(jd);
    }
    
    public void put(String name, long number) {
        JsonValue jd = new JsonValue();
        jd.put(name, number);
        data.add(jd);
    }
    
    public void put(String name, String string) {
        JsonValue jd = new JsonValue();
        jd.put(name, string);
        data.add(jd);
    }
    
    public void put(String name, boolean bool) {
        JsonValue jd = new JsonValue();
        jd.put(name, bool);
        data.add(jd);
    }
    
    public void put(String name, char character) {
        JsonValue jd = new JsonValue();
        jd.put(name, character);
        data.add(jd);
    }
    
    public void put(String name, byte[] numbers) {
        JsonValue jd = new JsonValue();
        jd.put(name, numbers);
        data.add(jd);
    }
    
    public void put(String name, short[] numbers) {
        JsonValue jd = new JsonValue();
        jd.put(name, numbers);
        data.add(jd);
    }
    
    public void put(String name, int[] numbers) {
        JsonValue jd = new JsonValue();
        jd.put(name, numbers);
        data.add(jd);
    }
    
    public void put(String name, float[] numbers) {
        JsonValue jd = new JsonValue();
        jd.put(name, numbers);
        data.add(jd);
    }
    
    public void put(String name, double[] numbers) {
        JsonValue jd = new JsonValue();
        jd.put(name, numbers);
        data.add(jd);
    }
    
    public void put(String name, long[] numbers) {
        JsonValue jd = new JsonValue();
        jd.put(name, numbers);
        data.add(jd);
    }
    
    public void put(String name) {
        JsonValue jd = new JsonValue();
        jd.put(name);
        data.add(jd);
    }
    
    public void put(String name, String[] strings) {
        JsonValue jd = new JsonValue();
        jd.put(name, strings);
        data.add(jd);
    }
    
    public void put(String name, boolean[] bools) {
        JsonValue jd = new JsonValue();
        jd.put(name, bools);
        data.add(jd);
    }
    
    public void put(String name, char[] characters) {
        JsonValue jd = new JsonValue();
        jd.put(name, characters);
        data.add(jd);
    }
    
    /**
     * Used to insert a generic type. Generic type must be either a primitive wrapper or String.
     * 
     * @param name
     * @param obj a generic object which can be cast to a primitive wrapper or String. Null is not supported.
     * @throws PrimitiveWrapperException when obj cannot be cast to a primitive wrapper or String
     */
    public void putGenericPrimitive(String name, Object obj) throws PrimitiveWrapperException {
        JsonValue jd = new JsonValue();
        jd.putGenericPrimitive(name, obj);
        if(!jd.sb.toString().trim().equals("")) //!jd.sb.isEmpty() in Java 15+
            data.add(jd);
    }
    
    public void reset() {
        data.clear();
    }
    
    /**
     * A method that returns an exact copy of the current 
     * JsonWriter object at the time of calling this method.
     * 
     * @return a new JsonWriter object
     */
    public JsonWriter copyOf() {
        JsonWriter jw = new JsonWriter();
        ArrayList<JsonData> array = new ArrayList<>();
        for(JsonData jd : data)
            array.add(jd);
        jw.data = array;
        return jw;
    }
    
    /**
     * 
     * @return JsonParser object with a copy of the writer's data.
     */
    public JsonParser toJsonParser() {
        JsonParser jp = new JsonParser();
        write(jp);
        return jp;
    }
    
    /**
     * Writes the JSON stored in the writer to the JsonParser. If the resetOnWrite flag has not been set, then this does not reset the writer after writing.
     * @param jp the JsonParser to write the data to.
     */
    public void write(JsonParser jp) {
        StringBuilder writer = new StringBuilder();
        if(data.size() == 1 && data.get(0) instanceof JsonObject) {
            writer.append(((JsonObject)data.get(0)).writeLastNameless());
            return;
        }

        writer.append("{\n");
        for(JsonData jd : data) {
            if(data.indexOf(jd)==data.size()-1)
                writer.append(jd.writeLast());
            else
                writer.append(jd.write());
        }
        writer.append("}");
        
        jp.json = writer.toString();
        jp.init();
    }
    
    public void write(JsonParser jp, boolean resetWriter) {
        write(jp);
        if(resetWriter)
            reset();
    }
    
    /**
     * Writes the JSON data to a String.
     * @return 
     */
    public String write() {
        StringBuilder writer = new StringBuilder();
        if(data.size() == 1 && data.get(0) instanceof JsonObject) {
            writer.append(((JsonObject)data.get(0)).writeLastNameless());
            return writer.toString();
        }

        writer.append("{\n");
        for(JsonData jd : data) {
            if(data.indexOf(jd)==data.size()-1)
                writer.append(jd.writeLast());
            else
                writer.append(jd.write());
        }
        writer.append("}");
        return writer.toString();
    }
    
    public String write(boolean resetWriter) {
        try {
            return write();
        } finally {
            if(resetWriter)
                reset();
        }
    }
    
    /**
     * Used to write a file to the system in JSON format. If the resetOnWrite flag has not been set, then this does not reset the writer after writing.
     * 
     * @param file 
     * @throws IOException 
     */
    public void write(File file) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            if(data.size() == 1 && data.get(0) instanceof JsonObject) {
                writer.append(((JsonObject)data.get(0)).writeLastNameless());
                return;
            }
            
            writer.append("{\n");
            for(JsonData jd : data) {
                if(data.indexOf(jd)==data.size()-1)
                    writer.append(jd.writeLast());
                else
                    writer.append(jd.write());
            }
            writer.append("}");
        }
    }
    
    public void write(File file, boolean resetWriter) throws IOException {
        write();
        if(resetWriter)
            reset();
    }
}
