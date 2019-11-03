package io.github.frapples.javademoandcookbook.springboot.business.support.controller;

import com.google.common.collect.ImmutableMap;
import io.github.frapples.javademoandcookbook.springbootwebcore.config.exceptionhandler.ExceptionToErrorCode;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/11/3
 */
@Controller
@RequestMapping("/html/")
@ExceptionToErrorCode
public class SupportController {

    @GetMapping(value = "/")
    public ResponseEntity<String> mainHtml() throws URISyntaxException {
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(new URI("/html/support/main.html"))
            .body("");
    }

    @GetMapping(value = "/support/**")
    @ResponseBody
    public ResponseEntity<InputStreamResource> loadStaticFile(HttpServletRequest request) {
        String path = request.getRequestURI().split("/support/")[1];
        InputStream in = getClass().getResourceAsStream("/public/" + path);
        return ResponseEntity
            .ok()
            .contentType(MediaType.valueOf(contentType(path)))
            .body(new InputStreamResource(in));
    }

    private String contentType(String routePath) {
        String extension = FilenameUtils.getExtension(routePath);
        Map<String, String> table = ImmutableMap.<String, String>builder()
            .put("js", "application/javascript; charset=UTF-8")
            .put("css", "text/css; charset=UTF-8")
            .put("html", "text/html; charset=UTF-8")
            .put("htm", "text/html; charset=UTF-8")
            .put("vue", "text/vue; charset=UTF-8")
            .build();
        String defaultType = "text/html; charset=UTF-8";
        return table.getOrDefault(extension, defaultType);
    }

}
