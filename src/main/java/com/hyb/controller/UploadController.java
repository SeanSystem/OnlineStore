package com.hyb.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.hyb.utils.JsonUtils;
import com.hyb.utils.UUIDUtils;

/**
 * 处理文件上传请求
 * @author Sean
 *
 */
@Controller
public class UploadController {

	@Value("${PRODUCT_IMAGE_PATH}")
	private String PRODUCT_IMAGE_PATH;
	
	@Value("${PRODUCT_IMAGE_MAPPING_PATH}")
	private String PRODUCT_IMAGE_MAPPING_PATH;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String upload(MultipartFile uploadFile){
		
		try {
			
			//获取文件名
			String filename = uploadFile.getOriginalFilename();
			//为防止文件重名，为上传问文件生成一个随机文件名
			filename = UUIDUtils.getCode()+filename.substring(filename.lastIndexOf("."));
			//上传文件
			File file = new File(PRODUCT_IMAGE_PATH+filename);
			if(!file.exists()){
				file.mkdirs();
			}
			
				//创建返回结果数据集
				Map<String,Object> map = new HashMap<String, Object>();
				
				//如果文件上传成功
				uploadFile.transferTo(file);
				map.put("error", 0);
				map.put("url",PRODUCT_IMAGE_MAPPING_PATH+"/"+filename);
				//为防止出现浏览器兼容性，将返回结果转成json形式响应
				return JsonUtils.objectToJson(map);
				
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", 1);
			map.put("message","图片上传失败！");
			return JsonUtils.objectToJson(map);
		}	
		
	}
}
