package uk.khall;


import uk.khall.image.shape.AreaColor;
import uk.khall.image.shape.BSplineBrushStroke;
import uk.khall.image.utils.AverageOutlineFilter;
import uk.khall.image.utils.FilterChain;
import uk.khall.image.utils.pallet.MedianFilter;
import uk.khall.image.utils.pallet.TempPosterizeFilter;
import uk.khall.ui.display.ImageCanvas;
import uk.khall.utils.FindPalletfromImage;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_java;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class ETFImageScaleRandomWithNewPalette extends javax.swing.JFrame{
    private javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
    private ImageCanvas canvasPanel = new ImageCanvas();
    private int canvasWidth = 0;
    private int canvasHeight = 0;
    private double diffVal = 20;
    private int blur = 5;
    private int iterations = 2;
    private String imageVariantName;
    private String imageName;
    private String outputFolder;
    private String palletImageName;
    private int loopLength;
    private int randomBound  = 5;
    private int scale = 450;
    private int kernal = 5;
    private int cutOff = 100;
    private int palletSize = 100;
    private boolean useSrcType = true;
    private String backImageName = null;
    private Random random = new Random(System.currentTimeMillis());

    public ETFImageScaleRandomWithNewPalette(){
        this.setVisible(true);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();;
        canvasPanel.setFont(new Font("Dialog", 0, 11));
        getContentPane().setLayout(new GridBagLayout());
        setTitle("Image Messer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        canvasPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                canvasPanelMouseClicked(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                canvasPanelMouseReleased(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                canvasPanelMousePressed(evt);
            }
        });
        canvasPanel.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                canvasPanelMouseMoved(evt);
            }

        });
        jScrollPane.setViewportView(canvasPanel);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.weighty = 4.0;
        getContentPane().add(jScrollPane, gridBagConstraints);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (getCanvasWidth() == 0 || getCanvasHeight() == 0) {
            setSize(new Dimension(2400, 1800));
        } else {
            setSize(new Dimension(getCanvasWidth(), getCanvasHeight()));
        }
        setLocation(10, 10);

    }
    public String getImageVariantName() {
        return imageVariantName;
    }

    public void setImageVariantName(String imageVariantName) {
        this.imageVariantName = imageVariantName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPalletImageName() {
        return palletImageName;
    }

    public void setPalletImageName(String palletImageName) {
        this.palletImageName = palletImageName;
    }

    public int getLoopLength() {
        return loopLength;
    }

    public void setLoopLength(int loopLength) {
        this.loopLength = loopLength;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public double getDiffVal() {
        return diffVal;
    }

    public void setDiffVal(double diffVal) {
        this.diffVal = diffVal;
    }

    public int getBlur() {
        return blur;
    }

    public void setBlur(int blur) {
        this.blur = blur;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getRandomBound() {
        return randomBound;
    }

    public void setRandomBound(int randomBound) {
        this.randomBound = randomBound;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getKernal() {
        return kernal;
    }

    public void setKernal(int kernal) {
        this.kernal = kernal;
    }

    public int getCutOff() {
        return cutOff;
    }

    public void setCutOff(int cutOff) {
        this.cutOff = cutOff;
    }

    public int getPalletSize() {
        return palletSize;
    }

    public void setPalletSize(int palletSize) {
        this.palletSize = palletSize;
    }

    public String getBackImageName() {
        return backImageName;
    }

    public void setBackImageName(String backImageName) {
        this.backImageName = backImageName;
    }

    /**
     *
     *
     * @return the canvasWidth
     */
    private int getCanvasWidth() {
        return canvasWidth;
    }

    /**
     * @param canvasWidth the canvasWidth to set
     */
    private void setCanvasWidth(int canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    /**
     * @return the canvasHeight
     */
    private int getCanvasHeight() {
        return canvasHeight;
    }

    /**
     * @param canvasHeight the canvasHeight to set
     */
    private void setCanvasHeight(int canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    private void canvasPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_demImagePanelMouseClicked
        // find the x and y co-ordinate of image panel:
        int clickX = evt.getX();
        int clickY = evt.getY();


    }//GEN-LAST:event_canvasPanelMouseClicked

    private void canvasPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneMouseReleased
        // repaint the image Panel:

    }//GEN-LAST:event_jScrollPaneMouseReleased

    private void canvasPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneMouseReleased
        // repaint the image Panel:

    }//GEN-LAST:event_jScrollPaneMouseReleased

    private void canvasPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneMouseReleased
        // repaint the image Panel:

    }//GEN
    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
     * @param
     */
    public void drawBrushStrokes() {

        int resolution = 5;

        Random r = new Random(System.currentTimeMillis());
        boolean usingEnd = false;
        Color outColour = Color.white;

        try {


            int level = 3;
            BufferedImage substrateImage = null;
            BufferedImage palletImage = null;
            BufferedImage backImage = null;

            if (getImageVariantName() != null) {
                try {
                    String target_img = "data\\"+getImageVariantName()+".jpg";
                    File sampleimagefile = new File(target_img);
                    substrateImage = ImageIO.read(sampleimagefile);
                    backImage = new BufferedImage(substrateImage.getWidth(),substrateImage.getHeight(),substrateImage.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            boolean backImageset = false;
            if (backImageName!=null) {
                try {
                    File backImageFile = new File(backImageName);
                    backImage = ImageIO.read(backImageFile);
                    backImageset = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(palletImageName!=null){
                try {
                    File palletImageFile = new File("data\\"+ palletImageName +".jpg");
                    palletImage = ImageIO.read(palletImageFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            FilterChain filterChain = null;

            filterChain = new FilterChain();
            MedianFilter bbf = new MedianFilter();
            bbf.setHRadius(getBlur());
            bbf.setVRadius(getBlur());
            bbf.setIterations(getIterations());
            filterChain.addFilter(bbf);
            BufferedImage blurredImage = null;
            if(backImageset){
                blurredImage = filterChain.runFilters(backImage);
            } else {
                blurredImage = filterChain.runFilters(substrateImage);
            }

            canvasPanel.setImage(blurredImage);
            try {
                ImageIO.write(blurredImage, "jpeg", new File("D:\\Users\\theke\\java projects\\JavaND4J\\blurredImage.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            TempPosterizeFilter pfilter = null;
            BufferedImage posterizedImage = null;

            FindPalletfromImage finder = new FindPalletfromImage();
            finder.setDiffVal(getDiffVal());
            finder.setImage(palletImage);
            finder.setCutOff(getCutOff());
            finder.createPallet();

            filterChain = new FilterChain();
            ArrayList<Color> colors = finder.getReducedColorList(getPalletSize());
            ArrayList<Color> brighterListcolors = finder.brighterList(colors);
            ArrayList<Color> darkerListcolors = finder.darkerList(colors);
            pfilter = new TempPosterizeFilter();
            brighterListcolors.addAll(darkerListcolors);
            System.out.println("new color size = " + brighterListcolors.size());
            pfilter.setColors(brighterListcolors);
            //pfilter.setUsesrcType(useSrcType);
            filterChain.addFilter(pfilter);

            posterizedImage = filterChain.runFilters(blurredImage);

            try {
                ImageIO.write(posterizedImage, "jpeg", new File("D:\\Users\\theke\\java projects\\JavaND4J\\posterised.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            canvasPanel.setImage(posterizedImage);

            AverageOutlineFilter outlineFilter=null;
            //filterChain = new FilterChain();
            outlineFilter = new AverageOutlineFilter();
            outlineFilter.setColors(colors);
            outlineFilter.setPosterizedImage(posterizedImage);
            outlineFilter.setOutlineColor(null);
            //filterChain.addFilter(outlineFilter);
            //outlineImage = filterChain.runFilters(substrateImage);
            outlineFilter.createSortedAreas(substrateImage);
            //canvasPanel.setImage(outlineImage);
            //ImageIO.write(outlineImage,"jpg", new File(getImageName() + "_outline.jpg"));
            ArrayList<AreaColor> sortedAreas = outlineFilter.getSortedAreas();


            Graphics2D graphics2D = backImage.createGraphics();
            if(posterizedImage!=null){
                graphics2D.drawImage(posterizedImage,0,0,this);
            } else {
                graphics2D.setPaint(Color.white);
                graphics2D.setBackground(Color.white);
                graphics2D.setPaint(Color.white);

                graphics2D.clearRect(0, 0, backImage.getWidth(), backImage.getHeight());
                graphics2D.fillRect(0, 0, backImage.getWidth(), backImage.getHeight());
            }


            //BrushStroke brushStroke = new BrushStroke(graphics2D,substrateImage.getWidth(),substrateImage.getHeight(),substrateImage);
            BSplineBrushStroke brushStroke = new BSplineBrushStroke(graphics2D,substrateImage.getWidth(),substrateImage.getHeight(),posterizedImage);

            int maxSize = substrateImage.getHeight()*substrateImage.getWidth();

            try {
                for (int c = 0; c<getLoopLength(); c++) {
                    INDArray flowField = Nd4j.readBinary(new File("etf_kernal_" + getImageName() + kernal + "_" + scale + "_" + c + ".bin"));
                    //INDArray flowField = Nd4j.readBinary(new File("etf_kernal_" + getImageName() + kernal + "_" + c + ".bin"));
                    int arrayWidth = (int)flowField.shape()[1];
                    int arrayHeight = (int)flowField.shape()[0];
                    int ratioH = substrateImage.getHeight() / arrayHeight;
                    int ratioW = substrateImage.getWidth() / arrayWidth;
                    for (AreaColor areaColor : sortedAreas) {
                        Area area = areaColor.getArea();
                        double topX = area.getBounds().getX();
                        double topY = area.getBounds().getY();
                        double maxX = area.getBounds().getMaxX();
                        double maxY = area.getBounds().getMaxY();
                        double iwidthx = maxX-maxY;
                        double iheighty = maxY-topY;
                        double tptopX = topX/ratioW;
                        double tptopY = topY/ratioH;
                        double tpmaxX = maxX/ratioW;
                        double tpmaxY = maxY/ratioH;


                        //System.out.println("topX = " + topX + " topY = " + topY + " maxX = " + maxX + "maxY = " + maxY);
    /*                    long totalR = 0;
                        long totalG = 0;
                        long totalB = 0;
                        long count = 0;*/
                        ArrayList<java.awt.Point> pixelList = areaColor.getPixelList();
                        //int countlength=1+(pixelList.size()/50);
                        double ratio = (double)pixelList.size()/(double)outlineFilter.getMaxAreaSize();
                        double globalratio =   (double)pixelList.size()/(double)maxSize;
                        double vsize = 4 + (ratio * 3);
                        if(pixelList.size()>0) {
                            int cc = 0;
                            while (cc < pixelList.size()) {

                                java.awt.Point pp = pixelList.get(cc);
                                int i = Math.min((pp.y / ratioH), scale-1);
                                int j = Math.min((pp.x / ratioW), scale-1);
                                i = Math.max(i, 0);
                                j = Math.max(j, 0);

                                float v0 = flowField.getFloat(i, j, 0);
                                float v1 = flowField.getFloat(i, j, 1);

                                Point p = new Point(j, i);
                                Point p2 = new Point((int) (j + v1 * kernal), (int) (i + v0 * kernal));
                                Point tp = new Point(pp.x, pp.y);
                                Point tp2 = new Point((int) (pp.x + (v1 * vsize * ratioW)), (int) (pp.y + (v0 * vsize * ratioH)));
                                double overallbrushheight = Math.max(Math.abs(p2.y() - p.y()),1);
                                double overallbrushwidth = Math.max(Math.abs(p2.x() - p.x()),1);
                                double overalbrusharea = overallbrushwidth*overallbrushheight;
                                float distance = (float) (Math.sqrt((p2.x() - p.x()) * (p2.x() - p.x()) + (p2.y() - p.y()) * (p2.y() - p.y())));
                                int randomVal = random.nextInt(randomBound);
                                int brushWidth = (int) (distance * ratioW/(randomBound+randomVal));
                                if (distance > 1) {
                                    Color primaryColor = new Color(posterizedImage.getRGB(tp.x(), tp.y()));
                                    int tp2x = tp2.x();
                                    int tp2y = tp2.y();

                                    if (tp2x<0)
                                        tp2x=0;
                                    else if (tp2x>substrateImage.getWidth()-1)
                                        tp2x=substrateImage.getWidth()-1;
                                    if (tp2y<0)
                                        tp2y=0;
                                    else if (tp2y>substrateImage.getHeight()-1)
                                        tp2y=substrateImage.getHeight()-1;
                                    Color endColor = new Color(posterizedImage.getRGB(tp2x, tp2y));
                                    ArrayList<Color> secondaryColors = new ArrayList<>();
                                    secondaryColors.add(primaryColor.brighter());
                                    secondaryColors.add(primaryColor.darker());
                                    secondaryColors.add(areaColor.getColor());
                                    if(usingEnd) {
                                        ArrayList<Color> secondaryEndColors = new ArrayList<>();
                                        secondaryColors.add(endColor.brighter());
                                        secondaryColors.add(endColor.darker());
                                        secondaryColors.add(areaColor.getColor());
                                        brushStroke.addColorBrushStroke(primaryColor, endColor, secondaryColors, secondaryEndColors, tp.x(), tp2.x(), tp.y(), tp2.y(), brushWidth);
                                    } else {
                                        brushStroke.addTaperingColorBrushStroke(primaryColor, secondaryColors,tp.x(), tp2.x(), tp.y(), tp2.y(), brushWidth);
                                        //brushStroke.addColorBrushStroke(primaryColor, secondaryColors,tp.x(), tp2.x(), tp.y(), tp2.y(), brushWidth);
                                    }
                                }
                                cc=cc+Math.max((int)(overalbrusharea),40);
                            }
                        }
                    }
                    System.out.println("c = " + c);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Leaving loop early");
            }
            canvasPanel.setImage(backImage);
            ImageIO.write(backImage,"jpg",new File(getOutputFolder() + getImageVariantName() +"_" + getPalletImageName() + "_" + getScale() + "_" + getKernal() + ".jpg"));
            System.out.println("finished");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] params){
        Loader.load(opencv_java.class);
        boolean superRes =false;
        boolean upload = false;

        String imageName = "martinmask";
        String imageVariantName = "martin";
        String palletImage = "pastel12";
        String drawFolder = "D:/Users/theke/java projects/PictureComplete/input/";
        String uploadFolder = "D:/Users/theke/java projects/PictureComplete/upload/";
        String backImageName = "D:\\Users\\theke\\java projects\\JavaND4J\\data\\martin.jpg";
        ETFImageScaleRandomWithNewPalette etfi = new ETFImageScaleRandomWithNewPalette();
        etfi.setVisible(true);
        etfi.setLoopLength(2);
        etfi.setBlur(2);
        etfi.setIterations(2);
        etfi.setDiffVal(20);
        etfi.setCutOff(0);
        etfi.setRandomBound(5);
        etfi.setPalletSize(200);
        etfi.setImageName(imageName);
        etfi.setImageVariantName(imageVariantName);
        etfi.setPalletImageName(palletImage);
        etfi.setOutputFolder(drawFolder);
        etfi.setScale(600);
        etfi.setKernal(5);

        //etfi.setBackImageName(backImageName);
        etfi.drawBrushStrokes();
        if(superRes) {
            SuperResolution superResolution = new SuperResolution(3, "FSRCNN", imageName + " " + palletImage + ".jpg",
                    drawFolder, uploadFolder);
            superResolution.render();
        }
    }
}
