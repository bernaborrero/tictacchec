package com.deltagames.tictacchec.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.deltagames.tictacchec.R;
import com.deltagames.tictacchec.Utils.GoogleServices;
import com.deltagames.tictacchec.Utils.RewardsConstants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.BaseGameUtils;

/**
 * Base Activity, containing all the connections to the Google Play Services
 * Created by Bernabé Borrero on 14/05/15.
 */
public class BaseActivity extends BaseGameActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleServices {

    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient googleApiClient;
    private SharedPreferences sharedPref;

    private boolean resolvingConnectionFailure;
    private boolean signInClicked;
    private boolean autoStartSignInFlow;
    private boolean showSignIn;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        setUpGooglePlayServices();

        sharedPref = this.getSharedPreferences(RewardsConstants.SCORES_FILE, Context.MODE_PRIVATE);
    }

    /**
     * Set up Google Play Services related data
     */
    private void setUpGooglePlayServices() {
        resolvingConnectionFailure = false;
        signInClicked = false;
        autoStartSignInFlow = true;
        showSignIn = true;

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }

    @Override
    protected GoogleApiClient getApiClient() {
        return googleApiClient;
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        showSignIn = false;
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (resolvingConnectionFailure) {
            return;
        }

        if (signInClicked || autoStartSignInFlow) {
            autoStartSignInFlow = false;
            signInClicked = false;
            resolvingConnectionFailure = true;
            if (!BaseGameUtils.resolveConnectionFailure(this, googleApiClient, connectionResult, RC_SIGN_IN, getString(R.string.signin_other_error))) {
                resolvingConnectionFailure = false;
            }
        }

        showSignIn = true;
    }

    @Override
    public void onShowAchievementsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(googleApiClient), RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.achievements_not_available)).show();
        }
    }

    @Override
    public void onShowLeaderBoardsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(googleApiClient), RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.leaderboards_not_available)).show();
        }
    }

    @Override
    protected void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        if (request == RC_SIGN_IN) {
            signInClicked = false;
            resolvingConnectionFailure = false;
            if (response == RESULT_OK) {
                googleApiClient.connect();
            }
        }
    }

    @Override
    public void onSignInButtonClicked() {
        signInClicked = true;
        googleApiClient.connect();
    }

    @Override
    public void onSignOutButtonClicked() {
        signInClicked = false;
        Games.signOut(googleApiClient);
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }

        showSignIn = true;
    }

    @Override
    public void unlockAchievement(String achievementCode) {
        if (isSignedIn()) {
            Games.Achievements.unlock(googleApiClient, achievementCode);
        }
    }

    @Override
    public void incrementAchievement(String achievementCode) {
        if (isSignedIn()) {
            Games.Achievements.increment(googleApiClient, achievementCode, 1);
        }
    }

    @Override
    public void updateHighScore(String leaderBoardCode, int score) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(googleApiClient, leaderBoardCode, score);
        }
    }

    /**
     * Update the Google Play Services games played
     */
    private void updateGamesPlayed() {
        int gamesPlayed = sharedPref.getInt(RewardsConstants.Achievements.GAMES_PLAYED, 0);

        if (gamesPlayed == 1) {
            unlockAchievement(RewardsConstants.Achievements.firstGamePlayed);
            incrementAchievement(RewardsConstants.Achievements.tenGamesPlayed);
            incrementAchievement(RewardsConstants.Achievements.fiftyGamesPlayed);
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesPlayed);
        }
        else if (gamesPlayed >= 2 && gamesPlayed <= 10) {
            incrementAchievement(RewardsConstants.Achievements.tenGamesPlayed);
            incrementAchievement(RewardsConstants.Achievements.fiftyGamesPlayed);
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesPlayed);
        }
        else if (gamesPlayed >= 11 && gamesPlayed <= 50) {
            incrementAchievement(RewardsConstants.Achievements.fiftyGamesPlayed);
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesPlayed);
        }
        else if (gamesPlayed >= 51 && gamesPlayed <= 100) {
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesPlayed);
        }
    }

    /**
     * Update the Google Play Services games won
     */
    private void updateGamesWon() {
        int gamesWon = sharedPref.getInt(RewardsConstants.Achievements.GAMES_WON, 0);

        if (gamesWon == 1) {
            unlockAchievement(RewardsConstants.Achievements.firstGameWon);
            incrementAchievement(RewardsConstants.Achievements.tenGamesWon);
            incrementAchievement(RewardsConstants.Achievements.fiftyGamesWon);
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesWon);
        }
        else if (gamesWon >= 2 && gamesWon <= 10) {
            incrementAchievement(RewardsConstants.Achievements.tenGamesWon);
            incrementAchievement(RewardsConstants.Achievements.fiftyGamesWon);
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesWon);
        }
        else if (gamesWon >= 11 && gamesWon <= 50) {
            incrementAchievement(RewardsConstants.Achievements.fiftyGamesWon);
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesWon);
        }
        else if (gamesWon >= 51 && gamesWon <= 100) {
            incrementAchievement(RewardsConstants.Achievements.aHundredGamesWon);
        }
    }

    /**
     * Update the Google Play Services leader boards
     */
    private void updateLeaderBoard() {
        int highScore = sharedPref.getInt(RewardsConstants.LeaderBoards.HIGH_SCORE, 0);
        updateHighScore(RewardsConstants.LeaderBoards.highScore, highScore);
    }

    /**
     * Increment the number of games the user has played
     */
    public void incrementGamesPlayed() {
        int gamesPlayed = sharedPref.getInt(RewardsConstants.Achievements.GAMES_PLAYED, 0);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(RewardsConstants.Achievements.GAMES_PLAYED, ++gamesPlayed);
        editor.commit();

        updateGamesPlayed();
    }

    /**
     * Increment the number of games the user has won
     */
    public void incrementGamesWon() {
        int gamesWon = sharedPref.getInt(RewardsConstants.Achievements.GAMES_WON, 0);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(RewardsConstants.Achievements.GAMES_WON, ++gamesWon);
        editor.commit();

        updateGamesWon();
    }

    /**
     * Increment the high score of the user
     * @param score the score to add to the current high score
     */
    public void incrementScore(int score) {
        int highScore = sharedPref.getInt(RewardsConstants.LeaderBoards.HIGH_SCORE, 0);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(RewardsConstants.LeaderBoards.HIGH_SCORE, highScore + score);
        editor.commit();

        updateLeaderBoard();
    }

    /**
     * Check if the user has signed in in Google Play Services
     * @return true if the user has signed in, false otherwise
     */
    public boolean isSignedIn() {
        return (googleApiClient != null && googleApiClient.isConnected());
    }

    /**
     * If the sign in message should be displayed
     * @return true if the message should be displayed, false otherwise
     */
    public boolean isShowSignIn() {
        return showSignIn;
    }
}
