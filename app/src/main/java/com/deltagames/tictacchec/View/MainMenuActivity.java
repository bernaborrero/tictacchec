package com.deltagames.tictacchec.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.deltagames.tictacchec.R;
import com.deltagames.tictacchec.Utils.GoogleServices;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameUtils;


public class MainMenuActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleServices {

    private TextView playWithComputerButton, playWithPersonButton, exitButton;
    private SignInButton signInButton;
    private View.OnClickListener mainMenuListener;

    private GoogleApiClient googleApiClient;
    private boolean resolvingConnectionFailure;
    private boolean signInClicked;
    private boolean autoStartSignInFlow;
    private boolean hardMode;

    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpGooglePlayServices();
        setUpGUI();
    }

    /**
     * Set up Google Play Services related data
     */
    private void setUpGooglePlayServices() {
        resolvingConnectionFailure = false;
        signInClicked = false;
        autoStartSignInFlow = true;
        hardMode = false;

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }

    /**
     * Set up the GUI elements of the main menu
     */
    private void setUpGUI() {
        playWithComputerButton = (TextView) findViewById(R.id.playWithComputerButton);
        playWithPersonButton = (TextView) findViewById(R.id.playWithPersonButton);
        exitButton = (TextView) findViewById(R.id.exitButton);
        signInButton = (SignInButton) findViewById(R.id.signInButton);

        setUpListeners();
    }

    /**
     * Set up the listeners of the main menu
     */
    private void setUpListeners() {
        mainMenuListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.playWithComputerButton:
                        Intent computerIntent = new Intent(getBaseContext(), GameActivity.class);
                        computerIntent.putExtra(GameActivity.GAME_MODE, GameActivity.GameMode.COMPUTER);
                        startActivity(computerIntent);
                        break;
                    case R.id.playWithPersonButton:
                        Intent personIntent = new Intent(getBaseContext(), GameActivity.class);
                        personIntent.putExtra(GameActivity.GAME_MODE, GameActivity.GameMode.PERSON);
                        startActivity(personIntent);
                        break;
                    case R.id.exitButton:
                        finish();
                        break;
                    case R.id.signInButton:
                        onSignInButtonClicked();
                        break;
                }
            }
        };

        playWithComputerButton.setOnClickListener(mainMenuListener);
        playWithPersonButton.setOnClickListener(mainMenuListener);
        exitButton.setOnClickListener(mainMenuListener);
        signInButton.setOnClickListener(mainMenuListener);
    }

    /**
     * Check if the user has signed in in Google Play Services
     * @return true if the user has signed in, false otherwise
     */
    private boolean isSignedIn() {
        return (googleApiClient != null && googleApiClient.isConnected());
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
    public void onConnected(Bundle bundle) {

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
    }

    @Override
    public void onStartGameRequested(boolean hardMode) {
        startGame(hardMode);
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
    public void onShowLeaderboardsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(googleApiClient), RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.leaderboards_not_available)).show();
        }
    }

    /**
     * Start the game
     * @param hardMode the mode to start the game
     */
    private void startGame(boolean hardMode) {
        this.hardMode = hardMode;
        Log.d("GAME MENU", "Starting game... (?)");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            signInClicked = false;
            resolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
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
    }

}
