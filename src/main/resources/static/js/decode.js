// html태그 decode
function decodeHTMLEntities(str) {
    if(str !== undefined && str !== null && str !== '') {
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");
        str = str.replace(/\"/g,"&quot;");
        str = str.replace(/\'/g,"&#39;");
        str = str.replace(/\n/g,"<br />");
    }
    return str;
}


//html태그 encode
function encodeHTMLEntities(str) {
    if(str !== undefined && str !== null && str !== '') {
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        str = str.replace(/&quot;/g,"\"");
        str = str.replace(/&#39;/g,"\'");
        str = str.replace(/(<br>|<br\/>|<br \/>)/g,"\n");
    }
    return str;
}