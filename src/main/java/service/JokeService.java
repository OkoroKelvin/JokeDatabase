package service;

import model.Joke;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JokeService {

    public List<Joke> getAllSortedJokesByLikes(boolean fromLeast) throws IOException {
        List<Joke> jokes = readFromCsv();
        if (fromLeast) jokes.sort(Comparator.comparingInt(Joke::getLikes));
        else jokes.sort(Comparator.comparingInt(Joke::getLikes).reversed());
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

    public void likeJoke(int id) throws IOException {
        List<Joke> newJokes = readFromCsv().stream().peek(joke -> {
            if (joke.getId() == id) joke.like();
        }).toList();

        writeJokesToFile(newJokes);
    }

    public void addJoke(Joke joke) throws IOException {
        writeAJokeToFile(joke);
    }

    public List<Joke> getJokes() throws IOException {
        return readFromCsv();
    }

    public void removeJoke(int id) throws IOException {
        List<Joke> newJokes = readFromCsv().stream().filter(joke -> joke.getId() != id).toList();

        writeJokesToFile(newJokes);
    }
    private List<Joke> readFromCsv() throws IOException {
        File file = new File("jokes.csv");
        Joke.resetCount();
        List<Joke> jokes = new ArrayList<>();
        if(file.exists()) {
            BufferedReader csvReader = new BufferedReader(new FileReader(file));
            String row = "";
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                List<String> comments = new ArrayList<>(Arrays.asList(data[2].split("-###-")));
                Joke joke = new Joke(data[1], comments, LocalDateTime.parse(data[3]), Integer.parseInt(data[4]));
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
                out.write(joke.getId()+ "," +joke.getContent() + "," + commentsString + "," + joke.getDateCreated() + "," + joke.getLikes());
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
        ArrayList<Joke> newJokes = new ArrayList<>(jokes);

        if(newJokes.size() < 1) return null;
        writeToCsv(newJokes.get(0), false);
        newJokes.remove(newJokes.get(0));
        newJokes.forEach(joke -> {
            try {
                writeAJokeToFile(joke);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return jokes;
    }


}
