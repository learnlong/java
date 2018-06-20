package com.worksyun.api.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.worksyun.api.mapper.CertificationMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Certification;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.api.service.CertificationService;
import com.worksyun.commons.model.LoginModel;
import com.worksyun.commons.util.DateUtil;
import com.worksyun.commons.util.UploadFileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/Certification")
@Api(description = "实名认证相关操作")
public class CertificationController {

	public static final String filesPath = "Test";

	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;

	@Autowired
	private CertificationMapper certificationMapper;

	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private CertificationService certificationService;

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/authentication")
	@ApiOperation(value = "实名认证添加", notes = "实名认证添加操作")
	@ResponseBody
	public ResponseEntity<LoginModel> authentication(HttpServletRequest request) throws IOException {

		Map<String, Object> ParamMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String name = request.getParameter("name");
			String idCard = request.getParameter("idCard");
			String token = request.getParameter("token");

			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
			// 验证用户是否存在
			if (userBaseInfo2.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByTel(paramMap);
			if (!userBaseInfo.isEmpty()) {
				for (int i = 0; i < userBaseInfo.size(); i++) {
					String userId2 = userBaseInfo.get(i).getUserid();
					if (userId.equals(userId2)) {
						Userbaseinfo userbaseinfo = userBaseInfo.get(i);

						String certificationId = userbaseinfo.getCertificationid();
						if (certificationId == null || certificationId.equals("")) {
							// 将certificationId添加到userbaseinfo
							String certificationId2 = UUID.randomUUID().toString().replace("-", "");
							userbaseinfo.setCertificationid(certificationId2);

							// 生成certification数据
							Certification certification = new Certification();
							certification.setCertificationid(certificationId2);
							certification.setCreationuserid(userId);
							certification.setCreationtime(new Date());
							certification.setCreationusername(name);
							certification.setLocalhoststatus(1);
							certification.setTelecomstatus(2);
							certification.setActivationStatus(0);
							certificationService.save(certification);
						} else {
							// 更新certification数据
							Certification certification = certificationMapper.selectByPrimaryKey(certificationId);
							certification.setModifytime2(new Date());
							certification.setModifyuserid(userId);
							certification.setModifyusername(name);
							certification.setActivationStatus(0);
							certificationMapper.updateByPrimaryKey(certification);
						}
						// 插入身份证图片
						MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
						String avater = null;
						String avater2 = null;
						MultipartFile pic = multipartRequest.getFile("pic");
						MultipartFile pic2 = multipartRequest.getFile("pic2");
						avater = UploadFileUtil.createPath2(pic);
						avater2 = UploadFileUtil.createPath2(pic2);
						userbaseinfo.setCardpicture1(avater);// 用户图像
						userbaseinfo.setCardpicture2(avater2);// 用户图像
						userbaseinfo.setName(name);
						userbaseinfo.setIdcard(idCard);
						userbaseinfoMapper.updateByPrimaryKey(userbaseinfo);
					}
				}

			}
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace(), data),
					new HttpHeaders(), HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/authenticationtStatus")
	@ApiOperation(value = "查看实名认证状态", notes = "查看实名认证状态")
	@ResponseBody
	public ResponseEntity<LoginModel> authenticationtStatus(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request)
			throws IOException {
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
			// 验证用户是否存在
			if (userBaseInfo.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			String certificationid = userBaseInfo.get(0).getCertificationid();
			Certification certification = certificationMapper.selectByPrimaryKey(certificationid);
			if (certification == null) {
				data.put("localhoststatus", 0);
			} else {
				data.put("localhoststatus", certification.getLocalhoststatus());
			}
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace(), data),
					new HttpHeaders(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/image", method = RequestMethod.POST)
	@ApiOperation(value = "附件上传（图片）,支持批量", notes = "")
	public String imageUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
			Iterator<String> iterator = req.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile file = req.getFile(iterator.next());
				String fileNames = file.getOriginalFilename();
				int split = fileNames.lastIndexOf(".");
				// 存储文件
				String path = StringUtils.join(UploadFileUtil.ROOT, filesPath, File.separator,
						DateUtil.formatyyyyMMdd(new Date()));
				File uploadDir = new File(path);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				if (!uploadDir.canWrite()) {
					return "上传目录没有写权限！";
				}
				// 文件名 fileNames.substring(0,split)
				String filename = fileNames.substring(0, split);
				// 文件格式 fileNames.substring(split+1,fileNames.length())
				String extName = fileNames.substring(split + 1, fileNames.length());
				String fileType = "gif,png,bmp,jpeg,jpg";

				System.out.println("文件后缀名" + extName);
				// 判断是否为允许上传的文件类型
				if (!Arrays.<String>asList(fileType.split(",")).contains(extName)) {
					// item.delete();
					System.out.println("文件类型不正确，必须为" + fileType + "的文件！");
					return "文件类型不正确，请重新上传！";
				}

				// 文件内容 file.getBytes()
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = filename + df.format(new Date()) + "." + extName;
				File uploadedFile = new File(path, newFileName);
				byte[] logoBytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
				stream.write(logoBytes);
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
}
