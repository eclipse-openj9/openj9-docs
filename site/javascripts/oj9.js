window.onload = function () {
    console.log(document.getElementById("api")!== null)
    if (document.getElementById("api") !== null) {
        console.log(document.getElementById("api")!== null)
        document.getElementsByClassName('md-content')[0].style.marginRight="auto";
        console.log("Hello 1");
    } else {
        console.log(document.getElementById("api")!== null)
        document.getElementsByClassName('md-content')[0].style.marginRight="12.1rem";
        console.log("Hello 2");
    }
}
