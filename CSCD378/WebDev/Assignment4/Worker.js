function addEntry(){
    var table = document.getElementById("MovieTable");
    var form = document.forms["newEntry"];
    var row = table.insertRow();
    console.log(form);
    row.insertCell(0).innerHTML = form.elements["MovieTitle"].value;
    row.insertCell(1).innerHTML = form.elements["ReleaseDate"].value;
    row.insertCell(2).innerHTML = form.elements["Studio"].value;
    row.insertCell(3).innerHTML = form.elements["Description"].value;
    row.insertCell(4).innerHTML = form.elements["Price"].value;
}