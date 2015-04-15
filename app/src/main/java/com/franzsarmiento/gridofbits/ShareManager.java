package com.franzsarmiento.gridofbits;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

/**
 * Created by User on 4/13/2015.
 */
public class ShareManager {
    private final String TAG = "ShareManager";

    // Share via Facebook
    protected void shareBitScoreFacebook(Activity activity, String shareFacebook) {
        ShareDialog shareDialog = new ShareDialog(activity);

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://goo.gl/FLkfJy"))
                    .setContentTitle(shareFacebook)
                    .build();

            shareDialog.show(linkContent);
        } else {
            Intent intentFacebook = new Intent(Intent.ACTION_VIEW);
            intentFacebook.setType("text/plain");
            intentFacebook.putExtra(android.content.Intent.EXTRA_TEXT, "Share via Facebook");

            List<ResolveInfo> matchFacebook = activity.getPackageManager().queryIntentActivities(intentFacebook, 0);
            for (ResolveInfo info : matchFacebook) {
                if ((info.activityInfo.name).contains("facebook")) {
                    intentFacebook.setPackage(info.activityInfo.packageName);
                }
            }
            activity.startActivity(intentFacebook);
        }
    }

    // Share via Twitter
    protected void shareBitScoreTwitter(Activity activity, String shareTweet) {
        // Create intent using ACTION_VIEW and a normal Twitter url:
        String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s", Utils.urlEncode(shareTweet), Utils.urlEncode(""));
        Intent intentTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

        // Narrow down to official Twitter app, if available:
        List<ResolveInfo> matchTwitter = activity.getPackageManager().queryIntentActivities(intentTwitter, 0);
        for (ResolveInfo info : matchTwitter) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intentTwitter.setPackage(info.activityInfo.packageName);
            }
        }
        activity.startActivity(intentTwitter);
    }

    // Share via Email srsly?
    protected void shareBitScoreEmail(Activity activity, String shareEmail) {
        Intent intentEmail = new Intent(Intent.ACTION_SEND);
        intentEmail.setType("message/rfc822");
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        intentEmail.putExtra(Intent.EXTRA_TEXT, shareEmail);
        try {
            activity.startActivity(Intent.createChooser(intentEmail, "Send Email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Log.e(TAG, "No email clients installed");
        }
    }
}
