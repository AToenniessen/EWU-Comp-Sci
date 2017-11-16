var Vtable = $("#VehicleTable");
var Mtable = $("#MPGTable");
var Metable = $("#MPGeTable");

var update = false;
var id = 1;

updateAll();

function updateAll() {
    updateTable(Vtable, "api/v1/vehicles/GET", ['ID', 'Fuel', 'Type', 'Make', 'Model', 'Year']);
    updateTable(Mtable, "api/v1/mpg/GET", ['ID', 'Gas Miles', 'Gallons used', 'MPG', '$/Gallon', '$/Mile-Gas']);
    updateTable(Metable, "api/v1/mpge/GET", ['ID', 'Electric Miles', 'MPGe', 'kW-hr/100 Miles', '$/kW-hr', '$/Mile-kW']);
}

// $.delete = function (data){
//         $.ajax({
//             url: 'Backend.php',
//             type: 'DELETE',
//             data: data
//         });
//     };
function addVehicleEntry() {
    var form = document.forms["newVehicleEntry"];
    var data = {
        ID: id,
        FUEL: form['Fuel'].value,
        TYPE: form['Type'].value,
        MAKE: form['Make'].value,
        MODEL: form['Model'].value,
        YEAR: form['Year'].value
    };
    $.post("api/v1/vehicles/", data, function (response) {
        console.log(response);
    });
    clearForm(form);
    updateTable(Vtable, "api/v1/vehicles/GET", ['ID', 'Fuel', 'Type', 'Make', 'Model', 'Year']);
    if(update)
        document.getElementById("VehicleButton").style.display = "none";
}

function addAllEntry() {
    addVehicleEntry();
    addMPGEntry();
    addMPGeEntry();
}

function appendRows() {
    var vRow = $('#vehicleRowTemplate').find('tr.hide').clone(true).removeClass('hide table-line');
    var mRow = $('#mpgRowTemplate').find('tr.hide').clone(true).removeClass('hide table-line');
    var meRow = $('#mpgeRowTemplate').find('tr.hide').clone(true).removeClass('hide table-line')
    Vtable.append(vRow);
    Mtable.append(mRow);
    Metable.append(meRow);
}

function addMPGEntry() {
    var form = document.forms["newMPGEntry"];
    var data = {
        ID: id,
        MILES: (form['GasMiles'].value !== "" ? form['GasMiles'].value : 0),
        GALLONS: (form['GallonsUsed'].value !== "" ? form['GallonsUsed'].value : 0),
        $GALLON: (form['$/Gallon'].value !== "" ? form['$/Gallon'].value : 0)
    };
    $.post("api/v1/mpg/", data, function (response) {
        console.log(response);
    });
    clearForm(form);
    updateTable(Mtable, "api/v1/mpg/GET", ['ID', 'Gas Miles', 'Gallons used', 'MPG', '$/Gallon', '$/Mile-Gas']);
    if(update)
        document.getElementById("MPGButton").style.display = "none";
}

function addMPGeEntry() {
    var form = document.forms["newMPGeEntry"];
    var data = {
        ID: id,
        MILES: (form['ElectricMiles'].value !== "" ? form['ElectricMiles'].value : 0),
        MPGE: (form['MPGe'].value !== "" ? form['MPGe'].value : 0),
        $kWHR: (form['$/kW-hr'].value !== "" ? form['$/kW-hr'].value : 0)
    };
    $.post("api/v1/mpge/", data, function (response) {
        console.log(response);
    });
    clearForm(form);
    updateTable(Metable, "api/v1/mpge/GET", ['ID', 'Electric Miles', 'MPGe', 'kW-hr/100 Miles', '$/kW-hr', '$/Mile-kW']);
    if(update)
        document.getElementById("MPGeButton").style.display = "none";
}

function updateTable(table, path, fields) {
    $.get(path, function (response) {
        console.log(response);
        var resp = JSON.parse(response);
        for (var i = 0; i < resp.length; i++) {
            var data = resp[i];
            var row;
            var cells;
            if (table.find('tr')[i + 1] === undefined) {
                appendRows();
                row = table.find('tr')[i + 1];
                cells = row.cells;
            }
            else {
                row = table.find('tr')[i + 1];
                cells = row.cells;
            }
            id = data[fields[0]] + 1;
            cells[0].innerHTML = data[fields[0]];
            cells[1].innerHTML = data[fields[1]];
            cells[2].innerHTML = data[fields[2]];
            cells[3].innerHTML = data[fields[3]];
            cells[4].innerHTML = data[fields[4]];
            cells[5].innerHTML = data[fields[5]];
        }
        if(resp.length === 0 && table.find('tr')[1] !== undefined){
                row = table.find('tr')[1];
                row.remove();
        }
    })
}

function clearForm(form) {
    for (var i = 0; i < form.length; i++) {
        form[i].value = "";
    }
}

$('.vehicle-update').click(function () {
    var row = $(this).closest('tr');
    document.getElementById("VehicleButton").style.display = "";
    update = true;
    var form = document.forms["newVehicleEntry"];
    var cells = row.find('td');
    for (var i = 0; i < form.length; i++) {
        form[i].value = cells[i + 1].innerHTML;
    }
    id = row.find('td')[0].innerHTML;
});
$('.vehicle-remove').click(function () {
    var row = $(this).closest('tr');
    var id = row.find('td')[0].innerHTML;
    $.get("api/v1/vehicles/DELETE/", {ID: id});
    updateAll();
});
$('.mpg-update').click(function () {
    var row = $(this).closest('tr');
    document.getElementById("MPGButton").style.display = "";
    update = true;
    var form = document.forms["newMPGEntry"];
    var cells = row.find('td');
    form[0].value = cells[1].innerHTML;
    form[1].value = cells[2].innerHTML;
    form[2].value = cells[4].innerHTML;
    id = row.find('td')[0].innerHTML;
});
$('.mpg-remove').click(function () {
    var row = $(this).closest('tr');
    var id = row.find('td')[0].innerHTML;
    $.get("api/v1/mpg/DELETE/", {ID: id});
    updateAll();
});
$('.mpge-update').click(function () {
    var row = $(this).closest('tr');
    document.getElementById("MPGeButton").style.display = "";
    update = true;
    var form = document.forms["newMPGeEntry"];
    var cells = row.find('td');
    form[0].value = cells[1].innerHTML;
    form[1].value = cells[2].innerHTML;
    form[2].value = cells[4].innerHTML;
    id = row.find('td')[0].innerHTML;
});
$('.mpge-remove').click(function () {
    var row = $(this).closest('tr');
    var id = row.find('td')[0].innerHTML;
    $.get("api/v1/mpge/DELETE/", {ID: id});
    updateAll();
});
