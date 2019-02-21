var markedjs = document.createElement("script");
markedjs.setAttribute("type", "text/javascript");
markedjs.setAttribute("src", "/static/js/marked.min.js");
var highlight = document.createElement("script");
highlight.setAttribute("type", "text/javascript");
highlight.setAttribute("src", "/static/js/highlight.pack.js");
document.body.appendChild(markedjs);
document.body.appendChild(highlight);

var md = {
    isInit : false,
    init : function () {
        marked.setOptions({
            langPrefix: 'hljs lang-',
            breaks: true,
            headerIds: false,
            xhtml: true,
            highlight: function (code ,event) {
                return hljs.highlightAuto(code, [event]).value;
            }
        });
    },
    parse : function (str) {
        if (!this.isInit)
            this.init();
        return marked(str);
    },
    parseAuto : function (before, after) {
        if (after === undefined)
            after = before;
        var text = typeof(before)=='string' ? before : before.innerHTML;
        // text = text.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return {'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'}[t];});
        convertCode(after, this.parse(text));
    }
}

function convertCode(element, contentStr) {
    var content = document.createElement("div");
    content.innerHTML = contentStr;
    content.querySelectorAll('code.hljs').forEach(function (codeElement) {
        var ul = document.createElement("ul");
        codeElement.innerHTML.split(/[\n]/g).forEach(function (line, index) {
            var li = document.createElement("li");
            li.setAttribute("onClick", 'selectLine(this)');
            var numDiv = document.createElement("span");
            numDiv.className = 'hljs-ln-num';
            numDiv.setAttribute("ln-num", index + 1);
            var codeDiv = document.createElement("span");
            codeDiv.className = 'hljs-ln-code';
            codeDiv.innerHTML = line;
            li.appendChild(numDiv);
            li.appendChild(codeDiv);
            ul.appendChild(li);
        });
        clean(codeElement).appendChild(ul);
    });

    clean(element);
    for (var i = 0; i < content.childNodes.length; i++)
        element.appendChild(content.childNodes[i]);

    element.querySelectorAll('code.hljs ul').forEach(function (ulElement) {
        var width = ulElement.scrollWidth + "px";
        ulElement.childNodes.forEach(function(liElement){
            liElement.style.width = width;
        });
    });
    content = null;
}

function clean(element) {
    while (element.hasChildNodes())
        element.removeChild(element.firstChild);
    return element;
}

function selectLine(element) {
    for (var child = element.parentNode.firstChild; child; child = child.nextSibling)
        child.classList.remove('selected');
    element.classList.add('selected');
}