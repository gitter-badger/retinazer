package com.github.antag99.retinazer.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShortBagTest {

    /**
     * Ensures that the elements of a bag are actually stored
     */
    @Test
    public void testStorage() {
        ShortBag bag = new ShortBag();

        bag.set(0, (short) 0);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 0, bag.get(1));
        assertEquals((short) 0, bag.get(2));
        assertEquals((short) 0, bag.get(3));
        assertEquals((short) 0, bag.get(4));
        assertEquals((short) 0, bag.get(5));
        assertEquals((short) 0, bag.get(6));
        assertEquals((short) 0, bag.get(7));

        bag.set(1, (short) 1);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 1, bag.get(1));
        assertEquals((short) 0, bag.get(2));
        assertEquals((short) 0, bag.get(3));
        assertEquals((short) 0, bag.get(4));
        assertEquals((short) 0, bag.get(5));
        assertEquals((short) 0, bag.get(6));
        assertEquals((short) 0, bag.get(7));

        bag.set(2, (short) 2);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 1, bag.get(1));
        assertEquals((short) 2, bag.get(2));
        assertEquals((short) 0, bag.get(3));
        assertEquals((short) 0, bag.get(4));
        assertEquals((short) 0, bag.get(5));
        assertEquals((short) 0, bag.get(6));
        assertEquals((short) 0, bag.get(7));

        bag.set(3, (short) 3);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 1, bag.get(1));
        assertEquals((short) 2, bag.get(2));
        assertEquals((short) 3, bag.get(3));
        assertEquals((short) 0, bag.get(4));
        assertEquals((short) 0, bag.get(5));
        assertEquals((short) 0, bag.get(6));
        assertEquals((short) 0, bag.get(7));

        bag.set(4, (short) 4);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 1, bag.get(1));
        assertEquals((short) 2, bag.get(2));
        assertEquals((short) 3, bag.get(3));
        assertEquals((short) 4, bag.get(4));
        assertEquals((short) 0, bag.get(5));
        assertEquals((short) 0, bag.get(6));
        assertEquals((short) 0, bag.get(7));

        bag.set(5, (short) 5);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 1, bag.get(1));
        assertEquals((short) 2, bag.get(2));
        assertEquals((short) 3, bag.get(3));
        assertEquals((short) 4, bag.get(4));
        assertEquals((short) 5, bag.get(5));
        assertEquals((short) 0, bag.get(6));
        assertEquals((short) 0, bag.get(7));

        bag.set(6, (short) 6);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 1, bag.get(1));
        assertEquals((short) 2, bag.get(2));
        assertEquals((short) 3, bag.get(3));
        assertEquals((short) 4, bag.get(4));
        assertEquals((short) 5, bag.get(5));
        assertEquals((short) 6, bag.get(6));
        assertEquals((short) 0, bag.get(7));

        bag.set(7, (short) 7);
        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 1, bag.get(1));
        assertEquals((short) 2, bag.get(2));
        assertEquals((short) 3, bag.get(3));
        assertEquals((short) 4, bag.get(4));
        assertEquals((short) 5, bag.get(5));
        assertEquals((short) 6, bag.get(6));
        assertEquals((short) 7, bag.get(7));

        bag.clear();

        assertEquals((short) 0, bag.get(0));
        assertEquals((short) 0, bag.get(1));
        assertEquals((short) 0, bag.get(2));
        assertEquals((short) 0, bag.get(3));
        assertEquals((short) 0, bag.get(4));
        assertEquals((short) 0, bag.get(5));
        assertEquals((short) 0, bag.get(6));
        assertEquals((short) 0, bag.get(7));
    }

    /**
     * Ensures that the bag contains the default value by default
     */
    @Test
    public void testDefault() {
        ShortBag bag = new ShortBag();
        assertEquals((short) 0, bag.get(0));
        bag.set(0, (short) 1);
        assertEquals((short) 0, bag.get(1));
        assertEquals((short) 0, bag.get(2));
        assertEquals((short) 0, bag.get(3));
    }

    /**
     * Ensures that the bag resizes correctly when out of capacity, that it
     * does not resize when queried for non-existing elements, and that it does
     * not resize when the default value is set.
     */
    @Test
    public void testCapacity() {
        ShortBag bag;

        bag = new ShortBag();
        assertEquals(0, bag.getCapacity());
        bag.set(0, (short) 1);
        assertEquals(1, bag.getCapacity());
        bag.set(1, (short) 2);
        assertEquals(2, bag.getCapacity());
        bag.set(2, (short) 3);
        assertEquals(4, bag.getCapacity());
        bag.set(3, (short) 4);
        assertEquals(4, bag.getCapacity());
        bag.set(4, (short) 5);
        assertEquals(8, bag.getCapacity());
        bag.set(8, (short) 6);
        assertEquals(16, bag.getCapacity());
        bag.set(35, (short) 7);
        assertEquals(64, bag.getCapacity());

        bag = new ShortBag();
        for (int i = 0; i < 32; i++) {
            bag.get((1 << i) - 1);
            assertEquals(0, bag.getCapacity());
        }
        bag.get(Integer.MAX_VALUE);
        assertEquals(0, bag.getCapacity());

        bag = new ShortBag();
        for (int i = 0; i < 31; i++) {
            bag.set((1 << i) - 1, (short) 0);
            assertEquals(0, bag.getCapacity());
        }
        bag.set(Integer.MAX_VALUE, (short) 0);
        assertEquals(0, bag.getCapacity());
    }

    /**
     * When a negative index is used, an {@link ArrayIndexOutOfBoundsException} should be thrown.
     */
    @Test
    public void testArrayIndexOutOfBoundsException() {
        ShortBag bag = new ShortBag();
        for (int i = 0; i < 32; i++) {
            try {
                bag.set(-(1 << i), (short) 0);
            } catch (ArrayIndexOutOfBoundsException ex) {
                continue;
            }

            fail("ArrayIndexOutOfBoundsException expected for index " + (-(1 << i)));
        }

        for (int i = 0; i < 32; i++) {
            try {
                bag.get(-(1 << i));
            } catch (ArrayIndexOutOfBoundsException ex) {
                continue;
            }

            fail("ArrayIndexOutOfBoundsException expected for index " + (-(1 << i)));
        }
    }
}
