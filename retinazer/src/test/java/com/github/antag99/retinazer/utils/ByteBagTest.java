package com.github.antag99.retinazer.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteBagTest {

    /**
     * Ensures that the elements of a bag are actually stored
     */
    @Test
    public void testStorage() {
        ByteBag bag = new ByteBag();

        bag.set(0, (byte) 0);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 0, bag.get(1));
        assertEquals((byte) 0, bag.get(2));
        assertEquals((byte) 0, bag.get(3));
        assertEquals((byte) 0, bag.get(4));
        assertEquals((byte) 0, bag.get(5));
        assertEquals((byte) 0, bag.get(6));
        assertEquals((byte) 0, bag.get(7));

        bag.set(1, (byte) 1);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 1, bag.get(1));
        assertEquals((byte) 0, bag.get(2));
        assertEquals((byte) 0, bag.get(3));
        assertEquals((byte) 0, bag.get(4));
        assertEquals((byte) 0, bag.get(5));
        assertEquals((byte) 0, bag.get(6));
        assertEquals((byte) 0, bag.get(7));

        bag.set(2, (byte) 2);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 1, bag.get(1));
        assertEquals((byte) 2, bag.get(2));
        assertEquals((byte) 0, bag.get(3));
        assertEquals((byte) 0, bag.get(4));
        assertEquals((byte) 0, bag.get(5));
        assertEquals((byte) 0, bag.get(6));
        assertEquals((byte) 0, bag.get(7));

        bag.set(3, (byte) 3);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 1, bag.get(1));
        assertEquals((byte) 2, bag.get(2));
        assertEquals((byte) 3, bag.get(3));
        assertEquals((byte) 0, bag.get(4));
        assertEquals((byte) 0, bag.get(5));
        assertEquals((byte) 0, bag.get(6));
        assertEquals((byte) 0, bag.get(7));

        bag.set(4, (byte) 4);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 1, bag.get(1));
        assertEquals((byte) 2, bag.get(2));
        assertEquals((byte) 3, bag.get(3));
        assertEquals((byte) 4, bag.get(4));
        assertEquals((byte) 0, bag.get(5));
        assertEquals((byte) 0, bag.get(6));
        assertEquals((byte) 0, bag.get(7));

        bag.set(5, (byte) 5);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 1, bag.get(1));
        assertEquals((byte) 2, bag.get(2));
        assertEquals((byte) 3, bag.get(3));
        assertEquals((byte) 4, bag.get(4));
        assertEquals((byte) 5, bag.get(5));
        assertEquals((byte) 0, bag.get(6));
        assertEquals((byte) 0, bag.get(7));

        bag.set(6, (byte) 6);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 1, bag.get(1));
        assertEquals((byte) 2, bag.get(2));
        assertEquals((byte) 3, bag.get(3));
        assertEquals((byte) 4, bag.get(4));
        assertEquals((byte) 5, bag.get(5));
        assertEquals((byte) 6, bag.get(6));
        assertEquals((byte) 0, bag.get(7));

        bag.set(7, (byte) 7);
        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 1, bag.get(1));
        assertEquals((byte) 2, bag.get(2));
        assertEquals((byte) 3, bag.get(3));
        assertEquals((byte) 4, bag.get(4));
        assertEquals((byte) 5, bag.get(5));
        assertEquals((byte) 6, bag.get(6));
        assertEquals((byte) 7, bag.get(7));

        bag.clear();

        assertEquals((byte) 0, bag.get(0));
        assertEquals((byte) 0, bag.get(1));
        assertEquals((byte) 0, bag.get(2));
        assertEquals((byte) 0, bag.get(3));
        assertEquals((byte) 0, bag.get(4));
        assertEquals((byte) 0, bag.get(5));
        assertEquals((byte) 0, bag.get(6));
        assertEquals((byte) 0, bag.get(7));
    }

    /**
     * Ensures that the bag contains the default value by default
     */
    @Test
    public void testDefault() {
        ByteBag bag = new ByteBag();
        assertEquals((byte) 0, bag.get(0));
        bag.set(0, (byte) 1);
        assertEquals((byte) 0, bag.get(1));
        assertEquals((byte) 0, bag.get(2));
        assertEquals((byte) 0, bag.get(3));
    }

    /**
     * Ensures that the bag resizes correctly when out of capacity, that it
     * does not resize when queried for non-existing elements, and that it does
     * not resize when the default value is set.
     */
    @Test
    public void testCapacity() {
        ByteBag bag;

        bag = new ByteBag();
        assertEquals(0, bag.getCapacity());
        bag.set(0, (byte) 1);
        assertEquals(1, bag.getCapacity());
        bag.set(1, (byte) 2);
        assertEquals(2, bag.getCapacity());
        bag.set(2, (byte) 3);
        assertEquals(4, bag.getCapacity());
        bag.set(3, (byte) 4);
        assertEquals(4, bag.getCapacity());
        bag.set(4, (byte) 5);
        assertEquals(8, bag.getCapacity());
        bag.set(8, (byte) 6);
        assertEquals(16, bag.getCapacity());
        bag.set(35, (byte) 7);
        assertEquals(64, bag.getCapacity());

        bag = new ByteBag();
        for (int i = 0; i < 32; i++) {
            bag.get((1 << i) - 1);
            assertEquals(0, bag.getCapacity());
        }
        bag.get(Integer.MAX_VALUE);
        assertEquals(0, bag.getCapacity());

        bag = new ByteBag();
        for (int i = 0; i < 31; i++) {
            bag.set((1 << i) - 1, (byte) 0);
            assertEquals(0, bag.getCapacity());
        }
        bag.set(Integer.MAX_VALUE, (byte) 0);
        assertEquals(0, bag.getCapacity());
    }

    /**
     * When a negative index is used, an {@link ArrayIndexOutOfBoundsException} should be thrown.
     */
    @Test
    public void testArrayIndexOutOfBoundsException() {
        ByteBag bag = new ByteBag();
        for (int i = 0; i < 32; i++) {
            try {
                bag.set(-(1 << i), (byte) 0);
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
