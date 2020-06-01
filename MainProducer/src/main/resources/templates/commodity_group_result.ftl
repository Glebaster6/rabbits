<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Результаты: результаты товарной группы">

    <script src="/js/profile.js"></script>
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
                                    <h2 id="facilityTitle" class="d-block" style="font-size: 1.5rem; font-weight: bold">
                                        <a href="javascript:void(0);"></a>
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
                                                <label style="font-weight:bold;">Наименование товарной группы</label>
                                            </div>
                                            <div id="commodityGroupName" class="col-md-8 col-6">
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Результат ABC для дохода</label>
                                            </div>
                                            <div id="profitAbc" class="col-md-8 col-6">
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Результат ABC для прибыли</label>
                                            </div>
                                            <div id="revenueAbc" class="col-md-8 col-6">
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Результат ABC для объема продаж</label>
                                            </div>
                                            <div id="volumeOfSalesAbc" class="col-md-8 col-6">
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Результат XYZ для дохода</label>
                                            </div>
                                            <div id="profitXyz" class="col-md-8 col-6">
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Результат XYZ для прибыли</label>
                                            </div>
                                            <div id="revenueXyz" class="col-md-8 col-6">
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Результат XYZ для объема продаж</label>
                                            </div>
                                            <div id="volumeOfSalesXyz" class="col-md-8 col-6">
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Прогноз объема продаж</label>
                                            </div>
                                            <div id="prediction" class="col-md-8 col-6">
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

    <br>
    <hr>
    <br>

    <div id="curve_start_chart" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%"></div>
    <div id="curve_chart_percent" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%; margin-top: 20px"></div>
    <div id="curve_chart_percent_result" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%; margin-top: 20px"></div>
    <div id="bar_chart_abc" style="width: 90%; height: 40%; margin-left: 10%; margin-right: 10%; margin-top: 20px"></div>

</@layout.menu>