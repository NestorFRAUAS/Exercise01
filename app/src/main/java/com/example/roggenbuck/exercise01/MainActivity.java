package com.example.roggenbuck.exercise01;

import android.app.IntentService;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private ConstraintLayout constraintLayout;
    private EditText editTextRed, editTextGreen, editTextBlue;
    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    private RadioGroup rgr;
    private RadioButton rdBgC, rdTc, rdBc,rdFABC;
    private Button btn;
    private TextView textView;
    private FloatingActionButton fab;
    private CheckBox checkBox;
    private int editTextRedNum, editTextGreenNum, editTextBlueNum;
    private int red, green, blue;
    private int opRed, opGreen, opBlue;
    private String whichLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        whichLayout = null;

        // Initializing the layout
        constraintLayout = findViewById(R.id.constraintLayout);

        // Initializing objects for RGB preferences.
        // First the EditTexts, because they come fist in layout.
        editTextRed = (EditText) findViewById(R.id.editTextRed);
        editTextGreen = (EditText) findViewById(R.id.editTextGreen);
        editTextBlue = (EditText) findViewById(R.id.editTextBlue);

        // Initializing TextView for Color Preview
        textView = findViewById(R.id.textViewColorPrev);

        // Initializing SeekBars.
        seekBarRed = (SeekBar) findViewById(R.id.seekBarRed);
        seekBarGreen = (SeekBar) findViewById(R.id.seekBarGreen);
        seekBarBlue = (SeekBar) findViewById(R.id.seekBarBlue);

        // Initializing the CheckBox
        checkBox = findViewById(R.id.checkBox);

        // TODO Shared Preferences
        editTextRed.setText("0");
        editTextBlue.setText("0");
        editTextGreen.setText("0");

        fab = findViewById(R.id.floatingActionButton);

        editTextRed.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }


            /*
            What happens, if text text is changed. EditText and Seekbar of the same
            color need to be synchronized.
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                try
                {
                    if (Integer.parseInt(editTextRed.getText().toString()) >= 0
                            && Integer.parseInt(editTextRed.getText().toString()) < 256)
                    {
                        String color = convertColorsToHex();
                        String opColor = inverseColor(color);
                        opColor = isAGrayColor(opColor);
                        textView.setBackgroundColor(Color.parseColor(color));
                        textView.setTextColor(Color.parseColor(opColor));
                        editTextRedNum = Integer.parseInt(editTextRed.getText().toString());
                        seekBarRed.setProgress(editTextRedNum);
                    }
                    else
                        {
                        seekBarRed.setProgress(editTextRedNum);
                        }

                }catch (Exception e)
                {

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                try {
                    if (Integer.parseInt(editTextRed.getText().toString()) > 255) {
                        editTextRedNum = 255;
                        editTextRed.setText("" + editTextRedNum);
                    }
                    String color = convertColorsToHex();
                    String opColor = inverseColor(color);
                    opColor = isAGrayColor(opColor);
                    textView.setBackgroundColor(Color.parseColor(color));
                    textView.setTextColor(Color.parseColor(opColor));

                    editTextRed.setSelection(editTextRed.length());
                } catch (Exception e)
                {

                }
            }
        });

        editTextGreen.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                try {

                    if (Integer.parseInt(editTextGreen.getText().toString()) >= 0
                            && Integer.parseInt(editTextGreen.getText().toString()) < 256) {
                        String color = convertColorsToHex();
                        String opColor = inverseColor(color);
                        opColor = isAGrayColor(opColor);
                        textView.setBackgroundColor(Color.parseColor(color));
                        textView.setTextColor(Color.parseColor(opColor));
                        editTextGreenNum = Integer.parseInt(editTextGreen.getText().toString());
                        seekBarGreen.setProgress(editTextGreenNum);
                    } else {
                        String color = convertColorsToHex();
                        String opColor = inverseColor(color);
                        textView.setBackgroundColor(Color.parseColor(color));
                        textView.setTextColor(Color.parseColor(opColor));
                        editTextGreenNum = 255;
                        seekBarGreen.setProgress(editTextGreenNum);

                    }

                }catch (Exception e)
                {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {


                    if (Integer.parseInt(editTextGreen.getText().toString()) > 255) {
                        editTextGreenNum = 255;
                        editTextGreen.setText("" + editTextGreenNum);
                    }
                    String color = convertColorsToHex();
                    String opColor = inverseColor(color);
                    opColor = isAGrayColor(opColor);
                    textView.setBackgroundColor(Color.parseColor(color));
                    textView.setTextColor(Color.parseColor(opColor));
                    editTextGreen.setSelection(editTextGreen.length());
                } catch (Exception e) {

                }
            }
        });

        editTextBlue.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (Integer.parseInt(editTextBlue.getText().toString()) >= 0
                            && Integer.parseInt(editTextBlue.getText().toString()) < 256) {
                        String color = convertColorsToHex();
                        String opColor = inverseColor(color);
                        opColor = isAGrayColor(opColor);
                        textView.setBackgroundColor(Color.parseColor(color));
                        textView.setTextColor(Color.parseColor(opColor));
                        editTextBlueNum = Integer.parseInt(editTextBlue.getText().toString());
                        seekBarBlue.setProgress(editTextBlueNum);
                    } else {
                        editTextBlueNum = 255;
                        seekBarBlue.setProgress(editTextBlueNum);
                    }

                }catch (Exception e)
                {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {


                    if (Integer.parseInt(editTextBlue.getText().toString()) > 255) {
                        editTextBlueNum = 255;
                        editTextBlue.setText("" + editTextBlueNum);
                    }
                    String color = convertColorsToHex();
                    String opColor = inverseColor(color);
                    opColor = isAGrayColor(opColor);
                    textView.setBackgroundColor(Color.parseColor(color));
                    textView.setTextColor(Color.parseColor(opColor));
                    editTextBlue.setSelection(editTextBlue.length());
                }catch (Exception e) {

                }

            }
        });

        // Handling SeekBars
        // If you change it, the value shall appear in the corresponding EditText View
        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    editTextRed.setText("" + i);
                    String color = convertColorsToHex();
                    String opColor = inverseColor(color);
                    opColor = isAGrayColor(opColor);
                    textView.setBackgroundColor(Color.parseColor(color));
                    textView.setTextColor(Color.parseColor(opColor));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    editTextGreen.setText("" + i);
                    String color = convertColorsToHex();
                    String opColor = inverseColor(color);
                    opColor = isAGrayColor(opColor);
                    textView.setBackgroundColor(Color.parseColor(color));
                    textView.setTextColor(Color.parseColor(opColor));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    editTextBlue.setText("" + i);
                    String color = convertColorsToHex();
                    String opColor = inverseColor(color);
                    opColor = isAGrayColor(opColor);
                    textView.setBackgroundColor(Color.parseColor(color));
                    textView.setTextColor(Color.parseColor(opColor));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Handling RadioGroup / RadioButtons
        rgr = findViewById(R.id.radioGroup);

        rdBc = findViewById(R.id.radioBtnBC);
        rdBgC = findViewById(R.id.radioBtnBgC);
        rdTc = findViewById(R.id.radioBtnTC);
        rdFABC = findViewById(R.id.radioBtnFABC);

        rgr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioBtnBgC:
                        whichLayout = getString(R.string.radioBtnBgC);
                        break;
                    case R.id.radioBtnTC:
                        whichLayout = getString(R.string.radioBtnTC);
                        break;
                    case R.id.radioBtnBC:
                        whichLayout = getString(R.string.radioBtnBC);
                        break;
                    case R.id.radioBtnFABC:
                        whichLayout = getString(R.string.radioBtnFABC);
                }

            }
        });

        btn = findViewById(R.id.button);
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

    public void setColor(View view) {
        String color = convertColorsToHex();
        try {
            switch (whichLayout) {
                case "Background Color":
                    constraintLayout.setBackgroundColor(Color.parseColor(color));
                    break;
                case "Text Color":
                    String opColor = inverseColor(color);
                    rdFABC.setTextColor(Color.parseColor(color));
                    rdTc.setTextColor(Color.parseColor(color));
                    rdBgC.setTextColor(Color.parseColor(color));
                    rdBc.setTextColor(Color.parseColor(color));
                    checkBox.setTextColor(Color.parseColor(color));
                    break;
                case "Button Color":
                    btn.setBackgroundColor(Color.parseColor(color));
                    break;
                case "Floating Action Button Color":
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

                    break;
            }
        }catch (Exception e)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Please select which element´s color you want to change",  Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /*
    I want to use HTML color codes as a string. This is the way,
    one would use it in XML-files.
     */
    public String convertColorsToHex(){
        String hexRed, hexGreen, hexBlue, hexColorCode;
        red = Integer.parseInt(editTextRed.getText().toString());
        green = Integer.parseInt(editTextGreen.getText().toString());
        blue = Integer.parseInt(editTextBlue.getText().toString());
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
        hexColorCode = "#" + hexRed + hexGreen + hexBlue;
        return hexColorCode;
    }

    /*
    Calculating the opposite colors. These are the additive inverse numbers.
    Subtracting both, must result in the neutral element (0).
     */
    public String inverseColor(String originColor){
        String hexRed, hexGreen, hexBlue, hexColorCode;
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
