/**
 * Created by zeruch on 18/02/17.
 */
package com.risingapp.test.image;

import com.risingapp.test.model.OvvaProgram;
import com.risingapp.test.response.OvvaTvProgramResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@org.springframework.stereotype.Component
public class ImageGenerator {
    private static final int HEIGHT = 800;
    private static final int WIDTH = 700;
    private static final int LEFT_POS = 10;

    public BufferedImage processProgram(OvvaTvProgramResponse response) throws IOException, FontFormatException {

        java.util.List<OvvaProgram> programs = response.getData().getPrograms();

        BufferedImage resultImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphicsGlobal = resultImage.createGraphics();

        // draw main label
        graphicsGlobal.setColor(new Color(255, 0, 0));
        graphicsGlobal.fillRect(5, 5, 160, 25);

        graphicsGlobal.setColor(new Color(255, 255, 255));
        graphicsGlobal.setFont(new Font("default", Font.BOLD, 16));
        graphicsGlobal.drawString("Телепрограмма", LEFT_POS, 23);

        // draw list of programs
        int yPos = 100;
        for (OvvaProgram currenProgram : programs) {
            graphicsGlobal.setFont(new Font("default", Font.PLAIN, 20));
            graphicsGlobal.setColor(new Color(0, 140, 255, 255));
            graphicsGlobal.drawString(parseTime(currenProgram.getRealtime_begin()), LEFT_POS, yPos);

            graphicsGlobal.setFont(new Font("default", Font.PLAIN, 18));
            graphicsGlobal.setColor(new Color(255, 255, 255));


            for (String line : getTitleLines(currenProgram.getTitle())) {
                graphicsGlobal.drawString(line, LEFT_POS + 70, yPos);
                yPos += 30;
            }
        }

        // draw 3 big images
        float scale = 0.45f;
        graphicsGlobal.scale(scale, scale);

        graphicsGlobal.drawImage(ImageIO.read(new URL(programs.get(0).getImage().getPreview())), (int) (360 / scale), 0, null);
        graphicsGlobal.drawImage(ImageIO.read(new URL(programs.get(programs.size() / 2).getImage().getPreview())), (int) (360 / scale), (int) (256 / scale), null);
        graphicsGlobal.drawImage(ImageIO.read(new URL(programs.get(programs.size() - 1).getImage().getPreview())), (int) (360 / scale), (int) (512 / scale), null);

        // draw gradient
        graphicsGlobal.scale(1 / scale, 1 / scale);
        GradientPaint gradientPaintOdd = new GradientPaint(0, 0,
                new Color(0, 0, 0, 70), 0, 256, new Color(0, 0, 0, 255), true);
        GradientPaint gradientPaintEven = new GradientPaint(256, 256,
                new Color(0, 0, 0, 70), 256, 512, new Color(0, 0, 0, 255), true);
        graphicsGlobal.setPaint(gradientPaintOdd);
        graphicsGlobal.fillRect(360, 0, 360, 256);
        graphicsGlobal.fillRect(360, 512, 360, 256);
        graphicsGlobal.setPaint(gradientPaintEven);
        graphicsGlobal.fillRect(360, 256, 360, 256);

        // draw labels
        int yText = 225;
        graphicsGlobal.setColor(Color.WHITE);

        graphicsGlobal.drawString(programs.get(0).getTitle(), 370, yText);
        graphicsGlobal.drawString(parseTime(programs.get(0).getRealtime_begin()), 370, yText + 20);

        graphicsGlobal.drawString(programs.get(programs.size() / 2).getTitle(), 370, yText + 256);
        graphicsGlobal.drawString(parseTime(programs.get(programs.size() / 2).getRealtime_begin()), 370, yText + 276);

        graphicsGlobal.drawString(programs.get(programs.size() - 1).getTitle(), 370, yText + 512);
        graphicsGlobal.drawString(parseTime(programs.get(programs.size() - 1).getRealtime_begin()), 370, yText + 532);

        return resultImage;
    }

    private static java.util.List<String> getTitleLines(String title) {

        java.util.List<String> lines = new ArrayList<>();
        if (title.length() <= 26) return Arrays.asList(title);

        int s = 0;
        String[] words = title.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (i == words.length - 1) return Arrays.asList(title);

            if (words[i].length() + s > 24) {

                lines.add(title.substring(0, s - 1));
                lines.addAll(getTitleLines(title.substring(s, title.length())));
                break;
            }
            s += words[i].length() + 1;
        }
        return lines;
    }

    private String parseTime(int dateTime) {
        long longTime = (long) dateTime * 1000L;
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        return localDateFormat.format(new Date(longTime));
    }
}