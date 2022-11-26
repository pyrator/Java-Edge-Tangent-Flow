package uk.khall;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;

public class ETFImageWrapper {

    public static void main(String[] params){
        Loader.load(opencv_java.class);
        boolean superRes = false;
        String imageName = "martinmask";
        String imageVariantName = "martin";
        String palletImage = "verona";
        int scale = 600;
        int loopLength = 2;
        int kernel = 5;
        boolean palletize = false;
        ETFImageBDVariScale etfi = new ETFImageBDVariScale();
        etfi.setImageName(imageName);
        etfi.setLoopLength(loopLength);
        etfi.setScale(scale);
        etfi.setKernal(kernel);
        etfi.initialiseETF();
        for (int i = 0; i < loopLength; i++) {
            System.out.println("i=" + i);
            etfi.refine_ETF();
            System.out.println("completed refine_ETF " + i);
            etfi.saveFlowField(i);
            System.out.println("completed draw_arrowline " + i);
        }
        String drawFolder = "D:/Users/theke/java projects/PictureComplete/input/";
        String uploadFolder = "D:/Users/theke/java projects/PictureComplete/upload/";
        ETFImageScaleRandomWithBgFast etfif = new ETFImageScaleRandomWithBgFast();
        etfif.setVisible(true);
        etfif.setLoopLength(loopLength);
        etfif.setBlur(2);
        etfif.setIterations(2);
        etfif.setDiffVal(20);
        etfif.setRandomBound(5);
        etfif.setImageName(imageName);
        etfif.setImageVariantName(imageVariantName);
        etfif.setOutputFolder(drawFolder);
        etfif.setScale(scale);
        etfif.setKernal(kernel);
        etfif.setUsingEnd(true);
        //etfi.setBackImageName(backImageName);
        etfif.drawBrushStrokes();
        if (palletize){
            ETFImageScaleRandomWithNewPalette etrwbp = new ETFImageScaleRandomWithNewPalette();
            etrwbp.setVisible(true);
            etrwbp.setLoopLength(loopLength);
            etrwbp.setBlur(2);
            etrwbp.setIterations(2);
            etrwbp.setDiffVal(20);
            etrwbp.setCutOff(1);
            etrwbp.setRandomBound(5);
            etrwbp.setPalletSize(100);
            etrwbp.setImageName(imageName);
            etrwbp.setImageVariantName(imageVariantName);
            etrwbp.setPalletImageName(palletImage);
            etrwbp.setOutputFolder(drawFolder);
            etrwbp.setScale(600);
            etrwbp.setKernal(5);
            etrwbp.drawBrushStrokes();
        } else {
            ETFImageScaleRandomWithBg etrwb = new ETFImageScaleRandomWithBg();
            etrwb.setVisible(true);
            etrwb.setDiffVal(30);
            etrwb.setBlur(3);
            etrwb.setIterations(2);
            etrwb.setRandomBound(5);
            etrwb.setLoopLength(loopLength);
            etrwb.setImageName(imageName);
            etrwb.setImageVariantName(imageVariantName);
            etrwb.setOutputFolder(drawFolder);
            etrwb.setScale(scale);
            etrwb.setKernal(kernel);
            etrwb.setUseBrush(false);
            etrwb.setUseDots(false);
            etrwb.setUsingEnd(false);
            etrwb.setBackImageName(etfif.getUploadImageName());
            etrwb.drawBrushStrokes();
        }
        if(superRes) {
            SuperResolution superResolution = new SuperResolution(3, "FSRCNN", imageName + ".jpg",
                    drawFolder, uploadFolder);
            superResolution.render();
        }
    }
}
