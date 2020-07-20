package com.reactnebula.simplejsonutil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonWriter {
    ArrayList<JsonValue> data = new ArrayList<>();
    
    public JsonObject putObject(String name) {
        JsonObject jo = new JsonObject(name);
        data.add(jo);
        return jo;
    }
    
    public void putObject(JsonObject jo) {
        data.add(jo);
    }
    
    public void put(String name, double number) {
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
    
    public void put(String name, double[] numbers) {
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
    
    public void reset() {
        data.clear();
    }
    
    public void write(JsonParser jp) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for(JsonValue jd : data)
            if(data.indexOf(jd)==data.size()-1)
                sb.append(jd.writeLast());
            else
                sb.append(jd.write());
        sb.append("}");
        jp.json = sb.toString();
    }
    
    public void write(String file) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append("{\n");
            for(JsonValue jd : data)
                if(data.indexOf(jd)==data.size()-1)
                    writer.append(jd.writeLast());
                else
                    writer.append(jd.write());
            writer.append("}");
        }
        reset();
    }
}
