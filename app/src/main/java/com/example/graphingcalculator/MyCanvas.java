package com.example.graphingcalculator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class MyCanvas extends View {

    /** the height and with of the screen */
    private int height, width;

    /** the boundary x and y values */
    private double leftNum, rightNum, topNum, bottomNum;

    /** the pixel location of each edge */
    private int leftEdge, rightEdge, topEdge, bottomEdge;

    /** array of the coefficients of the user's inputs */
    private double[] coefficients;

    /** the degree or power of the current equation */
    private int degrees;

    /** the color of the graph */
    private int graphColor;

    /** the number of equations to graph */
    private ArrayList<Equation> equations;

    /** the visibilities of the above equations (on or off) */
    private boolean[] visibilities;

    public MyCanvas(Context context) {
        super(context);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    protected void onDraw(Canvas canvas) {
        //minMaxBounds();
        createAxes(canvas);
        drawGraph(canvas);
    }

    /** generates a graph based on the user's inputs */
    private void drawGraph(Canvas canvas) {

        for (int j = 0; j < equations.size(); j++) {
            if (visibilities[j]) {
                graphColor = equations.get(j).getColor();
                coefficients = equations.get(j).getCoefficients();
                degrees = equations.get(j).getDegree();

                Paint paint = new Paint();
                paint.setColor(graphColor);
                paint.setStrokeWidth(10);

                final double spacing = 0.0001 * rightNum;

                //tiny increments to represent x values, then draws line between small increments
                for (double i = leftNum; i <= rightNum - spacing; i += spacing) {

                    double output1 = getOutput(i);
                    double output2 = getOutput(i + spacing);

                    double x1, x2;
                    double y1, y2;

                    x1 = (i / rightNum) * (rightEdge - 1.0 * width / 2) + (1.0 * width / 2);
                    x2 = ((i + spacing) / rightNum) * (rightEdge - 1.0 * width / 2) + (1.0 * width / 2);

                    y1 = (output1 / topNum) * (topEdge - 1.0 * height / 2) + (1.0 * height / 2);
                    y2 = (output2 / topNum) * (topEdge - 1.0 * height / 2) + (1.0 * height / 2);

                    canvas.drawLine((float) x1, (float) y1, (float) x2, (float) y2, paint);

                }
            }
        }
    }

    /** outputs the correct y-value output for the equation */
    private double getOutput(double x) {

        double val = coefficients[1];
        Log.d("degrees", "degrees: " + degrees);
        for (int i = 0; i < degrees; i++) {
            val += coefficients[i] * Math.pow(x, degrees - i);
        }
        return val;
    }

    /** draws the axes on the canvas */
    private void createAxes(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);

        final int margin = 20;

        leftEdge = margin;
        rightEdge = width - margin;
        topEdge = height/2 - (width - 2*margin)/2;
        bottomEdge = height/2 + (width - 2*margin)/2;

        //draws the x-axis
        canvas.drawLine(margin, height/2, width - margin, height/2, paint);

        //draws the y-axis
        canvas.drawLine(width/2, height/2 - (width - 2*margin)/2, width/2, height/2 + (width - 2*margin)/2, paint);


        //drawing the number boundaries w/ tick marks
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);


        //left tick marks and numbers
        canvas.drawText(leftNum + "", leftEdge, height / 2 + 3 * margin, paint);
        canvas.drawLine(leftEdge, height/2 + margin, leftEdge, height/2 - margin, paint);

        //right tick marks and numbers
        canvas.drawText(rightNum + "", rightEdge, height / 2 + 3 * margin, paint);
        canvas.drawLine(rightEdge, height/2 + margin, rightEdge, height/2 - margin, paint);

        //upper tick marks and numbers
        canvas.drawText(topNum + "", width/2, topEdge - margin, paint);
        canvas.drawLine(width/2 - margin, topEdge, width/2 + margin, topEdge, paint);

        //lower tick marks and numbers
        canvas.drawText(bottomNum + "", width/2, bottomEdge + 2 * margin, paint);
        canvas.drawLine(width/2 - margin, bottomEdge, width/2 + margin, bottomEdge, paint);
    }

    public void setVisibilities(boolean[] setVisibilities) { visibilities = setVisibilities; }

    public void setEquations(ArrayList<Equation> setEquations) { equations = setEquations; }

    public void setColor(int setColor) { graphColor = setColor; }

    public void setNums(int xNum, int yNum) {
        leftNum = -xNum;
        rightNum = xNum;
        bottomNum = -yNum;
        topNum = yNum;
    }

}
