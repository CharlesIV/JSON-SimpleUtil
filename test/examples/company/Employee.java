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

import com.reactnebula.simplejsonutil.*;

/**
 *
 * @author Charles
 * 
 * 
 * DISCLAIMER: This is just to demonstrate a possible way to use the SimpleJsonUtil.
 * The code here is not meant to be used for anything and is not written to be used
 * as a guide to writing code for a "Company", "Employee", or "EmployeeFactory".
 */
public class Employee implements Stringifiable {
    
    private final String firstName, lastName, department;
    private final char gender;
    private Employee manager;
    private int pay;
    
    public Employee(String firstName, String lastName, char gender, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public int getPay() {
        return pay;
    }
    
    public void setPay(int pay) {
        this.pay = pay;
    }
    
    public boolean hasManager() {
        return manager != null;
    }
    
    public void setManager(Employee e) {
        manager = e;
    }
    
    public Employee getManager() {
        return manager;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject jo = new JsonObject("customer");
        jo.put("firstName", firstName);
        jo.put("lastName", lastName);
        jo.put("gender", gender);
        jo.put("department", department);
        jo.put("pay", pay);
        if(hasManager())
            jo.putObject("manager", manager.toJson());
        return jo;
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Employee))
            return false;
        Employee e = (Employee)o;
        
        if(manager == null) {
            if(e.manager != null)
                return false;
        } else if(!manager.equals(manager))
            return false;
        
        return lastName.equals(e.lastName) && firstName.equals(e.firstName) && pay == e.pay && gender == e.gender && department.equals(e.department);
    }
}
