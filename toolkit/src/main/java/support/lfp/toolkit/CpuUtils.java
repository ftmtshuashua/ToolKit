package support.lfp.toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.regex.Pattern;


/**
 * <pre>
 * Tip:
 *      CUP工具
 *
 * Function:
 *      getNumCores()           :获得CPU核心数
 *      getMinCpuFrequence()    :获得CPU最小核心频率(KHZ)
 *      getCurCpuFreq()         :获得CPU当前核心频率(KHZ)
 *      getCpuName()            :获得CPU名字
 *      getThreadValue()        :获得最优线程数
 *      getCupInfo()            :获得CPU信息
 *
 *
 * Created by LiFuPing on 2018/6/27.
 * </pre>
 */
public class CpuUtils {

    //Private Class to display only CPU devices in the directory listing
    private static final class CpuFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            //Check if filename is "cpu", followed by a single digit number
            if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                return true;
            }
            return false;
        }
    }

    /**
     * 获得CPU核心数目
     *
     * @return int
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 获取CPU最小频率（单位KHZ）
     *
     * @return String
     */
    public static String getMinCpuFrequence() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 实时获取CPU当前频率（单位KHZ）
     *
     * @return String
     */
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取CPU名字
     *
     * @return String
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得CPU信息
     *
     * @return String
     */
    public static String getCupInfo() {
        return MessageFormat.format("CPU_Info：\nName:{0}\n核心数:{1}核\n最小频率:{2}khz\n当前频率:{3}khz"
                , getCpuName()
                , getNumCores()
                , getMinCpuFrequence()
                , getCurCpuFreq()
        );
    }

    /**
     * 根据CPU核心数计算最优线程数<br>
     * CPU敏感的程序，线程数大于处理器个数是没有意义的<br>
     * 参考：https://blog.csdn.net/holmofy/article/details/81271839
     *
     * @param blockingRate 阻塞率 - 计算结果=核心数*阻塞率。最优值：计算密集型阻塞率=1，IO密集型：阻塞率应该经可能大
     * @param defualt      默认线程个数 - 当获取CPU核心数失败的时候使用默认数目
     * @return 计算之后的线程个数
     */
    public static int getThreadValue(float blockingRate, int defualt) {
        int maxConcurrency;
        try {
            final int numCores = getNumCores();
            maxConcurrency = (int) (numCores * blockingRate);
            if (maxConcurrency < numCores) maxConcurrency = numCores;
        } catch (Exception e) {
            e.printStackTrace();
            maxConcurrency = defualt;
        }
        return maxConcurrency;
    }

}
