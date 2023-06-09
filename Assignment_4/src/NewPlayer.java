import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class contains network elements and actionListener for buttons
 * @auther Wang Huidong
 * @version 1.0.0
 * @since 03/05/2023
 */
public class NewPlayer {
    Socket sock;
    ObjectOutputStream out;
    GameView view;
    boolean youR2 = false;
    /**
     * go function started the client program
     */
    public void go() throws IOException, ClassNotFoundException {
        view = new GameView();
        view.go();
        view.submit.addActionListener(new submitListener());
        view.b.get(0).addActionListener(new buttonListener0());
        view.b.get(1).addActionListener(new buttonListener1());
        view.b.get(2).addActionListener(new buttonListener2());
        view.b.get(3).addActionListener(new buttonListener3());
        view.b.get(4).addActionListener(new buttonListener4());
        view.b.get(5).addActionListener(new buttonListener5());
        view.b.get(6).addActionListener(new buttonListener6());
        view.b.get(7).addActionListener(new buttonListener7());
        view.b.get(8).addActionListener(new buttonListener8());

        sock = new Socket("127.0.0.1", 6666);
        out = new ObjectOutputStream(sock.getOutputStream());

        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        TransmitInfo info;
        while ((info = (TransmitInfo) in.readObject()) != null) {
            transInfoHandler(info);
        }

    }
    /**
     * This class creates a thread for submit request.
     */
    class submitListener implements ActionListener{
        /**
         * This function demonstrate what happens after clicking submit button
         * @Override
        */
        public void actionPerformed(ActionEvent e) {
            // set UI
            String name = view.textField.getText();
            view.frame.setTitle("Tic-Tac-Toe-Player: " + name);
            view.upperPanelLabel.setText("WELCOME " + name);
            TransmitInfo newGameInfo = new TransmitInfo();
            newGameInfo.newPlayer = true;
            // inform server
            try {
                out.writeObject(newGameInfo);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            view.submit.setEnabled(false);
        }
    }

    /**
     * Below are actionListeners for 9 buttons. They can send information to server.
     */
    class buttonListener0 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 0;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 0 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    class buttonListener1 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 1;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 1 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class buttonListener2 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 2;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 2 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class buttonListener3 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 3;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 3 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class buttonListener4 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 4;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 4 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class buttonListener5 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 5;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 5 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class buttonListener6 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 6;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 6 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class buttonListener7 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 7;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 7 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    class buttonListener8 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisableButtons();
            TransmitInfo playInfo = new TransmitInfo();
            playInfo.spot = 8;
            playInfo.newPlayer = false;
            try {
                out.writeObject(playInfo);
                System.out.println("Button 8 is clicked");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * The function handles game information object from server
     * @param i, it is a serializable object containing game status.
     */
    private void transInfoHandler(TransmitInfo i){
        if (i.youAreTwo) {
            System.out.println("You are the second player");
            view.setDisableButtons();
            youR2 = true;
            return;
        }
        if (i.left){
            view.someOneLeft();
        }
        if (i.oneWins == 0){
            if (i.playerNumber == 1){
                view.b.get(i.spot).setText("X");
                view.b.get(i.spot).setFont(new Font("Arial", Font.BOLD, 25));
                view.b.get(i.spot).setForeground(Color.GREEN);
                if (youR2){
                    view.upperPanelLabel.setText("Your opponent has moved, now is your turn.");
                }else{
                    view.upperPanelLabel.setText("Valid move, please wait for your opponent");
                }
            }
            if (i.playerNumber == 2){
                view.b.get(i.spot).setText("O");
                view.b.get(i.spot).setFont(new Font("Arial", Font.BOLD, 25));
                view.b.get(i.spot).setForeground(Color.RED);
                if (youR2){
                    view.upperPanelLabel.setText("Valid move, please wait for your opponent");
                }else{
                    view.upperPanelLabel.setText("Your opponent has moved, now is your turn.");
                }
            }
        }else{ // player 1 wins
            if (i.oneWins == 1){
                view.b.get(i.spot).setText("X");
                view.b.get(i.spot).setFont(new Font("Arial", Font.BOLD, 25));
                view.b.get(i.spot).setForeground(Color.GREEN);
                if (youR2){
                    System.out.println("you lose");
                    view.showLoseWindow();
                }else{
                    System.out.println("you win");
                    view.showWinWindow();
                }
                System.out.println("Last move by 1 at spot: " + i.spot);
                System.exit(0);
            }else{ // player 2 wins
                if (i.oneWins == 2){
                    view.b.get(i.spot).setText("O");
                    view.b.get(i.spot).setFont(new Font("Arial", Font.BOLD, 25));
                    view.b.get(i.spot).setForeground(Color.RED);
                    if (youR2){
                        System.out.println("you win");
                        view.showWinWindow();
                    }else{
                        System.out.println("you lose");
                        view.showLoseWindow();
                    }
                    System.out.println("Last move by 2 at spot: " + i.spot);
                    System.exit(0);
                }else{ // draws
                    if (i.oneWins == 4){
                        if (i.playerNumber == 1){
                            view.b.get(i.spot).setText("X");
                            view.b.get(i.spot).setFont(new Font("Arial", Font.BOLD, 25));
                            view.b.get(i.spot).setForeground(Color.GREEN);
                        }else{
                            view.b.get(i.spot).setText("O");
                            view.b.get(i.spot).setFont(new Font("Arial", Font.BOLD, 25));
                            view.b.get(i.spot).setForeground(Color.RED);
                        }
                        view.showDrawWindow();
                        System.exit(0);
                    }
                }
            }
        }
        if (i.unlockButtons){
            System.out.println("Unlock buttons");
            view.setEnableButtons();
        }
    }

    /**
     * Starts go function
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        NewPlayer p = new NewPlayer();
        p.go();
    }

}
