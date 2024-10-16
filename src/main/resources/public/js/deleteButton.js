document.getElementById("deleteButton").addEventListener("click", function() {
    if (confirm("¿Estás seguro de que querés cancelar esta donación?")) {
        document.getElementById("deleteForm").submit();
    }
});