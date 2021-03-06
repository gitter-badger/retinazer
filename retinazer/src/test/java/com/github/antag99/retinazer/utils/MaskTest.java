/*******************************************************************************
 * Copyright (C) 2015 Anton Gustafsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.antag99.retinazer.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.antag99.retinazer.utils.Mask;

public class MaskTest {

    @Test
    public void testPushAndPop() {
        Mask mask = new Mask();
        mask.set(0);

        for (int i = 0; i < 128; i++) {
            mask.push(i);
            assertTrue(mask.get(i + 1));
            mask.pop(i);
            assertTrue(mask.get(i));
            mask.push(0);
            assertTrue(mask.get(i + 1));
        }
    }

    @Test
    public void testPush() {
        Mask mask = new Mask();
        for (int i = 0; i < 128; i++) {
            mask.set(i);
            mask.push(0);
            assertFalse(String.valueOf(i), mask.get(i));
            assertTrue(String.valueOf(i), mask.get(i + 1));
        }
    }

    @Test
    public void testPop() {
        Mask mask = new Mask();
        for (int i = 0; i < 128; i++)
            mask.set(i);
        for (int i = 0; i < 128; i++) {
            mask.pop(0);
            assertTrue(String.valueOf(i), mask.get(0));
        }
    }
}
