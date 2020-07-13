package com.carry.www.utils.ExportUtil;

import org.apache.poi.xwpf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：下载WORD工具类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月02日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class WordUtil {

   /**
   * @方法描述: 导出操作 生成服务器或者项目里的文件，给后续download提供下载文件，这里目前只写了word的，后续可以修改此方法，可以多类型通用
    * path class的路径   pathSuffix word模板的路径， path+pathSuffix 既是文件的绝对路径 ，可以使服务器或者项目里的路径 这里用项目里的路径
   * @return: void
   * @Author: carry
   */
    public void exportToWordMethod(HttpServletResponse response,  Map<String, Object> params, String path, String pathSuffix) {
        try {
            //此处谁调用 谁传值
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("name", "测试");

            //String filePath = path + "template/123.docx";
            String filePath = path+pathSuffix;
            InputStream is = new FileInputStream(filePath);
            XWPFDocument doc = new XWPFDocument(is);
            //替换段落里面的变量
            this.replaceInPara(doc, params);
            //替换表格里面的变量
            this.replaceInTable(doc, params);
            //此处临时放置文件的路径，如果是项目的建议操作完删掉，如果是服务器本地的无所谓
            String downloadURL = path + "template/wordTemplate.docx";
            OutputStream os = new FileOutputStream(downloadURL);
            doc.write(os);
            this.download(downloadURL, response);
            this.close(is);
            this.close(os);
        } catch (Exception e) {

        }

    }

    /**
    * @方法描述:
     * path 下载的文件路径  projectname  这里意义不大 直接被vue前台替换了   suffix后缀名  这里意义不大 直接被vue前台替换了
    * @return: javax.servlet.http.HttpServletResponse
    * @Author: carry
    */
    public HttpServletResponse download(String downloadPath, HttpServletResponse response) throws Exception {
        // path是指欲下载的文件的路径。
        File file = new File(downloadPath);
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(downloadPath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 设置response的Header  这里意义不大 直接被vue前台替换了 所以可以删掉
        response.addHeader("Content-Disposition", "attachment;filename="
                + new String(("" + "docx").getBytes("gbk"),
                "iso-8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(
                response.getOutputStream());
        //注意这里很重要 vue的回调里根据ContentType类判断是否是下载的操作，这里不要改
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();

        return response;
    }

    /**
    * @方法描述: 替换段落里面的变量
     * doc 要替换的文档  params 替换的参数，数据库查询出来的数据
    * @return: void
    * @Author: carry
    */
    private void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    /**
     * @方法描述: 替换段落里面的变量
     * para 要替换的文档  params 替换的参数，数据库查询出来的数据
     * @return: void
     * @Author: carry
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = this.matcher(runText);
                if (matcher.find()) {
                    while ((matcher = this.matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }

    /**
    * @方法描述: 替换表格里面的变量
    * @return: void
    * @Author: carry
    */
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
    * @方法描述:  正则匹配字符串
    * @return: java.util.regex.Matcher
    * @Author: carry
    */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
    * @方法描述: 关闭输入流
    * @return: void
    * @Author: carry
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
   * @方法描述: 关闭输出流
   * @return: void
   * @Author: carry
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
