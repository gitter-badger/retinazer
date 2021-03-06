package com.github.antag99.retinazer.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DoubleBagTest {

    /**
     * Ensures that the elements of a bag are actually stored
     */
    @Test
    public void testStorage() {
        DoubleBag bag = new DoubleBag();

        bag.set(0, 0d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(0d, bag.get(1), 0d);
        assertEquals(0d, bag.get(2), 0d);
        assertEquals(0d, bag.get(3), 0d);
        assertEquals(0d, bag.get(4), 0d);
        assertEquals(0d, bag.get(5), 0d);
        assertEquals(0d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);

        bag.set(1, 1d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(1d, bag.get(1), 0d);
        assertEquals(0d, bag.get(2), 0d);
        assertEquals(0d, bag.get(3), 0d);
        assertEquals(0d, bag.get(4), 0d);
        assertEquals(0d, bag.get(5), 0d);
        assertEquals(0d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);

        bag.set(2, 2d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(1d, bag.get(1), 0d);
        assertEquals(2d, bag.get(2), 0d);
        assertEquals(0d, bag.get(3), 0d);
        assertEquals(0d, bag.get(4), 0d);
        assertEquals(0d, bag.get(5), 0d);
        assertEquals(0d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);

        bag.set(3, 3d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(1d, bag.get(1), 0d);
        assertEquals(2d, bag.get(2), 0d);
        assertEquals(3d, bag.get(3), 0d);
        assertEquals(0d, bag.get(4), 0d);
        assertEquals(0d, bag.get(5), 0d);
        assertEquals(0d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);

        bag.set(4, 4d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(1d, bag.get(1), 0d);
        assertEquals(2d, bag.get(2), 0d);
        assertEquals(3d, bag.get(3), 0d);
        assertEquals(4d, bag.get(4), 0d);
        assertEquals(0d, bag.get(5), 0d);
        assertEquals(0d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);

        bag.set(5, 5d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(1d, bag.get(1), 0d);
        assertEquals(2d, bag.get(2), 0d);
        assertEquals(3d, bag.get(3), 0d);
        assertEquals(4d, bag.get(4), 0d);
        assertEquals(5d, bag.get(5), 0d);
        assertEquals(0d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);

        bag.set(6, 6d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(1d, bag.get(1), 0d);
        assertEquals(2d, bag.get(2), 0d);
        assertEquals(3d, bag.get(3), 0d);
        assertEquals(4d, bag.get(4), 0d);
        assertEquals(5d, bag.get(5), 0d);
        assertEquals(6d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);

        bag.set(7, 7d);
        assertEquals(0d, bag.get(0), 0d);
        assertEquals(1d, bag.get(1), 0d);
        assertEquals(2d, bag.get(2), 0d);
        assertEquals(3d, bag.get(3), 0d);
        assertEquals(4d, bag.get(4), 0d);
        assertEquals(5d, bag.get(5), 0d);
        assertEquals(6d, bag.get(6), 0d);
        assertEquals(7d, bag.get(7), 0d);

        bag.clear();

        assertEquals(0d, bag.get(0), 0d);
        assertEquals(0d, bag.get(1), 0d);
        assertEquals(0d, bag.get(2), 0d);
        assertEquals(0d, bag.get(3), 0d);
        assertEquals(0d, bag.get(4), 0d);
        assertEquals(0d, bag.get(5), 0d);
        assertEquals(0d, bag.get(6), 0d);
        assertEquals(0d, bag.get(7), 0d);
    }

    /**
     * Ensures that the bag contains the default value by default
     */
    @Test
    public void testDefault() {
        DoubleBag bag = new DoubleBag();
        assertEquals(0d, bag.get(0), 0d);
        bag.set(0, 1d);
        assertEquals(0d, bag.get(1), 0d);
        assertEquals(0d, bag.get(2), 0d);
        assertEquals(0d, bag.get(3), 0d);
    }

    /**
     * Ensures that the bag resizes correctly when out of capacity, that it
     * does not resize when queried for non-existing elements, and that it does
     * not resize when the default value is set.
     */
    @Test
    public void testCapacity() {
        DoubleBag bag;

        bag = new DoubleBag();
        assertEquals(0, bag.getCapacity());
        bag.set(0, 1d);
        assertEquals(1, bag.getCapacity());
        bag.set(1, 2d);
        assertEquals(2, bag.getCapacity());
        bag.set(2, 3d);
        assertEquals(4, bag.getCapacity());
        bag.set(3, 4d);
        assertEquals(4, bag.getCapacity());
        bag.set(4, 5d);
        assertEquals(8, bag.getCapacity());
        bag.set(8, 6d);
        assertEquals(16, bag.getCapacity());
        bag.set(35, 7d);
        assertEquals(64, bag.getCapacity());

        bag = new DoubleBag();
        for (int i = 0; i < 32; i++) {
            bag.get((1 << i) - 1);
            assertEquals(0, bag.getCapacity());
        }
        bag.get(Integer.MAX_VALUE);
        assertEquals(0, bag.getCapacity());

        bag = new DoubleBag();
        for (int i = 0; i < 31; i++) {
            bag.set((1 << i) - 1, 0d);
            assertEquals(0, bag.getCapacity());
        }
        bag.set(Integer.MAX_VALUE, 0d);
        assertEquals(0, bag.getCapacity());
    }

    /**
     * When a negative index is used, an {@link ArrayIndexOutOfBoundsException} should be thrown.
     */
    @Test
    public void testArrayIndexOutOfBoundsException() {
        DoubleBag bag = new DoubleBag();
        for (int i = 0; i < 32; i++) {
            try {
                bag.set(-(1 << i), 0d);
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
