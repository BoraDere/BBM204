import java.util.*;
import java.io.*;

public class Quiz1 {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        List<String> ignoreList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.equals("...")) {
                break;
            }
            ignoreList.add(line.trim().toLowerCase());
        }

        Map<String, List<String>> keywordIndex = new TreeMap<>();

        while ((line = reader.readLine()) != null) {
            String[] words = line.trim().toLowerCase().split("\\s+");
            List<String> keywords = new ArrayList<>();

            for (String word : words) {
                if (!ignoreList.contains(word)) {
                    keywords.add(word);
                }
            }

            for (String keyword : keywords) {
                if (!keywordIndex.containsKey(keyword)) {
                    keywordIndex.put(keyword, new ArrayList<>());
                }
                keywordIndex.get(keyword).add(line);
            }
        }
        for (String keyword : keywordIndex.keySet()) {
            List<String> titles = keywordIndex.get(keyword);
            Collections.sort(titles);

            for (String title : titles) {
                String lowerTitle = title.toLowerCase();
                String[] words = lowerTitle.split("\\s+");
                StringBuilder result = new StringBuilder();
                boolean replaced = false;

                for (String word : words) {
                    if (!replaced && word.equals(keyword)) {
                        result.append(word.toUpperCase()).append(" ");
                        replaced = true;
                    }
                    else {
                        result.append(word).append(" ");
                    }
                }
                System.out.println(result.toString().trim());
            }
        }
        reader.close();
    }
}