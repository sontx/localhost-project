var tooltip = (function() {

    var elem, hideHandler, that = {};

    that.init = function(options) {
        elem = $(options.selector);
    };

    that.show = function(text) {
        clearTimeout(hideHandler);

        elem.find("span").html(text);
        elem.delay(200).fadeIn().delay(4000).fadeOut();
    };

    return that;
}());

$(function() {
    tooltip.init({
        "selector" : ".bb-alert"
    });
});

if (!String.prototype.format) {
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) { 
            return typeof args[number] != 'undefined'
                ? args[number]
                : match;
        });
    };
};

function registerAccount(email) {
    bootbox.dialog({
        title : 'You want to register an account?',
        message : 
              '<span>Send an email to admin for your request: </span>' 
            + '<a href="mailto:' + email + '" onclick="function() {bootbox.hideAll();};">' + email + '</a>',
        buttons : {
            close : {
                label : "Close",
                className : "btn-success"
            }
        }
    });
    return false;
}

function redirect(link) {
	window.location.href = link;
}