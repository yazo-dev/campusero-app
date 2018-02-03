package com.frezarin.campusparty.Utils;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by macbook on 26/05/17.
 */

public class ToolbarHelper {

    public static void colorizeToolbar(Toolbar toolbarView, final int colorResource, final Activity activity) {
        final int Color = ContextCompat.getColor(activity, colorResource);
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color, PorterDuff.Mode.MULTIPLY);

        for (int i = 0; i < toolbarView.getChildCount(); i++) {
            final View v = toolbarView.getChildAt(i);

            //Step 1 : Changing the color of back button (or open drawer button).
            if (v instanceof ImageButton) {
                //Action Bar back button
                ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
            }


            if (v instanceof ActionMenuView) {
                for (int j = 0; j < ((ActionMenuView) v).getChildCount(); j++) {

                    //Step 2: Changing the color of any ActionMenuViews - icons that are not back button, nor text, nor overflow menu icon.
                    //Colorize the ActionViews -> all icons that are NOT: back button | overflow menu
                    final View innerView = ((ActionMenuView) v).getChildAt(j);

                    if (innerView instanceof ActionMenuItemView) {
                        final ActionMenuItemView menuView = (ActionMenuItemView) innerView;
                        for (int k = 0; k < menuView.getCompoundDrawables().length; k++) {
                            if (menuView.getCompoundDrawables()[k] != null) {

                                //Important to set the color filter in seperate thread, by adding it to the message queue
                                //Won't work otherwise.
                                innerView.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            menuView.setCompoundDrawableTintList(ContextCompat.getColorStateList(activity, colorResource));
                                        }
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                            menuView.getCompoundDrawables()[finalK].setTint(toolbarIconsColor);
//                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }

            //Step 3: Changing the color of title and subtitle.
            toolbarView.setTitleTextColor(Color);
            toolbarView.setSubtitleTextColor(Color);

            //Step 4: Changing the color of the Overflow Menu icon.
            //setOverflowButtonColor(activity, colorFilter);
        }

    }

//    private static void setOverflowButtonColor(final Activity activity, final PorterDuffColorFilter colorFilter) {
//        final String overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description);
//        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//        final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                final ArrayList<View> outViews = new ArrayList<>();
//                decorView.findViewsWithText(outViews, overflowDescription,
//                        View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
//                if (outViews.isEmpty()) {
//                    return;
//                }
//                ImageView overflow = (ImageView) outViews.get(0);
//                overflow.setColorFilter(colorFilter);
//                removeOnGlobalLayoutListener(decorView, this);
//            }
//        });
//    }
//
//    private static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
//        } else {
//            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
//        }
//    }

}
