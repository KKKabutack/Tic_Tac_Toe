This is an instruction of how to play the game!

General using tips:
1.  Start ServerProgram.java first
2.  Start NewPlayer
3.  You can choose to start Player2, or if you can find a way to start NewPlayer again and make them run in parallel,
    my suggestion is you simply start Player2, because it actually creates a NewPlayer Object.
4.  About how to play, and the rules of the game, you can view the instructions on the menu bar,
    it's a simple Tic-Tac-Toe game, you could Google it directly, you should try to make a row, column, or diagonal
    line of 3.
5.  MOST IMPORTANTLY!!!! The file that you started first is Player 1
    (when you open it, it connects the server, and the whenever you click on submit button doesn't matter),
    and should make the first move.

About how to set up the game using terminal:
1.  Open terminal, access  ~/src/ (~is the address of the decompressed folder)
2.  javac *
3.  java ServerProgram
4.  open another terminal window
5.  java NewPlayer
6.  open another terminal window
7.  access ~/src/ and java NewPlayer (or java Player2 if you want.)
8.  start the game.

About how to set up the game using IDEs:
1.  Import the whole folder into your IDE, and remember to overwrite existing files to avoid configuration problems.
2.  Click and run ServerProgram.
3.  Click and run NewPlayer.
4.  Click and run Player2 (a phony class to create another NewPlayer object).
5.  Start your game.

Issues you may face:
1.  There might be some connection issues. Get to the source code, find where IP address and TCP port number is,
    change it to your local IP address and find another TCP port greater than 5000.
2.  Every time a game ends, please repeat the procedures mentioned above,
    including restart the server, to start a new game.