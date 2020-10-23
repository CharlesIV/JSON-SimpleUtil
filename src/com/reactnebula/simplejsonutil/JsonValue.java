package com.reactnebula.simplejsonutil;

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
        sb.append('"').append(string).append('"');
        appendEnding();
    }
    
    public void put(String name, boolean bool) {
        appendBeginning(name);
        sb.append(bool);
        appendEnding();
    }
    
    public void put(String name, char character) {
        appendBeginning(name);
        sb.append('"').append(character).append('"');
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
            sb.append('"').append(strings[i]).append('"');
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
            sb.append('"').append(characters[i]).append('"');
            if(i < characters.length-1)
                sb.append(", ");
        }
        sb.append("]");
        appendEnding();
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
        sb.append('"').append(name).append('"');
        sb.append(":");
    }
    
    protected void appendEnding() {
        sb.append(",");
        sb.append("\n");
    }
}
