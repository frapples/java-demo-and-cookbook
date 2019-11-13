package io.github.frapples.javademoandcookbook.springboot.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.sun.javaws.security.AppContextUtil;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/11/13
 */
@Slf4j
@RestController
@RequestMapping("system")
public class SystemController implements ApplicationContextAware {

    @Setter(AccessLevel.PRIVATE)
    private ApplicationContext applicationContext;


    @PostMapping("/json-rpc/{service}")
    public String jsonRpc(@PathVariable("service") String serviceName, @RequestBody String bodyString) throws IOException {
        Object bean = applicationContext.getBean(serviceName);
        JsonRpcServer jsonRpcServer = new JsonRpcServer(new ObjectMapper(), bean, bean.getClass());
        try (ByteArrayOutputStream w = new ByteArrayOutputStream()) {
            jsonRpcServer.handleRequest(IOUtils.toInputStream(bodyString, Charsets.UTF_8), w);
            return w.toString(Charsets.UTF_8.name());
        }
    }

}
