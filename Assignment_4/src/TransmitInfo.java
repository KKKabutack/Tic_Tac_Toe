import java.io.Serializable;

/**
 * The class collects game information and is serializable for transmission purpose
 * @auther Wang Huidong
 * @version 1.0.0
 * @since 03/05/2023
 */
public class TransmitInfo implements Serializable {
    public int oneWins;
    public boolean youAreTwo = false;
    public int spot;
    public int playerNumber;
    public boolean newPlayer = false;
    public boolean unlockButtons;
    public boolean left;

}
