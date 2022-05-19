package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Joke {

    private static int count;
    private int id;
    private String content;
    private List<String> comments;
    private final LocalDateTime dateCreated;
    private int likes;

    public Joke(String content) {
        this(content,new ArrayList<>(), LocalDateTime.now(),0 );
    }

    public Joke(String content, List<String> comments, LocalDateTime dateCreated, int likes) {
        this.id = ++count;
        this.content = content;
        this.comments = comments;
        this.dateCreated = dateCreated;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public static void resetCount(){
        count = 0;
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

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                ", dateCreated=" + dateCreated +
                ", likes=" + likes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joke joke = (Joke) o;
        return id == joke.id && Objects.equals(dateCreated, joke.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated);
    }
}
