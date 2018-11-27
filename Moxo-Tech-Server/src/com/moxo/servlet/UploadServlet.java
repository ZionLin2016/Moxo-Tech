package com.moxo.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.moxo.model.UploadList;
import com.moxo.model.UploadMsg;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String temp;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String savePath = this.getServletContext().getRealPath("/upload");
		System.out.println("上传目录：" + savePath);
		// 上传时生成的临时文件保存目录
		String tempPath = this.getServletContext().getRealPath("/temp");
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}

		String message = "";
		ArrayList<UploadMsg> upList = new ArrayList<UploadMsg>();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 100 * 30);
			factory.setRepository(tmpFile);
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 监听文件上传进度
			upload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength, int arg2) {
					System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
				}
			});
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return;
			}

			upload.setFileSizeMax(1024 * 1024 * 30);
			upload.setSizeMax(1024 * 1024 * 300);

			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			if (list != null && list.size() > 0) {
				for (FileItem item : list) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
						System.out.println(name + "=" + value);
					} else {
						String filename = item.getName();
						System.out.println(filename);
						if (filename == null || filename.trim().equals("")) {
							continue;
						}
						// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
						// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
						// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
						filename = filename.substring(filename.lastIndexOf("\\") + 1);
						// 得到上传文件的扩展名
						String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
						// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
						System.out.println("上传的文件的扩展名是：" + fileExtName);
						// 获取item中的上传文件的输入流
						InputStream in = item.getInputStream();
						// 得到文件保存的名称
						String saveFilename = makeFileName() + "." + fileExtName;
						// 得到文件的保存目录
						String realSavePath = makePath(saveFilename, savePath);
						// 创建一个文件输出流 在windows中使用\\ mac中用/
						File storeFile = new File(realSavePath + File.separator + saveFilename);

						item.write(storeFile);

						// 删除处理文件上传时生成的临时文件
						// item.delete();
						message = "File upload success!";

						String route = request.getScheme() + "://" + request.getServerName() + ":"
								+ request.getServerPort() + request.getContextPath() + temp + saveFilename;
						System.out.println("url " + route);

						UploadMsg um = new UploadMsg();
						um.setStatus(true);
						um.setMessage(message);
						um.setResource_address(route);

						upList.add(um);

					}
				}
			}

		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			UploadMsg um = new UploadMsg();
			um.setStatus(false);
			um.setMessage("A single file beyond the maximum");
			um.setResource_address(null);
			upList.add(um);

			e.printStackTrace();
			// request.setAttribute("message", "单个文件超出最大值！！！");
			return;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			UploadMsg um = new UploadMsg();
			um.setStatus(false);
			um.setMessage("The total size of upload files are beyond the limit of maximum");
			um.setResource_address(null);
			upList.add(um);

			e.printStackTrace();
			// request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
			return;
		} catch (Exception e) {
			UploadMsg um = new UploadMsg();
			um.setStatus(false);
			um.setMessage("File upload failed");
			um.setResource_address(null);
			upList.add(um);

			e.printStackTrace();
		}
		UploadList uploadList = new UploadList();
		uploadList.setContent(upList);
		Gson gson = new Gson();
		String test = gson.toJson(uploadList);
		ServletOutputStream out = response.getOutputStream();
		OutputStreamWriter outWriter = new OutputStreamWriter(out, "utf-8");
		BufferedWriter buffered = new BufferedWriter(outWriter);
		buffered.write(test);
		buffered.flush();
		out.close();
	}

	/**
	 * @Method: makeFileName
	 * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 * @param filename
	 *            文件的原始名称
	 * @return uuid+"_"+文件的原始名称
	 */
	private String makeFileName() { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString();
	}

	/**
	 * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 * 
	 * @Method: makePath
	 * @Description:
	 *
	 * @param filename
	 *            文件名，要根据文件名生成存储目录
	 * @param savePath
	 *            文件存储路径
	 * @return 新的存储目录
	 */
	private String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		// int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// 构造新的保存目录 在windows中使用\\ mac中用/
		String dir = savePath + File.separator + dir1 + File.separator; // upload\2\3

		temp = "/upload" + "/" + dir1 + "/";
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}