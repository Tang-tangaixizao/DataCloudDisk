package com.example.test.service;

import com.example.test.dao.MyDao;
import com.example.test.entity.FileInfo;
import com.example.test.entity.UserInfo;
import com.example.test.entity.QueryFileInfo;
import com.example.test.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**实现Service层的接口写具体的业务逻辑
 * */
@Service
public class MyServiceImpl implements MyService{
    @Autowired
    private MyDao myDao;

    @Override
    public UserInfo UserLogin(String username,String userpwd) {
        return myDao.UserLogin(username,userpwd);
    }

    @Override
    public int UserRegister(String id, String username,
                            String userpwd,String sex,String userType) {
        return myDao.UserRegister(id,username,userpwd,sex,userType);
    }

    @Override
    public List<UserInfo> queryAllInfo() {
        return myDao.queryAllInfo();
    }

    @Override
    public int delUser(String id) {
        return myDao.delUser(id);
    }

    @Override
    public int updateUserInfo(String id,String sex, String type) {
        return myDao.updateUserInfo(id,sex,type);
    }

    @Override
    public List<FileInfo> queryFileAll(String url,String fileName) {
        /**使用工具类来获取hdfs文件系统的信息
         * */
        MyUtil.setUrl(url);
        return MyUtil.getFileInfo(fileName);
    }

    @Override
    public boolean delFile(String url,String delFilePath,String delFile) throws IOException {
        MyUtil.setUrl(url);
        return MyUtil.delCurrentFile(delFilePath+delFile);
    }

    @Override
    public boolean changeFileName(String url,String oldName, String newName,String filePath,String fileContent) {
        MyUtil.setUrl(url);
        boolean isOk=false;
        try {
            isOk= MyUtil.updateFileName(filePath+oldName,filePath+newName);
            if(!("".equals(fileContent))){
                MyUtil.changeFileContent(filePath+oldName,filePath+newName,fileContent);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    @Override
    public void downFile(String url, String fileName, String downPath) {
        MyUtil.setUrl(url);
        MyUtil.downloadFile(fileName,downPath);
    }

    @Override
    public void uploadFile(String url,String locationFile, String targetFile) {
        MyUtil.setUrl(url);
        String str=locationFile.substring(locationFile.lastIndexOf("\\")+1);
        if("".equals(targetFile)){
            MyUtil.uploadFile(locationFile,"/temporary/"+str);
        }else{
            MyUtil.uploadFile(locationFile,targetFile+"/"+str);
        }
    }

    @Override
    public void createFolder(String url,String parentFolder,String createFolder) {
        MyUtil.setUrl(url);
        MyUtil.createFolder("/"+parentFolder+createFolder);
    }

    @Override
    public void createFile(String url, String createPath, String createFile,String fileContent) {
            MyUtil.setUrl(url);
            MyUtil.createFile(createPath,createFile,fileContent);
    }

    @Override
    public List<QueryFileInfo> selectFile(String url, String queryFile)throws Exception {
        List<QueryFileInfo> allFileInfoList;
        List<QueryFileInfo> list=new ArrayList<>();
        MyUtil.setUrl(url);
        allFileInfoList=MyUtil.printDir("/");
        for (QueryFileInfo queryFileInfo:allFileInfoList){
            if(queryFileInfo.getFileName().contains(queryFile)){
                list.add(queryFileInfo);
            }
        }
        /**去除递归结果中文件路径和文件名相同的
         * */
        for(int i = 0;i<list.size()-1;i++) {
            for(int j = list.size()-1;j>i;j--) {
                if(list.get(j).getFileName().equals(list.get(i).getFileName())&&
                list.get(j).getFilePath().equals(list.get(i).getFilePath())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    @Override
    public UserInfo queryUserById(String id) {
        return myDao.queryUserById(id);
    }

}
