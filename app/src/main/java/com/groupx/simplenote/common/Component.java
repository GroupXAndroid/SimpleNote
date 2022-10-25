package com.groupx.simplenote.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.groupx.simplenote.R;

public class Component {

    public int getColorFromColorChooser(View view,Context context) {
        int color = context.getColor(R.color.noteColorDefault);

        final ImageView imageNoteDetailBack, imageNoteDetailSave, imageNoteDetailColorOptionLens,
                imageViewCheckColor1, imageViewCheckColor2, imageViewCheckColor3, imageViewCheckColor4,
                imageViewCheckColor5, imageViewCheckColor6;

        imageViewCheckColor1 = view.getRootView().findViewById(R.id.imageCheckColor1);
        imageViewCheckColor2 = view.getRootView().findViewById(R.id.imageCheckColor2);
        imageViewCheckColor3 = view.getRootView().findViewById(R.id.imageCheckColor3);
        imageViewCheckColor4 = view.getRootView().findViewById(R.id.imageCheckColor4);
        imageViewCheckColor5 = view.getRootView().findViewById(R.id.imageCheckColor5);
        imageViewCheckColor6 = view.getRootView().findViewById(R.id.imageCheckColor6);

        switch (view.getId()) {
            case R.id.viewChooseColor1:
                color = context.getColor(R.color.noteColorDefault);
                imageViewCheckColor1.setImageResource(R.drawable.ic_check);
                imageViewCheckColor2.setImageResource(0);
                imageViewCheckColor3.setImageResource(0);
                imageViewCheckColor4.setImageResource(0);
                imageViewCheckColor5.setImageResource(0);
                imageViewCheckColor6.setImageResource(0);

                break;
            case R.id.viewChooseColor2:
                color = context.getColor(R.color.noteColor2);
                imageViewCheckColor1.setImageResource(0);
                imageViewCheckColor2.setImageResource(R.drawable.ic_check);
                imageViewCheckColor3.setImageResource(0);
                imageViewCheckColor4.setImageResource(0);
                imageViewCheckColor5.setImageResource(0);
                imageViewCheckColor6.setImageResource(0);

                break;
            case R.id.viewChooseColor3:
                color = context.getColor(R.color.noteColor3);
                imageViewCheckColor1.setImageResource(0);
                imageViewCheckColor2.setImageResource(0);
                imageViewCheckColor3.setImageResource(R.drawable.ic_check);
                imageViewCheckColor4.setImageResource(0);
                imageViewCheckColor5.setImageResource(0);
                imageViewCheckColor6.setImageResource(0);

                break;
            case R.id.viewChooseColor4:
                color = context.getColor(R.color.noteColor4);
                imageViewCheckColor1.setImageResource(0);
                imageViewCheckColor2.setImageResource(0);
                imageViewCheckColor3.setImageResource(0);
                imageViewCheckColor4.setImageResource(R.drawable.ic_check);
                imageViewCheckColor5.setImageResource(0);
                imageViewCheckColor6.setImageResource(0);

                break;
            case R.id.viewChooseColor5:
                color = context.getColor(R.color.noteColor5);
                imageViewCheckColor1.setImageResource(0);
                imageViewCheckColor2.setImageResource(0);
                imageViewCheckColor3.setImageResource(0);
                imageViewCheckColor4.setImageResource(0);
                imageViewCheckColor5.setImageResource(R.drawable.ic_check);
                imageViewCheckColor6.setImageResource(0);

                break;
            case R.id.viewChooseColor6:
                color = context.getColor(R.color.noteColor6);
                imageViewCheckColor1.setImageResource(0);
                imageViewCheckColor2.setImageResource(0);
                imageViewCheckColor3.setImageResource(0);
                imageViewCheckColor4.setImageResource(0);
                imageViewCheckColor5.setImageResource(0);
                imageViewCheckColor6.setImageResource(R.drawable.ic_check);
                break;
        }
        return color;
    }
}
