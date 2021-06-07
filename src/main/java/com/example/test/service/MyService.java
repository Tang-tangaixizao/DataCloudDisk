package com.example.test.service;

import com.example.test.entity.FileInfo;
import com.example.test.entity.UserInfo;
import com.example.test.entity.QueryFileInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**Service层用来处理业务逻辑
 * */
@Service
public interface MyService {
    UserInfo UserLogin(String username,String userpwd);
    int UserRegister(String id,String username,
                     String userpwd,String sex,String userType);
    List<UserInfo> queryAllInfo();
    int delUser(String id);
    int updateUserInfo(String id,String sex,String type);
    List<FileInfo> queryFileAll(String url,String fileName);
    boolean delFile(String url,String delFilePath,String delFile) throws IOException;
    boolean changeFileName(String url,String oldName, String newName,String filePath,String fileContent);
    void downFile(String url,String fileName,String downPath);
    void uploadFile(String url,String locationFile,String targetFile);
    void createFolder(String url,String parentFolder,String createFolder);
    void createFile(String url ,String createPath,String createFile,String fileContent);
    List<QueryFileInfo> selectFile(String url, String queryFile) throws Exception;
    UserInfo queryUserById(String id);
}
