package common;

import edu.princeton.cs.algs4.In;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 需要数据集进行测试的测试类
 */
public class TestData {

    private final String urlPrefix;
    private final String fileParentPath;

    private final String tinyFile;
    private final String mediumFile;
    private final String largeFile;


    public TestData(String urlPrefix, String filePrefix,
                    String tinyFileName, String mediumFileName, String largeFileName) {
        this.urlPrefix = urlPrefix;
        fileParentPath = filePrefix;
        tinyFile = tinyFileName;
        mediumFile = mediumFileName;
        largeFile = largeFileName;
    }

    private String getFileName(DataSize dataSize) {
        String fileName;
        switch (dataSize) {
            case TINY:
                fileName = tinyFile;
                break;
            case MEDIUM:
                fileName = mediumFile;
                break;
            case LARGE:
                fileName = largeFile;
                break;
            default:
                throw new IllegalArgumentException("未知DataSize枚举类型：" + dataSize);
        }
        if (fileName == null) {
            throw new RuntimeException("dataSize=" + dataSize + "的测试数据集未配置");
        }
        return fileName;
    }

    public In getIn(DataSize dataSize, boolean useLocal) {
        In in;
        if (useLocal) {
            File file = new File(fileParentPath + getFileName(dataSize));
            in = new In(file);
        } else {
            try {
                URL url = new URL(urlPrefix + getFileName(dataSize));
                in = new In(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return in;
    }

}
