package com.risingapp.test.creator;

import com.risingapp.test.enums.OvvaAction;
import com.risingapp.test.enums.OvvaLanguage;
import org.springframework.stereotype.Component;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Component
public class OvvaQueryCreator {

    public String createQuery(OvvaAction ovvaAction, OvvaLanguage language, String... parameters) {

        String url = ovvaAction.getUrl();
        url = url.replace(":lang", language.getValue());

        for (int i = 0; i < parameters.length; i++) {
            url += parameters[i];
            if (i != parameters.length - 1) url += "/";
        }

        return url;
    }
}
