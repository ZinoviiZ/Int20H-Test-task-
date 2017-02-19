package com.risingapp.test.response;

import com.risingapp.test.model.OvvaTvProgram;
import lombok.Data;

import java.util.List;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Data
public class OvvaTvProgramResponse extends OvvaResponse {

    private OvvaTvProgram data;
}
