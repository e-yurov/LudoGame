package ru.vsu.cs.yurov.graphics;

import ru.vsu.cs.yurov.logics.Game;
import ru.vsu.cs.yurov.logics.Piece;
import ru.vsu.cs.yurov.logics.Player;
import ru.vsu.cs.yurov.logics.actions.piece.PieceActionReceiver;

import java.util.Scanner;

public class TextAdapter {
    private PieceActionReceiver receiver = new PieceActionReceiver(){
        Scanner scanner = new Scanner(System.in);

        @Override
        public Piece receive(Player player, int number) {
            System.out.println(player.getColor() + " player turn");
            System.out.println("Die roll: " + number);
            Piece[] pieces = player.getPieces();
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i].canMove()) {
                    System.out.println("Piece " + i + " can move");
                }
            }

            System.out.println("Choose piece to move:");
            int index = -1;
            while (true) {
                String line = scanner.nextLine();
                try {
                    index = Integer.parseInt(line);
                    if (index < 0 || index > 3) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Wrong piece number!");
                }
            }
            return pieces[index];
        }
    };
    private Game game = new Game();

    public void play() {
        game.setReceiver(receiver);
        game.setGameDrawer(new TextGameDrawer(game, System.out));
        game.start();
    }

    public static void main(String[] args) {
        TextAdapter textAdapter = new TextAdapter();
        textAdapter.play();
    }
}
