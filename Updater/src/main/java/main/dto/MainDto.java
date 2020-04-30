package main.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MainDto implements Serializable {
    public enum Action {
        LOGIN,PARSE_EXCEL,LOGOUT,ANALYZE_MARKET, ANALYZE_NEURAL, GET_ALL_USERS, REGISTER, SAVE_EVALUATION, SAVE_EVALUATION_DATA
    }

    private Action action;
    private String json;
    private byte[] file;
}
