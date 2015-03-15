import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class MeetingCopyConstructorTests {

	/**Parameters are the meeting to be passed to the copy constructors
	 * 
	 * @return meeting for copy constructor
	 */
	@Parameters
	public Collection<Object[]> data(){
		
		Set<Contact> contacts = new HashSet<Contact>();
		
		contacts.add(new ContactImpl(0, "John Smith", "Notes"));
	
		return Arrays.asList(new Object[][]{
				{new MeetingImpl(0, TestTools.createCalendarHours(1), contacts)},
				{new FutureMeetingImpl(0, TestTools.createCalendarHours(1), contacts)},
				{new PastMeetingImpl(0, TestTools.createCalendarHours(-1), contacts, "Notes")}
		});
	}
	
	Meeting original;
	
	public MeetingCopyConstructorTests(Meeting meeting){
		this.original = meeting;
	}
	
	@Test
	public void test() {
		PastMeeting copy = new PastMeetingImpl(this.original);
		assertEquals(original, copy);
	}

}
