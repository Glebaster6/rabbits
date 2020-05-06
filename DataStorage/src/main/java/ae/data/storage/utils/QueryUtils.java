package ae.data.storage.utils;

public class QueryUtils {
    private final static String getAbcResultsByEvaluationIdAndPeriodQuery = "SELECT p.id,\n" +
            "                   CASE\n" +
            "                       WHEN p.result_percent > 0 AND p.result_percent <= 0.80 THEN 'A'\n" +
            "                       WHEN p.result_percent > 0.80 AND p.result_percent <= 0.95 THEN 'B'\n" +
            "                       WHEN p.result_percent > 0.95 AND p.result_percent <= 1 THEN 'C'\n" +
            "                       END as profit_result,\n" +
            "                   p.percent as profit_percent,\n" +
            "                   p.result_percent as profit_result_percent,\n" +
            "                   revenue.revenue_result,\n" +
            "                   revenue.revenue_percent,\n" +
            "                   revenue.revenue_result_percent,\n" +
            "                   consumption.consumption_result,\n" +
            "                   consumption.consumption_percent,\n" +
            "                   consumption.consumption_result_percent,\n" +
            "                   volume_of_sales.volume_of_sales_result,\n" +
            "                   volume_of_sales.volume_of_sales_percent,\n" +
            "                   volume_of_sales.volume_of_sales_result_percent\n" +
            "            FROM (\n" +
            "                     SELECT data.id,\n" +
            "                            data.percent,\n" +
            "                            sum(percent)\n" +
            "                            OVER (ORDER BY percent DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as result_percent\n" +
            "                     FROM (\n" +
            "                              SELECT cg.id,\n" +
            "                                     (cg.revenue - cg.consumption) / (\n" +
            "                                         SELECT sum(cgs.revenue - cgs.consumption)\n" +
            "                                         FROM evaluation_data cgs\n" +
            "                                         WHERE cgs.period = :period\n" +
            "                                         LIMIT 1\n" +
            "                                     ) as percent\n" +
            "                              FROM evaluation_data cg\n" +
            "                              WHERE cg.period = :period AND cg.evaluation_id = :evaluation_id\n" +
            "                              ORDER BY cg.revenue - cg.consumption DESC\n" +
            "                          ) data\n" +
            "                 ) AS p\n" +
            "                     FULL OUTER JOIN (\n" +
            "                SELECT r.id,\n" +
            "                       CASE\n" +
            "                           WHEN r.result_percent > 0.0 AND r.result_percent <= 0.80 THEN 'A'\n" +
            "                           WHEN r.result_percent > 0.80 AND r.result_percent <= 0.95 THEN 'B'\n" +
            "                           WHEN r.result_percent > 0.95 AND r.result_percent <= 1 THEN 'C'\n" +
            "                           END as revenue_result,\n" +
            "                       r.percent as revenue_percent,\n" +
            "                       r.result_percent as revenue_result_percent\n" +
            "                FROM (\n" +
            "                         SELECT data.id,\n" +
            "                                data.percent,\n" +
            "                                sum(percent)\n" +
            "                                OVER (ORDER BY percent DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as result_percent\n" +
            "                         FROM (\n" +
            "                                  SELECT cg.id,\n" +
            "                                         cg.revenue / (\n" +
            "                                             SELECT sum(cgs.revenue)\n" +
            "                                             FROM evaluation_data cgs\n" +
            "                                             WHERE cgs.period = :period\n" +
            "                                             LIMIT 1\n" +
            "                                         ) as percent\n" +
            "                                  FROM evaluation_data cg\n" +
            "                                  WHERE cg.period = :period AND cg.evaluation_id = :evaluation_id\n" +
            "                                  ORDER BY revenue DESC\n" +
            "                              ) data\n" +
            "                     ) AS r\n" +
            "            ) revenue ON p.id = revenue.id\n" +
            "                     FULL OUTER JOIN (\n" +
            "                SELECT c.id,\n" +
            "                       CASE\n" +
            "                           WHEN c.result_percent > 0 AND c.result_percent <= 0.80 THEN 'A'\n" +
            "                           WHEN c.result_percent > 0.80 AND c.result_percent <= 0.95 THEN 'B'\n" +
            "                           WHEN c.result_percent > 0.95 AND c.result_percent <= 1 THEN 'C'\n" +
            "                           END as consumption_result,\n" +
            "                       c.percent as consumption_percent,\n" +
            "                       c.result_percent as consumption_result_percent\n" +
            "                FROM (\n" +
            "                         SELECT data.id,\n" +
            "                                data.percent,\n" +
            "                                sum(percent)\n" +
            "                                OVER (ORDER BY percent DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as result_percent\n" +
            "                         FROM (\n" +
            "                                  SELECT cg.id,\n" +
            "                                         cg.consumption / (\n" +
            "                                             SELECT sum(cgs.consumption)\n" +
            "                                             FROM evaluation_data cgs\n" +
            "                                             WHERE cgs.period = :period\n" +
            "                                             LIMIT 1\n" +
            "                                         ) as percent\n" +
            "                                  FROM evaluation_data cg\n" +
            "                                  WHERE cg.period = :period AND cg.evaluation_id = :evaluation_id\n" +
            "                                  ORDER BY consumption DESC\n" +
            "                              ) data\n" +
            "                     ) AS c\n" +
            "            ) consumption ON p.id = consumption.id\n" +
            "                     FULL OUTER JOIN (\n" +
            "                SELECT v.id,\n" +
            "                       CASE\n" +
            "                           WHEN v.result_percent > 0 AND v.result_percent <= 0.80 THEN 'A'\n" +
            "                           WHEN v.result_percent > 0.80 AND v.result_percent <= 0.95 THEN 'B'\n" +
            "                           WHEN v.result_percent > 0.95 AND v.result_percent <= 1 THEN 'C'\n" +
            "                           END as volume_of_sales_result,\n" +
            "                       v.percent as volume_of_sales_percent,\n" +
            "                       v.result_percent as volume_of_sales_result_percent\n" +
            "                FROM (\n" +
            "                         SELECT data.id,\n" +
            "                                data.percent,\n" +
            "                                sum(percent)\n" +
            "                                OVER (ORDER BY percent DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) as result_percent\n" +
            "                         FROM (\n" +
            "                                  SELECT cg.id,\n" +
            "                                         volume_of_sales::float / (\n" +
            "                                             SELECT sum(cgs.volume_of_sales)::float\n" +
            "                                             FROM evaluation_data cgs\n" +
            "                                             WHERE cgs.period = :period AND cg.evaluation_id = :evaluation_id\n" +
            "                                             LIMIT 1\n" +
            "                                         ) as percent\n" +
            "                                  FROM evaluation_data cg\n" +
            "                                  WHERE cg.period = :period\n" +
            "                                  ORDER BY volume_of_sales DESC\n" +
            "                              ) data\n" +
            "                     ) AS v\n" +
            "            ) volume_of_sales ON p.id = volume_of_sales.id;";

    private final static String getProfitXyzResultQuery = "select ad.*\n" +
            "from (\n" +
            "         SELECT p.id,\n" +
            "                p.percent,\n" +
            "                CASE\n" +
            "                    WHEN p.percent > 0 AND p.percent <= 10 THEN 'X'\n" +
            "                    WHEN p.percent > 10 AND p.percent <= 25 THEN 'Y'\n" +
            "                    WHEN p.percent > 25 THEN 'Z'\n" +
            "                    END as result\n" +
            "         FROM (\n" +
            "                  SELECT max(cg.id)                                                                        as id,\n" +
            "                         stddev_samp(cg.revenue - cg.consumption) / avg(cg.revenue - cg.consumption) * 100 as percent\n" +
            "                  FROM evaluation_data cg\n" +
            "                  where evaluation_id = :evaluation_id\n" +
            "                  GROUP BY cg.commodity_group_id\n" +
            "              ) p\n" +
            "     ) ad\n" +
            "         JOIN evaluation_data ed ON ad.id = ed.id\n" +
            "    AND ed.period = (\n" +
            "        select max(ed2.period)\n" +
            "        from evaluation_data ed2\n" +
            "        where ed2.evaluation_id = :evaluation_id\n" +
            "    )\n" +
            "ORDER BY ad.id";

    private final static String getRevenueXyzResultQuery = "select ad.*\n" +
            "from (\n" +
            "         SELECT p.id,\n" +
            "                p.percent,\n" +
            "                CASE\n" +
            "                    WHEN p.percent > 0 AND p.percent <= 10 THEN 'X'\n" +
            "                    WHEN p.percent > 10 AND p.percent <= 25 THEN 'Y'\n" +
            "                    WHEN p.percent > 25 THEN 'Z'\n" +
            "                    END as result\n" +
            "         FROM (\n" +
            "                  SELECT max(cg.id)                                                                        as id,\n" +
            "                         stddev_samp(cg.revenue) / avg(cg.revenue) * 100 as percent\n" +
            "                  FROM evaluation_data cg\n" +
            "                  where evaluation_id = :evaluation_id\n" +
            "                  GROUP BY cg.commodity_group_id\n" +
            "              ) p\n" +
            "     ) ad\n" +
            "         JOIN evaluation_data ed ON ad.id = ed.id\n" +
            "    AND ed.period = (\n" +
            "        select max(ed2.period)\n" +
            "        from evaluation_data ed2\n" +
            "        where ed2.evaluation_id = :evaluation_id\n" +
            "    )\n" +
            "ORDER BY ad.id";

    private final static String getVolumeOfSalesXyzResultQuery = "select ad.*\n" +
            "from (\n" +
            "         SELECT p.id,\n" +
            "                p.percent,\n" +
            "                CASE\n" +
            "                    WHEN p.percent > 0 AND p.percent <= 10 THEN 'X'\n" +
            "                    WHEN p.percent > 10 AND p.percent <= 25 THEN 'Y'\n" +
            "                    WHEN p.percent > 25 THEN 'Z'\n" +
            "                    END as result\n" +
            "         FROM (\n" +
            "                  SELECT max(cg.id)                                                                        as id,\n" +
            "                         stddev_samp(cg.volume_of_sales) / avg(cg.volume_of_sales) * 100 as percent\n" +
            "                  FROM evaluation_data cg\n" +
            "                  where evaluation_id = :evaluation_id\n" +
            "                  GROUP BY cg.commodity_group_id\n" +
            "              ) p\n" +
            "     ) ad\n" +
            "         JOIN evaluation_data ed ON ad.id = ed.id\n" +
            "    AND ed.period = (\n" +
            "        select max(ed2.period)\n" +
            "        from evaluation_data ed2\n" +
            "        where ed2.evaluation_id = :evaluation_id\n" +
            "    )\n" +
            "ORDER BY ad.id";

    private final static String getCommodityGroupAnalysisResultQuery = "SELECT r.*,\n" +
            "       extract(YEAR from r.starts_date)  as year,\n" +
            "       extract(MONTH from r.starts_date) as month,\n" +
            "       extract(DAY from r.starts_date)   as day,\n" +
            "       abc.profit_result                 as abc_profit_result,\n" +
            "       abc.revenue_result                as abc_revenue_result,\n" +
            "       abc.volume_of_sales_result        as abc_volume_of_sales_result,\n" +
            "       xyz.profit_result                 as xyz_profit_result,\n" +
            "       xyz.revenue_result                as xyz_revenue_result,\n" +
            "       xyz.volume_of_sales_result        as xyz_volume_of_sales_result\n" +
            "FROM (\n" +
            "         SELECT max(cgd.id)                                                     as cg_id,\n" +
            "                cgd.name                                                        as name,\n" +
            "                count(cgd.id)                                                   as count_periods,\n" +
            "                max(cgd.period)                                                 as period,\n" +
            "                max(cgd.starts_date::date)                                      as starts_date,\n" +
            "                json_agg(round((cgd.revenue - cgd.consumption)::numeric, 2))    as profit_graph,\n" +
            "                json_agg(round(cgd.revenue::numeric, 2))                        as revenue_graph,\n" +
            "                json_agg(round(cgd.consumption::numeric, 2))                    as consumption_graph,\n" +
            "                json_agg(round(cgd.volume_of_sales::numeric, 2))                as volume_of_sales_graph,\n" +
            "                json_agg(round(cgd.profit_percent::numeric, 5))                 as profit_percent_graph,\n" +
            "                json_agg(round(cgd.revenue_percent::numeric, 5))                as revenue_percent_graph,\n" +
            "                json_agg(round(cgd.volume_of_sales_percent::numeric, 5))        as volume_of_sales_percent,\n" +
            "                json_agg(round(cgd.profit_result_percent::numeric, 5))          as profit_result_percent_graph,\n" +
            "                json_agg(round(cgd.revenue_result_percent::numeric, 5))         as revenue_result_percent_graph,\n" +
            "                json_agg(round(cgd.volume_of_sales_result_percent::numeric, 5)) as volume_of_sales_result_percent,\n" +
            "                json_agg(cgd.profit_result)                                     as profit_results,\n" +
            "                json_agg(cgd.revenue_result)                                    as revenue_results,\n" +
            "                json_agg(cgd.volume_of_sales_result)                            as volume_of_sales_result\n" +
            "         FROM (\n" +
            "                  SELECT cg.*,\n" +
            "                         c.name,\n" +
            "                         e.starts_from as starts_date,\n" +
            "                         ar.profit_result,\n" +
            "                         ar.profit_percent,\n" +
            "                         ar.profit_result_percent,\n" +
            "                         ar.revenue_result,\n" +
            "                         ar.revenue_percent,\n" +
            "                         ar.revenue_result_percent,\n" +
            "                         ar.volume_of_sales_result,\n" +
            "                         ar.volume_of_sales_percent,\n" +
            "                         ar.volume_of_sales_result_percent\n" +
            "                  FROM evaluation_data cg\n" +
            "                           JOIN evaluation e ON cg.evaluation_id = e.id\n" +
            "                           JOIN abc_result ar on cg.id = ar.evaluation_data_id\n" +
            "                           JOIN commodity_group c on cg.commodity_group_id = c.id\n" +
            "                  WHERE c.id = (\n" +
            "                      select min(ed.id)\n" +
            "                      from evaluation_data ed\n" +
            "                               JOIN (\n" +
            "                          select *\n" +
            "                          from evaluation_data ed1\n" +
            "                          WHERE ed1.id = :id\n" +
            "                      ) byid ON ed.evaluation_id = byid.evaluation_id\n" +
            "                          AND ed.commodity_group_id = byid.evaluation_id\n" +
            "                  )\n" +
            "                  ORDER BY cg.id\n" +
            "              ) cgd\n" +
            "         GROUP BY cgd.name\n" +
            "     ) r\n" +
            "         JOIN xyz_result xyz ON r.cg_id = xyz.evaluation_data_id\n" +
            "         JOIN abc_result abc ON r.cg_id = abc.evaluation_data_id;";

    private final static String getEvaluationDataByPeriodAndEvaluationQuery = "select ed.id,\n" +
            "       cg.id as commodity_group_id,\n" +
            "       cg.name,\n" +
            "       round(ed.revenue::numeric, 2) as revenue,\n" +
            "       round((ed.revenue - consumption)::numeric, 2) as profit,\n" +
            "       round(ed.consumption::numeric, 2) as consumption,\n" +
            "       ed.volume_of_sales\n" +
            "from evaluation_data ed\n" +
            "join commodity_group cg on ed.commodity_group_id = cg.id\n" +
            "WHERE evaluation_id = :evaluation_id\n" +
            "  AND period = :period";

    public static String getAbcResultsByEvaluationIdAndPeriodQuery() {
        return getAbcResultsByEvaluationIdAndPeriodQuery;
    }

    public static String getProfitXyzResultQuery() {
        return getProfitXyzResultQuery;
    }

    public static String getRevenueXyzResultQuery() {
        return getRevenueXyzResultQuery;
    }

    public static String getVolumeOfSalesXyzResultQuery() {
        return getVolumeOfSalesXyzResultQuery;
    }

    public static String getCommodityGroupAnalysisResultQuery() {
        return getCommodityGroupAnalysisResultQuery;
    }

    public static String getEvaluationDataByPeriodAndEvaluationQuery() {
        return getEvaluationDataByPeriodAndEvaluationQuery;
    }
}
