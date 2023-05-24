# MVPmm-interpreter

# MVPmm-base
MVPmm is a simple scripting language created with ANTLR4 using visitor pattern and supports the following features:
1. Variables: variables can be assigned and used in expressions.
2. Arithmetic expressions: arithmetic expressions can be used involving addition, subtraction, multiplication, division, and modulus operations.
3. Parentheses: parentheses can be used to group subexpressions to control operator precedence.
4. Conditional statements: the language allows for if-else statements that can evaluate boolean expressions and execute different blocks of code based on the result. It also support switch statements, that can compare a value to multiple expressions.
5. Functions: functions can be declared, their return and parameter type can be specified and function operators can be overloaded.
6. Comparison operators: the grammar allows for comparison operators such as ==, !=, <, >, <=, and >=.
7. Printing: the language includes a simple print statement that can output the value of an expression.
8. Printing to file: the language includes print statements that can output the value of an expression to a new file, or to append to an existing file.

#### Tools

> Oracle OpenJDK 19.0.2

> Apache Maven 3.8.5

> IntelliJ IDEA Ultimate 2022.3.2
> - ANTLR v4 plugin

#### Build

Generate ANTLR visitor with:

    mvn clean antlr4:antlr4@generate-visitor
    or with MVPmm-interpreter[generate-visitor] run configuration

Build jar with:

    mvn clean install
    or with MVPmm-interpreter[clean,install] run configuration

Run jar with:

    java -jar target/MVPmm-interpreter-1.0.jar -f samples/test.MVPmm
    java -jar target/MVPmm-interpreter-1.0.jar -i