function csrf() {
    let req = new XMLHttpRequest();
    req.open('GET', document.location, false);
    req.send(null);
    let headers = req.getAllResponseHeaders().split("\n")
        .map(x => x.replace("\r", ""))
        .map(x => x.split(/: */, 2))
        .filter(x => x[0])
        .reduce((ac, x) => { ac[x[0]] = x[1]; return ac; }, {});
    return headers["x-csrf-token"];
}


//var items_total, itemsPerPage = 20, page_total, current_page = 1;
function pageSetting(items) {
    // 設定頁數
    items_total = $(items).length;
    page_total = Math.ceil(items_total / itemsPerPage);
    $("#total_page").text("/" + page_total);
    $("#item_total").text("共" + items_total + "個結果");


    $("#page").html("");
    for (let i = 1; i <= page_total; i++) {
        $("#page").append(`<option value="${i}">${i}</option>`);
    }

    $(".page-prev").on("click", function () {
        if ($("#page option:selected").val() > 1) {
            let temp = $("#page option:selected");
            temp.prev().attr("selected", true);
            temp.attr("selected", false);
            current_page -= 1;
            changePage(items, current_page);
        }
    })
    $(".page-next").on("click", function () {
        if ($("#page option:selected").val() < page_total) {
            let temp = $("#page option:selected");
            temp.next().attr("selected", true);
            temp.attr("selected", false);
            current_page += 1;
            changePage(items, current_page);
        }
    });

    reorderPage("#clock_list tr.clock_items");


    $("#page").on("change", function () {
        if (current_page != $(this).val()) {
            $("#page option:selected").attr("selected", false);
            $(this).attr("selected", true);
            window.scrollTo({ top: 0, behavior: 'smooth' });
            current_page = $(this).val();
            changePage(items, current_page);
        }
    })
}

function changePage(items, pageNum) {
    $(items).each(function (index, item) {
        if ($(item).attr("data-page") == pageNum) {
            $(item).show();
        } else {
            $(item).hide();
        }
    });
}

function reorderPage(items) {
    $(items).each(function (index, item) {
        $(item).attr("data-page", Math.floor(index / 20) + 1);
    });
}

function getEmployeeList() {
    let employee_list = {};
    //取得員工資料
    $.ajax({
        url: "http://localhost:8080/employees",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            for (item in data) {
                employee_list[data[item]["empNo"]] = data[item];
            }
            // console.log(employee_list);
        }
    });

    return employee_list;
}