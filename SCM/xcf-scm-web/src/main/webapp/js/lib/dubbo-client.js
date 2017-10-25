function getMethods(clz) {
    var version = $("#dubbo-version").val();
    $.ajax({
        type: "POST",url: "getMethods",
        data: {cls:clz,version:version},dataType: "json",
        success:function(msg){
            if(msg.length>0){
                var val = "";
                $("#div-methods").children().each(function () {
                    $(this).remove();
                });
                $.each(msg,function (idx,item) {
                    //console.log(item);
                    val += "<div><a href='#' onclick=javascript:getSignature('"+clz+"','"+item+"')>"+item+"</a></div>"
                });
                $("#div-methods").append(val);
                $("#cls-selected").val(clz);
            }else {
                initSignature();
            }
        },
        error:function(err,c){

        }
    });
}
function initSignature() {
    $("#div-methods").children().each(function () {
        $(this).remove();
    });
    $(".div-methods-detail").children().each(function () {
        $(this).remove();
    });
    $("#div-result-execute").children().each(function () {
        $(this).remove();
    });
    $("#mtd-selected").val('');
    $("#cls-selected").val('');
}
function getSignature(clz,method) {
    var version = $("#dubbo-version").val();
    $.ajax({
        type: "POST",url: "getSignature",
        data: {cls:clz,version:version,mtd:method},dataType: "json",
        success:function(msg){
            if(msg!=null){
                $(".div-methods-detail").children().each(function () {
                    $(this).remove();
                });
               //$(".div-methods-detail").innerText = "";
                $("#div-input-desc").append("<div>"+msg.input+"</div>");
                $("#div-input--detail-desc").append("<div>"+msg.inputDetail+"</div>");
                $("#div-output-desc").append("<div>"+msg.output+"</div>");

                $("#mtd-selected").val(method);
            }
        },
        error:function(err,c){
            $(".div-methods-detail").children().each(function () {
                $(this).remove();
            });
        }
    });
}
function getInterfaceList() {
    $.get($('#zk-form').attr('action'), $('#zk-form').serialize(), function(data){
        //alert('done');
        //console.log(data);
        //$('#packageForm').reset();
        var val = "";
        $("#interface").children().each(function () {
            $(this).remove();
        });
        $.each(data,function (idx,item) {
            //console.log(item);
            val += "<li data='"+item+ "'><a href='#' onclick=getMethods('"+item+"')>"+item+"</a></li>";
        });
        $("#interface").append(val);
    });
}
$(document).ready(function(){
    //$("#zk-form").submit();
    getInterfaceList();
    $(".btn-reload").click(function () {
       getInterfaceList();
    });
    $(".btn-execute").click(function () {
      var method = $("#mtd-selected").val();
      var clz = $("#cls-selected").val();
      var version = $("#dubbo-version").val();
      var group = $("#dubbo-group").val().trim();
      var input = $("#method-input").val().trim();

      $.ajax({
          type: "POST",url: "invoke",
          data: {cls:clz,version:version,mtd:method,input:input,group:group}/*,dataType: "json"*/,
          success:function(msg){
              $("#div-result-execute").children().each(function () {
                  $(this).remove();
              });
              //if(msg.length>0){
              console.log(msg);
              var val = "";
              /*$.each(msg,function (idx,item) {
                  //console.log(item);
                  val += item.toString();
              });*/
              if(msg!=null){
                  //val = JSON.parse(msg);
                  //val = $.parseJSON(msg);
                  val = msg.toString();
              }
              $("#div-result-execute").append("<div>"+val+"</div>");
          },
          error:function(err,c){
              $("#div-result-execute").children().each(function () {
                  $(this).remove();
              });
              $("#div-result-execute").append("<div>"+err.responseText+"</div>");
          }
      });
  }) ;
});