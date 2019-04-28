var x = 150, y = 40, w = 42;
var start = 0;
var m = 0;
var func = [];
function init(id,path,funct){
    func = funct;
    initCanvas(id);
    var canvas = document.getElementById("c1");
    var canvas2 = document.getElementById("c2");
    var canvas3 = document.getElementById("c3");
    var ctx = canvas.getContext("2d");
    var ctx2 = canvas2.getContext("2d");
    var ctx3 = canvas3.getContext("2d");
    var img = new Image();
    var white = new Image();
    white.src = "./img/white.png"
//	for(var i=0;i<path.length;i++){
//		img.src=path[i];
//	}
    img.src=path[randomNum(0,path.length-1)];
    white.onload=function () {
        ctx3.drawImage(white,0,0,310,155)
    };
    img.onload = function(){
        ctx.drawImage(img, 0, 0,310,155);
        ctx2.drawImage(img, 0, 0,310,155);
        var blockWidth = w;
        var _y = y+ 2;// 滑块实际的y坐标
        var ImageData = ctx2.getImageData(x, _y, blockWidth, blockWidth);
        canvas2.width = blockWidth;
        ctx2.putImageData(ImageData, 0, _y)
    };
    initData();
    draw(ctx,'fill');
    draw(ctx2,'clip');
    draw(ctx3,'clip');
}

function initCanvas(id){
    var html ="<canvas id='c1' ></canvas>"+
    "<canvas id='c3' style='position: absolute;left:0px;' ></canvas>"+
        "<canvas id='c2' style='position: absolute;left:0px;' ></canvas>"+
        "<div>"+
        "<button id='b' onmousedown='down(event)' onmousemove='move(event)' onmouseup='up()' style='width: 50px;height: 25px;'>|||</button>"+
        "</div>";
    $("#"+id).html(html);
//	$("#c1").hide();
//	$("#c2").hide();
//	$("#c3").hide();
}
function initImg(path,img,img1){
    img.src=path;
    img1.onload=function () {
        ctx3.drawImage(img1,0,0,300,150)
    };
    img.onload = function(){
        ctx.drawImage(img, 0, 0,310,155);
        ctx2.drawImage(img, 0, 0,310,155);
        var blockWidth = w;
        var _y = y+ 2;// 滑块实际的y坐标
        var ImageData = ctx2.getImageData(x, _y, blockWidth, blockWidth);
        canvas2.width = blockWidth;
        ctx2.putImageData(ImageData, 0, _y)
    };
}
function initData(){
    var x1 = randomNum(1,5);
    var y1 = randomNum(0,2);
    x = x1*50;
    y = y1*50;
}

function draw(ctx,operation) {
    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.lineTo(x + w, y);
    ctx.lineTo(x + w, y + w);
    ctx.lineTo(x, y + w);
    ctx.fillStyle = '#fff';
    ctx[operation]();
}

function mouseIn(){
	$("#c1").show();
	$("#c2").show();
	$("#c3").show();
}

function down(e){
    start = e.pageX;
}
var distance = 0;
function move(e) {
    m = e.pageX;
    if(start != 0){
        var d =m-start;
        $("#b").css("margin-left",d);
        $("#c2").css("left",9+d);
        distance = $("#c2").css("left").split("p")[0];
        if(distance <=9 ){
            $("#c2").css("left",9);
            $("#b").css("margin-left",0);
        }
        if(distance >=309 ){
            $("#c2").css("left",250);
            $("#b").css("margin-left",250);
        }
    }
    // console.info($("#c2").css("left"))
}
function up() {
    if(distance <= (x+15) && distance >= (x-15)){
        func.success();
    }else{
        func.def();
    }
    start = 0;
    m=0;
}

function randomNum(minNum,maxNum){
    switch(arguments.length){
        case 1:
            return parseInt(Math.random()*minNum+1);
            break;
        case 2:
            return parseInt(Math.random()*(maxNum-minNum+1)+minNum);
            break;
        default:
            return 0;
            break;
    }
}