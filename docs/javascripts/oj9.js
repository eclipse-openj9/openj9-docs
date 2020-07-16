window.onload = function () {
    if (document.getElementById("api") !== null) {
        document.getElementsByClassName('md-content')[0].style.marginRight="auto";
        document.getElementsByClassName('md-sidebar--secondary')[0].style.zIndex="-9999999";
    }  else {
        document.getElementsByClassName('md-sidebar--secondary')[0].style.zIndex="0";
    }
}
