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
    
    String json;
    HashMap<Integer, Integer> depth;
    
    private final String SEPERATOR = "\":";
    private final String ARRAY_SEPERATOR = "\":[";
    
    private int index;
    private int dep, greatestIndex;
    
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
        depth = new HashMap<>();
        int currentDepth = 0;
        for(int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if(c=='{' || c=='[') {
                currentDepth++;
                depth.put(i, currentDepth);
            } else if(c=='}' || c==']') {
                currentDepth--;
                depth.put(i, currentDepth);
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
    
    private int getIndexOfValue(String key) {
        index = 0;
        int depthTest = 0;
        while((index = json.indexOf(key, index+1)) != -1) {
            depthTest = findDepth(index);
            if(depthTest==1) 
                return index;
        }
        return -1;
    }
    
    private int findDepth(int index) {
        dep = greatestIndex = -1;
        depth.forEach((i, d)->{
            if(i > index) 
                return;
            if(i > greatestIndex) {
                greatestIndex = i;
                dep = d;
            }
        });
        return dep;
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
    
    public String parseStringedValue(String name, String seperator) throws JsonValueNotFoundException {
        int index = getIndexOfValue(name);
        if(index==-1)
            throw new JsonValueNotFoundException(name);
        String result = json.substring(index);
        int comma = index;
        while((comma = json.indexOf(',', comma+1)) != -1) {
            if(findDepth(comma)==1)
                break;
        }
        if(comma != -1)
            result = result.substring(0, comma-index);
        else
            result = result.substring(0, result.length()-1);
        return result.replace(name+seperator, "");
    }
    
    public byte parseByte(String name) throws JsonValueNotFoundException {
        return Byte.parseByte(parseStringedValue(name, SEPERATOR));
    }
    
    public short parseShort(String name) throws JsonValueNotFoundException {
        return Short.parseShort(parseStringedValue(name, SEPERATOR));
    }
    
    public int parseInteger(String name) throws JsonValueNotFoundException {
        return Integer.parseInt(parseStringedValue(name, SEPERATOR));
    }
    
    public float parseFloat(String name) throws JsonValueNotFoundException {
        return Float.parseFloat(parseStringedValue(name, SEPERATOR));
    }
    
    public double parseDouble(String name) throws JsonValueNotFoundException {
        return Double.parseDouble(parseStringedValue(name, SEPERATOR));
    }
    
    public long parseLong(String name) throws JsonValueNotFoundException {
        return Long.parseLong(parseStringedValue(name, SEPERATOR));
    }
    
    public String parseString(String name) throws JsonValueNotFoundException {
        return parseStringedValue(name, SEPERATOR).replace("\"", "");
    }
    
    public boolean parseBoolean(String name) throws JsonValueNotFoundException {
        return Boolean.parseBoolean(parseStringedValue(name, SEPERATOR));
    }
    
    public char parseCharacter(String name) throws JsonValueNotFoundException {
        return parseStringedValue(name, SEPERATOR).charAt(1);
    }
    
    public byte[] parseByteArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").trim().split(",");
        byte[] result = new byte[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Byte.parseByte(array[i]);
        return result;
    }
    
    public short[] parseShortArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").trim().split(",");
        short[] result = new short[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Short.parseShort(array[i]);
        return result;
    }
    
    public int[] parseIntegerArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").trim().split(",");
        int[] result = new int[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Integer.parseInt(array[i]);
        return result;
    }
    
    public float[] parseFloatArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").trim().split(",");
        float[] result = new float[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Float.parseFloat(array[i]);
        return result;
    }
    
    public double[] parseDoubleArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").trim().split(",");
        double[] result = new double[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Double.parseDouble(array[i]);
        return result;
    }
    
    public long[] parseLongArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").trim().split(",");
        long[] result = new long[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Long.parseLong(array[i]);
        return result;
    }
    
    public String[] parseStringArray(String name) throws JsonValueNotFoundException {
        return parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").replace("\n", "").replace("\",", "%%").replace("\"", "").trim().split("%%");
    }
    
    public boolean[] parseBooleanArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").trim().split(",");
        boolean[] result = new boolean[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = Boolean.parseBoolean(array[i]);
        return result;
    }
    
    public char[] parseCharacterArray(String name) throws JsonValueNotFoundException {
        String[] array = parseStringedValue(name, ARRAY_SEPERATOR).replace("]", "").replace("\n", "").replace("\",", "%%").replace("\"", "").trim().split("%%");
        char[] result = new char[array.length];
        for(int i = 0; i < array.length; i++)
            result[i] = array[i].charAt(0);
        return result;
    }
    
    public JsonObject parseObject(String name) throws JsonValueNotFoundException {
        String object = parseStringedValue(name, SEPERATOR);
        JsonObject jo = new JsonObject(name);
        jo.sb = new StringBuilder();
        jo.sb = jo.sb.append(name).append(SEPERATOR).append(object.trim()).replace(jo.sb.lastIndexOf("\n"), jo.sb.length(), "");
        return jo;
    }
    
    public JsonObject[] parseObjectArray(String name) throws JsonValueNotFoundException {
        if(json.contains(name+"\":null"))
            return null;
        if(json.contains(name+ARRAY_SEPERATOR)) {
            int arrayStart = json.indexOf(name + ARRAY_SEPERATOR);
            int arrayEnd = json.indexOf("\n]", arrayStart);
            String array = json.substring(arrayStart+name.length()+4, arrayEnd);
            String[] objects = array.split("},");
            JsonObject[] jObjects = new JsonObject[objects.length];
            for(int i = 0; i < objects.length; i++) {
                JsonObject temp = new JsonObject("temp");
                temp.sb.delete(0, temp.sb.length());
                temp.sb.append(objects[i]);
                jObjects[i] = temp;
            }
            return jObjects;
        } else
            throw new JsonValueNotFoundException(name);
    }
}
