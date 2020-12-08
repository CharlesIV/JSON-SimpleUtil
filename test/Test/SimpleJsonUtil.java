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

import com.reactnebula.simplejsonutil.*;
import java.io.IOException;

/**
 *
 * @author Charles
 */
public class SimpleJsonUtil {
    public static void main(String[] args) throws IOException {
        JsonWriter writer = new JsonWriter();
        EmployeeFactory factory = new EmployeeFactory();
        Employee emp1 = factory.newCustomer("Bill", "Johnson", 'm', "Arizonia", 23);
        Employee emp2 = factory.newCustomer("Deborah", "Johnson", 'f', "Arizonia", 21);
        Employee emp3 = factory.newCustomer("John", "Wilson", 'm', "California", 33);
        Employee emp4 = factory.newCustomer("Ethan", "Ballar", 'm', "South Carolina", 42);
        
        String company = "Code R Us";
        String ceo = "John Brick";
        writer.put("company", company);
        writer.put("ceo", ceo);
        
        JsonObjectArray employees = writer.putObjectArray("employees");
        employees.putObject(emp1.toJson());
        employees.putObject(emp2.toJson());
        employees.putObject(emp3.toJson());
        employees.putObject(emp4.toJson());
        
        writer.write("company.json"); //throws IOException
        
        JsonParser parser = new JsonParser("company.json");
        try {
            //Parsing for any value can throw a JsonValueNotFoundException
            String pCompany = parser.parseString("company");
            System.out.println(pCompany);
            JsonObject[] pEmployees = parser.parseObjectArray("employees");
            Employee pEmp1 = factory.fromJson(pEmployees[0]); //throws IncompatibleJsonObjectException
            boolean match = emp1.equals(pEmp1);
            System.out.println(match);
        } catch (JsonValueNotFoundException | IncompatibleJsonObjectException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
