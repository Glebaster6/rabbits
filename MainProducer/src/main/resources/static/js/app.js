var stompClient = null;
const data = []

google.charts.load('current', {'packages': ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawStartChart);

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/notify', function (value) {
            const resp = JSON.parse(value.body)
            resp.data = JSON.parse(resp.json)
            data.push(resp)
            showValue(resp)
        });
    });
}

function showValue(message) {
    switch (message.action) {
        case 'RETURN_FACILITY_DATA': {
            fillFacilityData(message.data)
            break
        }
        case 'RETURN_EVALUATION_DATA': {
            fillEvaluationData(message.data)
            break
        }
        case 'RETURN_EVALUATION_DATA_BY_EVALUATION_AND_PERIOD': {
            fillEvaluationDataByPeriod(message.data)
            break;
        }
        case 'RETURN_COMMODITY_GROUP_RESULT': {
            fillCommodityGroupResultData(message.data)
            break;
        }
    }
    $("#greetings").append("");
}

function fillFacilityData(value) {
    document.getElementById("facilityName").innerText = value.facilityName;
    document.getElementById("facilityDescription").innerText = value.facilityDescription;

    const evaluations = value.evaluations

    evaluations.forEach(eval => {
        $("#tableBody").append("                        <tr>\n" +
            "                            <td>" + eval.evaluationName + "</td>\n" +
            "                            <td>" + eval.createdAt + "</td>\n" +
            "                            <td>\n" +
            "                                <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
            "                                    <button onclick=\"location.href='" + value.facilityId + "/info/" + eval.evaluationId + "'\"  class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
            "                                            data-target=\"#edit\"><span class=\"glyphicon glyphicon-search\"></span>\n" +
            "                                    </button>\n" +
            "                                </p>\n" +
            "                            </td>\n" +
            "                            <td>\n" +
            "                                <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
            "                                    <button onclick=\"location.href='" + value.facilityId + "delete/" + eval.evaluationId + "'\" class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
            "                                            data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
            "                                    </button>\n" +
            "                                </p>\n" +
            "                            </td>\n" +
            "                        </tr>");
    })
}

function fillEvaluationData(value) {
    document.getElementById("evaluationName").innerText = value.evaluationName;
    document.getElementById("evaluationDescription").innerText = value.evaluationDescription;

    const evaluations = value.evaluations

    evaluations.forEach(eval => {
        $("#tableBody").append("                        <tr>\n" +
            "                            <td>" + eval.period + "</td>\n" +
            "                            <td>" + eval.cgCount + "</td>\n" +
            "                            <td>\n" +
            "                                <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
            "                                    <button onclick=\"location.href='" + value.evaluationId + "/period/" + eval.periodNum + "'\"  class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
            "                                            data-target=\"#edit\"><span class=\"glyphicon glyphicon-search\"></span>\n" +
            "                                    </button>\n" +
            "                                </p>\n" +
            "                            </td>\n" +
            "                            <td>\n" +
            "                                <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
            "                                    <button onclick=\"location.href='" + value.evaluationId + "/delete/period/" + eval.periodNum + "'\" class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
            "                                            data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
            "                                    </button>\n" +
            "                                </p>\n" +
            "                            </td>\n" +
            "                        </tr>");
    })
}

function fillEvaluationDataByPeriod(value) {
    const evaluations = value.evaluationDataRows

    evaluations.forEach(eval => {
        $("#tableBody").append("                        <tr>\n" +
            "                            <td>" + eval.name + "</td>\n" +
            "                            <td>" + eval.revenue + "</td>\n" +
            "                            <td>" + eval.profit + "</td>\n" +
            "                            <td>" + eval.consumption + "</td>\n" +
            "                            <td>" + eval.volumeOfSales + "</td>\n" +
            "                            <td>\n" +
            "                                <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
            "                                    <button onclick=\"location.href+='" + "/evaluation_data/" + eval.id + "'\"  class=\"btn btn-primary btn-xs\" data-title=\"Edit\" data-toggle=\"modal\"\n" +
            "                                            data-target=\"#edit\"><span class=\"glyphicon glyphicon-search\"></span>\n" +
            "                                    </button>\n" +
            "                                </p>\n" +
            "                            </td>\n" +
            "                            <td>\n" +
            "                                <p data-placement=\"top\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
            "                                    <button onclick=\"location.href='" + "/evaluation_data/delete/" + eval.id + "'\" class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
            "                                            data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
            "                                    </button>\n" +
            "                                </p>\n" +
            "                            </td>\n" +
            "                        </tr>");
    })
}

function fillCommodityGroupResultData(value) {
    document.getElementById("commodityGroupName").innerText = value.name;
    document.getElementById("profitAbc").innerText = value.abcProfitResult;
    document.getElementById("revenueAbc").innerText = value.abcRevenueResult;
    document.getElementById("volumeOfSalesAbc").innerText = value.abcVolumeOfSalesResult;
    document.getElementById("profitXyz").innerText = value.xyzProfitResult;
    document.getElementById("revenueXyz").innerText = value.xyzRevenueResult;
    document.getElementById("volumeOfSalesXyz").innerText = value.xyzVolumeOfSalesResult;

    drawStartChart(value)
}

function generateDate(value) {
    const dateType = 'month'
    const periodCount = value.periodCount

    const startDate = new Date(value.startYear, value.startMonth, value.startDay)
    console.log(new Date(2019, 12, 1))
    const results = []
    for (let i = 0; i < periodCount; i++) {
        results.push(addZero(startDate.getDay()) + '.' + addZero(startDate.getMonth() + 1) + '.' + startDate.getFullYear())
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

    if (val < 10) {
        return '0'.concat(val)
    } else {
        return val
    }
}

function drawStartChart(value) {
    const startData = google.visualization.arrayToDataTable(generateStartData(value));
    const percentData = google.visualization.arrayToDataTable(generatePercentDataChart(value));
    const percentResultData = google.visualization.arrayToDataTable(generatePercentResultDataChart(value));
    const abcResultData = google.visualization.arrayToDataTable(generateAbcResultChartData(value));


    const curveLineChartOtions = {
        title: value.name,
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

function generateStartData(value) {
    const periodCount = value.periodCount
    const periods = generateDate(value)
    const profitData = JSON.parse(value.profitChart)
    const consumptionData = JSON.parse(value.consumptionChart)
    const revenueData = JSON.parse(value.revenueChart)

    const results = []
    results.push(['Период', 'Доход', 'Расход', 'Прибыль'])
    for (let i = 0; i < periodCount; i++) {
        results.push([periods[i], parseFloat(revenueData[i]), parseFloat(consumptionData[i]), parseFloat(profitData[i])])
    }

    return results;
}

function generatePercentDataChart(value) {
    const periodCount = value.periodCount
    const periods = generateDate(value)
    const profitData = JSON.parse(value.profitPercentChart)
    const revenueData = JSON.parse(value.revenuePercentChart)
    const volumeOfSalesPercent = JSON.parse(value.volumeOfSalesPercentChart)

    const results = []
    results.push(['Период', 'Прибыль', 'Доход', 'Объем Продаж'])
    for (let i = 0; i < periodCount; i++) {
        results.push([periods[i], profitData[i], revenueData[i], volumeOfSalesPercent[i]])
    }

    return results;
}

function generatePercentResultDataChart(value) {
    const periodCount = value.periodCount
    const periods = generateDate(value)
    const profitData = JSON.parse(value.profitResultPercentChart)
    const revenueData = JSON.parse(value.revenueResultPercentChart)
    const volumeOfSalesPercent = JSON.parse(value.volumeOfSalesResultPercentChart)

    const results = []
    results.push(['Период', 'Прибыль', 'Доход', 'Объем Продаж'])
    for (let i = 0; i < periodCount; i++) {
        results.push([periods[i], profitData[i], revenueData[i], volumeOfSalesPercent[i]])
    }

    return results;
}

function generateAbcResultChartData(value) {
    const periodCount = value.periodCount
    const periods = generateDate(value)
    const profitData = JSON.parse(value.profitResults)
    const revenueData = JSON.parse(value.revenueResults)
    const volumeOfSalesPercent = JSON.parse(value.volumeOfSalesResults)

    const results = []
    results.push(['Период', 'Прибыль', 'Доход', 'Объем Продаж'])
    for (let i = 0; i < periodCount; i++) {
        results.push([periods[i], profitData[i], revenueData[i], volumeOfSalesPercent[i]])
    }

    return results;
}

