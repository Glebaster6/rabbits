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
        PARSE_EXCEL,
        LOGOUT,
        ANALYZE_MARKET,
        ANALYZE_NEURAL,
        GET_ALL_USERS,
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
        RETURN_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD
    }

    private Action action;
    private String json;
    private byte[] file;
    private String user;
}
