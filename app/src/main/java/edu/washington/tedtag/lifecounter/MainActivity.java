package edu.washington.tedtag.lifecounter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public int playerCount = 4;
    public String loseMessage = "";
    public ArrayList<ArrayList> players = new ArrayList<>();
    public ArrayList<Integer> playerLife = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            playerCount = savedInstanceState.getInt("STATE_PLAYER_COUNT");
            playerLife = savedInstanceState.getIntegerArrayList("STATE_PLAYER_LIFE");
            loseMessage = savedInstanceState.getString("STATE_LOSE_MESSAGE");
            TextView message = (TextView) findViewById(R.id.lose_message);
            message.setText(loseMessage);
        }

        // Set players[0] to null to make it easier to keep track of the index
        players.add(0, null);

        Button addPlayer = (Button) findViewById(R.id.add_player);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerCount < 8) {
                    playerCount++;
                    findViewById(getResources().getIdentifier("p" + playerCount + "_container", "id", getPackageName())).setVisibility(View.VISIBLE);
                    createPlayers(null);
                }
            }
        });

        createPlayers(savedInstanceState);

    }

    private void createPlayers(Bundle savedInstanceState) {
        for (int i = 1; i <= playerCount; i++) {
            final ArrayList<View> thisPlayer = new ArrayList<>();
            thisPlayer.add(0, findViewById(getResources().getIdentifier("p" + i + "_life", "id", getPackageName())));
            thisPlayer.add(1, findViewById(getResources().getIdentifier("p" + i + "_a1", "id", getPackageName())));
            thisPlayer.add(2, findViewById(getResources().getIdentifier("p" + i + "_a5", "id", getPackageName())));
            thisPlayer.add(3, findViewById(getResources().getIdentifier("p" + i + "_s1", "id", getPackageName())));
            thisPlayer.add(4, findViewById(getResources().getIdentifier("p" + i + "_s5", "id", getPackageName())));

            if (savedInstanceState != null) {
                // Restore value of members from saved state
                TextView lifeText = (TextView) thisPlayer.get(0);
                lifeText.setText(playerLife.get(i) + " Life");
            }

            thisPlayer.get(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView lifeText = (TextView) thisPlayer.get(0);
                    int life = Integer.parseInt(lifeText.getText().toString().split(" ")[0]);
                    life++;
                    lifeText.setText(life + " Life");
                }
            });

            thisPlayer.get(2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView lifeText = (TextView) thisPlayer.get(0);
                    int life = Integer.parseInt(lifeText.getText().toString().split(" ")[0]);
                    life = life + 5;
                    lifeText.setText(life + " Life");
                }
            });

            thisPlayer.get(3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView lifeText = (TextView) thisPlayer.get(0);
                    int life = Integer.parseInt(lifeText.getText().toString().split(" ")[0]);
                    life--;
                    lifeText.setText(life + " Life");

                    if(life <= 0) {
                        loseMessage = "Player " + players.indexOf(thisPlayer) + " lost!";
                        TextView message = (TextView) findViewById(R.id.lose_message);
                        message.setText(loseMessage);
                    }
                }
            });

            thisPlayer.get(4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView lifeText = (TextView) thisPlayer.get(0);
                    int life = Integer.parseInt(lifeText.getText().toString().split(" ")[0]);
                    life = life - 5;
                    lifeText.setText(life + " Life");

                    if(life <= 0) {
                        TextView message = (TextView) findViewById(R.id.lose_message);
                        loseMessage = "Player " + players.indexOf(thisPlayer) + " lost!";
                        message.setText(loseMessage);
                    }
                }
            });
            players.add(i, thisPlayer);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        ArrayList<Integer> playerLifeState = new ArrayList();
        playerLifeState.add(0, 0);
        for (int i = 1; i <= playerCount; i++) {
            TextView lifeText = (TextView) findViewById(getResources().getIdentifier("p" + i + "_life", "id", getPackageName()));
            int life = Integer.parseInt(lifeText.getText().toString().split(" ")[0]);
            playerLifeState.add(i, life);
        }

        // Save the user's current game state
        savedInstanceState.putInt("STATE_PLAYER_COUNT", playerCount);
        savedInstanceState.putIntegerArrayList("STATE_PLAYER_LIFE", playerLifeState);
        savedInstanceState.putString("STATE_LOSE_MESSAGE", loseMessage);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
