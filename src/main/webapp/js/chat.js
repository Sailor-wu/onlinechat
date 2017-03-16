/*nav*/
$("#nav li").each(function (index, item) {
    var self = $(this);
    self.find("a").on("click", function (e) {
        e.preventDefault();
        var href = $(this).attr("href");

        $(href).addClass("active").siblings().removeClass("active");
        self.find("i").addClass("active");
        self.siblings("li").find("i").removeClass("active");
    })
});

$(document).on("click", function () {
    $("#dropdown-list").removeClass("active");
});

$(".chat-add").on("click", function (e) {
    e.stopPropagation();
    var dropdownList = $("#dropdown-list");
    dropdownList.hasClass("active") ? dropdownList.removeClass("active") : dropdownList.addClass("active");
});