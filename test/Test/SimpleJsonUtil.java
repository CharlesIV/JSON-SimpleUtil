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

import com.reactnebula.simplejsonutil.exceptions.IncompatibleJsonObjectException;
import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import com.reactnebula.simplejsonutil.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Charles
 */
public class SimpleJsonUtil {
    public static void main(String[] args) throws IOException, JsonValueNotFoundException {
        JsonWriter writer = new JsonWriter();
        EmployeeFactory factory = new EmployeeFactory();
        Employee emp1 = factory.newEmployee("Bill", "Johnson", 'm', "Arizonia", 23, "Jim", "Catherine");
        Employee emp2 = factory.newEmployee("Deborah", "Johnson", 'f', "Arizonia", 21);
        Employee emp3 = factory.newEmployee("John", "Wilson", 'm', "California", 33);
        Employee emp4 = factory.newEmployee("Ethan", "Ballar", 'm', "South Carolina", 42);
        
        String company = "Code R Us";
        String ceo = "John Brick";
        writer.put("company", company);
        writer.put("ceo", ceo);
        JsonObject building = writer.putObject("building");
        building.put("address", "8213 W 1223 N, Legit, Arizonia");
        building.put("security", true);
        
        JsonObjectArray products = writer.putObjectArray("products");
            JsonObject pr1 = products.putObject();
                pr1.put("name", "3D Games");
                pr1.put("description", "Amazing never seen games.");
            JsonObject pr2 = products.putObject();
                pr2.put("name", "JsonUtil");
                pr2.put("description", "A hopefully good utility.");
                JsonObjectArray packages = pr2.putObjectArray("packages");
                    JsonObject pa1 = packages.putObject();
                        pa1.put("title", "beginner");
                        pa1.put("price", 20);
                    JsonObject pa2 = packages.putObject();
                        pa2.put("title", "intermediate");
                        pa2.put("price", 40);
        
        JsonParser p = new JsonParser(pr2);
        p.parseString("name");
                        
        JsonObjectArray employees = writer.putObjectArray("employees");
        employees.putObject(emp1.toJson());
        employees.putObject(emp2.toJson());
        employees.putObject(emp3.toJson());
        employees.putObject(emp4.toJson());
        
        writer.write(new File("company.json")); //throws IOException
        
        JsonParser parser = new JsonParser(new File("company.json"));
        try {
            //Parsing for any value can throw a JsonValueNotFoundException
            boolean address = parser.parseBoolean("building.security");
            System.out.println(address);
            
            JsonObject[] pEmployees = parser.parseObjectArray("employees");
            Employee pEmp1 = factory.fromJson(pEmployees[0]); //throws IncompatibleJsonObjectException
            
            String[] children = new JsonParser(pEmployees[2]).parseStringArray("children");
            System.out.println(children.length);
            
            boolean match = emp1.equals(pEmp1);
            System.out.println(match);
            
        } catch (JsonValueNotFoundException | IncompatibleJsonObjectException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }
}
