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
import android.view.View;
import android.widget.Button;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initaliseSoundEffectButton();
    }

    private void initaliseSoundEffectButton() {
        final SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this);
        final boolean soundEffectsEnabled = sharedPreferencesUtil.isSoundEffectsEnabled();
        final Button soundEffectsButton = (Button) findViewById(R.id.btnSoundEffects);
        final int buttonText =
                soundEffectsEnabled ? R.string.sound_effects_on : R.string.sound_effects_off;
        soundEffectsButton.setText(buttonText);
    }

    public void btnPlayEasyOnClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_EASY);
        startActivity(intent);
    }

    public void btnPlayMediumOnClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_MEDIUM);
        startActivity(intent);
    }

    public void btnPlayHardOnClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.SELECTED_DIFFICULTY, GameActivity.DIFFICULTY_HARD);
        startActivity(intent);
    }

    public void btnBestTimesOnClick(View view) {
        startActivity(new Intent(this, BestTimesActivity.class));
    }

    public void btnHowToPlayOnClick(View view) {
        HowToPlayDialogFragment dialogFragment = new HowToPlayDialogFragment();
        dialogFragment.show(getFragmentManager(),
                getResources().getString(R.string.how_to_play));
    }

    /**
     * Fired when the Sound Effects button is clicked.
     * @param view the clicked View.
     */
    public void btnSoundEffectsOnClick(final View view) {
        final SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this);
        // Toggle the previously set value.
        final boolean soundEffectsEnabled = !sharedPreferencesUtil.isSoundEffectsEnabled();
        sharedPreferencesUtil.setSoundEffectsEnabled(soundEffectsEnabled);
        // Reinitialise the Sound Effects Button so that its text reflects the currently saved
        // value.
        initaliseSoundEffectButton();
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
