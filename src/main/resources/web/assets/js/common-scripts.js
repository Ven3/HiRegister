/*---LEFT BAR ACCORDION----*/
$(function () {
    $('#nav-accordion').dcAccordion({
        eventType: 'click',
        autoClose: true,
        saveState: true,
        disableLink: true,
        speed: 'slow',
        showCount: false,
        autoExpand: true,
//        cookie: 'dcjq-accordion-1',
        classExpand: 'dcjq-current-parent'
    });
});

var Script = function () {


//    sidebar dropdown menu auto scrolling

    jQuery('#sidebar .sub-menu > a').click(function () {
        var o = ($(this).offset());
        diff = 250 - o.top;
        if (diff > 0)
            $("#sidebar").scrollTo("-=" + Math.abs(diff), 500);
        else
            $("#sidebar").scrollTo("+=" + Math.abs(diff), 500);
    });


//    sidebar toggle

    $(function () {
        function responsiveView() {
            var wSize = $(window).width();
            if (wSize <= 768) {
                $('#container').addClass('sidebar-close');
                $('#sidebar > ul').hide();
            }

            if (wSize > 768) {
                $('#container').removeClass('sidebar-close');
                $('#sidebar > ul').show();
            }
        }

        $(window).on('load', responsiveView);
        $(window).on('resize', responsiveView);
    });

    $('.fa-bars').click(function () {
        if ($('#sidebar > ul').is(":visible") === true) {
            $('#main-content').css({
                'margin-left': '0px'
            });
            $('#sidebar').css({
                'margin-left': '-210px'
            });
            $('#sidebar > ul').hide();
            $("#container").addClass("sidebar-closed");
        } else {
            $('#main-content').css({
                'margin-left': '210px'
            });
            $('#sidebar > ul').show();
            $('#sidebar').css({
                'margin-left': '0'
            });
            $("#container").removeClass("sidebar-closed");
        }
    });

// custom scrollbar
    $("#sidebar").niceScroll({
        styler: "fb",
        cursorcolor: "#4ECDC4",
        cursorwidth: '3',
        cursorborderradius: '10px',
        background: '#404040',
        spacebarenabled: false,
        cursorborder: ''
    });

    $("html").niceScroll({
        styler: "fb",
        cursorcolor: "#4ECDC4",
        cursorwidth: '6',
        cursorborderradius: '10px',
        background: '#404040',
        spacebarenabled: false,
        cursorborder: '',
        zindex: '1000'
    });

// widget tools

    jQuery('.panel .tools .fa-chevron-down').click(function () {
        var el = jQuery(this).parents(".panel").children(".panel-body");
        if (jQuery(this).hasClass("fa-chevron-down")) {
            jQuery(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
        } else {
            jQuery(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
            el.slideDown(200);
        }
    });

    jQuery('.panel .tools .fa-times').click(function () {
        jQuery(this).parents(".panel").parent().remove();
    });


//    tool tips

    $('.tooltips').tooltip();

//    popovers

    $('.popovers').popover();


// custom bar chart

    if ($(".custom-bar-chart")) {
        $(".bar").each(function () {
            var i = $(this).find(".value").html();
            $(this).find(".value").html("");
            $(this).find(".value").animate({
                height: i
            }, 2000)
        })
    }


}();

function initDept() {

    $.get("/cgetdept", {deptid: 0}, function (data) {
        var depts = data.data;
        $.each(depts, function (index, content) {
            $("#deptid").append("<option value='" + content.deptid + "'>" + content.dname);
        });
    });

    $("#deptid").change(function () {
        $.get("/cgetdept", {deptid: $("#deptid").val()}, function (data) {
            $("#roomid").text("");
            $("#roomid").append("<option value='' selected>--请选择科室--</option>");
            var depts = data.data;
            $.each(depts, function (index, content) {
                $("#roomid").append("<option value='" + content.deptid + "'>" + content.dname);
            });
        });
        // fillDoctor($("#deptid").val());
    });

    $("#roomid").change(function () {
        fillDoctor($("#roomid").val());
    });

    function fillDoctor(deptid) {

        $.get("/cgetdoctors", {deptid: deptid}, function (data) {
            var doctors = data.data;
            $("#docpanel").text("");
            $.each(doctors, function (index, doctor) {
                var item =
                    "<div class=\"col-lg-3 col-md-6 col-sm-6 col-xs-12 mb\">\n" +
                    "                                        <!-- WHITE PANEL - TOP USER -->\n" +
                    "                                        <div class=\"white-panel pn\">\n" +
                    "                                            <div class=\"white-header\">\n" +
                    "                                                <h5>" + doctor.dept.dname + "</h5>\n" +
                    "                                            </div>\n" +
                    "                                            <p><img src='" + doctor.drinfo.picurl + "' class=\"img-rounded\" width=\"80\"></p>\n" +
                    "                                            <p><b>" + doctor.drinfo.realname + "</b></p>\n" +
                    "                                            <div class=\"row\">\n" +
                    "                                                <div class=\"col-lg-6 col-md-6 col-sm-6 col-xs-6\">\n" +
                    "                                                    <label>医龄</label>\n" +
                    "                                                    <div>" + doctor.jobage + "</div>\n" +
                    "                                                </div>\n" +
                    "                                                <div class=\"col-lg-6 col-md-6 col-sm-6 col-xs-6\">\n" +
                    "                                                    <label>选择</label>\n" +
                    "                                                    <div>\n" +
                    "                                                        <span></span>\n" +
                    "                                                        <input type=\"radio\" class=\"ez-radio\" required value='" + doctor.id + "'  name=\"drid\">\n" +
                    "                                                    </div>\n" +
                    "                                                </div>\n" +
                    "                                            </div>\n" +
                    "                                        </div>\n" +
                    "                                    </div>"
                ;

                $("#docpanel").append(item);
            });
        });
    };

}

function searchMedicine(type, value) {
    $.post("/findmedicine", {type: type, value: value}, function (data) {
        if (data.state == 200) {
            alert("查询失败！！");
        }
        $("#mdcContent").text("");
        var items = data.data;
        $.each(items, function (index, item) {

            var tr = "<tr>" +
                "<td>" + item.medicine.name + "</td>" +
                "<td><a href='/viewmedicine?mid="+item.mid+"'>"+item.mid+"</a> </td>" +
                "<td>" + item.medicine.type + "</td>" +
                "<td>" + item.medicine.producer.pdname + "</td>" +
                "<td>" + item.medicine.function + "</td>" +
                "<td>" + item.medicine.price + "</td>" +
                "<td>" + item.amount + "</td>"
                + "</tr>";
            $("#mdcContent").append(tr);
        });


    });
}
function initDate(birthdaydt) {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth() + 1;
    var day = today.getDate();
    if(month < 10){
        month = "0" + month;
    }
    if(day < 10){
        day = "0" + day;
    }
    var iniDate = year+"-"+month+"-"+day;
    $(birthdaydt).val(iniDate);
}
