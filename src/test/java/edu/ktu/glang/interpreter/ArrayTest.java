package edu.ktu.glang.interpreter;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArrayTest {
    @Test
    void array_simple_declare(){
        String program = """
                         int[2] arr;
                         """;

        String expected = """
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void array_declare_with_elements(){
        String program = """
                         int[5] arr = { 1, 2, 3 };
                         print(arr[0]);
                         print(arr[1]);
                         print(arr[2]);
                         print(arr[3]);
                         """;

        String expected = """
                          1
                          2
                          3
                          null
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void array_type_mismatch(){
        String program = """
                         string[2] arr = { 1, "abc" };
                         """;

        assertThrows(ClassCastException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void array_out_of_size(){
        String program = """
                         string[2] arr = { 1, 2, 3 };
                         """;

        assertThrows(IndexOutOfBoundsException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void array_assignment(){
        String program = """
                         int[2] arr;
                         arr[1] = 1;
                         print(arr[1]);
                         """;

        String expected = """
                          1
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void array_assignment_type_mismatch(){
        String program = """
                         int[2] arr;
                         arr[1] = "abc";
                         """;

        assertThrows(ClassCastException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }
}
