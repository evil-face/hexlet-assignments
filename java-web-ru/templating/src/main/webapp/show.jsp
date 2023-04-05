<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User ${user.get("id")}</title>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
		<div class="container my-3">
			<div class="card border-secondary mb-3" style="max-width: 18rem;">
            	<div class="card-header">
            		<div class="row align-items-center justify-content-evenly">
            			<div class="col-4">
            				User ${user.get("id")}
            			</div>
            			<div class="col-4">
            				<a class="btn btn-outline-danger" href="/users/delete?id=${user.get("id")}" role="button">Delete</a>
            			</div>
            		</div>
            	</div>
                <div class="card-body text-secondary">
                	<h5 class="card-title">
                		${user.get("firstName")} ${user.get("lastName")}
                	</h5>
                	<p class="card-text">
                		E-mail: ${user.get("email")}
                	</p>
                </div>
            </div>
        </div>
    </body>
</html>
<!-- END -->
