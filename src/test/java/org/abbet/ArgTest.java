package org.abbet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgTest {
    //-l -p 8080 -d /usr/logs
    // [-l], [-p,8080] ,[-d, /usr/logs]
    // {-l:[], -p:[8080], -d:[/usr/logs]}
    // Single Option
    @Test
    public void should_set_boolean_option_to_true_if_flag_present(){
        BooleanOption option = Args.parse(BooleanOption.class,"-l");
        assertTrue(option.log());
    }
    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present(){
        BooleanOption option = Args.parse(BooleanOption.class,"");
        assertFalse(option.log());
    }
    static record BooleanOption(@Option("l")boolean log){}
    //TODO:  -Integer -p 8080
    @Test
    public void should_set_int_option_if_use_p(){
        IntegerOption option = Args.parse(IntegerOption.class,"-p","8080");
        assertEquals(8080,option.port());
    }
    static record IntegerOption(@Option("p") int port){}
    //TODO:  -String -d /usr/logs
    //TODO: multi options: -l -p 8080 -d /usr/logs
    // sad path:
    //TODO: -bool -l t / -l t f
    //TODO: - int -p 8080 9080
    //TODO: -string -d /-d /usr/logs /usr/vars
    // default value
    //TODO: - bool : false
    //TODO: -int :0
    //TODO: - string: ""
    @Test
    @Disabled
    public void should_1(){
       Options options = Args.parse(Options.class,"-l","-p","8080","-d","/usr/logs");
       assertTrue(options.logging());
       assertEquals(8080,options.port());
       assertEquals("/usr/logs",options.directory());
    }
    @Test
    @Disabled
    public void should_2(){
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this","is","a","list"},options.group());
        assertArrayEquals(new int[]{1,2,-3,5},options.decimals());
    }
    static record Options(@Option("l")boolean logging,@Option("p")int port,@Option("d")String directory){}
    static record ListOptions(@Option("g")String[] group,@Option("d")int[] decimals){}

}