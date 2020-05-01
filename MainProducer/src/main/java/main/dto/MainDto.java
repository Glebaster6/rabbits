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
        LOGIN,PARSE_EXCEL,LOGOUT,ANALYZE_MARKET, ANALYZE_NEURAL, GET_ALL_USERS, REGISTER, SAVE_EVALUATION, SAVE_EVALUATION_DATA
    }

    private Action action;
    private String json;
    private byte[] file;
}
