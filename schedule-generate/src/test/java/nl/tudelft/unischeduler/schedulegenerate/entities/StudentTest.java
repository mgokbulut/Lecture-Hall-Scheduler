
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
package nl.tudelft.unischeduler.schedulegenerate.entities;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.ArgumentMatchers.anyInt;
    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.stubbing.Answer;

public class StudentTest {

    private nl.tudelft.unischeduler.schedulegenerate.entities.Student studentUnderTest;

@Before
public void setUp() throws Exception {
                                studentUnderTest = new Student("netId","type",false,new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime()) ;
}
                
    @Test
    public void testEquals() throws Exception {
    // Setup
        
    // Run the test
 final boolean result =  studentUnderTest.equals("o");

        // Verify the results
 assertThat(result).isTrue() ;
    }
                                                                                    
    @Test
    public void testHashCode() throws Exception {
    // Setup
        
    // Run the test
 final int result =  studentUnderTest.hashCode();

        // Verify the results
 assertThat(result).isEqualTo( 0 ) ;
    }
                                                                                    
    @Test
    public void testCompareTo() throws Exception {
    // Setup
                final nl.tudelft.unischeduler.schedulegenerate.entities.Student otherStudent = new nl.tudelft.unischeduler.schedulegenerate.entities.Student("netId", "type", false, new java.util.GregorianCalendar(2019, java.util.Calendar.JANUARY, 1).getTime());

    // Run the test
 final int result =  studentUnderTest.compareTo(otherStudent);

        // Verify the results
 assertThat(result).isEqualTo( 0 ) ;
    }
                                                                    }

