package com.eastrobot.kbs.template.util;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class FileUploadUtil {

    /** 文件存放的目录 **/
    private static final String UPLOAD_ROOT_DIR = "DATAS";

    private static String fileUploadRootPath;

    static {
        //获取根目录
        File file;
        try {
            file = new File(ResourceUtils.getURL("./" + UPLOAD_ROOT_DIR).getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取根目录失败，无法创建上传目录！");
        }
        File upload = Paths.get(file.getAbsolutePath()).toFile();
        if (!upload.exists()) {
            upload.mkdirs();
        }
        fileUploadRootPath = file.getAbsolutePath();
    }

    /**
     * 上传单个文件
     * 最后文件存放路径为：static/upload/image/test.jpg
     * 文件访问路径为：http://127.0.0.1:8080/upload/image/test.jpg
     * 该方法返回值为：/upload/image/test.jpg
     *
     * @param inputStream 文件流
     * @param catePath    分类文件路径，如：image/
     * @param filename    文件名，如：test.jpg
     *
     * @return 成功：上传后的文件访问路径，失败返回：null
     */
    public static String upload(InputStream inputStream, String catePath, String filename) {

        File uploadFile = Paths.get(fileUploadRootPath, catePath, filename).toFile();
        try {
            FileUtils.copyInputStreamToFile(inputStream, uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return Paths.get(UPLOAD_ROOT_DIR, catePath, filename).toString();
    }

    /**
     * 删除文件
     *
     * @param path 文件访问的路径upload开始 如： /upload/image/test.jpg
     *
     * @return true 删除成功； false 删除失败
     */
    public static boolean delete(String path) {
        return Paths.get(fileUploadRootPath).getParent().resolve(path).toFile().delete();
    }

}
