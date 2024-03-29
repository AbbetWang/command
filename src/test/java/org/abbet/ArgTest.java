package org.abbet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgTest {
    //-l -p 8080 -d /usr/logs
    // [-l], [-p,8080] ,[-d, /usr/logs]
    @Test
    public void should(){
       Options options = Args.parse(Options.class,"-l","-p","8080","-d","/usr/logs");
       assertTrue(options.logging());
       assertEquals(8080,options.port());
       assertEquals("/usr/logs",options.directory());
    }
    static record Options(@Option("l")boolean logging,@Option("p")int port,@Option("d")String directory){}

}