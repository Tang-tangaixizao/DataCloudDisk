package com.example.test.entity;
/**文件信息实体类
 * */
public class FileInfo {
   private String filePermission;    //文件权限
   private String creator;           //创建者
   private String fileSize;          //文件大小
   private String createTime;        //创建时间
   private String blockSize;         //块大小
   private String fileName;          //文件名
   private String content;           //文件内容

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileInfo() {
    }

    public void setFilePermission(String filePermission) {
        this.filePermission = filePermission;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setBlockSize(String blockSize) {
        this.blockSize = blockSize;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePermission() {
        return filePermission;
    }

    public String getCreator() {
        return creator;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getBlockSize() {
        return blockSize;
    }

    public String getFileName() {
        return fileName;
    }

    public FileInfo(String filePermission, String creator, String fileSize, String createTime, String blockSize, String fileName,String content) {
        this.filePermission = filePermission;
        this.creator = creator;
        this.fileSize = fileSize;
        this.createTime = createTime;
        this.blockSize = blockSize;
        this.fileName = fileName;
        this.content=content;
    }
}
