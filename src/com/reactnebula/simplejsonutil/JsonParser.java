package com.reactnebula.simplejsonutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Charles
 */
public class JsonParser {
    private final String SEPERATOR = "\":";
    private final String ARRAY_SEPERATOR = "\":[";
    private final String VALUE_BREAK = "\n\t";
    private final String STRING_ARRAY_SEPERATOR = "\", \"";
    
    String json;
    private HashMap<Integer, Integer> depthMap;
    private int index, depth, greatestIndex;
    
    /**
     * Used for creating an internal parser for reading ObjectArrays
     */
    JsonParser() {}
    
    /**
     * Takes a file path and reads the data in as a String.
     * @param file the path to the file
     * @throws IOException 
     */
    public JsonParser(String file) throws IOException {
        json = new String(Files.readAllBytes(Paths.get(file)));
        init();
    }
    
    /**
     * Takes the JsonObject and copies its data internally,
     * discarding the JsonObject. Any changes to the object after the
     * parser is created will not appear in the parser.
     * @param jo 
     */
    public JsonParser(JsonObject jo) {
        json = jo.sb.toString() + "\n}";
        init();
    }
    
    /**
     * Creates a depth map used to determine the end of objects and arrays
     */
    final void init() {
        depthMap = new HashMap<>();
        int currentDepth = 0;
        for(int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if(c=='{' || c=='[') {
                currentDepth++;
                depthMap.put(i, currentDepth);
            } else if(c=='}' || c==']') {
                currentDepth--;
                depthMap.put(i, currentDepth);
            }
        }
    }
    
    public JsonParser setParser(String file) throws IOException {
        json = new String(Files.readAllBytes(Paths.get(file)));
        init();
        return this;
    }
    
    /**
     * Takes the JsonObject and copies its data internally,
     * discarding the JsonObject. Any changes to the object after the
     * parser is set will not appear in the parser.
     * @param jo 
     */
    public JsonParser setParser(JsonObject jo) {
        json = jo.sb.toString() + "\n}";
        init();
        return this;
    }
    
    private int getIndexOfValue(String key, int depth, int startIndex) {
        index = startIndex-1;
        int depthTest = 0;
        while((index = json.indexOf(key, index+1)) != -1) {
            depthTest = findDepth(index);
            if(depthTest==depth) 
                return index;
        }
        return -1;
    }
    
    private int findDepth(int index) {
        depth = greatestIndex = -1;
        depthMap.forEach((i, d)->{
            if(i > index) 
                return;
            if(i > greatestIndex) {
                greatestIndex = i;
                depth = d;
            }
        });
        return depth;
    }
    
    public String[] parseValues() {
        String[] keys = json.split(SEPERATOR);
        int index;
        ArrayList<String> valid = new ArrayList<>();
        for(int i = 0; i < keys.length-1; i++) {
            for(index = keys[i].length()-1; index > 0; index--) {
                if(keys[i].charAt(index)=='\"')
                    break;
            }
            keys[i] = keys[i].substring(index+1, keys[i].length());
            if(findDepth(json.indexOf("\""+keys[i]+SEPERATOR))==1)
                valid.add(keys[i]);
        }
        String[] result = new String[valid.size()];
        System.arraycopy(valid.toArray(), 0, result, 0, result.length);
        return result;
    }
    /**
     * Returns any value as a String. This is includes Objects and ObjectArrays
     * @param name
     * @return the value associated with the name parameter as a String
     * @throws JsonValueNotFoundException 
     */
    public String parseStringedValue(String name) throws JsonValueNotFoundException {
        return parseStringedValue(name, SEPERATOR);
    }
    
    private String parseStringedValue(String name, String seperator) throws JsonValueNotFoundException {
        String[] path = name.split("\\.");
        int index = -1;
        int lastIndex = -1;
        int dep = 0;
        for(int i = 0; i < path.length; i++) {
            dep = i+1;
            int startIndex = 0;
            do {
                index = getIndexOfValue("\""+path[i]+(i+1==path.length ? seperator : SEPERATOR), dep, startIndex);
                startIndex = index+1;
            } while(lastIndex > index && index != -1);
            if(index==-1)
                throw new JsonValueNotFoundException(name);
            lastIndex = index;
        }
        
        String result = json.substring(index);
        int newBreak = index;
        while((newBreak = json.indexOf(VALUE_BREAK, newBreak+1)) != -1) {
            if(findDepth(newBreak)==dep)
                break;
        }
        if(newBreak != -1)
            result = result.substring(0, newBreak-index);
        else {
            int ending = result.length()-1;
            for(int i = result.length(); i > 0; i--) {
                if(findDepth(i+index) == dep) {
                    ending = i;
                    break;
                }
            }
            result = result.substring(0, ending).trim();
        }
        StringBuilder value = new StringBuilder();
        value.append(result.replace("\""+path[dep-1]+seperator, ""));
        
        if(value.charAt(value.length()-1)==',')
            value.deleteCharAt(value.length()-1);
        
        return value.toString();
    }
    
    public byte parseByte(String name, byte defaultValue) {
        try {
            return parseByte(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public byte parseByte(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        try {
            return Byte.parseByte(value);
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("byte", value);
        }
    }
    
    public short parseShort(String name, short defaultValue) {
        try {
            return parseShort(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public short parseShort(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        try {
            return Short.parseShort(value);
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("short", value);
        }
    }
    
    public int parseInteger(String name, int defaultValue) {
        try {
            return parseInteger(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public int parseInteger(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("integer", value);
        }
    }
    
    public float parseFloat(String name, float defaultValue) {
        try {
            return parseFloat(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public float parseFloat(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        try {
            return Float.parseFloat(value);
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("float", value);
        }
    }
    
    public double parseDouble(String name, double defaultValue) {
        try {
            return parseDouble(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public double parseDouble(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        try {
            return Double.parseDouble(value);
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("double", value);
        }
    }
    
    public long parseLong(String name, long defaultValue) {
        try {
            return parseLong(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public long parseLong(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        try {
            return Long.parseLong(value);
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("long", value);
        }
    }
    
    public String parseString(String name, String defaultValue) {
        try {
            return parseString(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public String parseString(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return "";
        try {
            if(value.charAt(0)!='"')
                throw new IncorrectParseTypeException("String", value);
            value = revertEscapedString(value);
            return value.substring(1, value.length()-1);
        } catch(StringIndexOutOfBoundsException e) {
            throw new IncorrectParseTypeException("String", value);
        }
    }
    
    public boolean parseBoolean(String name, boolean defaultValue) {
        try {
            return parseBoolean(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public boolean parseBoolean(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return false;
        switch (value.toLowerCase()) {
            case "true":
                return true;
            case "false":
                return false;
            default:
                throw new IncorrectParseTypeException("boolean", value);
        }
    }
    
    public char parseCharacter(String name, char defaultValue) {
        try {
            return parseCharacter(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public char parseCharacter(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        try {
            value = revertEscapedString(value);
            return value.charAt(1);
        } catch(StringIndexOutOfBoundsException e) {
            throw new IncorrectParseTypeException("char", value);
        }
    }
    
    public byte[] parseByteArray(String name, byte[] defaultValue) {
        try {
            return parseByteArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public byte[] parseByteArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("byte[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new byte[0];
        String[] array = value.replace("]", "").split(",");
        byte[] result = new byte[array.length];
        try {
            for(int i = 0; i < array.length; i++)
                result[i] = Byte.parseByte(array[i].trim());
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("byte[]", "["+value);
        }
        return result;
    }
    
    public short[] parseShortArray(String name, short[] defaultValue) {
        try {
            return parseShortArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public short[] parseShortArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("short[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new short[0];
        String[] array = value.replace("]", "").split(",");
        short[] result = new short[array.length];
        try {
            for(int i = 0; i < array.length; i++)
                result[i] = Short.parseShort(array[i].trim());
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("short[]", "["+value);
        }
        return result;
    }
    
    public int[] parseIntegerArray(String name, int[] defaultValue) {
        try {
            return parseIntegerArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public int[] parseIntegerArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("int[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new int[0];
        String[] array = value.replace("]", "").split(",");
        int[] result = new int[array.length];
        try {
            for(int i = 0; i < array.length; i++)
                result[i] = Integer.parseInt(array[i].trim());
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("int[]", "["+value);
        }
        return result;
    }
    
    public float[] parseFloatArray(String name, float[] defaultValue) {
        try {
            return parseFloatArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public float[] parseFloatArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("float[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new float[0];
        String[] array = value.replace("]", "").split(",");
        float[] result = new float[array.length];
        try {
            for(int i = 0; i < array.length; i++)
                result[i] = Float.parseFloat(array[i].trim());
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("float[]", "["+value);
        }
        return result;
    }
    
    public double[] parseDoubleArray(String name, double[] defaultValue) {
        try {
            return parseDoubleArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public double[] parseDoubleArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("double[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new double[0];
        String[] array = value.replace("]", "").split(",");
        double[] result = new double[array.length];
        try {
            for(int i = 0; i < array.length; i++)
                result[i] = Double.parseDouble(array[i].trim());
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("double[]", "["+value);
        }
        return result;
    }
    
    public long[] parseLongArray(String name, long[] defaultValue) {
        try {
            return parseLongArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public long[] parseLongArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("long[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new long[0];
        String[] array = value.replace("]", "").split(",");
        long[] result = new long[array.length];
        try {
            for(int i = 0; i < array.length; i++)
                result[i] = Long.parseLong(array[i].trim());
        } catch(NumberFormatException e) {
            throw new IncorrectParseTypeException("long[]", "["+value);
        }
        return result;
    }
    
    public String[] parseStringArray(String name, String[] defaultValue) {
        try {
            return parseStringArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public String[] parseStringArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("String[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new String[0];
        try {
            value = revertEscapedString(value);
            String[] result = value.substring(1, value.length()-2).split(STRING_ARRAY_SEPERATOR);
            return result;
        } catch(StringIndexOutOfBoundsException e) {
            throw new IncorrectParseTypeException("String[]", "["+value);
        }
    }
    
    public boolean[] parseBooleanArray(String name, boolean[] defaultValue) {
        try {
            return parseBooleanArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public boolean[] parseBooleanArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("boolean[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new boolean[0];
        String[] array = value.replace("]", "").split(",");
        boolean[] result = new boolean[array.length];
        for(int i = 0; i < array.length; i++) {
            switch(array[i].trim().toLowerCase()) {
                case "true":
                    result[i] = true;
                case "false":
                    result[i] = false;
                default:
                    throw new IncorrectParseTypeException("boolean[]", "["+value);
            }
        }
        return result;
    }
    
    public char[] parseCharacterArray(String name, char[] defaultValue) {
        try {
            return parseCharacterArray(name);
        } catch(JsonValueNotFoundException e) {
            return defaultValue;
        }
    }
    
    public char[] parseCharacterArray(String name) throws JsonValueNotFoundException {
        String value;
        try {
            value = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String v = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("char[]", v);
        }
        if(value.equals("null")||value.equals("]"))
            return new char[0];
        value = revertEscapedString(value);
        String[] array = value.substring(1, value.length()-2).split(STRING_ARRAY_SEPERATOR);
        char[] result = new char[array.length];
        try {
            for(int i = 0; i < array.length; i++)
                result[i] = array[i].charAt(0);
        } catch(StringIndexOutOfBoundsException e) {
            throw new IncorrectParseTypeException("char[]", "["+value);
        }
        return result;
    }
    
    public JsonObject parseObject(String name) throws JsonValueNotFoundException {
        String object = parseStringedValue(name);
        if(object.equals("null"))
            return null;
        JsonObject jo = new JsonObject(name);
        jo.sb = new StringBuilder();
        try {
            jo.sb = jo.sb.append(name).append(SEPERATOR).append(object.trim()).replace(jo.sb.lastIndexOf("\n"), jo.sb.length(), "");
        } catch(StringIndexOutOfBoundsException e) {
            throw new IncorrectParseTypeException("JsonObject", object);
        }
        return jo;
    }
    
    public JsonObject[] parseObjectArray(String name) throws JsonValueNotFoundException {
        String array;
        try {
            array = parseStringedValue(name, ARRAY_SEPERATOR);
        } catch(JsonValueNotFoundException e) {
            String value = parseStringedValue(name, SEPERATOR);
            throw new IncorrectParseTypeException("ObjectArray", value);
        }
        if(array.trim().equals("]"))
            return new JsonObject[0];
        
        int objectIndex = -1;
        ArrayList<Integer> indexes = new ArrayList<>();
        
        JsonParser parser = new JsonParser();
        parser.json = "["+array;
        parser.init();
        
        do {
            objectIndex = parser.getIndexOfValue("},", 1, objectIndex+1);
            if(objectIndex != -1)
                indexes.add(objectIndex);
        } while (objectIndex != -1);
        indexes.add(array.length());
        
        JsonObject[] jObjects = new JsonObject[indexes.size()];
        int lastIndex = 0;
        for(int i = 0; i < indexes.size(); i++) {
            JsonObject temp = new JsonObject("temp");
            temp.sb.delete(0, temp.sb.length());
            temp.sb.append(array.subSequence(lastIndex, indexes.get(i)));
            jObjects[i] = temp;
            lastIndex = indexes.get(i);
        }
        return jObjects;
    }
    
    private String revertEscapedString(String value) {
        if(value.contains("\\\\"))
            value = value.replace("\\\\", "\\");
        if(value.contains("\\\""))
            value = value.replace("\\\"", "\"");
        if(value.contains("\\\n"))
            value = value.replace("\\\n", "\n");
        if(value.contains("\\\t"))
            value = value.replace("\\\t", "\t");
        if(value.contains("\\\'"))
            value = value.replace("\\\'", "\'");
        if(value.contains("\\\f"))
            value = value.replace("\\\f", "\f");
        if(value.contains("\\\r"))
            value = value.replace("\\\r", "\r");
        if(value.contains("\\\b"))
            value = value.replace("\\\b", "\b");
        return value;
    }
}
