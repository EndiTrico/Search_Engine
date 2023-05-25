import java.util.*;

public class Main {
    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();
        Scanner scanner = new Scanner(System.in);

        displayHashMap((HashMap<Integer, List<String>>) SearchEngine.getDocuments());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ", 2);

            String commandName = parts[0];

            if (commandName.equals("index")) {
                String[] words = line.split(" ");

                String documentID = words[1];
                List<String> tokens = Arrays.asList(Arrays.copyOfRange(words, 2, words.length));

                searchEngine.index(documentID, tokens);
                displayHashMap((HashMap<Integer, List<String>>) SearchEngine.getDocuments());

            } else if (commandName.equals("query")) {

                List<Integer> results = (searchEngine.query(parts[1]));

                for (Integer value : results) {
                    System.out.print(value + " ");
                }
            } else return;

        }

        scanner.close();
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