package io.github.frapples.springbootcookbook.web.controller;

import io.github.frapples.springbootcookbook.biz.ResponseDTO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
}
