function ckeckusername(username,iusername){
	username.val().length!=11 ? iusername.hide() : iusername.show()
}
function ckeckpassword(password,ipassword){
	password.val().length>=8 ? ipassword.show() : ipassword.hide()

}
function changeeventrusername(){
	ckeckusername($("#username"),$("#iusername"))
	ckeckusername($("#rusername"),$("#irusername"))
}
function changeeventrpassword(){
	ckeckpassword($("#password"),$("#ipassword"))
	ckeckpassword($("#rpassword"),$("#irpassword"))
}
function changeeventrpassword2(){
	($("#rpassword2").val()==$("#rpassword").val() && $("#rpassword2").val().length>=8)? $("#irpassword2").show():$("#irpassword2").hide()
}
function changeeventrusername2(){
	$("#rphone").val().length>=2? $("#irphone").show():$("#irphone").hide()
}
$("#irusername").hide()
$("#irpassword").hide()
$("#iusername").hide()
$("#ipassword").hide()
$('#irpassword2').hide()
$("#irphone").hide()

function adminlogin() {
	$.ajax({
		type:"get",
		datatype:"json",
		url:"/admin/checkadmin",
		data:{
			username:$("#adminusername").val(),
			password:$("#adminpassword").val(),
			code:$("#admincode").val()
		},
		success:function (data) {
			if (data==2){
				window.location.href="/admin/adminmain.html"
			}else if(data==0){
				fail("账号或密码错误")
				$("#username").val("")
				$("#password").val("")
				$("#code").val("")
			}else {
				fail("验证码不一致")
				$("#code").val("")
			}
		}
	});
}
function register() {
	if($("#rusername").val().length!=11){
		fail("手机号码格式错误")
		return;
	}
	if ($("#rpassword").val().length<8){
		fail("密码长度小于8位")
		return;
	}
	if($("#rpassword2").val()!=$("#rpassword").val()){
		fail("两次密码不同");
		return;
	}
	$.ajax({
		type:"get",
		datatype:"json",
		url:"/admin/register",
		data:{
			username:$("#rusername").val(),
			password:$("#rpassword").val()
		},
		success:function (data) {
			if (data==1){
				success("注册成功")
			}else if(data=2){
				fail("已有此用户，注册失败")
			}
			$("#rusername").val("")
			$("#rpassword").val("")
			$("#rpassword2").val("")
		}
	});
}

function useregister(){
	if($("#rphone").val()<2){
		fail("姓名长度小于二");
		return;
	}
	if($("#rusername").val().length!=11){
		fail("手机号码格式错误")
		return;
	}
	if ($("#rpassword").val().length<8){
		fail("密码长度小于8位")
		return;
	}
	if($("#rpassword2").val()!=$("#rpassword").val()){
		fail("两次密码不同");
		return;
	}
	$.ajax({
		type:"get",
		datatype:"json",
		url:"/user/register",
		data:{
			name:$("#rphone").val(),
			phone:$("#rusername").val(),
			password:$("#rpassword").val()
		},
		success:function (data) {
			if (data==1){
				success("注册成功")
			}else if(data=2){
				fail("已有此用户，注册失败")
			}
			$("#userregistermyModal").modal('hide', 'fit')
			// $("#rusername").val("")
			// $("#rpassword").val("")
			// $("#rpassword2").val("")
			// $("#rphone").val("")
		}
	});
}
function userlogin() {
	$.ajax({
		type:"get",
		datatype:"json",
		url:"/user/checkuser",
		data:{
			phone:$("#username").val(),
			password:$("#password").val(),
			code:$("#code").val()
		},
		success:function (data) {
			if (data==2){
				window.location.href="/user/usermain.html"
			}else if(data==0){
				fail("账号或密码错误")
				$("#username").val("")
				$("#password").val("")
				$("#code").val("")
			}else if(data==1){
				fail("验证码不一致")
				$("#code").val("")
			}else if(data==4){
				fail("用户状态异常")

			}
		}
	});
}