/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import org.junit.Ignore;
import org.junit.Test;
import test.BetterCurveShapeTest;

/**
 * Simple wrapper on Marlin long tests
 */
public class RunJUnitLongTest {
/*
    @Test(timeout = 600000)
    public void clipTestPoly() throws InterruptedException {
        ClipShapeTest.main(new String[]{"-poly"});
        ClipShapeTest.main(new String[]{"-poly", "-doDash"});
    }

    @Test(timeout = 900000)
    public void clipTestQuad() throws InterruptedException {
        ClipShapeTest.main(new String[]{"-quad"});
        ClipShapeTest.main(new String[]{"-quad", "-doDash"});
    }

    @Test(timeout = 900000)
    public void clipTestCubic() throws InterruptedException {
        ClipShapeTest.main(new String[]{"-cubic"});
        ClipShapeTest.main(new String[]{"-cubic", "-doDash"});
    }
*/
    @Test(timeout = 600000)
    public void subdivideTestQuad() throws InterruptedException {
        BetterCurveShapeTest.main(new String[]{"-quad"});
        BetterCurveShapeTest.main(new String[]{"-quad", "-doDash"});
    }

    @Test(timeout = 600000)
    public void subdivideTestCubic() throws InterruptedException {
        BetterCurveShapeTest.main(new String[]{"-cubic"});
        BetterCurveShapeTest.main(new String[]{"-cubic", "-doDash"});
    }

    @Ignore
    @Test
    public void clipTestsSlow() {
        ClipShapeTest.main(new String[]{"-slow"});
    }
}
