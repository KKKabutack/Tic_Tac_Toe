import java.io.*;

/**
 * This is a phony Java class for a easier way to initiate two NewPlayer instance
 * @auther Wang Huidong
 * @version 1.0.0
 * @since 03/05/2023
 */
public class Player2 {
    /**
     * Create an instance of NewGame
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        NewPlayer myGame = new NewPlayer();
        myGame.go();
    }
}

