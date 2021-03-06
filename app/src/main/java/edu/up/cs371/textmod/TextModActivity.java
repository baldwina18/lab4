package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import java.util.ArrayList;

public class TextModActivity extends ActionBarActivity implements View.OnClickListener {

    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image

    protected Button upperCaseButton;
    protected Button lowerCaseButton;
    protected EditText editText;
    protected Button copyNameButton;
    protected Spinner spinner;
    protected Button reverse;
    protected Button clear;
    protected Button ClearSpaceButton;
    protected Button altCaseButton;

    protected Button moveChars;
    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);

        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);

        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        spinner = (Spinner)findViewById(R.id.spinner);
        // get array of strings
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());

        upperCaseButton = (Button) findViewById(R.id.button6);
        upperCaseButton.setOnClickListener(this);

        lowerCaseButton = (Button) findViewById(R.id.button7);
        lowerCaseButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
        copyNameButton = (Button)findViewById(R.id.button2);
        copyNameButton.setOnClickListener(this);
        reverse = (Button)findViewById(R.id.button4);
        reverse.setOnClickListener(this);
        clear = (Button)findViewById(R.id.button);
        clear.setOnClickListener(this);

        ClearSpaceButton = (Button)findViewById(R.id.ClearSpaceButton);
        ClearSpaceButton.setOnClickListener(this);

        altCaseButton = (Button) findViewById(R.id.altCaseButton);
        altCaseButton.setOnClickListener(this);

        moveChars = (Button)findViewById(R.id.moveChars);
        moveChars.setOnClickListener(this);
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
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

    @Override
    public void onClick(View v) {
        String text;
        String spinnerText;
        if(v.getId() == R.id.button6) {

            editText.setText(editText.getText().toString().toUpperCase());

        } else if (v.getId() == R.id.button7) {

            editText.setText(editText.getText().toString().toLowerCase());

        } else if(v.getId() == R.id.button2) {
            text = this.editText.getText().toString();
            spinnerText = spinner.getSelectedItem().toString();
            editText.setText(text+spinnerText);
        } else if (v.getId() == R.id.button4) {
            String curText = editText.getText().toString();
            String rev = "";
            int len = curText.length();
            for (int i=0; i<len; i++) {
                rev = curText.charAt(i)+rev;
            }
            editText.setText(rev);
        } else if (v.getId() == R.id.button) {
            editText.setText("");
        }
        else if(v.getId() == R.id.ClearSpaceButton)
        {
            String curText = editText.getText().toString();
            text = curText.replace(" ", "");
            editText.setText(text);
        } else if (v.getId() == R.id.altCaseButton) {
            for(int i =0; i < editText.getText().toString().length(); i++) {
                if (i %2 == 0) {
                    editText.setText(editText.getText().toString().substring(0,i) +
                            Character.toUpperCase(editText.getText().toString().charAt(i)) +
                            editText.getText().toString().substring(i+1));
                } else {
                    editText.setText(editText.getText().toString().substring(0,i) +
                            Character.toLowerCase(editText.getText().toString().charAt(i)) +
                            editText.getText().toString().substring(i+1));
                }
            }
        }else if (v.getId() == R.id.moveChars) {
            String curText = editText.getText().toString();
            int lenvar = curText.length()-2;
            int randi = (int) (Math.random() * lenvar) +1;
            String moved = curText.substring(randi) + curText.substring(0, randi);
            editText.setText(moved);
        }
    }

    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {

        }
    }
}
