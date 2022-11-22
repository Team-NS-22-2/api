package com.mju.insuranceCompany.global.utility;

import com.mju.insuranceCompany.global.constant.CommonConstants;
import com.mju.insuranceCompany.global.constant.DocUtilConstants;
import com.mju.insuranceCompany.global.constant.ExceptionConstants;
import com.mju.insuranceCompany.service.accident.domain.Accident;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDialogUtil {

    private static FileInputStream in;
    private static FileOutputStream out;
    private static Frame frame;
    private static FileDialog dialog;
    private static String serverPath = "fileServer/";

    private static final String submitPath = DocUtilConstants.SUBMIT_PATH;

    public static String upload(String dirInsurance) throws IOException {
        frame = new Frame();
        frame.setUndecorated(true); // 서순 중요!!! 제일 위에 배치해야 함.
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.pack();
        dialog = new FileDialog(frame, DocUtilConstants.FILE_UPLOAD_HEAD, FileDialog.LOAD);
        dialog.setVisible(true);
        String fileName = dialog.getFile();
        String originPath = dialog.getDirectory()+fileName;

        String saveDirectory = serverPath + dirInsurance;
        Files.createDirectories(Paths.get(saveDirectory));
        String savePath = saveDirectory + "/" + fileName;

        if(dialog.getDirectory() == null) {
            frame.dispose();
            return null;
        }

        try {
            in = new FileInputStream(originPath);
            out = new FileOutputStream(savePath);
            readIOBuffer();
        } catch (FileNotFoundException e) {
            throw new MyFileNotFoundException();
        }
        return fileName;
    }

    public static String uploadAccidentDocumentFile(String dir) {

        try {
            File folder = new File(dir);
            if (!folder.getParentFile().exists()) {
                folder.getParentFile().mkdirs();
            }


            frame = new JFrame();
            frame.setUndecorated(true);
            frame.pack();
            frame.setVisible(true);

            dialog = new FileDialog(frame, DocUtilConstants.FILE_UPLOAD_HEAD, FileDialog.LOAD);

            dialog.setFile(getExtension(dir));
            dialog.setAlwaysOnTop(true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.setModal(true);


            String originPath = getFullPath(dialog.getDirectory(), dialog.getFile());
            if(dialog.getDirectory() == null)
                return null;
            in = new FileInputStream(originPath);
            out = new FileOutputStream(dir);
            readIOBuffer();
        } catch (FileNotFoundException e) {
            throw new MyFileNotFoundException(ExceptionConstants.FILE_NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dir;
    }

    private static String getFullPath(String directory, String fileName) {
        return directory + fileName;
    }

    public static void download(String dir) {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);

        dialog = new FileDialog(frame, DocUtilConstants.FILE_DOWNLOAD_HEAD,FileDialog.SAVE);
        dialog.setFile(getFileName(dir));
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        if(dialog.getDirectory() == null)
            return;
        String saveFilePath = getFullPath(dialog.getDirectory(), dialog.getFile());
        try {
            in = new FileInputStream(dir);
            out = new FileOutputStream(saveFilePath);
            readIOBuffer();
        } catch (FileNotFoundException e) {
            throw new MyFileNotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void isExist(Accident accident, AccDocType accDocType) {
        String directory = getDirectory(accident);
        String extension = DocUtilConstants.HWP_EXTENSION;
        if(accDocType==AccDocType.PICTUREOFSITE)
            extension = DocUtilConstants.JPG_EXTENSION;
        File folder = new File(submitPath+directory+ CommonConstants.SLASH +accDocType.getDesc()+extension);
        try {
            if (!folder.exists()) {
                throw new MyFileNotFoundException(ExceptionConstants.getFileNotFoundMessage(accDocType.getDesc()));
            }
        } catch (MyFileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getDirectory(Accident accident) {
        return accident.getCustomerId() + CommonConstants.SLASH + accident.getId();
    }

    public static void deleteDir(Accident accident) {
        File file = new File(submitPath+getDirectory(accident));

        try {
            if (file.exists()) { //파일존재여부확인
                if (file.isDirectory()) { //파일이 디렉토리인지 확인
                    File[] files = file.listFiles();
                    for (File value : files) value.delete();
                }
            } else {
                throw new MyFileNotFoundException(ExceptionConstants.FILE_NOT_FOUND);
            }
        } catch (MyFileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readIOBuffer() throws IOException {
        try {
            int read, bytebuffer=0;
            byte[] buffer = new byte[8192];
            while((read=in.read(buffer)) != -1){
                out.write(buffer, 0 ,read);
                bytebuffer += read;
                if(bytebuffer > 1024*1024){
                    bytebuffer = 0;
                    out.flush();
                }
            }
        } finally {
            out.close();
            in.close();
            frame.dispose();
        }
    }
    private static String getExtension(String path) {
        int lastIndexOf = path.lastIndexOf(DocUtilConstants.DOT);
        return DocUtilConstants.ASTERISK+path.substring(lastIndexOf);
    }

    private static String getFileName(String dir) {
        return  dir.substring(dir.lastIndexOf(CommonConstants.SLASH) + 1);
    }
}
