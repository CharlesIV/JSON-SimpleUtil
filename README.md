# SimpleJsonUtil
A simple java utility to parse and write JSON

This utility was made for those who need a simple way of writing and parsing primitives, Strings, and arrays to a JSON file. There is also support for JSON Object creation. It is meant to give you explicit control over the exact information you write to JSON without the use of reflection. If there are any problems and/or suggestions please let me know. 

The parser supports passing in a dot notation to a value, ex: "building.address".

Escaped characters are allowed in String objects.

Parser will now throw a universal RuntimeException if you use the wrong parse method for a value. You can still use parseStringedValue() to get the value of any variable or object in String format.
