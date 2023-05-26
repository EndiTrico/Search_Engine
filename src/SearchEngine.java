import java.util.*;

public class SearchEngine {
//    private static Map<Integer, List<String>> documents;
//    private Pattern alphaNumericPattern = Pattern.compile("^[a-zA-Z0-9]+$");
//
//
//    public static Map<Integer, List<String>> getDocuments() {
//        return documents;
//    }
//
//    public SearchEngine() {
//        this.documents = new HashMap<>();
//        this.retrieveSomeDocuments();
//    }
//
//    public void index(String documentID, List<String> tokens) {
//        if (!isNumeric(documentID)) {
//            System.out.println(" index error The document ID does NOT contain only numbers");
//            return;
//        }
//
//        if (!tokens.isEmpty()) {
//            for (String token : tokens) {
//                if (!isAlphanumeric(token)) {
//                    System.out.println("index error Invalid token: " + token);
//                    return;
//                }
//            }
//        } else {
//            System.out.println("index error Tokens cannot be empty");
//            return;
//        }
//
//        int docID = Integer.parseInt(documentID);
//
//        documents.put(docID, tokens);
//
//        System.out.println("index ok " + docID);
//    }
//
//    public List<Integer> executeQuery(String query) {
//        Stack<List<Integer>> stack = new Stack<>();
//        List<Integer> result = new ArrayList<>();
//
//        String[] tokens = query.toLowerCase().split(" ");
//        for (String token : tokens) {
//            if (token.equals("(")) {
//                stack.push(result);
//                result = new ArrayList<>();
//            } else if (token.equals(")")) {
//                if (!stack.isEmpty()) {
//                    List<Integer> prevResult = stack.pop();
//                    prevResult.retainAll(result); // Perform list intersection
//                    result = prevResult;
//                } else {
//                    // Error: Unbalanced parentheses
//                    return null;
//                }
//            } else if (token.equals("&")) {
//                stack.push(result);
//                result = new ArrayList<>();
//            } else if (token.equals("|")) {
//                if (!stack.isEmpty()) {
//                    List<Integer> prevResult = stack.pop();
//                    prevResult.addAll(result); // Perform list union
//                    result = prevResult;
//                } else {
//                    // Error: Invalid query
//                    return null;
//                }
//            } else {
//                Set<Integer> docIds = getDocumentIdsForToken(token);
//                if (docIds != null) {
//                    if (result.isEmpty()) {
//                        result.addAll(docIds);
//                    } else {
//                        result.retainAll(docIds); // Perform list intersection
//                    }
//                } else {
//                    // Token not found in the index
//                    return null;
//                }
//            }
//        }
//
//        if (!stack.isEmpty()) {
//            // Error: Unbalanced parentheses
//            return null;
//        }
//
//        return result;
//    }
//
//    private Set<Integer> getDocumentIdsForToken(String token) {
//        Set<Integer> docIds = new HashSet<>();
//        for (Map.Entry<Integer, List<String>> entry : documents.entrySet()) {
//            if (entry.getValue().contains(token)) {
//                docIds.add(entry.getKey());
//            }
//        }
//        return docIds;
//    }
//
//    public boolean isNumeric(String documentID) {
//        return documentID.matches("0|([1-9]\\d*)");
//    }
//
//    public boolean isAlphanumeric(String documentID) {
//        return documentID.matches("^[a-zA-Z0-9]+$");
//    }
//
//    public void retrieveSomeDocuments() {
//
////        documents.put(1, List.of("pasta", "soup", "pizza", "spaghetti", "risotto", "fish"));
////        documents.put(2, List.of("dog", "cat", "bird", "shark", "dolphin", "fish", "eagle", "elephant", "crocodile", "giraffe"));
////        documents.put(3, List.of("red", "blue", "yellow", "orange", "green", "white", "black", "purple", "pink"));
////        documents.put(4, List.of("xiaomi", "nokia", "samsung", "apple", "huawei"));
////        documents.put(5, List.of("computer", "mouse", "monitor", "keyboard", "printer", "motherboard", "cpu", "usb", "disk"));
////        documents.put(6, List.of("rose", "lily", "iris", "tulip", "lotus", "daisy", "sunflower", "orchid"));
//
//        documents.put(1, List.of("bread", "butter", "salt"));
//        documents.put(2, List.of("cake", "sugar", "eggs", "flour", "sugar", "cocoa", "cream", "butter"));
//        documents.put(3, List.of("soup", "fish", "potato", "salt", "pepper"));
//    }

    private Map<String, Set<Integer>> index;

    public SearchEngine() {
        index = new HashMap<>();
    }

    public void indexDocument(String docId, String document) {
        String[] tokens = document.toLowerCase().split(" ");

        if (!isNumeric(docId)) {
            System.out.println("index error The document ID does NOT contain only numbers");
            return;
        }

        int documentID = Integer.parseInt(docId);

        if (tokens.length != 0) {
            for (String token : tokens) {
                if (!isAlphanumeric(token)) {
                    System.out.println("index error Invalid token: " + token);
                    return;
                } else
                    index.computeIfAbsent(token, k -> new HashSet<>()).add(documentID);
            }
        } else {
            System.out.println("index error Tokens cannot be empty");
            return;
        }

        System.out.println("index ok " + documentID);

    }

    public Set<Integer> executeQuery(String query) {
        Stack<Set<Integer>> stack = new Stack<>();
        Stack<Character> opStack = new Stack<>();

        String[] tokens = query.toLowerCase().split(" ");
        for (String token : tokens) {
            if (token.equals("(")) {
                opStack.push('(');
            } else if (token.equals(")")) {
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    applyOperator(stack, opStack.pop());
                }
                opStack.pop(); // Pop the '('
            } else if (token.equals("&") || token.equals("|")) {
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    applyOperator(stack, opStack.pop());
                }
                opStack.push(token.charAt(0));
            } else {
                stack.push(getMatchingDocuments(token));
            }
        }

        while (!opStack.isEmpty()) {
            applyOperator(stack, opStack.pop());
        }

        return stack.isEmpty() ? new HashSet<>() : stack.pop();
    }

    private void applyOperator(Stack<Set<Integer>> stack, char operator) {
        Set<Integer> set2 = stack.pop();
        Set<Integer> set1 = stack.pop();

        if (operator == '&') {
            stack.push(intersect(set1, set2));
        } else if (operator == '|') {
            stack.push(union(set1, set2));
        }
    }

    private Set<Integer> getMatchingDocuments(String token) {
        return index.getOrDefault(token, new HashSet<>());
    }

    private Set<Integer> intersect(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    private Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    public boolean isNumeric(String documentID) {
        return documentID.matches("0|([1-9]\\d*)");
    }

    public boolean isAlphanumeric(String documentID) {
        return documentID.matches("^[a-zA-Z0-9]+$");
    }
}
