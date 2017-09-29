function calculateMortgage(){
    var principal = parseFloat(document.getElementById("principal").value);
    var interestRate = parseFloat(document.getElementById("interest").value);
    var payment = parseFloat(document.getElementById("payment").value);
    var table = document.getElementById("outputTable");

    resetTable(table);

    var balance = principal;

    while(principal > 0){
        var monthlyInterest = principal * ((interestRate/12)/100);

        if(principal - (payment + monthlyInterest) <= 0){
            payment = principal + monthlyInterest;
            insertEntry(table, principal, monthlyInterest, payment, 0);
            principal = 0;
        }
        else{
            balance = (principal + monthlyInterest) - payment;
            insertEntry(table, principal, monthlyInterest, payment, balance);
            principal = balance;
        }
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
    while(table.rows.length > 1){
        table.deleteRow(1);
    }
}
