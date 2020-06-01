<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Профиль">
    <div class="container">
        <div class="row ">
            <div class="col-md-12 py-5 border">
                <h4 class="pb-4">Загрузите файл с данными</h4>
                <form action="/evaluation/train" method="post" enctype="multipart/form-data">
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <input id="file" name="file"
                                   placeholder="Excel файл с данными" class="form-control" required="required" type="file"
                                   accept=".csv" multiple>
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