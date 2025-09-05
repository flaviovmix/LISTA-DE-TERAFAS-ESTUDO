
var inputData = ''; //{"__method":"ajax"';
var debugCall = false;

var jci = new CCollection();
var activeAjax = new CCollection();

var activeAjaxRequest = 0;
var lastParse = "";

function $Id(id){
    if (document.getElementById(id)!=null && document.getElementById(id)!=undefined){
        return $('#'+id);
    } else {
        return undefined;
    }
}

function clearInput(){
    if(debugCall)alert("clearInput()");
    jci = new CCollection();
    input("__method", "ajax");
}

function input(k, v){
    if(debugCall)alert("input()");
    while (v.indexOf("\\")>=0){
        v = v.replace("\\","_char(134)_");
    }
    v = v.replace(/"/g,'\\"');
    v = v.replace(/(\r\n|\n|\r)/gm,"\\n");
    var i=null;
    for (var x=0; x<jci.size(); x++){
        i = jci[x];
        if (i.getKey()==k){
            break;
        } else {
            i=null;
        }
    }
    if (i==undefined){
        i = new CDomain();
        i.setKey(k);
        i.setValue(jTrim(v));
        jci.add(i);
    } else {
        i.setType("multiple");
        if (i.getValue().indexOf("[")>=0){
            i.setValue('['+i.getValue().substring(1, i.getValue().length-1)+', "'+jTrim(v)+'"]');
        } else {
            i.setValue('["'+i.getValue()+'", "'+jTrim(v)+'"]');
        }
    }
}

function parseInput(){
    try {
        var inputData='{';
        if (jci.size()==0)input("__method", "ajax");
        for (var x=0; x<jci.size(); x++){
            i = jci[x];
            if (inputData.length>1)inputData+=", ";
            if (i.getType()=="multiple"){
                inputData+='"'+i.getKey()+'":'+i.getValue();
            } else {
                inputData+='"'+i.getKey()+'":"'+i.getValue()+'"';
            }
        }
        inputData+='}';
        lastParse = inputData;
        if(debugCall)alert(inputData);
        return JSON.parse(inputData);
    } catch (err) {
        alert('Erro na leitúra do formulário, informe ao suporte do sistema.\n'+err);
        return null;
    }
}

function ajaxURL(url, closeModal, protectURL, lck){
    ajaxURL(url, closeModal, protectURL, 1);
}

function ajaxURL(url, closeModal, protectURL, lck){
    var i = parseInput();
    if(closeModal)closeAllFrameModal(0);
    ajaxURL2(url, i, protectURL, lck);
}

function ajaxURL2(url, ji, protectURL, lck){
    var activeIdx = activeAjax.getIdx(url);
    if (url!=null && ji!=null && url!='null' && ((activeIdx<0 && protectURL==1) || protectURL!=1)){

        // Launch AJAX request.
        $.ajax(
        {
            url: url,
            type: "POST",  
            data: ji,
            dataType: "json",
				
            error: function (request, status, error) {
                alert("AJAX - error("+url+","+status+","+error+","+request.responseText+")");
                clearInput();
                if($Id('ld-xt')!=null && $Id('ld-xt')!=undefined){
                    if(lck==1)$Id('ld-xt').hide();
                }
                var idx = activeAjax.getIdx(url);
                activeAjax.remove(idx);
                activeAjaxRequest--;
            },
            
            beforeSend: function(){
                if(debugCall){
                    alert("Load - "+url);
                    console.log("Load - "+url);
                }
                if (protectURL==1)activeAjax.add(url);
                clearInput();
                if($Id('ld-xt')!=null && $Id('ld-xt')!=undefined){
                    if(lck==1)$Id('ld-xt').show();
                }
                if(debugCall)alert('Iniciada chamada ajax! URL = '+url);
                activeAjaxRequest++;
            },
						
            complete: function(msg){
                if($Id('ld-xt')!=null  && $Id('ld-xt')!=undefined && activeAjaxRequest==0){
                    if(lck==1)$Id('ld-xt').hide();
                } 
                if(debugCall)alert('Finalizada chamada ajax!');
            },
						
            success: function( strData ){
                activeAjaxRequest--;
                var response = strData;
                try {
                    if (response==null){
                        var idx = activeAjax.getIdx(url);
                        activeAjax.remove(idx);
                        if(debugCall)alert("UnLoad - "+url);
                    } else {
                        if(debugCall)alert(response);
                        if(debugCall)alert(response.idx);
                        if (response.idx==1){
                            takeAction(response.data, activeAjax.getIdx(url));
                        } else {
                            for (var x=0;x<response.data.length;x++){
                                if(debugCall)alert("Success2! \n\n" + response.idx + '=' + response.data[x] + "=" + x);
                                takeAction(response.data[x], activeAjax.getIdx(url));
                            }
                        }
                        if (jTrim(response.callBack)!=''){
                            if (response.callBack.indexOf(";")>0){
                                var js = response.callBack.split(";");
                                for (var x=0;x<js.length;x++){
                                    if (jTrim(js[x])!='')eval(js[x]+";");
                                }
                            } else {
                                eval(response.callBack);
                            }
                        }
                        if($Id('ld-xt')!=null && $Id('ld-xt')!=undefined){
                            if(lck==1)$Id('ld-xt').hide();
                        }
                        var idx = activeAjax.getIdx(url);
                        activeAjax.remove(idx);
                        if(debugCall)alert("UnLoad - "+url);    
                    }
                } catch (err) {
                    alert('Ajax transport error, informe ao suporte do sistema.\n'+err);
                    var idx = activeAjax.getIdx(url);
                    activeAjax.remove(idx);
                }
            }
        }							
        );         
        
    } else if (activeIdx>=0){
        //if(lck==1)alert("A requisição já está em andamento, aguarde!");
    }
}

function takeAction(json, idx){
    if(debugCall)alert(json.action+'-'+json.code+"-"+json.id+"-"+json.html);
    if (json.target=='__PARENT'){
        json.target='';
        parent.takeAction(json);
    } else {
        if (json.action=='__EVAL'){
            //alert(json.code);
            try {
                var a = eval(json.code);
            } catch(err) {
                alert('Ajax script error, informe ao suporte do sistema : '+err.message + ' - ' + json.code);
                activeAjax.remove(idx);
            //alert(json.code);
            }
        } else if (json.action=='__UPDATE'){
            try {
                if ($Id(json.id)!=null) {
                    var st = $Id(json.id).scrollTop();
                    $Id(json.id).html('carregando...');
                    $Id(json.id).html(json.html);
                    if (st>0)$Id(json.id).scrollTop(st);
                }
                //else alert('Referência de objeto não encontrada ID :'+json.id+', comunique o suporte do sistema.');
            } catch(err) {
                alert('Ajax update error, informe ao suporte do sistema. ERROR: '+err+" - ID : "+json.id);
                activeAjax.remove(idx);
            }
        }   
    }
}

function copyToClipboard (text) { 
    window.prompt ("Copy to clipboard: Ctrl+C, Enter", text); 
}

var kAlive = false;

function stopKeepAlive(){
    kAlive = false;
}

function alive(){
    var str = 'Alive!';
    console.log(str)
}

function keepAlive(){
    kAlive = true;
    updateKeepAlive();
}

function updateKeepAlive(){
    if (kAlive){
        ajaxURL('KeepAlive.jsap', 0, 0);
        setTimeout('updateKeepAlive()', 50000);
    }
}

var URLCheck = false;
var activeURL = "";
var checkInterval = 30;

function startActiveURL(url, t){
    URLCheck = true;
    activeURL = url;
    checkInterval = t;
    checkURL();
}

function checkURL(){
    if (URLCheck){
        ajaxURL(activeURL,0, 0);
        setTimeout('checkURL()', 500*checkInterval);
    }
}

function stopActiveURL(){
    URLCheck = false;
}

function submitUpload(frm, url, progressURL, validate){
    var submitData=checkForm(validate);
    if(submitData){
        var fr = $Id(frm);
        fr.attr('action', url);
        fr.attr('target', 'IFR_UPLOAD');
        fr.get(0).submit();
        startActiveURL(progressURL, 1);
    } else {
        return false;
    }
}

jasapPage = {};
