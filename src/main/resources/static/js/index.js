$(function(){
    if(localStorage.getItem("closeable") == "true"){
        localStorage.removeItem("closeable");
        window.close();
    }
})