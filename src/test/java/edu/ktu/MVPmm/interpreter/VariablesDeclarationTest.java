package edu.ktu.MVPmm.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariablesDeclarationTest {

    @Test
    void declare_and_print_variable() {
        String program = """
                         int a(5);            
                         print(a);       
                         """;

        String expected = """
                          5
                          """;

        String actual = MVPmmInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void undeclared_variable_throws_exception() {
        String program = """
                         a(5);            
                         print(a);       
                         """;

        assertThrows(RuntimeException.class,
                () -> {
                    MVPmmInterpreter.execute(program);
                });
    }

    @Test
    void assing_variable_and_print() {
        String program = """
                         int a(5);
                         a(6);          
                         print(a);       
                         """;

        String expected = """
                          6
                          """;

        String actual = MVPmmInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void assing_variable_with_type_mismatch() {
        String program = """
                         int a(5);
                         a("abc");                  
                         """;

        assertThrows(ClassCastException.class,
                () -> {
                    MVPmmInterpreter.execute(program);
                });
    }
}
