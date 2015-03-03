import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class ContactManagerAddMeetingTest {
	
	/**
	 * 1) Meeting date
	 * 2) Number of contacts
	 * 3) 
	 * 
	 * @return Object[] of Parameters
	 */
	@Parameters
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {
				{},
				{}
		});
	}

	@Before
	public void setUp() throws Exception {
	}

	/*Add FutureMeeting tests:
	 * 
	 * 1) Past meeting added, in the past, contacts correct
	 * 2) Past meeting added in the future
	 * 3) Past meeting added with incorrect contacts 
	 * 4) Past meeting added in the past, no contacts
	 * 
	 * 5) Future meeting added, in the future, contacts correct
	 * 6) Future meeting added in the past
	 * 7) Future meeting added in the future, no contacts
	 * 8) Future meeting added with incorrect contacts
	 * 
	 * 9) Several meetings added (both types), meeting ID increases
	 */
	
	

}
