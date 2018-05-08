package com.example.roggenbuck.exercise01;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private int red, green, blue, opRed, opGreen, opBlue;
    private String hexRed;
    private String hexGreen;
    private String hexBlue;
    private String color;
    private ConstraintLayout consLayAbAc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadPrefs();
        TextView textViewHexColor = findViewById(R.id.textViewHexColor);
        color = convertColorsToHex();
        textViewHexColor.setBackgroundColor(Color.parseColor(color));
        String opColor = inverseColor(color);
        opColor = isAGrayColor(opColor);
        textViewHexColor.setTextColor(Color.parseColor(opColor));
        textViewHexColor.append(convertDecToHex());
        consLayAbAc = findViewById(R.id.consLayAbAc);

    }

    public void loadPrefs()
    {
        try
        {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.roggenbuck.exercise01",
                    Context.MODE_PRIVATE);
            red = sharedPreferences.getInt("red", 0);
            green = sharedPreferences.getInt("green", 0);
            blue = sharedPreferences.getInt("blue", 0);

        }
        catch (Exception e)
        {
        }
    }

    public String convertColorsToHex(){
        hexRed = Integer.toHexString(red);
        hexGreen = Integer.toHexString(green);
        hexBlue = Integer.toHexString(blue);
        // Fascinating, that comparison between 'hex' and 'dec' works.
        // RGB values are always represented with 2 Bits.
        if(red<16){
            hexRed = "0" + hexRed;
        }
        if(green<16){
            hexGreen = "0" + hexGreen;
        }
        if(blue<16){
            hexBlue = "0" + hexBlue;
        }
        color = "#" + hexRed + hexGreen + hexBlue;
        return color;
    }

    public String inverseColor(String originColor){
        opRed = 255 - red;
        opGreen = 255 - green;
        opBlue = 255 - blue;

        hexRed = Integer.toHexString(opRed);
        hexGreen = Integer.toHexString(opGreen);
        hexBlue = Integer.toHexString(opBlue);
        if(opRed<16){
            hexRed = "0" + hexRed;
        }
        if(opGreen<16){
            hexGreen = "0" + hexGreen;
        }
        if(opBlue<16){
            hexBlue = "0" + hexBlue;
        }
        String hexColorCode = "#" + hexRed + hexGreen + hexBlue;
        return hexColorCode;
    }

    /*
    This method is used, because the opposite color of gray,
    is also gray. The text 'Color Preview' must be
    readable every time.
     */
    public String isAGrayColor(String color)
    {
        if(opRed == opBlue && opBlue == opGreen
                && opRed > 59 && opRed < 159)
        {
            return "#FFFFFF";
        }
        if(opRed == opBlue && opBlue == opGreen
                && opRed > 159 && opRed < 255)
        {
            return "#000000";
        }
        return color;
    }

    public String convertDecToHex()
    {
        String hexVersion = "";
        if(red < 16)
        {
            hexVersion = "(" + 0  + Integer.toHexString(red);
        }
        else
        {
            hexVersion += "(" + Integer.toHexString(red);
        }
        hexVersion += ",";

        if(green < 16)
        {
            hexVersion = 0 + "" + Integer.toHexString(green);
        }
        else
        {
            hexVersion +=  Integer.toHexString(green);
        }
        hexVersion += ",";
        if(blue < 16)
        {
            hexVersion += 0 + "" + Integer.toHexString(blue);
        }
        else
        {
            hexVersion += Integer.toHexString(blue) + ")";
        }
        return hexVersion;
    }

    public void okButton(View view) {
        setResult(1);
        finish();

    }

    public void cancelButton(View view) {
        setResult(0);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Snackbar.make(consLayAbAc, "Close by pressing one of the buttons!",
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}

//TODO Do not forget, to find an other attribute than LENGTH_LONG for the other SnackBars
// Add logging to the App