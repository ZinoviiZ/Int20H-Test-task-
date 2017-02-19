package com.risingapp.test.service;

import com.risingapp.test.cache.CacheManager;
import com.risingapp.test.connector.OvvaConnector;
import com.risingapp.test.creator.OvvaQueryCreator;
import com.risingapp.test.enums.OvvaAction;
import com.risingapp.test.enums.OvvaChannel;
import com.risingapp.test.enums.OvvaLanguage;
import com.risingapp.test.response.OvvaTvProgramResponse;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Service
public class OvvaService {

    @Autowired private OvvaConnector ovvaConnector;
    @Autowired private CacheManager cacheManager;

    @Autowired private OvvaQueryCreator queryCreator;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ResponseEntity getTvProgram(HttpServletResponse httpServletResponse) throws URISyntaxException {

        String query = queryCreator.createQuery(OvvaAction.GET_TV_PROGRAM, OvvaLanguage.UA, OvvaChannel.CHANNEL_1PLUS1.getValue());
        OvvaTvProgramResponse ovvaTvProgramResponse = ovvaConnector.send(query, OvvaTvProgramResponse.class);

        File file = new File("/Users/zinoviyzubko/Desktop/Developing/RisingTest/src/main/webapp/test.png");

        cacheManager.addTvProgram(sdf.format(new Date()), OvvaChannel.CHANNEL_1PLUS1.getValue(), file);

        try(FileInputStream inputStream = new FileInputStream(file)) {

        byte[] bytes = IOUtils.toByteArray(inputStream);
            httpServletResponse.setContentType("image/png");
            httpServletResponse.getOutputStream().write(bytes);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }
}
