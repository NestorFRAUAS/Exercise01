package com.example.roggenbuck.exercise01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private SharedPreferences sharedPreferences;
    private ConstraintLayout constraintLayout;
    private EditText editTextRed, editTextGreen, editTextBlue;
    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    private RadioGroup rgr;
    private RadioButton rdBgC, rdTc, rdBc,rdFABC;
    private Button btn;
    private TextView textView;
    private FloatingActionButton fab;
    private CheckBox checkBox;
    public static Snackbar snackbar;
    private Boolean checkboxChecked;
    private int red, green, blue;
    private int opRed, opGreen, opBlue;
    private int indexRadiogroup;
    private SensorManager sensorManager;
    private Sensor sensor;

    private String whichLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivityForResult(intent, 2);
                return false;
            }
        });




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

        // Initialize RadioGroups and -buttons.
        rgr = findViewById(R.id.radioGroup);
        rdBc = findViewById(R.id.radioBtnBC);
        rdBgC = findViewById(R.id.radioBtnBgC);
        rdTc = findViewById(R.id.radioBtnTC);
        rdFABC = findViewById(R.id.radioBtnFABC);

        // Initializing the CheckBox
        checkBox = findViewById(R.id.checkBox);



        fab = findViewById(R.id.floatingActionButton);
        // Handle the snackbar, which automatically dismisses,
        // if one is wiping to the right.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Information. Wipe to the right to dismiss...",
                        Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();
            }
        });

        // Initializing preferences
        loadPrefs();

        editTextRed.setText("" + red);
        editTextBlue.setText("" + blue);
        editTextGreen.setText("" + green);


        rgr.check(indexRadiogroup);
        seekBarRed.setProgress(red);
        seekBarGreen.setProgress(green);
        seekBarBlue.setProgress(blue);


        // Following lines of code are inspired by Andras Balázs Lajtha
        // https://stackoverflow.com/questions/16163215/android-styling-seek-bar
        seekBarRed.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBarRed.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBarGreen.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        seekBarGreen.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        seekBarBlue.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        seekBarBlue.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        String color = convertColorsToHex();
        String opColor = inverseColor(color);
        opColor = isAGrayColor(opColor);
        textView.setBackgroundColor(Color.parseColor(color));
        textView.setTextColor(Color.parseColor(opColor));


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
                        seekBarRed.setProgress(red);
                    }
                    else
                        {
                        seekBarRed.setProgress(red);
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
                        red = 255;
                        editTextRed.setText("" + red);
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
                        seekBarGreen.setProgress(green);
                    } else {
                        String color = convertColorsToHex();
                        String opColor = inverseColor(color);
                        textView.setBackgroundColor(Color.parseColor(color));
                        textView.setTextColor(Color.parseColor(opColor));
                        green = 255;
                        seekBarGreen.setProgress(green);

                    }

                }catch (Exception e)
                {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {


                    if (Integer.parseInt(editTextGreen.getText().toString()) > 255) {
                        green = 255;
                        editTextGreen.setText("" + green);
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
                        seekBarBlue.setProgress(blue);
                    } else {
                        blue = 255;
                        seekBarBlue.setProgress(blue);
                    }

                }catch (Exception e)
                {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {


                    if (Integer.parseInt(editTextBlue.getText().toString()) > 255) {
                        blue = 255;
                        editTextBlue.setText("" + blue);
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
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please select which element´s color you want to change",  Toast.LENGTH_SHORT);
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

    public void safePrefs()
    {
        sharedPreferences = getSharedPreferences("com.example.roggenbuck.exercise01",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // R/G/B values are stored.
        try
        {
            editor.putInt("red", Integer.parseInt(editTextRed.getText().toString()));
            editor.putInt("green", Integer.parseInt(editTextGreen.getText().toString()));
            editor.putInt("blue", Integer.parseInt(editTextBlue.getText().toString()));
            editor.putInt("indexRadiogroup", rgr.getCheckedRadioButtonId());
            editor.putString("whichLayout", whichLayout);
            // Saving CheckBox state
            editor.putBoolean("checkboxChecked", checkBox.isChecked());
            editor.apply();

        }
        catch(Exception e)
        {
            editor.putInt("red", 0);
            editor.putInt("green", 0);
            editor.putInt("blue", 0);
            editor.putString("whichLayout", "");
            editor.putInt("indexRadiogroup", 0);

            // Saving CheckBox state
            editor.putBoolean("checkboxChecked", false);
            editor.apply();
        }
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
            indexRadiogroup = sharedPreferences.getInt("indexRadiogroup", 0);
            whichLayout = sharedPreferences.getString("whichLayout", "");
            checkboxChecked = sharedPreferences.getBoolean("checkboxChecked", false);
            if(checkboxChecked)
            {
                checkBox.setChecked(true);
            }

            switch (whichLayout)
            {

                case "Background Color":
                    indexRadiogroup = R.id.radioBtnBgC;
                    break;
                case "Text Color":
                    indexRadiogroup = R.id.radioBtnTC;
                    break;
                case "Button Color":
                    indexRadiogroup = R.id.radioBtnBC;
                    break;
                case "Floating Action Button Color":
                    indexRadiogroup = R.id.radioBtnFABC;
                    break;
                case "":
                    break;
            }
        }
        catch (Exception e)
        {
            safePrefs();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sharedPreferences = getSharedPreferences("com.example.roggenbuck.exercise01",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        safePrefs();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2)
        {
            if (resultCode == 0) {
                Snackbar.make(constraintLayout, "About activity was closed using CANCEL.",
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if (resultCode == 1) {
                Snackbar.make(constraintLayout, "About activity was closed using OK.",
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        }
        }
    }
}
