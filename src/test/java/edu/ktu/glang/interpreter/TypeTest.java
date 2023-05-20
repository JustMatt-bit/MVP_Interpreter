package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeTest {
    @Test
    void int_type_declaration(){
        String program = """
                         int a = 5;
                         print(a);
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void int_type_assignment(){
        String program = """
                         int a = 5;
                         a = 4;
                         print(a);
                         """;

        String expected = """
                          4
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void double_type_declaration(){
        String program = """
                         double a = 5.432;
                         print(a);
                         """;

        String expected = """
                          5.432
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void double_type_assignment(){
        String program = """
                         double a = 5.432;
                         a = 4.357;
                         print(a);
                         """;

        String expected = """
                          4.357
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void string_type_declaration(){
        String program = """
                         string a = "abc";
                         print(a);
                         """;

        String expected = """
                          abc
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void string_type_assignment(){
        String program = """
                         string a = "abc";
                         a = "abd";
                         print(a);
                         """;

        String expected = """
                          abd
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void bool_type_declaration(){
        String program = """
                         bool a = true;
                         print(a);
                         """;

        String expected = """
                          true
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void bool_type_assignment(){
        String program = """
                         bool a = true;
                         a = false;
                         print(a);
                         """;

        String expected = """
                          false
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }
}
