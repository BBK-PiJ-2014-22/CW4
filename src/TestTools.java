import java.util.Calendar;
import java.util.GregorianCalendar;


public class TestTools {
	
	/**Creates a new calendar different from today's date by months. Minus for in the past. 
	 * 
	 * @param differenceInMonths The difference from present day in months to assign the calendar
	 * @return A calendar that differs from todays date by the difference
	 */
	public static Calendar createCalendarMonths(int differenceInMonths){
		
		Calendar newCalendar = new GregorianCalendar();
		newCalendar.add(Calendar.MONTH,differenceInMonths);
		
		return newCalendar;
		
	}
	
	public static Calendar createCalendarHours(int differenceInHours){
		
		Calendar newCalendar = new GregorianCalendar();
		newCalendar.add(Calendar.HOUR,differenceInHours);
		
		return newCalendar;
	}
	
	public static Calendar createCalendarSeconds(int differenceInSeconds){
		
		Calendar newCalendar = new GregorianCalendar();
		newCalendar.add(Calendar.SECOND,differenceInSeconds);
		
		return newCalendar;
	}

}
