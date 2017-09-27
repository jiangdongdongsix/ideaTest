

$(function () {

})

$(".queueInfo").click(function(){
    var command={"customerName":"22233"};
    $.ajax({
        type:"POST",
        url: ctx + "queue/virtualqueue",
        data:command,
        success:function(data){
           var dataObject = JSON.parse(data);
           console.log(dataObject);
           // location.href = ctx + "queue/lineup?queueId="+dataObject.id;
        }
    })

})