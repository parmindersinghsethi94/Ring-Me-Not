package app.com.ringmenot.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.ringmenot.MainActivity;
import app.com.ringmenot.R;

public class UsersAdapter extends ArrayAdapter<Missed> {
    private Context ctx;
    public UsersAdapter(Context context, ArrayList<Missed> users) {
        super(context, 0, users);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Missed user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.caller, parent, false);
        }
        Typeface helvetica = Typeface.createFromAsset(ctx.getAssets(), "CaviarDreams.ttf");


        // Lookup view for data population
        TextView operator = (TextView) convertView.findViewById(R.id.operator);
        TextView circle = (TextView) convertView.findViewById(R.id.circle);
        TextView number = (TextView) convertView.findViewById(R.id.number);
        operator.setTypeface(helvetica);
        circle.setTypeface(helvetica);
        number.setTypeface(helvetica);
        // Populate the data into the template view using the data object
        operator.setText(user.getOperator());
        circle.setText(user.getCircle());
        number.setText(user.getNumber());
        // Return the completed view to render on screen
        return convertView;
    }
}