package com.carry.www.utils.file;


import com.carry.www.utils.base.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 类描述：文件上传
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月02日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class FileUploadUtils {

    // 默认大小 50M
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    // 默认的文件名最大长度 100
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    //  默认上传的地址
    private static String defaultBaseDir = "/hdys";

    /**
     * @方法描述: 文件上传 默认基地址
     * @Param: [file, filePath, modelPath]
     * @return: java.lang.String
     * @Author: carry
     */
    public static final String upload(MultipartFile file, String filePath, String modelPath) throws IOException {
        try {

            return upload(defaultBaseDir, file, filePath, modelPath);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }


    /**
     * @方法描述: 文件上传
     * @Param: [baseDir 文件上传基路径, file 文件, filePath 文件路径, modelPath 模块路径]
     * @return: java.lang.String
     * @Author: carry
     */
    public static final String upload(String baseDir, MultipartFile file, String filePath, String modelPath) throws IOException {

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new RuntimeException("文件名太长！");
        }

        assertAllowed(file);
        String path = "";
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        // 拼接日期
        String fileName = DateUtils.getNowDateTimeFmt("yyyy-MM-dd hh24:mi:ss") + file.getOriginalFilename();
        // 获取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        path = filePath + "/" + suffix + "/" + modelPath;

        File desc = getAbsoluteFile(baseDir + "/" + path, fileName);
        file.transferTo(desc);

        return baseDir + "/" + path + "/" + fileName;
    }

    /**
     * @方法描述:上传文件
     * @Param: [uploadDir, filename]
     * @return: java.io.File
     * @Author: carry
     */
    private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException {
        File fileRoot = new File(uploadDir);

        if (!fileRoot.exists()) {
            fileRoot.mkdirs();
        }

        File targetFile = new File(fileRoot.getAbsolutePath(), filename);

        return targetFile;
    }


    /**
     * @方法描述: 文件大小校验
     * @Param: [file]
     * @return: void
     * @Author: carry
     */
    public static final void assertAllowed(MultipartFile file) {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new RuntimeException("文件最大支持" + DEFAULT_MAX_SIZE);
        }
    }
}
