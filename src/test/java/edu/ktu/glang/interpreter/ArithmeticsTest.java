package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArithmeticsTest {

    @Test
    void print_int_add_expression() {
        String program = """
                         print(2+3);
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void print_int_multiplication_expression() {
        String program = """
                         print(2*3);
                         """;

        String expected = """
                          6
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void print_int_complex1_expression() {
        String program = """
                         print(1+2*3);
                         """;

        String expected = """
                          7
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void print_int_complex2_expression() {
        String program = """
                         print(8/2*(2+2));
                         """;

        String expected = """
                          16
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void print_double_add_expression() {
        String program = """
                         print(3+2.5);
                         """;

        String expected = """
                          5.5
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void print_double_multiplication_expression() {
        String program = """
                         print(2.25*3);
                         """;

        String expected = """
                          6.75
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void print_double_complex1_expression() {
        String program = """
                         print(1.5+2.25*3.5);
                         """;

        String expected = """
                          9.375
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void bad_arithemtic_operation_type() {
        String program = """
                         print("abc" + 2.5);
                         """;

        assertThrows(ClassCastException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }
}