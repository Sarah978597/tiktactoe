package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
// j1 : 0, j2 : 1, vide : 2

    private TextView jscore1,jscore2,status;
    private Button [] buttons = new Button[9];
    private Button reset;
    private int j1,j2,compteur;
    boolean active;
    int [] statu = {2,2,2,2,2,2,2,2,2};
    int [] [] gagnant = {
            {0,1,2},  {3,4,5}, {6,7,8},// ligne
            {0,3,6},  {1,4,7}, {2,5,8}, //colomne
            {0,4,8}, {2,4,6} // croissement
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jscore1 = (TextView) findViewById(R.id.score1);
        jscore2 = (TextView) findViewById(R.id.score2);
        status = (TextView) findViewById(R.id.gagne);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeat();
            }
        });
        for(int i = 0; i < buttons.length; i++){
            String btnid = "btn"+i;
            int id = getResources().getIdentifier(btnid,"id",getPackageName());
            buttons[i]=(Button)findViewById(id);
            buttons[i].setOnClickListener(this);
        }
        compteur = 0; j1=0; j2=0;
        active = true;

    }// fin onCreate

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
       String btnid = v.getResources().getResourceEntryName(v.getId());
       int pointeur = Integer.parseInt(btnid.substring(btnid.length()-1, btnid.length()));
       if(active){
           ((Button)v).setText("X");
           ((Button)v).setTextColor(Color.parseColor("#FFFB0707"));
           statu[pointeur] = 0;
       }else {
           ((Button)v).setText("0");
           ((Button)v).setTextColor(Color.parseColor("#FF03A9F4"));
           statu[pointeur] = 1;

       }
        compteur++;
       if (checkvainceur()){
           if(active){
            j1++;
            update();
               Toast.makeText(this, "Le joeur numéro 1 a remportter la manche !!", Toast.LENGTH_SHORT).show();
               repeat();
           }else {
               j2++;
               update();
               Toast.makeText(this, "Le joeur numéro 2 a remportter la manche !!", Toast.LENGTH_SHORT).show();
               repeat();
           }

       }else if( compteur == 9){
           repeat();
           Toast.makeText(this, "Pas de gagnant!", Toast.LENGTH_SHORT).show();
       }else {
           active = !active;
       }
       if(j1 ==0 && j2 == 0){
           status.setText("");
       }else if(j1<j2){
           status.setText("Joeur num 2 est vainceur");
       } else if(j2<j1){
           status.setText("Joeur num 1 est vainceur");
       }else if(j2 == j1){
           status.setText("Exéco");
       }

    }// fin oClick
     public boolean checkvainceur(){
        boolean resultat = false;
        for(int [] vainposition : gagnant){
            if(statu[vainposition[0]] == statu[vainposition[1]] && statu[vainposition[1]] == statu[vainposition[2]] && statu[vainposition[0]] != 2 ){
                resultat = true;
            }
        }// fin for

        return resultat;
    }// fin ckeckvainceur

    public void  update (){
        jscore1.setText(Integer.toString(j1));
        jscore2.setText(Integer.toString(j2));

    }// fin update

    public void repeat(){
         compteur =0;
        active = true;
        for(int i = 0; i < buttons.length; i++){
            statu[i] = 2;
            buttons[i].setText("");

        }

    }
}