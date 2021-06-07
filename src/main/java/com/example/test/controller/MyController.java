package com.example.test.controller;

import com.example.test.entity.FileInfo;
import com.example.test.entity.UserInfo;
import com.example.test.entity.QueryFileInfo;
import com.example.test.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
/**该项目是将HDFS与应用软件开发相结合，
 * 建设一个文件管理系统，
 * 这里HDFS负责具体的文件存放，
 * 应用前台端通过调用对应接口完成对应功能的操作。
 *这里系统应具有的功能有登录、用户管理、目录管理（增删改、检索）
 * 、文件上传下载等。
 * */

/**Controller层
 * 暴露接口给前端
 * */
@Controller
public class MyController {
    /**hdfs文件系统的IP地址
     * */
    private String url="hdfs://192.168.79.136:9000/";
    /*连接Service层
    * */
    @Autowired
    private MyService myService;

    /**用户登录接口
     * */
    @RequestMapping("/login.do")
    @ResponseBody
    public UserInfo UserLogin(String name, String pwd) {
        return myService.UserLogin(name,pwd);
    }

    /**按ID来查询用户信息
     * */
    @RequestMapping("/queryById")
    @ResponseBody
    public UserInfo queryUserById(String id){
        return myService.queryUserById(id);
    }

    /**用户注册
     * */
    @RequestMapping("/register")
    @ResponseBody
    public int UserRegister(String name,
                            String pwd,
                            String sex){
        String id=UUID.randomUUID().toString().replace("-","");
        /**默认管理员身份
         * */
        String userType="管理员";
        if("".equals(name)||"".equals(pwd)||"".equals(sex)){
            return 0;
        }else{
            return myService.UserRegister(id,name,pwd,sex,userType);
        }
    }

    /**查询所有的用户信息
     * */
    @RequestMapping("/queryAll")
    @ResponseBody
    public List<UserInfo> queryAllInfo(){
        return myService.queryAllInfo();
    }

    /**按id来删除用户
     * */
    @RequestMapping("/delUser")
    @ResponseBody
    public int delUer(String id){
        return myService.delUser(id);
    }

    /**修改用户信息
     * */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public int updateUserInfo(String id,String sex,String type){
        return myService.updateUserInfo(id,sex,type);
    }

    /**获取hdfs文件系统信息
     * */
    @RequestMapping("/queryFileAll")
    @ResponseBody
    public List<FileInfo> queryFileAll(String fileName){
        return myService.queryFileAll(url,fileName);
    }
    
    /**删除当前目录的文件
     * */
    @RequestMapping("/delFile")
    @ResponseBody
    public boolean delFile(String delFilePath,String delFile) throws IOException {
        return myService.delFile(url,delFilePath,delFile);
    }

    /**修改文件名即内容
     * */
    @RequestMapping("/changeFileName")
    @ResponseBody
    public boolean changeFileName(String oldName,String newName,String filePath,String fileContent){
       System.out.println("更改文件名:"+oldName+" newName "+newName+" "+filePath);
         myService.changeFileName(url,oldName,newName,filePath,fileContent);
        return true;
    }

    /**下载文件
     * */
    @RequestMapping("/downFile")
    @ResponseBody
    public void downFile(String fileName,String downPath){
            System.out.println("下载文件名"+fileName+" 下载路径:"+downPath);
            myService.downFile(url,fileName,downPath);
    }

    /**上传文件
     * */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public void uploadFile(String locationFilePath,String targetFilePath){
        System.out.println("multiFile "+" "+locationFilePath+" "+targetFilePath);
        myService.uploadFile(url,locationFilePath,targetFilePath);
    }

    /**创建文件夹
     * */
    @RequestMapping("/createFolder")
    @ResponseBody
    public void createFolder(String parentFolder,String createFolder){
        myService.createFolder(url,parentFolder,createFolder);
    }

    /**创建文件
     * */
    @RequestMapping("/createFile")
    @ResponseBody
    public void createFile(String createPath,String createFile,String fileContent){
        myService.createFile(url,createPath,createFile,fileContent);
    }

    /**检索文件
     * */
    @RequestMapping("/selectFile")
    @ResponseBody
    public List<QueryFileInfo> selectFile(String queryFileName) throws Exception {
        return myService.selectFile(url,queryFileName);
    }
}
