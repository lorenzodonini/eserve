package de.tum.ecorp.reservationapp.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

    Spinner spinnerTime;
    Spinner spinnerSeats;
    Spinner spinnerTable;

    private TextView seats, tables, reservationDate;


    public RestaurantReservationFragment() {

    }

    public static RestaurantReservationFragment newInstance(Restaurant restaurant) {//ArrayList<Table> tables){//, OpeningTimes times) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reservationService = new MockReservationService(restaurantResource);

        View rootView = inflater.inflate(R.layout.fragment_restaurant_reservation, container, false);

        if (mRestaurant != null) {
            spinnerTime = (Spinner) rootView.findViewById(R.id.spinnerTime);
            spinnerSeats = (Spinner) rootView.findViewById(R.id.spinnerSeats);
            spinnerTable = (Spinner) rootView.findViewById(R.id.spinnerTable);
            reservationDate = (TextView) rootView.findViewById(R.id.date);
            seats = (TextView) rootView.findViewById(R.id.seats);
            tables = (TextView) rootView.findViewById(R.id.tables);

            myCalendar = Calendar.getInstance();
            updateLabel();
            initializeSpinners();
            date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                    initializeSpinners();
                }

            };

            reservationDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });


            Button reserve = (Button) rootView.findViewById(R.id.reserve);
        }

        return rootView;
    }

    private void initializeSpinners() {
        Log.d("Restaurant   ", "" + mRestaurant);
        Log.d("OPening times   ", "" + mRestaurant.getOpeningTimes());
        chosenDate = myCalendar.getTime();
        Log.d("DATE", "" + chosenDate);
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
        spinnerTime.setAdapter(adapter);

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Object item = parentView.getItemAtPosition(position);
                String choice = parentView.getItemAtPosition(position).toString();
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.nrOfSeats, android.R.layout.simple_spinner_item);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                seats.setVisibility(View.VISIBLE);
                spinnerSeats.setVisibility(View.VISIBLE);
                spinnerSeats.setAdapter(adapter);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // do nothing
            }

        });

        spinnerSeats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = parent.getItemAtPosition(position).toString();

                tableSlots = reservationService.getAvailableTables(mRestaurant.getId(), chosenDate, Integer.parseInt(choice), timeSlots);
//                List<String> strings = tableSlots.stream()
//                        .map(object -> (object != null ? object.toString() : null))
//                        .collect(Collectors.toList());
                List<String> stringListTables = Lists.transform(tableSlots, new Function<Object, String>() {
                    @Override
                    public String apply(Object arg0) {
                        if (arg0 != null)
                            return arg0.toString();
                        else
                            return "";
                    }
                });
                if (stringListTables.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stringListTables);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tables.setVisibility(View.VISIBLE);
                    spinnerTable.setAdapter(adapter);
                } else {
                    tables.setText(R.string.noTables);
                    tables.setVisibility(View.VISIBLE);
                    spinnerTable.setBackgroundResource(R.color.colorRed);
                    spinnerTable.setVisibility(View.VISIBLE);
                    spinnerTable.setEnabled(false);
                }

                spinnerTable.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        reservationDate.setText(sdf.format(myCalendar.getTime()));
    }

}
