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

/**
 *
 * @author Charles
 */
public class Employee implements Stringifiable {
    
    private String firstName, lastName, address;
    private char gender;
    private int age;
    private String[] children;
    
    public Employee(String firstName, String lastName, char gender, String address, int age, String... children) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.children = children;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }
    
    public boolean hasChildren() {
        return children.length > 0;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject jo = new JsonObject("customer");
        jo.put("firstName", firstName);
        jo.put("lastName", lastName);
        jo.put("gender", gender);
        jo.put("address", address);
        jo.put("age", age);
        jo.put("children", children);
        return jo;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Employee))
            return false;
        Employee e = (Employee)o;
        if(children.length != e.children.length)
            return false;
        for(int i = 0; i < children.length; i++)
            if(!children[i].equals(e.children[i]))
                return false;
        return lastName.equals(e.lastName) && firstName.equals(e.firstName) && age == e.age && gender == e.gender && address.equals(e.address);
    }
}
