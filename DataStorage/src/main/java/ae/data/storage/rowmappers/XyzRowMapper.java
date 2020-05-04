package ae.data.storage.rowmappers;

import main.dto.XyzResultDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class XyzRowMapper implements RowMapper<XyzResultDto> {

    @Override
    public XyzResultDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return XyzResultDto.builder()
                .id(resultSet.getLong("id"))
                .percent(resultSet.getFloat("percent"))
                .result(resultSet.getString("result"))
                .build();
    }
}
