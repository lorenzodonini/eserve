package de.tum.ecorp.reservationapp.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import com.google.common.base.Function;

public class SearchViewController {

    private final Function<String, Void> searchHandler;
    private View container;
    private ImageView clearButton;
    private ImageView closeButton;
    private EditText text;
    private InputMethodManager inputMethodManager;

    public SearchViewController(Context context, View container, ImageView clearButton, ImageView closeButton, EditText text, Function<String, Void> searchHandler) {
        this.container = container;
        this.clearButton = clearButton;
        this.closeButton = closeButton;
        this.text = text;
        this.searchHandler = searchHandler;
        this.inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
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
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //Still perform a search operation, then hide keyboard & search bar
                    searchHandler.apply(v.getText().toString());
                    hideSearchBar();
                    return true;
                }
                return false;
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
        text.requestFocus();
        inputMethodManager.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSearchBar() {
        text.clearFocus();
        inputMethodManager.hideSoftInputFromWindow(text.getWindowToken(), 0);
        container.setVisibility(View.GONE);
    }

}
