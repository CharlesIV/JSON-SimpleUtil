package com.reactnebula.simplejsonutil;

import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class JsonObjectArray extends JsonData {
    
    ArrayList<JsonObject> jObjects = new ArrayList<>();
    
    JsonObjectArray(String name) {
        sb.append(indent);
        sb.append('"').append(name).append('"');
        sb.append(":");
        sb.append("[\n");
    }
    
    public JsonObject putObject() {
        JsonObject jo = new JsonObject("");
        jo.sb.insert(0, indent);
        jo.indent=indent;
        jObjects.add(jo);
        return jo;
    }
    
    public void putObject(JsonObject jo) {
        int beginning = jo.sb.indexOf("{");
        jo.sb.replace(0, beginning, "");
        jo.sb.insert(0, indent);
        jo.indent=indent;
        jo.insertIndent(jo, indent.substring(0, indent.length()-1));
        jObjects.add(jo);
    }
    
    @Override
    String write() {
        jObjects.forEach(jo -> {
            if(jo == jObjects.get(jObjects.size()-1)) 
                sb.append(jo.writeLast());
            else
                sb.append(jo.write());
        });
        sb.append(indent).append("],\n");
        return sb.toString();
    }
    
    @Override
    String writeLast() {
        jObjects.forEach(jo -> {
            if(jo == jObjects.get(jObjects.size()-1)) 
                sb.append(jo.writeLast());
            else
                sb.append(jo.write());
        });
        sb.append(indent).append("]\n");
        return sb.toString();
    }
}
