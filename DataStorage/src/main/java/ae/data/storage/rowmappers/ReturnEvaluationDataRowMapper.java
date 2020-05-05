package ae.data.storage.rowmappers;

import main.dto.EvaluationDataRowDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnEvaluationDataRowMapper implements RowMapper<EvaluationDataRowDto> {
    @Override
    public EvaluationDataRowDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return EvaluationDataRowDto.builder()
                .id(resultSet.getLong("id"))
                .evaluationId(resultSet.getLong("commodity_group_id"))
                .name(resultSet.getString("name"))
                .revenue(resultSet.getDouble("revenue"))
                .profit(resultSet.getDouble("profit"))
                .consumption(resultSet.getDouble("consumption"))
                .volumeOfSales(resultSet.getInt("volume_of_sales"))
                .build();
    }
}
