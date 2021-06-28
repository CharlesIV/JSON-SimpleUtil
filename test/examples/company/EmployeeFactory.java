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

import com.reactnebula.simplejsonutil.exceptions.IncompatibleJsonObjectException;
import com.reactnebula.simplejsonutil.JsonObject;
import com.reactnebula.simplejsonutil.JsonParser;
import com.reactnebula.simplejsonutil.exceptions.JsonValueNotFoundException;
import com.reactnebula.simplejsonutil.ObjectFactory;

/**
 *
 * @author Charles
 * 
 * 
 * DISCLAIMER: This is just to demonstrate a possible way to use the SimpleJsonUtil.
 * The code here is not meant to be used for anything and is not written to be used
 * as a guide to writing code for a "Company", "Employee", or "EmployeeFactory".
 */
public class EmployeeFactory implements ObjectFactory {
    
    public static final int STARTING_PAY = 10;
    
    public Employee newEmployee(String firstName, String lastName, char gender, String department) {
        Employee e = new Employee(firstName, lastName, gender, department);
        e.setPay(STARTING_PAY);
        return e;
    }
    
    public Employee newEmployee(String firstName, String lastName, char gender, String department, int pay) {
        Employee e = new Employee(firstName, lastName, gender, department);
        e.setPay(pay);
        return e;
    }
    
    @Override
    public Employee fromJson(JsonObject jo) throws IncompatibleJsonObjectException {
        JsonParser jp = new JsonParser(jo);
        try {
            String firstName = jp.parseString("firstName");
            String lastName = jp.parseString("lastName");
            char gender = jp.parseCharacter("gender");
            String department = jp.parseString("department");
            int pay = jp.parseInteger("pay");
            
            Employee e = new Employee(firstName, lastName, gender, department);
            if(pay != STARTING_PAY)
                e.setPay(pay);
            
            
            try {
                JsonObject manager = jp.parseObject("manager");
                Employee manag = fromJson(manager);
                e.setManager(manag);
            } catch(JsonValueNotFoundException ex) {}
            
            return e;
        } catch (JsonValueNotFoundException ex) {
            throw new IncompatibleJsonObjectException(ex.getMessage());
        }
    }
}
