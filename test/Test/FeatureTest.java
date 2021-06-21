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

/**
 *
 * @author Charles
 */
public class FeatureTest {
    public static void main(String[] args) throws Exception { 
        
        JsonWriter writer = new JsonWriter();
        
        JsonObject jo = new JsonObject("character");
            jo.put("name", "Chuck");
            jo.put("lvl", 10);
            JsonObjectArray joa = jo.putObjectArray("inv");
                JsonObject jo1 = joa.putObject();
                    jo1.put("name", "sword");
                    jo1.put("category", "weapon");
                    jo1.put("cost", 100);
                    JsonObjectArray joa2 = jo1.putObjectArray("abilities");
                        joa2.putObject();
                        joa2.putObject();
                        joa2.putObject();
                JsonObject jo2 = new JsonObject("item");
                    jo2.put("name", "steak");
                    jo2.put("category", "food");
                    jo2.put("cost", 5);
                joa.putObject(jo2);
            jo.put("weight", 150);
        writer.putObject(jo);
        
        writer.put("setting", "Narnia");
        JsonObject jo3 = writer.putObject("camp");
            jo3.put("buildings", new String[]{"tent", "tent", "blacksmith", "command hall", "dining hall"});
            jo3.put("leader", "Marlow");
        
        JsonParser parser = writer.toJsonParser();
        JsonObject jo4 = parser.parseObject("character");
        
        writer.reset();
        writer.putObject(jo4);
        
        writer.write(new File("testing.json"));  
    }
}
