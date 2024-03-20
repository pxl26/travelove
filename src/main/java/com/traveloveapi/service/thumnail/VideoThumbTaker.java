package com.traveloveapi.service.thumnail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.stereotype.Component;

@Component
public class VideoThumbTaker {
    protected String ffmpegApp;

//    public VideoThumbTaker(String ffmpegApp)
//    {
//        this.ffmpegApp = ffmpegApp;
//    }

    public void getThumb(String videoFilename, String thumbFilename, int width, int height,int hour, int min, float sec)
            throws IOException, InterruptedException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y", "-i", videoFilename, "-vframes", "1",
                "-ss", hour + ":" + min + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height, "-an", thumbFilename);
        Process process = processBuilder.start();
        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null);
        process.waitFor();
    }

    public static void main(String[] args) throws Exception, IOException
    {
        FFmpegFrameGrabber g = new FFmpegFrameGrabber("C:\\JavaEE\\New Project\\tape\\src\\main\\webapp\\web-resources\\videos\\vid.mp4");

        g.setFormat("mp4");
        g.start();
        ImageIO.write(g.grab().getBufferedImage(), "png", new File("C:\\JavaEE\\New Project\\tape\\src\\main\\webapp\\web-resources\\thumbnails\\video-frame-" + System.currentTimeMillis() + ".png"));
        g.stop();
    }
}