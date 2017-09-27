

$(function(){
    var common = {"id":id,"eatNumber":3,"seatFlag":false};
    virtualQueue(common);
})


//确认排队
$(".keyborardRight").click(function () {
    var tel = $("#telNumber").val();
    $.ajax({
        type:"GET",
        url: ctx + "queue/confirmqueue?queueId="+id +"&tel="+tel,
        success:function(data){
            console.log(data);
            alert("排队成功");
            location.href= ctx + "queue/success";
        }
    })
})


//取消排队
$(".left").click(function(){
    $.ajax({
        type:"GET",
        url: ctx + "queue/cancel?queueId="+id,
        success:function(data){
            console.log("取消成功");
        }
    })
})


//虚拟排队
function virtualQueue(common){
    $.ajax({
        type:"POST",
        url: ctx + "queue/update",
        data:common,
        success:function(data){
            console.log(data);
        }
    })
}