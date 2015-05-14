package com.deltagames.tictacchec.Utils;

/**
 * Interface to manage the connections with Google Play Services
 * Created by Bernabé Borrero on 12/05/15.
 */
public interface GoogleServices {

    /**
     * Handle the displaying of the Achievements
     */
    public void onShowAchievementsRequested();

    /**
     * Handle the displaying of the Leader Boards
     */
    public void onShowLeaderBoardsRequested();

    /**
     * Handle the sign in click
     */
    public void onSignInButtonClicked();

    /**
     * Handle the sign out click
     */
    public void onSignOutButtonClicked();

    /**
     * Unlock an achievement
     * @param achievementCode the Google Play Services code of the achievement
     */
    public void unlockAchievement(String achievementCode);

    /**
     * Increment the value of an incremental achievement
     * @param achievementCode the Google Play Services code of the achievement
     */
    public void incrementAchievement(String achievementCode);

    /**
     * Update the user's score
     * @param score the new score
     */
    public void updateHighScore(String leaderBoardCode, int score);


}