package be.newpage.milkyway.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import be.newpage.milkyway.Expression;
import be.newpage.milkyway.MyPreferences;
import be.newpage.milkyway.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("c dd MMM");

    private List<Expression> expressions;

    public MyAdapter(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_expression, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expression expression = expressions.get(position);
        holder.setValues(expression.getDate(), expression.getVolume());
    }

    @Override
    public int getItemCount() {
        return expressions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView milkTextView;
        public TextView lenaTextView;
        public ImageView suskewiiieeet;

        public ViewHolder(View v) {
            super(v);

            dateTextView = (TextView) v.findViewById(R.id.date);
            milkTextView = (TextView) v.findViewById(R.id.milk_volume);
            lenaTextView = (TextView) v.findViewById(R.id.lena_volume);
            suskewiiieeet = (ImageView) v.findViewById(R.id.suskewiiieeet);
        }

        public void setValues(Date date, int milk) {
            dateTextView.setText(dateFormat.format(date));
            milkTextView.setText(String.format("%dml", milk));

            Date dateOfBirth = MyPreferences.getBirthDate();
            int currentWeight = MyPreferences.getCurrentWeight();
            int lenaVolume = GraphFragment.getLenaVolumePerDay(dateOfBirth, currentWeight);

            float foefelFactor = 5.0f/12.0f;
            lenaVolume = Math.round(foefelFactor * ((float) lenaVolume));

            lenaTextView.setText(String.format("%dml", lenaVolume));
        }
    }
}