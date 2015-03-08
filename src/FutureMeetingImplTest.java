import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;


public class FutureMeetingImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPastDate() {
		new FutureMeetingImpl(0, TestTools.createCalendar(-1), new HashSet<Contact>());
	}

}
