package seedu.contax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.contax.testutil.Assert.assertThrows;
import static seedu.contax.testutil.TypicalAppointments.APPOINTMENT_ALICE;
import static seedu.contax.testutil.TypicalAppointments.APPOINTMENT_ALONE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.contax.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.contax.model.appointment.exceptions.OverlappingAppointmentException;
import seedu.contax.testutil.AppointmentBuilder;

public class DisjointAppointmentListTest {

    private final DisjointAppointmentList appointmentList = new DisjointAppointmentList();

    @Test
    public void contains_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.contains(null));
    }

    @Test
    public void contains_appointmentNotInList_returnsFalse() {
        assertFalse(appointmentList.contains(APPOINTMENT_ALICE));
    }

    @Test
    public void contains_appointmentInList_returnsTrue() {
        appointmentList.add(APPOINTMENT_ALICE);
        assertTrue(appointmentList.contains(APPOINTMENT_ALICE));
    }

    @Test
    public void contains_duplicatedAppointmentInList_returnsTrue() {
        appointmentList.add(APPOINTMENT_ALICE);
        Appointment duplicate = new AppointmentBuilder(APPOINTMENT_ALICE).build();
        assertTrue(appointmentList.contains(duplicate));
    }

    @Test
    public void containsOverlapping_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.containsOverlapping(null));
    }

    @Test
    public void containsOverlapping_appointmentAlreadyInList_returnsTrue() {
        appointmentList.add(APPOINTMENT_ALICE);
        assertTrue(appointmentList.containsOverlapping(APPOINTMENT_ALICE));
    }

    @Test
    public void containsOverlapping_duplicatedAppointmentInList_returnsTrue() {
        appointmentList.add(APPOINTMENT_ALICE);
        Appointment duplicate = new AppointmentBuilder(APPOINTMENT_ALICE).build();
        assertTrue(appointmentList.containsOverlapping(duplicate));
    }

    @Test
    public void containsOverlapping_overlappingAppointmentsInList_returnsTrue() {
        appointmentList.add(APPOINTMENT_ALICE);
        Appointment editedAppointment = new AppointmentBuilder(APPOINTMENT_ALICE)
                .withStartDateTime(APPOINTMENT_ALICE.getStartDateTime().value.plusMinutes(10)).build();
        assertTrue(appointmentList.containsOverlapping(editedAppointment));
    }

    @Test
    public void containsOverlapping_disjointAppointmentsInList_returnsFalse() {
        appointmentList.add(APPOINTMENT_ALICE);
        assertFalse(appointmentList.containsOverlapping(APPOINTMENT_ALONE));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.add(null));
    }

    @Test
    public void add_overlappingAppointment_throwsOverlappingAppointmentException() {
        appointmentList.add(APPOINTMENT_ALICE);
        assertThrows(OverlappingAppointmentException.class, () -> appointmentList.add(APPOINTMENT_ALICE));

        Appointment editedAppointment = new AppointmentBuilder(APPOINTMENT_ALICE)
                .withName("Another Meeting")
                .withStartDateTime(APPOINTMENT_ALICE.getStartDateTime().value.plusMinutes(1)).build();
        assertThrows(OverlappingAppointmentException.class, () -> appointmentList.add(editedAppointment));
    }

    @Test
    public void add_unsortedNewAppointment_successSortsPosition() {
        LocalDateTime baseDateTime = APPOINTMENT_ALONE.getStartDateTime().value;
        appointmentList.add(APPOINTMENT_ALONE);

        Appointment beforeAppointment = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(2)).build();
        Appointment afterAppointment = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.plusDays(2)).build();

        appointmentList.add(beforeAppointment);
        assertEquals(beforeAppointment, appointmentList.asUnmodifiableObservableList().get(0));
        assertEquals(APPOINTMENT_ALONE, appointmentList.asUnmodifiableObservableList().get(1));

        appointmentList.add(afterAppointment);
        assertEquals(beforeAppointment, appointmentList.asUnmodifiableObservableList().get(0));
        assertEquals(APPOINTMENT_ALONE, appointmentList.asUnmodifiableObservableList().get(1));
        assertEquals(afterAppointment, appointmentList.asUnmodifiableObservableList().get(2));
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> appointmentList.setAppointment(null, APPOINTMENT_ALICE));
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> appointmentList.setAppointment(APPOINTMENT_ALICE, null));
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, ()
            -> appointmentList.setAppointment(APPOINTMENT_ALICE, APPOINTMENT_ALONE));
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        appointmentList.add(APPOINTMENT_ALICE);
        appointmentList.setAppointment(APPOINTMENT_ALICE, APPOINTMENT_ALICE);
        DisjointAppointmentList expectedAppointmentList = new DisjointAppointmentList();
        expectedAppointmentList.add(APPOINTMENT_ALICE);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentIsOverlappingWithOnlyTarget_success() {
        appointmentList.add(APPOINTMENT_ALICE);
        Appointment editedAppointment = new AppointmentBuilder(APPOINTMENT_ALICE)
                .withName("Another Meeting")
                .withStartDateTime(APPOINTMENT_ALICE.getStartDateTime().value.plusMinutes(1)).build();

        appointmentList.setAppointment(APPOINTMENT_ALICE, editedAppointment);
        DisjointAppointmentList expectedAppointmentList = new DisjointAppointmentList();
        expectedAppointmentList.add(editedAppointment);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentDisjoint_success() {
        appointmentList.add(APPOINTMENT_ALICE);
        Appointment disjointAppointment = new AppointmentBuilder(APPOINTMENT_ALICE)
                .withName("Another Meeting")
                .withStartDateTime(APPOINTMENT_ALICE.getStartDateTime().value.plusYears(1)).build();

        appointmentList.setAppointment(APPOINTMENT_ALICE, disjointAppointment);
        DisjointAppointmentList expectedAppointmentList = new DisjointAppointmentList();
        expectedAppointmentList.add(disjointAppointment);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentDisjointDifferentOrdering_successSortsPosition() {
        LocalDateTime baseDateTime = APPOINTMENT_ALONE.getStartDateTime().value;
        Appointment modifiedAloneAppointment = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(1)).build();

        appointmentList.add(modifiedAloneAppointment);
        appointmentList.add(APPOINTMENT_ALONE);

        Appointment beforeAppointment = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withName("Another Meeting")
                .withStartDateTime(baseDateTime.minusDays(2)).build();
        Appointment afterAppointment = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withName("Another Meeting")
                .withStartDateTime(baseDateTime.plusDays(2)).build();

        appointmentList.setAppointment(APPOINTMENT_ALONE, beforeAppointment);
        assertEquals(beforeAppointment, appointmentList.asUnmodifiableObservableList().get(0));
        assertEquals(modifiedAloneAppointment, appointmentList.asUnmodifiableObservableList().get(1));

        appointmentList.setAppointment(beforeAppointment, afterAppointment);
        assertEquals(modifiedAloneAppointment, appointmentList.asUnmodifiableObservableList().get(0));
        assertEquals(afterAppointment, appointmentList.asUnmodifiableObservableList().get(1));

        appointmentList.add(beforeAppointment);
        appointmentList.setAppointment(modifiedAloneAppointment, modifiedAloneAppointment);
        assertEquals(beforeAppointment, appointmentList.asUnmodifiableObservableList().get(0));
        assertEquals(modifiedAloneAppointment, appointmentList.asUnmodifiableObservableList().get(1));
        assertEquals(afterAppointment, appointmentList.asUnmodifiableObservableList().get(2));

        appointmentList.setAppointment(afterAppointment, afterAppointment);
        assertEquals(beforeAppointment, appointmentList.asUnmodifiableObservableList().get(0));
        assertEquals(modifiedAloneAppointment, appointmentList.asUnmodifiableObservableList().get(1));
        assertEquals(afterAppointment, appointmentList.asUnmodifiableObservableList().get(2));
    }

    @Test
    public void setAppointment_editedAppointmentOverlaps_throwsOverlappingAppointmentException() {
        Appointment disjointAppointment = new AppointmentBuilder(APPOINTMENT_ALICE)
                .withName("Another Meeting")
                .withStartDateTime(APPOINTMENT_ALICE.getStartDateTime().value.plusYears(1)).build();
        appointmentList.add(APPOINTMENT_ALICE);
        appointmentList.add(disjointAppointment);

        assertThrows(OverlappingAppointmentException.class, ()
            -> appointmentList.setAppointment(APPOINTMENT_ALICE, disjointAppointment));
    }

    @Test
    public void setAppointments_nullDisjointAppointmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> appointmentList.setAppointments((DisjointAppointmentList) null));
    }

    @Test
    public void setAppointments_anotherDisjointAppointmentList_replacesOwnDataWithSuppliedList() {
        appointmentList.add(APPOINTMENT_ALICE);
        DisjointAppointmentList expectedAppointmentList = new DisjointAppointmentList();
        expectedAppointmentList.add(APPOINTMENT_ALONE);
        appointmentList.setAppointments(expectedAppointmentList);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> appointmentList.setAppointments((List<Appointment>) null));
    }

    @Test
    public void setAppointments_list_replacesOwnListWithProvidedList() {
        appointmentList.add(APPOINTMENT_ALICE);
        List<Appointment> appointmentArrayList = new ArrayList<>();
        appointmentArrayList.add(APPOINTMENT_ALONE);
        appointmentList.setAppointments(appointmentArrayList);

        DisjointAppointmentList expectedAppointmentList = new DisjointAppointmentList();
        expectedAppointmentList.add(APPOINTMENT_ALONE);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointments_unsortedList_sortsListUponSetting() {
        appointmentList.add(APPOINTMENT_ALICE);
        List<Appointment> appointmentArrayList = new ArrayList<>();
        LocalDateTime baseDateTime = APPOINTMENT_ALONE.getStartDateTime().value;
        appointmentArrayList.add(APPOINTMENT_ALONE);
        appointmentArrayList.add(new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(1)).build());
        appointmentArrayList.add(new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(2)).build());
        appointmentArrayList.add(new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(3)).build());
        appointmentList.setAppointments(appointmentArrayList);

        DisjointAppointmentList expectedAppointmentList = new DisjointAppointmentList();
        expectedAppointmentList.add(new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(3)).build());
        expectedAppointmentList.add(new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(2)).build());
        expectedAppointmentList.add(new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(baseDateTime.minusDays(1)).build());
        expectedAppointmentList.add(APPOINTMENT_ALONE);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointments_listWithDuplicateAppointments_throwsOverlappingAppointmentException() {
        List<Appointment> badList = Arrays.asList(APPOINTMENT_ALICE, APPOINTMENT_ALICE);
        assertThrows(OverlappingAppointmentException.class, () -> appointmentList.setAppointments(badList));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> appointmentList.asUnmodifiableObservableList().remove(0));
        assertThrows(UnsupportedOperationException.class, ()
            -> appointmentList.asUnmodifiableObservableList().add(APPOINTMENT_ALICE));
    }

    @Test
    public void removeAppointment_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.remove(null));
    }

    @Test
    public void removeAppointment_appointmentNotInList_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, ()
            -> appointmentList.remove(APPOINTMENT_ALICE));
    }

    @Test
    public void removeAppointment_appointmentInList_success() {
        appointmentList.add(APPOINTMENT_ALONE);
        appointmentList.remove(APPOINTMENT_ALONE);
        assertEquals(new DisjointAppointmentList(), appointmentList);
    }

    @Test
    public void findSlotsBetweenAppointments_nullInputs_throwsNullPointerException() {
        LocalDateTime refDateTime = LocalDateTime.parse("2022-12-12T12:30");
        assertThrows(NullPointerException.class,
            () -> new DisjointAppointmentList().findSlotsBetweenAppointments(null, refDateTime, 1));
        assertThrows(NullPointerException.class,
            () -> new DisjointAppointmentList().findSlotsBetweenAppointments(refDateTime, null, 1));
    }

    @Test
    public void findSlotsBetweenAppointments_nonPositiveDuration_throwsIllegalArgumentException() {
        LocalDateTime refDateTime = LocalDateTime.parse("2022-12-12T12:30");
        assertThrows(IllegalArgumentException.class,
                () -> new DisjointAppointmentList().findSlotsBetweenAppointments(refDateTime, refDateTime, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new DisjointAppointmentList().findSlotsBetweenAppointments(refDateTime, refDateTime, -1));
    }

    @Test
    public void findSlotsBetweenAppointments_rangeLargerThanOrEqualToSlot_success() {
        LocalDateTime exactRangeStart = LocalDateTime.parse("2022-12-12T12:30");
        LocalDateTime exactRangeEnd = LocalDateTime.parse("2022-12-12T14:30");
        LocalDateTime largerRangeStart = exactRangeStart.minusMinutes(1);
        LocalDateTime largerRangeEnd = exactRangeEnd.plusMinutes(1);

        Appointment appointment1 = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(LocalDateTime.parse("2022-12-12T12:00"))
                .withDuration(30).build();
        Appointment appointment2 = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(LocalDateTime.parse("2022-12-12T14:30"))
                .withDuration(30).build();

        DisjointAppointmentList refList = new DisjointAppointmentList();
        refList.add(appointment1);
        refList.add(appointment2);

        List<Appointment> expectedList = List.of(appointment1);

        assertEquals(expectedList, refList.findSlotsBetweenAppointments(exactRangeStart, exactRangeEnd, 120));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(exactRangeStart, exactRangeEnd, 60));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(exactRangeStart, exactRangeEnd, 1));

        assertEquals(List.of(), refList.findSlotsBetweenAppointments(largerRangeStart, largerRangeEnd, 121));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(largerRangeStart, largerRangeEnd, 120));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(largerRangeStart, largerRangeEnd, 60));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(largerRangeStart, largerRangeEnd, 1));
    }

    @Test
    public void findSlotsBetweenAppointments_rangeSmallerThanSlot_success() {
        LocalDateTime exactRangeStart = LocalDateTime.parse("2022-12-12T12:30");
        LocalDateTime exactRangeEnd = LocalDateTime.parse("2022-12-12T14:30");
        LocalDateTime smallerRangeStart = exactRangeStart.plusMinutes(1);
        LocalDateTime smallerRangeEnd = exactRangeEnd.minusMinutes(1);
        LocalDateTime largerRangeStart = exactRangeStart.minusMinutes(1);
        LocalDateTime largerRangeEnd = exactRangeEnd.plusMinutes(1);

        Appointment appointment1 = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(LocalDateTime.parse("2022-12-12T12:00"))
                .withDuration(30).build();
        Appointment appointment2 = new AppointmentBuilder(APPOINTMENT_ALONE)
                .withStartDateTime(LocalDateTime.parse("2022-12-12T14:30"))
                .withDuration(30).build();

        DisjointAppointmentList refList = new DisjointAppointmentList();
        refList.add(appointment1);
        refList.add(appointment2);

        List<Appointment> expectedList = List.of(appointment1);

        assertEquals(List.of(), refList.findSlotsBetweenAppointments(smallerRangeStart, exactRangeEnd, 120));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(smallerRangeStart, exactRangeEnd, 119));

        assertEquals(List.of(), refList.findSlotsBetweenAppointments(exactRangeStart, smallerRangeEnd, 120));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(exactRangeStart, smallerRangeEnd, 119));

        assertEquals(List.of(), refList.findSlotsBetweenAppointments(smallerRangeStart, smallerRangeEnd, 119));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(smallerRangeStart, smallerRangeEnd, 118));

        assertEquals(List.of(), refList.findSlotsBetweenAppointments(smallerRangeStart, largerRangeEnd, 120));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(smallerRangeStart, largerRangeEnd, 119));
        assertEquals(List.of(), refList.findSlotsBetweenAppointments(largerRangeStart, smallerRangeEnd, 120));
        assertEquals(expectedList, refList.findSlotsBetweenAppointments(largerRangeStart, smallerRangeEnd, 119));
    }

    @Test
    public void equals() {
        DisjointAppointmentList refList = new DisjointAppointmentList();
        DisjointAppointmentList list2 = new DisjointAppointmentList();
        list2.add(APPOINTMENT_ALICE);
        DisjointAppointmentList list3 = new DisjointAppointmentList();
        list3.add(APPOINTMENT_ALICE);

        assertTrue(refList.equals(refList));
        assertTrue(refList.equals(new DisjointAppointmentList()));
        assertTrue(list2.equals(list3));

        assertFalse(refList.equals(null));
        assertFalse(refList.equals(0));
        assertFalse(refList.equals(list2));
    }

    @Test
    public void hashCodeTest() {
        DisjointAppointmentList refList = new DisjointAppointmentList();
        DisjointAppointmentList list2 = new DisjointAppointmentList();
        list2.add(APPOINTMENT_ALICE);
        DisjointAppointmentList list3 = new DisjointAppointmentList();
        list3.add(APPOINTMENT_ALICE);

        assertEquals(refList.hashCode(), refList.hashCode());
        assertEquals(refList.hashCode(), new DisjointAppointmentList().hashCode());
        assertEquals(list2.hashCode(), list3.hashCode());

        assertNotEquals(refList.hashCode(), list2.hashCode());
    }

    @Test
    public void iterator() {
        DisjointAppointmentList refList = new DisjointAppointmentList();

        refList.add(APPOINTMENT_ALICE);
        refList.add(APPOINTMENT_ALONE);

        HashSet<Appointment> result = new HashSet<>();
        for (Appointment x : refList) {
            result.add(x);
        }

        assertTrue(result.contains(APPOINTMENT_ALONE));
        assertTrue(result.contains(APPOINTMENT_ALICE));
    }
}
