/*
 * The MIT License(MIT)
 *
 * Copyright(c) 2015 Franz Sarmiento.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.franzsarmiento.gridofbits;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;


public class BestTimesActivity extends Activity {

    public final static String BEST_TIMES_SHARED_PREF =
            "com.franzsarmiento.gridofbits.best_times_shared_pref";

    public final static String PREF_KEY_HARD = "hard_best_times";
    public final static String PREF_KEY_MEDIUM = "medium_best_times";
    public final static String PREF_KEY_EASY = "easy_best_times";

    public final static String[] PREF_KEY_SUFFIXES = {"1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_times);

        SharedPreferences pref = getSharedPreferences(BEST_TIMES_SHARED_PREF, Context.MODE_PRIVATE);

        displayTimesOnTextViews(pref, PREF_KEY_HARD, new int[]{
                R.id.tvHard1st,
                R.id.tvHard2nd,
                R.id.tvHard3rd
        });
        displayTimesOnTextViews(pref, PREF_KEY_MEDIUM, new int[]{
                R.id.tvMedium1st,
                R.id.tvMedium2nd,
                R.id.tvMedium3rd
        });
        displayTimesOnTextViews(pref, PREF_KEY_EASY, new int[]{
                R.id.tvEasy1st,
                R.id.tvEasy2nd,
                R.id.tvEasy3rd
        });
    }

    private void displayTimesOnTextViews(SharedPreferences pref, String prefKey, int[] textViewIds) {
        for (int i = 0; i < textViewIds.length; i++) {
            TextView textView = (TextView) findViewById(textViewIds[i]);
            long time = pref.getLong(prefKey + PREF_KEY_SUFFIXES[i], 0);
            String formattedTime = (i + 1) + ") " + Utils.formatMillisToSeconds(time);

            if (time == 0) {
                formattedTime = (i + 1) + ") -";
            }

            textView.setText(formattedTime);
        }
    }

}
