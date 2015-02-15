package example.com.rocketinternettest;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;


/**
 * Created by Kavya Shree, 14-02-2015.
 */
public class FilterDialog extends DialogFragment {
    public final static int leftValueDefault = 1;
    public final static int rightValueDefault = 499;
    FilterDialogClosed listener;

    public FilterDialog() {
    }

    public void setListener(FilterDialogClosed listener) {
        this.listener = listener;
    }

    void showDialog(final Context context, LayoutInflater layoutInflater) {
        AlertDialog levelDialog;

        SharedPreferences sharedPreferences = context.getSharedPreferences("RIAndroidTest", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        View view = layoutInflater.inflate(R.layout.filter, null);
        final RangeBar rangebar = (RangeBar) view.findViewById(R.id.rangebar);
        final TextView leftValue = (TextView) view.findViewById(R.id.rangeLeft);
        final TextView rightValue = (TextView) view.findViewById(R.id.rangeRight);

        rangebar.setTickCount(rightValueDefault);
        rangebar.setTickHeight(25);
        rangebar.setBarColor(229999999);
        rangebar.setLeft(leftValueDefault);
        rangebar.setRight(rightValueDefault);
        leftValue.setText(String.valueOf(leftValueDefault));
        rightValue.setText(String.valueOf(rightValueDefault));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Price Range");
        builder.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (rangebar.getLeftIndex() < leftValueDefault || rangebar.getLeftIndex() > rightValueDefault) {
                    editor.putInt("filterLeft", leftValueDefault);
                } else {
                    editor.putInt("filterLeft", rangebar.getLeftIndex());
                }

                if (rangebar.getRightIndex() < leftValueDefault || rangebar.getRightIndex() > rightValueDefault) {
                    editor.putInt("filterRight", rightValueDefault);
                } else {
                    editor.putInt("filterRight", rangebar.getRightIndex());
                }

                editor.apply();
                listener.onFilterDialogClosed();
            }
        });
        builder.setView(view);
        levelDialog = builder.create();
        levelDialog.show();

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBarLocal, int leftThumbIndex, int rightThumbIndex) {
                if (leftThumbIndex < leftValueDefault) {
                    leftThumbIndex = leftValueDefault;
                    rangeBarLocal.setLeft(leftValueDefault);
                }
                if (rightThumbIndex > rightValueDefault) {
                    rightThumbIndex = rightValueDefault;
                    rangeBarLocal.setRight(rightValueDefault);
                }
                leftValue.setText(String.valueOf(leftThumbIndex));
                rightValue.setText(String.valueOf(rightThumbIndex));
            }
        });
    }

    interface FilterDialogClosed {
        void onFilterDialogClosed();
    }
}