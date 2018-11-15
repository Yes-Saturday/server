$(document).keydown(function (event) {
    if (!event.ctrlKey || event.keyCode !== 68)
        return;

    var pathname = window.location.pathname;
    var infoStr = "/info/";

    if (pathname.length <= infoStr.length)
        return;

    if (pathname.indexOf(infoStr) != 0)
        return;

    var blogId = pathname.substring(6);

    $.ajax({
        url : "/favorite",
        method : "POST",
        data : {'blogId':blogId}
    });
});