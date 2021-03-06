package com.github.antag99.retinazer.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntBagTest {

    /**
     * Ensures that the elements of a bag are actually stored
     */
    @Test
    public void testStorage() {
        IntBag bag = new IntBag();

        bag.set(0, 0);
        assertEquals(0, bag.get(0));
        assertEquals(0, bag.get(1));
        assertEquals(0, bag.get(2));
        assertEquals(0, bag.get(3));
        assertEquals(0, bag.get(4));
        assertEquals(0, bag.get(5));
        assertEquals(0, bag.get(6));
        assertEquals(0, bag.get(7));

        bag.set(1, 1);
        assertEquals(0, bag.get(0));
        assertEquals(1, bag.get(1));
        assertEquals(0, bag.get(2));
        assertEquals(0, bag.get(3));
        assertEquals(0, bag.get(4));
        assertEquals(0, bag.get(5));
        assertEquals(0, bag.get(6));
        assertEquals(0, bag.get(7));

        bag.set(2, 2);
        assertEquals(0, bag.get(0));
        assertEquals(1, bag.get(1));
        assertEquals(2, bag.get(2));
        assertEquals(0, bag.get(3));
        assertEquals(0, bag.get(4));
        assertEquals(0, bag.get(5));
        assertEquals(0, bag.get(6));
        assertEquals(0, bag.get(7));

        bag.set(3, 3);
        assertEquals(0, bag.get(0));
        assertEquals(1, bag.get(1));
        assertEquals(2, bag.get(2));
        assertEquals(3, bag.get(3));
        assertEquals(0, bag.get(4));
        assertEquals(0, bag.get(5));
        assertEquals(0, bag.get(6));
        assertEquals(0, bag.get(7));

        bag.set(4, 4);
        assertEquals(0, bag.get(0));
        assertEquals(1, bag.get(1));
        assertEquals(2, bag.get(2));
        assertEquals(3, bag.get(3));
        assertEquals(4, bag.get(4));
        assertEquals(0, bag.get(5));
        assertEquals(0, bag.get(6));
        assertEquals(0, bag.get(7));

        bag.set(5, 5);
        assertEquals(0, bag.get(0));
        assertEquals(1, bag.get(1));
        assertEquals(2, bag.get(2));
        assertEquals(3, bag.get(3));
        assertEquals(4, bag.get(4));
        assertEquals(5, bag.get(5));
        assertEquals(0, bag.get(6));
        assertEquals(0, bag.get(7));

        bag.set(6, 6);
        assertEquals(0, bag.get(0));
        assertEquals(1, bag.get(1));
        assertEquals(2, bag.get(2));
        assertEquals(3, bag.get(3));
        assertEquals(4, bag.get(4));
        assertEquals(5, bag.get(5));
        assertEquals(6, bag.get(6));
        assertEquals(0, bag.get(7));

        bag.set(7, 7);
        assertEquals(0, bag.get(0));
        assertEquals(1, bag.get(1));
        assertEquals(2, bag.get(2));
        assertEquals(3, bag.get(3));
        assertEquals(4, bag.get(4));
        assertEquals(5, bag.get(5));
        assertEquals(6, bag.get(6));
        assertEquals(7, bag.get(7));

        bag.clear();

        assertEquals(0, bag.get(0));
        assertEquals(0, bag.get(1));
        assertEquals(0, bag.get(2));
        assertEquals(0, bag.get(3));
        assertEquals(0, bag.get(4));
        assertEquals(0, bag.get(5));
        assertEquals(0, bag.get(6));
        assertEquals(0, bag.get(7));
    }

    /**
     * Ensures that the bag contains the default value by default
     */
    @Test
    public void testDefault() {
        IntBag bag = new IntBag();
        assertEquals(0, bag.get(0));
        bag.set(0, 1);
        assertEquals(0, bag.get(1));
        assertEquals(0, bag.get(2));
        assertEquals(0, bag.get(3));
    }

    /**
     * Ensures that the bag resizes correctly when out of capacity, that it
     * does not resize when queried for non-existing elements, and that it does
     * not resize when the default value is set.
     */
    @Test
    public void testCapacity() {
        IntBag bag;

        bag = new IntBag();
        assertEquals(0, bag.getCapacity());
        bag.set(0, 1);
        assertEquals(1, bag.getCapacity());
        bag.set(1, 2);
        assertEquals(2, bag.getCapacity());
        bag.set(2, 3);
        assertEquals(4, bag.getCapacity());
        bag.set(3, 4);
        assertEquals(4, bag.getCapacity());
        bag.set(4, 5);
        assertEquals(8, bag.getCapacity());
        bag.set(8, 6);
        assertEquals(16, bag.getCapacity());
        bag.set(35, 7);
        assertEquals(64, bag.getCapacity());

        bag = new IntBag();
        for (int i = 0; i < 32; i++) {
            bag.get((1 << i) - 1);
            assertEquals(0, bag.getCapacity());
        }
        bag.get(Integer.MAX_VALUE);
        assertEquals(0, bag.getCapacity());

        bag = new IntBag();
        for (int i = 0; i < 31; i++) {
            bag.set((1 << i) - 1, 0);
            assertEquals(0, bag.getCapacity());
        }
        bag.set(Integer.MAX_VALUE, 0);
        assertEquals(0, bag.getCapacity());
    }

    /**
     * When a negative index is used, an {@link ArrayIndexOutOfBoundsException} should be thrown.
     */
    @Test
    public void testArrayIndexOutOfBoundsException() {
        IntBag bag = new IntBag();
        for (int i = 0; i < 32; i++) {
            try {
                bag.set(-(1 << i), 0);
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
