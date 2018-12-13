package com.basics.android.udacity.scorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Score point that ends the match
    private final int SET_POINT = 10;

    // Tracks the score for Teams A & B
    private int teamsScore[] = {0, 0};

    // The score panels for Teams A & B
    private LinearLayout[] scoreTeamsPanels = new LinearLayout[2];

    // The score numbers for Teams A & B
    private TextView[] scoreTeamsNumbers = new TextView[2];

    // Track the current team with service (0 = Team A; 1 = Team B)
    private int currentService;

    // Tracks the game services for Teams A & B
    private int teamsServices[] = {0, 0};

    // The services stats for Teams A & B
    private TextView[] statsTeamsServices = new TextView[2];

    // Tracks the aces points for Teams A & B
    private int teamsAces[] = {0, 0};

    // The aces stats for Teams A & B
    private TextView[] statsTeamsAces = new TextView[2];

    // Tracks the attacks points for Teams A & B
    private int teamsAttacks[] = {0, 0};

    // The attacks stats for Teams A & B
    private TextView[] statsTeamsAttacks = new TextView[2];

    // Tracks the blocks points for Teams A & B
    private int teamsBlocks[] = {0, 0};

    // The blocks stats for Teams A & B
    private TextView[] statsTeamsBlocks = new TextView[2];

    // Tracks the faults for Teams A & B
    private int teamsFaults[] = {0, 0};

    // The faults stats for Teams A & B
    private TextView[] statsTeamsFaults = new TextView[2];

    // Tracks the errors for Teams A & B
    private int teamsErrors[] = {0, 0};

    // The errors stats for Teams A & B
    private TextView[] statsTeamsErrors = new TextView[2];

    // The efficiency stats for Teams A & B
    private TextView[] statsTeamsEfficiency = new TextView[2];

    /*
     * About app buttons
     *
     * The "First Service" buttons indicate the team is servicing first, to update the stats panel.
     * After the game starts, these buttons turn disabled and the remain buttons turn enabled.
     *
     * Each of panel remain buttons score a single point to a respective team according to
     * volleyball rules.
     * OBS: The idea of multiple buttons is give more stats about the efficiency of each team
     * after the match ends.
     *
     * The "Reset" button reset the scores, re-enable the "First Service" buttons and disable the
     * others buttons.
     */

    // Buttons to start the game and indicate which team has the first service
    private Button buttonFirstService1;
    private Button buttonFirstService2;

    // Buttons to indicate the team which scored by ace service
    private Button buttonAcePoint1;
    private Button buttonAcePoint2;

    // Buttons to indicate the team which scored by regular attack
    private Button buttonAttackPoint1;
    private Button buttonAttackPoint2;

    // Buttons to indicate the team which scored by blocking an opponent attack
    private Button buttonBlockPoint1;
    private Button buttonBlockPoint2;

    // Buttons to indicate the team which scores by an opponent fault.
    private Button buttonOpponentFault1;
    private Button buttonOpponentFault2;

    // Buttons to indicate the team which scores by an opponent error.
    private Button buttonOpponentError1;
    private Button buttonOpponentError2;

    // Button to reset the game
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the resource id for each button on XML layout and assign the variables
        buttonFirstService1 = (Button) findViewById(R.id.btn_first_service_1);
        buttonFirstService2 = (Button) findViewById(R.id.btn_first_service_2);
        buttonAcePoint1 = (Button) findViewById(R.id.btn_ace_point_1);
        buttonAcePoint2 = (Button) findViewById(R.id.btn_ace_point_2);
        buttonAttackPoint1 = (Button) findViewById(R.id.btn_attack_point_1);
        buttonAttackPoint2 = (Button) findViewById(R.id.btn_attack_point_2);
        buttonBlockPoint1 = (Button) findViewById(R.id.btn_block_point_1);
        buttonBlockPoint2 = (Button) findViewById(R.id.btn_block_point_2);
        buttonOpponentFault1 = (Button) findViewById(R.id.btn_opponent_fault_1);
        buttonOpponentFault2 = (Button) findViewById(R.id.btn_opponent_fault_2);
        buttonOpponentError1 = (Button) findViewById(R.id.btn_opponent_error_1);
        buttonOpponentError2 = (Button) findViewById(R.id.btn_opponent_error_2);
        buttonReset = (Button) findViewById(R.id.btn_reset);

        // Find the resource id for each team panel and score on XML layout and assign the variables
        scoreTeamsPanels[0] = (LinearLayout) findViewById(R.id.score_panel_1);
        scoreTeamsPanels[1] = (LinearLayout) findViewById(R.id.score_panel_2);
        scoreTeamsNumbers[0] = (TextView) findViewById(R.id.score_team_a);
        scoreTeamsNumbers[1] = (TextView) findViewById(R.id.score_team_b);

        // Find the resource id for each stat cell on XML layout and assign the variables
        statsTeamsServices[0] = (TextView) findViewById(R.id.service_stat_1);
        statsTeamsServices[1] = (TextView) findViewById(R.id.service_stat_2);
        statsTeamsAces[0] = (TextView) findViewById(R.id.ace_stat_1);
        statsTeamsAces[1] = (TextView) findViewById(R.id.ace_stat_2);
        statsTeamsAttacks[0] = (TextView) findViewById(R.id.attack_stat_1);
        statsTeamsAttacks[1] = (TextView) findViewById(R.id.attack_stat_2);
        statsTeamsBlocks[0] = (TextView) findViewById(R.id.block_stat_1);
        statsTeamsBlocks[1] = (TextView) findViewById(R.id.block_stat_2);
        statsTeamsFaults[0] = (TextView) findViewById(R.id.fault_stat_1);
        statsTeamsFaults[1] = (TextView) findViewById(R.id.fault_stat_2);
        statsTeamsErrors[0] = (TextView) findViewById(R.id.error_stat_1);
        statsTeamsErrors[1] = (TextView) findViewById(R.id.error_stat_2);
        statsTeamsEfficiency[0] = (TextView) findViewById(R.id.efficieny_stat_1);
        statsTeamsEfficiency[1] = (TextView) findViewById(R.id.efficieny_stat_2);

        // Set OnClick method of "First Service" button of Team A side
        buttonFirstService1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(0);
            }
        });

        // Set OnClick method of "First Service" button of Team B side
        buttonFirstService2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(1);
            }
        });

        // Set OnClick method of "Ace Point" button of Team A side
        buttonAcePoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(0, "ace");
            }
        });

        // Set OnClick method of "Ace Point" button of Team B side
        buttonAcePoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(1, "ace");
            }
        });

        // Set OnClick method of "Attack Point" button of Team A side
        buttonAttackPoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(0, "attack");
            }
        });

        // Set OnClick method of "Attack Point" button of Team B side
        buttonAttackPoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(1, "attack");
            }
        });

        // Set OnClick method of "Block Point" button of Team A side
        buttonBlockPoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(0, "block");
            }
        });

        // Set OnClick method of "Block Point" button of Team B side
        buttonBlockPoint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(1, "block");
            }
        });

        // Set OnClick method of "Oppon. Fault" button of Team A side
        buttonOpponentFault1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(0, "fault");
            }
        });

        // Set OnClick method of "Oppon. Fault" button of Team B side
        buttonOpponentFault2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(1, "fault");
            }
        });

        // Set OnClick method of "Oppon. Error" button of Team A side
        buttonOpponentError1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(0, "error");
            }
        });

        // Set OnClick method of "Oppon. Error" button of Team B side
        buttonOpponentError2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreForTeam(1, "error");
            }
        });

        // Set OnClick method of "Reset Button"
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        // Set app for start
        resetGame();
    }

    /**
     * Resets the score and stats for both teams back to 0 and re-adjust the control panel.
     */
    public void resetGame() {
        // Set no current team with service
        currentService = -1;

        // Set all scores and stats variables to zero
        teamsScore[0] = 0;
        teamsScore[1] = 0;
        teamsServices[0] = 0;
        teamsServices[1] = 0;
        teamsAces[0] = 0;
        teamsAces[1] = 0;
        teamsAttacks[0] = 0;
        teamsAttacks[1] = 0;
        teamsBlocks[0] = 0;
        teamsBlocks[1] = 0;
        teamsFaults[0] = 0;
        teamsFaults[1] = 0;
        teamsErrors[0] = 0;
        teamsErrors[1] = 0;

        // Update scores panels
        displayScore(0, 0);
        displayScore(1, 0);

        // Update stats panels
        displayStat(statsTeamsServices[0], 0);
        displayStat(statsTeamsServices[1], 0);
        displayStat(statsTeamsAces[0], 0);
        displayStat(statsTeamsAces[1], 0);
        displayStat(statsTeamsAttacks[0], 0);
        displayStat(statsTeamsAttacks[1], 0);
        displayStat(statsTeamsBlocks[0], 0);
        displayStat(statsTeamsBlocks[1], 0);
        displayStat(statsTeamsFaults[0], 0);
        displayStat(statsTeamsFaults[1], 0);
        displayStat(statsTeamsErrors[0], 0);
        displayStat(statsTeamsErrors[1], 0);
        displayStat(statsTeamsEfficiency[0], 0);
        displayStat(statsTeamsEfficiency[1], 0);


        // Re-enable "First Service" buttons and disable the others
        buttonFirstService1.setEnabled(true);
        buttonFirstService2.setEnabled(true);
        buttonAcePoint1.setEnabled(false);
        buttonAcePoint2.setEnabled(false);
        buttonAttackPoint1.setEnabled(false);
        buttonAttackPoint2.setEnabled(false);
        buttonBlockPoint1.setEnabled(false);
        buttonBlockPoint2.setEnabled(false);
        buttonOpponentFault1.setEnabled(false);
        buttonOpponentFault2.setEnabled(false);
        buttonOpponentError1.setEnabled(false);
        buttonOpponentError2.setEnabled(false);

        // remove winning indicators on panels, return them to normal color
        scoreTeamsPanels[0].setBackgroundResource(R.drawable.score_background);
        scoreTeamsPanels[1].setBackgroundResource(R.drawable.score_background);
    }

    /**
     * Start the game and update service stats for the given Team by 1 point.
     *
     * @param index of Team on scoreTeams[] variable
     */
    public void startGame(int index) {

        // Disable "First Service" buttons and enable the others
        buttonFirstService1.setEnabled(false);
        buttonFirstService2.setEnabled(false);
        buttonAttackPoint1.setEnabled(true);
        buttonAttackPoint2.setEnabled(true);
        buttonBlockPoint1.setEnabled(true);
        buttonBlockPoint2.setEnabled(true);
        buttonOpponentFault1.setEnabled(true);
        buttonOpponentFault2.setEnabled(true);
        buttonOpponentError1.setEnabled(true);
        buttonOpponentError2.setEnabled(true);

        // set the current team with service
        setCurrentService(index);
    }

    /**
     * Set the current team with service and enable the "Ace Point" button for the team.
     * The opponent team will have his "Ace Point" button disabled since only the team with
     * service is capable of ace points
     *
     * @param index of Team on scoreTeams[] variable
     */
    public void setCurrentService(int index) {
        currentService = index;

        if(index == 0) {
            buttonAcePoint1.setEnabled(true);
            buttonAcePoint2.setEnabled(false);
        } else {
            buttonAcePoint1.setEnabled(false);
            buttonAcePoint2.setEnabled(true);
        }
    }

    /**
     * Increase the score for the given Team by 1 point and increase corresponding stats.
     *
     * @param index  of Team on scoreTeams[] variable
     * @param action performed by a team (or its opponent) to result a point
     */
    public void scoreForTeam(int index, String action) {
        // Increase the service stat for current team with service and update it on panel
        displayStat(statsTeamsServices[currentService], ++teamsServices[currentService]);

        // Set the service to team which scored
        setCurrentService(index);

        // Handle the action performed by team to achieve the point (accordind to button pressed)
        // and update corresponding stat on stats panel
        switch (action) {
            case "ace":
                // Increase the ace stat to team which scored by ace and update the stat on panel
                displayStat(statsTeamsAces[index], ++teamsAces[index]);
                break;
            case "attack":
                // Increase the attack stat to team which scored by attack and update the stat on panel
                displayStat(statsTeamsAttacks[index], ++teamsAttacks[index]);
                break;
            case "block":
                // Increase the block stat to team which scored by block and update the stat on panel
                displayStat(statsTeamsBlocks[index], ++teamsBlocks[index]);
                break;
            case "fault":
                // Here the opponent fault stats are updated instead the team which scored because
                // the team achieved a point by an opponent fault and we'd like to register faults
                // commited by a team
                displayStat(statsTeamsFaults[index ^ 1], ++teamsFaults[index ^ 1]);
                break;
            case "error":
                // Here the opponent error stats are updated instead the team which scored because
                // the team achieved a point by an opponent error and we'd like to register erros
                // commited by a team
                displayStat(statsTeamsErrors[index ^ 1], ++teamsErrors[index ^ 1]);
                break;
        }

        // Increase score of team which scored and update the corresponding panel
        displayScore(index, ++teamsScore[index]);

        // Calculate and update efficiency stats for both teams
        int efficiency = calculateEfficiency(teamsAces[index], teamsAttacks[index], teamsBlocks[index], teamsServices[index]);
        displayStat(statsTeamsEfficiency[index], efficiency);
        efficiency = calculateEfficiency(teamsAces[index ^ 1], teamsAttacks[index ^ 1], teamsBlocks[index ^ 1], teamsServices[index ^ 1]);
        displayStat(statsTeamsEfficiency[index ^ 1], efficiency);

        // Check if the game is finished, end and match and set the winner panel with a color
        // indicator
        checkEndGame(index);
    }

    /**
     * Check if the given team achieved the set point with a difference of 2 points to the
     * opponent and finish the game
     *
     * @param index of Team on scoreTeams[] variable to check winning
     */
    public void checkEndGame(int index){
        if((teamsScore[index] >= SET_POINT) && ((teamsScore[index] - teamsScore[index ^1]) > 1)) {
            // Set the color indicator on winner's score panel
            scoreTeamsPanels[index].setBackgroundResource(R.drawable.end_score_background);

            // Disable all control panel buttons
            buttonFirstService1.setEnabled(false);
            buttonFirstService2.setEnabled(false);
            buttonAcePoint1.setEnabled(false);
            buttonAcePoint2.setEnabled(false);
            buttonAttackPoint1.setEnabled(false);
            buttonAttackPoint2.setEnabled(false);
            buttonBlockPoint1.setEnabled(false);
            buttonBlockPoint2.setEnabled(false);
            buttonOpponentFault1.setEnabled(false);
            buttonOpponentFault2.setEnabled(false);
            buttonOpponentError1.setEnabled(false);
            buttonOpponentError2.setEnabled(false);
        }
    }

    /**
     * Displays the given score for the given Team.
     *
     * @param index of Team on scoreTeams[] variable
     * @param value to display on score panel
     */
    public void displayScore(int index, int value) {
        scoreTeamsNumbers[index].setText(String.valueOf(value));
    }

    /**
     * Displays the given stat for a Team.
     *
     * @param textview of corresponding stat cell of a Team
     * @param value to display on corresponding stat cell
     */
    public void displayStat(TextView textview, int value) {
        textview.setText(String.valueOf(value));
    }

    /**
     * Calculate team efficiency during the set game.
     *
     * Here, I didn't have a deep approach on efficiency calculation. For pourpose of this app, I
     * choose to considerate the efficiency as the percentage of services of a team which lead to
     * a successful point
     *
     * @param aces total
     * @param attacks total
     * @param blocks total
     * @param services total
     */
    public int calculateEfficiency(int aces, int attacks, int blocks, int services){
        if(services == 0) {
            return 0;
        }

        return (int) 100 * (aces + attacks + blocks) / services;
    }

}
