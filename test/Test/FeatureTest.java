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
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Charles
 */
public class FeatureTest {
    public static void main(String[] args) throws Exception {
        JsonWriter writer = new JsonWriter();
        JsonObject jo = new JsonObject("test");
            jo.put("name", new char[] {'"'});
            jo.put("uno", new String[] {"\"\"hi\"\""});
            
            JsonObject obj = jo.putObject("obj");
                obj.put("ha", true);
            JsonObjectArray joa = jo.putObjectArray("list");
            
                JsonObject generic = new JsonObject("generic");
                generic.put("uno", 1);
                generic.put("dos");
                generic.put("tres");
                    JsonObject sub = new JsonObject("sub");
                    sub.put("subby");
                    generic.putObject(sub);
            
                joa.putObject(generic);
                
        writer.putObject(jo);
        writer.write(new File("testing.json"));
        
        JsonParser parser = new JsonParser(new File("testing.json"));
        System.out.println(Arrays.toString(parser.parseStringArray("test.uno")));
        System.out.println(Arrays.toString(parser.parseStringArray("test.name")));
        
        
        
        String json = "{\n" +
                    "	\"test\":{\n" +
                    "		\"name\":[\"\\\"\"],\n" +
                    "		\"uno\":[\"\\\"\\\"hi\\\"\\\"\"],\n" +
                    "		\"obj\":{\n" +
                    "			\"ha\":true\n" +
                    "		},\n" +
                    "		\"list\":[\n" +
                    "		{\n" +
                    "			\"uno\":1,\n" +
                    "			\"dos\":null,\n" +
                    "			\"tres\":null,\n" +
                    "			\"sub\":{\n" +
                    "				\"subby\":null\n" +
                    "		}\n" +
                    "		}\n" +
                    "		]\n" +
                    "	}\n" +
                    "}";
        JsonParser p = new JsonParser(json);
        String j = p.parseStringedValue("test.name");
        System.out.println(j);
        
    }
}
