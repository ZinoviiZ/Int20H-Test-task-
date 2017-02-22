package com.risingapp.test.response;

import com.risingapp.test.model.OvvaTvProgram;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OvvaTvProgramResponse extends OvvaResponse {

    private OvvaTvProgram data;
}
