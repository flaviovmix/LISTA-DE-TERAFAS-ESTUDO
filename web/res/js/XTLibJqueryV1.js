var colModal = new CCollection();
var colFramesModal = new CCollection();

var Client = {
    viewportWidth: function() {
        return self.innerWidth || (document.documentElement.clientWidth || document.body.clientWidth);   
    },   
    viewportHeight: function() {
        return self.innerHeight || (document.documentElement.clientHeight || document.body.clientHeight);
    },
    viewportSize: function() {
        return { 
            width: this.viewportWidth(), 
            height: this.viewportHeight() 
        };   
    } 
}; 

function closeAllFrameModal(){
    closeAllFrameModal(1);
}

function closeAllFrameModal(executeOnclose){
    if (colFramesModal.size()>0){
        var fm = colFramesModal[0];
        if(fm.getAttribute("oncloseURL")!=null && fm.getAttribute("oncloseURL")!='null' && fm.getAttribute("oncloseURL")!='' && fm.getAttribute("oncloseURL")!=undefined && executeOnclose){
            $Id('ld-xt').show();
            var i = parseInput();
            ajaxURL2(fm.getAttribute("oncloseURL"), i, 1, 1)
        }
        document.body.removeChild(fm);
        colFramesModal.remove(0);
        fm = null;
    }
    if (colFramesModal.size()>0)closeAllFrameModal(executeOnclose);
}

function stretchModal(id, t, l){

    var fm = document.getElementById(id);
    var w = Client.viewportWidth()-l;
    var h = Client.viewportHeight()-t;
    
    fm.setAttribute('ft',t);
    fm.setAttribute('fl',l);
    fm.setAttribute('fw',w);
    fm.setAttribute('fh',h);
    
    fm.style.top = t;
    fm.style.left = l;
    fm.style.width = w;
    fm.style.height = h;
}

function ifModal(id, src, w, h, t, l, type, move, mt, ml, oncloseURL, block, bw, bh, bt, bl, btype){
   
   var eld = $("#content");
   
    if (eld.prev().is('nav')){
        t = 62;
        l = toInt(eld.css("left").replace('px',''));
    }

    var fm = document.getElementById(id);
    var createF = 0;
    if (fm==undefined || fm==null){
        var fm = document.createElement("IFRAME");
        createF = 1;
    }
    
    fm.style.position = 'absolute';
    fm.style.zIndex = 10;
    fm.frameBorder = 0;
    fm.scrolling = 'No';
    fm.src = src;
    
    fm.setAttribute('vindex',colFramesModal.size());
    fm.id = id;
    if (oncloseURL!=null && oncloseURL!='null' && oncloseURL!='' && oncloseURL!=undefined){
        fm.setAttribute("oncloseURL", oncloseURL);
    }

    fm.setAttribute('ftype',type);
    fm.setAttribute('ft',t);
    fm.setAttribute('fl',l);
    fm.setAttribute('fh',h);
    fm.setAttribute('fw',w);

    if (type=='center'){
        t = (((((Client.viewportHeight()+document.body.scrollTop)/2)-(h/2))+t));
        if (eld.prev().is('nav')){
            var cd = (eld.width()/2);
            var cf = (w/2);
            l = (l+cd-cf);
        } else {
            l = (((((Client.viewportWidth()+document.body.scrollLeft)/2)-(w/2))+l));
        }
    } else if (type=='auto'){
        if (eld.prev().is('nav')){
            w = eld.width();
        } else {
            w = (Client.viewportWidth()-l);
        }
        h = (Client.viewportHeight()-t);
    } 
    
    //fm.style.border = "1px solid red";
    fm.style.top = t;
    fm.style.left = l;
    fm.style.width = w;
    fm.style.height = h;
    
    fm.height = h;

    if (createF==1){
        document.body.appendChild(fm);
        colFramesModal.add(fm);
    }
}

function hideModal(){
    for (var x=0; x < colFramesModal.size(); x++){
        var fm = colFramesModal[x];
        if(fm!=undefined)fm.style.visibility='hidden';
    }
}

function unhideModal(){
    for (var x=0; x < colFramesModal.size(); x++){
        var fm = colFramesModal[x];
        if(fm!=undefined)fm.style.visibility='visible';
    }
}

function downFile(src){
    var fm = document.createElement("IFRAME");
    fm.style.position = 'absolute';
    fm.style.top = 0;
    fm.style.left = 0;
    fm.style.width = 0;
    fm.style.height = 0;
    fm.src = src;
    fm.frameBorder = 0;
    fm.scrolling = 'No';
    fm.id = '__DOWNFILEFRAME';
    fm.name = '__DOWNFILEFRAME';
    document.body.appendChild(fm);
}

function jModal(name, url, width, height){
    var wmodal = window.open(url, name, 'dependent=yes,hotkeys=no,titlebar=no,z-lock=yes,alwaysRaised=no,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=auto,width='+width+',height='+height+',top=' + ((screen.height/2)-30-(height/2)) + ',left=' + ((screen.width/2)-10-(width/2)) );
    colModal.add(wmodal);
    wmodal.focus();
}

function jModalAuto(name, url){
    jModalAuto(name, url, true);
}

function jModalAuto(name, url, linked){

    //alert(navigator.appName); 
    //alert(screen.width+' - '+screen.height+' - prototype:'+window.screen.availWidth+' - '+window.screen.availHeight);
    width = (screen.width-20);
    height = (screen.height-80);

    if (screen.width<1050) width = (screen.width-20);
    else if (screen.width>1400) width = (screen.width-20)
    else width = (screen.width-20);

    if (screen.height<800) height = 690;
    else if (screen.height>900) height = (screen.height-100)
    else height = (screen.height-60);

    if (navigator.appName=='Netscape'){
        l = ((screen.width/2)-6-(width/2));
        t = 0;
    } else {
        l = ((screen.width/2)-6-(width/2));
        t = ((screen.height/2)-40-(height/2));
    }
    height = window.screen.availHeight - 70;
    
    //alert('resizable=yes,maximized=yes,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=auto,top=' + t + ',left=' + l + ',width='+width+',height='+height);

    //var wmodal = window.open(url, name, 'dependent=yes,hotkeys=no,titlebar=no,z-lock=yes,alwaysRaised=no,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=auto,width='+width+',height='+height+',top=' + t + ',left=' + l );
    var wmodal = window.open(url, name, 'resizable=yes,maximized=no,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=auto,width='+width+',height='+height+',top=' + t + ',left=' + l );
    if (linked)colModal.add(wmodal);
    
    wmodal.focus();
}

function jWindow(name, url, width, height){
    jWindow(name, url, width, height, true);
}

function jWindow(name, url, width, height, linked){
    var wsys;
    if (width>900){
        wsys = window.open(url, name, 'resizable=yes,maximized=yes,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=auto,width='+width+',height='+height+',top=' + (parseInt((screen.height/2),10)-39-parseInt((height/2),10)) + ',left=' + (parseInt((screen.width/2),10)-5-parseInt((width/2),10)));
    } else {
        wsys = window.open(url, name, 'resizable=yes,maximized=yes,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=auto,width='+width+',height='+height+',top=' + (parseInt((screen.height/2),10)-35-parseInt((height/2),10)) + ',left=' + (parseInt((screen.width/2),10)-6-parseInt((width/2),10)));
    }
    if (linked)colModal.add(wsys);
    wsys.focus();
}

function closeModal(){
    closeAllFrameModal(0);
    parent.closeAllFrameModal(1);
    if(top.opener!=undefined)top.window.close();
}

function jscrollTop(ft, x){ 
    $Id(ft).get(0).contentWindow.scrollTo(x,0);
}  
function jscrollLeft(fl, y){ 
    $Id(fl).get(0).contentWindow.scrollTo(0,y);
}  
