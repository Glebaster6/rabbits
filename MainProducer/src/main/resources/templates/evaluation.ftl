<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Результаты: ${model.result.name} ">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card">

                    <div class="card-body">
                        <div class="card-title mb-4">
                            <div class="d-flex justify-content-start">
                                <div class="image-container">
                                    <div class="middle">
                                        <input type="file" style="display: none;" id="profilePicture" name="file"/>
                                    </div>
                                </div>
                                <div class="userData ml-3">
                                    <h2 class="d-block" style="font-size: 1.5rem; font-weight: bold"><a
                                                href="javascript:void(0);">${model.result.name}</a>
                                    </h2>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" id="basicInfo-tab" data-toggle="tab"
                                           href="#basicInfo" role="tab" aria-controls="basicInfo" aria-selected="true">Основная
                                            информация</a>
                                    </li>
                                </ul>
                                <div class="tab-content ml-1" id="myTabContent">
                                    <div class="tab-pane fade show active" id="basicInfo" role="tabpanel"
                                         aria-labelledby="basicInfo-tab">


                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Наименование оценки</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.result.name}
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Описание оценки</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.result.description}
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Шаг периода</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.result.periodType}
                                            </div>
                                        </div>
                                        <hr/>
                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Дата начала оценки</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.result.startsFromDate}
                                            </div>
                                        </div>
                                        <hr/>
                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Дата создания оценки</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.result.evaluationCreated}
                                            </div>
                                        </div>
                                        <hr/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
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
    </script>



</@layout.menu>