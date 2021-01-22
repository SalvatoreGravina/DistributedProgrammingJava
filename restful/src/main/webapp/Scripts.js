var baseUrl = "http://10.0.2.2:8080/restful/resources/";
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path='/';";
}
function redirectLogin() {
    window.location.href = "/restful/loginUser.html";
}
function redirectRegistration() {
    window.location.href = "/restful/registration.html";
}
function redirectProfile() {
    window.location.href = "/restful/profile.html";

}
function redirectMyOrders() {
    window.location.href = "/restful/myorders.html";

}
function redirectNewOrder() {
    window.location.href = "/restful/neworder.html";
}
function loginUser() {
    var data = document.getElementById("loginform");
    var inputElements = data.getElementsByTagName("input");
    var details = new Object();
    for (var i = 0; i < inputElements.length; i++) {
        details[inputElements[i].name] = inputElements[i].value;
    }
    details["token"] = JavaScriptInterface.readToken();
    var formBody = [];
    for (var property in details) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(details[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var response = xmlHttp.responseXML;
            var result = response.getElementsByTagName("result")[0].innerHTML;
            if (result.localeCompare("success") === 0) {
                document.getElementById("result").innerHTML = result;
                document.getElementById("result").style.visibility = "visible";
                setCookie("email", details["email"], 10);
                setCookie("password", details["password"], 10);
                setTimeout(() => {
                    window.location.href = "/restful/home.html";
                }, 1000);
            } else {
                document.getElementById("result").innerHTML = "login failed";
                document.getElementById("result").style.visibility = "visible";
            }
        }
    };
    xmlHttp.open("POST", baseUrl + "UserService/users/login", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(formBody);
}

function getOrders() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var a = new Date();
            var response = JSON.parse(xmlHttp.responseText);
            var table = "<table><tr><th>IDOrdine</th><th>data</th><th>Importo</th></tr>";
            for (var i = 0; i < response.length; i++) {
                console.log(response[i]);
                table += "<tr>";
                table += "<td>" + response[i].ID + "</td><td>" + response[i].dataCreazione.substring(0,16) + "</td><td>" + response[i].costo + "</td></tr>";
            }
            table += "</table>";
            document.getElementById("orderList").innerHTML = table;
            document.getElementById("orderList").style.visibility = "visible";
        }
    };
    var cookie = getCookie("email");
    console.log(cookie);
    xmlHttp.open("GET", baseUrl + "OrderService/orders/" + cookie, true);
    xmlHttp.send();
}
function getMenu() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var response = JSON.parse(xmlHttp.responseText);
            var table = "<table><tr><th>tipo</th><th>nome</th><th>costo</th></tr>";
            for (var i = 0; i < response.length; i++) {
                table += "<tr>";
                table += "<td class=\"productID\" hidden>" + response[i].id_prodotto + "</td>";
                table += "<td class=\"productType\">" + response[i].tipo + "</td>";
                table += "<td>" + response[i].nome + "</td>";
                table += "<td>" + response[i].costo + "</td><td><input class=\"quantity\" type=\"number\" value=\"0\" min=\"0\"></td>";
                table += "</tr>";
            }
            table += "</table>";
            document.getElementById("menu").innerHTML = table;
        }
    };
    xmlHttp.open("GET", baseUrl + "ProductService/products", true);
    xmlHttp.send();

    var today = new Date().toISOString();
    var todayn = today.substring(0, 16);

    document.getElementById("deliveryTime").min = todayn;

}

function getInternalMenu() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var response = JSON.parse(xmlHttp.responseText);
            var table = "<table><tr><th>tipo</th><th>nome</th><th>costo</th></tr>";
            for (var i = 0; i < response.length; i++) {
                table += "<tr>";
                table += "<td class=\"productID\" hidden>" + response[i].id_prodotto + "</td>";
                table += "<td class=\"productType\">" + response[i].tipo + "</td>";
                table += "<td>" + response[i].nome + "</td>";
                table += "<td>" + response[i].costo + "</td><td><input class=\"quantity\" type=\"number\" value=\"0\" min=\"0\"></td>";
                table += "</tr>";
            }
            table += "</table>";
            document.getElementById("menu").innerHTML = table;
        }
    };
    xmlHttp.open("GET", "http://localhost:8080/restful/resources/ProductService/products", true);
    xmlHttp.send();
}

function getTables() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var response = JSON.parse(xmlHttp.responseText);
            var label = "Seleziona il tavolo<br/>";
            var select = "<select id=\"tableselect\" name=\"tables\">";
            for (var i = 0; i < response.length; i++) {
                var option = "<option value=\""+response[i].ID_tavolo+","+response[i].capienza+"\">";
                option += "tavolo " + response[i].ID_tavolo + " - " + response[i].capienza + " posti";
                option += "</option>";
                select += option;
            }
            select += "</select>";
            document.getElementById("tableDiv").innerHTML = label+select;
        }
    };
    xmlHttp.open("GET", "http://localhost:8080/restful/resources/TableService/tables", true);
    xmlHttp.send();
}

function addInternalOrder(){
    var pizzaMap = new Object();
    var friedMap = new Object();
    var tr = document.getElementsByTagName("tr");
    for (var i = 1; i < tr.length; i++) {
        var ID = tr[i].getElementsByClassName("productID")[0].innerHTML;
        var type = tr[i].getElementsByClassName("productType")[0].innerHTML;
        var quantity = tr[i].getElementsByClassName("quantity")[0].value;
        if (type.localeCompare("pizza") === 0 && quantity > 0) {
            pizzaMap[ID] = quantity;
        } else if (type.localeCompare("fritto") === 0 && quantity > 0) {
            friedMap[ID] = quantity;
        }
        console.log(ID);

    }
    var dict = new Object();
    dict["type"] = 2;
    var sel = document.getElementById("tableselect");
    var selection = sel.options[sel.selectedIndex].value;
    dict["table"] = selection.split(",")[0];
    dict["sitting"] = selection.split(",")[1];
    dict["pizzaMap"] = JSON.stringify(pizzaMap);
    dict["friedMap"] = JSON.stringify(friedMap);
    console.log(dict["pizzaMap"]);
    console.log(dict["friedMap"]);
    var formBody = [];
    for (var property in dict) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(dict[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var response = xmlHttp.responseXML;
            var result = response.getElementsByTagName("result")[0].innerHTML;
            if (result.localeCompare("success") === 0) {
                document.getElementById("result").innerHTML = "Ordine ricevuto!";
                document.getElementById("result").style.visibility = "visible";

                setTimeout(() => {
                    window.location.href = "/restful/internalorder.html";
                }, 1000);
            } else {
                document.getElementById("result").innerHTML = "Errore ordine";
                document.getElementById("result").style.visibility = "visible";

                setTimeout(() => {
                    window.location.href = "/restful/internalorder.html";
                }, 1000);
            }
        }
    };
    console.log(formBody);
    xmlHttp.open("POST", "http://localhost:8080/restful/resources/" + "OrderService/orders", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(formBody);
}
function addOrder() {
    var pizzaMap = new Object();
    var friedMap = new Object();
    var tr = document.getElementsByTagName("tr");
    for (var i = 1; i < tr.length; i++) {
        var ID = tr[i].getElementsByClassName("productID")[0].innerHTML;
        var type = tr[i].getElementsByClassName("productType")[0].innerHTML;
        var quantity = tr[i].getElementsByClassName("quantity")[0].value;
        if (type.localeCompare("pizza") === 0 && quantity > 0) {
            pizzaMap[ID] = quantity;
        } else if (type.localeCompare("fritto") === 0 && quantity > 0) {
            friedMap[ID] = quantity;
        }
        console.log(ID);

    }
    var dict = new Object();
    var date = Date.parse(document.getElementById("deliveryTime").value);
    console.log(date.valueOf());
    dict["type"] = 3;
    dict["email"] = getCookie("email");
    dict["pizzaMap"] = JSON.stringify(pizzaMap);
    dict["friedMap"] = JSON.stringify(friedMap);
    dict["deliveryTime"] = date;
    console.log(dict["pizzaMap"]);
    console.log(dict["friedMap"]);
    var formBody = [];
    for (var property in dict) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(dict[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var response = xmlHttp.responseXML;
            var result = response.getElementsByTagName("result")[0].innerHTML;
            if (result.localeCompare("success") === 0) {
                document.getElementById("result").innerHTML = "Ordine ricevuto!";
                document.getElementById("result").style.visibility = "visible";

                setTimeout(() => {
                    window.location.href = "/restful/myorders.html";
                }, 1000);
            } else {
                document.getElementById("result").innerHTML = "Errore ordine";
                document.getElementById("result").style.visibility = "visible";

                setTimeout(() => {
                    window.location.href = "/restful/neworder.html";
                }, 1000);
            }
        }
    };
    console.log(formBody);
    xmlHttp.open("POST", baseUrl + "OrderService/orders", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(formBody);
}

function getInfo() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {

            var response = JSON.parse(xmlHttp.responseText);
            document.getElementById("name").value = response[0].name;
            document.getElementById("surname").value = response[0].surname;
            document.getElementById("address").value = response[0].address;
            document.getElementById("email").value = response[0].email;
            document.getElementById("password").value = response[0].password;
            document.getElementById("phone").value = response[0].phone;

        }
    };
    var cookie = getCookie("email");
    xmlHttp.open("GET", baseUrl + "UserService/users/" + cookie, true);
    xmlHttp.send();
}
function modifyUser() {
    var data = document.getElementById("profileform");
    var inputElements = data.getElementsByTagName("input");
    var details = new Object();
    details["oldemail"] = getCookie("email");
    for (var i = 0; i < inputElements.length; i++) {
        details[inputElements[i].name] = inputElements[i].value;
    }
    var formBody = [];
    for (var property in details) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(details[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {

            var response = xmlHttp.responseXML;
            var result = response.getElementsByTagName("result")[0].innerHTML;
            if (result.localeCompare("success") === 0) {
                setCookie("email", details["email"], 10);
                document.getElementById("result").innerHTML = "update succeded";
                document.getElementById("result").style.visibility = "visible";

            } else {
                document.getElementById("result").innerHTML = "update not succeded";
                document.getElementById("result").style.visibility = "visible";
            }
        }
    };
    xmlHttp.open("PUT", baseUrl + "UserService/users", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(formBody);
}
function deleteUser() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {

            var response = xmlHttp.responseXML;
            var result = response.getElementsByTagName("result")[0].innerHTML;
            if (result.localeCompare("success") === 0) {
                document.getElementById("result").innerHTML = "delete succeded";
                document.getElementById("result").style.visibility = "visible";
                setCookie("email", "", -1);
                setCookie("password", "", -1);
                setTimeout(() => {
                    window.location.href = "/restful/index.html";
                }, 1000);
            } else {
                document.getElementById("result").innerHTML = "delete failed";
                document.getElementById("result").style.visibility = "visible";
            }
        }
    };
    var cookie = getCookie("email");
    xmlHttp.open("DELETE", baseUrl + "UserService/users/" + cookie, true);
    xmlHttp.send();
}

function addUser() {
    var data = document.getElementById("registrationform");
    var inputElements = data.getElementsByTagName("input");
    var details = new Object();
    for (var i = 0; i < inputElements.length; i++) {
        details[inputElements[i].name] = inputElements[i].value;
    }
    details["token"] = JavaScriptInterface.readToken();
    var formBody = [];
    for (var property in details) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(details[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {

            var response = xmlHttp.responseXML;
            var result = response.getElementsByTagName("result")[0].innerHTML;
            if (result.localeCompare("success") === 0) {
                document.getElementById("result").innerHTML = "registration succeded";
                document.getElementById("result").style.visibility = "visible";
                setCookie("email", details["email"], 10);
                setTimeout(() => {
                    window.location.href = "/restful/home.html";
                }, 1000);
            } else {
                document.getElementById("result").innerHTML = "registration not succeded";
                document.getElementById("result").style.visibility = "visible";
            }
        }
    };

    xmlHttp.open("POST", baseUrl + "UserService/users", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send(formBody);
}
