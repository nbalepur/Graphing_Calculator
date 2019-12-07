package com.example.graphingcalculator;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EquationItemAdapter extends ArrayAdapter<Equation> {

    private final static String TAG = "EquationItemAdapter";
    private Context context;
    private int resource;

    ArrayList<Equation> equations;
    ArrayList<Boolean> visibilities = new ArrayList<>();

    public EquationItemAdapter(Context setContext, int setResource, ArrayList<Equation> setEquations) {
        super(setContext, setResource, setEquations);
        equations = setEquations;
        context = setContext;
        resource = setResource;

        for (int i = 0; i < equations.size(); i++) {
            visibilities.add(true);
        }

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String equation = getItem(position).getEquation();
        int color = getItem(position).getColor();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView equationTextView = convertView.findViewById(R.id.equationTextView);
        Button changeColorButton = convertView.findViewById(R.id.colorChangeBtn);

        changeColorButton.setBackgroundColor(color);
        equationTextView.setText(equation);

        CheckBox visibilityCheck = convertView.findViewById(R.id.visibilityCheck);
        visibilityCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                visibilities.set(position, b);
            }
        });

        return convertView;
    }

    public ArrayList<Boolean> getVisibilities() {
        return visibilities;
    }

}
