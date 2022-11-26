package uk.khall;


import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_java;
import org.datavec.image.loader.NativeImageLoader;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.opencv.core.CvType.CV_32FC1;


public class ETFImageBDVariScale {
    /**
     * import cv2
     * import numpy as np
     * import copy
     * import matplotlib.pyplot as plt
     * <p>
     * M_PI = 3.14159265358979323846
     * KERNEL = 5
     * COLOUR_OR_GRAY = 0
     * input_img = "data\\input.jpg"
     * SIZE = (scale, scale, 3)
     * <p>
     * flowField = np.zeros(SIZE, dtype = np.float32)
     * refinedETF = np.zeros(SIZE, dtype = np.float32)
     * gradientMag = np.zeros(SIZE, dtype = np.float32)
     * <p>
     * ####################
     * # Generate ETF
     * ####################
     * #memo
     * #cv2.normalize(src[, dst[, alpha[, beta[, norm_type[, dtype[, mask]]]]]])：正規化
     * #cv2.Sobel(src, ddepth, dx, dy[, dst[, ksize[, scale[, delta[, borderType]]]]])：微分
     * #cv2.magnitude(x, y[, magnitude])：2次元ベクトルの大きさ
     * <p>
     * def initial_ETF(input_img, size):
     * global flowField
     * global refinedETF
     * global gradientMag
     * <p>
     * src = cv2.imread(input_img, COLOUR_OR_GRAY)
     * src_n = np.zeros(size, dtype = np.float32)
     * src_n = cv2.normalize(src.astype('float32'), None, 0.0, 1.0, cv2.NORM_MINMAX)
     * <p>
     * #Generate grad_x and grad_y
     * grad_x = []
     * grad_y = []
     * grad_x = cv2.Sobel(src_n, cv2.CV_32FC1, 1, 0, ksize=5)
     * grad_y = cv2.Sobel(src_n, cv2.CV_32FC1, 0, 1, ksize=5)
     * <p>
     * #Compute gradient
     * gradientMag = cv2.sqrt(grad_x**2.0 + grad_y**2.0)
     * gradientMag = cv2.normalize(gradientMag.astype('float32'), None, 0.0, 1.0, cv2.NORM_MINMAX)
     * h,w = src.shape[0], src.shape[1]
     * for i in range(h):
     * for j in range(w):
     * u = grad_x[i][j]
     * v = grad_y[i][j]
     * n = np.array([v, u, 0.0])
     * cv2.normalize(np.array([v, u, 0.0]).astype('float32'), n)
     * flowField[i][j] = n
     * rotateFlow(flowField, flowField, 90.0)
     * <p>
     * <p>
     * if __name__ == '__main__':
     * initial_ETF(input_img, SIZE)
     * for i in range(10):
     * refine_ETF(KERNEL)
     * draw_arrowline(i,KERNEL)
     */

    public ETFImageBDVariScale(){

    }
    private String imageName;
    private int scale ;
    private INDArray flowField;
    private INDArray refinedETF;
    private INDArray gradientMag;
    private long h;
    private long w;
    private int kernal;
    private int loopLength;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getLoopLength() {
        return loopLength;
    }

    public void setLoopLength(int loopLength) {
        this.loopLength = loopLength;
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

    public void initialiseETF() {
        try {
            flowField = Nd4j.zeros(getScale(), getScale(), 3);
            refinedETF = Nd4j.zeros(getScale(), getScale(), 3);
            gradientMag = Nd4j.zeros(getScale(), getScale(), 3);
            h=getScale();
            w=getScale();
            String input_img = "data\\"+ getImageName() + ".jpg";
            Mat origsrc = imread(input_img, IMREAD_GRAYSCALE);
            Mat src = new Mat();
            Size sz = new Size (getScale(),getScale());
            resize(origsrc,src,sz);
            NativeImageLoader nativeImageLoader = new NativeImageLoader(getScale(), getScale(), 1);//Use the nativeImageLoader to convert to numerical matrix

            Mat src_nmat = new Mat(getScale(), getScale(), CV_8UC1, new Scalar(0,0,0,0));
            normalize(src, src_nmat, 0, 1, NORM_MINMAX,CV_32FC1, null);


            Mat grad_xmat = new Mat();
            Mat grad_ymat = new Mat();

            Sobel(src_nmat, grad_xmat, CV_32FC1, 1, 0,5,1,0,BORDER_DEFAULT);
            Sobel(src_nmat, grad_ymat, CV_32FC1, 0, 1,5,1,0,BORDER_DEFAULT);


            INDArray grad_x = nativeImageLoader.asMatrix(grad_xmat);//put image into INDArray
            INDArray grad_y = nativeImageLoader.asMatrix(grad_ymat);//put image into INDArray

            imwrite("data\\"+getImageName()+"gradx.jpg", nativeImageLoader.asMat(grad_x.mul(255)));
            imwrite("data\\"+getImageName()+"grady.jpg", nativeImageLoader.asMat(grad_y.mul(255)));

            Mat gradientMat = new Mat();
            Mat interMat = nativeImageLoader.asMat(grad_x.mul(grad_x).add(grad_y.mul(grad_y)));
            sqrt(interMat, gradientMat);
            normalize(gradientMat, gradientMat, 0.0, 1.0, NORM_MINMAX, CV_32FC1, null);
            gradientMag = nativeImageLoader.asMatrix(gradientMat);

            boolean firsterror = true;
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {

                    float u;
                    float v;
                    try {
                        int[] pos = {0, 0, i, j};
                        u = grad_x.getFloat(pos);
                        v = grad_y.getFloat(pos);
                        flowField.putScalar(i, j, 0, v);
                        flowField.putScalar(i, j, 1, u);
                        flowField.putScalar(i, j, 2, 0);

                    } catch (Exception e) {
                        if (firsterror) {
                            e.printStackTrace();
                            System.out.println(" i = " + i + " j = " + j);
                            firsterror = false;
                        }
                    }

                }
            }

            rotateFlow(90.0f);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * def rotateFlow(src, dst, theta):
     * theta = theta / 180.0 * M_PI;
     * h,w = src.shape[0], src.shape[1]
     * for i in range(h):
     * for j in range(w):
     * v = src[i][j]
     * print (v[0])
     * print (v[1])
     * rx = v[0] * np.cos(theta) - v[1] * np.sin(theta)
     * ry = v[1] * np.cos(theta) + v[0] * np.sin(theta)
     * flowField[i][j] = [rx, ry, 0.0]
     *
     * @param theta
     */
    private void rotateFlow(float theta) {
        theta = theta / 180.0f * (float) Math.PI;
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                float v0 = flowField.getFloat(i, j, 0);
                float v1 = flowField.getFloat(i, j, 1);
                float rx = v0 * cosTheta - v1 * sinTheta;
                float ry = v1 * cosTheta + v0 * sinTheta;
                flowField.putScalar(i, j, 0, rx);
                flowField.putScalar(i, j, 1, ry);
                flowField.putScalar(i, j, 2, 0.0);
            }
        }
    }

    /**
     * def refine_ETF(kernel):
     * global flowField
     * global refinedETF
     * global gradientMag
     * h_f,w_f = flowField.shape[0], flowField.shape[1]
     * for r in range(h_f):
     * for c in range(w_f):
     * computeNewVector(c, r, kernel)
     * flowField = copy.deepcopy(refinedETF)
     */
    public void refine_ETF() {
        long[] hw_f = flowField.shape();
        for (int r = 0; r < (int) hw_f[0]; r++) {
            for (int c = 0; c < (int) hw_f[1]; c++) {
                computeNewVector(c, r);
            }
        }
        flowField = refinedETF.dup();

    }

    /**
     * #Paper's Eq(1)
     * def computeNewVector(x, y, kernel):
     * global flowField
     * global refinedETF
     * global gradientMag
     * t_cur_x = flowField[y][x]
     * t_new = (0, 0, 0)
     * h_r,w_r = refinedETF.shape[0], refinedETF.shape[1]
     * for r in range(y - kernel, y + kernel + 1):
     * for c in range(x - kernel, x + kernel + 1):
     * if (r < 0 or r >= h_r or c < 0 or c >= w_r):
     * continue
     * t_cur_y = flowField[r][c]
     * a = np.array([x, y])
     * b = np.array([c, r])
     * try:
     * phi = computePhi(t_cur_x, t_cur_y);
     * w_s = computeWs(a, b, kernel);
     * w_m = computeWm(gradientMag[y][x], gradientMag[r][c])
     * w_d = computeWd(t_cur_x, t_cur_y)
     * t_new += phi * t_cur_y * w_s * w_m * w_d
     * except IndexError:
     * print ('exception %s %s %s %s' % (x,y,r,c))
     * <p>
     * n = t_new
     * cv2.normalize(t_new, n)
     * refinedETF[y][x] = n
     *
     * @param x column value
     * @param y row value
     */

    private void computeNewVector(int x, int y) {

        float t_cur_x0 = flowField.getFloat(y, x, 0);
        float t_cur_x1 = flowField.getFloat(y, x, 1);

        INDArray t_cur_x = Nd4j.create(new float[]{t_cur_x0, t_cur_x1, 0.0f}, new int[]{1, 3});
        INDArray t_new = Nd4j.zeros(1, 3);
        long h_r = scale;
        long w_r = scale;
        for (int r = y - getKernal(); r < y + getKernal() + 1; r++) {
            for (int c = x - getKernal(); c < x + getKernal() + 1; c++) {

                if (r < 0 || r >= h_r || c < 0 || c >= w_r)
                    continue;
                INDArray t_cur_y = Nd4j.create(new float[]{flowField.getFloat(r, c, 0), flowField.getFloat(r, c, 1), flowField.getFloat(r, c, 2)}, new int[]{1, 3});
                INDArray a = Nd4j.zeros(1, 2);
                a.putScalar(0, 0, x);
                a.putScalar(0, 1, y);
                INDArray b = Nd4j.zeros(1, 2);
                b.putScalar(0, 0, c);
                b.putScalar(0, 1, r);
                float phi = computePhi(t_cur_x, t_cur_y);
                float w_s = computeWs(a, b);
                int[] wmxypos = {0, 0, y, x};
                int[] wmrcpos = {0, 0, r, c};
                float wmxy = gradientMag.getFloat(wmxypos);
                float wmrc = gradientMag.getFloat(wmrcpos);
                float w_m = computeWm(wmxy, wmrc);
                float w_d = computeWd(t_cur_x, t_cur_y);
                t_new = t_new.add(t_cur_y.mul(phi).mul(w_s).mul(w_m).mul(w_d));
                t_cur_y.close();
                a.close();
                b.close();
            }
        }


        INDArray nn = snormalize2(t_new.reshape(3, 1, 1));

        refinedETF.putScalar(y, x, 0, nn.getFloat(0, 0));
        refinedETF.putScalar(y, x, 1, nn.getFloat(0, 1));
        refinedETF.putScalar(y, x, 2, nn.getFloat(0, 2));
        t_new.close();
    }

    /**
     * np.dot(x,y)
     *
     * @param x column
     * @param y row
     * @return dot product
     */
    private float computePhi(INDArray x, INDArray y) {

        x = x.mmul(y.reshape(3, 1));
        if (x.getFloat(0, 0) > 0) {
            return 1f;
        }
        return -1f;
    }

    /**
     * np.linalg.norm(x-y) < r:
     *
     * @param x column
     * @param y row
     * @return white space euclidean distance
     */
    private float computeWs(INDArray x, INDArray y) {
        if (x.distance2(y) < getKernal()) {
            return 1f;
        }
        return 0f;
    }

    /**
     * wm = (1 + np.tanh(gradmag_y - gradmag_x)) / 2
     *
     * @param gradmag_y gradient
     * @param gradmag_x gradient
     * @return centre
     */
    private float computeWm(float gradmag_y, float gradmag_x) {
        float wm = (float) ((1 + Math.tanh(gradmag_y - gradmag_x)) / 2);
        return wm;
    }

    /**
     * abs(x.dot(y))
     *
     * @param x column
     * @param y row
     * @return  absolute
     */
    private float computeWd(INDArray x, INDArray y) {

        x = x.mmul(y.reshape(3, 1));
        return Math.abs(x.getFloat(0, 0));

    }

    /**
     * @param count flowfield count
     */
    public void saveFlowField(int count) {
        try {
            Nd4j.saveBinary(flowField,new File("etf_kernal_" + getImageName() + getKernal() + "_" + scale + "_" + count +".bin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Normalise the array
     * @param array array of values
     * @return normalised array
     */
    private static INDArray snormalize2(INDArray array) {
        try {
            NativeImageLoader convImageLoader = new NativeImageLoader(1, 1, 3);
            Mat nmat = convImageLoader.asMat(array);
            Mat nmat2 = nmat.clone();
            normalize(nmat2, nmat);
            INDArray t_new = convImageLoader.asMatrix(nmat);
            return t_new.reshape(1, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] params) {
        Loader.load(opencv_java.class);
        ETFImageBDVariScale etfi = new ETFImageBDVariScale();
        int loopLength = 2;
        etfi.setImageName("whitleytree");
        etfi.setLoopLength(loopLength);
        etfi.setScale(600);
        etfi.setKernal(5);
        etfi.initialiseETF();
        for (int i = 0; i < loopLength; i++) {
            System.out.println("i=" + i);
            etfi.refine_ETF();
            System.out.println("completed refine_ETF " + i);
            etfi.saveFlowField(i);
            System.out.println("completed draw_arrowline " + i);
        }
    }
}
