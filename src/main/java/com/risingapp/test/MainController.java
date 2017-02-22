/**
 * Created by zinoviyzubko on 18.02.17.
 */
package com.risingapp.test;

import com.risingapp.test.service.OvvaService;
import com.risingapp.test.service.VkUserApiService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class MainController {

    private VkUserApiService vkUserApiService;
    private OvvaService ovvaService;

    @Autowired
    public void setVkUserApiService(VkUserApiService vkUserApiService) {
        this.vkUserApiService = vkUserApiService;
    }

    @Autowired
    public void setOvvaService(OvvaService ovvaService) {
        this.ovvaService = ovvaService;
    }

    @RequestMapping("/")
    public String getIndex() {
        return "index.html";
    }

    @RequestMapping("/get/tv_program")
    public ResponseEntity getImage(HttpServletResponse response) throws URISyntaxException, IOException, FontFormatException {
        return ovvaService.getTvProgram(response);
    }

    @RequestMapping("/savePhoto")
    public ResponseEntity firstStepAuthorize(@RequestParam("code") String code) throws ClientException, ApiException {
        return  vkUserApiService.getImageUrl(code);
    }
}
