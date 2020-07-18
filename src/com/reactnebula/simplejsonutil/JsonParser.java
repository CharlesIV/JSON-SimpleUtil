package com.reactnebula.simplejsonutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Charles
 */
public class JsonParser {
    
    String json;
    
    public JsonParser(String file) throws IOException {
        json = new String(Files.readAllBytes(Paths.get(file)));
    }
    
    public JsonParser(JsonObject jo) {
        json = jo.sb.toString();
    }
    
    public String[] parseValues() {
        int index = 0;
        int lastIndex = 0;
        int size = json.split("\":").length-1;
        int[] start = new int[size];
        int[] end = new int[size];
        int i = 0;
        while((index = json.indexOf("\":", index+1)) != -1) {
            start[i] = json.replaceAll(" ", "\n").indexOf("\n\"", lastIndex+1);
            end[i] = index;
            lastIndex = index;
            i++;
        }
        String[] result = new String[start.length];
        for(int ii = 0; ii < start.length; ii++)
            result[ii] = json.substring(start[ii]+2, end[ii]);
        return result;
    }
    
    public double parseNumber(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":")[1];
            String result = find.split("\n")[0];
            if(result.endsWith(","))
                result = result.replace(",", "");
            return Double.parseDouble(result);
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public String parseString(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":")[1];
            String result = find.split("\n")[0].replace("\"", "");
            if(result.endsWith(","))
                result = result.replace(",", "");
            return result;
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public boolean parseBoolean(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":")[1];
            String result = find.split("\n")[0];
            if(result.endsWith(","))
                result = result.replace(",", "");
            return Boolean.parseBoolean(result);
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public char parseCharacter(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":")[1];
            String result = find.split("\n")[0];
            if(result.endsWith(","))
                result = result.replace(",", "");
            return result.charAt(1);
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public double[] parseNumberArray(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":\\[")[1].split("\\]")[0];
            String[] list = find.split(","); 
            double[] result = new double[list.length];
            for(int i = 0; i < list.length; i++)
                result[i] = Double.parseDouble(list[i].trim());
            return result;
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public String[] parseStringArray(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":\\[")[1].split("\\]")[0];
            String[] list = find.split(","); 
            String[] result = new String[list.length];
            for(int i = 0; i < list.length; i++)
                result[i] = list[i].replace("\"", "").trim();
            return result;
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public boolean[] parseBooleanArray(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":\\[")[1].split("\\]")[0];
            String[] list = find.split(","); 
            boolean[] result = new boolean[list.length];
            for(int i = 0; i < list.length; i++)
                result[i] = Boolean.parseBoolean(list[i].trim());
            return result;
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public char[] parseCharacterArray(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            String find = json.split(name + "\":\\[")[1].split("\\]")[0];
            String[] list = find.split(","); 
            char[] result = new char[list.length];
            for(int i = 0; i < list.length; i++) 
                result[i] = list[i].trim().charAt(1);
            return result;
        } else
            throw new JsonValueNotFoundException(name);
    }
    
    public JsonObject parseObject(String name) throws JsonValueNotFoundException {
        if(json.contains(name)) {
            JsonObject jo = new JsonObject(name);
            String jString = json.split("\""+name+"\":\\{\n")[1];
            int closeBracket = jString.indexOf("}");
            int openBracket = jString.indexOf("{");
            if(closeBracket < openBracket || openBracket == -1)
                jo.sb.append(jString.split("\n\\}")[0]);
            else {
                //Determine how many open braces before closing
            }
            System.out.println((jo.sb));
            return jo;
        } else
            throw new JsonValueNotFoundException(name);
    }
}
