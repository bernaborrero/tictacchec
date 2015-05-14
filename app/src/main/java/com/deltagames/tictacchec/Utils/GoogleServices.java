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
}