package edu.ktu.glang.interpreter;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionTest {
    @Test
    void function_call_no_args(){
        String program = """
                        fun void testas() {
                        print(5);
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
    void function_call_with_args(){
        String program = """
                        fun void testas (int a) {
                        print(5);
                        }
                        int a = 10;
                        testas(a)
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
                        fun void testas (string a) {
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
                        fun void testas (int b) {
                        print(b);
                        }
                        int a = 10;
                        testas(a)
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
                        fun void testas (int a, int b) {
                        print(a);
                        print(b);
                        }
                        int a = 10;
                        int b = 5;
                        testas(a, b)
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
                        fun void testas (int a) {
                        print(5);
                        }
                        testas()
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void function_call_operator_overload(){
        String program = """
                        fun void testas (int a, int b) {
                        print(5);
                        }
                        int c = 5;
                        int d = 9;
                        testas(c, == <, d)
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_mixed_arguments(){
        String program = """
                        fun void testas (int a, string b) {
                        print(a);
                        print(b);
                        print(a + 1);
                        }
                        int c = 4;
                        string d = "abc";
                        testas(c, + -, d)
                         """;

        String expected = """
                          4
                          abc
                          3
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_multiple_operator_overloads(){
        String program = """
                        fun void testas () {
                            if(5 != 5){
                                print(4 + 3);
                            }
                        }
                        testas(+ -, != ==)
                         """;

        String expected = """
                          1
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_only_operator_overload(){
        String program = """
                        fun void testas () {
                            if(5 != 5){
                                print(6);
                            }
                        }
                        testas(!= ==)
                         """;

        String expected = """
                          6
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_call_operator_overload_mismatch(){
        String program = """
                        fun void testas () {
                        print(5);
                        }
                        int a = 5;
                        testas(< +)
                         """;

        assertThrows(ParseCancellationException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void function_return_value(){
        String program = """
                        fun int testas () {
                            return 5;
                        }
                        int a = testas();
                        print(a);
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_return_void(){
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
    void function_recursive_fibonaci_sequence(){
        String program = """
                        fun int testas (int n) {
                            if(n <= 1){
                                return n;
                            }
                            else{
                                int recOne = n - 1;
                                int recTwo = n - 2;
                                int fib = testas(recOne) + testas(recTwo);
                            return fib;
                            }
                        }
                        int b = 12;
                        int a = testas(b);
                        print(a);
                         """;

        String expected = """
                          144
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void function_return_type_mismatch(){
        String program = """
                        fun int testas () {
                            return "abc";
                        }
                        testas()
                         """;

        assertThrows(ClassCastException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }

    @Test
    void function_return_empty(){
        String program = """
                        fun int testas () {
                            return;
                        }
                        testas()
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }
}
