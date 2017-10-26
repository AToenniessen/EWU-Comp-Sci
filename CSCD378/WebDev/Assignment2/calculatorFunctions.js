sessionStorage.expression = "";

var evaluated = false;

$(window).on("load", init());

function init(){
    $(".number").click(numEntered);
    $(".operation").click(opEntered);
    $(".special").click(specialEntered);
}
function numEntered(){
    var output = $("#readout");
    var btnVal = $(this).text().toString();

    if(evaluated){
        evaluated = false;
        sessionStorage.expression = "";
        output.get(0).value = "";
    }
    if(btnVal === "."){
        if(output.get(0).value.indexOf(".") < 0 || output.get(0).value.length < 1){
            sessionStorage.expression += btnVal;
            updateScreen(btnVal);
        }
    }
    else{
        sessionStorage.expression += btnVal;
        updateScreen(btnVal);
    }
}
function opEntered(){
    var output = $("#readout");
    var btnVal = $(this).text().toString();
    if(evaluated){
        sessionStorage.expression = output.get(0).value;
        evaluated = false;
    }
    if(btnVal === "="){
        evaluated = true;
        output.get(0).value = eval(sessionStorage.expression);
    }
    else if(btnVal === "Sq"){
        if(sessionStorage.expression.indexOf("+" || "-" || "*" || "/") < 0)
            evaluated = true;
        var val = sessionStorage.expression.split(/[+\-/*%]/).pop();
        var len = val.length;
        if(val%1 >= 0 && val !== "") {
            sessionStorage.expression = sessionStorage.expression.substr(0, sessionStorage.expression.length - len);
            val = Math.sqrt(val);
            sessionStorage.expression += val;
            var temp = output.get(0).value;
            output.get(0).value = temp.substr(0, temp.length - len);
            updateScreen(val);
        }
    }
    else{
        if(sessionStorage.expression.length > 0) {
            sessionStorage.expression += btnVal;
            updateScreen(btnVal);
        }
    }
}
function specialEntered(){
    var output = $("#readout");
    var btnVal = $(this).text().toString();
    var express = sessionStorage.expression;
    var temp = output.get(0).value;
    if(evaluated){
        sessionStorage.expression = output.get(0).value;
        evaluated = false;
    }
    if(btnVal === "C"){
        sessionStorage.expression = "";
        output.get(0).value = "";
    }
    else if(btnVal === "CE"){
        if(express.length >0) {
            sessionStorage.expression = express.substr(0, express.length - 1);
            output.get(0).value = sessionStorage.expression;
        }
    }
    else if(btnVal === "+-"){
        express = sessionStorage.expression.split(/[^()][%*+\-/]/).pop();
        if(express.indexOf("(") >= 0)
            express = express.substr(1, express.length - 2);
        if(express % 1 >= 0 || express % 1 <= 0){
            if(express * -1 < 0) {
                output.get(0).value = temp.substr(0, temp.length - express.length);
                sessionStorage.expression = sessionStorage.expression.substr(0, sessionStorage.expression.length - express.length);
                sessionStorage.expression += "(" + express * -1 + ")";
                updateScreen("(" + express * -1 + ")");
            }
            else {
                output.get(0).value = temp.substr(0, temp.length - (express.length + 2));
                sessionStorage.expression = sessionStorage.expression.substr(0, sessionStorage.expression.length - (express.length + 2));
                sessionStorage.expression += express * -1;
                updateScreen(express * -1);
            }
        }
    }
    else{
        express = sessionStorage.expression.split(/[^()][%*+\-/]/).pop();
        var val = "";
        if(express.indexOf("(") >= 0){
            temp = express.substr(1, express.length - 2);
            val = "(" + eval(1 / temp) + ")";
        }
        else
            val = eval(1 / express);
        temp = output.get(0).value;
        output.get(0).value = temp.substr(0, sessionStorage.expression.length - express.length);
        sessionStorage.expression = sessionStorage.expression.substr(0, sessionStorage.expression.length - express.length);
        sessionStorage.expression += val;
        updateScreen(val);
    }
}
function updateScreen(x){
    var output = $("#readout");
    output.get(0).value += x;
}