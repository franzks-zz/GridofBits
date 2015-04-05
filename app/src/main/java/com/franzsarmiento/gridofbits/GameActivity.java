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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;


public class GameActivity extends Activity {

    public final static String SELECTED_DIFFICULTY = "selected_difficulty";

    public final static int DIFFICULTY_EASY = 1;
    public final static int DIFFICULTY_MEDIUM = 2;
    public final static int DIFFICULTY_HARD = 3;

    public final static int[] SIZES = {4,6,8};

    private int mSelectedDifficulty;
    private int mGridSize;

    private boolean[][] mAnswer;
    private int[] mSumRow;
    private int[] mSumCol;
    private int mTotalNumOfCorrectOnes;
    private int mCurrentNumOfCorrectOnes;

    private GridLayout mGrid;
    private ToggleButton[][] mToggles;
    private TextView[] mTvAnswersRow;
    private TextView[] mTvAnswersCol;

    private TextView mTvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSelectedDifficulty = getIntent().getIntExtra(SELECTED_DIFFICULTY, DIFFICULTY_EASY);
        mGridSize = SIZES[mSelectedDifficulty - 1];
        generateAnswer();
        buildGrid();
        startTimer();
    }

    private void generateAnswer() {
        mAnswer = new boolean[mGridSize][mGridSize];
        mSumRow = new int[mGridSize];
        mSumCol = new int[mGridSize];
        mTotalNumOfCorrectOnes = 0;
        mCurrentNumOfCorrectOnes = 0;

        ArrayList<Integer[]> cells = new ArrayList<Integer[]>();

        for (int row = 0; row < mGridSize; row++) {
            for (int col = 0; col < mGridSize; col++) {
                Integer[] arr = {row, col};
                cells.add(arr);
            }
        }

        Random rand = new Random();

        for (int i = 0; i < mGridSize * mGridSize / 2; i++) {
            int index = rand.nextInt(cells.size());
            Integer[] cell = cells.get(index);
            cells.remove(index);

            mAnswer[cell[0]][cell[1]] = true;
            mSumRow[cell[0]] += Math.pow(2, mGridSize - cell[1] - 1);
            mSumCol[cell[1]] += Math.pow(2, mGridSize - cell[0] - 1);

            mTotalNumOfCorrectOnes += 1;
        }
    }

    private void buildGrid() {
        mGrid = (GridLayout) findViewById(R.id.grid);
        mGrid.setRowCount(mGridSize + 1);
        mGrid.setColumnCount(mGridSize + 1);

        mToggles = new ToggleButton[mGridSize][mGridSize];
        mTvAnswersRow = new TextView[mGridSize];
        mTvAnswersCol = new TextView[mGridSize];

        int lengthOfSides = getResources().getDisplayMetrics().widthPixels / (mGridSize + 1);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                lengthOfSides, lengthOfSides);

        for (int row = 0; row < mGridSize; row++) {
            for (int col = 0; col < mGridSize; col++) {
                ToggleButton toggle = new ToggleButton(this);
                toggle.setLayoutParams(layoutParams);
                toggle.setGravity(Gravity.FILL_HORIZONTAL);

                toggle.setTextOn("1");
                toggle.setTextOff("0");
                toggle.setText("0");

                final int final_row = row;
                final int final_col = col;

                toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        checkIfWon(final_row, final_col, checked);
                    }
                });

                mToggles[row][col] = toggle;
                mGrid.addView(toggle);
            }

            TextView textView = new TextView(this);
            textView.setText("" + mSumRow[row]);
            textView.setGravity(Gravity.FILL_HORIZONTAL);

            mTvAnswersRow[row] = textView;
            mGrid.addView(textView);
        }

        for (int col = 0; col < mGridSize; col++) {
            TextView textView = new TextView(this);
            textView.setText("" + mSumCol[col]);
            textView.setGravity(Gravity.FILL_HORIZONTAL);

            mTvAnswersCol[col] = textView;
            mGrid.addView(textView);
        }
    }

    private void checkIfWon(int row, int col, boolean checked) {
        if (checked == mAnswer[row][col]) {
            mCurrentNumOfCorrectOnes += 1;
        } else {
            mCurrentNumOfCorrectOnes -= 1;
        }

        if (mCurrentNumOfCorrectOnes == mTotalNumOfCorrectOnes) {
            showResultActivity();
        }
    }

    private void showResultActivity() {
        Intent intent = new Intent(this, GameResultActivity.class);
        intent.putExtra(SELECTED_DIFFICULTY, mSelectedDifficulty);
        intent.putExtra(GameResultActivity.TOTAL_TIME, System.currentTimeMillis() - mStartTime);
        startActivity(intent);
    }

    private long mStartTime;
    private long mPauseTime;

    private void startTimer() {
        mTvTimer = (TextView) findViewById(R.id.tvTimer);
        final Handler handler = new Handler();

        final Runnable timeUpdater = new Runnable() {
            @Override
            public void run() {
                mTvTimer.setText(GridOfBitsUtils.formatMillisToSeconds(
                        System.currentTimeMillis() - mStartTime));
                handler.postDelayed(this, 100);
            }
        };

        mStartTime = System.currentTimeMillis();
        mPauseTime = System.currentTimeMillis();
        handler.post(timeUpdater);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPauseTime = System.currentTimeMillis();
    }

    @Override
    public void onResume() {
        super.onResume();
        mStartTime += System.currentTimeMillis() - mPauseTime;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
