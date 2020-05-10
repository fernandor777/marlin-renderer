/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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
package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import static java.awt.geom.Path2D.WIND_NON_ZERO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.marlin.pisces.MarlinProperties;

/**
 * Simple Line rendering test using GeneralPath to enable Pisces / marlin / ductus renderers
 */
public class LineTests {

    public static void main(String[] args) {
        
        final int N = 100;

        final float lineStroke = 4f;
        final int size = 1000;

        // First display which renderer is tested:
        // JDK9 only:
        System.setProperty("sun.java2d.renderer.verbose", "true");
        System.out.println("Testing renderer: ");
        // Other JDK:
        String renderer = "undefined";
        try {
            renderer = sun.java2d.pipe.RenderingEngine.getInstance().getClass().getName();
            System.out.println(renderer);
        } catch (Throwable th) {
            // may fail with JDK9 jigsaw (jake)
            if (false) {
                System.err.println("Unable to get RenderingEngine.getInstance()");
                th.printStackTrace();
            }
        }

        System.out.println("LineTests: size = " + size);

        final BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        g2d.setClip(0, 0, size, size);
        g2d.setStroke(new BasicStroke(lineStroke));

        for (int i = 0; i < N; i++) {

            g2d.setBackground(Color.GRAY); // BLUE
            g2d.clearRect(0, 0, size, size);

            final long start = System.nanoTime();

            paint(g2d, size - 2.0 * lineStroke);

            final long time = System.nanoTime() - start;

            System.out.println("paint: duration= " + (1e-6 * time) + " ms.");
        }

        try {
            final File file = new File("LinesTest-norm-subpix_lg_" + MarlinProperties.getSubPixel_Log2_X()
                    + "x" + MarlinProperties.getSubPixel_Log2_Y() + ".png");

            System.out.println("Writing file: " + file.getAbsolutePath());
            ImageIO.write(image, "PNG", file);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            g2d.dispose();
        }
    }

    private static void paint(final Graphics2D g2d, final double size) {

        final double half = size / 2.0;
        final double radius = 266.0;

        g2d.setPaint(Color.RED);
        g2d.fill(new Ellipse2D.Double(half - radius, half - radius, 2f * radius, 2f * radius));

        final Path2D.Double path = new Path2D.Double(WIND_NON_ZERO, 10);
        
        boolean on = false;

        for (double angle = 1.0 / 3; angle <= 90.0; angle += 1.0) {
            double angRad = Math.toRadians(angle);

            double cos = Math.cos(angRad);
            double sin = Math.sin(angRad);

            if (on) {
                g2d.setPaint(Color.WHITE);
            } else {
               g2d.setPaint(Color.BLACK); 
            }
            on = !on;
            
            path.reset();

            path.moveTo(5.0 * cos, 5.0 * sin);
            path.lineTo(size * cos, size * sin);

            g2d.draw(path);
        }
    }
}
