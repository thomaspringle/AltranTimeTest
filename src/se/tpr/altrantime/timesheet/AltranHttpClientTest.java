package se.tpr.altrantime.timesheet;

import java.io.InputStream;
import java.util.List;

import se.tpr.altrantime.test.R;
import se.tpr.altrantime.timesheet.dt.TimesheetStatus;
import se.tpr.altrantime.timesheet.dt.TimesheetTableBean;
import se.tpr.altrantime.timesheet.dt.TimesheetTableDayBean;
import se.tpr.altrantime.timesheet.dt.TimesheetTableRowBean;
import se.tpr.altrantime.util.AltranHttpClient;
import android.content.Context;
import android.content.res.Resources;
import android.test.ActivityTestCase;

public class AltranHttpClientTest extends ActivityTestCase {

	public void testTimesheetTableParsing() {
		
		Context testContext = getInstrumentation().getContext();
	    Resources testRes = testContext.getResources();
	      
		InputStream inputStream =  testRes.openRawResource(R.raw.timesheet_table);

		String timesheetTableJSON = convertStreamToString(inputStream);
		System.out.println(timesheetTableJSON);
		
		TimesheetTableBean timesheetTableBean = AltranHttpClient.parseTimesheetTableBean(timesheetTableJSON);
		
		assertEquals("Day is not parsed correct", 9, timesheetTableBean.getCurrentDate().getDay());
		assertEquals("Year is not parsed correct", 2013, timesheetTableBean.getCurrentDate().getYear());
		assertEquals("Month is not parsed correct", 10, timesheetTableBean.getCurrentDate().getMonth());
		
		assertEquals("TimesheetStatus is not parsed correct", TimesheetStatus.SIGNED_BY_MANAGER, timesheetTableBean.getTimesheetStatus());
		
		assertEquals("WeekSelection is not parsed correct", 2013, timesheetTableBean.getWeekSelection().getYear());
		assertEquals("WeekSelection is not parsed correct", 32, timesheetTableBean.getWeekSelection().getRights());
		assertEquals("WeekSelection is not parsed correct", "40B", timesheetTableBean.getWeekSelection().getWeek());
		assertEquals("WeekSelection is not parsed correct", "1162", timesheetTableBean.getWeekSelection().getUserLogin());
		
		TimesheetTableRowBean timesheetTableRowBean1 = timesheetTableBean.getTimesheetTableRows().get(0);
	
		assertEquals("Table row is not parsed correct", 11882, timesheetTableRowBean1.getCustomerNo());
		assertEquals("Table row is not parsed correct", 752183, timesheetTableRowBean1.getJobNo());
		assertEquals("Table row is not parsed correct", 664389, timesheetTableRowBean1.getLineId());
		assertEquals("Table row is not parsed correct", "Altran Norway AS /SEK", timesheetTableRowBean1.getCustomer());
		assertEquals("Table row is not parsed correct", "Norsk Kennel Klub", timesheetTableRowBean1.getProject());
		assertEquals("Table row is not parsed correct", "B115", timesheetTableRowBean1.getTaskCode());
		assertEquals("Table row is not parsed correct", "Consultancy hours", timesheetTableRowBean1.getTask());
		assertEquals("Table row is not parsed correct", "bugfix", timesheetTableRowBean1.getDescription());
		
		List<TimesheetTableDayBean> days1 = timesheetTableRowBean1.getDays();
		
		assertEquals("Table row is not parsed correct", 0, days1.get(0).getEntryNo());
		assertEquals("Table row is not parsed correct", 0, days1.get(0).getHours().intValue());
		assertEquals("Table row is not parsed correct", 0, days1.get(1).getEntryNo());
		assertEquals("Table row is not parsed correct", 0, days1.get(1).getHours().intValue());
		assertEquals("Table row is not parsed correct", 664389, days1.get(2).getEntryNo());
		assertEquals("Table row is not parsed correct", 1, days1.get(2).getHours().intValue());
		assertEquals("Table row is not parsed correct", 0, days1.get(3).getEntryNo());
		assertEquals("Table row is not parsed correct", 0, days1.get(3).getHours().intValue());
		assertEquals("Table row is not parsed correct", 0, days1.get(4).getEntryNo());
		assertEquals("Table row is not parsed correct", 0, days1.get(4).getHours().intValue());
		assertEquals("Table row is not parsed correct", 0, days1.get(5).getEntryNo());
		assertEquals("Table row is not parsed correct", 0, days1.get(5).getHours().intValue());
		assertEquals("Table row is not parsed correct", 0, days1.get(6).getEntryNo());
		assertEquals("Table row is not parsed correct", 0, days1.get(6).getHours().intValue());

	}
	
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
