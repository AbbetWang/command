package org.abbet;

class StringOptionParser extends IntOptionParser {
    
    public StringOptionParser() {
        super(String::valueOf);
    }
}
