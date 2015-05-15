package com.deltagames.tictacchec.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deltagames.tictacchec.R;
import com.deltagames.tictacchec.View.Utils.BoldTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;


public class MainMenuActivity extends BaseActivity {

    private TextView playWithComputerButton, playWithPersonButton, openLeaderBoardsButton, openAchievementsButton;
    private SignInButton signInButton;
    private BoldTextView signOutButton;
    private View.OnClickListener mainMenuListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpGUI();
    }

    /**
     * Set up the GUI elements of the main menu
     */
    private void setUpGUI() {
        playWithComputerButton = (TextView) findViewById(R.id.playWithComputerButton);
        playWithPersonButton = (TextView) findViewById(R.id.playWithPersonButton);
        openLeaderBoardsButton = (TextView) findViewById(R.id.openLeaderBoardsButton);
        openAchievementsButton = (TextView) findViewById(R.id.openAchievementsButton);
        signInButton = (SignInButton) findViewById(R.id.signInButton);
        signOutButton = (BoldTextView) findViewById(R.id.signOutButton);

        updateSignInButtons();
        setUpListeners();
    }

    /**
     * Set properly the visibility of the sign in / out buttons
     */
    private void updateSignInButtons() {
        signInButton.setVisibility(isShowSignIn() ? View.VISIBLE : View.GONE);
        signOutButton.setVisibility(isShowSignIn() ? View.GONE : View.VISIBLE);

        int orange = getResources().getColor(R.color.orange);
        int fadedOrange = getResources().getColor(R.color.fadedOrange);

        openLeaderBoardsButton.setTextColor(isShowSignIn() ? fadedOrange : orange);
        openAchievementsButton.setTextColor(isShowSignIn() ? fadedOrange : orange);
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
                    case R.id.openLeaderBoardsButton:
                        if (isShowSignIn()) {
                            Toast.makeText(getBaseContext(), R.string.signin_leaderboards_error, Toast.LENGTH_SHORT).show();
                        } else {
                            onShowLeaderBoardsRequested();
                        }

                        break;
                    case R.id.openAchievementsButton:
                        if (isShowSignIn()) {
                            Toast.makeText(getBaseContext(), R.string.signin_achievements_error, Toast.LENGTH_SHORT).show();
                        } else {
                            onShowAchievementsRequested();
                        }

                        break;
                    case R.id.signInButton:
                        onSignInButtonClicked();
                        break;
                    case R.id.signOutButton:
                        onSignOutButtonClicked();
                        break;
                }
            }
        };

        playWithComputerButton.setOnClickListener(mainMenuListener);
        playWithPersonButton.setOnClickListener(mainMenuListener);
        openLeaderBoardsButton.setOnClickListener(mainMenuListener);
        openAchievementsButton.setOnClickListener(mainMenuListener);
        signInButton.setOnClickListener(mainMenuListener);
        signOutButton.setOnClickListener(mainMenuListener);
    }

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);
        updateSignInButtons();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        updateSignInButtons();
    }

    @Override
    public void onSignOutButtonClicked() {
        super.onSignOutButtonClicked();
        updateSignInButtons();
    }

}
