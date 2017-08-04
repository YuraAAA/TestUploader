package hello;

import hello.storage.ResponseData;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class FileUploadController {

    public static final String IMAGE_DIR = "G:\\files";

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public void serveFile(@PathVariable String filename, HttpServletResponse response) throws IOException {
        File[] files = new File(IMAGE_DIR).listFiles((dir, name) -> name.equalsIgnoreCase(filename));
        if (files == null || files.length == 0) throw new FileNotFoundException();
        IOUtils.copy(new FileInputStream(files[0]), response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping("/")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        create(file.getBytes(), file.getOriginalFilename());
        return ResponseEntity.ok().body(ResponseData.createSuccess(file.getOriginalFilename()).toString());
    }

    public String create(byte[] fileContent, String name) throws IOException {
        String status = "Created file...";
        File newFile = new File(IMAGE_DIR, name);
        newFile.createNewFile();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
        stream.write(fileContent);
        stream.close();
        return status;
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleStorageFileNotFound(IOException ex) {
        return ResponseEntity.notFound().build();
    }

}
