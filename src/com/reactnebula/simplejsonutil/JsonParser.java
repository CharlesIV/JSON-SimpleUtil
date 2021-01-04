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
    
    String json;
    private HashMap<Integer, Integer> depthMap;
    private int index, depth, greatestIndex;
    
    public JsonParser(String file) throws IOException {
        json = new String(Files.readAllBytes(Paths.get(file)));
        init();
    }
    
    /**
     * Takes the JsonObject and copies it's data internally,
     * discarding the JsonObject. Any changes to the object after the
     * parser is created will not appear in the parser.
     * @param jo 
     */
    public JsonParser(JsonObject jo) {
        json = jo.sb.toString() + "\n}";
        init();
    }
    
    private void init() {
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
    
    public void setParser(String file) throws IOException {
        json = new String(Files.readAllBytes(Paths.get(file)));
        init();
    }
    
    /**
     * Takes the JsonObject and copies it's data internally,
     * discarding the JsonObject. Any changes to the object after the
     * parser is set will not appear in the parser.
     * @param jo 
     */
    public void setParser(JsonObject jo) {
        json = jo.sb.toString() + "\n}";
        init();
    }
    
    private int getIndexOfValue(String key, int depth) {
        index = 0;
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
            index = getIndexOfValue(path[i], dep);
            if(index==-1)
                throw new JsonValueNotFoundException(name);
            if(lastIndex > index)
                throw new JsonValueNotFoundException(name);
            lastIndex = index;
        }
        
        String result = json.substring(index);
        int comma = index;
        while((comma = json.indexOf(',', comma+1)) != -1) {
            if(findDepth(comma)==dep)
                break;
        }
        if(comma != -1)
            result = result.substring(0, comma-index);
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
        return result.replace(path[dep-1]+seperator, "");
    }
    
    public byte parseByte(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        return Byte.parseByte(value);
    }
    
    public short parseShort(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        return Short.parseShort(value);
    }
    
    public int parseInteger(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        return Integer.parseInt(value);
    }
    
    public float parseFloat(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        return Float.parseFloat(value);
    }
    
    public double parseDouble(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        return Double.parseDouble(value);
    }
    
    public long parseLong(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        return Long.parseLong(value);
    }
    
    public String parseString(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return "";
        return value.replace("\"", "");
    }
    
    public boolean parseBoolean(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return false;
        return Boolean.parseBoolean(value);
    }
    
    public char parseCharacter(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name);
        if(value.equals("null"))
            return 0;
        return value.charAt(1);
    }
    
    public byte[] parseByteArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new byte[0];
        String[] array = value.replace("]", "").trim().split(",");
        byte[] result = new byte[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Byte.parseByte(array[i]);
        return result;
    }
    
    public short[] parseShortArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new short[0];
        String[] array = value.replace("]", "").trim().split(",");
        short[] result = new short[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Short.parseShort(array[i]);
        return result;
    }
    
    public int[] parseIntegerArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new int[0];
        String[] array = value.replace("]", "").trim().split(",");
        int[] result = new int[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Integer.parseInt(array[i]);
        return result;
    }
    
    public float[] parseFloatArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new float[0];
        String[] array = value.replace("]", "").trim().split(",");
        float[] result = new float[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Float.parseFloat(array[i]);
        return result;
    }
    
    public double[] parseDoubleArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new double[0];
        String[] array = value.replace("]", "").trim().split(",");
        double[] result = new double[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Double.parseDouble(array[i]);
        return result;
    }
    
    public long[] parseLongArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new long[0];
        String[] array = value.replace("]", "").trim().split(",");
        long[] result = new long[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Long.parseLong(array[i]);
        return result;
    }
    
    public String[] parseStringArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new String[0];
        return value.replace("]", "").replace("\n", "").replace("\",", "%%").replace("\"", "").trim().split("%%");
    }
    
    public boolean[] parseBooleanArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new boolean[0];
        String[] array = value.replace("]", "").trim().split(",");
        boolean[] result = new boolean[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Boolean.parseBoolean(array[i]);
        return result;
    }
    
    public char[] parseCharacterArray(String name) throws JsonValueNotFoundException {
        String value = parseStringedValue(name, ARRAY_SEPERATOR);
        if(value.equals("null"))
            return new char[0];
        String[] array = value.replace("]", "").replace("\n", "").replace("\",", "%%").replace("\"", "").split("%%");
        char[] result = new char[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = array[i].trim().charAt(0);
        return result;
    }
    
    public JsonObject parseObject(String name) throws JsonValueNotFoundException {
        String object = parseStringedValue(name);
        if(object.equals("null"))
            return null;
        JsonObject jo = new JsonObject(name);
        jo.sb = new StringBuilder();
        jo.sb = jo.sb.append(name).append(SEPERATOR).append(object.trim()).replace(jo.sb.lastIndexOf("\n"), jo.sb.length(), "");
        return jo;
    }
    
    public JsonObject[] parseObjectArray(String name) throws JsonValueNotFoundException {
        String array = parseStringedValue(name, ARRAY_SEPERATOR);
        String[] objects = array.split("},");
        JsonObject[] jObjects = new JsonObject[objects.length];
        for(int i = 0; i < objects.length; i++) {
            JsonObject temp = new JsonObject("temp");
            temp.sb.delete(0, temp.sb.length());
            temp.sb.append(objects[i].trim());
            jObjects[i] = temp;
        }
        return jObjects;
    }
}
