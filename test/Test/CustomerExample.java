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
public class CustomerExample implements Stringifiable {
    
    String firstName;
    String lastName;
    String address;
    int age;
    String[] children;
    
    public CustomerExample(String firstName, String lastName, String address, int age, String... children) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.children = children;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject jo = new JsonObject("customer");
        jo.put("firstName", firstName);
        jo.put("lastName", lastName);
        jo.put("address", address);
        jo.put("age", age);
        jo.put("children", children);
        return jo;
    }

    public static CustomerExample fromJson(JsonObject jo) throws IncompatibleJsonObjectException {
        JsonParser jp = new JsonParser(jo);
        try {
            String firstName = jp.parseString("firstName");
            String lastName = jp.parseString("lastName");
            String address = jp.parseString("address");
            int age = jp.parseInteger("age");
            String[] children = jp.parseStringArray("children");
            return new CustomerExample(firstName, lastName, address, age, children);
        } catch (JsonValueNotFoundException ex) {
            throw new IncompatibleJsonObjectException(ex.getMessage());
        }
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(" ");
        sb.append(lastName).append(", ");
        sb.append(address).append(", ");
        sb.append(age).append(", ");
        for(int i = 0; i < children.length; i++)
            sb.append(children[i]).append(":");
        return sb.toString();
    }
}
