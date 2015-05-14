package com.deltagames.tictacchec.View;

import android.content.Intent;
import android.os.Bundle;

import com.deltagames.tictacchec.R;
import com.deltagames.tictacchec.Utils.GoogleServices;
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

    private boolean resolvingConnectionFailure;
    private boolean signInClicked;
    private boolean autoStartSignInFlow;
    private boolean showSignIn;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        setUpGooglePlayServices();
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
