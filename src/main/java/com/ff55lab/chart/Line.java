package com.ff55lab.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Line extends View {

    public static interface OnGetDataListener {
        Data onGetData();
    }

    private static final int POINT_Y = 0;
    private static final int POINT_X = 1;

    private static final int OFFSET_V = 0; // Vertical (Axis Y)
    private static final int OFFSET_H = 1; // Horizontal (Axis X)

    private Data _data = null;
    private OnGetDataListener _onGetData;

    private Path _pathAxisX = null;
    private Path _pathAxisY = null;
    private Paint _paintAxisXY = null;
    private Paint _paintAxisXY_Title = null;
    private Map<String, Float[]> _titleAxisX = null;
    private Map<String, Float[]> _titleAxisY = null;

    private List<Path> _pathLine = null;
    private List<Paint> _paintLine = null;
    private List<Path> _pathLine_Legend = null;
    private Map<String, Float[]> _pathLine_LegendTitle = null;


    public void setOnGetDataListener(OnGetDataListener listener) {
        _onGetData = listener;
    }

    private void redraw() {
        float[] _startPoint = new float[] {0f, 0f};

        int width = getWidth();
        int height = getHeight();

        int partSize = _data.getTitle().size();
        int partWidth = width / (partSize + 6);
        int partHeight = height / 6;


        _startPoint[POINT_X] = (partWidth * 2);
        _startPoint[POINT_Y] = (height - partHeight / 2);

        _pathAxisX = new Path();
        _pathAxisX.moveTo(_startPoint[POINT_X], _startPoint[POINT_Y]);
        _pathAxisX.lineTo((partWidth * (2 + partSize)), _startPoint[POINT_Y]);

        _pathAxisY = new Path();
        _pathAxisY.moveTo(_startPoint[POINT_X], _startPoint[POINT_Y]);
        _pathAxisY.lineTo(_startPoint[POINT_X], (partHeight / 2));

        _paintAxisXY = new Paint();
        _paintAxisXY.setStrokeWidth(3f);
        _paintAxisXY.setStyle(Paint.Style.STROKE);
        _paintAxisXY.setARGB(255, 128, 128, 128);

        _paintAxisXY_Title = new Paint();
        _paintAxisXY_Title.setTextSize(Common.dipToPixel(getContext(), 14f));
        _paintAxisXY_Title.setStyle(Paint.Style.FILL);
        _paintAxisXY_Title.setColor(Color.BLACK);

        _titleAxisX = new LinkedHashMap<>();
        for (int i = 0; i < partSize; i++) {
            Float[] xy = new Float[] {0f, 0f};
            xy[OFFSET_H] = (partWidth * i * 1f);
            xy[OFFSET_V] = (partHeight / 2f);

            if (_data.getTitle().isVisible(i))
                _titleAxisX.put(_data.getTitle().get(i), xy);
            else
                _titleAxisX.put(" ", xy);
        }

        _titleAxisY = new LinkedHashMap<>();
        float distanceAxisY = _data.getLegend().getMax() - _data.getLegend().getMin();
        float gapAxisY = distanceAxisY / 5;
        for (int i = 0; i <= 5; i++) {
            float yTitle = _data.getLegend().getMin() + (gapAxisY * i);

            Float[] xy = new Float[] {0f, 0f};
            xy[POINT_X] = _startPoint[POINT_X] - (partWidth * 1.5f);
            xy[POINT_Y] = _startPoint[POINT_Y] - (partHeight * i * 1f);

            _titleAxisY.put(Integer.toString((int) yTitle), xy);
        }

        _pathLine = new LinkedList<>();
        _paintLine = new LinkedList<>();
        _pathLine_Legend = new LinkedList<>();
        _pathLine_LegendTitle = new LinkedHashMap<>();
        int legendIdx = 0;
        for (String title : _data.getLegend().getList().keySet()) {

            List<Float> legend = _data.getLegend().getList().get(title);

            Path path = new Path();
            for (int i = 0; i < legend.size(); i++) {
                float point = legend.get(i);
                float pointY = (point - _data.getLegend().getMin()) / distanceAxisY * (partHeight * 5f);

                if (i == 0)
                    path.moveTo(_startPoint[POINT_X] + (partWidth * (0.5f + i)), _startPoint[POINT_Y] - pointY);
                else
                    path.lineTo(_startPoint[POINT_X] + (partWidth * (0.5f + i)), _startPoint[POINT_Y] - pointY);
            }

            Paint paint = new Paint();
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(_data.getLegend().getColor(title));

            _pathLine.add(path);
            _paintLine.add(paint);


            // Legend description
            path = new Path();
            path.moveTo((partWidth * (2 + partSize + 1)), ((legendIdx + 0.5f) * partHeight));
            path.lineTo((partWidth * (2 + partSize + 4)), ((legendIdx + 0.5f) * partHeight));

            Float[] xy = new Float[] {0f, 0f};
            xy[OFFSET_H] = 0f;
            xy[OFFSET_V] = (partHeight / 8 * 5f);

            _pathLine_Legend.add(path);
            _pathLine_LegendTitle.put(title, xy);

            legendIdx++;
        }

        invalidate(); // Call this to redraw the view
    }

    public Line(Context context) {
        super(context);
    }

    public Line(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Line(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            int width = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(width / 2, mode);
        } catch (Exception e) {
            // Do nothing
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (_onGetData != null && _data == null) {
            _data = _onGetData.onGetData();
            if (_data != null)
                redraw();
        }
        if (_data != null) {
            canvas.drawPath(_pathAxisX, _paintAxisXY);
            canvas.drawPath(_pathAxisY, _paintAxisXY);
            for (String title : _titleAxisX.keySet()) {
                canvas.drawTextOnPath(title, _pathAxisX,
                        _titleAxisX.get(title)[OFFSET_H],
                        _titleAxisX.get(title)[OFFSET_V],
                        _paintAxisXY_Title);
            }
            for (String title : _titleAxisY.keySet()) {
                canvas.drawText(title,
                        _titleAxisY.get(title)[POINT_X],
                        _titleAxisY.get(title)[POINT_Y],
                        _paintAxisXY_Title);
            }
            for (int i = 0; i < _pathLine.size(); i++) {
                Path path = _pathLine.get(i);
                Paint paint = _paintLine.get(i);
                Path pathTitle = _pathLine_Legend.get(i);
                canvas.drawPath(path, paint);
                canvas.drawPath(pathTitle, paint);
            }
            int pathTitleIdx = 0;
            for (String title : _pathLine_LegendTitle.keySet()) {
                Path pathTitle = _pathLine_Legend.get(pathTitleIdx);
                canvas.drawTextOnPath(title, pathTitle,
                        _pathLine_LegendTitle.get(title)[OFFSET_H],
                        _pathLine_LegendTitle.get(title)[OFFSET_V],
                        _paintAxisXY_Title);
                pathTitleIdx++;
            }
        }

    }

}
