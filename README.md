### Chart for Android
Chart for Android
Provides simple way to draw Chart on View.

## Line Chart

```sh
**Sample Matrix Data**
	profit	lost	margin
jan	20		10		10
feb	30		22		8
mar	40		33		7
apr	50		55		-5
may	60		58		2
jun	70		60		10
jul	80		65		15
aug	90		68		22
sep	100		78		22
oct	110		80		30
nov	120		82		38
dec	130		88		42
```

**Sample Android Layout**
activity_main.xml
```sh
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <com.ff55lab.chart.Line
        android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#ffe3e3e3"/>

</RelativeLayout>
```

Sample Android Code to draw Line Chart on View
ActivityMain.java
```sh
Line lineChart = (Line) findViewById(R.id.chartView);
lineChart.setOnGetDataListener(new Line.OnGetDataListener() {
    @Override
    public Data onGetData() {
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
});
```
