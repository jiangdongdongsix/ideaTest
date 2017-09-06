

$(function () {

})

$(".queueInfo").click(function(){
    var command={"customerName":""};
    $.ajax({
        type:"POST",
        url: ctx + "queue/save",
        data:command,
        success:function(data){
           var dataObject = JSON.parse(data);
           location.href = ctx + "queue/lineup?queueId="+dataObject.id;
        }
    })
})