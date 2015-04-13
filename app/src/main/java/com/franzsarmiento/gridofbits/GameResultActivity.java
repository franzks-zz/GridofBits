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
import android.widget.TextView;

import com.facebook.share.widget.ShareDialog;

public class GameResultActivity extends Activity {

    private ShareDialog mShareDialog;

    public final static String TOTAL_TIME = "total_time";

    private int mSelectedDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        mSelectedDifficulty = getIntent().getIntExtra(
                GameActivity.SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_EASY);
        long totalTime = getIntent().getLongExtra(TOTAL_TIME, 0);

        TextView tvYourTimeDifficulty = (TextView) findViewById(R.id.tvYourTimeDifficulty);
        TextView tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        TextView tvNewBestTime = (TextView) findViewById(R.id.tvNewBestTime);

        tvYourTimeDifficulty.setText("Your total time for this " +
                Utils.getDifficultyInString(mSelectedDifficulty) + " round is:");
        tvTotalTime.setText(Utils.formatMillisToSeconds(totalTime));

        // Create Facebook ShareDialog instance
        mShareDialog = new ShareDialog(this);

        if (!hasBeatenPreviousBestTimes(totalTime)) {
            tvNewBestTime.setVisibility(View.INVISIBLE);
        }
    }

    private boolean hasBeatenPreviousBestTimes(long totalTime) {
        String prefKey = BestTimesActivity.PREF_KEY_EASY;
        switch (mSelectedDifficulty) {
            case GameActivity.DIFFICULTY_MEDIUM:
                prefKey = BestTimesActivity.PREF_KEY_MEDIUM;
                break;
            case GameActivity.DIFFICULTY_HARD:
                prefKey = BestTimesActivity.PREF_KEY_HARD;
                break;
        }

        SharedPreferences pref = getSharedPreferences(
                BestTimesActivity.BEST_TIMES_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        long swap = Long.MAX_VALUE;
        boolean inserted = false;
        for (int i = 0; i < BestTimesActivity.PREF_KEY_SUFFIXES.length; i++) {
            String key = prefKey + BestTimesActivity.PREF_KEY_SUFFIXES[i];

            long prevTime = pref.getLong(key, Long.MAX_VALUE);

            if (!inserted) {
                if (totalTime <= prevTime) {
                    editor.putLong(key, totalTime);
                    swap = prevTime;
                    inserted = true;
                }
            } else {
                if (swap == Long.MAX_VALUE)
                    break;
                editor.putLong(key, swap);
                swap = prevTime;
            }
        }
        // editor.commit() --> editor.apply()
        // apply() handles the task in background
        editor.apply();

        return inserted;
    }

    public void btnPlayAgainOnClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.SELECTED_DIFFICULTY, mSelectedDifficulty);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
