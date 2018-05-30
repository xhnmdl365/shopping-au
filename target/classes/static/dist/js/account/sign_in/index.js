(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
var FormView = require('./view/FormView');
$(function () {
	new FormView({});
});

},{"./view/FormView":3}],2:[function(require,module,exports){
var $ = require('jquery');
var BackBone = require('backbone');

module.exports = BackBone.Model.extend({
	defaults: {
		email: '',
		password: '',
		state: false
	},
	initialize: function(options) {
		this.signInPath = "/doSignIn";
	},
	doSignIn: function() {
		var self = this;
		BackBone.$.ajax({
			url: this.signInPath,
			type: 'POST',
			dataType: 'JSON',
			data: {
				email: self.get('email'),
				password: self.get('password')
			}
		}).done(function(rs) {
			if (rs) {
				self.trigger('signInDone', rs);
			} else {
				self.trigger('signInFail');
			}
		}).fail(function() {
			self.trigger('signInFail');
		})
	}
});
},{"backbone":4,"jquery":5}],3:[function(require,module,exports){
var $ = require('jquery');
var BackBone = require('backbone');
var layer = require('layer')
var FormModel = require('../model/FormModel');


module.exports = BackBone.View.extend({
	el: 'form',
	events: {
		'input #email, #password': 'inputChange',
		'click #sign-in:not([disabled])': 'doSignIn'
	},
	initialize: function() {
		this.model = new FormModel();

		this.model.on('change', this.statusChange, this);
		this.model.on('signInDone', this.signInDone, this);
		this.model.on('signInFail', this.signInFail, this);
	},
	inputChange: function(e) {
		var data = {};
		var key = this.$(e.target).attr('id');
		data[key] = this.$(e.target).val();
		this.model.set(data);
	},

	statusChange: function () {
		var model = this.model;
		if(model.get('email') && model.get('password')) {
			this.signInSwitch('enable');
		}else {
			this.signInSwitch('disable');
		}
	},
	signInSwitch: function (status) {
		var $btn = this.$('#sign-in');
		$btn.html('SIGN IN');
		if(status === 'enable') {
			$btn.prop('disabled', false);
		}else {
			$btn.prop('disabled', true);
			if(status === 'loading') {
				$btn.html('SIGN IN<i></i>');
			}
		}
	},
	doSignIn: function () {
		this.signInSwitch('loading');
		this.model.doSignIn();
	},
	signInDone: function (rs) {
		var code = rs.code;
		if(code === '000') {
			layer.open({
				content: 'Success'
				,skin: 'msg'
				,time: 2 //2秒后自动关闭
			});
			this.signInSwitch('enable');
			var link = new URL(window.location.href).searchParams.get('link')
			if(!link) {
				link = '/'
			}
			window.location.href = link
		}else {
			layer.open({
				content: rs.msg
				,skin: 'msg'
				,time: 2 //2秒后自动关闭
			});
			this.signInSwitch('enable');
		}

	},
	signInFail: function () {
		layer.open({
			content: 'system error, please try later'
			,skin: 'msg'
			,time: 2 //2秒后自动关闭
		});
		this.signInSwitch('enable');
	}
});
},{"../model/FormModel":2,"backbone":4,"jquery":5,"layer":6}],4:[function(require,module,exports){
module.exports = window.Backbone;
},{}],5:[function(require,module,exports){
module.exports = window.$;
},{}],6:[function(require,module,exports){
module.exports = window.layer;
},{}]},{},[1]);
