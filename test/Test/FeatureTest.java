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
        writer.put("byteArray", new byte[]{1,2,3});
        writer.put("shortArray", new short[]{1,2,3});
        writer.put("intArray", new int[]{1,2,3});
        writer.put("longArray", new long[]{1,2,3});
        writer.put("floatArray", new float[]{1,2,3});
        writer.put("doubleArray", new double[]{1,2,3});
        writer.put("charArray", new char[]{'a', 'b', 'c'});
        writer.put("booleanArray", new boolean[]{true, false, true});
        
        JsonObject jo1 = writer.putObject("object");
            jo1.put("name", "object name");
            jo1.put("num", 231812L);
            jo1.put("objectBools", new boolean[]{true, false, true});
            JsonObject jo2 = jo1.putObject("subObject");
                jo2.put("name", "object2 name");
                jo2.put("numbers", new int[]{1,2,3,4});
                JsonObjectArray joa = jo2.putObjectArray("objectArray");
                    JsonObject arrayObject = new JsonObject("arrayObject");
                    arrayObject.put("name", "arrayObject");
                    for(int i = 0; i < 3; i++) {
                        JsonObject temp = arrayObject.copyOf();
                        temp.put("value", i);
                        joa.putObject(temp);
                    }
        
        writer.write("testing.json");
        
        JsonParser parser = new JsonParser("testing.json");
        String name = parser.parseString("object.subObject.objectArray");
        System.out.println(name);
    }
}
