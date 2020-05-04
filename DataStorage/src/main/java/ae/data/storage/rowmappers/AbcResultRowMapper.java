package ae.data.storage.rowmappers;

import main.dto.AbcResultDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AbcResultRowMapper implements RowMapper<AbcResultDto> {
    @Override
    public AbcResultDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return AbcResultDto.builder()
                .commodityGroupId(resultSet.getLong("id"))
                .profitResult(resultSet.getString("profit_result"))
                .profitPercent(resultSet.getFloat("profit_percent"))
                .profitResultPercent(resultSet.getFloat("profit_result_percent"))
                .revenueResult(resultSet.getString("revenue_result"))
                .revenuePercent(resultSet.getFloat("revenue_percent"))
                .revenueResultPercent(resultSet.getFloat("revenue_result_percent"))
                .volumeOfSalesResult(resultSet.getString("volume_of_sales_result"))
                .volumeOfSalesPercent(resultSet.getFloat("volume_of_sales_percent"))
                .volumeOfSalesResultPercent(resultSet.getFloat("volume_of_sales_result_percent"))
                .build();
    }
}
