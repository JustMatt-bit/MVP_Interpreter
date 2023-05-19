package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionTest {
    @Test
    void function_call_no_args(){
        String program = """
                        fun int testas() {
                        print(5);
                        }
                        testas();
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_with_args(){
        String program = """
                        fun int testas (int a) {
                        print(5);
                        }
                        int a = 10;
                        testas(a);
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_args_mismatch(){
        String program = """
                        fun int testas (string a) {
                        print(5);
                        }
                        int a = 10;
                        testas(a);
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void function_call_uses_args(){
        String program = """
                        fun int testas (int b) {
                        print(b);
                        }
                        int a = 10;
                        testas(a);
                         """;

        String expected = """
                          10
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_uses_multiple_args(){
        String program = """
                        fun int testas (int a, int b) {
                        print(a);
                        print(b);
                        }
                        int a = 10;
                        int b = 5;
                        testas(a, b);
                         """;

        String expected = """
                          10
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_without_args(){
        String program = """
                        fun int testas (int a) {
                        print(5);
                        }
                        testas();
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void function_call_operator_overload(){
        String program = """
                        fun int testas (int a, ==, int b) {
                        print(5);
                        }
                        int c = 5;
                        int d = 9;
                        testas(c, <, d);
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_only_operator_overload(){
        String program = """
                        fun int testas (==) {
                        print(5);
                        }
                        testas(<);
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_operator_overload_mismatch(){
        String program = """
                        fun int testas (==) {
                        print(5);
                        }
                        int a = 5;
                        testas(a);
                         """;

        String expected = """
                          5
                          """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }
}
