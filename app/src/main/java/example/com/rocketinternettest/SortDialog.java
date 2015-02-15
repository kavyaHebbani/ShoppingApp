package example.com.rocketinternettest;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

/**
 * Created by Kavya Shree, 14-02-2015.
 */
public class SortDialog extends DialogFragment {
    SortDialogClosed listener;

    public SortDialog() {
    }

    public void setListener(SortDialogClosed listener) {
        this.listener = listener;
    }

    void showDialog(final Context context) {
        AlertDialog levelDialog;

        final CharSequence[] items = {"Price Asc", "Price Desc", "Name Asc", "Name Desc"};

        SharedPreferences sharedPreferences = context.getSharedPreferences("RIAndroidTest", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sort By");
        builder.setSingleChoiceItems(items, sharedPreferences.getInt("sortChoice", 0), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        editor.putInt("sortChoice", 0);
                        editor.apply();
                        break;
                    case 1:
                        editor.putInt("sortChoice", 1);
                        editor.apply();
                        break;
                    case 2:
                        editor.putInt("sortChoice", 2);
                        editor.apply();
                        break;
                    case 3:
                        editor.putInt("sortChoice", 3);
                        editor.apply();
                        break;
                }
                listener.onSortDialogClosed();
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    interface SortDialogClosed {
        void onSortDialogClosed();
    }
}