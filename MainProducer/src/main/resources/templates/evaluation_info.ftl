<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Произвести оценку">
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
