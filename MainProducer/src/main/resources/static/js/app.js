var stompClient = null;
const data = []

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
            "                                    <button onclick=\"location.href='" + "/evaluation_data/delete/" + eval.id +  "'\" class=\"btn btn-danger btn-xs\" data-title=\"Delete\" data-toggle=\"modal\"\n" +
            "                                            data-target=\"#delete\"><span class=\"glyphicon glyphicon-trash\"></span>\n" +
            "                                    </button>\n" +
            "                                </p>\n" +
            "                            </td>\n" +
            "                        </tr>");
    })
}

