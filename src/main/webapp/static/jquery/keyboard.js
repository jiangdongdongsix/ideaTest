/* 
* numkeyboard 1.0 
* Copyright (c) 2014 BowenLuo http://www.luobo.com/ 
* Date: 2014-06-08
    <ul>
        <li><input type="text" placeholder="手机号码后四位" id="numkeyboard1" class="numkeyboard" /></li>
        <li><input type="password"  placeholder="开箱密码"  id="numkeyboard2"  class="numkeyboard"/></li>        
    </ul>
*    Example:$(".numkeyboard").numkeyboard();
*/ 
(function($){ 
$.fn.numkeyboard = function(options){ 
var defaults = { 
    keyboardRadix:1000,//键盘大小基数
    mainbackground:'#C8BFE7', //主背景色
    menubackground:'#4A81B0', //头背景色
    exitbackground:'#4376A0', //关闭按钮背景色
    buttonbackground:'#ff730e', //键盘按钮背景色
    clickeve:false//是否绑定元素click事件
} 
var options = $.extend(defaults, options); 
var keyboardRadix = options.keyboardRadix;
if(keyboardRadix<300){
    keyboardRadix=300;
}
var numkeyboardcount = 0;
var activeinputele;
this.each(function(){     
    numkeyboardcount++;
    //添加唯一的数字键盘
    if(numkeyboardcount<2){
    keyBoardId = randomOnlyId();
    $("body").append("<div id='"+keyBoardId+"' class='auth_keybord'>"
    +"<div id='auth_keybord_exit' class='auth_keybord_exit'>关闭</div>"
    +"<div id='auth_keybord_menu' class='auth_keybord_menu'></div>"
    +"<ul class='number_list' id='number_list'>"
    +"<li><button type='button'>7</button></li>"
    +"<li><button type='button'>8</button></li>"
    +"<li><button type='button'>9</button></li>"
    +"<li><button type='button'>4</button></li>"
    +"<li><button type='button'>5</button></li>"
    +"<li><button type='button'>6</button></li>"
    +"<li><button type='button'>1</button></li>"
    +"<li><button type='button'>2</button></li>"
    +"<li><button type='button'>3</button></li>"
    +"<li><button type='button'>0</button></li>"                                
    +"<li><button type='submit'>清除</button></li>"
    +"<li><button type='submit'>退格</button></li>"
    +"</ul></div>");    
    }    
    //元素选择器
    var inputele = $(this);
    var keyboard = $("#"+keyBoardId+"");
    var keyboard_exit = keyboard.children('div:first');
    var keyboard_menu = keyboard.children('div:eq(1)');
    var keyboard_buttonul = keyboard.find('ul:first');
    var keyboard_buttonli = keyboard_buttonul.children('li');
    var keyboard_button = keyboard_buttonli.children('button');
    //元素css样式控制
    keyboard.css({"position":"absolute","z-index":"10","display":"none","background":options.mainbackground,overflow:"hidden",
            "width":keyboardRadix*0.6,"height":keyboardRadix*0.5,"border-radius":keyboardRadix*0.01});
    keyboard_exit.css({"position":"absolute","z-index":"1","right":"0","background":options.exitbackground,"cursor":"pointer","text-align":"center",
            "width":keyboardRadix*0.16,"height":keyboardRadix*0.081,"border-top-right-radius":keyboardRadix*0.01,"line-height":keyboardRadix*0.081+"px",
            "font-family":"'微软雅黑',arial","font-size":keyboardRadix*0.036+"px","corlor":"#000","letter-spacing":keyboardRadix*0.005});
    keyboard_menu.css({position:"relative",background:options.menubackground,cursor:"move",margin:"auto",
    width:keyboardRadix*0.6,height:keyboardRadix*0.081,"border-top-left-radius":keyboardRadix*0.01,"border-top-right-radius":keyboardRadix*0.01});
    keyboard_buttonul.css({position:"relative",margin:"auto","margin-top":keyboardRadix*0.03+"px",width:keyboardRadix*0.54,height:keyboardRadix*0.37});
    keyboard_buttonli.css({position:"relative",margin:"auto",overflow:"hidden","float":"left",width:keyboardRadix*0.18,height:keyboardRadix*0.09});
    var buttonborder = String(keyboardRadix*0.001+"px solid "+options.buttonbackground);
    keyboard_button.css({"position":"relative","float":"left", padding: "0","cursor":"pointer","background":options.buttonbackground,"text-align":"center",
            "width":keyboardRadix*0.16,"height":keyboardRadix*0.08,"border-radius":keyboardRadix*0.004,border:buttonborder,
            "line-height":keyboardRadix*0.08+"px",margin:"0 0 0 "+keyboardRadix*0.01+"px",
            "font-family":"'微软雅黑',arial","font-size":keyboardRadix*0.032+"px","color":"#fff"});
    keyboard_button.mousedown(function(event){
        $(this).css({background:"#666",top:"2px"});
        event.preventDefault();
    }).mouseup(function(){
        $(this).css({background:options.buttonbackground,top:"0"});
    });

    keyboard_exit.click(function(){
        exit(options.clickeve);
    });    
    inputele.focus(function(event){
        activeinputele = $(this);
        var p = GetScreenPosition(this);
        if(keyboard.css("display")=="none"){
            keyboard.css({"display":"block","left":p.x,"top":p.y+$(this).height()*1.2});
            mouseDrag();
            touchDrag();
        }});
        
    if(options.clickeve){
        inputele.click(function(){    
        activeinputele = $(this);
        var p = GetScreenPosition(this);
        if(keyboard.css("display")=="none"){
            keyboard.css({"display":"block","left":p.x,"top":p.y+$(this).height()*1.2});
            mouseDrag();
            touchDrag();
        }});
    }    
    if(numkeyboardcount<2){
        for(var i=0;i<keyboard_button.length;i++){
            numbuttonclick(i);
        }
    }        
    function randomOnlyId(){
        var tempRandom = String(Date.now());
        var tempRandomLength = tempRandom.length;
        tempRandom = tempRandom.substring(tempRandomLength-5,tempRandomLength-1);
        var randomId = "auth_keybord"+tempRandom;
        if($("#randomId").length>0){
            randomOnlyId()
        }else{
            return randomId;
        }
    }
    
    function getElem(id) {
      return document.getElementById(id);
    }
    
    function numbuttonclick(buttonnum){
        keyboard_buttonli.children('button:eq('+buttonnum+')').click(function(e){
            
            var buttontext = keyboard_buttonli.children('button:eq('+buttonnum+')').text();                
            if(buttontext/1){
                clickkey(buttontext/1);
            }else{
                if(buttontext=="0"){
                    clickkey(0);
                }
                if(buttontext=="退格"){
                    backspace();
                }
                if(buttontext=="清除"){
                    keyclear();
                }
            }                
        })
    }
    
    function keyclear(){
         activeinputele.val("");
    }
    function backspace(){
        var inputtext = activeinputele.val();
        if(inputtext.length>0){
            inputtext = inputtext.substring(0,inputtext.length-1);
            activeinputele.val(inputtext);
        }    
    }    
    function clickkey(num){
        var inputtext = activeinputele.val();
        inputtext = inputtext+num;        
         activeinputele.val(inputtext);
    }    
    
    function exit(){    
        keyboard.css({"display":"none"});
    }
    
    function GetScreenPosition(object) {
        var position = {};            
        position.x = object.offsetLeft;
        position.y = object.offsetTop;
        while (object.offsetParent) {
            position.x = position.x + object.offsetParent.offsetLeft;
            position.y = position.y + object.offsetParent.offsetTop;
            if (object == document.getElementsByTagName("body")[0]) {
                break;
            }
            else{
                object = object.offsetParent;
            }
        }
        return position;
    }
    
    function mouseDrag() {
        var moveEle = keyboard;
        var eventEle = keyboard_menu;
        var stx = etx = curX = sty = ety = curY = 0;
        var MAction = false;
        var eleLeft = +moveEle.css("left").split('px')[0];
        var eleTop = +moveEle.css("top").split('px')[0];
        eventEle.mousedown(function(event){
            MAction = true;
            stx = event.pageX;    
            sty = event.pageY;
            eleLeft = +moveEle.css("left").split('px')[0];
            eleTop = +moveEle.css("top").split('px')[0];
            event.preventDefault();
        }).mousemove(function(event){            
        if(MAction){
            var curX = event.pageX-stx;
            var curY = event.pageY-sty;                
            moveEle.css({"left":eleLeft+curX,"top":eleTop+curY});
            event.preventDefault();
        }});
        $("body").mouseup(function(event){
            stx = etx = curX = sty = ety = curY = 0;
            MAction = false;
                
        });
    }
    
   function touchDrag() {
        var moveEle = keyboard;
        var eventEle = keyboard_menu;
        var stx = sty = etx = ety = curX = curY = 0;
        var TAction = false;
        var eleLeft = +moveEle.css("left").split('px')[0];
        var eleTop = +moveEle.css("top").split('px')[0];
        
        eventEle.on("touchstart", function(event) { //touchstart
            var event = event.originalEvent;
            TAction = true;
            curX = curY = 0;
            // 元素当前位置
            eleLeft = +moveEle.css("left").split('px')[0];
            eleTop = +moveEle.css("top").split('px')[0];
            // 手指位置
            stx = event.touches[0].pageX;
            sty = event.touches[0].pageY;
        });
        eventEle.on("touchmove", function(event) {
            if(TAction){
            var event = event.originalEvent;
            event.preventDefault();
            curX = event.touches[0].pageX - stx;
            curY = event.touches[0].pageY - sty;
            //alert(eleLeft+"-"+gundongX);
            moveEle.css({"left":eleLeft+curX,"top":eleTop+curY});
            }
            
        });
        eventEle.on("touchend", function(event) {
            stx = etx = curX = sty = ety = curY = 0;
            MAction = false;
            
         });

        function getT3d(elem, ename) {
            var elem = elem[0];
            var str1 = elem.style.webkitTransform;
            if (str1 == "") return "0";
            str1 = str1.replace("translate3d(", "");
            str1 = str1.replace(")", "");
            var carr = str1.split(",");

            if (ename == "x") return carr[0];
            else if (ename == "y") return carr[1];
            else if (ename == "z") return carr[2];
            else return "";
        }
    }
}); 
}; 
})(jQuery);