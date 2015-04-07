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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class HomeActivity extends Activity {

    private RadioGroup mRgDifficulty;

    private SharedPreferences mSharedPref;
    private final static String LAST_SELECTED_DIFFICULTY_SHARED_PREF =
            "com.franzsarmiento.gridofbits.last_selected_difficulty_shared_pref";
    private final static String LAST_SELECTED_DIFFICULTY = "last_selected_difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRgDifficulty = (RadioGroup) findViewById(R.id.rgDifficulty);

        mSharedPref = getSharedPreferences(
                LAST_SELECTED_DIFFICULTY_SHARED_PREF, Context.MODE_PRIVATE);
        switch(mSharedPref.getInt(LAST_SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_EASY)) {
            case GameActivity.DIFFICULTY_EASY:
                ((RadioButton)findViewById(R.id.rbEasy)).setChecked(true);
                break;
            case GameActivity.DIFFICULTY_MEDIUM:
                ((RadioButton)findViewById(R.id.rbMedium)).setChecked(true);
                break;
            case GameActivity.DIFFICULTY_HARD:
                ((RadioButton)findViewById(R.id.rbHard)).setChecked(true);
                break;
        }
    }

    public void btnPlayOnClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        SharedPreferences.Editor editor = mSharedPref.edit();

        switch (mRgDifficulty.getCheckedRadioButtonId()) {
            case R.id.rbEasy:
                intent.putExtra(GameActivity.SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_EASY);
                editor.putInt(LAST_SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_EASY);
                break;
            case R.id.rbMedium:
                intent.putExtra(GameActivity.SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_MEDIUM);
                editor.putInt(LAST_SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_MEDIUM);
                break;
            case R.id.rbHard:
                intent.putExtra(GameActivity.SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_HARD);
                editor.putInt(LAST_SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_HARD);
                break;
        }

        editor.commit();
        startActivity(intent);
    }

    public void btnBestTimesOnClick(View view) {
        startActivity(new Intent(this, BestTimesActivity.class));
    }

    // Return to home screen
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
