<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Результаты: ${model.result.name} ">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart', 'bar']});
        google.charts.setOnLoadCallback(drawStartChart);

        function generateDate() {
            const dateType = '${model.result.period}'
            const periodCount =
            ${model.result.periodCount}

            const startDate = new Date(${model.result.startYear}, ${model.result.startMonth}, ${model.result.startDay})
            const results = []
            for (let i = 0; i < periodCount; i++) {
                results.push(addZero(startDate.getDay()) + '.' + addZero(startDate.getMonth()) + '.' + startDate.getFullYear())
                switch (dateType) {
                    case 'month': {
                        startDate.setMonth(startDate.getMonth() + 1)
                        break
                    }
                    case 'week': {
                        startDate.setDate(startDate.getDate() + 7)
                        break
                    }
                    case 'year': {
                        startDate.setDate(startDate.setFullYear(startDate.getFullYear() + 1))
                        break
                    }
                    case 'quarter': {
                        startDate.setMonth(startDate.getMonth() + 3)
                        break
                    }
                    case 'day': {
                        startDate.setDate(startDate.getDate() + 1)
                        break
                    }
                }
            }

            return results;
        }
        function addZero(val) {

            if (val < 10){
                return '0'.concat(val)
            } else {
                return val
            }
        }
        function drawStartChart() {
            const startData = google.visualization.arrayToDataTable(generateStartData());
            const percentData = google.visualization.arrayToDataTable(generatePercentDataChart());
            const percentResultData = google.visualization.arrayToDataTable(generatePercentResultDataChart());
            const abcResultData = google.visualization.arrayToDataTable(generateAbcResultChartData());

            const curveLineChartOtions = {
                title: '${model.result.name}',
                curveType: 'function',
                legend: {position: 'bottom'}
            };

            const barChartOptions = {
                chart: {
                    title: 'Результаты ABC анализа',
                }
            };

            const startChart = new google.visualization.LineChart(document.getElementById('curve_start_chart'));
            const percentChart = new google.visualization.LineChart(document.getElementById('curve_chart_percent'));
            const percentResultChart = new google.visualization.LineChart(document.getElementById('curve_chart_percent_result'));
            const barChart = new google.charts.Bar(document.getElementById('bar_chart_abc'));

            startChart.draw(startData, curveLineChartOtions);
            percentChart.draw(percentData, curveLineChartOtions);
            percentResultChart.draw(percentResultData, curveLineChartOtions);
            barChart.draw(abcResultData, google.charts.Bar.convertOptions(barChartOptions));
        }
        function generateStartData() {
            const periodCount =
            ${model.result.periodCount}
            const periods = generateDate()
            const profitData =
            ${model.result.profitChart}
            const consumptionData =
            ${model.result.consumptionChart}
            const revenueData =
            ${model.result.revenueChart}

            const results = []
            results.push(['Период', 'Доход', 'Расход', 'Прибыль'])
            for (let i = 0; i < periodCount; i++) {
                results.push([periods[i], revenueData[i], consumptionData[i], profitData[i]])
            }

            return results;
        }
        function generatePercentDataChart() {
            const periodCount =
            ${model.result.periodCount}
            const periods = generateDate()
            const profitData =
            ${model.result.profitPercentChart}
            const revenueData =
            ${model.result.revenuePercentChart}
            const volumeOfSalesPercent =
            ${model.result.volumeOfSalesPercentChart}

            const results = []
            results.push(['Период', 'Прибыль', 'Доход', 'Объем Продаж'])
            for (let i = 0; i < periodCount; i++) {
                results.push([periods[i], profitData[i],revenueData[i], volumeOfSalesPercent[i]])
            }

            return results;
        }
        function generatePercentResultDataChart() {
            const periodCount =
            ${model.result.periodCount}
            const periods = generateDate()
            const profitData =
            ${model.result.profitResultPercentChart}
            const revenueData =
            ${model.result.revenueResultPercentChart}
            const volumeOfSalesPercent =
            ${model.result.volumeOfSalesResultPercentChart}

            const results = []
            results.push(['Период', 'Прибыль', 'Доход', 'Объем Продаж'])
            for (let i = 0; i < periodCount; i++) {
                results.push([periods[i], profitData[i],revenueData[i], volumeOfSalesPercent[i]])
            }

            return results;
        }
        function generateAbcResultChartData() {
            const periodCount =
            ${model.result.periodCount}
            const periods = generateDate()
            const profitData =
            ${model.result.profitResults}
            const revenueData =
            ${model.result.revenueResults}
            const volumeOfSalesPercent =
            ${model.result.volumeOfSalesResults}

            const results = []
            results.push(['Период', 'Прибыль', 'Доход', 'Объем Продаж'])
            for (let i = 0; i < periodCount; i++) {
                results.push([periods[i], profitData[i],revenueData[i], volumeOfSalesPercent[i]])
            }

            return results;
        }
    </script>

    <div id="curve_start_chart" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%"></div>
    <div id="curve_chart_percent" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%; margin-top: 20px"></div>
    <div id="curve_chart_percent_result" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%; margin-top: 20px"></div>
    <div id="bar_chart_abc" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%; margin-top: 20px"></div>



</@layout.menu>