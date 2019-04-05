package io.github.frapples.javademoandcookbook.springboot.business.filedemo;

import io.github.frapples.javademoandcookbook.springboot.common.base.vo.ResponseVo;
import io.github.frapples.javademoandcookbook.springboot.web.resolver.UserId;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/filedemo")
public class FileDemoController {

    /**
     * 1. 演示了如何进行文件上传
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo upload(@RequestParam("file") MultipartFile file,
        @RequestParam("helloId") String helloId) {
        if (file.isEmpty()) {
            return ResponseVo.ofSystemError();
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
            return ResponseVo.ofSuccess(map);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVo.ofSystemError();
        }
    }

    /**
    * 1. 演示了如何文件下载。 参考：https://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
    * 2. 返回值类型也可以使用byte[]，但是很显然，文件太大时，这种方式需要把文件的所有内容载入内存
    */
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    @ResponseBody
    public Resource download(HttpServletResponse response) {
        String filename = "test.txt";
        String s = "This is a file";

        ByteArrayResource resource = new ByteArrayResource(s.getBytes());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setHeader("Content-Disposition", "attachment" + filename);
        return resource;
    }

    /**
     * 1. 演示了使用自定义参数解析器@UserId
     */
    @RequestMapping(value = "/arg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo customerArgResolver(@UserId String userId) {
        return ResponseVo.ofSuccess(userId);
    }
}
