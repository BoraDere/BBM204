import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class TrieNode {
    Map<Character, TrieNode> children = new TreeMap<>();
    boolean isEndOfWord = false;
    PriorityQueue<Result> results = new PriorityQueue<>(Comparator.comparingLong(Result::getWeight).reversed());
}

class Result {
    long weight;
    String word;

    Result(long weight, String word) {
        this.weight = weight;
        this.word = word;
    }

    long getWeight() {
        return weight;
    }

    String getWord() {
        return word;
    }
}

class Trie {
    TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    void insert(String word, long weight) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.results.add(new Result(weight, word));
        }
        node.isEndOfWord = true;
    }

    List<Result> search(String prefix, int limit) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return Collections.emptyList();
        }
        return node.results.stream().limit(limit).collect(Collectors.toList());
    }
}

public class Quiz4 {
    public static void main(String[] args) throws IOException {
        Trie trie = new Trie();
        BufferedReader databaseReader = new BufferedReader(new FileReader(args[0]));
        int n = Integer.parseInt(databaseReader.readLine());

        for (int i = 0; i < n; i++) {
            String[] line = databaseReader.readLine().split("\t");
            trie.insert(line[1].toLowerCase(), Long.parseLong(line[0]));
        }

        databaseReader.close();

        BufferedReader queryReader = new BufferedReader(new FileReader(args[1]));
        String query;

        while ((query = queryReader.readLine()) != null) {
            String[] line = query.split("\t");
            String prefix = line[0].toLowerCase();
            int limit = Integer.parseInt(line[1]);
            System.out.println("Query received: \"" + prefix + "\" with limit " + limit + ". Showing results:");
            List<Result> results = trie.search(prefix, limit);

            if (results.isEmpty()) {
                System.out.println("No results.");
            } 
            else {
                for (Result result : results) {
                    System.out.println("- " + result.getWeight() + " " + result.getWord());
                }
            }
        }

        queryReader.close();
    }
}