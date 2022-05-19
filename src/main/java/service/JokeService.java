package service;

import model.Joke;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JokeService {

    public List<Joke> getAllSortedJokesByLikes() throws IOException {
        List<Joke> jokes = readFromCsv();
        jokes.sort(Comparator.comparingInt(Joke::getLikes));
        return jokes;
    }

    public List<Joke> getAllSortedJokesByDate() throws IOException {
        List<Joke> jokes = readFromCsv();
        jokes.sort(Comparator.comparing(Joke::getDateCreated));
        return jokes;
    }

    public void addCommentToJoke(int id, String comment) throws IOException {
        List<Joke> newJokes = readFromCsv().stream().peek(joke -> {
            if (joke.getId() == id) joke.addComment(comment);
        }).toList();

        writeJokesToFile(newJokes);

    }

    public void removeCommentsFromJoke(int id) throws IOException {
        List<Joke> newJokes = readFromCsv().stream().peek(joke -> {
            if (joke.getId() == id) joke.setComments(new ArrayList<String>());
        }).toList();

        writeJokesToFile(newJokes);
    }

    public void addJoke(Joke joke) throws IOException {
        writeAJokeToFile(joke);
    }

    public void removeJoke(int id) throws IOException {
        List<Joke> newJokes = readFromCsv().stream().filter(joke -> joke.getId() != id).toList();

        writeJokesToFile(newJokes);
    }
    private List<Joke> readFromCsv() throws IOException {
        File file = new File("jokes.csv");
        List<Joke> jokes = new ArrayList<>();
        if(file.exists()) {
            BufferedReader csvReader = new BufferedReader(new FileReader(file));
            String row = "";
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Joke joke;
                    List<String> comments = new ArrayList<>(Arrays.asList(data[2].split("-###-")));
                    joke = new Joke(data[0], comments, LocalDateTime.parse(data[2]), Integer.parseInt(data[3]));
                jokes.add(joke);
            }
            csvReader.close();
        }

        return jokes;
    }


    private Joke writeToCsv(Joke joke, boolean append) throws IOException {
        File file = new File("jokes.csv");
        if(!append) Joke.resetCount();
        if(file.exists() || file.createNewFile()){
            Scanner sc = new Scanner(System.in);

            try(BufferedWriter out = new BufferedWriter(new FileWriter(file, append))) {
                String commentsString = joke.getComments().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining("-###-"));
                out.write(joke.getContent() + "," + commentsString + "," + joke.getDateCreated() + "," + joke.getLikes());
                out.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return joke;

    }

    private Joke writeAJokeToFile(Joke joke) throws IOException {
        return writeToCsv(joke, true);
    }

    private List<Joke> writeJokesToFile(List<Joke> jokes) throws IOException {
        writeToCsv(jokes.get(0), false);
        jokes.remove(0);
        jokes.forEach(joke -> {
            try {
                writeAJokeToFile(joke);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return jokes;
    }


}
