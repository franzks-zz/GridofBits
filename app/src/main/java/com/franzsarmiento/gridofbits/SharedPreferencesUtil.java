package com.franzsarmiento.gridofbits;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Singleton for accessing SharedPreferences.
 */
public class SharedPreferencesUtil {

    private static final String KEY_SOUND_EFFECTS = "soundEffects";

    private static final boolean DEFAULT_SOUND_EFFECTS = true;

    private SharedPreferences sharedPreferences;

    private static SharedPreferencesUtil instance = null;

    /**
     * Get an instance of the SharedPreferencesUtil.
     * @param context the Context which will be used to instantiate the SharedPreferencesUtil.
     * @return if an instance of the SharedPreferencesUtil exists, that instance will be returned
     * . Otherwise, a new instance will be created.
     */
    public static SharedPreferencesUtil getInstance(final Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtil(context);
        }
        return instance;
    }

    private SharedPreferencesUtil(final Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Sets whether sound effects are enabled or disabled.
     * @param enabled <code>true</code> if sound effects should be played, <code>false</code> if sound
     * effects should not be played.
     */
    public void setSoundEffectsEnabled(final boolean enabled) {
        this.sharedPreferences.edit().putBoolean(KEY_SOUND_EFFECTS, enabled).commit();
    }

    /**
     * Finds out if sound effects are enabled or disabled.
     * @return <code>true</code> if sound effects should be played, <code>false</code> if sound
     * effects should not be played.
     */
    public boolean isSoundEffectsEnabled() {
        return this.sharedPreferences.getBoolean(KEY_SOUND_EFFECTS, DEFAULT_SOUND_EFFECTS);
    }
}
