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

/**
 * Wrapper class for all static utility methods used in more than one class.
 */
public class GridOfBitsUtils {

    public static String formatMillisToSeconds(long millis) {
        long deciseconds = millis / 100;
        String time = deciseconds / 10 + "." + deciseconds % 10 + " seconds";
        return time;
    }

    public static String getDifficultyInString(int difficulty) {
        switch (difficulty) {
            case GameActivity.DIFFICULTY_EASY:
                return "Easy";
            case GameActivity.DIFFICULTY_MEDIUM:
                return "Medium";
            case GameActivity.DIFFICULTY_HARD:
                return "Hard";
        }
        return null;
    }

}
