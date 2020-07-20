/*
 * Copyright (C) 2020 Charles
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

import com.reactnebula.simplejsonutil.JsonObject;
import com.reactnebula.simplejsonutil.JsonParser;
import com.reactnebula.simplejsonutil.JsonValueNotFoundException;
import com.reactnebula.simplejsonutil.JsonWriter;
import java.io.IOException;

/**
 *
 * @author Charles
 */
public class SimpleJsonUtil {
    public static void main(String[] args) {
        JsonWriter writer = new JsonWriter();
        writer.put("company", "Numbers R Us");
        writer.put("owner", "John");
        writer.put("departments", new String[]{"finance", "production", "sales", "manager"});
        
        JsonObject emp = writer.putObject("employee");
            emp.put("name", "Jim");
            emp.put("age", 26);
            emp.put("manager", true);
            JsonObject contact = emp.putObject("contact");
                contact.put("email", "jim@aol.com");
                contact.put("phone", "222-222-2222");
        try {
            writer.write("test.json");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try {
            JsonParser parser = new JsonParser("test.json");
            boolean manager = parser.parseBoolean("manager");
            System.out.println(manager);
            
            JsonObject emp2 = parser.parseObject("employee");
            JsonParser newParser = new JsonParser(emp2);
            String name = newParser.parseString("name");
            System.out.println(name);
            
            JsonObject contact2 = newParser.parseObject("contact");
            newParser.setParser(contact2);
            String email = newParser.parseString("email");
            System.out.println(email);
        } catch(IOException e) {
            e.printStackTrace();
        } catch (JsonValueNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
