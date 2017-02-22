/**
 * Created by zinoviyzubko on 18.02.17.
 */
package com.risingapp.test;

import com.risingapp.test.response.GetTvProgramResponse;
import com.risingapp.test.service.OvvaService;
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

    @Autowired private OvvaService ovvaService;

    @RequestMapping("/")
    public String getIndex() {
        return "index.html";
    }

    @RequestMapping("/get_tv_program")
    public @ResponseBody GetTvProgramResponse getImage() throws URISyntaxException, IOException, FontFormatException {
        return ovvaService.getTvProgram();
    }
}
