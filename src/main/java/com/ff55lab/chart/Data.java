package com.ff55lab.chart;

import android.graphics.Color;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Data {

    public class Title {

        private List<String> _title = new LinkedList<>();
        private Map<String, Boolean> _showTitle = new LinkedHashMap<>();

        protected Title() {}

        protected void reset() {
            _title = new LinkedList<>();
            _showTitle = new LinkedHashMap<>();
        }

        public int size() {
            try {
                return _title.size();
            } catch (Exception e) {
                return 0;
            }
        }

        public List<String> getList() {
            try {
                return _title;
            } catch (Exception e) {
                return null;
            }
        }

        public String get(int i) {
            try {
                return _title.get(i);
            } catch (Exception e) {
                return null;
            }
        }

        public boolean isVisible(int i) {
            try {
                return _showTitle.get(get(i));
            } catch (Exception e) {
                return false;
            }
        }

        public Title add(String title, boolean showTitle) {
            try {
                if (!_title.contains(title) && !_title.add(title))
                    return null;

                _showTitle.put(title, showTitle);

            } catch (Exception e) {
                // Do nothing
            }

            return this;
        }

        public Title add(String title) {
            return add(title, true);
        }

        public Title addHidden() {
            String title = null;
            try {
                title = "_hidden_"+Integer.toString(_title.size());
            } catch (Exception e) {
                // Do nothing
            }
            return add(title, false);
        }

    }

    public class Legend {

        private List<Float> _line;
        private Map<String, List<Float>> _legend = new LinkedHashMap<>();
        private Map<String, Integer> _legendColor = new LinkedHashMap<>();
        private float _min;
        private float _max;

        protected Legend() {}

        public float getMax() {
            return _max;
        }

        public float getMin() {
            return _min;
        }

        public Map<String, List<Float>> getList() {
            try {
                return _legend;
            } catch (Exception e) {
                return null;
            }
        }

        protected void reset() {
            _min = 0f;
            _max = 0f;
            _line = null;
            _legend = new LinkedHashMap<>();
            _legendColor = new LinkedHashMap<>();
        }

        public int getColor(String name) {
            try {
                if (_legendColor.containsKey(name))
                    return _legendColor.get(name);
            } catch (Exception e) {
                // Do nothing
            }
            return Color.BLACK;
        }

        public Legend addName(String name, int color) {
            try {
                if (!_legend.containsKey(name)) {
                    _legend.put(name, new LinkedList<Float>());
                    _legendColor.put(name, color);
                }

                _line = _legend.get(name);

            } catch (Exception e) {
                // Do nothing
            }

            return this;
        }
        public Legend addName(String name, int alpha, int red, int green, int blue) {
            return addName(name, Color.argb(alpha, red, green, blue));
        }

        public Legend addName(String name, int red, int green, int blue) {
            return addName(name, 255, red, green, blue);
        }

        public Legend addName(String name) {
            return addName(name, 255, 0, 38);
        }

        public Legend add(float y) {
            try {
                if (_line != null && _line.add(y)) {
                    if (y < _min) _min = y;
                    if (y > _max) _max = y;

                    return this;
                }

            } catch (Exception e) {
                // Do nothing
            }
            return null;
        }

    }

    private Title _title = new Title();
    private Legend _legend = new Legend();

    public Title getTitle() {
        try {
            return _title;
        } catch (Exception e) {
            return null;
        }
    }

    public Legend getLegend() {
        try {
            return _legend;
        } catch (Exception e) {
            return null;
        }
    }

    public void reset() {
        try {
            _title.reset();
            _legend.reset();
        } catch (Exception e) {
            // Do nothing
        }
    }

    public static Data newTemplate() {
        try {
            Data data = new Data();
            data.getTitle().add("1").add("2").add("3")
                    .add("4").add("5").add("6")
                    .add("7").add("8").add("9")
                    .add("10").add("11").add("12");
            data.getLegend().addName("Profit")
                    .add(20).add(30).add(40)
                    .add(50).add(60).add(70)
                    .add(80).add(90).add(100)
                    .add(110).add(120).add(130);
            data.getLegend().addName("Loss", 255, 96, 178, 79)
                    .add(10).add(22).add(33)
                    .add(55).add(58).add(60)
                    .add(65).add(68).add(78)
                    .add(80).add(82).add(88);
            data.getLegend().addName("Margin", 33, 66, 255)
                    .add(20-10).add(30-22).add(40-33)
                    .add(50-55).add(60-58).add(70-60)
                    .add(80-65).add(90-68).add(100-78)
                    .add(110-80).add(120-82).add(130-88);
            data.getLegend().addName("Fortune", Color.argb(255, 175, 149, 61))
                    .add(54).add(48).add(25)
                    .add(0).add(100).add(150)
                    .add(100).add(54).add(-2)
                    .add(30).add(20).add(15);
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public static Data newTemplate2() {
        try {
            Data data = new Data();
            data.getTitle().add("1").add("4").add("7").add("10").add("12");
            data.getLegend().addName("Profit")
                    .add(20).add(50).add(80).add(110).add(130);
            data.getLegend().addName("Loss", 255, 96, 178, 79)
                    .add(10).add(55).add(65).add(80).add(88);
            data.getLegend().addName("Margin", 33, 66, 255)
                    .add(20 - 10).add(50-55).add(80 - 65).add(110-80).add(130-88);
            data.getLegend().addName("Fortune", Color.argb(255, 175, 149, 61))
                    .add(54).add(0).add(100).add(30).add(15);
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public static Data newTemplate3() {
        try {
            Data data = new Data();
            data.getTitle().add("Jan").addHidden().addHidden()
                    .add("Apr").addHidden().addHidden()
                    .add("Jul").addHidden().addHidden()
                    .add("Oct").addHidden().add("Dec");
            data.getLegend().addName("Profit")
                    .add(20).add(30).add(40)
                    .add(50).add(60).add(70)
                    .add(80).add(90).add(100)
                    .add(110).add(120).add(130);
            data.getLegend().addName("Loss", 255, 96, 178, 79)
                    .add(10).add(22).add(33)
                    .add(55).add(58).add(60)
                    .add(65).add(68).add(78)
                    .add(80).add(82).add(88);
            data.getLegend().addName("Margin", 33, 66, 255)
                    .add(20-10).add(30-22).add(40-33)
                    .add(50-55).add(60-58).add(70-60)
                    .add(80-65).add(90-68).add(100-78)
                    .add(110-80).add(120-82).add(130-88);
            data.getLegend().addName("Fortune", Color.argb(255, 175, 149, 61))
                    .add(54).add(48).add(25)
                    .add(0).add(100).add(150)
                    .add(100).add(54).add(-2)
                    .add(30).add(20).add(15);
            return data;
        } catch (Exception e) {
            return null;
        }
    }
}
