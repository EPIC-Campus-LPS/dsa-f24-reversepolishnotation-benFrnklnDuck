import java.util.EmptyStackException;

public class ReversePolishNotation {
    /**
     * Create a stack to hold numbers in the equation
     * While loop: continues until input equation is empty
     * if an operator is found, take off the last two things in the stack, perform the operation, and push the result
     * if the last operator is reached, ie length < 3, end the while loop
     * else cut the operator off of the input
     * if a number is found, push it and cut it off of the input
     * check that there's only one number after the loop ends
     * catch any exceptions
     * @param input a postfix style equation in the form of the string to be evaluated
     * @return the integer form of the answer after evaluation
     */
    public static int evaluatePostfix (String input) {
        try {
            Stack stack = new Stack();
            while (input.length() > 0) {
                if (input.substring(0, 1).equals("*")) {
                    int nextNum = Integer.parseInt(stack.pop()) * Integer.parseInt(stack.pop());
                    stack.push(nextNum + "");
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else if (input.substring(0, 1).equals("/")) {
                    int lastNum = Integer.parseInt(stack.pop());
                    int nextNum = Integer.parseInt(stack.pop()) / lastNum;
                    stack.push(nextNum + "");
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else if (input.substring(0, 1).equals("+")) {
                    int nextNum = Integer.parseInt(stack.pop()) + Integer.parseInt(stack.pop());
                    stack.push(nextNum + "");
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else if (input.substring(0, 1).equals("-")) {
                    int lastNum = Integer.parseInt(stack.pop());
                    int nextNum = Integer.parseInt(stack.pop()) - lastNum;
                    stack.push(nextNum + "");
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else {
                    stack.push(input.substring(0, input.indexOf(" ")));
                    input = input.substring(input.indexOf(" ") + 1);
                }
            }
            if (stack.size() > 1) {
                throw new IllegalArgumentException("Invalid postfix expression.");
            }
            return Integer.parseInt(stack.pop());
        } catch (EmptyStackException|StringIndexOutOfBoundsException|NegativeArraySizeException e) {
            throw new IllegalArgumentException("Invalid postfix expression.");
        }
    }

    /**
     * check for specific exceptions
     * create a new stack to hold the operators
     * while loop: continues until equation is empty
     * if the first thing is an operator, push it without question and cut it from the input
     * if it is an open parenthesis, push it without question and cut it from the input
     * if it is a closed parenthesis, pop every operator until the open parenthesis is reached and add them to the output, then pop the parenthesis
     * If the next thing is an operator:
     * if there's anything on the stack of higher precedence, pop everything that isn't of lower precedence and add it to the output
     * push the operator and cut it from the input
     * if it is a number/letter, push it to the output and cut it from the input
     * push everything left over on the stack
     * catch any exceptions
     * this method calls findPrecedence and isOperator
     * @param input an infix equation in the form of a string to be changed
     * @return a string representing the original input but in postfix form
     */
    public static String infixToPostfix (String input) {
        int opCount = 0;
        int opendCount = 0;
        int inRow = 0;
        int begParenCount = 0;
        int endParenCount = 0;
        String tempIn = input;
        while (tempIn.length() > 0) {
            if (inRow > 1) {
                throw new IllegalArgumentException("Improper infix.");
            }
            if (tempIn.substring(0, 1).equals("(")) {
                //System.out.print("beginning parenthesis ");
                begParenCount++;
                if (tempIn.length() < 3) {
                    tempIn = "";
                } else {
                    tempIn = tempIn.substring(2);
                }
            } else if (tempIn.substring(0, 1).equals(")")) {
                //System.out.print("end parenthesis ");
                endParenCount++;
                if (tempIn.length() < 3) {
                    tempIn = "";
                } else {
                    tempIn = tempIn.substring(2);
                }
            } else if (isOperator(tempIn.substring(0, 1))) {
                //System.out.print("operator ");
                opCount ++;
                if (tempIn.length() < 3) {
                    tempIn = "";
                } else {
                    tempIn = tempIn.substring(2);
                }
                inRow++;
            } else {
                //System.out.print("num ");
                inRow = 0;
                opendCount++;
                if (tempIn.indexOf(" ") == -1) {
                    tempIn = "";
                } else {
                    tempIn = tempIn.substring(tempIn.indexOf(" ") + 1);
                }
            }
        }
        if ((opCount + 1) != opendCount) {
            throw new IllegalArgumentException("Improper infix.");
        }
        if (begParenCount != endParenCount) {
            throw new IllegalArgumentException("Improper infix.");
        }
        try {
            Stack stack = new Stack();
            String output = "";
            while (input.length() > 0) {
                if (isOperator(input.substring(0, 1)) && stack.isEmpty()) {
                    stack.push(input.substring(0, 1));
                    input = input.substring(2);
                } else if (input.substring(0, 1).equals("(")) {
                    stack.push("(");
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else if (input.substring(0, 1).equals(")")) {
                    while (!stack.peek().equals("(")) {
                        output += stack.pop() + " ";
                    }
                    stack.pop();
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else if (input.substring(0, 1).equals("*") || input.substring(0, 1).equals("/")) {
                    while (!stack.isEmpty() && findPrecedence(stack.peek()) >= findPrecedence(input.substring(0, 1))) {
                        output += stack.pop() + " ";
                    }
                    stack.push(input.substring(0, 1));
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else if (input.substring(0, 1).equals("+") || input.substring(0, 1).equals("-")) {
                    while (!stack.isEmpty() && findPrecedence(stack.peek()) >= findPrecedence(input.substring(0, 1))) {
                        output += stack.pop() + " ";
                    }
                    stack.push(input.substring(0, 1));
                    if (input.length() < 3) {
                        input = "";
                    } else {
                        input = input.substring(2);
                    }
                } else {
                    if (input.indexOf(" ") == -1) {
                        output += input + " ";
                        input = "";
                    } else {
                        output += input.substring(0, input.indexOf(" ")) + " ";
                        input = input.substring(input.indexOf(" ") + 1);
                    }
                }
            }
            while (!stack.isEmpty()) {
                output += stack.pop() + " ";
            }
            return output;
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Not proper infix.");
        }
    }

    /**
     * if it is addition or subtraction, return 1
     * if it is multiplication or division, return 2
     * if neither, return 0
     * @param operator the operator whose precedence should be evaluated
     * @return a number representing its precedence based on order of operations, parenthesis being a special case
     */
    public static int findPrecedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * checks if the given value is an operator or not
     * @param str the single character string to be looked at
     * @return a boolean representing if the given character is an operator
     */
    public static boolean isOperator(String str) {
        if (str.equals("(") || str.equals("*") || str.equals("/") || str.equals("+") || str.equals("-")) {
            return true;
        }
        return false;
    }

    //EC
    public static String prefixToInfix (String input) {
        return "idk";
    }

    public static String postFixToInfix (String input) {
        return "idk";
    }

    public static int evaluatePrefix (String input) {
        return -1;
    }

    public static String infixToPrefix (String input) {
        return "idk";
    }
}
