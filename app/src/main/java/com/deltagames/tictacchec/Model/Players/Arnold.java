package com.deltagames.tictacchec.Model.Players;

import com.badlogic.gdx.Gdx;
import com.deltagames.tictacchec.Model.Board.Board;
import com.deltagames.tictacchec.Model.Board.Coordinates;
import com.deltagames.tictacchec.Model.Board.Move;
import com.deltagames.tictacchec.Model.Board.Moves;

import java.util.concurrent.Semaphore;

/**
 * Artificial Intelligence of the Tic Tac Chec.
 * This is Arnold, and he has returned.
 * Created by Bernabé Borrero on 27/04/15.
 */
public class Arnold extends Player {

    private final String ARNOLD_TAG = "Arnold";
    private final int EXHAUSTIVE_LEVELS = 8;

    @Override
    public void move(Board board, Player enemy, Semaphore blockingSemaphore) {

        new ThinkingThread(board, this, enemy, blockingSemaphore).run();

    }

    private class ThinkingThread extends Thread {

        private Board board;
        private Arnold arnold;
        private Player enemy;
        private Semaphore blockingSemaphore;

        public ThinkingThread(Board board, Arnold arnold, Player enemy, Semaphore blockingSemaphore) {
            this.board = board;
            this.arnold = arnold;
            this.enemy = enemy;
            this.blockingSemaphore = blockingSemaphore;
        }

        @Override
        public void run() {
            try {
                blockingSemaphore.acquire();

                // calculations
                Move minInfinite = new Move(null, null, -100000);
                Move maxInfinite = new Move(null, null, +100000);

                Move move = alphabeta(board, arnold, enemy, null, EXHAUSTIVE_LEVELS, minInfinite, maxInfinite, true);
                board.set(move.getPiece(), move.getCoordinates());

                blockingSemaphore.release();
            } catch (InterruptedException e) {
                Gdx.app.log(ARNOLD_TAG, "Whoops! I can't block the UI!");
            }
        }

        /**
         * MiniMax with alpha beta to get the best Move
         * @param board the current Board
         * @param arnold Arnold
         * @param human the HumanPlayer
         * @param node the current Move
         * @param depth the exhaustivity of the algorithm
         * @param alpha the lowest Move
         * @param beta the highest Move
         * @param maximizingPlayer true if the current Player is the maximizing one (Arnold or not)
         * @return the best Move
         */
        private Move alphabeta(Board board, Player arnold, Player human, Move node, int depth, Move alpha, Move beta, boolean maximizingPlayer) {

            Player currentPlayer = maximizingPlayer ? arnold : human;
            Coordinates previousCoordinates = null;

            if (node != null) {
                previousCoordinates = node.getPiece().getCoordinates();
                board.set(node.getPiece(), node.getCoordinates());

                if (currentPlayer.hasWon() || depth == 0) {
                    board.set(node.getPiece(), previousCoordinates); // go back to previous state
                    return node;
                }
            }

            Moves moves = currentPlayer.getMoves(board);

            if (maximizingPlayer) {
                for (Object move : moves) {
                    alpha = max(alpha, alphabeta(board, arnold, human, (Move) move, depth - 1, alpha, beta, false));
                    if (beta.getWeight() <= alpha.getWeight()) {
                        break;
                    }
                }

                if (previousCoordinates != null) {
                    board.set(node.getPiece(), previousCoordinates); // go back to previous state
                }

                return alpha;
            }
            else {

                for (Object move : moves) {
                    beta = min(beta, alphabeta(board, arnold, human, (Move) move, depth - 1, alpha, beta, true));
                    if (beta.getWeight() <= alpha.getWeight()) {
                        break;
                    }
                }

                if (previousCoordinates != null) {
                    board.set(node.getPiece(), previousCoordinates); // go back to previous state
                }

                return beta;
            }
        }

        /**
         * Retrieves the Move with the highest weight
         * @param m1 the first Move
         * @param m2 the second Move
         * @return the Move with the highest weight, or the first one if they are equal
         */
        private Move max(Move m1, Move m2) {
            return (m1.getWeight() >= m2.getWeight()) ? m1 : m2;
        }

        /**
         * Retrieves the Move with the lowest weight
         * @param m1 the first Move
         * @param m2 the second Move
         * @return the Move with the lowest weight, or the first one if they are equal
         */
        private Move min(Move m1, Move m2) {
            return (m1.getWeight() <= m2.getWeight()) ? m1 : m2;
        }

    }
}
