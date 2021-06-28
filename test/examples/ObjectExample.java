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

import com.reactnebula.simplejsonutil.JsonObject;
import com.reactnebula.simplejsonutil.JsonParser;
import com.reactnebula.simplejsonutil.JsonWriter;
import com.reactnebula.simplejsonutil.ObjectFactory;
import com.reactnebula.simplejsonutil.Stringifiable;
import com.reactnebula.simplejsonutil.exceptions.IncompatibleJsonObjectException;
import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Charles
 */
public class ObjectExample implements Stringifiable {
    int v1 = 0;
    double v2 = 1;
    String string = "string";
    int random = (int)(Math.random()*100);
    
    public static void main(String[] args) throws IOException {
        JsonWriter writer = new JsonWriter();
        
        ObjectExample oeo = new ObjectExample();
        writer.putObject("one", oeo.toJson());
        
        ObjectExample oet = new ObjectExample();
        JsonObject joe = writer.putObject("two");
        joe.put("v1", oet.v1);
        joe.put("v2", oet.v2);
        joe.put("string", oet.string);
        joe.put("random", oet.random);
        
        writer.write(new File("example.json"));
        
        JsonParser parser = new JsonParser(new File("example.json"));
        try {
            JsonObject jo = parser.parseObject("two");
            System.out.println("Original random: " + oet.random);
            
            //Parsing Example 1
            //Can be used to create the Object
            //Will have to use type casting
            //Can also pass in a premade ObjectFactory, see example 4
            ObjectExample examp = (ObjectExample)jo.toObject(o->{
                JsonParser parse = new JsonParser(o);
                ObjectExample oe = new ObjectExample();
                try {
                    oe.random = parse.parseInteger("random");
                } catch(JsonValueNotFoundException e) {
                    //can optionally create/repair the missing values
                    throw new IncompatibleJsonObjectException("Missing random value");
                }
                return oe;
            });
            System.out.println("Parsed random (1): " + examp.random);
            
            //Parsing Example 2
            //Can be useed to just extract the values
            //Reusing the parser if you dont need to parse anything else from the original JSON
            parser.setParser(jo);
            int rand = parser.parseInteger("random");
            System.out.println("Parsed random (2): " + rand);
            
            //Parsing Exmple 3
            //Can be useed to just extract the values
            //Making a new parser, that way you could reuse the original parser to parse the other ExampleObject (Can't in this example since it was overritten in Example 2)
            JsonParser parse = new JsonParser(jo);
            rand = parse.parseInteger("random");
            System.out.println("Parsed random (3): " + rand);
            
            //Parsing Example 4
            //Can be used to create the Object
            //Using a dedicated Factory that implements ObjectFactory
            ExampleFactory factory = new ExampleFactory();
            ObjectExample oef = factory.fromJson(jo); //or = jo.toObject(factory);
            System.out.println("Parsed random (4): " + oef.random);
            
            //Proof of parsing other values
            System.out.println(parse.parseString("string"));
            
        } catch(JsonValueNotFoundException | IncompatibleJsonObjectException ex) {
            System.err.println("Example failed.");
        }
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject jo = new JsonObject("oe");
        jo.put("v1", v1);
        jo.put("v2", v2);
        jo.put("string", string);
        jo.put("random", random);
        return jo;
    }
    
    private static class ExampleFactory implements ObjectFactory {

        @Override
        public ObjectExample fromJson(JsonObject jo) throws IncompatibleJsonObjectException {
            JsonParser parser = new JsonParser(jo);
            ObjectExample oe = new ObjectExample();
            try {
                int rand = parser.parseInteger("random");
                oe.random = rand;
            } catch(JsonValueNotFoundException ex) {
                //can optionally create/repair the missing values
                throw new IncompatibleJsonObjectException("Missing random value");
            }
            return oe;
        }
        
    }
}
