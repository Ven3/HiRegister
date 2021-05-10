package bai.ctrl;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
public class FileCtrl {

    /**
     * 文件上传
     * @param imgfile
     * @param model
     * @return
     */
    @RequestMapping("/fileupload")
    public boolean fileUpload(MultipartFile imgfile, Model model){
        if(imgfile.isEmpty()){
           return  false;
        }
        String fName = imgfile.getOriginalFilename();
        int size = (int) imgfile.getSize();
        System.err.println(fName + "---" + size);
        String dest = "D:/Workspace/ImgServer/"+ UUID.randomUUID().toString().replaceAll("-","")+".jpg";
        System.err.println(dest);
        File destFile = new File(dest);
        try {
            imgfile.transferTo(destFile);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 文件下载
     * @param response
     * @return
     */
    @RequestMapping("/download/{path}/**")
    public void downLoad(HttpServletResponse response, HttpServletRequest request, @PathVariable String path){
        String fileName = this.getClass().getResource("/").getPath() + request.getRequestURI().replace("/download","");
        File file = new File(fileName);

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        response.setBufferSize(buff.length);
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File( fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (ClientAbortException e){
            System.out.println("Broweser Control !!");
        }catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
