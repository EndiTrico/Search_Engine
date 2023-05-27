import java.util.*;

public class Main {
    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ", 2);

            String commandName = parts[0];

            if (commandName.equals("index")) {
                String[] words = line.split(" ");

                String documentID = words[1];
                List<String> tokens = Arrays.asList(Arrays.copyOfRange(words, 2, words.length));

                searchEngine.indexCommand(documentID, tokens);

            } else if (commandName.equals("query")) {

                List<String> results = searchEngine.queryCommand(parts[1]);

                if (results.isEmpty()) {
                    System.out.println("query error not found in any document");
                } else {
                    System.out.print("query results ");
                    for (String value : results) {
                        System.out.print(value + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Error Command Name");
                return;
            }
        }
        scanner.close();

/*
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.indexCommand("2", "cake sugar eggs flour sugar cocoa cream butter");
        searchEngine.indexCommand("1", "bread butter salt");
        searchEngine.indexCommand("3", "soup fish potato salt pepper");
        searchEngine.indexCommand("1", "DGGG");

        System.out.println(searchEngine.getIndex());
        // Example queries
        System.out.println("Query: butter");
        System.out.println("results: " + searchEngine.queryCommand("butter"));
        System.out.println();

        System.out.println("Query: sugar");
        System.out.println("Results: " + searchEngine.queryCommand("sugar"));
        System.out.println();

        System.out.println("Query: soup");
        System.out.println("Results: " + searchEngine.queryCommand("soup"));
        System.out.println();

        System.out.println("Query: (butter | potato) & salt");
        System.out.println("Results: " + searchEngine.queryCommand("( butter | potato ) & salt"));

        System.out.println("Query: bread | ( fish | flour )");
        System.out.println("Results: " + searchEngine.queryCommand("bread | ( fish | flour )"));

        System.out.println("Query: bread & ( fish & flour )");
        System.out.println("Results: " + searchEngine.queryCommand("bread & ( fish & flour )"));

        System.out.println("Query: salt & ( fish | flour )");
        System.out.println("Results: " + searchEngine.queryCommand("salt & ( fish | flour )"));
  */
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