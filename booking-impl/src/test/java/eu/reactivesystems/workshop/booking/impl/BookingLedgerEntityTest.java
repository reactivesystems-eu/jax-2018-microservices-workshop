package eu.reactivesystems.workshop.booking.impl;

import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.UUID;

public class BookingLedgerEntityTest {


    private static ActorSystem system;
    private PersistentEntityTestDriver<BookingCommand, PBookingEvent, PBookingLedgerState> driver;
    private UUID accommodation;
    private UUID host;
    private UUID guest;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Before
    public void createTestDriver() {
        accommodation = UUID.randomUUID();
        host = UUID.randomUUID();
        guest = UUID.randomUUID();
        driver = new PersistentEntityTestDriver<>(system, new BookingLedgerEntity(), accommodation.toString());
    }

    /*

    @Test
    public void testNotCreated() {
        PersistentEntityTestDriver.Outcome<PBookingEvent, PBookingLedgerState> outcome = driver.run(
                new RequestBooking(guest, LocalDate.now().plusDays(5), 3, 1)
        );
        assertThat(outcome.state().getStatus(), equalTo(PBookingLedgerStatus.NOT_CREATED));
        assertEquals(0, outcome.events().size());
        assertEquals(1, outcome.issues().size());
        assertEquals(PersistentEntityTestDriver.UnhandledCommand.class,
                outcome.issues().get(0).getClass());
    }

    @Test
    public void testCreateBookingLedger() {
        CreateBookingLedger createCommand = new CreateBookingLedger(
                new Listing(accommodation, host));
                PersistentEntityTestDriver.Outcome<PBookingEvent, PBookingLedgerState> outcome = driver.run(createCommand);
        assertThat(outcome.state().getStatus(), equalTo(PBookingLedgerStatus.LISTED));
        assertThat(outcome.events(), hasItem(new PBookingEvent.BookingLedgerCreated(accommodation, host)));
        assertEquals(Collections.emptyList(), outcome.issues());
    }

*/
}
