var ie = (document.all)?true:false;

window.onload = function (){

}
window.onunload=function (){

}

function loadScript(scriptURL){
    var newScript = document.createElement("script");
    newScript.setAttribute("type","text/javascript");
    document.body.appendChild(newScript);
    newScript.src = scriptURL;
} 

function loadStyle(fileURL){
    var headID = document.getElementsByTagName("head")[0];
    var cssNode = document.createElement('link');
    cssNode.type = 'text/css';  
    cssNode.rel = 'stylesheet';  
    cssNode.href = fileURL;
    cssNode.media = 'screen';  
    headID.appendChild(cssNode);
} 

function jTrim(strMsg){
    strMsg = strMsg+'';
    strNm = '';
    tamstr = strMsg.length;
    tot = tamstr;
    ileft = 0;
    iright = 0;
    while(strMsg.charAt(ileft) == ' '){
        ileft++;
    }
    if(ileft == tot)return '';
    while(strMsg.charAt(tamstr-1)== ' '){
        tamstr--;
        iright++;
    }
    strNm = strMsg.substring(ileft,tot-iright);
    return strNm;
}


//Função para converter data
function cDate(value,typ){
    var objd, art, arh, strd;
    var fr=1;
    var ard = value.split("/");
    if(ard.length == 3){
        if(isNaN(ard[0]) || (ard[0].length>2) || (ard[0]>31) || (ard[0]<1)) fr=0;
        if(isNaN(ard[1]) || (ard[1].length>2) || (ard[1]>12) || (ard[1]<1)) fr=0;
        if(ard[2].length <= 4){
            if((isNaN(ard[2])) || (ard[2]<0)) fr=0;
            objd = new Date();
            objd.setFullYear(parseInt(ard[2],10), parseInt(ard[1], 10)-1, parseInt(ard[0],10));
            strd = (parseInt(ard[0], 10)+"/"+parseInt(ard[1], 10)+"/"+parseInt(ard[2], 10));
        }else{
            art = ard[2].split(' ')
            if((isNaN(art[0])) || (art[0].length != 4) || (art[0]<0)) fr=0;
            arh = art[1].split(':')
            if((isNaN(arh[0])) || (arh[0].length>2) || (arh[0]>23) || (arh[0]<0)) fr=0;
            if((isNaN(arh[1])) || (arh[1].length>2) || (arh[1]>59) || (arh[1]<0)) fr=0;
            if(arh.length>=3){
                if(arh.length>3) fr=0;
                if((isNaN(arh[2])) || (arh[2].length>2) || (arh[2]>59) || (arh[2]<0)) fr=0;
                objd = new Date(parseInt(art[0],10), parseInt(ard[1], 10)-1, parseInt(ard[0],10), parseInt(arh[0],10), parseInt(arh[1],10), parseInt(arh[2],10));
                strd = (parseInt(ard[0], 10)+"/"+parseInt(ard[1], 10)+"/"+parseInt(art[0],10)+" "+parseInt(arh[0],10)+":"+parseInt(arh[1],10)+":"+parseInt(arh[2],10));
            }else{
                objd = new Date(parseInt(art[0],10), parseInt(ard[1], 10)-1, parseInt(ard[0],10), parseInt(arh[0],10), parseInt(arh[1],10),0);
                strd = (parseInt(ard[0], 10)+"/"+parseInt(ard[1], 10)+"/"+parseInt(art[0],10)+" "+parseInt(arh[0],10)+":"+parseInt(arh[1],10)+":0");
            }
        }
    }else{
        fr=0;
    }
    if(fr){
        switch(typ){
            case "date":
                return objd;
                break;
            case "string":
                return strd;
                break;
            case "type":
                if(ard[2].length == 4){
                return 'shortdate';
            }else{
                return 'longdate';
            };
            
            break;
        }
    }else{
        return null;
    }
}

//Função para validação de data
function isDate(value){
    var objd, strd, typd;
    var fr=1;
    objd=cDate(value,'date')
    strd=cDate(value,'string')
    typd=cDate(value,'type')
    if((isNaN(objd)) || (objd==null)) fr=0;
    if(strd==null) fr=0;
    if(typd==null) fr=0;
    if (fr){
        if(objd.getYear() < 1000){
            if(typd=='shortdate'){
                if((objd.getDate()+'/'+(objd.getMonth()+1)+'/'+(objd.getYear()+1900)) != strd)fr=0;
            }else{
                if((objd.getDate()+'/'+(objd.getMonth()+1)+'/'+(objd.getYear()+1900)+' '+objd.getHours()+':'+objd.getMinutes()+':'+objd.getSeconds()) != strd)fr=0;
            }
        }else{
            if(typd=='shortdate'){
                if((objd.getDate()+'/'+(objd.getMonth()+1)+'/'+objd.getYear()) != strd)fr=0;
            }else{
                if((objd.getDate()+'/'+(objd.getMonth()+1)+'/'+objd.getYear()+' '+objd.getHours()+':'+objd.getMinutes()+':'+objd.getSeconds()) != strd)fr=0;
            }
        }
    }
    return fr;
}

//Função para validação de número inteiro
function isInteger(value){
    if (isNaN(value))return 0;
    if (value!=parseInt(value,10)){
        return 0;
    }else{ 
        return 1;
    }
}

// Conversão de String para inteiro
function toInt(value){
    if (isInteger(value)){
        return parseInt(value,10);
    }else{
        return 0;
    }
}

//Função para validação de decimal
function isDecimal(value){
    while(value.indexOf('.')>0){
        value = value.replace('.','');
    }
    value = value.replace(',','');
    if (value.indexOf(',')>0){
        return 0;
    }else{
        if (isNaN(value))return 0;	
        if (value!=parseFloat(value,10)){
            return 0;
        }else{ 
            return 1;
        }
    }
}

// Conversão de String para decimal
function toFload(value){
    if (isDecimal(value)){
        while(value.indexOf('.')>0){
            value = value.replace('.','');
        }
        value = value.replace(',','.');
        return parseFloat(value,10);
    }else{
        return 0;
    }
}

// Conversão de decimal para string
function floatToString(value){
    value = value+''; 
    if (value=='')value = '0';
    if (value.indexOf('.')<0) {
        value = value+'.00';
    }else{
        dif = value.length - 1 - value.indexOf('.');
        if(dif==0){
            value = value+'00';
        } else if (dif==1){
            value = value+'0';
        } else if (dif>2){
            value = value.substring(0, (value.length - (dif-2)) );
        }
    }
    value = value.replace('.',',')
    aux = '';
    ct = 0;
    for (x=value.length-1; x>=0 ; x--){
        aux = value.charAt(x) + aux;
        if(x<(value.length-3)){
            if (ct==2){
                if (x>0)aux = '.' + aux;
                ct = 0;
            } else {
                ct++;
            }
        }
    }
    return aux;
}

// Função para selecionar um radio ou checkbox pelo label
function checkInput(obj, ct){
    var ln = obj;
    if (ct=="undefined" || ct==null){
        while(ln.nodeName!='TR'){
            ln = ln.parentNode;
        }
        ct=0;
    }
    for (var i = 0; i < ln.childNodes.length; i++) {
        if (ln.childNodes[i].nodeName=='TD'){
            checkInput(ln.childNodes[i], ct++);
        } else if (ln.childNodes[i].nodeName=='INPUT' && (ln.childNodes[i].type=='radio' || ln.childNodes[i].type=='checkbox')){
            if (ln.childNodes[i].type=='radio'){
                ln.childNodes[i].checked=true;
            }else{
                ln.childNodes[i].checked=(!ln.childNodes[i].checked);
            }
            break;
        }
    }
}


function formataMascara(o, m){
    var vl = o.value;
    var aux = '';
    for (x=0; x<vl.length; x++){
        if (x < m.length){
            if (m.charAt(x)!='N' && m.charAt(x)!='L' && m.charAt(x)!='A'){
                aux = aux + m.charAt(x);
                if (m.charAt(x)!=vl.charAt(x)){
                    m = m.substring(0, x) + m.substring(x+1, m.length);
                }
            } 
            //alert(m.charAt(x)+'-'+x+'-'+vl.charAt(x));
            if (m.charAt(x)=='N'){
                if ("0123456789".indexOf(vl.charAt(x)) >= 0)aux = aux + vl.charAt(x);
            } else if (m.charAt(x)=='L'){
                if ("QWERTYUIOPASDFGHJKLÇZXCVBNMqwertyuiopasdfghjklçzxcvbnmáéíóúâêîôûãõàèìòùÁÉÍÓÚÂÊÎÔÛÃÕÀÈÌÒÙ".indexOf(vl.charAt(x)) >= 0)aux = aux + vl.charAt(x);
            } else if (m.charAt(x)=='A'){
                if ("0123456789QWERTYUIOPASDFGHJKLÇZXCVBNMqwertyuiopasdfghjklçzxcvbnmáéíóúâêîôûãõàèìòùÁÉÍÓÚÂÊÎÔÛÃÕÀÈÌÒÙ".indexOf(vl.charAt(x)) >= 0)aux = aux + vl.charAt(x);
            }
        }
    }
    if (o.value!=aux)o.value = aux;
}

function formataMoeda(fld, milSep, decSep, e) {
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;

    if (whichCode == 13) return true;  // Enter
    if (whichCode == 8) return true;  // Delete
    key = String.fromCharCode(whichCode);  // Get key value from key code
    if (strCheck.indexOf(key) == -1) return false;  // Not a valid key
    len = fld.value.length;
    for(i = 0; i < len; i++)
        if ((fld.value.charAt(i) != '0') && (fld.value.charAt(i) != decSep)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(fld.value.charAt(i))!=-1) aux += fld.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) fld.value = '';
    if (len == 1) fld.value = '0'+ decSep + '0' + aux;
    if (len == 2) fld.value = '0'+ decSep + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += milSep;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        fld.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
            fld.value += aux2.charAt(i);
        fld.value += decSep + aux.substr(len - 2, len);
    }
    return false;
}

function getIframe(win) {
    // Loop through the window's IFRAME objects looking for a matching window object
    var iframes = document.getElementsByTagName("IFRAME");
    for (var i=0; i<iframes.length; i++) {
        var o = iframes.item(i);
        var w = null;
        if (o.contentWindow) {
            // For IE5.5 and IE6
            w = o.contentWindow;
        } else if (window.frames && window.frames[o.id].window) {
            w = window.frames[o.id];
        }
        if (w == win) {
            return o; 
        }
    }
    return null;
}

function clearSelection () { 
    if (document.selection) 
        document.selection.empty(); 
    else if (window.getSelection) 
        window.getSelection().removeAllRanges(); 
} 

function changeClass(obj, evt){
    if (obj.getAttribute('venabled')!=null && obj.getAttribute('venabled')==1){
        for (var i=0; i < obj.childNodes.length; i++){
            if (obj.childNodes[i].nodeName!='#text'){
                if (obj.childNodes[i].getAttribute(evt+'Class')!=null){
                    obj.childNodes[i].className = obj.childNodes[i].getAttribute(evt+'Class');
                }
                obj.childNodes[i].setAttribute('venabled', obj.getAttribute('venabled'));
                changeClass(obj.childNodes[i], evt);
            }
        }
    }
}

// Objeto de coleção
function CCollection() {
    /* --CCollection object-- */
    var lsize = 0;

    this.add = _add;
    this.remove = _remove;
    this.isEmpty = _isEmpty;
    this.size = _size;
    this.clear = _clear;
    this.getIdx = _getIdx;
    this.clone = _clone;

    function _add(newItem) {
        /* --adds a new item to the collection-- */
        if (newItem == null) return;
        lsize++;
        this[(lsize - 1)] = newItem;
    }

    function _remove(index) {
        /* --removes the item at the specified index-- */
        if (index < 0 || index > this.length - 1) return;
        this[index] = null;

        /* --reindex collection-- */
        for (var i = index; i <= lsize; i++)
            this[i] = this[i + 1];
        lsize--;
    }

    function _isEmpty() {
        return lsize == 0
    }     /* --returns boolean if collection is/isn't empty-- */

    function _size() {
        return lsize
    }     /* --returns the size of the collection-- */

    function _clear() {
        /* --clears the collection-- */
        for (var i = 0; i < lsize; i++)
            this[i] = null;
        lsize = 0;
    }

    function _getIdx(item) {
        for (var i = 0; i < lsize; i++){
            if (this[i]==item) return i;
        }
        return -1;
    }

    function _clone() {
        /* --returns a copy of the collection-- */
        var c = new CCollection();
        for (var i = 0; i < lsize; i++)
            c.add(this[i]);
        return c;
    }
}


// Objeto de domain (input)
function CDomain() {
    /* --CDomain object-- */
    var key;
    var value;
    var type;

    this.setKey = _setKey;
    this.getKey = _getKey;
    this.setValue = _setValue;
    this.getValue = _getValue;
    this.setType = _setType;
    this.getType = _getType;

    function _setKey(aKey) {
        key = aKey;
    }
    function _getKey() {
        return key;
    }

    function _setValue(aValue) {
        value = aValue;
    }
    function _getValue() {
        return value;
    }

    function _setType(aType) {
        type = aType;
    }
    function _getType() {
        return type;
    }
}

function jLink(url){
    self.location.href=url;
}

function focusTo(id){
    if ($Id(id)!=null && $(id)!=undefined){
        $Id(id).get(0).focus();
        $Id(id).get(0).focus();
    }
}

function replaceAll(string, token, newtoken) {
    while (string.indexOf(token) != -1) {
        string = string.replace(token, newtoken);
    }
    return string;
}

function fmtMoeda(o){
    var data = o.value;
    data = replaceAll(data, ".", "");
    if(data=='')data='0';
    if (data.indexOf(',')>-1){
        data = data.substring(0, data.indexOf(',')+3);
        if (data.length-2==data.indexOf(',')){
            data = data+'0';
        } else if (data.length-1==data.indexOf(',')){
            data = data+'00';
        }
    } else {
        data = data+',00';
    }
    var aux = '';
    var c = 0;
    data = data.replace('.','');
    for (var x = data.indexOf(',');x>0;x--){
        if (c==3){
            aux = '.' + aux;
            c=0;
        }
        aux = data.substring(x-1,x) + aux + '';
        c++;
    }
    o.value = aux+data.substring(data.indexOf(','),data.length);
}

function checkAll(partName, st, c1, c2){
    var ia = $('input[name|="'+partName+'"]');
    alert
    for (var x = 0; x < ia.length;x++){
        domEl = ia.get(x)
        domEl.checked = st;
        checkLine(domEl, c1, c2, domEl.checked);
    }
}

function checkLine(obj, bcolor, fcolor, bl){
    var ln;
    ln = obj;
    while(ln.nodeName!='TR'){
        ln = ln.parentNode;
    }
    if(bl){
        ln.setAttribute('sel','1');
        for (var i = 0; i < ln.childNodes.length; i++) {
            if(ln.childNodes[i].nodeType==1){
                col = ln.childNodes[i];
                //col.style.background=bcolor;
                //col.style.color=fcolor;
            }
        }
    }else{
        ln.setAttribute('sel','0');
        for (var x = 0; x < ln.childNodes.length; x++) {
            if(ln.childNodes[x].nodeType==1){
                col = ln.childNodes[x];
                //col.style.background=ln.style.background;
                //col.style.color=ln.style.color;
            }
        }
    }
}

var focusID;
function selectText(obj){
    if (obj.id != focusID){
        obj.select();
        focusID = obj.id;
    }
}

function modalchk(){
    for (var x=0; x < colModal.size(); x++){
        if (!colModal[x].closed)colModal[x].close();
    }
}
