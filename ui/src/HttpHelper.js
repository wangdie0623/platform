const cross_option = {
    xhrFields: {
        withCredentials: true
    },
    crossDomain: true,
    cache: false,
}



// VUE_APP_*可自定义环境变量
function HttpHelper(cross) {
    this.origin = process.env.VUE_APP_ORIGIN;
    this.cross = cross;
    this.install = function (Vue, options) {
        Vue.prototype.$helper = this;
    }
    this.postForm = function (url, data) {
        url = this.origin + url;
        var o = $.extend({type: "POST", url: url, data: data}, this.cross ? cross_option : {})
        return $.ajax(o);
    }

    this.postFormSyn = function (url, data) {
        url = this.origin + url;
        var o = $.extend({type: "POST", url: url, async: false, data: data}, this.cross ? cross_option : {})
        return $.ajax(o);
    }

    this.postJson = function (url, data) {
        url = this.origin + url;
        var o = $.extend({
            type: "POST",
            url: url,
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data)
        }, this.cross ? cross_option : {})
        return $.ajax(o);
    }

    this.postJsonSyn = function (url, data) {
        url = this.origin + url;
        var o = $.extend({
            type: "POST",
            url: url,
            async: false,
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data)
        }, this.cross ? cross_option : {})
        return $.ajax(o);
    }

    this.get = function (url, params) {
        url = this.origin + url;
        var o = $.extend({type: "get", url: url, data: params}, this.cross ? cross_option : {})
        return $.ajax(o);
    }

    this.getSyn = function (url, params) {
        url = this.origin + url;
        var o = $.extend({type: "get", url: url, async: false, data: params}, this.cross ? cross_option : {});
        return $.ajax(o);
    }

    this.postFile = function (url, data) {
        var o = $.extend({
            url: url,
            type: 'POST',
            cache: false,
            async: false,
            data: data,
            processData: false,
            contentType: false
        }, this.cross ? cross_option : {});
        return $.ajax(o)
    }
}

var Helper = new HttpHelper(true);

export default Helper;