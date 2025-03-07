package com.example.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton ivLeftArrow, ivRightArrow;
    private TextView tvMonthYear;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure your XML file is activity_main.xml

        // --------------------- Gift Icon Popup ---------------------
        LinearLayout giftLayout = findViewById(R.id.giftLayout);
        giftLayout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Gift");
            builder.setMessage("You have a new gift!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        // --------------------- Settings Icon Navigation ---------------------
        // Note: We now reference the FrameLayout with the ID 'settingsLayout'
        FrameLayout settingsLayout = findViewById(R.id.settingsLayout);
        settingsLayout.setOnClickListener(v -> {
            // Launch SettingsActivity (create this activity separately and add it to your manifest)
            startActivity(new Intent(MainActivity.this, Settings.class));
        });

        // --------------------- "See all" Clickable Text ---------------------
        TextView textSeeAll = findViewById(R.id.textSeeAll);
        textSeeAll.setOnClickListener(v -> {
            // Replace the container with the BadgesFragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.constraintLayout, new BadgesFragment())
                    .commit();
        });

        // --------------------- Arrow Navigation for Month/Year ---------------------
        ivLeftArrow = findViewById(R.id.ivLeftArrow);
        ivRightArrow = findViewById(R.id.ivRightArrow);
        tvMonthYear = findViewById(R.id.tvMonthYear);

        calendar = Calendar.getInstance();
        updateMonthYearDisplay();

        ivLeftArrow.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateMonthYearDisplay();
        });

        ivRightArrow.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateMonthYearDisplay();
        });

        // --------------------- Day Bubble Selection ---------------------
        LinearLayout activityLogLayout = findViewById(R.id.activityLogLayout);
        int childCount = activityLogLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View bubbleView = activityLogLayout.getChildAt(i);
            bubbleView.setOnClickListener(v -> {
                // Reset all bubbles to unselected (gray)
                for (int j = 0; j < activityLogLayout.getChildCount(); j++) {
                    View child = activityLogLayout.getChildAt(j);
                    child.setBackgroundResource(R.drawable.bgcircle_gray);
                }
                // Set the clicked bubble to selected
                v.setBackgroundResource(R.drawable.bgcircle_selected);
            });
        }
    }

    /**
     * Updates the Month/Year label based on the current calendar value.
     */
    private void updateMonthYearDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tvMonthYear.setText(sdf.format(calendar.getTime()));
    }
}
