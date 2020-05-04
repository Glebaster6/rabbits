<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Профиль">

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
                                    <h2 class="d-block" style="font-size: 1.5rem; font-weight: bold"><a
                                                href="javascript:void(0);">${model.user.firstName} ${model.user.lastName}</a>
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
                                                <label style="font-weight:bold;">ФИО</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.user.firstName} ${model.user.lastName} ${model.user.patronymic}
                                            </div>
                                        </div>
                                        <hr/>

                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Дата рождения</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.user.birthDate}
                                            </div>
                                        </div>
                                        <hr/>


                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Логин</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.user.login}
                                            </div>
                                        </div>
                                        <hr/>
                                        <div class="row">
                                            <div class="col-sm-3 col-md-2 col-5">
                                                <label style="font-weight:bold;">Должность</label>
                                            </div>
                                            <div class="col-md-8 col-6">
                                                ${model.user.position}
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
    <br>
<#--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">-->

<#if model.user.evaluationTableData?size != 0>
    <div class="container">
        <div class="row">


            <div class="col-md-12">
                <div class="table-responsive">


                    <table id="mytable" class="table table-bordred table-striped">

                        <thead>

                        <th>Наименование</th>
                        <th>Дата создания</th>
                        <th>Дата начала оценки</th>
                        <th>Тип периода</th>
                        <th>Данные загружены</th>
                        <th>Просмотр</th>
                        <th>Редактировать</th>
                        <th>Удалить</th>
                        </thead>
                        <tbody>


                        <#list model.user.evaluationTableData as row>
                            <tr>
                                <td>${row.name}</td>
                                <td>${row.dateCreated}</td>
                                <td>${row.dateStarted}</td>
                                <td>${row.periodType}</td>
                                <td><input type="checkbox" class="checkthis"
                                           placeholder="true" <#if row.uploaded> checked disabled<#else> disabled </#if>/>
                                </td>
                                <td>
                                    <p data-placement="top" data-toggle="tooltip" title="Edit">
                                        <button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal"
                                                data-target="#edit"><span class="glyphicon glyphicon-search"></span>
                                        </button>
                                    </p>
                                </td>
                                <td>
                                    <p data-placement="top" data-toggle="tooltip" title="Delete">
                                        <button class="btn btn-warning btn-xs" data-title="Delete" data-toggle="modal"
                                                data-target="#delete"><span class="glyphicon glyphicon-pencil"></span>
                                        </button>
                                    </p>
                                </td>
                                <td>
                                    <p data-placement="top" data-toggle="tooltip" title="Delete">
                                        <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal"
                                                data-target="#delete"><span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </p>
                                </td>
                            </tr>
                        </#list>

                        </tbody>

                    </table>
                    <#else >
                        <h2 class="text-center">Оценки еще не созданы</h2>
                    </#if>
                </div>
            </div>
        </div>
    </div>


</@layout.menu>