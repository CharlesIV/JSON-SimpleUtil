/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reactnebula.simplejsonutil;

import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import com.reactnebula.simplejsonutil.exceptions.PrimitiveWrapperException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

/**
 *
 * @author Charles
 */
public class JsonWriter {
    private JsonObject data;
    
    public JsonWriter() {
        data = new JsonObject();
    }
    
    public JsonWriter(JsonObject object) {
        data = object;
    }
    
    public JsonObject putObject(String name) {
        return data.putObject(name);
    }
    
    public void putObject(Stringifiable s) {
        data.putObject(s);
    }
    
    public void putObject(JsonObject jo) {
        data.putObject(jo);
    }
    
    public void putObject(String name, JsonObject jo) {
        data.putObject(name, jo);
    }
    
    public JsonObjectArray putObjectArray(String name) {
        return data.putObjectArray(name);
    }
    
    public void put(String name, byte number) {
        data.put(name, number);
    }
    
    public void put(String name, short number) {
        data.put(name, number);
    }
    
    public void put(String name, int number) {
        data.put(name, number);
    }
    
    public void put(String name, float number) {
        data.put(name, number);
    }
    
    public void put(String name, double number) {
        data.put(name, number);
    }
    
    public void put(String name, long number) {
        data.put(name, number);
    }
    
    public void put(String name, String string) {
        data.put(name, string);
    }
    
    public void put(String name, boolean bool) {
        data.put(name, bool);
    }
    
    public void put(String name, char character) {
        data.put(name, character);
    }
    
    public void put(String name, byte[] numbers) {
        data.put(name, numbers);
    }
    
    public void put(String name, short[] numbers) {
        data.put(name, numbers);
    }
    
    public void put(String name, int[] numbers) {
        data.put(name, numbers);
    }
    
    public void put(String name, float[] numbers) {
        data.put(name, numbers);
    }
    
    public void put(String name, double[] numbers) {
        data.put(name, numbers);
    }
    
    public void put(String name, long[] numbers) {
        data.put(name, numbers);
    }
    
    public void put(String name) {
        data.put(name);
    }
    
    public void put(String name, String[] strings) {
        data.put(name, strings);
    }
    
    public void put(String name, boolean[] bools) {
        data.put(name, bools);
    }
    
    public void put(String name, char[] characters) {
        data.put(name, characters);
    }
    
    /**
     * Used to insert a generic type. Generic type must be either a primitive wrapper or String.
     * 
     * @param name
     * @param obj a generic object which can be cast to a primitive wrapper or String. Null is not supported.
     * @throws PrimitiveWrapperException when obj cannot be cast to a primitive wrapper or String
     */
    public void putGenericPrimitive(String name, Object obj) throws PrimitiveWrapperException {
        data.putGenericPrimitive(name, obj);
    }
    
    public void reset() {
        data = new JsonObject();
    }
    
    /**
     * A method that returns an exact copy of the current 
     * JsonWriter object at the time of calling this method.
     * 
     * @return a new JsonWriter object
     */
    public JsonWriter copyOf() {
        JsonWriter jw = new JsonWriter();
        jw.data = data.copyOf();
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
     * Creates a Stream of SimpleEntrys which contains a JSON name and value pair.
     * @return 
     */
    public Stream<SimpleEntry<String, String>> stream() {
        Builder stream = Stream.builder();
        JsonParser parser = toJsonParser();
        String[] names = parser.parseValues();
        try {
            for(String n : names)
                stream.add(new SimpleEntry(n, parser.parseStringedValue(n)));
        } catch(JsonValueNotFoundException e) {}
        return stream.build();
    }
    
    /**
     * Writes the JSON stored in the writer to the JsonParser.
     * @param jp the JsonParser to write the data to.
     */
    public void write(JsonParser jp) {
        jp.json = write();
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
        return data.toString();
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
     * Used to write a file to the system in JSON format.
     * 
     * @param file 
     * @throws IOException 
     */
    public void write(File file) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(write());
        }
    }
    
    public void write(File file, boolean resetWriter) throws IOException {
        write();
        if(resetWriter)
            reset();
    }
}
