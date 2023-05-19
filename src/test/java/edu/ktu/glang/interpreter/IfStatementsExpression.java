package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IfStatementsExpression {
    @Test
    void if_true(){
        String program = """
                         if(5 == 5){
                         print(5);
                         }
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void if_false(){
        String program = """
                         if(5 == 8){
                         print(5);
                         }
                         """;

        String expected = """
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void if_else(){
        String program = """
                         if(5 == 8){
                         print(5);
                         }
                         else{
                         print(6);
                         }
                         """;

        String expected = """
                          6
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void if_true_multiple(){
        String program = """
                         if(5 == 5){
                         print(5);
                         print(6);
                         }
                         """;

        String expected = """
                          5
                          6
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void if_else_multiple(){
        String program = """
                         if(5 == 8){
                         print(5);
                         print(6);
                         }
                         else{
                         print(7);
                         print(8);
                         }
                         """;

        String expected = """
                          7
                          8
                          """;

        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }
}