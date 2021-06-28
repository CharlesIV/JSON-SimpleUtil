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

import com.reactnebula.simplejsonutil.JsonParser;
import com.reactnebula.simplejsonutil.JsonWriter;
import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import java.util.Arrays;

/**
 *
 * @author Charles
 */
public class SimpleDataStorage {
    public static void main(String[] args) {
        int initial = 3;
        int v = 5;
        String mode = "addition";
        int[] iterations = new int[10];
        for(int i = 0; i < iterations.length; i++)
            iterations[i] = 3+i*v;
        
        JsonWriter writer = new JsonWriter();
        writer.put("initial", initial);
        writer.put("v", v);
        writer.put("mode", mode);
        writer.put("iterations", iterations);
        
        //Can now write the data as a String, file, stream, or JsonParser
        //For this demonstration I'll just write it to a JsonParser
        
        JsonParser parser = writer.toJsonParser();
        try {
            System.out.println(parser.parseInteger("initial"));
            System.out.println(parser.parseInteger("v"));
            System.out.println(parser.parseString("mode"));
            System.out.println(Arrays.toString(parser.parseIntegerArray("iterations")));
            
        } catch(JsonValueNotFoundException e) {
            System.err.println("Example failed.");
        }
    }
}
