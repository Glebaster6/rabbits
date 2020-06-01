<#ftl encoding='UTF-8'>
<#import "menu_layout.ftl" as layout/>
<@layout.menu title="Профиль">
<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="loader" ></div>
    <script>
        window.setTimeout(function(){
            window.location.href = "http://localhost:8080/";
        }, 2000);
    </script>

<style>
    .loader {
        border: 16px solid #f3f3f3;
        border-radius: 50%;
        border-top: 16px solid #3498db;
        width: 120px;
        height: 120px;
        -webkit-animation: spin 2s linear infinite; /* Safari */
        animation: spin 2s linear infinite;
        margin-left: 50%;
        margin-top: 25%;
    }

    /* Safari */
    @-webkit-keyframes spin {
        0% { -webkit-transform: rotate(0deg); }
        100% { -webkit-transform: rotate(360deg); }
    }

    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }
</style>
</@layout.menu>