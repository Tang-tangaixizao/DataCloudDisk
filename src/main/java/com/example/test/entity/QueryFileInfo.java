package com.example.test.entity;

/**存储检索文件信息
 * */
public class QueryFileInfo {
    private String filePath;    //存储文件路径
    private String fileName;    //存储文件名
    private boolean isFile;     //标记是否是文件

    public QueryFileInfo() {
    }

    public QueryFileInfo(String filePath, String fileName, boolean isFile) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.isFile = isFile;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isFile() {
        return isFile;
    }
}
