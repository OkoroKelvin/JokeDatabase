package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Joke {

    private static int count;
    private int id;
    private String content;
    private List<String> comments;
    private final LocalDateTime dateCreated;
    private int likes;

    public Joke(String content) {
        this.id = count++;
        this.content = content;
        this.comments = new ArrayList<>();
        this.dateCreated = LocalDateTime.now();
        this.likes = 0;
    }

    public int getId() {
        return id;
    }

    public static void resetCount(){
        count = 0;
    }

    public Joke(String content, List<String> comments, LocalDateTime dateCreated, int likes) {
        this.content = content;
        this.comments = comments;
        this.dateCreated = dateCreated;
        this.likes = likes;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public int getLikes() {
        return likes;
    }

    public void like() {
        this.likes += 1;
    }
}
