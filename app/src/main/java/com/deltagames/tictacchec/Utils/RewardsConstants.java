package com.deltagames.tictacchec.Utils;

/**
 * Class to store the Google Play Services rewards codes
 * Created by Bernabé Borrero on 14/05/15.
 */
public abstract class RewardsConstants {

    public static final String SCORES_FILE = "savedScores";

    public static abstract class Achievements {

        /**
         * Moves done
         */
        public static final String firstMoveDone = "CgkIi5711N4BEAIQDA";

        /**
         * Games played
         */
        public static final String GAMES_PLAYED = "GAMES_PLAYED";
        public static final String firstGamePlayed = "CgkIi5711N4BEAIQAQ";
        public static final String tenGamesPlayed = "CgkIi5711N4BEAIQBA";
        public static final String fiftyGamesPlayed = "CgkIi5711N4BEAIQBQ";
        public static final String aHundredGamesPlayed = "CgkIi5711N4BEAIQBg";

        /**
         * Games won
         */
        public static final String GAMES_WON = "GAMES_WON";
        public static final String firstGameWon = "CgkIi5711N4BEAIQBw";
        public static final String tenGamesWon = "CgkIi5711N4BEAIQCA";
        public static final String fiftyGamesWon = "CgkIi5711N4BEAIQCQ";
        public static final String aHundredGamesWon = "CgkIi5711N4BEAIQCg";
    }

    public static abstract class LeaderBoards {

        /**
         * High Score
         */
        public static final String HIGH_SCORE = "HIGH_SCORE";
        public static final String highScore = "CgkIi5711N4BEAIQAw";
    }

}
