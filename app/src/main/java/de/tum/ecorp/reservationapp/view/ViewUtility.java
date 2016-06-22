package de.tum.ecorp.reservationapp.view;

import de.tum.ecorp.reservationapp.model.Restaurant;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

public class ViewUtility {
    //TODO: Decide if we want to support miles as well, then we need to use Locale information
    private final static String UNIT_KILOMETER = "km";
    private final static String UNIT_METER = "m";
    private final static StringBuilder mBuilder = new StringBuilder();

    public static String formatPriceRange(Restaurant.PriceRange priceRange) {
        Currency currency = Currency.getInstance(Locale.getDefault());

        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= priceRange.getNumberRepresentation(); i++) {
            result.append(currency.getSymbol());
        }

        return result.toString();
    }

    public static String formatFloat(float value, int decimals) {
        mBuilder.setLength(0);
        mBuilder.append("#.");
        char repeat = '#';
        char [] toFill = new char[decimals];
        Arrays.fill(toFill, repeat);
        mBuilder.append(toFill);

        DecimalFormat format = new DecimalFormat(mBuilder.toString());
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(value);
    }

    public static String formatDistance(float distance) {
        mBuilder.setLength(0);
        if (distance < 1000) {
            mBuilder.append((int)distance);
            mBuilder.append(UNIT_METER);
        } else {
            double kilometerValue = distance / 1000;
            DecimalFormat oneDecForm = new DecimalFormat("#.#");
            oneDecForm.setRoundingMode(RoundingMode.FLOOR);
            mBuilder.append(oneDecForm.format(kilometerValue));
            mBuilder.append(UNIT_KILOMETER);
        }

        return mBuilder.toString();
    }
}
