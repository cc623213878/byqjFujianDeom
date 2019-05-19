package com.byqj.utils;

import com.byqj.vo.PdfMessageListVo;
import com.google.common.collect.Lists;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFForExamUtil {

    private static final String BASE_FONT_PATH = "/product/FJNUTss/files/simsun.ttc,1";

    public static String createPdf(List<List<PdfMessageListVo>> pdfMessageListVosList, String pdfOneTemplateFileLocalPath
            , String pdfTwoTemplateFileLocalPath, String pdfThreeTemplateFileLocalPath, String uploadFileLocalPath, String fileName) {
        //templatePath 第一个pdf模板路径 使用的时候加上模板名称
        String oneTemplatePath = pdfOneTemplateFileLocalPath;
        //templatePath 第二个pdf模板（2个准考证）路径 使用的时候加上模板名称
        String twoTemplatePath = pdfTwoTemplateFileLocalPath;
        //templatePath 第三个pdf模板（3个准考证）路径 使用的时候加上模板名称
        String threeTemplatePath = pdfThreeTemplateFileLocalPath;
        //uploadFileLocalPath 存储路径
        String newPDFPath = uploadFileLocalPath + File.separator + fileName;
        try {
            List<PdfReader> readerList = Lists.newArrayList();
            OutputStream out = new FileOutputStream(newPDFPath);
            for (List<PdfMessageListVo> pdfMessageListVos : pdfMessageListVosList) {

                //一个准考证则调用一个准考证的模板
                if (pdfMessageListVos.size() <= 12) {
                    PdfReader reader = new PdfReader(threeTemplatePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    /* 读取PDF模板内容 */
                    PdfStamper ps = new PdfStamper(reader, bos);

                    /*设置使用itext-asian.jar的中文字体 */
                    BaseFont bf = BaseFont.createFont(BASE_FONT_PATH, BaseFont.IDENTITY_H, false);

                    ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
                    fontList.add(bf);

                    /* 获取模板中的所有字段 */
                    AcroFields fields = ps.getAcroFields();
                    fields.setSubstitutionFonts(fontList);

                    for (PdfMessageListVo pdfMessageListVo : pdfMessageListVos) {
                        //等于文字处理
                        if (pdfMessageListVo.getType() == 0) {
                            fields.setField(pdfMessageListVo.getName(), pdfMessageListVo.getContent());
                        }
                        //图片的内容处理
                        if (pdfMessageListVo.getType() == 1) {
                            String imgpath = uploadFileLocalPath + pdfMessageListVo.getContent();
                            int pageNo = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).page;
                            Rectangle signRect = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).position;
                            float x = signRect.getLeft();
                            float y = signRect.getBottom();
                            //根据路径读取图片
                            Image image = Image.getInstance(imgpath);
                            //获取图片页面
                            PdfContentByte under = ps.getOverContent(pageNo);
                            //图片大小自适应
                            //image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                            image.scaleAbsolute((float) (25 * 2.5), (float) (35 * 2.5));// 直接设定显示尺寸

                            //添加图片
                            image.setAbsolutePosition(x, y);
                            under.addImage(image);
                        }

                    }
                    /* 必须要调用这个，否则文档不会生成的 */
                    ps.setFormFlattening(true);
                    ps.close();

                    reader = new PdfReader(bos.toByteArray());
                    readerList.add(reader);

                }


                if (pdfMessageListVos.size() <= 24 && pdfMessageListVos.size() > 12) {
                    PdfReader reader = new PdfReader(twoTemplatePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    /* 读取PDF模板内容 */
                    PdfStamper ps = new PdfStamper(reader, bos);

                    /*设置使用itext-asian.jar的中文字体 */
                    BaseFont bf = BaseFont.createFont(BASE_FONT_PATH, BaseFont.IDENTITY_H, false);

                    ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
                    fontList.add(bf);

                    /* 获取模板中的所有字段 */
                    AcroFields fields = ps.getAcroFields();
                    fields.setSubstitutionFonts(fontList);

                    for (PdfMessageListVo pdfMessageListVo : pdfMessageListVos) {
                        //等于文字处理
                        if (pdfMessageListVo.getType() == 0) {
                            fields.setField(pdfMessageListVo.getName(), pdfMessageListVo.getContent());
                        }
                        //图片的内容处理
                        if (pdfMessageListVo.getType() == 1) {
                            String imgpath = uploadFileLocalPath + pdfMessageListVo.getContent();
                            int pageNo = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).page;
                            Rectangle signRect = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).position;
                            float x = signRect.getLeft();
                            float y = signRect.getBottom();
                            //根据路径读取图片
                            Image image = Image.getInstance(imgpath);
                            //获取图片页面
                            PdfContentByte under = ps.getOverContent(pageNo);
                            //图片大小自适应
                            //image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                            image.scaleAbsolute((float) (25 * 2.5), (float) (35 * 2.5));// 直接设定显示尺寸

                            //添加图片
                            image.setAbsolutePosition(x, y);
                            under.addImage(image);
                        }

                    }
                    /* 必须要调用这个，否则文档不会生成的 */
                    ps.setFormFlattening(true);
                    ps.close();

                    reader = new PdfReader(bos.toByteArray());
                    readerList.add(reader);

                }

                if (pdfMessageListVos.size() > 24) {
                    PdfReader reader = new PdfReader(oneTemplatePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    /* 读取PDF模板内容 */
                    PdfStamper ps = new PdfStamper(reader, bos);

                    /*设置使用itext-asian.jar的中文字体 */
                    BaseFont bf = BaseFont.createFont(BASE_FONT_PATH, BaseFont.IDENTITY_H, false);

                    ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
                    fontList.add(bf);

                    /* 获取模板中的所有字段 */
                    AcroFields fields = ps.getAcroFields();
                    fields.setSubstitutionFonts(fontList);

                    for (PdfMessageListVo pdfMessageListVo : pdfMessageListVos) {
                        //等于文字处理
                        if (pdfMessageListVo.getType() == 0) {
                            fields.setField(pdfMessageListVo.getName(), pdfMessageListVo.getContent());
                        }
                        //图片的内容处理
                        if (pdfMessageListVo.getType() == 1) {
                            String imgpath = uploadFileLocalPath + pdfMessageListVo.getContent();
                            int pageNo = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).page;
                            Rectangle signRect = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).position;
                            float x = signRect.getLeft();
                            float y = signRect.getBottom();
                            //根据路径读取图片
                            Image image = Image.getInstance(imgpath);
                            //获取图片页面
                            PdfContentByte under = ps.getOverContent(pageNo);
                            //图片大小自适应
                            //image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                            image.scaleAbsolute((float) (25 * 2.5), (float) (35 * 2.5));// 直接设定显示尺寸

                            //添加图片
                            image.setAbsolutePosition(x, y);
                            under.addImage(image);
                        }

                    }
                    /* 必须要调用这个，否则文档不会生成的 */
                    ps.setFormFlattening(true);
                    ps.close();

                    reader = new PdfReader(bos.toByteArray());
                    readerList.add(reader);
                }


            }

            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, out);
            document.open();
            PdfImportedPage page = null;
            for (PdfReader pdfReader : readerList) {
                //这里意思是请求pdf模板中第几页进行复制，假如多页可以重复add
                page = copy.getImportedPage(pdfReader, 1);
                copy.addPage(page);
                pdfReader.close();
            }
            copy.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    /**
     * @param pdfMessageListVosList
     * @param pdfOneTemplateFileLocalPath
     * @param pdfTwoTemplateFileLocalPath
     * @param uploadFileLocalPath
     * @param fileName
     * @return
     */
    public static String createPostNoticePdf(List<List<PdfMessageListVo>> pdfMessageListVosList, String pdfOneTemplateFileLocalPath
            , String pdfTwoTemplateFileLocalPath, String uploadFileLocalPath, String fileName) {
        //templatePath 第一个pdf模板路径 使用的时候加上模板名称
        String oneTemplatePath = pdfOneTemplateFileLocalPath;
        //templatePath 第二个pdf模板（半页）路径 使用的时候加上模板名称
        String twoTemplatePath = pdfTwoTemplateFileLocalPath;
        //uploadFileLocalPath 存储路径
        String newPDFPath = uploadFileLocalPath + File.separator + fileName;
        try {
            List<PdfReader> readerList = Lists.newArrayList();
            OutputStream out = new FileOutputStream(newPDFPath);
            for (List<PdfMessageListVo> pdfMessageListVos : pdfMessageListVosList) {

                //半页则调用半页的模板 TODO : 2019/4/9 条件修改多少长度为半页
                if (pdfMessageListVos.size() <= 13) {
                    PdfReader reader = new PdfReader(twoTemplatePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    /* 读取PDF模板内容 */
                    PdfStamper ps = new PdfStamper(reader, bos);

                    /*设置使用itext-asian.jar的中文字体 */
                    BaseFont bf = BaseFont.createFont(BASE_FONT_PATH, BaseFont.IDENTITY_H, false);

                    ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
                    fontList.add(bf);

                    /* 获取模板中的所有字段 */
                    AcroFields fields = ps.getAcroFields();
                    fields.setSubstitutionFonts(fontList);

                    for (PdfMessageListVo pdfMessageListVo : pdfMessageListVos) {
                        //等于文字处理
                        if (pdfMessageListVo.getType() == 0) {
                            fields.setField(pdfMessageListVo.getName(), pdfMessageListVo.getContent());
                        }
                        //图片的内容处理
                        if (pdfMessageListVo.getType() == 1) {
                            String imgpath = uploadFileLocalPath + pdfMessageListVo.getContent();
                            int pageNo = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).page;
                            Rectangle signRect = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).position;
                            float x = signRect.getLeft();
                            float y = signRect.getBottom();
                            //根据路径读取图片
                            Image image = Image.getInstance(imgpath);
                            //获取图片页面
                            PdfContentByte under = ps.getOverContent(pageNo);
                            //图片大小自适应
                            //image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                            image.scaleAbsolute((float) (25 * 2.5), (float) (35 * 2.5));// 直接设定显示尺寸

                            //添加图片
                            image.setAbsolutePosition(x, y);
                            under.addImage(image);
                        }

                    }
                    /* 必须要调用这个，否则文档不会生成的 */
                    ps.setFormFlattening(true);
                    ps.close();

                    reader = new PdfReader(bos.toByteArray());
                    readerList.add(reader);

                } else {
                    PdfReader reader = new PdfReader(oneTemplatePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    /* 读取PDF模板内容 */
                    PdfStamper ps = new PdfStamper(reader, bos);

                    /*设置使用itext-asian.jar的中文字体 */
                    BaseFont bf = BaseFont.createFont(BASE_FONT_PATH, BaseFont.IDENTITY_H, false);

                    ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
                    fontList.add(bf);

                    /* 获取模板中的所有字段 */
                    AcroFields fields = ps.getAcroFields();
                    fields.setSubstitutionFonts(fontList);

                    for (PdfMessageListVo pdfMessageListVo : pdfMessageListVos) {
                        //等于文字处理
                        if (pdfMessageListVo.getType() == 0) {
                            fields.setField(pdfMessageListVo.getName(), pdfMessageListVo.getContent());
                        }
                        //图片的内容处理
                        if (pdfMessageListVo.getType() == 1) {
                            String imgpath = uploadFileLocalPath + pdfMessageListVo.getContent();
                            int pageNo = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).page;
                            Rectangle signRect = fields.getFieldPositions(pdfMessageListVo.getName()).get(0).position;
                            float x = signRect.getLeft();
                            float y = signRect.getBottom();
                            //根据路径读取图片
                            Image image = Image.getInstance(imgpath);
                            //获取图片页面
                            PdfContentByte under = ps.getOverContent(pageNo);
                            //图片大小自适应
                            //image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                            image.scaleAbsolute((float) (25 * 2.5), (float) (35 * 2.5));// 直接设定显示尺寸

                            //添加图片
                            image.setAbsolutePosition(x, y);
                            under.addImage(image);
                        }

                    }
                    /* 必须要调用这个，否则文档不会生成的 */
                    ps.setFormFlattening(true);
                    ps.close();

                    reader = new PdfReader(bos.toByteArray());
                    readerList.add(reader);
                }


            }

            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, out);
            document.open();
            PdfImportedPage page = null;
            for (PdfReader pdfReader : readerList) {
                //这里意思是请求pdf模板中第几页进行复制，假如多页可以重复add
                page = copy.getImportedPage(pdfReader, 1);
                copy.addPage(page);
                pdfReader.close();
            }
            copy.close();
            out.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return fileName;
    }


}