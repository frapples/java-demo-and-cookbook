package io.github.frapples.springbootcookbook.web.controller;

import io.github.frapples.springbootcookbook.biz.ResponseDTO;
import io.github.frapples.springbootcookbook.web.resolver.UserId;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> hello() {
        Map map = new HashMap();
        map.put("a", "1");
        map.put("b", "2");
        return map;
    }

    /*
     * 1. 演示了如何进行文件上传
     * */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO upload(@RequestParam("file") MultipartFile file,
        @RequestParam("helloId") String helloId) {
        if (file.isEmpty()) {
            return ResponseDTO.ofFailed();
        }

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));

        try {
            File tempFile = File.createTempFile("tmp", null);
            file.transferTo(tempFile);
            tempFile.deleteOnExit();
            // 其它操作

            Map<String, Object> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("ext", ext);
            return ResponseDTO.ofSuccess(map);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseDTO.ofFailed();
        }
    }

    /*
    * 1. 演示了如何文件下载。 参考：https://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
    * 2. 返回值类型也可以使用byte[]，但是很显然，文件太大时，这种方式需要把文件的所有内容载入内存
    * */
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    @ResponseBody
    public Resource download(HttpServletResponse response) {
        String filename = "test.txt";
        String s = "This is a file";

        ByteArrayResource resource = new ByteArrayResource(s.getBytes());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        return resource;
    }

    /*
     * 1. 演示了使用自定义参数解析器@UserId
     * */
    @RequestMapping(value = "/arg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO customerArgResolver(@UserId String userId) {
        return ResponseDTO.ofSuccess(userId);
    }
}
