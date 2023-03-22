package org.example;

import com.sun.jna.*;
import net.sourceforge.lept4j.*;
import net.sourceforge.tess4j.Tesseract;
import org.opencv.core.*;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;
import org.slf4j.LoggerFactory.*;
import com.github.jaiimageio.plugins.tiff.*;
import org.apache.commons.io.*;
import org.opencv.imgcodecs.Imgcodecs;
import java.awt.image.BufferedImage;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.sql.SQLOutput;

public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    //Transforms image matrix to a buffered image
    private static BufferedImage matToBufferedImage(Mat mat) {
        BufferedImage bufferedImage = new BufferedImage(mat.cols(), mat.rows(), BufferedImage.TYPE_BYTE_GRAY);

        final byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        mat.get(0, 0, data);

        return bufferedImage;
    }
    public static void main(String[] args) {
        System.setProperty("OPENCV_OPENMP_THREADS", "1");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try{
            //Loading Image as a matrix
            Mat image=Imgcodecs.imread("pen.jpg");
            //Setting up and configuring Tesseract object
            Tesseract tess=new Tesseract();
            tess.setDatapath("C:\\Users\\User\\Desktop\\Devs\\JavaML\\Tesseract");
            tess.setLanguage("eng");

            //Image pre-processing
            Imgproc.cvtColor(image,image,Imgproc.COLOR_BGR2GRAY);
            //Imgproc.threshold(image,image,0,255,Imgproc.THRESH_BINARY|Imgproc.THRESH_OTSU);
            Imgproc.medianBlur(image,image,3);
            //Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
            //Imgproc.morphologyEx(image, image, Imgproc.MORPH_CLOSE, kernel);


            //Transforming matrix back to image
            BufferedImage img=matToBufferedImage(image);
            String recognizedText=tess.doOCR(img);
            System.out.println(recognizedText);



        }catch(Exception e){
            System.out.println("Error "+ e.getMessage());
        }



    }
}