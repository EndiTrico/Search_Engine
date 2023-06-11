import java.util.*;

public class Main {
    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ", 2);

            String commandName = parts[0];

            switch (commandName) {
                case "index" -> {
                    if (parts.length == 1)
                        System.out.println("index error no data");
                    else {
                        String[] words = line.split(" ");
                        String documentID = words[1];
                        List<String> tokens = Arrays.asList(Arrays.copyOfRange(words, 2, words.length));
                        searchEngine.indexCommand(documentID, tokens);
                    }
                }
                case "query" -> {
                    if (parts.length == 1)
                        System.out.println("query error no data");
                    else {
                        Set<String> results = searchEngine.queryCommand(parts[1]);

                        if (!results.isEmpty() && !searchEngine.isThereAnyQueryError()) {
                            System.out.print("query results ");
                            for (String value : results)
                                System.out.print(value + " ");
                            System.out.println();
                        } else if (results.isEmpty() && !searchEngine.isThereAnyQueryError())
                            System.out.println("query error empty results!");

                        searchEngine.setThereAnyQueryError(false);
                    }
                }
                case "" -> {
                    System.out.println("End of the Program!");
                    return;
                }
                case "show" -> {
                    Main.displayHashMap((HashMap<Integer, List<String>>) searchEngine.getIndex());
                }
                default -> {
                    System.out.println("Error Command Name");
                    return;
                }
            }
        }
        scanner.close();
    }

    private static void displayHashMap(HashMap<Integer, List<String>> hashMap) {
        for (Map.Entry<Integer, List<String>> entry : hashMap.entrySet()) {
            Integer key = entry.getKey();
            List<String> value = entry.getValue();

            System.out.print("Key: " + key + ", Values: ");
            for (String str : value)
                System.out.print(str + " ");

            System.out.println();
        }
    }
}