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
package examples.company;

import com.reactnebula.simplejsonutil.JsonObject;
import com.reactnebula.simplejsonutil.JsonObjectArray;
import com.reactnebula.simplejsonutil.JsonParser;
import com.reactnebula.simplejsonutil.JsonWriter;
import com.reactnebula.simplejsonutil.Stringifiable;
import com.reactnebula.simplejsonutil.exceptions.IncompatibleJsonObjectException;
import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Charles
 * 
 * 
 * DISCLAIMER: This is just to demonstrate a possible way to use the SimpleJsonUtil.
 * The code here is not meant to be used for anything and is not written to be used
 * as a guide to writing code for a "Company", "Employee", or "EmployeeFactory". ie.
 * It stores too much information by embedding each manager when writing to JSON, but
 * it's intentional to show that embedded objects do work.
 */
public class Company implements Stringifiable {
    public final String NAME;
    private final ArrayList<Employee> employees = new ArrayList<>();
    
    public Company(String name) {
        NAME = name;
    }
    
    public void addEmployee(Employee e) {
        employees.add(e);
    }
    
    public Employee getEmployee(String firstName, String lastName) {
        for(Employee e : employees) {
            if(e.getFirstName().equals(firstName) && e.getLastName().equals(lastName))
                return e;
        }
        return null;
    }
    
    public void exportCompany(String filePath) throws IOException {
        JsonWriter writer = new JsonWriter(toJson());
        writer.write(new File(filePath));
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject comp = new JsonObject("company");
        comp.put("name", NAME);
        JsonObjectArray employ = comp.putObjectArray("employees");
        for(Employee e : employees)
            employ.putObject(e.toJson());
        return comp;
    }
    
    public static void main(String[] args) throws IOException {
        EmployeeFactory factory = new EmployeeFactory();
        try {
            JsonParser parser = new JsonParser(new File("company.json"));
            String name = parser.parseString("name");
            JsonObject[] employees = parser.parseObjectArray("employees");
            
            Company abc = new Company(name);
            for(JsonObject employ : employees)
                abc.addEmployee(factory.fromJson(employ));
            
            System.out.println(abc.getEmployee("Charles", "Langley").getManager().getFirstName());
            
        } catch(IOException ex) {
            Company abc = new Company("Programming R Us");
            Employee a = factory.newEmployee("Charles", "Langley", 'm', "Programmer");
            Employee b = factory.newEmployee("Bill", "Simson", 'm', "Owner");
            Employee c = factory.newEmployee("Julia", "Simson", 'f', "Programmer");
            Employee d = factory.newEmployee("Hanna", "Barley", 'm', "Management");        
            Employee e = factory.newEmployee("Mack", "Peppers", 'm', "Artist");
            Employee f = factory.newEmployee("Jack", "Linn", 'm', "Finances");

            a.setManager(d);
            c.setManager(d);
            d.setManager(b);
            e.setManager(d);
            f.setManager(b);

            abc.addEmployee(a);
            abc.addEmployee(b);
            abc.addEmployee(c);
            abc.addEmployee(d);
            abc.addEmployee(e);
            abc.addEmployee(f);

            abc.exportCompany("company.json");
        } catch(JsonValueNotFoundException | IncompatibleJsonObjectException ex) {
            System.err.println("Example failed");
        }
    }
}
