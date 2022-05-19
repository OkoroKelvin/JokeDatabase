import model.Joke;
import service.JokeService;

import java.io.IOException;
import java.util.Scanner;

public class JokeApp {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        JokeService service = new JokeService();
        while (true) {
            String Menu = """
                    Press 1:	to create Joke  
                    Press 2: to see available jokes
                    press 3: to like a joke 
                    Press 4: to add comment to joke
                    Press 5: to remove comment from joke
                    Press 6: to view Joke by date
                    Press 7: to view Jokes by likes(most to least)
                    Press 8: to view Jokes by likes(least to most)
                    press -1: to exit  
                    """;
            System.out.println(Menu + "\n");
            int num1 = input.nextInt();
            input.nextLine();

            switch (num1) {
                case 1 -> {
                    System.out.println("Enter Content");
                    String content = input.nextLine();
                    Joke joke = new Joke(content);
                    service.addJoke(joke);
                    System.out.println("Joke Created and added to file");
                }

                case 2 -> {
                    service.getJokes().forEach(System.out::println);
                }
                case 3 -> {
                    System.out.println("Enter joke id");
                    int id = input.nextInt();

                    service.likeJoke(id);
                    System.out.println("Joke Liked");
                }

                case 4 -> {
                    System.out.println("Enter joke id for comment");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.println("Enter comment");
                    String comment = input.nextLine();

                    service.addCommentToJoke(id,comment);

                    System.out.println("Comment added");
                }

                case 5 -> {
                    System.out.println("Enter joke id to remove its comment");
                    int id = input.nextInt();

                    service.removeCommentsFromJoke(id);

                    System.out.println("Comment removed");
                }

                case 6 -> {
                    service.getAllSortedJokesByDate().forEach(System.out::println);
                }

                case 7 -> {
                    service.getAllSortedJokesByLikes(true).forEach(System.out::println);
                }

                case 8 -> {
                    service.getAllSortedJokesByLikes(false).forEach(System.out::println);
                }

                case -1 -> System.exit(0);
            }
        }
    }
}
