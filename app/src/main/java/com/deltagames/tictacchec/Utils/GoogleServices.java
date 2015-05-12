package com.deltagames.tictacchec.Utils;

/**
 * Interface to manage the connections with Google Play Services
 * Created by Bernabé Borrero on 12/05/15.
 */
public interface GoogleServices {
    public void onStartGameRequested(boolean hardMode);
    public void onShowAchievementsRequested();
    public void onShowLeaderboardsRequested();
    public void onSignInButtonClicked();
    public void onSignOutButtonClicked();
}