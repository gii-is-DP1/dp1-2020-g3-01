<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="owners">

	<h2>Mechanic Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out
						value="${mechanic.firstName} ${mechanic.lastName}" /></b></td>
		</tr>
		<tr>
			<th>Birth Date</th>
			<td><c:out value="${mechanic.birthDate}" /></td>
		</tr>
		<tr>
			<th>Residence</th>
			<td><c:out value="${mechanic.residence}" /></td>
		</tr>
		<tr>
			<th>Nationality</th>
			<td><c:out value="${mechanic.nationality}" /></td>
		</tr>
	</table>

</petclinic:layout>
