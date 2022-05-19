import model.Joke;
import service.JokeService;

import java.io.IOException;
import java.util.Scanner;

public class JokeApp {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        JokeService service = new JokeService();

        String Menu = """
                Press 1	to create Joke         
                """;
        System.out.println(Menu+"\n");
        int num1 = input.nextInt();

        if(num1==1){
            System.out.println("Enter Content");
            String content = input.nextLine();
            Joke joke = new Joke(content);
            service.addJoke(joke);
            System.out.println("Joke Created and added to file");
        }



    }
}
