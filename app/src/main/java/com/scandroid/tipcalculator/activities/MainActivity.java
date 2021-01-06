package com.scandroid.tipcalculator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.scandroid.tipcalculator.R;

import java.util.Locale;

import static com.scandroid.tipcalculator.lib.Utils.showInfoDialog;

public class MainActivity extends AppCompatActivity {

    EditText etBase;
    TextView tvPercent;
    SeekBar sbTipPercent;
    TextView tvTip;
    TextView tvTotal;
    private Snackbar mSnackBar;
    private CoordinatorLayout ml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFAB();
        setupViews();
        addSeekBarListener();
        addEditTextListener();
    }

    private void setupViews() {
        ml = findViewById(R.id.mLayout);
        etBase = findViewById(R.id.et_base);
        tvPercent = findViewById(R.id.tv_percent);
        sbTipPercent = findViewById(R.id.sb_tip_percent);
        tvTip = findViewById(R.id.tv_tip);
        tvTotal = findViewById(R.id.tv_total);
        mSnackBar = Snackbar.make(ml, "Welcome!", Snackbar.LENGTH_LONG);
    }

    private void addSeekBarListener() {
        sbTipPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int percent = progress;
                tvPercent.setText(String.valueOf(percent) + "%");
                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void addEditTextListener() {
        etBase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();

            }
        });
    }

    private void calculate() {
        if (etBase.length() == 0) {
            etBase.requestFocus();
            etBase.setError("Enter Amount");
        } else {
            double amount = Double.parseDouble(etBase.getText().toString());
            int percent = sbTipPercent.getProgress();
            double tip = amount * percent / 100.0;
            double total = amount + tip;
            tvTip.setText(String.format(Locale.getDefault(), "$%.2f", tip));
            tvTotal.setText(String.format(Locale.getDefault(), "$%.2f", total));
        }
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog(MainActivity.this,
                        getString(R.string.info_title), getString(R.string.rules));
            }
        });
    }

    private void dismissSnackBarIfShown() {
        if (mSnackBar.isShown()) {
            mSnackBar.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            showSettings();
            return true;
        } else if (item.getItemId() == R.id.action_about) {
            showAbout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettings() {
        dismissSnackBarIfShown();
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(intent, 1);
    }

    private void showAbout() {
        dismissSnackBarIfShown();
        showInfoDialog(MainActivity.this, "About Tip Calculator",
                "A quick way to calculate your tip!\nEnjoy!");
    }
}
