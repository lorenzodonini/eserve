package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.OpeningTime;

public class OpeningHoursArrayAdapter extends ArrayAdapter<OpeningTime> {
    private final Context context;

    public OpeningHoursArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.opening_hours_listitem, parent, false);

            viewHolder = new ViewHolder();
            //Getting subViews
            viewHolder.openingDayLabel = (TextView) convertView.findViewById(R.id.openingDayLabel);
            viewHolder.openingHoursLabel = (TextView) convertView.findViewById(R.id.openingHoursLabel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OpeningTime openingTime = getItem(position);
        if (openingTime != null) {
            viewHolder.openingDayLabel.setText(openingTime.getWeekdayAsString());
            viewHolder.openingHoursLabel.setText(openingTime.getConcatenatedOpeningTimes());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView openingDayLabel;
        TextView openingHoursLabel;
    }
}
