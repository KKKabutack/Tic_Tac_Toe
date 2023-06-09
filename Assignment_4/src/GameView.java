import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class only contains GUI components, and implementation of Exit and Instruction functionality.
 * @auther Wang Huidong
 * @version 1.0.0
 * @since 03/05/2023
 */
public class GameView {
    JFrame frame;
    JMenu menuControl, menuHelp;
    JMenuBar menuBar;
    JMenuItem menuExit, menuInstruction;
    JPanel mainPanel, namePanel, boardPanel, textPanel;
    JLabel upperPanelLabel;
    JTextField textField;
    JButton submit;
    ArrayList<JButton> b;

    /**
     * This go function starts Game GUI
     */
    public void go() {
        newButtons();

        // initiate mainPanel
        mainPanel = new JPanel(); // set it to boxLayout
        BoxLayout mainLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(mainLayout);

        // set menuBar
        menuBar = new JMenuBar();
        menuControl = new JMenu("Control");
        menuHelp = new JMenu("Help");
        menuExit = new JMenuItem("Exit");
        menuInstruction = new JMenuItem("Instructions");
        menuControl.add(menuExit);
        menuHelp.add(menuInstruction);
        menuBar.add(menuControl);
        menuBar.add(menuHelp);
        mainPanel.add(menuBar);
        menuExit.addActionListener(new exitListener());
        menuInstruction.addActionListener(new instructionListener());

        // set namePanel
        namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(450, 50));
        String labelInfo = "Enter your player name, please...";
        upperPanelLabel = new JLabel(labelInfo);
        namePanel.add(upperPanelLabel);
        mainPanel.add(namePanel);

        // set boardPanel (我还不知道怎么画一个井字)
        boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setPreferredSize(new Dimension(300, 300));
        addButtons();
        setDisableButtons();
        mainPanel.add(boardPanel);

        // set textPanel
        textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(450, 100));
        textField = new JTextField(20);
        submit = new JButton("Submit");
        textPanel.add(textField);
        textPanel.add(submit);
        mainPanel.add(textPanel);

        // set the frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.add(Box.createVerticalGlue());
        frame.setContentPane(mainPanel);
        frame.setJMenuBar(menuBar);
        frame.setTitle("Tic-Tac-Toe-Player");
        frame.setSize(300, 450);
        frame.setVisible(true);
        }

    /**
     * This class is the exit listener.
     */
    class exitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * This is a shortcut to new JButtons
     */
    class instructionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,"Some information about the game:\n" +
                    "Criteria for a valid move:\n" +
                    "- \uFEFFThe move is not occupied by any mark.\n" +
                    "- \uFEFFThe move is made in the player's turn.\n" +
                    "- \uFEFFThe move is made within the 3 x 3 board.\n" +
                    "The game would continue and switch among the opposite player until it reaches either one of the following conditions:\n" +
                    "- \uFEFFPlayer 1 wins.\n" +
                    "- \uFEFFPlayer 2 wins.\n" +
                    "- \uFEFFDraw.\n");
        }
    }
    private void addButtons() {
        for (int i = 0; i < 9; i++) {
            boardPanel.add(b.get(i));
        }
    }

    public void setDisableButtons(){
        for (int i = 0; i < 9; i++){
            b.get(i).setEnabled(false);
        }
    }
    public void setEnableButtons(){
        for (int i = 0; i < 9; i++){
            b.get(i).setEnabled(true);
        }
    }


    private void newButtons(){
        b = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            JButton j = new JButton();
            j.setPreferredSize(new Dimension(100, 100));
            b.add(j);
        }
    }

    /**
     * This function pops up a window for winning
     */
    public void showWinWindow(){
        JOptionPane.showMessageDialog(frame, "You win!");
    }

    /**
     * This function pops up a window for losing
     */
    public void showLoseWindow(){
        JOptionPane.showMessageDialog(frame, "You lose!");
    }

    /**
     * This function pops up a window for tied game
     */
    public void showDrawWindow(){
        JOptionPane.showMessageDialog(frame, "It's a draw!");
    }

    /**
     * This function pops up a window when someone left.
     */
    public void someOneLeft(){
        JOptionPane.showMessageDialog(frame, "The other player left. Game Over.");
        System.exit(0);
    }
}
