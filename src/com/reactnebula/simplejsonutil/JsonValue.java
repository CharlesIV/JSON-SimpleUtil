package com.reactnebula.simplejsonutil;

import com.reactnebula.simplejsonutil.exceptions.PrimitiveWrapperException;

/**
 *
 * @author Charles
 */
class JsonValue extends JsonData {
    
    public void put(String name, byte number) {
        appendBeginning(name);
        sb.append(number);
        appendEnding();
    }
    
    public void put(String name, short number) {
        appendBeginning(name);
        sb.append(number);
        appendEnding();
    }
    
    public void put(String name, int number) {
        appendBeginning(name);
        sb.append(number);
        appendEnding();
    }
    
    public void put(String name, float number) {
        appendBeginning(name);
        sb.append(number);
        appendEnding();
    }
    
    public void put(String name, double number) {
        appendBeginning(name);
        sb.append(number);
        appendEnding();
    }
    
    public void put(String name, long number) {
        appendBeginning(name);
        sb.append(number);
        appendEnding();
    }
    
    public void put(String name, String string) {
        appendBeginning(name);
        sb.append('"').append(escapeString(string)).append('"');
        appendEnding();
    }
    
    public void put(String name, boolean bool) {
        appendBeginning(name);
        sb.append(bool);
        appendEnding();
    }
    
    public void put(String name, char character) {
        appendBeginning(name);
        sb.append('"').append(escapeCharacter(character)).append('"');
        appendEnding();
    }
    
    public void put(String name) {
        appendBeginning(name);
        sb.append("null");
        appendEnding();
    }
    
    public void put(String name, byte[] numbers) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
            if(i < numbers.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, short[] numbers) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
            if(i < numbers.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, int[] numbers) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
            if(i < numbers.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, float[] numbers) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
            if(i < numbers.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, double[] numbers) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
            if(i < numbers.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, long[] numbers) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
            if(i < numbers.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, String[] strings) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < strings.length; i++) {
            sb.append('"').append(escapeString(strings[i])).append('"');
            if(i < strings.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, boolean[] bools) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < bools.length; i++) {
            sb.append(bools[i]);
            if(i < bools.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    public void put(String name, char[] characters) {
        appendBeginning(name);
        sb.append("[");
        for(int i = 0; i < characters.length; i++) {
            sb.append('"').append(escapeCharacter(characters[i])).append('"');
            if(i < characters.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
    }
    
    /**
     * Used to insert a generic type. Generic type must be either a primitive wrapper or String.
     * 
     * @param name
     * @param obj a generic object which can be cast to a primitive wrapper or String. Null is not supported.
     * @throws PrimitiveWrapperException when obj cannot be cast to a primitive wrapper or String
     */
    public void putGenericPrimitive(String name, Object obj) throws PrimitiveWrapperException {
        if(obj instanceof Integer) {
            put(name, (Integer)obj);
        } else if(obj instanceof String) {
            put(name, (String)obj);
        } else if(obj instanceof Double) {
            put(name, (Double)obj);
        } else if(obj instanceof Float) {
            put(name, (Float)obj);
        } else if(obj instanceof Boolean) {
            put(name, (Boolean)obj);
        } else if(obj instanceof Character) {
            put(name, (Character)obj);
        } else if(obj instanceof Byte) {
            put(name, (Byte)obj);
        } else if(obj instanceof Short) {
            put(name, (Short)obj);
        } else if(obj instanceof Long) {
            put(name, (Long)obj);
        } else {
            throw new PrimitiveWrapperException(name, obj != null ? obj.getClass().getSimpleName() : "null");
        }
    }
    
    private String escapeCharacter(char c) {
        switch(c) {
            case '\\':
                return "\\\\";
            case '\"':
                return "\\\"";
            case '\n':
                return "\\\n";
            case '\t':
                return "\\\t";
            case '\'':
                return "\\\'";
            case '\f':
                return "\\\f";
            case '\r':
                return "\\\r";
            case '\b':
                return "\\\b";
            default:
                return ""+c;
        }
    }
    
    private String escapeString(String string) {
        if(string.contains("\\"))
            string = string.replace("\\", "\\\\");
        if(string.contains("\""))
            string = string.replace("\"", "\\\"");
        if(string.contains("\n"))
            string = string.replace("\n", "\\\n");
        if(string.contains("\t"))
            string = string.replace("\t", "\\\t");
        if(string.contains("\'"))
            string = string.replace("\'", "\\\'");
        if(string.contains("\f"))
            string = string.replace("\f", "\\\f");
        if(string.contains("\r"))
            string = string.replace("\r", "\\\r");
        if(string.contains("\b"))
            string = string.replace("\b", "\\\b");
        return string;
    }
    
    @Override
    String writeLast() {
        sb.deleteCharAt(sb.length()-2);
        return write();
    }
    
    @Override
    String write() {
        return sb.toString();
    }
    
    protected void appendBeginning(String name) {
        sb.append(TAB);
        sb.append('"').append(name);
        sb.append(SEPERATOR);
    }
    
    protected void appendEnding() {
        sb.append(",");
        sb.append("\n");
    }
}
