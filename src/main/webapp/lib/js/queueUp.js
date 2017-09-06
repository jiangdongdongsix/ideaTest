

$(function(){
        var common = {"id":id,"eatNumber":3,"seatNum":"56"};
        $.ajax({
            type:"get",
            url: ctx + "queue/update",
            data:common,
            success:function(data){
               console.log("");
            }
        })
})

