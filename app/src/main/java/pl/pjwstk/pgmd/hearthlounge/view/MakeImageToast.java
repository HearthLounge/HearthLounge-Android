package pl.pjwstk.pgmd.hearthlounge.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.pjwstk.pgmd.hearthlounge.R;

/**
 * Created by Maciek Dembowski on 26.11.2017.
 */

public class MakeImageToast {

    @SuppressLint("ResourceAsColor")
    public static Toast makeImageToast(Context context, CharSequence text, int imageResId, int imageColor, int length) {
        Toast toast = Toast.makeText(context, text, length);

        View rootView = toast.getView();
        LinearLayout linearLayout = null;
        View messageTextView = null;

        // check (expected) toast layout
        if (rootView instanceof LinearLayout) {
            linearLayout = (LinearLayout) rootView;

            if (linearLayout.getChildCount() == 1) {
                View child = linearLayout.getChildAt(0);

                if (child instanceof TextView) {
                    messageTextView = (TextView) child;
                }
            }
        }

        // cancel modification because toast layout is not what we expected
        if (linearLayout == null || messageTextView == null) {
            return toast;
        }

        ViewGroup.LayoutParams textParams = messageTextView.getLayoutParams();
        ((LinearLayout.LayoutParams) textParams).gravity = Gravity.CENTER_VERTICAL;

        // convert dip dimension
        float density = context.getResources().getDisplayMetrics().density;
        int imageSize = (int) (density * 25 + 1f);
        int imageMargin = (int) (density * 15 + 0.5f);

        // setup image view layout parameters
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(imageSize, imageSize);
        imageParams.setMargins(0, 0, imageMargin, 0);
        imageParams.gravity = Gravity.CENTER_VERTICAL;

        // setup image view
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageResId); // przekazujemy wyglad ikonki
        imageView.setColorFilter(imageColor); // przekazujemy kolor ikonki
        imageView.setLayoutParams(imageParams);

        // modify root layout ZMIANA LOKOALIZACJI IKONY 0 - PRZED NAPISEM
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(imageView, 1);
        linearLayout.setBackgroundResource(R.drawable.toast_opacity); // usuwamy szary background toast

        return toast;
    }
}
