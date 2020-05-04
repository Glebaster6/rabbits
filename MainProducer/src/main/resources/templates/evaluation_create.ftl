<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Произвести оценку">
        <div class="container">
            <div class="row ">
                <div class="col-md-12 py-5 border">
                    <h4 class="pb-4">Введите данные производимой оценки</h4>
                    <form action="/evaluation/create" method="post" enctype="multipart/form-data">
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <input id="name" name="name" placeholder="Наименование оценки" class="form-control" type="text">
                            </div>
                            <div class="form-group col-md-6">
                                <input id="startsFrom" name="startsFrom" type="date" class="form-control" placeholder="Начало проведения">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <input id="file" name="file"
                                       placeholder="Excel файл с данными" class="form-control" required="required" type="file"
                                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" multiple>
                            </div>
                            <div class="form-group col-md-12">
                                <textarea id="description" name="description" cols="40" rows="5" class="form-control"></textarea>
                            </div>
                        </div>

                        <div class="form-row">
                            <input type="submit" name="submit" class="btn btn-success" value="Произвести оценку"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>

</@layout.menu>

