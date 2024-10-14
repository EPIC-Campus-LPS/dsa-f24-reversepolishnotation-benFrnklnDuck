import java.util.EmptyStackException;

public class ReversePolishNotation {
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
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Invalid postfix expression.");
        }
    }

    public static String infixToPostfix (String input) {
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
                    if (input.length() < 2) {
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


    public static int findPrecedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        } else {
            return 0;
        }
    }

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
