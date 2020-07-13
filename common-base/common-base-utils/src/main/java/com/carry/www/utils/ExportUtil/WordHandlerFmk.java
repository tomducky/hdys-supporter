package com.carry.www.utils.ExportUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年10月12日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class WordHandlerFmk {
    private Configuration configuration = null;

    public WordHandlerFmk() {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
    }

    public void createDoc( Map<String ,Object > result,String fileName,String inPath,String outPath) throws Exception {
        configuration.setDefaultEncoding("utf-8");
        //模板方在项目的resource下的template里面
        // configuration.setClassForTemplateLoading(this.getClass(), "/template");
        //模板放在项目的盘符下
        configuration.setDirectoryForTemplateLoading(new File(inPath));
        System.out.println("文件路径"+outPath);
        Template t = null;
        try {
            t = configuration.getTemplate(fileName,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outFile = new File(outPath);
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            t.process(result, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法描述:
     * path 下载的文件路径  projectname  这里意义不大 直接被vue前台替换了   suffix后缀名  这里意义不大 直接被vue前台替换了
     * @return: javax.servlet.http.HttpServletResponse
     * @Author: carry
     */
    public HttpServletResponse download(String downloadPath, HttpServletResponse response) throws Exception {
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(downloadPath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 设置response的Header  这里意义不大 直接被vue前台替换了 所以可以删掉
        response.addHeader("Content-Disposition", "attachment;filename="
                +"test"+ new String(("" + ".docx").getBytes("gbk"),
                "iso-8859-1"));
        OutputStream toClient = new BufferedOutputStream(
                response.getOutputStream());
        //注意这里很重要 vue的回调里根据ContentType类判断是否是下载的操作，这里不要改
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        this.close(fis);
        this.close(toClient);
        return response;
    }


    /**
     *  关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
