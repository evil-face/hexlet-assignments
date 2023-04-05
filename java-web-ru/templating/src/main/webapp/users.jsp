<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User List</title>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
			<table class="table table-sm">
            	<thead>
                	<tr>
                    	<th scope="col" class="w-auto">ID</th>
                        <th scope="col">Full Name</th>
                    </tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users}">
						<tr>
                			<td>${user.get("id")}</td>
                    		<td>
                    			<a href="/users/show?id=${user.get("id")}">${user.get("firstName")} ${user.get("lastName")}</a>
                    		</td>
						</tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
<!-- END -->
