/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordstack;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final int WORD_LENGTH = 5;
    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private ArrayList<String> words = new ArrayList<>();
    private Random random = new Random();
    private StackedLayout stackedLayout;
    private String word1, word2;

    private Stack<LetterTile> placedTiles = new Stack<>();

    private LinearLayout word1LinearLayout;
    private LinearLayout word2LinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();

                if (word.length() == WORD_LENGTH){
                    words.add(word);
                }
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.vertical_layout);
        stackedLayout = new StackedLayout(this);
        verticalLayout.addView(stackedLayout, 3);

        word1LinearLayout = findViewById(R.id.word1);
        word1LinearLayout.setOnDragListener(new DragListener());
        //word1LinearLayout.setOnDragListener(new DragListener());
        word2LinearLayout = findViewById(R.id.word2);
        word2LinearLayout.setOnDragListener(new DragListener());
        //word2LinearLayout.setOnDragListener(new DragListener());
    }

    private class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !stackedLayout.empty()) {
                LetterTile tile = (LetterTile) stackedLayout.peek();
                tile.moveToViewGroup((ViewGroup) v);
                if (stackedLayout.empty()) {
                    TextView messageBox = (TextView) findViewById(R.id.message_box);
                    messageBox.setText(word1 + " " + word2);
                }

                placedTiles.push(tile);
                Log.i("test", "PTsize"+placedTiles.size());

                return true;
            }
            return false;
        }
    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign Tile to the target Layout
                    LetterTile tile = (LetterTile) event.getLocalState();
                    tile.moveToViewGroup((ViewGroup) v);
                    if (stackedLayout.empty()) {
                        TextView messageBox = (TextView) findViewById(R.id.message_box);
                        messageBox.setText(word1 + " " + word2);
                    }
                    placedTiles.push(tile);

                    return true;
            }
            return false;
        }
    }

    public boolean onStartGame(View view) {


        word1LinearLayout.removeAllViews();
        word2LinearLayout.removeAllViews();
        stackedLayout.clear();

//        TextView messageBox = (TextView) findViewById(R.id.message_box);
//        messageBox.setText("Game started");
//
//        word1 = words.get(random.nextInt(words.size()));
//        word2 = words.get(random.nextInt(words.size()));
//
//        while(word1.equals(word2)){
//            word2 = words.get(random.nextInt(words.size()));
//        }
//        Log.i("test", "word1: " + word1);
//        Log.i("test", "word2: " + word2);
//
//        int counter1 = 0;
//        int counter2 = 0;


//        int rand;

        // can also use String Builder
//        char[] temp = new char[word1.length() + word2.length()];    //10
//
//        for(int i = 0; i < temp.length; i++){
//
//            rand = random.nextInt(2); // can use nextBoolean
//            if (rand != 1 && counter1 < word1.length()) {
//                temp[i] = word1.charAt(counter1);
//                Log.i("test", "temp " + Arrays.toString(temp));
//                counter1++;
//            }
//            else if (counter2 < word2.length()){
//                temp[i] = word2.charAt(counter2);
//                Log.i("test", "temp "+ Arrays.toString(temp));
//                counter2++;
//            }
//        }

//        StringBuilder sb = new StringBuilder();
//        while(counter1 < word1.length() && counter2 < word2.length()){
//            if(random.nextBoolean()){
//                sb.append(word1.charAt(counter1));
//            }
//            else{
//                sb.append(word2.charAt(counter2));
//            }
//        }
//        Log.i("test", "ok???");
//        if(counter1 < word1.length()){
//            sb.append(word1.toCharArray(), counter1, word1.length()-counter1);
//        }
//        else{
//            sb.append(word2.toCharArray(), counter2, word2.length()-counter2);
//        }
//
//        messageBox.setText(sb.toString());
//        Log.i("test", "ok???");
//        for (int i = sb.length() -1; i >= 0; i--){
//            LetterTile tile = new LetterTile(this, sb.toString().charAt(i));
//            stackedLayout.push(tile);
//        }
//        return true;




        int word1Index = random.nextInt(words.size());
        int word2Index = random.nextInt(words.size());

        while (word1Index == word2Index) {
            word2Index = random.nextInt(words.size());
        }

        word1 = words.get(word1Index);
        word2 = words.get(word2Index);

        String scrambledWords = scramble(word1, word2);

        for (int i = scrambledWords.length()-1; i >= 0; i--) {
            stackedLayout.push(new LetterTile(this, scrambledWords.charAt(i)));
        }

        Log.i("test", word1 + " + " + word2 + " = " + scrambledWords);

        Log.i("test", "initial: "+stackedLayout.tiles.size());

        TextView messageBox = (TextView) findViewById(R.id.message_box);
        messageBox.setText(scrambledWords);

        return true;
    }

    public boolean onUndo(View view) {
        if(!placedTiles.empty()) {
            placedTiles.pop().moveToViewGroup(stackedLayout);
            Log.i("test", "here1" + placedTiles.size());
        }
        else {
           // placedTiles.peek().moveToViewGroup(stackedLayout);
            Log.i("test", "here2"+ placedTiles.size());
        }
        return true;
    }



    // Returns the scrambled version of w1 and w2, preserving letter order.
    private String scramble(String w1, String w2) {
        int i = 0;
        int j = 0;
        StringBuilder s = new StringBuilder();

        // In a loop:
        //   Randomly pick a word.
        //   Add the character from that word at its index to the StringBuilder.
        //   Increment that index.
        while (i < w1.length() && j < w2.length()) {
            if (random.nextBoolean()) {
                // Pick word1.
                s.append(word1.charAt(i++));
            } else {
                // Pick word2.
                s.append(word2.charAt(j++));
            }
        }

        // Add the remaining letters from the unexhausted word.
        if (i < w1.length()) {
            s.append(word1.toCharArray(), i, word1.length()-i);
        } else {
            s.append(word2.toCharArray(), j, word2.length()-j);
        }

        return s.toString();
    }
}
