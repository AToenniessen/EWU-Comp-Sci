var table = $("#MovieTable");
var pos = 0;
var largest = 1;
var id = 1;
var edit = false;

$.delete = function (data){
        $.ajax({
            url: 'Backend.php',
            type: 'DELETE',
            data: data
        });
    };
$.get('Backend.php', function(response) {
    console.log(response);
    var res = JSON.parse(response);
    for(var i = 0; i < res.length; i++){
        updateRow(res[i]["ID"], [res[i]["MovieTitle"], res[i]["ReleaseDate"], res[i]["Studio"],
        res[i]["Description"], res[i]["Price"]]);
        id = res[i]["ID"];
    }
});
function addEntry(){
    var form = document.forms["newEntry"];
    if(!edit) {
        createID();
        updateRow(id, [form["MovieTitle"].value, form["ReleaseDate"].value, form["Studio"].value,
            form["Description"].value, form["Price"].value]);
    }
    else {
        edit = false;
        updateRow(pos, [form["MovieTitle"].value, form["ReleaseDate"].value, form["Studio"].value,
            form["Description"].value, form["Price"].value]);
    }
    var data = {
        ID : id,
        MovieTitle : form["MovieTitle"].value,
        ReleaseDate : form["ReleaseDate"].value,
        Studio : form["Studio"].value,
        Description : form["Description"].value,
        Price : form["Price"].value
    };
    $.post("Backend.php", data, function(response){
        console.log(response);
    });
    clearForm(form);

}
function findPos(){
    var rows = table.find('tr');
    for(var i = 0; i < rows.length; i++){
        var cur = rows[i].cells[0].innerHTML;
        if(cur === id) {
            pos = i;
            break;
        }
    }
}
function createID(){
    var rows = table.find('tr');
    if(rows.length === 1)
        id = largest;
    else {
        var cur;
        for (var i = 0; i < rows.length; i++) {
            cur = rows[i].cells[0].innerHTML;
            id = cur;
        }
        id++;
        largest = id;
    }
}
function clearForm(form){
    for(var i = 0; i < form.length; i++){
        form[i].value = "";
    }
}
$('.table-edit').click(function () {
    var row = $(this).closest('tr');
    var form = document.forms["newEntry"];
    var cells = row.find('td');
    for (var i = 0; i < form.length; i++) {
        form[i].value = cells[i + 1].innerHTML;
    }
    id = row.find('td')[0].innerHTML;
    findPos();
    edit = true;
});
$('.table-remove').click(function () {
    var row = $(this).closest('tr');
    var id = row.find('td')[0].innerHTML;
    $.get("Backend.php", {ID: id});
    row.remove();
});
function updateRow(id, data){
    var cells;
    var row = table.find('tr')[id];
    //row['ID'] = id;
    if(row === undefined){
        row = $('#tableRowTemplate').find('tr.hide').clone(true).removeClass('hide table-line');
        //row.find('td')[0] = id;
        table.append(row);
        cells = row.find('td');
        cells[0].innerHTML = id;
    }
    else
        cells = row.cells;
    for(var i = 0; i < data.length; i++){
        cells[i + 1].innerHTML = data[i];
        data[i] = "";
    }
}
