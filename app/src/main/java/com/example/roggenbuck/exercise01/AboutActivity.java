package com.example.roggenbuck.exercise01;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {
    private int red, green, blue, opRed, opGreen, opBlue;
    private SharedPreferences sharedPreferences;
    private TextView textViewHexColor;
    private String hexRed, hexGreen, hexBlue,
            hexColorCode, color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        loadPrefs();
        textViewHexColor = findViewById(R.id.textViewHexColor);
        color = convertColorsToHex();
        textViewHexColor.setBackgroundColor(Color.parseColor(color));
        String opColor = inverseColor(color);
        opColor = isAGrayColor(opColor);
        // TODO
        textViewHexColor.setTextColor(Color.parseColor(opColor));
        textViewHexColor.setText("The selected color value is (R,G,B) = (");

    }

    public void loadPrefs()
    {
        try
        {
            sharedPreferences = getSharedPreferences("com.example.roggenbuck.exercise01",
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
        hexColorCode = "#" + hexRed + hexGreen + hexBlue;
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

}
