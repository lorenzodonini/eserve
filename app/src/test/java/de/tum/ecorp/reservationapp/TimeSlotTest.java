package de.tum.ecorp.reservationapp;

import org.junit.Test;

import de.tum.ecorp.reservationapp.model.TimeSlot;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeSlotTest {

    @Test
    public void correct_fullHour_ID() throws Exception {

        TimeSlot ts;

        ts = new TimeSlot(0);
        assertEquals(ts.getSlotId(), 0);

        ts = new TimeSlot(1);
        assertEquals(ts.getSlotId(), 2);

        ts = new TimeSlot(2);
        assertEquals(ts.getSlotId(), 4);

        ts = new TimeSlot(8);
        assertEquals(ts.getSlotId(), 16);

        ts = new TimeSlot(15);
        assertEquals(ts.getSlotId(), 30);
    }

    @Test
    public void correct_halfHour_ID() throws Exception {

        TimeSlot ts;

        ts = new TimeSlot(0, 0);
        assertEquals(ts.getSlotId(), 0);

        ts = new TimeSlot(0, 20);
        assertEquals(ts.getSlotId(), 0);

        ts = new TimeSlot(0, 30);
        assertEquals(ts.getSlotId(), 1);

        ts = new TimeSlot(0, 50);
        assertEquals(ts.getSlotId(), 1);

        ts = new TimeSlot(1, 20);
        assertEquals(ts.getSlotId(), 2);

        ts = new TimeSlot(1, 29);
        assertEquals(ts.getSlotId(), 2);

        ts = new TimeSlot(1, 30);
        assertEquals(ts.getSlotId(), 3);

        ts = new TimeSlot(1, 35);
        assertEquals(ts.getSlotId(), 3);

        ts = new TimeSlot(2, 20);
        assertEquals(ts.getSlotId(), 4);

        ts = new TimeSlot(2, 40);
        assertEquals(ts.getSlotId(), 5);

        ts = new TimeSlot(8, 0);
        assertEquals(ts.getSlotId(), 16);

        ts = new TimeSlot(15, 59);
        assertEquals(ts.getSlotId(), 31);
    }

    @Test
    public void toString_format_correct() throws Exception {

        TimeSlot ts;

        ts = new TimeSlot(13, 20);
        assertArrayEquals(ts.toString().toCharArray(), "13:00".toCharArray());

        ts = new TimeSlot(13, 40);
        assertArrayEquals(ts.toString().toCharArray(), "13:30".toCharArray());

        ts = new TimeSlot(7, 20);
        assertArrayEquals(ts.toString().toCharArray(), "7:00".toCharArray());

        ts = new TimeSlot(7, 50);
        assertArrayEquals(ts.toString().toCharArray(), "7:30".toCharArray());

        ts = new TimeSlot(13, 59);
        assertArrayEquals(ts.toString().toCharArray(), "13:30".toCharArray());
    }

}
