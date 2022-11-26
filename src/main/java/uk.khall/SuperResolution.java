package uk.khall;
import org.bytedeco.javacpp.Loader;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_dnn_superres.DnnSuperResImpl;
import org.bytedeco.opencv.opencv_java;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;


public class SuperResolution {
    private int scale = 8;
    private String model = "LapSRN_x";
    private String imageName = "smallkarenandpaul";
    private String outputFolder ="";
    private String inputFolder = "";

    public SuperResolution(int scale, String model, String imageName, String inputFolder, String outputFolder){
        this.scale = scale;
        this.model = model;
        this.imageName = imageName;
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        if (outputFolder.endsWith("/") || outputFolder.endsWith("\\"))
            this.outputFolder = outputFolder;
        else
            this.outputFolder = outputFolder+"/";
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        if (inputFolder.endsWith("/") || inputFolder.endsWith("\\"))
            this.inputFolder = inputFolder;
        else
            this.inputFolder = inputFolder+"/";;
    }

    public void render(){
        Mat image = imread(inputFolder+imageName);
        DnnSuperResImpl sr =   DnnSuperResImpl.create();
        sr.readModel("models/"+model + "_x" + scale + ".pb");
        sr.setModel(model.toLowerCase(),scale);
        Mat upscaled = new Mat();
        sr.upsample(image,upscaled);
        int pos = imageName.lastIndexOf(".");
        String outputName = outputFolder+imageName.substring(0, pos);
        imwrite(outputName +"_upscaled" + "_" + model + "_" + scale + ".png", upscaled);
    }


    public static void main(String[] params){

        Loader.load(opencv_java.class);
        SuperResolution superResolution = new SuperResolution(3, "FSRCNN", "pyrator_a_yorkshire_landscape_with_drystone_wall_in_the_style_o_892ff17c-9182-4b57-9902-99a97d83dcf0.png", "D:\\Users\\theke\\Pictures\\Midjourney\\", "D:\\Users\\theke\\Pictures\\Midjourney\\enlarged\\");
        superResolution.render();

    }

}
