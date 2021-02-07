package com.reactnebula.simplejsonutil;

import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonObject extends JsonValue {
    
    ArrayList<JsonData> jObjects = new ArrayList<>();
    ArrayList<Integer> jIndex = new ArrayList<>();
    
    public JsonObject(String name) {
        if(!name.equals(""))
            sb.append(TAB).append('"').append(name).append('"').append(":");
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
        if(!written.toString().substring(index).replace('{', ' ').isBlank())
            written.deleteCharAt(written.length()-2);
        
        written.append(indent).append("},\n");
        return written.toString();
    }
}
