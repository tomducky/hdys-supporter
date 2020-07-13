package com.carry.www.utils.base;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 类描述：图片工具类
 *
 * @author carry
 * @version 1.0 CreateDate: 2019年4月28日
 *
 *  修订历史： 日期 修订者 修订描述
 */
public class ImgUtil {

   /**
    * @方法描述:   获取图片并显示在页面
    * @Param: a
    * @return: void
    * @Author: carry
    */
    public void queryPicToBinay(String adress, HttpServletResponse response) throws IOException {
        if (adress != null) {
            response.setContentType("image/jpeg");
            FileInputStream is = this.getPhotoImageBlob(adress);

            if (is != null) {
                int i = is.available(); // 得到文件大小
                byte data[] = new byte[i];
                is.read(data); // 读数据
                is.close();
                response.setContentType("image/jpeg");  // 设置返回的文件类型
                OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
                toClient.write(data); // 输出数据
                toClient.close();
            }
        }

    }


    /**
     * @方法描述:  读数据库，获取本地图片输入流
     * @Param: [adress]
     * @return: java.io.FileInputStream
     * @Author: carry
     */
    public FileInputStream getPhotoImageBlob(String adress) {
        FileInputStream is = null;
        File filePic = new File(adress);

        try {
            // 存入的时候 以hdys开头
            is = new FileInputStream("hdys" + filePic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return is;

    }
}
