package opencode.mla;

import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import opencode.mla.data.MLDataSource;

public class Plotter {
	private static final String PLOTTER_FRAME_TITLE = "MLA - Data Charting";
	private static final String DATA_CHART_LABEL = "Data Chart";
	private static final String X_AXIS_LABEL = "X-Axis";
	private static final String Y_AXIS_LABEL = "Y-Axis";
	private static final String DATA_LABEL = "Data";
	private static final String LINE_LABEL = "Line";
	
	private String chartName, dataLabel, xLabel, yLabel, lineLabel;
	private XYPlot plot;
	
	public static Plotter construct(String chartName, String dataLabel, MLDataSource dataSource) throws Exception {
		return new Plotter(chartName, null, null, dataSource, dataLabel, null, null);
	}

	public static Plotter construct(String chartName, String dataLabel, MLDataSource dataSource, String xLabel, String yLabel) throws Exception {
		return new Plotter(chartName, xLabel, yLabel, dataSource, dataLabel, null, null);
	}

	public static Plotter construct(String chartName, String lineLabel, XYPair lineInfo) throws Exception {
		return new Plotter(chartName, null, null, null, null, lineInfo, lineLabel);
	}

	public static Plotter construct(String chartName, String lineLabel, XYPair lineInfo, String xLabel, String yLabel) throws Exception {
		return new Plotter(chartName, xLabel, yLabel, null, null, lineInfo, lineLabel);
	}

	public static Plotter construct(String chartName, String dataLabel, MLDataSource dataSource, String lineLabel, XYPair lineInfo) throws Exception {
		return new Plotter(chartName, null, null, dataSource, dataLabel, lineInfo, lineLabel);
	}

	public static Plotter construct(String chartName, String dataLabel, MLDataSource dataSource, String lineLabel, XYPair lineInfo, String xLabel, String yLabel) throws Exception {
		return new Plotter(chartName, xLabel, yLabel, dataSource, dataLabel, lineInfo, lineLabel);
	}

	
	private Plotter(String chartName, String xLabel, String yLabel, MLDataSource dataSource, String dataLabel, XYPair xyPair, String lineLabel) throws Exception {
		this.chartName = chartName;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		
		this.dataLabel = dataLabel;
		this.lineLabel = lineLabel;
		
	    //Changes background color
	    this.plot = getPlot(dataSource, xyPair);
	    this.plot.setBackgroundPaint(new Color(255,228,new Random().nextInt(255)));
	}
	
	private XYPlot getPlot(MLDataSource scatteredDataPointsSource, XYPair lineData) throws Exception{
		// Create a single plot containing both the scatter and line
		XYPlot plot = new XYPlot();
		ValueAxis domain1 = new NumberAxis(StringUtils.isBlank(xLabel)?X_AXIS_LABEL:xLabel);
		ValueAxis range1 = new NumberAxis(StringUtils.isBlank(yLabel)?Y_AXIS_LABEL:yLabel);
		plot.setDomainAxis(0, domain1);
		plot.setRangeAxis(0, range1);

		if(scatteredDataPointsSource !=null){
			/* SETUP SCATTER */
			// Create the scatter data, renderer, and axis
			XYDataset collection1 = createScatterDataSet(scatteredDataPointsSource);
			XYItemRenderer renderer1 = new XYLineAndShapeRenderer(false, true);   // Shapes only
			// Set the scatter data, renderer, and axis into plot
			plot.setDataset(0, collection1);
			plot.setRenderer(0, renderer1);

			// Map the scatter to the first Domain and first Range
			plot.mapDatasetToDomainAxis(0, 0);
			plot.mapDatasetToRangeAxis(0, 0);
		}

		if(lineData != null){
			/* SETUP LINE */
			// Create the line data, renderer, and axis
			XYDataset collection2 = createLineDataSet(lineData);
			XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, false);   // Lines only

			// Set the line data, renderer, and axis into plot
			plot.setDataset(1, collection2);
			plot.setRenderer(1, renderer2);
	
			// Map the line to the second Domain and second Range
			plot.mapDatasetToDomainAxis(1, 0);
			plot.mapDatasetToRangeAxis(1, 0);
		}

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		return plot;
	}
	
	private XYDataset createScatterDataSet(MLDataSource scatteredDataPointsSource) throws Exception {
		XYSeriesCollection dataset = new XYSeriesCollection();

	    //Boys (Age,weight) series
	    XYSeries series1 = new XYSeries(StringUtils.isBlank(dataLabel)?DATA_LABEL:dataLabel);
	    scatteredDataPointsSource.reset();
		while (scatteredDataPointsSource.hasNext()) {
			double[] data = scatteredDataPointsSource.getNextAsNumbers();
		    series1.add(data[0], data[1]);
		}
	    dataset.addSeries(series1);
	    return dataset;
	}
	
	private XYDataset createLineDataSet(XYPair lineData){
		XYSeriesCollection dataset = new XYSeriesCollection();
		
	    //Boys (Age,weight) series
	    XYSeries series1 = new XYSeries(StringUtils.isBlank(lineLabel)?LINE_LABEL:lineLabel);
	    series1.add(lineData.getX1(), lineData.getY1());
	    series1.add(lineData.getX2(), lineData.getY2());
	    dataset.addSeries(series1);
	    return dataset;
	}
	
	public void show(){
		SwingUtilities.invokeLater(() -> {
			JFrame uiFrame = new JFrame(PLOTTER_FRAME_TITLE);
			uiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// Create the chart with the plot and a legend
			JFreeChart chart = new JFreeChart(StringUtils.isBlank(chartName)?DATA_CHART_LABEL:chartName, JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		    // Create Panel
		    ChartPanel panel = new ChartPanel(chart);
		    uiFrame.setContentPane(panel);
		    uiFrame.setLocationRelativeTo(null);
			uiFrame.pack();
			uiFrame.setVisible(true);
		});	
	}
}
