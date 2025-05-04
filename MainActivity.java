package com.thanhan.k22411caproject2;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "LanguagePrefs";
    private static final String KEY_LANGUAGE = "language";

    EditText edttilea;
    EditText edttileb;
    TextView txtresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load saved language before setting content view
        loadLocale();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addViews();

        // Initialize language buttons
        Button btnEnglish = findViewById(R.id.btn_english);
        Button btnVietnamese = findViewById(R.id.btn_vietnamese);

        // Set click listeners for language change
        btnEnglish.setOnClickListener(v -> {
            setLocale("en");
            recreate(); // Refresh activity to apply language change
        });

        btnVietnamese.setOnClickListener(v -> {
            setLocale("vi");
            recreate(); // Refresh activity to apply language change
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edttilea = findViewById(R.id.edttilea);
        edttileb = findViewById(R.id.edttitleb);
        txtresult = findViewById(R.id.txtresult);
    }

    public void do_solution(View view) {
        try {
            String hsa = edttilea.getText().toString();
            String hsb = edttileb.getText().toString();
            if (hsa.isEmpty() || hsb.isEmpty()) {
                txtresult.setText(getResources().getText(R.string.error_empty_input));
                return;
            }
            double a = Double.parseDouble(hsa);
            double b = Double.parseDouble(hsb);
            if (a == 0 && b == 0) {
                txtresult.setText(getResources().getText(R.string.title_infinity));
            } else if (a == 0 && b != 0) {
                txtresult.setText(getResources().getText(R.string.title_nosolution));
            } else {
                txtresult.setText("X = " + (-b / a));
            }
        } catch (NumberFormatException e) {
            txtresult.setText(getResources().getText(R.string.error_invalid_input));
        }
    }

    public void do_next(View view) {
        edttilea.setText("");
        edttileb.setText("");
        txtresult.setText("");
        edttilea.requestFocus();
    }

    public void do_exit(View view) {
        finish();
    }

    // Method to set the locale and save it
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Save selected language to SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LANGUAGE, languageCode);
        editor.apply();
    }

    // Method to load saved locale
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String language = prefs.getString(KEY_LANGUAGE, ""); // Default to system language if not set
        if (!language.isEmpty()) {
            setLocale(language);
        }
    }
}
