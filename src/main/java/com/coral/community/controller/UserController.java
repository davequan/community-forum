package com.coral.community.controller;

import com.coral.community.entity.User;
import com.coral.community.service.UserService;
import com.coral.community.util.CommunityUtil;
import com.coral.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Controller
@RequestMapping("/user")
public class UserController {
    private static  final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${community.path.upload}")
    private String uploadPath;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private  String contextPath;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public  String getSettingPage(){
        return "/site/setting";
    }
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHead(MultipartFile headerImage, Model model){
        if(headerImage == null){
            model.addAttribute("error","you have not select picture");
            return "/site/setting";
        }
        // file name cant be the same , the postfix need to be the same
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));  // get substring from the last .
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","file format is incorrect");
            return "/site/setting";
        }
        //generate random filename
        String fileName = CommunityUtil.generateUUID() + suffix;
        // confirm the file saving directory
        File dest = new File(uploadPath+"/"+fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("upload file fail"+ e.getMessage());
            throw new RuntimeException("upload file fail,server exception",e);
        }
        // update user headerUrl(web http)
        //http://localhost:8080/community/user/Header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(),headerUrl);
        return "redirect:/index";
    }

    // get image
    @RequestMapping(path = "/header/{fileName}" ,method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName , HttpServletResponse response){
        // get header from sever saving directory
        fileName = uploadPath + "/" + fileName;
        // file suffix
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //response the picture(Header)
        response.setContentType("image/"+ suffix);
        try( FileInputStream fis = new FileInputStream(fileName);
             ServletOutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int b =0;
            while((b =fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("read Header fail" + e.getMessage());
        }
    }



}
