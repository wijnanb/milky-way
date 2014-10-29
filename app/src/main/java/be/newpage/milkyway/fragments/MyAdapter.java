package be.newpage.milkyway.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import be.newpage.milkyway.DatabaseHelper;
import be.newpage.milkyway.Expression;
import be.newpage.milkyway.MyPreferences;
import be.newpage.milkyway.R;
import be.newpage.milkyway.activities.MainActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("c dd MMM");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private Context context;
    private List<Expression> expressions;
    private Map<String, Integer> totals;

    public MyAdapter(Context context, List<Expression> expressions, Map<String, Integer> totals) {
        this.context = context;
        this.expressions = expressions;
        this.totals = totals;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_expression, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Expression expression = expressions.get(position);

        boolean isHeader = true;
        String day = DatabaseHelper.dateFormat.format(expression.getDate());

        if (position > 0) {
            String previousDay = DatabaseHelper.dateFormat.format(expressions.get(position - 1).getDate());
            isHeader = !(day.equals(previousDay));
        }

        if (isHeader) {
            int total = totals.get(day);
            holder.setHeaderValues(expression.getDate(), total);
        }

        holder.setHeaderVisiblie(isHeader);
        holder.setDetailValues(expression.getDate(), expression.getVolume());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Delete? " + expression)
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d("delete", "delete " + position + " " + expression);
                                MainActivity mainActivity = (MainActivity) context;
                                mainActivity.getDatabaseHelper().deleteExpression(expression);
                                notifyItemRemoved(position);

                                expressions = mainActivity.getDatabaseHelper().queryForExpressions();
                                totals = mainActivity.getDatabaseHelper().queryTotalPerDay();

                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return expressions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View header;
        public TextView dateTextView;
        public TextView milkTextView;
        public TextView lenaTextView;
        public ImageView suskewiiieeet;

        public View detail;
        public TextView timeTextView;
        public TextView expressionTextView;
        public View deleteButton;

        public ViewHolder(View v) {
            super(v);

            header = v.findViewById(R.id.header);
            dateTextView = (TextView) v.findViewById(R.id.date);
            milkTextView = (TextView) v.findViewById(R.id.milk_volume);
            lenaTextView = (TextView) v.findViewById(R.id.lena_volume);
            suskewiiieeet = (ImageView) v.findViewById(R.id.suskewiiieeet);

            detail = v.findViewById(R.id.detail);
            timeTextView = (TextView) v.findViewById(R.id.time);
            expressionTextView = (TextView) v.findViewById(R.id.expression_volume);
            deleteButton = v.findViewById(R.id.delete);
        }

        public void setHeaderVisiblie(boolean visible) {
            header.setVisibility(visible ? View.VISIBLE : View.GONE);
        }

        public void setDetailValues(Date date, int milk) {
            timeTextView.setText(timeFormat.format(date));
            expressionTextView.setText(String.format("%dml", milk));
        }

        public void setHeaderValues(Date date, int total) {
            Date dateOfBirth = MyPreferences.getBirthDate();
            int currentWeight = MyPreferences.getCurrentWeight();
            int lenaVolume = GraphFragment.getLenaVolumePerDay(dateOfBirth, currentWeight, date);

            float foefelFactor = 5.0f / 12.0f;
            lenaVolume = Math.round(foefelFactor * ((float) lenaVolume));
            boolean passed = (total >= lenaVolume);

            milkTextView.setTypeface(null, passed ? Typeface.BOLD : Typeface.NORMAL);

            dateTextView.setText(dateFormat.format(date));
            milkTextView.setText(String.format("%dml", total));
            lenaTextView.setText(String.format("%dml", lenaVolume));
            suskewiiieeet.setVisibility(passed ? View.VISIBLE : View.INVISIBLE);
        }
    }
}