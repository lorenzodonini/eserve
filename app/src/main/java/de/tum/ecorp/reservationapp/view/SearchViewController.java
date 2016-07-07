package de.tum.ecorp.reservationapp.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.common.base.Function;

public class SearchViewController {

    private final Function<String, Void> searchHandler;
    private View container;
    private ImageView clearButton;
    private ImageView closeButton;
    private EditText text;

    public SearchViewController(View container, ImageView clearButton, ImageView closeButton, EditText text, Function<String, Void> searchHandler) {
        this.container = container;
        this.clearButton = clearButton;
        this.closeButton = closeButton;
        this.text = text;
        this.searchHandler = searchHandler;
    }

    public void initialiseSearchView() {

        // Search text changed listener
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.GONE);
                }
                searchHandler.apply(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSearchBar();
            }
        });

        clearButton.setVisibility(View.GONE);
        hideSearchBar();
    }

    public void showSearchBar() {
        container.setVisibility(View.VISIBLE);
    }

    public void hideSearchBar() {
        container.setVisibility(View.GONE);
    }

}
