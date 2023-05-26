import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        SearchEngine searchEngine = new SearchEngine();
//        Scanner scanner = new Scanner(System.in);
//
//        displayHashMap((HashMap<Integer, List<String>>) SearchEngine.getDocuments());
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(" ", 2);
//
//            String commandName = parts[0];
//
//            if (commandName.equals("index")) {
//                String[] words = line.split(" ");
//
//                String documentID = words[1];
//                List<String> tokens = Arrays.asList(Arrays.copyOfRange(words, 2, words.length));
//
//                searchEngine.index(documentID, tokens);
//                displayHashMap((HashMap<Integer, List<String>>) SearchEngine.getDocuments());
//
//            } else if (commandName.equals("query")) {
//
//                List<Integer> results = (searchEngine.executeQuery(parts[1]));
//
//                for (Integer value : results) {
//                    System.out.print(value + " ");
//                }
//            } else return;
//
//        }
//        scanner.close();

        SearchEngine searchEngine = new SearchEngine();
        searchEngine.indexDocument("2", "cake sugar eggs flour sugar cocoa cream butter");
        searchEngine.indexDocument("1", "bread butter salt");
        searchEngine.indexDocument("3", "soup fish potato salt pepper");
        // Example queries
        System.out.println("Query: butter");
        System.out.println("results: " + searchEngine.executeQuery("butter"));
        System.out.println();

        System.out.println("Query: sugar");
        System.out.println("Results: " + searchEngine.executeQuery("sugar"));
        System.out.println();

        System.out.println("Query: soup");
        System.out.println("Results: " + searchEngine.executeQuery("soup"));
        System.out.println();

        System.out.println("Query: (butter | potato) & salt");
        System.out.println("Results: " + searchEngine.executeQuery("( butter | potato ) & salt"));

        System.out.println("Query: bread | ( fish | flour )");
        System.out.println("Results: " + searchEngine.executeQuery("bread | ( fish | flour )"));

        System.out.println("Query: bread & ( fish & flour )");
        System.out.println("Results: " + searchEngine.executeQuery("bread & ( fish & flour )"));

        System.out.println("Query: salt & ( fish | flour )");
        System.out.println("Results: " + searchEngine.executeQuery("salt & ( fish | flour )"));
    }


    private static void displayHashMap(HashMap<Integer, List<String>> hashMap) {
        for (Map.Entry<Integer, List<String>> entry : hashMap.entrySet()) {
            Integer key = entry.getKey();
            List<String> value = entry.getValue();

            System.out.print("Key: " + key + ", Values: ");
            for (String str : value) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
    }
}