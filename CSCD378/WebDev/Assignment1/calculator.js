function calculateMortgage(){
    var principal = parseFloat(document.getElementById("principal").value);
    var rate = parseFloat(document.getElementById("interest").value);
    var payment = parseFloat(document.getElementById("payment").value);
    var table = document.getElementById("outputTable");

    resetTable(table);

    var balance = principal;

    var pos = 1;

    while(principal > 0){
        var monthly = principal * ((rate/12)/100);

        if(principal - payment + monthly <= 0){
            payment = principal + monthly;
            insertEntry(table, principal, rate, payment, 0);
            principal = 0;
        }
        else{
            balance = (principal + rate) - payment;
            insertEntry(table, principal, rate, payment, balance);
            principal = balance;
        }
        pos++;
    }
}
function insertEntry(table, principal, rate, payment, balance){
    var newRow = table.insertRow();

    newRow.insertCell(0).innerHTML = "$" + principal.toFixed(2).toString();
    newRow.insertCell(1).innerHTML = "$" + rate.toFixed(2).toString();
    newRow.insertCell(2).innerHTML = "$" + payment.toFixed(2).toString();
    newRow.insertCell(3).innerHTML = "$" + balance.toFixed(2).toString();
}
function resetTable(table){
    table.innerHTML = "";
    var newRow = table.insertRow();

    newRow.insertCell(0).innerHTML = "Starting Principal";
    newRow.insertCell(1).innerHTML = "Interest Amount";
    newRow.insertCell(2).innerHTML = "Payment";
    newRow.insertCell(3).innerHTML = "Ending Balance";
}
