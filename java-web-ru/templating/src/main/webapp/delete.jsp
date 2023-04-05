<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Delete User ${user.get("id")}</title>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
		<div class="container my-3">
			<div class="card border-danger mb-3" style="max-width: 18rem;">
            	<div class="card-header">
            		<div>
            			<div>
            				<h4 class="m-0 text-center">Deleting User #${user.get("id")}</h4>
            			</div>
            		</div>
            	</div>
                <div class="card-body text-danger">
                	<h5 class="card-title">
                		${user.get("firstName")} ${user.get("lastName")}
                	</h5>
                	<p class="card-text">
                		E-mail: ${user.get("email")}
                	</p>
                </div>
                <div class="card-footer text-muted">
                	<div class="row align-items-center justify-content-evenly">
						<div class="col d-flex justify-content-center">
							<span>Are you sure?</span>
						</div>
						<div class="col d-flex justify-content-center">
							<form action="/users/delete?id=${user.get("id")}" method="post">
								<button type="submit" class="btn btn-danger">Delete</button>
							</form>
						</div>
					</div>
                </div>
            </div>
        </div>
    </body>
</html>
<!-- END -->
