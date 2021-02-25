package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  TextView textView;
    private Button quitButton;
    //red=1 blue=0
    int activePlayer=0;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    boolean gameActive=true;
    int [][] winningPosition={
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
    public void dropIn(View view)
    {
        ImageView counter=(ImageView)view;
        //System.out.println(counter.getTag().toString());
        int tag=Integer.parseInt(counter.getTag().toString());
        if(gameState[tag]==2 && gameActive) {
            gameState[tag]=activePlayer;
            counter.setTranslationY(-1000f);
            textView.setText("Player "+(activePlayer+1));

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.blue1);
                activePlayer = 1;
                textView.setText("Player "+(activePlayer+1));

            } else {
                counter.setImageResource(R.drawable.red2);
                activePlayer = 0;
                textView.setText("Player "+(activePlayer+1));

            }
            counter.animate().translationYBy(1000f).rotation(720).setDuration(500);
              for(int[] w:winningPosition)
              {
                  if(gameState[w[0]]==gameState[w[1]] && gameState[w[1]]==gameState[w[2]] && gameState[w[0]]!=2 && gameActive)
                  { String win;
                      if(gameState[w[0]]==0)
                      {
                          win="Blue";
                      }
                      else
                      {
                          win = "Red";
                      }
                      TextView winner=(TextView)findViewById(R.id.someoneWon);
                      winner.setText(win+" has Won!");

                      LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                      linearLayout.setVisibility(view.VISIBLE);
                      gameActive=false;
                  }
                  else
                  {
                      boolean gameOver=true;
                        for(int i:gameState)
                        {
                            if(i==2)
                                gameOver=false;
                        }
                        if(gameOver)
                        {
                            TextView winner=(TextView)findViewById(R.id.someoneWon);
                            winner.setText("Draw!");
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                            linearLayout.setVisibility(view.VISIBLE);
                            gameActive=false;
                        }
                  }
              }

        }

    }
    GridLayout gridLayout;
    public void playAgain(View view)
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        linearLayout.setVisibility(view.INVISIBLE);
        activePlayer=0;
        gameActive=true;
        for(int i=0;i<gameState.length;i++)
        { gameState[i]=2;}

        gridLayout=(androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);
        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);

        }
        textView.setText("Player "+(activePlayer+1));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();

        quitButton=findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                System.exit(0);
            }
        });

        textView=findViewById(R.id.playerText);
        textView.setText("Player 1");
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }
    }
}