$(function(){
    $("[data-include]").each(function(i, elemento){
       var nombreArchivo = $(elemento).data("include") + ".html";
       $(elemento).load(nombreArchivo);
    });
});