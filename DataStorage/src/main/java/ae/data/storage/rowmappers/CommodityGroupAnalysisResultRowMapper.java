package ae.data.storage.rowmappers;

import main.dto.CommodityGroupResultDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommodityGroupAnalysisResultRowMapper implements RowMapper<CommodityGroupResultDto> {
    @Override
    public CommodityGroupResultDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return CommodityGroupResultDto.builder()
                .id(resultSet.getLong("cg_id"))
                .name(resultSet.getString("name"))
                .periodCount(resultSet.getInt("count_periods"))
                .period(resultSet.getString("period"))
                .startYear(resultSet.getString("year"))
                .startMonth(resultSet.getString("month"))
                .startDay(resultSet.getString("day"))
                .profitChart(resultSet.getString("profit_graph"))
                .revenueChart(resultSet.getString("revenue_graph"))
                .consumptionChart(resultSet.getString("consumption_graph"))
                .volumeOfSalesChart(resultSet.getString("volume_of_sales_graph"))
                .profitPercentChart(resultSet.getString("profit_percent_graph"))
                .revenuePercentChart(resultSet.getString("revenue_percent_graph"))
                .volumeOfSalesPercentChart(resultSet.getString("volume_of_sales_percent"))
                .profitResultPercentChart(resultSet.getString("profit_result_percent_graph"))
                .revenueResultPercentChart(resultSet.getString("revenue_result_percent_graph"))
                .volumeOfSalesResultPercentChart(resultSet.getString("volume_of_sales_result_percent"))
                .profitResults(resultSet.getString("profit_results"))
                .revenueResults(resultSet.getString("revenue_results"))
                .volumeOfSalesResults(resultSet.getString("volume_of_sales_result"))
                .abcProfitResult(resultSet.getString("abc_profit_result"))
                .abcRevenueResult(resultSet.getString("abc_revenue_result"))
                .abcVolumeOfSalesResult(resultSet.getString("abc_volume_of_sales_result"))
                .xyzProfitResult(resultSet.getString("xyz_profit_result"))
                .xyzRevenueResult(resultSet.getString("xyz_revenue_result"))
                .xyzVolumeOfSalesResult(resultSet.getString("xyz_volume_of_sales_result"))
                .build();
    }
}
