package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainDto implements Serializable {
    public enum Action {
        LOGIN,
        LOGOUT,
        REGISTER,
        SAVE_EVALUATION,
        DELETE_EVALUATION,
        SAVE_EVALUATION_DATA,
        GET_FACILITY_DATA,
        RETURN_FACILITY_DATA,
        GET_EVALUATION_DATA,
        RETURN_EVALUATION_DATA,
        GET_COMMODITY_GROUP_RESULT,
        RETURN_COMMODITY_GROUP_RESULT,
        GET_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD,
        RETURN_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD,
        TRAIN_MODEL,
        SAVE_TRAINED_MODEL,
        SAVE_DATASET,
        GET_MODEL,
        MAKE_PREDICTION,
        GET_PREDICTION,
        RETURN_PREDICTION,
        PARSE_EXCEL
    }

    private Action action;
    private String json;
    private byte[] file;
    private String user;
}


