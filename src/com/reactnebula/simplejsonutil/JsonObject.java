package com.reactnebula.simplejsonutil;

import com.reactnebula.simplejsonutil.exceptions.IncompatibleJsonObjectException;
import com.reactnebula.simplejsonutil.exceptions.IncorrectParseTypeException;
import com.reactnebula.simplejsonutil.exceptions.InvalidNameException;
import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import com.reactnebula.simplejsonutil.exceptions.PrimitiveWrapperException;
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonObject extends JsonArrayable {
    private final ArrayList<JsonData> data = new ArrayList<>();
    String name;
    private boolean lastPutObject = true;
    
    /**
     * Internally used constructor
     */
    JsonObject() {
        name = "";
    }
    
    public JsonObject(String name) {
        if(name.isEmpty())
            throw new InvalidNameException();
        this.name = name;
    }
    
    public JsonObject putObject(String name) {
        JsonObject jo = new JsonObject(name);
        jo.indent = indent + TAB;
        data.add(jo);
        lastPutObject = true;
        return jo;
    }
    
    public void putObject(Stringifiable s) {
        putObject(s.toJson());
    }
    
    public void putObject(JsonObject jo) {
        if(jo.name.isEmpty())
            throw new InvalidNameException();
        insertIndent(jo, indent+TAB);
        data.add(jo);
        lastPutObject = true;
    }
    
    public void putObject(String name, JsonObject jo) {
        if(jo.name.isEmpty())
            throw new InvalidNameException();
        insertIndent(jo, indent+TAB);
        jo.name = name;
        data.add(jo);
        lastPutObject = true;
    }
    
    public JsonObjectArray putObjectArray(String name) {
        JsonObjectArray joa = new JsonObjectArray(name);
        joa.indent = indent+TAB;
        data.add(joa);
        lastPutObject = true;
        return joa;
    }
    
    public void put(String name, byte number) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, number);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, number);
        }
    }
    
    public void put(String name, short number) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, number);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, number);
        }
    }
    
    public void put(String name, int number) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, number);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, number);
        }
    }
    
    public void put(String name, float number) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, number);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, number);
        }
    }
    
    public void put(String name, double number) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, number);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, number);
        }
    }
    
    public void put(String name, long number) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, number);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, number);
        }
    }
    
    public void put(String name, String string) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, string);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, string);
        }
    }
    
    public void put(String name, boolean bool) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, bool);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, bool);
        }
    }
    
    public void put(String name, char character) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, character);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, character);
        }
    }
    
    public void put(String name, byte[] numbers) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, numbers);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, numbers);
        }
    }
    
    public void put(String name, short[] numbers) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, numbers);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, numbers);
        }
    }
    
    public void put(String name, int[] numbers) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, numbers);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, numbers);
        }
    }
    
    public void put(String name, float[] numbers) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, numbers);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, numbers);
        }
    }
    
    public void put(String name, double[] numbers) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, numbers);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, numbers);
        }
    }
    
    public void put(String name, long[] numbers) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, numbers);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, numbers);
        }
    }
    
    public void put(String name) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name);
        }
    }
    
    public void put(String name, String[] strings) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, strings);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, strings);
        }
    }
    
    public void put(String name, boolean[] bools) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, bools);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, bools);
        }
    }
    
    public void put(String name, char[] characters) {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.put(name, characters);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).put(name, characters);
        }
    }
    
    /**
     * Used to insert a generic type. Generic type must be either a primitive wrapper or String.
     * 
     * @param name
     * @param obj a generic object which can be cast to a primitive wrapper or String. Null is not supported.
     * @throws PrimitiveWrapperException when obj cannot be cast to a primitive wrapper or String
     */
    public void putGenericPrimitive(String name, Object obj) throws PrimitiveWrapperException {
        if(lastPutObject) {
            JsonValue jd = new JsonValue();
            jd.indent = indent+TAB;
            jd.putGenericPrimitive(name, obj);
            data.add(jd);
            lastPutObject = false;
        } else {
            ((JsonValue)data.get(data.size()-1)).putGenericPrimitive(name, obj);
        }
    }
    
    /**
     * A method that returns an exact copy of the current 
     * JsonObject at the time of calling this method.
     * 
     * @return a new JsonObject
     */
    @Override
    public JsonObject copyOf() {
        JsonObject jo;
        if(!name.isEmpty())
            jo = new JsonObject(name);
        else {
            jo = new JsonObject();
        }
        jo.indent = indent;
        data.forEach(jd->{
            jo.data.add(jd.copyOf());
        });
        return jo;
    }
    
    /**
     * Returns an Object from the current JsonObject using the provided ObjectFactory.
     * @param factory
     * @return a Object whose type can be be cast to that of the factory's return type.
     * @throws IncompatibleJsonObjectException 
     */
    public Object toObject(ObjectFactory factory) throws IncompatibleJsonObjectException {
        return factory.fromJson(this);
    }
    
    static void insertIndent(JsonObject jo, String indent) {
        if(indent.length()==0)
            return;
        jo.indent = jo.indent + indent;
        for(JsonData jd : jo.data) {
            jd.indent = jd.indent + indent;
            if(jd instanceof JsonValue) {
                StringBuilder json = ((JsonValue)jd).sb;
                int index = 0;
                while(index != -1) {
                    json.insert(index+1, indent);
                    index = json.indexOf("\n\t", index+indent.length()+1);
                }
            } else if(jd instanceof JsonObject) {
                insertIndent(((JsonObject)jd), indent);
            } else if(jd instanceof JsonObjectArray) {
                JsonObjectArray.insertIndent((JsonObjectArray)jd, indent);
            }
        }
    }
    
    /**
     * Takes a JSON String and converts it into a JsonObject without a given name. 
     * Adding the JsonObject to a writer will require that its name be specified.
     * @param json used to create JsonObject
     * @return 
     */
    public static JsonObject fromJSON(String json) {
        JsonObject jo = fromJSON(json, "temp");
        jo.name = "";
        return jo;
    }
    
    /**
     * Takes a JSON String and converts it into a JsonObject.
     * @param json used to create JsonObject
     * @param name the name given to the JsonObject
     * @return 
     */
    public static JsonObject fromJSON(String json, String name) {
        JsonObject jo = new JsonObject(name);
        JsonParser parser = new JsonParser(json);
        String[] names = parser.parseValues();
        for(String n : names) {
            try {
                String value = parser.parseStringedValue(n);
                if(value.toLowerCase().equals("null"))
                    jo.put(n);
                else if(value.toLowerCase().equals("true"))
                    jo.put(n, true);
                else if(value.toLowerCase().equals("false"))
                    jo.put(n, false);
                else {
                    try {
                        jo.put(n, Long.valueOf(value));
                        continue;
                    } catch(NumberFormatException e) {
                        try {
                            jo.put(n, Double.valueOf(value));
                            continue;
                        } catch(NumberFormatException ex) {}
                    }
                }
                
                if(value.length()==1)
                    jo.put(n, value.charAt(0));
                
                else if(value.startsWith("[")) {
                    String[] arrayValues = value.substring(1, value.length()-1).split(",");
                    if(arrayValues.length==0)
                        jo.put(n, new int[0]);
                    String testValue = arrayValues[0].trim();
                    if(testValue.equals("true") || testValue.equals("false")) {
                        boolean[] bools = new boolean[arrayValues.length];
                        for(int i = 0; i < bools.length; i++)
                            bools[i] = Boolean.valueOf(arrayValues[i]);
                        jo.put(n, bools);
                    } else {
                        try {
                            Long.valueOf(testValue);
                            long[] nums = new long[arrayValues.length];
                            for(int i = 0; i < nums.length; i++)
                                nums[i] = Long.valueOf(arrayValues[i].trim());
                            jo.put(n, nums);
                            continue;
                        } catch(NumberFormatException e) {
                            try {
                                Double.valueOf(testValue);
                                double[] nums = new double[arrayValues.length];
                                for(int i = 0; i < nums.length; i++)
                                    nums[i] = Double.valueOf(arrayValues[i]);
                                jo.put(n, nums);
                                continue;
                            } catch(NumberFormatException ex) {}
                        }
                    }
                    if(testValue.length()==1) {
                        char[] chars = new char[arrayValues.length];
                        for(int i = 0; i < chars.length; i++)
                            jo.put(n, chars);
                        continue;
                    } else {
                        try {
                            JsonObject[] objArray = parser.parseObjectArray(n);
                            JsonObjectArray joa = jo.putObjectArray(n);
                            for(JsonObject obj : objArray)
                                joa.putObject(n, obj);
                            continue;
                        } catch(IncorrectParseTypeException | JsonValueNotFoundException ex) {}
                    }
                    jo.put(n, arrayValues);
                }
                
                try {
                    jo.put(n, parser.parseString(n));
                    continue;
                } catch(IncorrectParseTypeException e) {}
                
                try {
                    jo.putObject(n, parser.parseObject(n));
                } catch(IncorrectParseTypeException | JsonValueNotFoundException e) {}
                
            } catch(JsonValueNotFoundException e) {}
        }
        return jo;
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
        written.append(indent).append("{\n");
        for(JsonData jd : data) {
            if(data.indexOf(jd)==data.size()-1)
                written.append(jd.writeLast());
            else
                written.append(jd.write());
        }
        written.append(indent).append("}\n");
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
        written.append(indent).append("\"").append(name).append(SEPERATOR).append("{\n");
        for(JsonData jd : data) {
            if(data.indexOf(jd)==data.size()-1)
                written.append(jd.writeLast());
            else
                written.append(jd.write());
        }
        written.append(indent).append("}\n");
        return written.toString();
    }
    
    @Override
    public String toString() {
        return writeLastNameless();
    }
}
