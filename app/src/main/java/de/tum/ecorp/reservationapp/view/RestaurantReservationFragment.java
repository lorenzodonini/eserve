package de.tum.ecorp.reservationapp.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.tum.ecorp.reservationapp.R;
import de.tum.ecorp.reservationapp.model.Restaurant;
import de.tum.ecorp.reservationapp.model.Table;
import de.tum.ecorp.reservationapp.model.TimeSlot;
import de.tum.ecorp.reservationapp.resource.MockRestaurantResource;
import de.tum.ecorp.reservationapp.resource.RestaurantResource;
import de.tum.ecorp.reservationapp.service.MockReservationService;


public class RestaurantReservationFragment extends Fragment {
    public static final String ARG_RESTAURANT = "restaurant";

    private RestaurantResource restaurantResource = new MockRestaurantResource();
    private MockReservationService reservationService;
    private Restaurant mRestaurant;

    private List<Table> tableSlots;
    private List<TimeSlot> timeSlots;

    private Date chosenDate;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;


    private Spinner spinnerTime;
    private Spinner spinnerSeats;
    private Spinner spinnerTable;

    private LinearLayout includeLayout;
    private View timesSeatsLayout, tablesLayout;
    private TextView timeTextView, tablesTextView, reservationDate, noTables;
    private Button reserve;

    LayoutInflater layoutInflater;
    private String timeChoice;

    public RestaurantReservationFragment() {

    }

    public static RestaurantReservationFragment newInstance(Restaurant restaurant) {
        RestaurantReservationFragment fragment = new RestaurantReservationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey(ARG_RESTAURANT)) {
                mRestaurant = (Restaurant) arguments.get(ARG_RESTAURANT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reservationService = new MockReservationService(restaurantResource);

        final View rootView = inflater.inflate(R.layout.fragment_restaurant_reservation, container, false);

        final View timeView = inflater.inflate(R.layout.times_seats_layout, container, false);

        final View tableView = inflater.inflate(R.layout.tables_layout, container, false);

        if (mRestaurant != null) {
            findViewById(rootView, timeView, tableView);
            myCalendar = Calendar.getInstance();

            date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                    initializeSpinners(rootView);
                }

            };

            reservationDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCalendarDialog();
                }
            });

            Button reserve = (Button) rootView.findViewById(R.id.reserve);

            reserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("E-Serve Reservation")
                            .setMessage("Table Reserved")
                            .setPositiveButton("OK", null)
                            .create()
                            .show();
                }
            });
        }

        return rootView;
    }

    private void findViewById(View rootView, View timeView, View tableView) {
        includeLayout = (LinearLayout) rootView.findViewById(R.id.includeLayout);
        timeTextView = (TextView) rootView.findViewById(R.id.times);
        reservationDate = (TextView) rootView.findViewById(R.id.date);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        timesSeatsLayout = layoutInflater.inflate(R.layout.times_seats_layout, (LinearLayout) timeView.findViewById(R.id.includeTimesSeatsLinearLayout), false);
        tablesLayout = layoutInflater.inflate(R.layout.tables_layout, (LinearLayout) tableView.findViewById(R.id.includeTablesLinearLayout), false);
        reserve = (Button) rootView.findViewById(R.id.reserve);
        noTables = (TextView) rootView.findViewById(R.id.noTablesAvailable);
    }

    public void showCalendarDialog() {
        DatePickerDialog d = onCreateDialog();
        d.show();
    }

    protected DatePickerDialog onCreateDialog() {
        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.CalendarTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        return dialog;
    }

    private void initializeSpinners(final View rootView) {

        chosenDate = myCalendar.getTime();
        timeSlots = reservationService.getAvailableTimeSlots(mRestaurant.getId(), chosenDate);
        List<String> stringListTimes = Lists.transform(timeSlots, new Function<Object, String>() {
            @Override
            public String apply(Object arg0) {
                if (arg0 != null)
                    return arg0.toString();
                else
                    return "";
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stringListTimes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        check if there are available times
        if (!adapter.isEmpty()) {
            if (timesSeatsLayout.getParent() != null) {
                ((ViewGroup) timesSeatsLayout.getParent()).removeView(timesSeatsLayout);
            }
            //add times and seats view
            includeLayout.addView(timesSeatsLayout);

            spinnerTime = (Spinner) rootView.findViewById(R.id.spinnerTime);
            spinnerSeats = (Spinner) rootView.findViewById(R.id.spinnerSeats);
            timeTextView = (TextView) rootView.findViewById(R.id.times);
            timeTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGrey));
            spinnerTime.setAdapter(adapter);

            spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView,
                                           View selectedItemView, int position, long id) {
                    timeChoice = parentView.getItemAtPosition(position).toString();
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.nrOfSeats, android.R.layout.simple_spinner_item);

                    noTables.setVisibility(View.INVISIBLE);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSeats.setAdapter(adapter);
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // do nothing
                }

            });

            spinnerSeats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String peopleChoice = parent.getItemAtPosition(position).toString();
                    List<TimeSlot> chosenTime = new ArrayList<TimeSlot>();
                    String[] time = timeChoice.split(":");
                    chosenTime.add(new TimeSlot(Integer.parseInt(time[0]), Integer.parseInt(time[1])));
                    tableSlots = reservationService.getAvailableTables(mRestaurant.getId(), chosenDate, Integer.parseInt(peopleChoice), chosenTime);
                    List<String> stringListTables = Lists.transform(tableSlots, new Function<Object, String>() {
                        @Override
                        public String apply(Object arg0) {
                            if (arg0 != null)
                                return arg0.toString();
                            else
                                return "";
                        }
                    });
                    //check if there are available tables
                    if (stringListTables.size() > 0) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stringListTables);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        reserve.setEnabled(true);
                        if (tablesLayout.getParent() != null) {
                            ((ViewGroup) tablesLayout.getParent()).removeView(tablesLayout);
                        }

                        noTables.setVisibility(View.INVISIBLE);
                        //add tables view
                        includeLayout.addView(tablesLayout);
                        tablesTextView = (TextView) rootView.findViewById(R.id.tables);
                        spinnerTable = (Spinner) rootView.findViewById(R.id.spinnerTable);
                        spinnerTable.setAdapter(adapter);
                        tablesTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGrey));
                        tablesTextView.setText(R.string.availableTables);

                    } else {
                        if (tablesLayout.getParent() != null) {
                            ((ViewGroup) tablesLayout.getParent()).removeView(tablesLayout);
                        }
                        noTables.setVisibility(View.VISIBLE);
                        reserve.setEnabled(false);
                        tablesTextView.setText(R.string.noTables);
                        tablesTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            if (includeLayout.getParent() != null) {
                includeLayout.removeAllViews();
            }
            reserve.setEnabled(false);
            noTables.setVisibility(View.VISIBLE);
        }


    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        reservationDate.setText(sdf.format(myCalendar.getTime()));
    }

}
