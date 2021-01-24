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
package Test;

import com.reactnebula.simplejsonutil.*;

/**
 *
 * @author Charles
 */
public class FeatureTest {
    public static void main(String[] args) throws Exception {
        JsonWriter writer = new JsonWriter();
        writer.put("nullable");
        writer.put("string", "hello world");
        writer.put("byte", (byte)1);
        writer.put("short", (short)2);
        writer.put("int", 3);
        writer.put("long", 4L);
        writer.put("float", 5f);
        writer.put("double", 6.0);
        writer.put("char", 'a');
        writer.put("boolean", true);
        
        writer.put("stringArray", new String[]{"hello", "world"});
        writer.put("byteArray", new byte[0]);
        writer.put("shortArray", new short[]{1,2,3});
        writer.put("intArray", new int[]{1,2,3});
        writer.put("longArray", new long[]{1,2,3});
        writer.put("floatArray", new float[]{1,2,3});
        writer.put("doubleArray", new double[]{1,2,3});
        writer.put("charArray", new char[]{'a', 'b', 'c'});
        writer.put("booleanArray", new boolean[]{true, false, true});
        
        JsonObject jo = new JsonObject("fridge");
        jo.put("x", 20);
        jo.put("y", 20);
        jo.put("ending", 123);
        writer.putObject(jo);
        
        JsonObject jo1 = writer.putObject("object");
            jo1.put("name", "object name");
            jo1.put("num", 231812L);
            jo1.put("objectBools", new boolean[]{true, false, true});
            
            JsonObject jo2 = jo1.putObject("subObject");
                jo2.put("name", "object2 name");
                jo2.put("numbers", new int[]{1,2,3,4});
                
                JsonObject jo3 = new JsonObject("subSubObject");
                    jo3.put("name", "object 3 name");
                    jo3.put("nullable");
                    jo2.putObject(jo3);
                JsonObjectArray joa = jo2.putObjectArray("object-array");
                    JsonObject ajo1 = joa.putObject();
                        ajo1.put("letter", 'a');
                    JsonObject ajo2 = joa.putObject();
                        ajo2.put("letter", 'b');
                    JsonObject ajo3 = joa.putObject();
                        ajo3.put("letter", 'c');
                    JsonObject ajo4 = new JsonObject("manual");
                        ajo4.put("letter", 'd');
                        ajo4.put("number", 1);
                        joa.putObject(ajo4);
        
        JsonObjectArray joa2 = writer.putObjectArray("writer");
            JsonObject a2jo1 = joa2.putObject();
                a2jo1.put("letter", 'a');
            JsonObject a2jo2 = joa2.putObject();
                a2jo2.put("letter", 'b');
            JsonObject a2jo3 = joa2.putObject();
                a2jo3.put("letter", 'c');
            JsonObject a2jo4 = new JsonObject("manual");
                a2jo4.put("letter", 'd');
                a2jo4.put("number", 1);
                JsonObject inv = a2jo3.putObject("inventory");
                    inv.put("width");
                    inv.put("height");
                    JsonObjectArray a2joa1 = inv.putObjectArray("items");
                        JsonObject j = a2joa1.putObject();
                        j.put("name");
                joa2.putObject(a2jo4);
        
        JsonObjectArray furniture = writer.putObjectArray("furniture");
        JsonObject fridge = new JsonObject("fridge");
        furniture.putObject(fridge);
        fridge.put("x", 20);
        fridge.put("y", 20);
        JsonObject inv2 = new JsonObject("inventory");
        
        inv2.put("width", 2);
        inv2.put("height", 2);
        JsonObjectArray items = inv2.putObjectArray("items");
        items.putObject();
        
        fridge.putObject(inv2);
        
        writer.write("testing.json");
        
        JsonParser parser = new JsonParser("testing.json");
        byte[] name = parser.parseByteArray("byteArray");
    }
}
