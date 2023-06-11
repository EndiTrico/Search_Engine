import java.util.*;

public class SearchEngine {
    private final Map<Integer, List<String>> index;
    private boolean isThereAnyQueryError;

    public Map<Integer, List<String>> getIndex() {
        return index;
    }

    public boolean isThereAnyQueryError() {
        return isThereAnyQueryError;
    }

    public void setThereAnyQueryError(boolean thereAnyQueryError) {
        isThereAnyQueryError = thereAnyQueryError;
    }

    public SearchEngine() {
        index = new HashMap<>();
        isThereAnyQueryError = false;
        retrieveSomeDocuments();
    }


    public void indexCommand(String docId, List<String> tokens) {

        if (!isNumeric(docId)) {
            System.out.println("index error the document ID does NOT contain only numbers!");
            return;
        }

        int documentID = Integer.parseInt(docId);

        if (!tokens.isEmpty()) {
            for (String token : tokens) {
                if (!isAlphanumeric(token)) {
                    System.out.println("index error invalid token: " + token);
                    return;
                }
            }
        } else {
            System.out.println("index error tokens cannot be empty!");
            return;
        }

        index.put(documentID, tokens);

        System.out.println("index ok " + documentID);
    }

    public Set<String> queryCommand(String query) {
        Stack<Set<String>> stack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        String state = "start";
        int countOpeningParenthesis = 0;
        int countClosingParenthesis = 0;

        String[] tokens = splitIntoTokens(query);

        for (String token : tokens) {
            switch (token) {
                case "(" -> {
                    countOpeningParenthesis += 1;
                    if (state.equals("start") || state.equals("symbol") | state.equals("openingParenthesis")) {
                        operatorStack.push('(');
                        state = "openingParenthesis";
                    } else {
                        setThereAnyQueryError(true);
                        System.out.println("query error wrong order of opening parenthesis!");
                        return new HashSet<>();
                    }
                }
                case ")" -> {
                    countClosingParenthesis += 1;

                    if (state.equals("token") || state.equals("closingParenthesis")) {
                        while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                            applyOperator(stack, operatorStack.pop());
                        }
                        try {
                            operatorStack.pop();
                        } catch (Exception e) {
                            setThereAnyQueryError(true);
                            System.out.println("query error wrong order of parenthesis");
                            return new HashSet<>();
                        }
                        state = "closingParenthesis";
                    } else {
                        setThereAnyQueryError(true);
                        System.out.println("query error wrong order of closing parenthesis!");
                        return new HashSet<>();
                    }
                }
                case "&", "|" -> {
                    if (state.equals("token") || state.equals("closingParenthesis")) {
                        while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                            applyOperator(stack, operatorStack.pop());
                        }
                        operatorStack.push(token.charAt(0));
                        state = "symbol";
                    } else {
                        setThereAnyQueryError(true);
                        System.out.println("query error wrong order of " + token);
                        return new HashSet<>();
                    }
                }
                default -> {
                    if (state.equals("start") || state.equals("symbol") || state.equals("openingParenthesis") ||
                            state.equals("closingParenthesis")) {
                        stack.push(retrieveMatchingDocuments(token));
                        state = "token";
                    } else {
                        setThereAnyQueryError(true);
                        System.out.println("query error wrong order of token!");
                        return new HashSet<>();
                    }
                }
            }
        }

        if (countOpeningParenthesis != countClosingParenthesis) {
            setThereAnyQueryError(true);
            System.out.println("query error there are no equal number of opening and closing parenthesis!");
            return new HashSet<>();
        }

        while (!operatorStack.isEmpty()) {
            applyOperator(stack, operatorStack.pop());
        }

        if (stack.isEmpty()) {
            setThereAnyQueryError(true);
            System.out.println("query error empty stack!");
            return new HashSet<>();
        }

        return stack.pop();
    }

    public String[] splitIntoTokens(String query) {
        return query.split("\\s+|(?<=\\()|(?=\\))");
    }

    private void applyOperator(Stack<Set<String>> stack, char operator) {
        try {
            Set<String> list2 = stack.pop();
            Set<String> list1 = stack.pop();

            if (operator == '&') {
                stack.push(conjunction(list1, list2));
            } else if (operator == '|') {
                stack.push(disjunction(list1, list2));
            }
        } catch (Exception e) {
            System.out.println("query error empty stack!");
        }
    }

    private Set<String> retrieveMatchingDocuments(String token) {
        Set<String> result = new HashSet<>();
        for (Map.Entry<Integer, List<String>> entry : index.entrySet()) {
            if (entry.getValue().contains(token)) {
                result.add(entry.getKey().toString());
            }
        }
        return result;
    }

    private Set<String> conjunction(Set<String> list1, Set<String> list2) {
        Set<String> result = new HashSet<>(list1);
        result.retainAll(list2);
        return result;
    }

    private Set<String> disjunction(Set<String> list1, Set<String> list2) {
        Set<String> result = new HashSet<>(list1);
        result.addAll(list2);
        return result;
    }

    public boolean isNumeric(String documentID) {
        return documentID.matches("0|([1-9]\\d*)");
    }

    public boolean isAlphanumeric(String documentID) {
        return documentID.matches("^[a-zA-Z0-9]+$");
    }

    public void retrieveSomeDocuments() {
        index.put(1, List.of("bread", "butter", "salt"));
        index.put(2, List.of("cake", "sugar", "eggs", "flour", "sugar", "cocoa", "cream", "butter"));
        index.put(3, List.of("soup", "fish", "potato", "salt", "pepper"));
    }
}