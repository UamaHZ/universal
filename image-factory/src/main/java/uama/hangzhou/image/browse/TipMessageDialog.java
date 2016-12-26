package uama.hangzhou.image.browse;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import uama.hangzhou.image.R;

public class TipMessageDialog {
    /*
     *底部的dialog
     */
    public static android.app.Dialog showSaveMessage(Context context, final OnItemClickListener menuDialogClickListener) {
        return ShowBottomMenuDialog(context, menuDialogClickListener);
    }

    private static android.app.Dialog ShowBottomMenuDialog(Context context, final OnItemClickListener itemClickListener) {
        final android.app.Dialog dialog = new android.app.Dialog(context, R.style.DialogStyle);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_menu, null);
        dialog.setContentView(view);
        TextView savePicture = (TextView) view.findViewById(R.id.pop_save_tv);
        savePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick();
                dialog.dismiss();
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            lp.width = getDisplayHeight(context) / 2;
        } else {
            lp.width = getDisplayWidth(context);
        }
        mWindow.setAttributes(lp);
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.dialog_bottom_in);
        dialog.show();
        return dialog;
    }

    public static int getDisplayHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    public static int getDisplayWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}
