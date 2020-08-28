package com.mx3.thirdwayvsimplecalculator.ui.databinding;

import android.content.res.ColorStateList;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.button.MaterialButton;
import com.mx3.thirdwayvsimplecalculator.R;

public class DataBindingAdapters {

    @BindingAdapter("shown")
    public static void showOrInvisible(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("buttonEnabled")
    public static void setEnabled(MaterialButton button, boolean enable) {
        button.setEnabled(enable);
    }

    // TODO adjust disabled style
    @BindingAdapter("buttonEnabled")
    public static void setEnabled(AppCompatImageButton imageButton, boolean enable) {
        imageButton.setEnabled(enable);
        imageButton.setClickable(enable);

        if (enable) {
            ImageViewCompat.setImageTintList(imageButton,
                    ColorStateList.valueOf(ContextCompat.getColor(imageButton.getContext(), R.color.colorPrimaryDark)));
        } else {
            ImageViewCompat.setImageTintList(imageButton,
                    ColorStateList.valueOf(ContextCompat.getColor(imageButton.getContext(), android.R.color.darker_gray)));
        }
    }
}
