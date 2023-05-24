package edu.ktu.MVPmm.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwitchStatementTest {

    @Test
    void switch_default(){
        String program = """
                         int i(5);
                         switch(i):
                         case (4) {
                            print(4);
                         }
                         default {
                            print("default");
                         }
                         """;

        String expected = """
                          default
                          """;

        String actual = MVPmmInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void switch_single_case(){
        String program = """
                         int i(5);
                         switch(i):
                         case (5) {
                            print(5);
                         }
                         default {
                            print("default");
                         }
                         """;

        String expected = """
                          5
                          """;

        String actual = MVPmmInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void switch_multiple_case(){
        String program = """
                         string str("abc");
                         string caseStr("abc");
                         switch(str):
                         case ("abd") {
                            print(1);
                         }
                         case (caseStr) {
                            int a(2);
                            print(a);
                         }
                         case ("cba") {
                            print(3);
                         }
                         default {
                            print("default");
                         }
                         """;

        String expected = """
                          2
                          """;

        String actual = MVPmmInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void switch_no_default(){
        String program = """
                         int i(3);
                         switch(i):
                         case (4) {
                            print(4);
                         }
                         case (5) {
                            print(5);
                         }
                         """;

        String expected = """
                          """;

        String actual = MVPmmInterpreter.execute(program);
        assertEquals(expected, actual);
    }
}
