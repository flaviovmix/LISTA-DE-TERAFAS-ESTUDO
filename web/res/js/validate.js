
function ajaxSubmit(action, validate, closeModal, protectURL, lck){
    if(lck==1 && $Id('ld-xt')!=undefined)$Id('ld-xt').show();
    
    var isCheck=checkForm(validate);
    var oInputs = document.getElementsByTagName('input');
    var oSelect = document.getElementsByTagName('select');
    var oTextarea = document.getElementsByTagName('textarea');
    
    if(isCheck){
        for (var x = 0; x < oInputs.length; x++) {
            oInputs[x].disabled = false;
            if (oInputs[x].getAttribute('vsubmit')==1){
                switch(oInputs[x].type){
                    case "radio":
                        if (oInputs[x].checked)input(oInputs[x].name, oInputs[x].value);
                        break;
                    case "checkbox":
                        if (oInputs[x].checked)input(oInputs[x].name, oInputs[x].value);
                        break;
                    default:
                        input(oInputs[x].name, oInputs[x].value);
                        break;
                }
            }
        }
        for (var y = 0; y < oSelect.length; y++) {
            for (var i = 0; i < oSelect[y].length; i++) {
                if (oSelect[y].getAttribute('vsubmit')==1){
                    if(oSelect[y][i].selected)input(oSelect[y].name, oSelect[y][i].value);
                }
            }
        }
        for (var z = 0; z < oTextarea.length; z++) {
            if(oTextarea[z].getAttribute('vsubmit')==1)input(oTextarea[z].name,oTextarea[z].value);
        }
        ajaxURL(action, closeModal, protectURL, lck);
    }
}

function formDataUpload(formData){
    //var isCheck=checkForm(validate);

}

function checkForm(validate){
    var submitData=1;
    var oInputs = document.getElementsByTagName('input');
    var oSelect = document.getElementsByTagName('select');
    var oTextarea = document.getElementsByTagName('textarea');
    if (validate){
        for (var x = 0; x < oInputs.length; x++) {
            if (!oInputs[x].disabled){
                if (oInputs[x].getAttribute('vsubmit')==1){
                    switch(oInputs[x].type){
                        case "radio":
                            submitData=vCheck(oInputs[x],x);
                            break;
                        case "checkbox":
                            submitData=vCheck(oInputs[x],x);
                            break;
                        default:
                            submitData=vText(oInputs[x]);
                            break;
                    }
                }
            }
            if(!submitData){
                vEnable(oInputs);
                vMessage(oInputs[x]);
                if($Id('ld-xt')!=undefined)$Id('ld-xt').hide();                
                break;
            }
            
        }
        if(submitData){
            for (var y = 0; y < oSelect.length; y++) {
                if(oSelect[y].getAttribute('vsubmit')==1){
                    switch(oSelect[y].type){
                        case "select-one":
                            submitData=vSelect(oSelect[y]);
                            break;
                        case "select-multiple":
                            submitData=vSelect(oSelect[y]);
                            break;
                    }
                }
                if(!submitData){
                    vEnable(oSelect);
                    vMessage(oSelect[y]);
                    break;
                }
            }
        }
        if(submitData){
            for (var z = 0; z < oTextarea.length; z++) {
                if(oTextarea[z].getAttribute('vsubmit')==1){
                    submitData=vText(oTextarea[z]);
                }
                if(!submitData){
                    vEnable(oTextarea);
                    vMessage(oTextarea[z]);
                    break;
                }
            }
        }
        vEnable(oInputs);
        vEnable(oSelect);
        vEnable(oTextarea);
    }
    return submitData;
}

//Validação de input radio, checkbox
function vCheck(obj, pos){
    var oInputs = document.getElementsByTagName('input');
    var selected=false;
    for (var i = pos; i < oInputs.length; i++) {
        if ((!oInputs[i].disabled) && (oInputs[i].name==obj.name) && (oInputs[i].type==obj.type)){
            selected=(oInputs[i].checked)?true:selected;
            oInputs[i].disabled = true;
	}
    }
    if ((!selected) && (obj.getAttribute('vrequired')=='1')){
        obj.setAttribute('vmessage','O Campo '+obj.getAttribute('vdescription')+' é obrigatório.');
        if($Id('ld-xt')!=null && $Id('ld-xt')!=undefined){
            $Id('ld-xt').hide();
        }
        return 0;
    }else{
        return 1;
    }
}

//Validação de input select-one, select-multiple
function vSelect(obj){
    var selected=false;
    obj.disabled = true;
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].value!=obj.getAttribute('vselect')){
            selected=(obj[i].selected)?true:selected;
        }
    }
    if ((!selected) && (obj.getAttribute('vrequired')=='1')){
        obj.setAttribute('vmessage','O Campo '+obj.getAttribute('vdescription')+' é obrigatório.');
        if($Id('ld-xt')!=null && $Id('ld-xt')!=undefined){
            $Id('ld-xt').hide();
        }
        return 0;
    }else{
        return 1;
    }
}

//Validação de input text, file, hidden, password
function vText(obj){

    var vreturn=1;
    var aux='';
    obj.disabled = true;

    //Obrigatoriedade de preenchimento
    if((jTrim(obj.value)=='') && (obj.getAttribute('vrequired')=='1')){
        obj.setAttribute('vmessage','O Campo '+obj.getAttribute('vdescription')+' é obrigatório.')
        if($Id('ld-xt')!=null && $Id('ld-xt')!=undefined){
            $Id('ld-xt').hide();
        }
	vreturn=0;
    }
    
    //Verificações para tipo de dado data
    if(((obj.getAttribute('vdatatype')=='shortdate')||(obj.getAttribute('vdatatype')=='longdate')) && (vreturn) && (jTrim(obj.value)!='')){
        if(!isDate(obj.value)){
            if(obj.getAttribute('vformat')!=null)aux='\nInforme uma data válida no formato "'+obj.getAttribute('vformat')+'"';
            obj.setAttribute('vmessage','A data informada no campo '+obj.getAttribute('vdescription')+' é inválida.'+aux)
            vreturn=0;
        }
    }

    //Verificações para tipo de dado hora
    if((obj.getAttribute('vdatatype')=='time') && (vreturn) && (jTrim(obj.value)!='')){
        if(!isDate('23/01/1975 '+obj.value)){
            if(obj.getAttribute('vformat')!=null)aux='\nInforme uma hora válida no formato "'+obj.getAttribute('vformat')+'"';
            obj.setAttribute('vmessage','A hora informada no campo '+obj.getAttribute('vdescription')+' é inválida.'+aux)
            vreturn=0;
        }
    }

    //Verificações para tipo de dado inteiro
    if((obj.getAttribute('vdatatype')=='integer') && (vreturn) && (jTrim(obj.value)!='')){
        if(!isInteger(obj.value)){
            obj.setAttribute('vmessage','O valor do campo '+obj.getAttribute('vdescription')+' é inválido.\nInforme um número inteiro.')
            vreturn=0;
        }
    }

    //Verificações para tipo de dado decimal
    if((obj.getAttribute('vdatatype')=='decimal') && (vreturn) && (jTrim(obj.value)!='')){
        if(!isDecimal(obj.value)){
            obj.setAttribute('vmessage','O valor do campo '+obj.getAttribute('vdescription')+' é inválido.\nInforme um dado numérico.')
            vreturn=0;
        }
    }

    //Verificações para tipo de dado CPF
    if((obj.getAttribute('vdatatype')=='cpf') && (vreturn) && (jTrim(obj.value)!='')){
        if(!isCpf(obj.value)){
            obj.setAttribute('vmessage','O CPF informado no campo '+obj.getAttribute('vdescription')+' é inválido.')
            vreturn=0;
        }
    }

    //Verificações para tipo de dado CNPJ
    if((obj.getAttribute('vdatatype')=='cnpj') && (vreturn) && (jTrim(obj.value)!='')){
        if(!isCnpj(obj.value)){
            obj.setAttribute('vmessage','O CNPJ informado no campo '+obj.getAttribute('vdescription')+' é inválido.')
            vreturn=0;
	}
    }

    //Verificações para range
    if((obj.getAttribute('vmin')!=null) && (obj.getAttribute('vmax')!=null) && (vreturn) && (jTrim(obj.value)!='')){
        vreturn=vRange(obj);
    }

    //Verificações para min
    if((obj.getAttribute('vmin')!=null) && (vreturn) && (jTrim(obj.value)!='')){
        vreturn=vMin(obj);
    }

    //Verificações para comparacao
    if((obj.getAttribute('vcompare')!=null) && (obj.getAttribute('vrule')!=null) && (vreturn)){
        var oInputs = document.getElementsByTagName('input');
        var found = 0;
        for (var x = 0; x < oInputs.length; x++) {
            if (oInputs[x].name==obj.getAttribute('vcompare') && oInputs[x].type==obj.type){
                vreturn=vCompare(obj, oInputs[x]);
                found = 1;
                break;
            }
        }
        if (!found){
            obj.setAttribute('vmessage','Falha na validação do campo '+obj.getAttribute('vdescription')+', não foi possível encontrar a referência para comparação.')
            vreturn=0;
        }
    }

    //Verificações do número mínimo de caracteres 
    if((obj.getAttribute('vminlength')!=null) && (vreturn) && (jTrim(obj.value)!='')){
        if (isInteger(obj.getAttribute('vminlength'))){
            if (obj.value.length<obj.getAttribute('vminlength')){
                obj.setAttribute('vmessage','O número de caracteres do campo '+obj.getAttribute('vdescription')+' deve ser maior que '+obj.getAttribute('vminlength')+'.')
		vreturn=0;
            }
	}
    }

    //Verificações do número máximo de caracteres 
    if((obj.getAttribute('maxlength')!=null) && (vreturn) && (jTrim(obj.value)!='')){
        if (isInteger(obj.getAttribute('maxlength'))){
            if (obj.value.length>obj.getAttribute('maxlength')){
                obj.setAttribute('vmessage','O número de caracteres do campo '+obj.getAttribute('vdescription')+' deve ser menor que '+obj.getAttribute('maxlength')+'.')
		vreturn=0;
            }
        }
    }
	
    return vreturn;
}

//Função de Comparação
function vCompare(obj1, obj2){

    if (obj1.getAttribute('vdatatype')!=obj2.getAttribute('vdatatype')){
        obj1.setAttribute('vmessage','O tipo de dado do campo '+obj1.getAttribute('vdescription')+' deve ser igual ao do campo '+obj2.getAttribute('vdescription')+'.')
	return 0;
    } else {

        var v1=vConvert(obj1.value, obj1.getAttribute('vdatatype'));
        var v2=vConvert(obj2.value, obj2.getAttribute('vdatatype'));

	switch(obj1.getAttribute('vrule')){
            case "==":
                if (v1!=v2){
                    obj1.setAttribute('vmessage','O valor do campo '+obj1.getAttribute('vdescription')+' deve ser igual ao do campo '+obj2.getAttribute('vdescription')+'.')
                    return 0;
		}break;
            case ">=":
                if (v1<v2){
                    obj1.setAttribute('vmessage','O valor do campo '+obj1.getAttribute('vdescription')+' deve ser maior ou igual ao do campo '+obj2.getAttribute('vdescription')+'.')
                    return 0;
		}break;
            case "<=":
                if (v1>v2){
                    obj1.setAttribute('vmessage','O valor do campo '+obj1.getAttribute('vdescription')+' deve ser menor ou igual ao do campo '+obj2.getAttribute('vdescription')+'.')
                    return 0;
		}break;
            case ">":
                if (v1<=v2){
                    obj1.setAttribute('vmessage','O valor do campo '+obj1.getAttribute('vdescription')+' deve ser maior ao do campo '+obj2.getAttribute('vdescription')+'.')
                    return 0;
		}break;
            case "<":
                if (v1>=v2){
                    obj1.setAttribute('vmessage','O valor do campo '+obj1.getAttribute('vdescription')+' deve ser menor ao do campo '+obj2.getAttribute('vdescription')+'.')
                    return 0;
                }break;
            case "!=":
                if (v1==v2){
                    obj1.setAttribute('vmessage','O valor do campo '+obj1.getAttribute('vdescription')+' deve ser diferente ao do campo '+obj2.getAttribute('vdescription')+'.')
                    return 0;
                }break;
        }
	return 1;
    }
}

//Função de conversão de tipo de dado
function vConvert(value, dtype){
    switch(dtype){
        case "integer":return parseInt(toInt(value),10);
	case "decimal":return parseFloat(toFload(value),10);
	case "shortdate":return cDate(value,'date');
	case "longdate":return cDate(value,'date');
	case "time":return cDate('23/01/1975 '+value,'date');
	default:return value;
    }
}

//Mensagen de validação, foco e ação de input hidden
function vMessage(obj){
    swal(obj.getAttribute("vmessage"));
    if(obj.type!='hidden'){
        obj.focus();
    }
}

//habilitação de objetos do formulário
function vEnable(obj){
    for (var x = 0; x < obj.length; x++) {
        obj[x].disabled = false;
    }
}

//Validação de range
function vRange(obj){
    var value=obj.value;
    var vmin=obj.getAttribute('vmin');
    var vmax=obj.getAttribute('vmax');
    if((obj.getAttribute('vdatatype')=='integer')||(obj.getAttribute('vdatatype')=='decimal')){
        if (isDecimal(value) && isDecimal(vmin) && isDecimal(vmax)){
            value = parseFloat(value,10)
            vmin = parseFloat(vmin,10)
            vmax = parseFloat(vmax,10)
            if (vmin<=vmax){
                if ((value<vmin) || (value>vmax)){
                    obj.setAttribute('vmessage','O valor do campo '+obj.getAttribute('vdescription')+' deve ser um número entre '+vmin+' e '+vmax+'.')
                    return 0;
		}
            }
	}
    }
    if((obj.getAttribute('vdatatype')=='shortdate')||(obj.getAttribute('vdatatype')=='longdate')||(obj.getAttribute('vdatatype')=='time')){
        if (isDate(value) && isDate(vmin) && isDate(vmax)){
            if (obj.getAttribute('vdatatype')=='time'){
                value = cDate('23/01/1975 '+value,'date')
		vmin = cDate('23/01/1975 '+vmin,'date')
		vmax = cDate('23/01/1975 '+vmax,'date')
            }else{
                value = cDate(value,'date')
		vmin = cDate(vmin,'date')
		vmax = cDate(vmax,'date')
            }
            if (vmin<=vmax){
                if ((value<vmin) || (value>vmax)){
                    obj.setAttribute('vmessage','O valor do campo '+obj.getAttribute('vdescription')+' deve ser uma data entre '+vmin+' e '+vmax+'.')
                    return 0;
		}
            }
	}
    }
    return 1;
}

//Validação de min
function vMin(obj){
    var value=obj.value;
    var vmin=obj.getAttribute('vmin');
    if((obj.getAttribute('vdatatype')=='integer')||(obj.getAttribute('vdatatype')=='decimal')){
        if (isDecimal(value) && isDecimal(vmin)){
            value = parseFloat(value,10)
            vmin = parseFloat(vmin,10)
            if (value<vmin){
                obj.setAttribute('vmessage','O valor do campo '+obj.getAttribute('vdescription')+' deve ser um número maior ou igual a '+vmin+'.')
		return 0;
            }
	}
    }
    if((obj.getAttribute('vdatatype')=='shortdate')||(obj.getAttribute('vdatatype')=='longdate')||(obj.getAttribute('vdatatype')=='time')){
        if (isDate(value) && isDate(vmin)){
            if (obj.getAttribute('vdatatype')=='time'){
                value = cDate('23/01/1975 '+value,'date')
		vmin = cDate('23/01/1975 '+vmin,'date')
            }else{
                value = cDate(value,'date')
		vmin = cDate(vmin,'date')
            }
            if (value<vmin){
                obj.setAttribute('vmessage','O valor do campo '+obj.getAttribute('vdescription')+' deve ser uma data maior ou igual a '+vmin+'.')
		return 0;
            }
	}
    }
    return 1;
}

/*
function validaForm(name, action){
    validaForm(name, action, 1, null);
}

function validaForm(name, action, validate){
    validaForm(name, action, validate, null);
}

function validaForm(name, action, validate, target){
    
    var submitData=checkForm(validate);
    var frm = $Id(name).get(0);
    if (frm==undefined)submitData=0;

    if(submitData){
        if(($Id('divbody').get(0)!=undefined) && ($Id('divload').get(0)!=undefined) && target==null)showLoad(1);
        vEnable(frm);
	frm.action = action;
        frm.submit();
	return true;
    }else{
        return false;
    }
}
*/