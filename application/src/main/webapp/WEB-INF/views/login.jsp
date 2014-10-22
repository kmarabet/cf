<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
	<head>
        <style>
            .logo {
                width: 64px;
                height: 64px;
                background-repeat: no-repeat;
                background-position: center;
                background-image:url('./assets/img/browsers_icon.png');
            }
        </style>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title><spring:message code="login.title"/></title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link rel="stylesheet" href="./assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="./assets/css/font-awesome.min.css" />

		<!-- fonts -->
		<link rel="stylesheet" href="./assets/css/ace-fonts.min.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="./assets/css/ace.min.css" />

        <jsp:include page="./partial/locale.jsp" />
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="red">CF test</span>
									<span class="white">Application</span>
								</h1>
								<h4 class="blue">&copy;kmarabet</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												<spring:message code="login.enter.credentials"/>
											</h4>

											<div class="space-6"></div>

											<form id="loginForm" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" name="username" placeholder='<spring:message code="login.placeholder.name"/>' />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" name="password" placeholder='<spring:message code="login.placeholder.password"/>' />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key bigger-110"></i>
															<spring:message code="login.button.login"/>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->

										<div class="toolbar clearfix">
										</div>
									</div><!-- /widget-body -->
								</div><!-- /login-box -->
                                <div class="row no-margin text-center">
                                <div class="col-sm-3">
                                    <div title="Chrome 30+" class="logo " style="background-position:0 0"></div>
                                </div>
                                <div class="col-sm-3">
                                    <div title="Firefox 27+"class="logo" style="background-position:-64px 0"></div>
                                </div>
                                <div class="col-sm-3">
                                    <div title="IE 9+"class="logo" style="background-position:-128px 0"></div>
                                </div>
                                <div class="col-sm-3">
                                    <div title="Safari 6+"class="logo" style="background-position:-192px 0"></div>
                                </div>
							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='./assets/js/lib/jquery.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
        <script type="text/javascript">
         window.jQuery || document.write("<script src='assets/js/lib/jquery-1.10.2.min.js'>"+"<"+"/script>");
        </script>
        <![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/lib/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
		<script type="text/javascript" src="./assets/js/lib/jquery.validate.min.js"></script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript" src='./assets/js/custom/login.min.js'>
		</script>
	</body>
</html>
