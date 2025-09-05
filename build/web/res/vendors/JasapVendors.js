
function swalConfirm(msg, cmdOk, cmdCancel){
    swal(msg, { buttons: { cancel: "Não", ok: "Sim" }, }).then((value) => { switch (value) { case "ok": cmdOk; break; default: cmdCancel; break; } });    
    swal(msg, { buttons: { cancel: "Não", ok: "Sim" }, }).then((value) => { switch (value) { case "ok": cmdOk; break; default: break; } });    
}

function configSummernote(){
    var js = {
        height: 150,
        toolbar: [
        ['style', ['bold', 'italic', 'underline', 'clear']],
        ['font', ['fontname', 'fontsize']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['height', ['height']],
        ['insert', ['picture']]
        ]
    };
    return js;
}

function cPicker(p){
    $('.c-picker').each( function() {
        $(this).minicolors({
            control: $(this).attr('data-control') || 'hue',
            defaultValue: $(this).attr('data-defaultValue') || '',
            format: $(this).attr('data-format') || 'hex',
            keywords: $(this).attr('data-keywords') || '',
            inline: $(this).attr('data-inline') === 'true',
            letterCase: $(this).attr('data-letterCase') || 'lowercase',
            opacity: $(this).attr('data-opacity'),
            position: $(this).attr('data-position') || p,
            swatches: $(this).attr('data-swatches') ? $(this).attr('data-swatches').split('|') : [],
            change: function(hex, opacity) {
            var log;
            try {
                log = hex ? hex : 'transparent';
                if( opacity ) log += ', ' + opacity;
                console.log(log);
            } catch(e) {}
            },
            theme: 'default'
        });
    });
}

var kvfileInput = 0;
function previewArq(kvUrl, kvType, kvCaption) {
    if(kvfileInput>0)$('#kv-explorer').fileinput('destroy');
    $("#kv-explorer").fileinput({
        theme: 'explorer-fas',
        hideThumbnailContent:false,
        showBrowse: false,
        showRemove: false,
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-primary btn-lg",
        fileType: "any",
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        overwriteInitial: false,
        initialPreviewAsData: true,
        initialPreviewShowDelete: false,
        initialPreview: [ kvUrl ],
        initialPreviewConfig: [ {type: kvType, caption: kvCaption, url: "{$url}", key: 1} ] 
    });
    $('#kv-explorer').fileinput('zoom', 'thumb-kv-explorer-init-0');
    kvfileInput = 1;
}

function previewVideo(kvUrl, kvType, kvCaption) {
    if(kvfileInput>0)$('#kv-explorer').fileinput('destroy');
    $("#kv-explorer").fileinput({
        theme: 'explorer-fas',
        hideThumbnailContent:false,
        showBrowse: false,
        showRemove: false,
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-primary btn-lg",
        fileType: "any",
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        overwriteInitial: false,
        initialPreviewAsData: true,
        initialPreviewShowDelete: false,
        initialPreview: [ kvUrl ],
        initialPreviewConfig: [ {type: kvType, caption: kvCaption, filetype: "video/mp4", url: "{$url}", key: 1} ] 
    });
    $('#kv-explorer').fileinput('zoom', 'thumb-kv-explorer-init-0');
    kvfileInput = 1;
}

