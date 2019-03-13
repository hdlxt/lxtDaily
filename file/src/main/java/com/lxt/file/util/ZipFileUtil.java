package com.lxt.file.util;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @ClassName: ZipFileUtil
 * @Description: Zip文件工具类
 * @Author: lxt
 * @Date: 2019-02-18 13:51
 * @Version 1.0
 **/
public class ZipFileUtil {

	/**
	 * @Title: compressFiles2Zip
	 * @Description:  Zip文件压缩
	 * @Author: lxt 
	 * @param: files
	 * @param: zipFilePath
	 * @Date: 2019-02-18 15:40 
	 * @return: java.io.File
	 * @throws: 
	 */
	public static File compressFiles2Zip(List<File>files, String zipFilePath) {
		if (files != null && files.size() > 0) {
			if (isEndsWithZip(zipFilePath)) {
				ZipArchiveOutputStream zaos = null;
				try {
					File zipFile = new File(zipFilePath);
					zaos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
					// Use Zip64 extensions for all entries where they are required
					zaos.setUseZip64(Zip64Mode.AsNeeded);
					zaos.setLevel(0);
					// 将每个文件用ZipArchiveEntry封装
					// 再用ZipArchiveOutputStream写到压缩文件中
					for (File file : files) {
						if (file != null) {
							ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
							zaos.putArchiveEntry(zipArchiveEntry);
							InputStream is = null;
							try {
								is = new BufferedInputStream(new FileInputStream(file));
								byte[] buffer = new byte[1024 * 5];
								int len = -1;
								while ((len = is.read(buffer)) != -1) {
									// 把缓冲区的字节写入到ZipArchiveEntry
									zaos.write(buffer, 0, len);
								}
								// Writes all necessary data for this entry.
								zaos.closeArchiveEntry();
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								if (is != null) {
                                    is.close();
                                }
							}

						}
					}
					zaos.finish();
					return zipFile;
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (zaos != null) {
							zaos.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

			}

		}
		return null;
	}
	/**
	 * 判断文件名是否以.zip为后缀
	 *
	 * @param fileName
	 *            需要判断的文件名
	 * @return 是zip文件返回true,否则返回false
	 */
	public static boolean isEndsWithZip(String fileName) {
		boolean flag = false;
		if (fileName != null && !"".equals(fileName.trim())) {
			if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip")) {
				flag = true;
			}
		}
		return flag;
	}
	/**
	 * 解压文件
	 * 
	 * @param zipFile
	 *            待解压文件
	 * @param descDir
	 *            解压后存放位置
     * @param charset
	 *            编码格式eg: gbk/utf8
	 */
	public static void decompressZip(File zipFile, String descDir,String charset) throws IOException {
		ZipFile zip = new ZipFile(zipFile, Charset.forName(charset));// 解决中文文件夹乱码
		String name = zip.getName().substring(zip.getName().lastIndexOf(File.separator) + 1,
				zip.getName().lastIndexOf('.'));
		for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + File.separator + name + File.separator + zipEntryName).replaceAll("\\*", "/");

			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			FileOutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();

		}
		zip.close();
		return;
	}

	public static void main(String[] args) {
		List<File> files = Arrays.asList(new File("/home/lxt/Desktop/0308/file/20190308/20190308130337092_W9HT1G.png"));
		compressFiles2Zip(files,"/home/lxt/Desktop/0308/file/20190308/t.zip");
	}
}