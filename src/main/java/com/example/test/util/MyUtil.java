package com.example.test.util;

import com.example.test.entity.FileInfo;
import com.example.test.entity.QueryFileInfo;
import com.google.common.io.CharStreams;
import org.apache.commons.io.Charsets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**工具类
 * */
public class MyUtil {
    /**hdfs://IP地址:9000/
     * 默认获取根目录的所有文件
     * */
    private static String url;
    /**使用list来存储文件信息
     * */
    private static List<FileInfo> fileInfoList;
    /**存储所有文件信息
     * */
    private static List<QueryFileInfo> allFileInfoList=new ArrayList<>();
    private static Configuration conf=new Configuration();
    public static void setUrl(String url){
        MyUtil.url =url;
    }
    public static List<QueryFileInfo>getAllFileInfoList(){
        return allFileInfoList;
    }
    /**获取当前目录下文件的所有信息
     */
    public static List<FileInfo> getFileInfo(String fileName){
        fileInfoList=new ArrayList<>();
        try{
            FileSystem fs=FileSystem.get(URI.create(url),conf);
            /**根据指定的文件夹来列出文件信息
             * */
            Path path=new Path("/"+fileName);
            FileStatus stats[]=fs.listStatus(path);
            /**遍历读取到的文件信息
             * */
            for(int i=0;i<stats.length;i++){
                FileInfo fileInfo=new FileInfo();
                /**判断是否是文件
                 * */
                if(stats[i].isFile()){
                    InputStream in = fs.open(new Path(path+"/"+stats[i].getPath().getName()));
                    String result = CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
                    fileInfo.setContent(result);
                }
                    /**获取文件权限
                     * */
                    fileInfo.setFilePermission(stats[i].getPermission().toString());
                    /**获取创建者的信息
                     * */
                    fileInfo.setCreator(stats[i].getOwner());
                    /**获取文件大小
                     * */
                    fileInfo.setFileSize(String.valueOf(stats[i].getLen()));
                    /**获取创建文件的时间
                     * */
                    Date dateString =new Date(stats[i].getModificationTime());
                    //引入格式话日期 创建日期格式化对象
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
                    //开始格式化日期
                    String strTime=sdf.format(dateString);
                    fileInfo.setCreateTime(strTime);
                    /**获取块大小
                     * */
                    fileInfo.setBlockSize(String.valueOf(stats[i].getBlockSize()));
                    /**获取文件名
                     * */
                    fileInfo.setFileName(stats[i].getPath().getName());
                /**添加到list中
                 * */
                fileInfoList.add(fileInfo);
            }
            fs.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfoList;
    }

    /**删除当前目录的指定文件
     * */
    public static boolean delCurrentFile(String delFile) throws IOException {
        boolean isDeleted=false;
        FileSystem fs=null;
        try{
            fs = FileSystem.get(URI.create(url),conf);

            Path delef=new Path("/"+delFile);
            isDeleted=fs.delete(delef,true);
            System.out.println(isDeleted);
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            fs.close();
        }
        return isDeleted;
    }

    /**更改文件名
     * */
    public static boolean updateFileName(String oldName,String newName) throws URISyntaxException, IOException, InterruptedException {
        /**获取文件系统
         * */
        FileSystem fs = FileSystem.get(new URI(url), conf, "root");
        //2 文件名、文件夹名更改
        boolean str= fs.rename(new Path("/"+oldName),new Path("/"+newName));
        //3 关闭资源
        fs.close();
        return str;
    }

    /**修改文件内容
     * */
    public static void changeFileContent(String oldFileName,String fileName,String fileContent){
        try {
            FileSystem fs = FileSystem.get(URI.create(url+"/"+fileName),conf);
            Path delef=new Path("/"+oldFileName);
            fs.delete(delef,true);
            OutputStream out=fs.create(new Path(url+"/"+fileName));
            byte[] b=fileContent.getBytes();
            out.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**下载指定文件
     * */
    public static void downloadFile(String fileName,String downPath){
        Configuration configuration = new Configuration();
        try {
            FileSystem fs = FileSystem.get(new URI(url), configuration, "root");
            fs.copyToLocalFile(false,new Path(url+"/"+downPath+fileName),new Path("E:/"+fileName),true);
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**上传文件
     * */
    public static void uploadFile(String locationFile,String targetFile){
        try {
            FileSystem fileSystem = FileSystem.get(new URI(url), conf);
            FileInputStream in = new FileInputStream(new File(locationFile));//获取本地文件
            FSDataOutputStream out = fileSystem.create(new Path(targetFile)); //选择上传路径
            IOUtils.copyBytes(in,out,conf);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**创建文件夹
     * */
    public static void createFolder(String createFolderName){
        try{
            FileSystem fs=FileSystem.newInstance(URI.create(url),conf);
            Path dfs=new Path(url+createFolderName);
            fs.mkdirs(dfs);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**创建文件
     * */
    public static void createFile(String createPath,String createFile,String fileContent){
        try {
            FileSystem fs = FileSystem.get(URI.create(url+"/"+createPath+createFile),conf);
            OutputStream out=fs.create(new Path(url+"/"+createPath+createFile));
            byte[] b=fileContent.getBytes();
            out.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**递归遍历所有文件模块
     * */
    public static List<QueryFileInfo> printDir(String dir) throws Exception {
        QueryFileInfo queryFileInfo=null;
        FileSystem fs = getFileSystem();
        FileStatus[] globStatus = fs.listStatus(new Path(dir));
        Path[] stat2Paths = FileUtil.stat2Paths(globStatus);
        for (int i = 0; i < stat2Paths.length; i++) {
            queryFileInfo=new QueryFileInfo();
            /**遍历所有目录的信息
             * */
            String allFile=stat2Paths[i].toString().substring(stat2Paths[i].toString().indexOf(":9000")+5);
            /**获取最后一个文件信息
             * */
            if(fs.isDirectory(stat2Paths[i])) {
                String fileName=allFile.substring(allFile.lastIndexOf("/")+1);
                queryFileInfo.setFilePath(allFile);
                queryFileInfo.setFileName(fileName);
                queryFileInfo.setFile(false);
            }else {
                String filePath=allFile.substring(allFile.indexOf("/"),allFile.lastIndexOf("/"));
                String fileName=allFile.substring(allFile.lastIndexOf("/")+1);
                queryFileInfo.setFilePath(filePath);
                queryFileInfo.setFileName(fileName);
                queryFileInfo.setFile(true);
            }
            //判断是否为文件夹
            if (fs.isDirectory(stat2Paths[i])) {
                //递归调用
                printDir(stat2Paths[i].toString());
            }
            allFileInfoList.add(queryFileInfo);
        }
        return allFileInfoList;
    }
    public static FileSystem getFileSystem() throws Exception {
        URI uri = new URI(url);
        FileSystem fs = FileSystem.get(uri, conf);
        return fs;
    }
}
