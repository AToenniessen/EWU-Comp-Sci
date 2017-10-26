var proxy = 'https://cors-anywhere.herokuapp.com/';

var HttpClient = function () {
    this.get = function (aUrl, aCallback) {
        $.ajax({
            url: proxy + aUrl,
            type: 'GET',
            success: aCallback
        });
    }
};
function executeYoda(){
    var client = new HttpClient();
    if(!event.detail || event.detail == 1) {
        var phrase = $("#phrase").val().toString().replace(" ", "%20");
        phrase = phrase.replace(",", "%2C");

        clearTable(document.getElementById("yodTable"), document.getElementById("yodTable").rows.length);

        client.get('http://api.funtranslations.com/translate/yoda.json?text=' + phrase,
            function (response) {
                console.log(response);
                var row = document.getElementById("yodTable").insertRow();

                row.innerHTML = response['contents']["translated"];
            }
        )
    }
}
function execute(){
        var client = new HttpClient();
    if(!event.detail || event.detail == 1) {
        var word = $("#word").val().toString();
        clearTable(document.getElementById("defTable"), document.getElementById("defTable").rows.length);
        clearTable(document.getElementById("synTable"), document.getElementById("synTable").rows.length);
        clearTable(document.getElementById("antTable"), document.getElementById("antTable").rows.length);
        clearTable(document.getElementById("anaTable"), document.getElementById("anaTable").rows.length);
        clearTable(document.getElementById("forTable"), document.getElementById("forTable").rows.length);


        client.get('http://api.datamuse.com/words/?sp=' + word + '&qe=sp&md=d',
            function (response) {
                console.log(response);
                var table = document.getElementById("defTable");
                var cur = response[0]["defs"];
                for (var i = 0; i < cur.length; i++) {
                    var row = table.insertRow();
                    var str = cur[i].split("\t");
                    row.insertCell(0).innerHTML += str[0];
                    row.insertCell(1).innerHTML += str[1];

                }
            });
        client.get('http://api.datamuse.com/words/' + '?rel_syn=' + word,
            function (response) {
                console.log(response);
                var row = document.getElementById("synTable");
                for (var i = 0; i < response.length; i++) {
                    row.insertRow().innerHTML += response[i]["word"].toString()
                }
            });
        client.get('http://api.datamuse.com/words/' + '?rel_ant=' + word,
            function (response) {
                console.log(response);
                var row = document.getElementById("antTable");
                for (var i = 0; i < response.length; i++) {
                    row.insertRow().innerHTML += response[i]["word"].toString()
                }
            });
        client.get('http://www.anagramica.com/best/:' + word,
            function (response) {
                console.log(response);
                var row = document.getElementById("anaTable");
                for (var i = 0; i < response["best"].length; i++) {
                    row.insertRow().innerHTML += response["best"][i].toString()
                }
            });
        client.get('https://script.google.com/macros/s/AKfycbwLFkxM7QyEpaJAOxmVwKdFSEWVkX2iB683NTI8vq4QRuqI3Oo/exec?source=en&q=' + word + '&target=zh-CN',
            function (response) {
                console.log(response);
                var res = JSON.parse(response);
                var table = document.getElementById("forTable");
                var row = table.insertRow(1);
                row.insertCell(0).innerHTML += "Chinese Simplified";
                row.insertCell(1).innerHTML += res["translatedText"];
            }
        );
        client.get('https://script.google.com/macros/s/AKfycbwLFkxM7QyEpaJAOxmVwKdFSEWVkX2iB683NTI8vq4QRuqI3Oo/exec?source=en&q=' + word + '&target=es',
            function (response) {
                console.log(response);
                var res = JSON.parse(response);
                var table = document.getElementById("forTable");
                var row = table.insertRow(1);
                row.insertCell(0).innerHTML += "Spanish";
                row.insertCell(1).innerHTML += res["translatedText"];
            });
        client.get('https://script.google.com/macros/s/AKfycbwLFkxM7QyEpaJAOxmVwKdFSEWVkX2iB683NTI8vq4QRuqI3Oo/exec?source=en&q=' + word + '&target=hi',
            function (response) {
                console.log(response);
                var res = JSON.parse(response);
                var table = document.getElementById("forTable");
                var row = table.insertRow(1);
                row.insertCell(0).innerHTML += "Hindi";
                row.insertCell(1).innerHTML += res["translatedText"];
            });
    }
}
function clearTable(table, curLen){
    if(curLen !== undefined) {
        for (var i = 1; i < curLen; i++) {
            table.deleteRow(1);
        }
    }
}