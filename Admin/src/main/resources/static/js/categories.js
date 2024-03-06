$('document').ready(function() {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var id = $(this).attr('href').split('=')[1]; // Pobieramy id z href
        var categoryName = $(this).closest('tr').find('td:eq(1)').text(); // Pobieramy nazwę kategorii z tabeli
        $('#categoryEditId').val(id);
        $('#categoryNameEdit').val(categoryName);
        $('#editModal').modal();
    });
});
