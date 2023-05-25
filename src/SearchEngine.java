import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SearchEngine {
    private static Map<Integer, List<String>> documents;
    private Pattern alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9]+$");


    public static Map<Integer, List<String>> getDocuments() {
        return documents;
    }

    public SearchEngine() {
        this.documents = new HashMap<>();
        this.retrieveSomeDocuments();
    }

    public void index(String documentID, List<String> tokens) {
        if (!isNumeric(documentID)) {
            System.out.println(" index error The document ID does NOT contain only numbers");
            return;
        }

        if (!tokens.isEmpty()) {
            for (String token : tokens) {
                if (!isAlphanumeric(token)) {
                    System.out.println("index error Invalid token: " + token);
                    return;
                }
            }
        } else {
            System.out.println("index error Tokens cannot be empty");
            return;
        }

        int docID = Integer.parseInt(documentID);

        documents.put(docID, tokens);

        System.out.println("index ok " + docID);
    }

    public static List<Integer> findMaxOccurrences(List<Integer> numbers) {
        int maxCount = 0;
        List<Integer> maxOccurrences = new ArrayList<>();

        for (int i = 0; i < numbers.size(); i++) {
            int current = numbers.get(i);
            int count = 0;

            if (!maxOccurrences.contains(current)) {
                for (int j = i; j < numbers.size(); j++) {
                    if (numbers.get(j) == current) {
                        count++;
                    }
                }

                if (count > maxCount) {
                    maxCount = count;
                    maxOccurrences.clear();
                    maxOccurrences.add(current);
                } else if (count == maxCount) {
                    maxOccurrences.add(current);
                }
            }
        }

        return maxOccurrences;
    }

    public List<Integer> query(String query) {
        List<Integer> results = new ArrayList<>();

        // Remove any whitespace from the query string
        query = query.replaceAll("\\s+", "");

        // Call the recursive helper function to evaluate the query
        evaluateExpression(query, 0, query.length() - 1, results);

        return findMaxOccurrences(results);
    }

    private static void evaluateExpression(String query, int start, int end, List<Integer> results) {
        boolean isAndOperator = false;
        boolean isOrOperator = false;
        int index = start;
        while (index <= end) {
            char c = query.charAt(index);
            if (c == '&') {
                isAndOperator = true;
            } else if (c == '|') {
                isOrOperator = true;
            } else if (c == '(') {
                // Find the matching closing bracket
                int closeBracketIndex = findMatchingClosingBracket(query, index, end);
                if (closeBracketIndex != -1) {
                    // Recursively evaluate the subexpression within the parentheses
                    if (isOrOperator) {
                        List<Integer> tempResults = new ArrayList<>();
                        evaluateExpression(query, index + 1, closeBracketIndex - 1, tempResults);
                        results.addAll(tempResults);
                    } else if (isAndOperator) {
                        List<Integer> tempResults = new ArrayList<>(results);
                        results.clear();
                        evaluateExpression(query, index + 1, closeBracketIndex - 1, results);
                        results.retainAll(tempResults);
                    } else {
                        evaluateExpression(query, index + 1, closeBracketIndex - 1, results);
                    }

                    // Move the index to the character after the closing bracket
                    index = closeBracketIndex;
                }
            } else {
                // Find the next operator or the end of the expression
                int nextOperatorIndex = findNextOperator(query, index, end);
                if (nextOperatorIndex == -1) {
                    nextOperatorIndex = end + 1;
                }

                // Get the token substring
                String token = query.substring(index, nextOperatorIndex);

                // Check if the token exists in any document's list of tokens
                for (HashMap.Entry<Integer, List<String>> entry : documents.entrySet()) {
                    List<String> tokens = entry.getValue();
                    if (tokens.contains(token)) {
                        results.add(entry.getKey());
                    }
                }

                if (isOrOperator && !results.isEmpty()) {
                    // Token found with OR operator, exit the loop
                    return;
                } else if (isAndOperator && results.isEmpty()) {
                    // Token not found with AND operator, exit the loop
                    return;
                }

                // Reset the operator flags
                isAndOperator = false;
                isOrOperator = false;

                // Move the index to the next operator or end of expression
                index = nextOperatorIndex;
            }

            index++;
        }
    }

    private static int findMatchingClosingBracket(String query, int openBracketIndex, int end) {
        int nestedLevel = 1;
        int index = openBracketIndex + 1;
        while (index <= end) {
            char c = query.charAt(index);
            if (c == '(') {
                nestedLevel++;
            } else if (c == ')') {
                nestedLevel--;
                if (nestedLevel == 0) {
                    return index;
                }
            }
            index++;
        }
        return -1; // No matching closing bracket found
    }

    private static int findNextOperator(String query, int startIndex, int end) {
        for (int i = startIndex; i <= end; i++) {
            char c = query.charAt(i);
            if (c == '&' || c == '|') {
                return i;
            }
        }
        return -1; // No operator found
    }

    public boolean isNumeric(String documentID) {
        return documentID.matches("0|([1-9]\\d*)");
    }

    public boolean isAlphanumeric(String documentID) {
        return documentID.matches("^[a-zA-Z0-9]+$");
    }

    public void retrieveSomeDocuments() {

//        documents.put(1, List.of("pasta", "soup", "pizza", "spaghetti", "risotto", "fish"));
//        documents.put(2, List.of("dog", "cat", "bird", "shark", "dolphin", "fish", "eagle", "elephant", "crocodile", "giraffe"));
//        documents.put(3, List.of("red", "blue", "yellow", "orange", "green", "white", "black", "purple", "pink"));
//        documents.put(4, List.of("xiaomi", "nokia", "samsung", "apple", "huawei"));
//        documents.put(5, List.of("computer", "mouse", "monitor", "keyboard", "printer", "motherboard", "cpu", "usb", "disk"));
//        documents.put(6, List.of("rose", "lily", "iris", "tulip", "lotus", "daisy", "sunflower", "orchid"));

        documents.put(1, List.of("bread", "butter", "salt"));
        documents.put(2, List.of("cake", "sugar", "eggs", "flour", "sugar", "cocoa", "cream", "butter"));
        documents.put(3, List.of("soup", "fish", "potato", "salt", "pepper"));
    }

}
