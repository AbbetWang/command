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
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.log());
    }

    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.log());
    }

    static record BooleanOption(@Option("l") boolean log) {
    }

    @Test
    public void should_set_int_option_if_use_p() {
        IntegerOption option = Args.parse(IntegerOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    static record IntegerOption(@Option("p") int port) {
    }

    @Test
    public void should_set_string_option() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());
    }

    static record StringOption(@Option("d") String directory) {
    }

    @Test
    public void should_parse_multi_option() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    
    @Test
    @Disabled
    public void should_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }

}