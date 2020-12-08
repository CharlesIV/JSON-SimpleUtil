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

import com.reactnebula.simplejsonutil.IncompatibleJsonObjectException;
import com.reactnebula.simplejsonutil.JsonObject;
import com.reactnebula.simplejsonutil.JsonParser;
import com.reactnebula.simplejsonutil.JsonValueNotFoundException;
import com.reactnebula.simplejsonutil.ObjectFactory;

/**
 *
 * @author Charles
 */
public class EmployeeFactory implements ObjectFactory {
    
    public Employee newCustomer(String firstName, String lastName, char gender, String address, int age, String... children) {
        return new Employee(firstName, lastName, gender, address, age, children);
    }
    
    @Override
    public Employee fromJson(JsonObject jo) throws IncompatibleJsonObjectException {
        JsonParser jp = new JsonParser(jo);
        try {
            String firstName = jp.parseString("firstName");
            String lastName = jp.parseString("lastName");
            char gender = jp.parseCharacter("gender");
            String address = jp.parseString("address");
            int age = jp.parseInteger("age");
            String[] children = jp.parseStringArray("children");
            return new Employee(firstName, lastName, gender, address, age, children);
        } catch (JsonValueNotFoundException ex) {
            throw new IncompatibleJsonObjectException(ex.getMessage());
        }
    }
}
