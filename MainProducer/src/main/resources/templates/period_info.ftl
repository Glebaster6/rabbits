<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Оценка">
    <script src="/js/profile.js"></script>

    <br>

    <div class="container">
        <div class="row">


            <div class="col-md-12">
                <div class="table-responsive">


                    <table id="mytable" class="table table-bordred table-striped">

                        <thead>
                        <th>Товарная группа</th>
                        <th>Прибыль</th>
                        <th>Доход</th>
                        <th>Расход</th>
                        <th>Объём продаж</th>
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
