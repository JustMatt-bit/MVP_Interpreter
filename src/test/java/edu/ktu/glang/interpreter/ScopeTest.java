package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScopeTest {
    @Test
    void variable_in_cope(){
        String program = """
                         if(5 == 5){
                         int a = 5;
                         print(a);
                         }
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void variable_out_of_scope(){
        String program = """
                         if(5 == 5){
                         int a = 5;
                         }
                         print(a);
                         """;
        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void variable_out_of_function_scope(){
        String program = """
                        int a = 5;
                        fun void testas () {
                        print(a);
                        }
                        testas();
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void variable_only_in_function_scope(){
        String program = """
                        fun void testas () {
                        int a = 5;
                        }
                        testas();
                        print(a);
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void return_in_function(){
        String program = """
                        fun void testas () {
                        print(5);
                        return;
                        print(6);
                        }
                        testas()
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void return_in_main_program(){
        String program = """
                        return 5;
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }
}
