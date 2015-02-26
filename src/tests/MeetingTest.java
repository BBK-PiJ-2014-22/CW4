package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import manager.Meeting;
import manager.MeetingImpl;

@RunWith(Parameterized.class)
public class MeetingTest {

	Meeting meeting;
	int id;
	int year;
	int month;
	int day;
	
	/**Parameters should be the class of meeting being tested, the meeting ID, year, month, date
	 * 
	 * The constructor will create an object of the class type for testing.
	 * 
	 * @return
	 */
	
	@Parameters
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {     
                { MeetingImpl.class, 1, 2015, 10, 5}     
          });
		
	}
	
	public MeetingTest(Class testClass, int id, int year, int month, int day){
		
		Constructor<testClass> con = testClass.getConstructor(int.class, Calendar.class);
		
		
		this.meeting = meeting;
		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	@Before
	public void setUp(){
		this.meeting = new MeetingImpl(1,(Calendar) new GregorianCalendar(2015, 8, 30));
	}

	@Test
	public void testGetID() {
	
	
	}

}
