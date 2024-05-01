<%@page import="java.util.HashMap"%>
<%
HashMap userDetails=(HashMap)session.getAttribute("userDetails");
if(userDetails==null){
	session.setAttribute("msg", "sessionError");
	response.sendRedirect("User.jsp");
}else{
%>
<!DOCTYPE html>
<html>

<head>
    <title>GarageWorks</title>
    <link rel="icon" href="resource/autoworks-logo.png" />
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>

    <!-- fontawesome -->
    <link rel="stylesheet" href="resource/fontawesome/fontawesome.min.css">
    <script src="resource/fontawesome/fontawesome.min.js"></script>
    <!-- fontawesome END -->

    <!-- Lightbox CSS & Script -->
    <script src="resource/lightbox/ekko-lightbox.js"></script>
    <link rel="stylesheet" href="resource/lightbox/ekko-lightbox.css" />

    <!-- AOS CSS & Script -->
    <script src="resource/aos/aos.js"></script>
    <link rel="stylesheet" href="resource/aos/aos.css"/>

    <!-- custom css-->
    <link rel="stylesheet" href="resource/custom.css" />
    <!-- custom css END-->

    <meta name="author" content="Rahul Chauhan" />
    <meta name="description" content="This is a Auto Service website" />
    <meta name="keywords" content="best Auto Service comapny in noida" />
</head>

<body>
	<%
	String m=(String)session.getAttribute("msg");
	if(m!=null){
	%>
		<div class="alert alert-warning" role="alert">
		  <%=m %>
		</div>
	<%
	session.setAttribute("msg", null);
	}
	%>
    <nav class="navbar navbar-expand-sm bg-light sticky-top px-4">
        <a class="navbar-brand" href="UserHome.jsp">
            Garage<strong class="c-logo">Works</strong>
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <i class="fas fa-bars"></i> Menu
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav ml-auto">
                <!-- <ul class="navbar-nav mr-auto"> -->
                <!-- <ul class="navbar-nav mx-auto"> -->
                <li class="nav-item">
                    <a class="nav-link" href="UserHome.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="UserProfile.jsp">Profile</a>
                </li>
            </ul>
            <div>
                Welcome: <b> <%= userDetails.get("name")%> </b>
                <img src="GetUserPhoto?email=<%= userDetails.get("email")%>" height="30px"/>
                <a href="Logout">Logout</a>
            </div>
        </div>
    </nav>
    
    <section class="container">
    	<div class="row">
    		<div class="col-sm p-3 my-4 border">
    			<h3>User Photo Upload</h3>
    			<hr/>
    			<form action="UserPhotoUpload" method="post" enctype="multipart/form-data" >
		            <div class="py-2">
		                <input name="photo" class="form-control" type="file" accept=".jpg,.png" />
		            </div>
		            <div class="py-2">
		            	<input name="email" type="hidden" value="<%= userDetails.get("email")%>"/>
		                <button class="btn btn-success">Upload</button>
		            </div>
		        </form>
    		</div>
    		<div class="col-sm p-3 my-4 border">
    			<h3>User Password Change</h3>
    			<hr/>
    			<form action="UserPasswordChange" method="post" >
		            <div class="py-2">
		                <input name="o_password" class="form-control" type="password" placeholder="Your OLD Password" required />
		            </div>
		            <div class="py-2">
		                <input name="n_password" class="form-control" type="password" placeholder="Your NEW Password" required />
		            </div>
		            <div class="py-2">
		                <input name="c_password" class="form-control" type="password" placeholder="Confirm Password" required />
		            </div>
		            <div class="py-2">
		            	<input name="email" type="hidden" value="<%= userDetails.get("email")%>"/>
		                <button class="btn btn-success">Update</button>
		            </div>
		        </form>
    		</div>
    	</div>
    </section>
    
    <footer>
            Design & Develop by <a href="https://www.incapp.in">INCAPP</a> &copy; Reights reserved &nbsp;&nbsp;&nbsp; 
            Social Media:
            <a href="http://www.facebook.com/incapp" target="_blank" ><i class="fab fa-facebook"></i></a>
            <a href="http://www.instagram.com/incapp.in" target="_blank" ><i class="fab fa-instagram"></i></a>
            <a href="http://www.youtube.com/incapp" target="_blank" ><i class="fab fa-youtube"></i></a>
    </footer>

    <a id="myTopBtn"><i class="fas fa-arrow-circle-up"></i></a>
</body>
<script>
    AOS.init();
</script>

<script>
    //script for light box
    $(document).on('click', '[data-toggle="lightbox"]', function(event) {
          event.preventDefault();
          $(this).ekkoLightbox();
      });
  </script>

<script>
    //script for scroll to top
    $("#myTopBtn").click(function () {
        $("html, body").animate({scrollTop: 0}, 1000);
      });
  </script>


</html>
<%} %>

