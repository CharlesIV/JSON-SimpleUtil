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
package examples;

import com.reactnebula.simplejsonutil.*;
import java.io.File;

/**
 *
 * @author Charles
 */
public class FeatureTest {
    public static void main(String[] args) throws Exception { 
        
        JsonWriter writer = new JsonWriter();
        
        writer.put("name", "James");
        writer.put("age", 32);
        writer.put("height", 5.8);
        
        JsonObject jo = writer.putObject("car");
            jo.put("make", "toyota");
            jo.put("model", "tacoma");
            jo.put("year", 2013);
            JsonObject jo2 = new JsonObject("computer");
                jo2.put("cpu", "i7-7700k");
                jo2.put("gpu", "RTX 3080");
            jo.putObject(jo2);
            jo.put("numbers", new int[]{1,2,3,4});
        JsonObjectArray joa = jo.putObjectArray("array");
            JsonObject jo3 = joa.putObject("player1");
            jo3.put("name", "Bob");
            JsonObject jo4 = new JsonObject("player2");
                jo4.put("name", "Jill");
                joa.putObject(jo4);
            
            jo.putGenericPrimitive("generic", 5);
        writer.put("eh");
        
        JsonParser parser = writer.toJsonParser();
        
        System.out.println(parser.parseString("car.make"));
        
        JsonObject job = parser.parseObject("car");
        writer.reset();
        writer.put("help");
        writer.putObject(job);
        
        writer.stream().forEach(s->System.out.println(s));
        
        writer.write(new File("testing.json"));  
    }
}
