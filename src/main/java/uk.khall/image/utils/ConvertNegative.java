package uk.khall.image.utils;

import org.bytedeco.opencv.opencv_core.Mat;
import org.datavec.image.loader.NativeImageLoader;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.IOException;

public class ConvertNegative {
    /**
     * if args.base:
     *     base = cv2.imread(args.base, cv2.IMREAD_UNCHANGED)
     *     base = np.float32(base) / np.iinfo(base.dtype).max
     *     base = cv2.mean(base)
     * @param base
     * @return
     */
    private static float getBase(Mat base){
        NativeImageLoader nativeImageLoader = new NativeImageLoader(base.rows(), base.cols() ,3);
        try {
            INDArray baseArray = nativeImageLoader.asMatrix(base);
            INDArray divArray =  baseArray.div(255);
            return divArray.ameanNumber().floatValue();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * def invert(neg, base, print_progress=False):
     *     if print_progress:
     *             print(
     *                 ERASE_LINE + 'Removing orange mask...', end='\r'),
     *     # remove orange mask
     *     b,g,r = cv2.split(neg)
     *     b = b * (1 / base[0])
     *     g = g * (1 / base[1])
     *     r = r * (1 / base[2])
     *     res = cv2.merge((b,g,r))
     *     if print_progress:
     *             print(
     *                 ERASE_LINE + 'Inverting...', end='\r'),
     *     # invert
     *     res = 1 - res
     *     if print_progress:
     *             print(
     *                 ERASE_LINE + 'Normalizing...', end='\r'),
     *     res = cv2.normalize(res, None, 0.0, 1.0, cv2.NORM_MINMAX)
     *     return res
     * @param neg
     * @param base
     * @param print_progress
     */
    private static void invert(Mat neg, Mat base,  boolean print_progress){

    }
    public static void main(String[] params){

    }
}
