<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Оценка">
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
                                                <label style="font-weight:bold;">Наименование оценки</label>
                                            </div>
                                            <div id="evaluationName" class="col-md-8 col-6">

                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Описание оценки</label>
                                            </div>
                                            <div id="evaluationDescription" class="col-md-8 col-6">

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
    <br>



    <div class="container">
        <div class="row">


            <div class="col-md-12">
                <div class="table-responsive">


                    <table id="mytable" class="table table-bordred table-striped">

                        <thead>
                        <th>Период</th>
                        <th>Количество товарных групп</th>
                        <th>Просмотр</th>
                        <th>Удалить</th>
                        </thead>

                        <tbody id="tableBody">
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    </div>
</@layout.menu>
