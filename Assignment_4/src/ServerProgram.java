import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.net.*;
import java.util.Objects;

/**
 * This is the server program
 * @auther Wang Huidong
 * @version 1.0.0
 * @since 03/05/2023
 */
public class ServerProgram {
    private ArrayList<String> boardStatus;
    ServerSocket serverSock;
    int currentPlayer = 1;
    ArrayList<ObjectOutputStream> outputStreams = new ArrayList<ObjectOutputStream>(); // it helps broadcast

    /**
     * Create instance of this class and starts go function.
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ServerProgram serverProgram = new ServerProgram();
        serverProgram.go();
    }

    /**
     * Start server program
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public void go() throws IOException, ClassNotFoundException, InterruptedException {
        // Initiate boardStatus
        boardStatus = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            boardStatus.add("");
        }
        try{
            serverSock = new ServerSocket(6666);
            System.out.println("The server is now working...");
            while (true){
                Socket socket = serverSock.accept();
                System.out.println("Server is connected to a client");

                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    } // end of go

    /**
     * Checking if a player wins
     * @return 1 when player1 wins, 2 when player2 wins, 0 when nobody wins, 4 when game is draw
     */
    public int check() {
        // rows
        int start = 0;
        while (start <= 6) {
            if (boardStatus.get(start).equals(boardStatus.get(start + 1)) && boardStatus.get(start + 1).equals(boardStatus.get(start + 2))) {
                if (boardStatus.get(start).equals("O")) {
                    System.out.println("return 1");
                    return 2;
                } else {
                    if (boardStatus.get(start).equals("X")) {
                        System.out.println("return 2");
                        return 1;
                    }
                }
            }
            start += 3;
        }
        // columns
        start = 0;
        while (start <= 2) {
            if (boardStatus.get(start).equals(boardStatus.get(start + 3)) && boardStatus.get(start + 3).equals(boardStatus.get(start + 6))) {
                if (boardStatus.get(start).equals("O")) {
                    System.out.println("return 3");
                    return 2;
                } else {
                    if (boardStatus.get(start).equals("X")) {
                        System.out.println("return 4");
                        return 1;
                    }
                }
            }
            start += 1;
        }
        // oblique
        if (boardStatus.get(0).equals(boardStatus.get(4)) && boardStatus.get(4).equals(boardStatus.get(8))) {
            if (boardStatus.get(0).equals("O")) {
                return 2;
            } else {
                if (boardStatus.get(0).equals("X")) {
                    return 1;
                }
            }
        }
        if (boardStatus.get(2).equals(boardStatus.get(4)) && boardStatus.get(4).equals(boardStatus.get(6))) {
            if (boardStatus.get(2).equals("O")) {
                System.out.println("return 5");
                return 2;
            } else {
                if (boardStatus.get(2).equals("X")) {
                    System.out.println("return 6");
                    return 1;
                }
            }
        }
        // if draw
        for (int i = 0; i < 9; i++){
            if (boardStatus.get(i).equals("")){
                return 0;
            }
        }
        return 4;
    }

    /**
     * This class for thread dealing with request from client
     */
    class ClientHandler implements Runnable {
        private Socket sock;
        public ClientHandler(Socket sock){
            this.sock = sock;
        }

        /**
         * Dealing with player's moves
         * Inform user when one left when catch exception.
         */
        public void run(){
            // deal with new player
            try {
                ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
                outputStreams.add(out);
                ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                TransmitInfo info; // receive command
                while ((info = (TransmitInfo) in.readObject()) != null) {
                    if (!Objects.equals(boardStatus.get(info.spot), "")){
                        if (currentPlayer == 1){
                            TransmitInfo unlock = new TransmitInfo();
                            unlock.unlockButtons = true;
                            outputStreams.get(0).writeObject(unlock);
                            System.out.println("Wrong Move 1");
                        }else{
                            TransmitInfo unlock = new TransmitInfo();
                            unlock.unlockButtons = true;
                            outputStreams.get(1).writeObject(unlock);
                            System.out.println("Wrong Move 2");
                        }
                        continue;
                    }
                    if (info.newPlayer) {
                        System.out.println("New Game Request Received");
                        if (outputStreams.size() == 2) {
                            TransmitInfo youR2 = new TransmitInfo();
                            youR2.youAreTwo = true;
                            TransmitInfo youR1 = new TransmitInfo();
                            youR1.unlockButtons = true;
                            outputStreams.get(1).writeObject(youR2);
                            outputStreams.get(0).writeObject(youR1);
                        }
                    } else { // if already 2 player and the game started
                        if (currentPlayer == 1) {
                            System.out.println("Received a move from Player: " + Integer.toString(1));
                            boardStatus.set(info.spot, "X");
                            TransmitInfo sendBack = new TransmitInfo();
                            sendBack.oneWins = check();
                            sendBack.playerNumber = 1;
                            sendBack.spot = info.spot;
                            for (ObjectOutputStream o : outputStreams) {
                                o.writeObject(sendBack);
                            }
                            TransmitInfo unlock = new TransmitInfo();
                            unlock.unlockButtons = true;
                            outputStreams.get(1).writeObject(unlock);
                            switchPlayer();
                        }else{
                            System.out.println("Received a move from Player: " + Integer.toString(2));
                            boardStatus.set(info.spot, "O");
                            TransmitInfo sendBack = new TransmitInfo();
                            sendBack.oneWins = check();
                            sendBack.playerNumber = 2;
                            sendBack.spot = info.spot;
                            for (ObjectOutputStream o : outputStreams) {
                                o.writeObject(sendBack);
                                System.out.println("Info send back to all clients");
                            }
                            TransmitInfo unlock = new TransmitInfo();
                            unlock.unlockButtons = true;
                            outputStreams.get(0).writeObject(unlock);
                            switchPlayer();
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                // error occurs when user left
                for (ObjectOutputStream o : outputStreams) {
                    TransmitInfo left = new TransmitInfo();
                    left.left = true;
                    try {
                        o.writeObject(left);
                    } catch (IOException ignored) {}
                    System.out.println("One player left");
                }
                System.exit(0);
                throw new RuntimeException();
            }
        }
    }

    /**
     * switch players
     */
    private void switchPlayer(){
        if (currentPlayer == 1){
            currentPlayer = 2;
        }else{
            currentPlayer = 1;
        }
    }

}