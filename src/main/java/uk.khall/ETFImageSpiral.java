package uk.khall;


import uk.khall.image.shape.BSplineBrushStroke;
import uk.khall.ui.display.ImageCanvas;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_java;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;


public class ETFImageSpiral extends javax.swing.JFrame{
    private javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
    private ImageCanvas canvasPanel = new ImageCanvas();
    private int canvasWidth = 0;
    private int canvasHeight = 0;
    public ETFImageSpiral(){
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
            setSize(new Dimension(900, 1000));
        } else {
            setSize(new Dimension(getCanvasWidth(), getCanvasHeight()));
        }
        setLocation((screenSize.width - 500) / 2, (screenSize.height - 600) / 2);

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
    public void draw_arrowline() {

        int resolution = 5;
        int kernal = 5;
        Random r = new Random(System.currentTimeMillis());
        try {
            String input_img = "data\\buffalosmall.jpg";
            String target_img = "data\\buffalo.jpg";



            int blur = 3;
            int level = 3;
            BufferedImage substrateImage = null;
            BufferedImage outlineImage = null;
            BufferedImage backImage = null;
            BufferedImage posterizedImage = null;
            if (input_img != null) {
                try {
                    File sampleimagefile = new File(target_img);
                    substrateImage = ImageIO.read(sampleimagefile);
                    backImage = new BufferedImage(substrateImage.getWidth(),substrateImage.getHeight(),substrateImage.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

/*            FilterChain filterChain = null;
            PosterizeFilter pfilter = null;

            filterChain = new FilterChain();
            BoxBlurFilter bbf = new BoxBlurFilter();
            bbf.setHRadius(blur);
            bbf.setVRadius(blur);
            filterChain.addFilter(bbf);

            pfilter = new PosterizeFilter();
            pfilter.setLevel(level);
            filterChain.addFilter(pfilter);
            posterizedImage = filterChain.runFilters(substrateImage);
            canvasPanel.setImage(posterizedImage);

            AverageOutlineFilter outlineFilter=null;
            filterChain = new FilterChain();
            outlineFilter = new AverageOutlineFilter();
            outlineFilter.setColors(pfilter.getColors());
            outlineFilter.setPosterizedImage(posterizedImage);
            outlineFilter.setOutlineColor(Color.DARK_GRAY);
            filterChain.addFilter(outlineFilter);
            outlineImage = filterChain.runFilters(substrateImage);

            canvasPanel.setImage(outlineImage);
            ArrayList<AreaColor> areas = outlineFilter.getSortedAreas();*/


            Graphics2D graphics2D = backImage.createGraphics();
            graphics2D.setPaint(Color.white);
            graphics2D.setBackground(Color.white);
            graphics2D.setPaint(Color.white);

            graphics2D.clearRect(0, 0, backImage.getWidth(), backImage.getHeight());
            graphics2D.fillRect(0, 0, backImage.getWidth(), backImage.getHeight());
            //BrushStroke brushStroke = new BrushStroke(graphics2D,substrateImage.getWidth(),substrateImage.getHeight(),substrateImage);
            BSplineBrushStroke brushStroke = new BSplineBrushStroke(graphics2D,substrateImage.getWidth(),substrateImage.getHeight(),substrateImage);

            Mat dis = imread(input_img);
            Scalar scalar = new Scalar(255, 0, 0, 0);
            int DELTA = 1;
            int ARC_ANGLE = 5;
            int PREF_W = 300;
            int PREF_H = PREF_W;
            int LOOP_MAX = 150;
            for (int c = 0; c<1; c++) {
                INDArray flowField = Nd4j.readBinary(new File("etf_kernal_buffalo" + kernal + "_" + c + ".bin"));

                int arrayWidth = (int)flowField.shape()[1];
                int arrayHeight = (int)flowField.shape()[0];
                int ratioH = substrateImage.getHeight() / arrayHeight;
                int ratioW = substrateImage.getWidth() / arrayWidth;
                int x = PREF_W / 2;
                int y = PREF_H / 2;
                int width = 1;
                int height = 1;
                int startAngle = 0;
                int arcAngle = ARC_ANGLE;

                    for (int cc = 0; cc < LOOP_MAX; cc++) {
                        //g.drawArc(x, y, width, height, startAngle, arcAngle);
                        //(x1,y1)=(100+10cos150∘,100−10sin150∘)
                        //because (x1,y1) is 150 degrees around from the rightmost point of the circle, and the circle-center is at (100,100), and the radius is 10, and y increases down instead of up, which makes for the "-" in the second term.

                        //(x2,y2)=(100+10cos180∘,100−10sin180∘)
                        //because that point is 30 degrees further around from a point at angle 150 degrees.
                        for (int a = startAngle; a < arcAngle; a++  ) {
                            int x1 = x+ (int)(width*Math.cos(Math.toRadians(a)));
                            int y1 = y+ (int)(height*Math.sin(Math.toRadians(a)));
                            int i = x1;
                            int j = y1;
                            try {
                                float v0 = flowField.getFloat(i, j, 0);
                                float v1 = flowField.getFloat(i, j, 1);
                                Point p = new Point(j, i);
                                Point p2 = new Point((int) (j + v1 * 5), (int) (i + v0 * 5));
                                Point tp = new Point(j * ratioW, i * ratioH);
                                Point tp2 = new Point((int) (j + v1 * 5) * ratioW, (int) (i + v0 * 5) * ratioH);

                                float distance = (float) (Math.sqrt((p2.x() - p.x()) * (p2.x() - p.x()) + (p2.y() - p.y()) * (p2.y() - p.y())));




                                if (distance > 1) {

                                        Color primaryColor = new Color(substrateImage.getRGB(tp.x(), tp.y()));
                                        ArrayList<Color> secondaryColors = new ArrayList<>();
                                        secondaryColors.add(primaryColor.brighter());
                                        secondaryColors.add(primaryColor.darker());
                                        secondaryColors.add(primaryColor);
                                        brushStroke.addBrushStroke(primaryColor, secondaryColors, tp.x(), tp2.x(), tp.y(), tp2.y(), (int) (distance * ratioW / 2));

                                    //line(dis, p, p2, scalar);
                                }
                            } catch (Exception e) {
                                //e.printStackTrace();
                            }

                        }
                        x = x - DELTA;
                        y = y - DELTA;
                        width += 2 * DELTA;
                        height += 2 * DELTA;
                        startAngle = startAngle - arcAngle;
                        System.out.println("cc +" + cc );
                    }
                System.out.println("c = " + c);
            }
            canvasPanel.setImage(backImage);
            //canvasPanel.repaint();
            //imwrite("etf_kernal draw" + kernal + "all_5.png", dis);
            ImageIO.write(backImage,"jpg",new File("data/buffalloout bspline spiral.jpg"));
            System.out.println("finished");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] params){
        Loader.load(opencv_java.class);
        ETFImageSpiral etfi = new ETFImageSpiral();
        etfi.setVisible(true);
        etfi.draw_arrowline();

    }
}
