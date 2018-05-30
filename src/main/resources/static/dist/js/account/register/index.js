(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
var FormView = require('./view/FormView');

$(function () {
	new FormView({});
});

},{"./view/FormView":8}],2:[function(require,module,exports){
var $ = require('jquery');
var BackBone = require('backbone');


module.exports = BackBone.Model.extend({
	defaults: {
		email: '',
		status: false
	},
	initialize: function(options) {
	},
	validate: function (attrs, options) {
		if(options.err) {
			this.set('status', false);
			return options.err;
		}
		if(!this.emailAddressCheck(attrs.email)) {
			this.set('status', false);
			return 'email address is invalid';
		}
		this.set('status', true);
		this.trigger('validate.ok', this);
	},
	emailAddressCheck: function (str) {
		var reg = /^\S+@\S+\.\S{2,}$/;
		// var reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return reg.test(str);
	}
});
},{"backbone":11,"jquery":12}],3:[function(require,module,exports){
var $ = require('jquery');
var BackBone = require('backbone');

module.exports = BackBone.Model.extend({
	defaults: {
		state: false
	},
	initialize: function(options) {
		this.signUpPath = "/doSignUp";
	},
	doSignUp: function(data) {
		var self = this;
		BackBone.$.ajax({
			url: this.signUpPath,
			type: 'POST',
			dataType: 'JSON',
			contentType: 'application/json; charset=utf-8',
			data: JSON.stringify(data)
		}).done(function(rs) {
			if (rs) {
				self.trigger('signUpDone', rs);
			} else {
				self.trigger('signUpFail');
			}
		}).fail(function() {
			self.trigger('signUpFail');
		})
	}
});
},{"backbone":11,"jquery":12}],4:[function(require,module,exports){
var $ = require('jquery');
var BackBone = require('backbone');


module.exports = BackBone.Model.extend({
	defaults: {
		password: '',
		status: false
	},
	initialize: function(options) {
	},
	validate: function (attrs, options) {
		if(options.err) {
			this.set('status', false);
			return options.err;
		}
		var pw = attrs.password;
		if(pw.length > 30 || pw.length < 6) {
			this.set('status', false);
			return 'Password must be more than 6 and less than 30 characters long';
		}
		this.set('status', true);
		this.trigger('validate.ok', this);
	}
});
},{"backbone":11,"jquery":12}],5:[function(require,module,exports){
var $ = require('jquery');
var BackBone = require('backbone');


module.exports = BackBone.Model.extend({
	defaults: {
		username: '',
		status: false
	},
	initialize: function(options) {
	},
	validate: function (attrs, options) {
		if(options.err) {
			this.set('status', false);
			return options.err;
		}
		var username = attrs.username;
		if(username.length > 30 || username.length < 6) {
			this.set('status', false);
			return 'Username must be more than 6 and less than 30 characters long';
		}
		if(!this.userNameCheck(username)) {
			this.set('status', false);
			return 'Username can only consist of alphabetical, number, dot and underscore';
		}
		this.set('status', true);
		this.trigger('validate.ok', this);
	},
	userNameCheck: function (str) {
		var reg = /^[a-zA-Z0-9_\.]+$/;
		return reg.test(str);
	}
});
},{"backbone":11,"jquery":12}],6:[function(require,module,exports){
var BackBone = require('backbone');
module.exports = BackBone.View.extend({
	initialize: function() {
		this.modelInit();
		this.model.on('invalid', this.errViewUpdate, this);
		this.model.on('validate.ok', this.errViewUpdate, this);
	},
	errViewUpdate: function () {
		this.$el.removeClass('has-error has-feedback has-success');
		this.$('.form-control-feedback').removeClass('glyphicon-remove glyphicon-ok');
		if(this.model.get('status')){
			this.$el.addClass('has-success has-feedback');
			this.$('.form-control-feedback').addClass('glyphicon-ok');
			this.$('.sr-only').text('success');
			this.$('.err-msg').text('');
		}else {
			this.$el.addClass('has-error has-feedback');
			this.$('.form-control-feedback').addClass('glyphicon-remove');
			this.$('.sr-only').text('error, ' + this.model.validationError);
			this.$('.err-msg').text(this.model.validationError);
		}
	},
	modelInit: function () {

	}

});


},{"backbone":11}],7:[function(require,module,exports){
var $ = require('jquery');
var EmailModel = require('../model/EmailModel');
var BaseView = require('./BaseView');

module.exports = BaseView.extend({
	el: '.email-group',
	events: {
		'input #email': 'emailInput'
	},
	modelInit: function() {
		this.model = new EmailModel({});
	},
	emailInput: function (e) {
		this.model.set({
			email: $(e.target).val().trim()
		}, {
			validate: true
		});
	}
});

},{"../model/EmailModel":2,"./BaseView":6,"jquery":12}],8:[function(require,module,exports){
var $ = require('jquery');
var BackBone = require('backbone');
var layer = require('layer');
var FormModel = require('../model/FormModel');
var UserNameView = require('./UserNameView');
var PasswordView = require('./PasswordView');
var EmailView = require('./EmailView');

var viewSet ={
	username: 'UserNameView',
	password: 'PasswordView',
	email: 'EmailView'
}
module.exports = BackBone.View.extend({
	el: 'form',
	events: {
		'click #sign-up:not([disabled])': 'doSignUp'
	},
	initialize: function() {
		this.model = new FormModel();
		this.model.on('signUpDone', this.signUpDone, this);
		this.model.on('signUpFail', this.signUpFail, this);

		this.UserNameView = new UserNameView();
		this.PasswordView = new PasswordView();
		this.EmailView = new EmailView();

		this.UserNameView.model.on('change:status', this.statusChange, this);
		this.PasswordView.model.on('change:status', this.statusChange, this);
		this.EmailView.model.on('change:status', this.statusChange, this);
	},
	statusChange:function () {
		if(this.UserNameView.model.get('status')
			&& this.PasswordView.model.get('status')
			&& this.EmailView.model.get('status')) {
			this.signUpSwitch('enable');
		}else {
			this.signUpSwitch('disable');
		}
	},
	signUpSwitch: function (status) {
		var $btn = this.$('#sign-up');
		$btn.html('SIGN UP');
		if(status === 'enable') {
			$btn.prop('disabled', false);
		}else {
			$btn.prop('disabled', true);
			if(status === 'loading') {
				$btn.html('SIGN UP<i></i>');
			}
		}
	},
	doSignUp: function () {
		this.signUpSwitch('loading');
		this.model.doSignUp({
			username: this.UserNameView.model.get('username'),
			password: this.PasswordView.model.get('password'),
			email: this.EmailView.model.get('email')
		});
	},
	signUpDone: function (rs) {
		var code = rs.code;
		if(code === '000') {
			console.log('congratulations');
			layer.open({
				content: 'Success'
				,skin: 'msg'
				,time: 2 //2秒后自动关闭
			});
			this.signUpSwitch('disable');
			window.location.href = '/signin';

		}else if(code === '001') {
			// data is invalid
			layer.open({
				content: 'sign up failed'
				,skin: 'msg'
				,time: 2 //2秒后自动关闭
			});
			var errData = rs.errData;
			var self = this;
			_.map(errData, function (value, key) {
				self[viewSet[key]].model.isValid({err: value})
			});
		}else {
			layer.open({
				content: 'sign up failed, please try later'
				,skin: 'msg'
				,time: 2 //2秒后自动关闭
			});
			this.signUpSwitch('enable');
		}

	},
	signUpFail: function () {
		layer.open({
			content: 'system error, please try later'
			,skin: 'msg'
			,time: 2 //2秒后自动关闭
		});
		this.signUpSwitch('enable');
	}
});
},{"../model/FormModel":3,"./EmailView":7,"./PasswordView":9,"./UserNameView":10,"backbone":11,"jquery":12,"layer":13}],9:[function(require,module,exports){
var $ = require('jquery');
var PasswordModel = require('../model/PasswordModel');
var BaseView = require('./BaseView');

module.exports = BaseView.extend({
	el: '.password-group',
	events: {
		'input #password': 'passwordInput'
	},
	modelInit: function() {
		this.model = new PasswordModel({});
	},
	passwordInput: function (e) {
		this.model.set({
			password: $(e.target).val().trim()
		}, {
			validate: true
		});
	}
});

},{"../model/PasswordModel":4,"./BaseView":6,"jquery":12}],10:[function(require,module,exports){
var $ = require('jquery');
var UserNameModel = require('../model/UserNameModel');
var BaseView = require('./BaseView');

module.exports = BaseView.extend({
	el: '.username-group',
	events: {
		'input #username': 'usernameInput'
	},
	modelInit: function () {
		this.model = new UserNameModel();
	},
	usernameInput: function (e) {
		this.model.set({
			username: $(e.target).val().trim()
		}, {
			validate: true
		});
	}
});

},{"../model/UserNameModel":5,"./BaseView":6,"jquery":12}],11:[function(require,module,exports){
module.exports = window.Backbone;
},{}],12:[function(require,module,exports){
module.exports = window.$;
},{}],13:[function(require,module,exports){
module.exports = window.layer;
},{}]},{},[1]);
