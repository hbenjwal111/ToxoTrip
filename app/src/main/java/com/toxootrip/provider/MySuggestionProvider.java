package com.toxootrip.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by himanshu on 23-02-2018.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.toxotrip.provider.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}